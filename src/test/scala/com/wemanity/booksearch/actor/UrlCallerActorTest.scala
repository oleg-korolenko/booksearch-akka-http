package com.wemanity.booksearch.actor

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{StatusCodes, HttpResponse}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.wemanity.booksearch.StopSystemAfterAll
import UrlCallerActor.{Get}
import org.scalatest.{MustMatchers, WordSpecLike}

/**
  * Created by oleg on 09/12/15.
  */
class UrlCallerActorTest extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with MustMatchers
  with ImplicitSender
  with StopSystemAfterAll {

  "A UrlCallerActor" must {
    "return results as HttpResponse" in {
      val actorRef = TestActorRef[UrlCallerActor]
      actorRef ! Get(s"https://www.googleapis.com/books/v1/volumes?q={tom%20sawyer}")
      //expectMsg(HttpResponse(StatusCodes.OK,_,_,_))
      expectMsgClass(classOf[HttpResponse])
    }
  }


}
