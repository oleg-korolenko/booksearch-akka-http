package com.wemanity.booksearch

import spray.json.DefaultJsonProtocol

case class BookInfo(name: String, price: Double)

case class GoogleBooks(kind: String, totalItems: Int, items: Option[List[GoogleBook]]=None)

case class GoogleBook(id: String,volumeInfo:GoogleBookInfo)

case class GoogleBookInfo(title:String,authors:Array[String],publisher:String,description:String,averageRating:Double,ratingsCount:Int)

case class GoodReadsBookReviews(books: List[GoodReadsBookReview])

case class GoodReadsBookReview(id: Long, isbn: String, ratings_count: Int, average_rating: String)

case class BookInfos(googleBooks:Option[GoogleBooks]=None,reviews:Option[GoodReadsBookReviews]=None)

/**
  * Created by oleg on 28/02/16.
  */
trait BookJsonProtocol extends DefaultJsonProtocol {
  implicit val googleBookInfoJsonFormat = jsonFormat6(GoogleBookInfo)
  implicit val googleBookJsonFormat = jsonFormat2(GoogleBook)
  implicit val googleBooksJsonFormat = jsonFormat3(GoogleBooks)
  implicit val bookInfoProtocol = jsonFormat2(BookInfo)
  implicit val goodReadsBookReviewProtocol = jsonFormat4(GoodReadsBookReview)
  implicit val goodReadsBookReviewsProtocol = jsonFormat1(GoodReadsBookReviews)
  implicit val bookInfosProtocol = jsonFormat2(BookInfos)
}
