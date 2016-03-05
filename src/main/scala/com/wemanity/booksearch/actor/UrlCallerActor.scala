package com.wemanity.booksearch.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString
import com.wemanity.booksearch.actor.UrlCallerActor.Get

/**
  * Created by oleg on 06/12/15.
  */
object UrlCallerActor {

  def props() = Props(new UrlCallerActor())

  case class Get(url: String)

}

class UrlCallerActor extends Actor with ActorLogging {

  import akka.http.scaladsl.Http
  import akka.http.scaladsl.model._
  import akka.pattern.pipe
  import context.dispatcher

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
  val http = Http(context.system)

  override def receive: Receive = {
    case Get(url) => {
      log.info(s"https://www.googleapis.com/books/v1/volumes?q=$url")
      //piping back http response (and keeping sender)
      http.singleRequest(HttpRequest(uri = url)).to(self, sender)
    }
    case HttpResponse(StatusCodes.OK, headers, entity, p) => {
      log.info("Got response, body: " + entity.dataBytes.runFold(ByteString(""))(_ ++ _))
      sender ! HttpResponse(StatusCodes.OK, headers, entity, p)
    }

    case HttpResponse(code, _, _, _) =>
      log.error(s"Request failed, response code: $code")
  }

}