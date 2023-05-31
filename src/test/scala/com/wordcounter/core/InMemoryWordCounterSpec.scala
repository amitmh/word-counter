package com.wordcounter.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class InMemoryWordCounterSpec extends AnyFunSpec with Matchers {
  describe("In Memory Word Counter") {
    it("should add words") {
      val store = new ConcurrentWordStore
      val wc = InMemoryWordCounter(Translator.IdentityTranslator, store)
      val wordsToAdd = Seq("alpha", "beta", "others")
      wc.add(wordsToAdd: _*)
      val actualWords = store.snapshot().keySet
      actualWords.toList.sorted shouldBe actualWords.toList.sorted
    }
    it("should discard words with non-alphabetic chars") {
      val store = new ConcurrentWordStore
      val wc = InMemoryWordCounter(Translator.IdentityTranslator, store)
      val wordsToAdd = Seq("alpha1", "beta1", "others1")
      wc.add(wordsToAdd: _*)
      val actualWords = store.snapshot().keySet
      actualWords.toList shouldBe Nil
    }
    it("should return correct hits count") {
      val store = new ConcurrentWordStore
      val wc = InMemoryWordCounter(Translator.IdentityTranslator, store)
      val wordsToAdd = Seq("alpha", "beta", "others", "alpha")
      wc.add(wordsToAdd: _*)
      store.hits("alpha") shouldBe 2
    }
  }
}
