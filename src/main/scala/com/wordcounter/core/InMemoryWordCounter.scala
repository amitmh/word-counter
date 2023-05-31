package com.wordcounter.core

/**
 * Provides implementation of [[WordCounter]] using [[ConcurrentWordStore]]
 * @param store
 *   [[ConcurrentWordStore]] instance
 */
class InMemoryWordCounter(store: ConcurrentWordStore, translator: Translator) extends WordCounter {
  private def isValidWord(word: String): Boolean = word != null && word.forall(_.isLetter)

  /** @inheritdoc */
  override def add(words: String*): Unit =
    words.filter(isValidWord).foreach(translator.translate _ andThen store.add)

  /** @inheritdoc */
  override def hits(word: String): Int = store.hits(word)
}
object InMemoryWordCounter {
  def apply(
    translator: Translator = Translator.IdentityTranslator,
    store: ConcurrentWordStore = new ConcurrentWordStore
  ): InMemoryWordCounter = new InMemoryWordCounter(store, translator)
}
