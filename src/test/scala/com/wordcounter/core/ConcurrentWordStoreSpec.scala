package com.wordcounter.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Logger
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.language.postfixOps

class ConcurrentWordStoreSpec extends AnyFunSpec with Matchers {
  private val logger = Logger.getLogger(this.getClass.getSimpleName)
  describe("store") {
    it("add words concurrently") {
      val word = "word"
      val count = 1000

      val store = new ConcurrentWordStore
      def addWords(count: Int)(context: Int): Unit = {
        logger.info(s"[$context] adding ...")
        List.fill(count)(word).foreach(store.add)
        logger.info(s"[$context done adding")
      }
      val i = new AtomicInteger(0)

      implicit val ec: ExecutionContextExecutor =
        ExecutionContext.fromExecutor(Executors.newFixedThreadPool(20))
      val futures = List.fill(count) {
        Future(addWords(count)(i.incrementAndGet()))(ec)
      }
      futures.foreach(f => Await.result(f, Duration.Inf))

      store.hits(word) shouldBe count * count
    }
  }
}
