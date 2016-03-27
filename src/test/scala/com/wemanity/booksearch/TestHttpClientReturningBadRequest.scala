package com.wemanity.booksearch

import akka.actor.Actor
import akka.http.scaladsl.model.{StatusCodes, HttpResponse}
import com.wemanity.booksearch.actor.ActorHttpClient

import scala.concurrent.Future

/**
  * Created by oleg on 26/03/16.
  */
trait TestHttpClientReturningBadRequest extends ActorHttpClient{
  this: Actor =>
  implicit def executionContext = context.dispatcher

  override def get(url: String):Future[HttpResponse] = {
    Future(HttpResponse(StatusCodes.BadRequest))
  }

}
