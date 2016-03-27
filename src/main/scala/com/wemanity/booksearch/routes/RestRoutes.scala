package com.wemanity.booksearch.routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import com.wemanity.booksearch.BookJsonProtocol
import com.wemanity.booksearch.api.BookApi
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

/**
  * Routes definitions
  * Created by oleg on 19/12/15.
  */
trait RestRoutes extends BookApi with BookJsonProtocol{
  val routes = bookSearchRoutes

  def bookSearchRoutes =
    pathPrefix("book" / "isbn"/ LongNumber) { isbn =>
    pathEnd {
      get {
        // getting feature
       onSuccess(searchByISBN(isbn)) {
          // unwrapping optional
          _.fold(complete(NotFound))(bookInfo =>complete(bookInfo))
        }
      }
    }
  }
}