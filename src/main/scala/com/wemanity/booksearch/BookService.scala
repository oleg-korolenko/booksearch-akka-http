package com.wemanity.booksearch

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.wemanity.booksearch.actor.BookSearchActor
import com.wemanity.booksearch.routes.RestRoutes

/**
  * Created by oleg on 14/01/16.
  */
object BookService extends App with Service with RestRoutes {
  override implicit val system = ActorSystem("book-search")
  override implicit val executor = system.dispatcher

  override implicit val materializer = ActorMaterializer()
  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  override val bookSearchActor: ActorRef = system.actorOf(BookSearchActor.props(config))

  val host = config.getString("http.ip")
  val port = config.getInt("http.port")

  val bindingFuture: scala.concurrent.Future[ServerBinding] = Http().bindAndHandle(routes, host, port)
  bindingFuture.onFailure {
    case ex: Exception =>
      logger.error(ex, "Failed to bind to {}:{}!", host, port)
  }


}

