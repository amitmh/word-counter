package com.wordcounter.core

import java.util.concurrent.ConcurrentHashMap
import scala.collection.convert.ImplicitConversions.`map AsScala`

/** Allows storing words in concurrently */
class ConcurrentWordStore {
  private val state: ConcurrentHashMap[String, Int] = new ConcurrentHashMap[String, Int]()

  def add(word: String): Unit = {
    if (state.putIfAbsent(word, 1) != 0) {
      while (!incrementCount(word)) ()
    }
  }
  @inline private def incrementCount(word: String) = {
    val existingCount = state.get(word)
    state.replace(word, existingCount, existingCount + 1)
  }
  def hits(word: String): Int = state.getOrDefault(word, 0)

  def snapshot(): Map[String, Int] = state.toMap
}
