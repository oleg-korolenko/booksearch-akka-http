package com.wemanity.booksearch.actor

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import com.wemanity.booksearch.actor.BookSearchActor.SearchByISBN
import com.wemanity.booksearch.{BookInfos, GoodReadsBookReviews, GoogleBooks, StopSystemAfterAll}
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by oleg on 28/03/16.
  */
class BookSearchActorSpec extends TestKit(ActorSystem("test"))
  with WordSpecLike
  with MustMatchers
  with ImplicitSender
  with StopSystemAfterAll {

  val config = ConfigFactory.load()
  val isbn = 1617291013

  implicit val timeout = Timeout(5 seconds)
  implicit val executionContext = system.dispatcher


  "A BookSearchActor" should {
    "should return future using ask" in {
      val actorRef = TestActorRef(new BookSearchActor(config))
      val future = actorRef.ask(SearchByISBN(isbn))
      Await.ready(future, timeout.duration)
    }
    "should return book infos receiving search by isbn" in {
      val actorRef = TestActorRef(new BookSearchActor(config))
      actorRef ! SearchByISBN(isbn)
      val bookInfos = expectMsgClass(classOf[Some[BookInfos]])
      assertGoogleBooksResult(bookInfos.get.googleBooks)
      assertGoodReviewsResult(bookInfos.get.reviews)
    }
  }

  private def assertGoodReviewsResult(reviews: Option[GoodReadsBookReviews]) {
    val bookReviews = reviews.get
    assert(!bookReviews.books.isEmpty)
    val bookReview = bookReviews.books.head
    assertResult(isbn.toString)(bookReview.isbn)
  }

  private def assertGoogleBooksResult(googleBooks: Option[GoogleBooks]) {
    val bookSearchResult = googleBooks.get
    assertResult("books#volumes")(bookSearchResult.kind)
    assertResult(1)(bookSearchResult.totalItems)
    val title = bookSearchResult.items.get.head.volumeInfo.title
    assertResult("Akka in Action")(title)
  }
}