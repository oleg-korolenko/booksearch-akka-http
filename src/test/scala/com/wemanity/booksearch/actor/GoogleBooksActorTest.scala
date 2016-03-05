package com.wemanity.booksearch.actor

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{StatusCodes, HttpResponse}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.wemanity.booksearch.StopSystemAfterAll
import com.wemanity.booksearch.actor.BookSearchActor.SearchByName
import com.wemanity.booksearch.actor.UrlCallerActor.Get
import org.scalatest.{MustMatchers, WordSpecLike}

/**
  * Created by oleg on 09/12/15.
  */
class GoogleBooksActorTest extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with MustMatchers
  with ImplicitSender
  with StopSystemAfterAll {

  "A GoogleBooksActor" must {
    "return results from url call" in {
      val actorRef = TestActorRef[GoogleBooksActor]
      ???
    }
  }


}
