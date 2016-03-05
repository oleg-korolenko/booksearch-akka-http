package com.wemanity.booksearch

import com.wemanity.booksearch.actor.BookSearchActor.BookInfo
import spray.json.DefaultJsonProtocol

/**
  * Created by oleg on 28/02/16.
  */
object BookServiceJsonProtocol extends DefaultJsonProtocol {

  implicit val bookInfoProtocol = jsonFormat2(BookInfo)
}
