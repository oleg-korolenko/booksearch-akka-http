package com.wemanity.booksearch.routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import com.wemanity.booksearch.api.BookApi
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.wemanity.booksearch.BookServiceJsonProtocol.bookInfoProtocol

/**
  * Routes definitions
  * Created by oleg on 19/12/15.
  */
trait RestRoutes extends BookApi {
  val routes = bookSearchRoutes

  def bookSearchRoutes =
    pathPrefix("book" / "name" / Rest) { bookName =>
      pathEnd {
        get {
          // getting feature
          onSuccess(search(bookName)) {
            // unwrapping optional
            _.fold(complete(NotFound))(bookInfo => complete(bookInfo))
          }
        }
      }
    }
}