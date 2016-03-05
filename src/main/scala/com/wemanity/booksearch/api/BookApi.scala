package com.wemanity.booksearch.api



import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

import com.wemanity.booksearch.actor.BookSearchActor.{BookInfo, SearchByName}

/**
  * Contains book api methods
  * Book search actor creation should be provided by mixin class
  *
  * Created by oleg on 16/01/16.
  */
trait BookApi {

  implicit def executor: ExecutionContext
  implicit val requestTimeout= Timeout(5 seconds)

  def createBookSearchActor(): ActorRef

  def search(bookName: String) = {
    val bookSearcher = createBookSearchActor()
    // mapping to specific type as message is not typed
    bookSearcher.ask(SearchByName(bookName)).mapTo[Option[BookInfo]]

  }
}
