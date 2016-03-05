package com.wemanity.booksearch

import akka.testkit.TestKit
import org.scalatest.{Suite, BeforeAndAfterAll}

/**
  * Created by oleg on 09/12/15.
  */
trait StopSystemAfterAll extends BeforeAndAfterAll {
  this: TestKit with Suite =>
  override protected def afterAll() {
    super.afterAll()
    system.shutdown()
  }
}