package com.wemanity.booksearch

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor
import com.typesafe.config.Config
/**
  * Service definition to be mixed in
  * Created by oleg on 14/01/16.
  */
trait Service {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer
  def config: Config
  val logger: LoggingAdapter

}
