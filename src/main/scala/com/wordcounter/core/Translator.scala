package com.wordcounter.core

trait Translator {
  def translate(word: String): String
}
object Translator {
  object IdentityTranslator extends Translator {
    override def translate(word: String): String = word
  }
}