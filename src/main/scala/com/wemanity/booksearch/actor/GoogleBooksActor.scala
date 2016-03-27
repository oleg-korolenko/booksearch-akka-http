package com.wemanity.booksearch.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.wemanity.booksearch.{GoogleBooks, GoogleBook, BookJsonProtocol}
import com.wemanity.booksearch.actor.BookSearchActor.SearchByISBN

import scala.concurrent.Future
import scala.util.{Failure, Try}

/**
  * Created by oleg on 26/02/16.
  */

object GoogleBooksActor {
  def props() = Props(new GoogleBooksActor())

}


class GoogleBooksActor() extends Actor
  with ActorHttpClient
  with ActorLogging
  with BookJsonProtocol {

  import context.dispatcher

  implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))

  override def receive: Receive = {
    case SearchByISBN(isbn) => {
      val url = s"https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn"
      val foundBook: Future[Option[GoogleBooks]] = get(url)
        .flatMap { response =>
          response.status match {
            case OK => {
              Unmarshal(response.entity).to[GoogleBooks].map(Some(_))
            }
            case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
              log.error(s"Failed to call GoogleBooks : status: ${response.status} and entity: $entity")
              Future(None)
            }
          }
        }
      foundBook.to(sender)
    }
  }
}