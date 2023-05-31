package com.wordcounter.api

import cask._
import com.wordcounter.core.{InMemoryWordCounter, Translator}

object App extends MainRoutes {
  // TODO: replace with lib call for translator
  private val wordCounter = InMemoryWordCounter(Translator.IdentityTranslator)

  @get("/")
  def hello() = "Word Counter Apis"

  @get("/hits/:word")
  def hits(word: String): Int = wordCounter.hits(word)

  @postJson("/add")
  def addWords(words: Seq[String]): Unit = wordCounter.add(words: _*)

  initialize()

}
