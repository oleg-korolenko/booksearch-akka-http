package com.wemanity.booksearch.api


import akka.actor.{ActorRef, PoisonPill}
import akka.pattern.ask
import akka.util.Timeout
import com.wemanity.booksearch.{BookInfos, BookInfo}
import com.wemanity.booksearch.actor.BookSearchActor.{SearchByISBN, SearchByName}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

/**
  * Contains book api methods
  * Book search actor creation should be provided by mixin class
  *
  * Created by oleg on 16/01/16.
  */
trait BookApi {

  implicit def executor: ExecutionContext

  implicit val requestTimeout = Timeout(5 seconds)

  val bookSearchActor: ActorRef

  def searchByISBN(isbn: Long): Future[Option[BookInfos]] = {
    // mapping to specific type as message is not typed
    bookSearchActor.ask(SearchByISBN(isbn)).mapTo[Option[BookInfos]]
  }
}
