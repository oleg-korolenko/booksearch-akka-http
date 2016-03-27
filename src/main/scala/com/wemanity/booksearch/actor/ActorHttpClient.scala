package com.wemanity.booksearch.actor

import akka.actor.Actor
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.Future

/**
  * Created by oleg on 09/03/16.
  */
trait ActorHttpClient {

  this: Actor =>

  implicit val materializer:ActorMaterializer

  val http = Http(context.system)

  def get(url: String):Future[HttpResponse] = {
    http.singleRequest(HttpRequest(uri = url))
    /*
    Http().singleRequest(RequestBuilding.Get(uri)).map { response =>
      response.status match {
        case OK       => Right(response.entity.dataBytes.via(csv.parse())
        case NotFound => Left(NotFound -> s"No data found for $symbol")
        case status   => Left(status -> s"Request to $uri failed with status $status")
      }
    }*/
  }
}
