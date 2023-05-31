package com.wordcounter.api

import io.undertow.Undertow
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
class AppSpec extends AnyFunSpec with Matchers {
  def withServer[T](f: String => T): T = {
    val server = Undertow.builder
      .addHttpListener(8081, "localhost")
      .setHandler(App.defaultHandler)
      .build
    server.start()
    val res =
      try f("http://localhost:8081")
      finally server.stop()
    res
  }
  describe("ApiApp") {
    it("should be able to add words") {
      withServer { host =>
        val res = requests.get(host)
        res.text() shouldBe "Word Counter Apis"

        val addRes = requests.post(s"$host/add", data = """ { "words": ["alpha", "beta" ] }""")
        addRes.statusCode shouldBe 200

        val hitsRes = requests.get(s"$host/hits/alpha")
        hitsRes.text() shouldBe "1"
      }
    }
  }
}
