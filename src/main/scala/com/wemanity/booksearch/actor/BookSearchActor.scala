package com.wemanity.booksearch.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.wemanity.booksearch.actor.BookSearchActor.{BookInfo, SearchByName}
;

/**
  * Created by oleg on 06/12/15.
  */
object BookSearchActor {
  def props() = Props(new BookSearchActor())

  case class SearchByName(name: String)
  case class SearchByISBN(isbn: Integer)

  case class BookInfo(name: String, price: Double)

}

class BookSearchActor() extends Actor with ActorLogging {

  implicit def executionContext = context.dispatcher

  override def receive: Receive = {

    case SearchByName(name) => {
      // Arriving here through "ask"
      //TODO call different search actors
      sender ! Some(BookInfo(name, 10.00))
      /*
      val urlCaller = context.actorOf(UrlCallerActor.props())
      // all children actors will be stopped
      self ! PoisonPill*/
    }
    //case Response(response)=>log.info(s"Received :$response")
    case HttpResponse(StatusCodes.OK, headers, entity, _) => {
      println("Got it ")
    }
  }

}
