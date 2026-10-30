package com.example.util

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
  private val prefs: SharedPreferences =
    context.getSharedPreferences("english_speaking_prefs", Context.MODE_PRIVATE)

  var currentWordId: Int
    get() = prefs.getInt(KEY_CURRENT_WORD, 1)
    set(value) = prefs.edit().putInt(KEY_CURRENT_WORD, value.coerceIn(1, 2000)).apply()

  var hasSeenGuide: Boolean
    get() = prefs.getBoolean(KEY_HAS_SEEN_GUIDE, false)
    set(value) = prefs.edit().putBoolean(KEY_HAS_SEEN_GUIDE, value).apply()

  fun getBookmarks(): Set<Int> {
    val raw = prefs.getStringSet(KEY_BOOKMARKS, emptySet()) ?: emptySet()
    return raw.mapNotNull { it.toIntOrNull() }.toSet()
  }

  fun toggleBookmark(wordId: Int): Boolean {
    val current = getBookmarks().toMutableSet()
    val isNowBookmarked = if (current.contains(wordId)) {
      current.remove(wordId)
      false
    } else {
      current.add(wordId)
      true
    }
    prefs.edit().putStringSet(KEY_BOOKMARKS, current.map { it.toString() }.toSet()).apply()
    return isNowBookmarked
  }

  fun isBookmarked(wordId: Int): Boolean {
    return getBookmarks().contains(wordId)
  }

  fun getCompletedWords(): Set<Int> {
    val raw = prefs.getStringSet(KEY_COMPLETED, emptySet()) ?: emptySet()
    return raw.mapNotNull { it.toIntOrNull() }.toSet()
  }

  fun markWordCompleted(wordId: Int) {
    val current = getCompletedWords().toMutableSet()
    current.add(wordId)
    prefs.edit().putStringSet(KEY_COMPLETED, current.map { it.toString() }.toSet()).apply()
  }

  fun resetAllProgress() {
    prefs.edit()
      .putInt(KEY_CURRENT_WORD, 1)
      .putStringSet(KEY_COMPLETED, emptySet())
      .putStringSet(KEY_BOOKMARKS, emptySet())
      .apply()
  }

  companion object {
    private const val KEY_CURRENT_WORD = "current_word"
    private const val KEY_HAS_SEEN_GUIDE = "has_seen_guide"
    private const val KEY_BOOKMARKS = "bookmarks"
    private const val KEY_COMPLETED = "completed_words"
  }
}
