package com.wemanity.booksearch.actor

import akka.actor.{Actor, Props, ActorLogging}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializerSettings, ActorMaterializer}
import com.wemanity.booksearch.{GoodReadsBookReviews, GoodReadsBookReview, BookJsonProtocol}
import com.wemanity.booksearch.actor.BookSearchActor.SearchByISBN
import akka.pattern._
import scala.concurrent.Future
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._


/**
  * Created by oleg on 14/03/16.
  */
object GoodReadsActor {
  def props(apiKey:String) = Props(new GoodReadsActor(apiKey))
}


class GoodReadsActor(apiKey:String) extends Actor
  with ActorHttpClient
  with ActorLogging
  with BookJsonProtocol {

  import context.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))

  override def receive: Receive = {
    case SearchByISBN(isbn) => {

      val url = s"https://www.goodreads.com/book/review_counts.json?isbns=$isbn&key=$apiKey"
      val foundBook: Future[Option[GoodReadsBookReviews]] = get(url)
        .flatMap { response =>
          response.status match {
            case OK => {
              Unmarshal(response.entity).to[GoodReadsBookReviews].map(Some(_))
            }
            case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
              log.error(s"Failed to call GoodReads : status: ${response.status} and entity: $entity")
              Future(None)
            }
          }
        }
      foundBook.to(sender)
    }
  }
}
