package com.wemanity.booksearch.actor

import akka.actor.{Actor, ActorSystem}
import akka.http.scaladsl.model.{HttpProtocol, StatusCodes, HttpResponse}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.wemanity.booksearch.{TestHttpClientReturningBadRequest, GoogleBooks, GoogleBook, StopSystemAfterAll}
import com.wemanity.booksearch.actor.BookSearchActor.SearchByISBN
import org.scalatest.{MustMatchers, WordSpecLike}


/**
  * Created by oleg on 09/12/15.
  */
class GoogleBooksActorSpec extends TestKit(ActorSystem("test"))
  with WordSpecLike
  with MustMatchers
  with ImplicitSender
  with StopSystemAfterAll {

  "A GoogleBooksActor" should {
    "should return book info searching by ISBN" in {
      val actorRef = TestActorRef(new GoogleBooksActor)
      actorRef ! SearchByISBN(1935182757)
      val bookInfos = expectMsgClass(classOf[Some[GoogleBooks]])
      val bookSearchResult = bookInfos.get
      assertResult("books#volumes")(bookSearchResult.kind)
      assertResult(1)(bookSearchResult.totalItems)
      val title = bookSearchResult.items.get.head.volumeInfo.title
      assertResult("Scala in Action")(title)
    }

    "should return book info without found items searching by non existing ISBN" in {
      val actorRef = TestActorRef(new GoogleBooksActor)
      actorRef ! SearchByISBN(1)
      val bookInfos = expectMsgClass(classOf[Some[GoogleBooks]])
      val bookSearchResult = bookInfos.get
      assertResult("books#volumes")(bookSearchResult.kind)
      assertResult(0)(bookSearchResult.totalItems)
      assertResult(None)(bookSearchResult.items)
    }
    "return None if google books returned an error code" in {
      val actorRef = TestActorRef(new GoogleBooksActor with TestHttpClientReturningBadRequest)
      actorRef ! SearchByISBN(1935182757)
      expectMsgClass(classOf[Option[Nothing]])
    }
  }


}
