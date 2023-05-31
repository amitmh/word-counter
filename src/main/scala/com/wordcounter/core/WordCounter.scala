package com.wordcounter.core

/** Word Counter - provides interface to add words and how many times a word has been added */
trait WordCounter {

  /**
   * adds valid words
   *
   * @param words
   *   valid or invalid word sequence
   */
  def add(words: String*): Unit

  /**
   * finds how many times <code>word</code> added
   * @param word
   *   to be searched
   * @return
   *   how many the word been added
   */
  def hits(word: String): Int
}
