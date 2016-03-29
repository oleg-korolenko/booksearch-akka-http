package com.wemanity.booksearch.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.{ask, _}
import akka.util.Timeout
import com.typesafe.config.Config
import com.wemanity.booksearch._
import com.wemanity.booksearch.actor.BookSearchActor.SearchByISBN

import scala.concurrent.duration._

/**
  * Created by oleg on 06/12/15.
  */
object BookSearchActor {
  def props(config: Config) = Props(new BookSearchActor(config))

  case class SearchByName(name: String)

  case class SearchByISBN(isbn: Long)

}

class BookSearchActor(config: Config) extends Actor with ActorLogging {

  implicit def executionContext = context.dispatcher

  implicit val requestTimeout = Timeout(5 seconds)

  lazy val googleBooksActor = context.actorOf(GoogleBooksActor.props())
  lazy val goodReadsActor = context.actorOf(GoodReadsActor.props(config.getString("api.goodreads.key")))

  override def receive: Receive = {

    case SearchByISBN(isbn) => {
      val gBookFuture = googleBooksActor.ask(SearchByISBN(isbn)).mapTo[Option[GoogleBooks]]
      val bookReviewsFuture = goodReadsActor.ask(SearchByISBN(isbn)).mapTo[Option[GoodReadsBookReviews]]

      val fullBookInfos =
        for (
          (gBook, bookReviews) <- gBookFuture.zip(bookReviewsFuture)
        ) yield Some(BookInfos(gBook, bookReviews))

      // pipe feature back to sender
      fullBookInfos.to(sender)
    }
  }
}
