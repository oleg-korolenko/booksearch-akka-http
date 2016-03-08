package com.wemanity.booksearch.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.wemanity.booksearch.actor.BookSearchActor.{SearchByISBN, SearchByName}
import com.wemanity.booksearch.actor.UrlCallerActor.Get

/**
  * Created by oleg on 26/02/16.
  */

object GoogleBooksActor {
  def props() = Props(new GoogleBooksActor())
}


class GoogleBooksActor() extends Actor with ActorLogging {

  import akka.http.scaladsl.model._

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))

  override def receive: Receive = {
    case SearchByName(name) => {
      val url = s"https://www.googleapis.com/books/v1/volumes?q={$name}"
      val urlCaller = context.actorOf(UrlCallerActor.props())
      urlCaller ! Get(url)
    }
    case SearchByISBN(isbn) => {
      val url  = s"https://www.googleapis.com/books/v1/volumes?q=isbn:{$isbn}"
      val urlCaller = context.actorOf(UrlCallerActor.props())
      urlCaller ! Get(url)
    }

    case HttpResponse(code, headers, entity, _) =>
      log.info("Got response from url caller :" + headers)

  }
}