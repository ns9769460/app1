package com.example.data

data class WordItem(
  val id: Int,
  val chapterId: Int,
  val word: String,
  val tag: String = "Common Word",
  val tip: String = "Say all 5 sentences aloud to make this word active in your speaking.",
  val examples: List<String>
)

data class ChapterInfo(
  val id: Int,
  val title: String,
  val subtitle: String,
  val startWordId: Int,
  val endWordId: Int,
  val wordsCount: Int = 100,
  val accentColorHex: Long = 0xFF108A4E
)
