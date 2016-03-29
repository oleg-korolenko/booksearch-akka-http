package com.wemanity.booksearch.actor

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit, ImplicitSender}
import com.typesafe.config.ConfigFactory
import com.wemanity.booksearch.{GoodReadsBookReviews, StopSystemAfterAll}
import com.wemanity.booksearch.actor.BookSearchActor.SearchByISBN
import org.scalatest.{MustMatchers, WordSpecLike}


/**
  * Created by oleg on 26/03/16.
  */
class GoodReadsActorSpec extends TestKit(ActorSystem("test"))
  with WordSpecLike
  with MustMatchers
  with ImplicitSender
  with StopSystemAfterAll {

  val apiKey = ConfigFactory.load().getString("api.goodreads.key")


  "A GoodReadsActor" should {
    "should return book reviews info searching by ISBN" in {
      val isbn =1935182757
      val actorRef = TestActorRef(new GoodReadsActor(apiKey))
      actorRef ! SearchByISBN(isbn)
      val bookInfos=expectMsgClass(classOf[Some[GoodReadsBookReviews]])
      val bookReviews=bookInfos.get
      assert(!bookReviews.books.isEmpty)
      val bookReview=bookReviews.books.head
      assertResult(isbn.toString)(bookReview.isbn)
    }
    "should return None searching for non existing book review" in {
      val isbn =11
      val actorRef = TestActorRef(new GoodReadsActor(apiKey))
      actorRef ! SearchByISBN(isbn)
      expectMsgClass(classOf[Option[Nothing]])
    }

  }

}
