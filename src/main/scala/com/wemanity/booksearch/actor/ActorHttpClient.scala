package com.wemanity.booksearch.actor

import akka.actor.Actor
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.Future

/**
  * Trait mo mix with actors, provides some basic Http operations
  * Created by oleg on 09/03/16.
  */
trait ActorHttpClient {

  this: Actor =>

  implicit val materializer:ActorMaterializer

  val http = Http(context.system)

  def get(url: String):Future[HttpResponse] = {
    http.singleRequest(HttpRequest(uri = url))
  }
}
