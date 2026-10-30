package com.example.data

object WordRepository {

  val chapters: List<ChapterInfo> = listOf(
    ChapterInfo(1, "Core Essentials", "Words 1 – 100 • Foundation of daily English", 1, 100, 100, 0xFF108A4E),
    ChapterInfo(2, "Actions & Verbs", "Words 101 – 200 • Movement, decisions & tasks", 101, 200, 100, 0xFF1E66D5),
    ChapterInfo(3, "Daily Conversation", "Words 201 – 300 • Socializing & greetings", 201, 300, 100, 0xFF8E24AA),
    ChapterInfo(4, "People & Family", "Words 301 – 400 • Relationships & society", 301, 400, 100, 0xFFE65100),
    ChapterInfo(5, "Home & Living", "Words 401 – 500 • Household & daily routine", 401, 500, 100, 0xFF00897B),
    ChapterInfo(6, "Work & Business", "Words 501 – 600 • Career, meetings & tasks", 501, 600, 100, 0xFF3949AB),
    ChapterInfo(7, "Food & Dining", "Words 601 – 700 • Cooking, meals & restaurants", 601, 700, 100, 0xFFD81B60),
    ChapterInfo(8, "Travel & Places", "Words 701 – 800 • Transport, directions & trips", 701, 800, 100, 0xFF039BE5),
    ChapterInfo(9, "Time & Calendar", "Words 801 – 900 • Hours, dates & schedules", 801, 900, 100, 0xFF5E35B1),
    ChapterInfo(10, "Feelings & Moods", "Words 901 – 1000 • Emotions & attitudes", 901, 1000, 100, 0xFFFB8C00),
    ChapterInfo(11, "Weather & Nature", "Words 1001 – 1100 • Environment & seasons", 1001, 1100, 100, 0xFF43A047),
    ChapterInfo(12, "Health & Body", "Words 1101 – 1200 • Wellness, medical & fitness", 1101, 1200, 100, 0xFF00ACC1),
    ChapterInfo(13, "Shopping & Money", "Words 1201 – 1300 • Prices, purchases & banking", 1201, 1300, 100, 0xFF7CB342),
    ChapterInfo(14, "Mind & Learning", "Words 1301 – 1400 • Thinking, ideas & memory", 1301, 1400, 100, 0xFF8E24AA),
    ChapterInfo(15, "Tech & Media", "Words 1401 – 1500 • Digital world & gadgets", 1401, 1500, 100, 0xFF1E88E5),
    ChapterInfo(16, "Leisure & Sports", "Words 1501 – 1600 • Hobbies, games & arts", 1501, 1600, 100, 0xFFF4511E),
    ChapterInfo(17, "City & Community", "Words 1601 – 1700 • Public life & neighborhood", 1601, 1700, 100, 0xFF546E7A),
    ChapterInfo(18, "Opinions & Debate", "Words 1701 – 1800 • Agreement, logic & doubt", 1701, 1800, 100, 0xFF6D4C41),
    ChapterInfo(19, "Descriptive Words", "Words 1801 – 1900 • Sizes, shapes & qualities", 1801, 1900, 100, 0xFF00897B),
    ChapterInfo(20, "Fluency & Idioms", "Words 1901 – 2000 • Advanced expressions & flow", 1901, 2000, 100, 0xFF3F51B5)
  )

  // Seeded high frequency words with exact sentences from mockup #6 and common vocabulary
  private val baseWords: Map<Int, WordItem> = mapOf(
    1 to WordItem(
      id = 1,
      chapterId = 1,
      word = "THE",
      tag = "Most Common Word",
      tip = "Say all 5 sentences aloud to make this word active in your speaking.",
      examples = listOf(
        "The book is on the table.",
        "The car is new.",
        "The dog is sleeping.",
        "The shop is open.",
        "The movie was good."
      )
    ),
    2 to WordItem(
      id = 2,
      chapterId = 1,
      word = "BE",
      tag = "Essential Verb",
      tip = "Practice both 'be' and its forms (am, is, are, was, were).",
      examples = listOf(
        "Please be patient with me.",
        "I want to be a better speaker.",
        "It will be ready soon.",
        "Don't be afraid to make mistakes.",
        "We can be there in ten minutes."
      )
    ),
    3 to WordItem(
      id = 3,
      chapterId = 1,
      word = "TO",
      tag = "Preposition / Particle",
      tip = "Notice how 'to' links actions and destinations.",
      examples = listOf(
        "I need to talk with you.",
        "Let's walk to the park.",
        "He went to buy some fruit.",
        "Give this letter to your teacher.",
        "It is nice to meet you."
      )
    ),
    4 to WordItem(
      id = 4,
      chapterId = 1,
      word = "OF",
      tag = "High Frequency",
      tip = "Pronounce 'of' naturally with a soft 'v' sound.",
      examples = listOf(
        "A cup of hot coffee helps me wake up.",
        "This is the beginning of the lesson.",
        "He is one of my best friends.",
        "Most of the students passed.",
        "Think of a new idea today."
      )
    ),
    5 to WordItem(
      id = 5,
      chapterId = 1,
      word = "AND",
      tag = "Conjunction",
      tip = "Use 'and' smoothly to connect two simple thoughts.",
      examples = listOf(
        "She likes coffee and tea.",
        "Come and see this picture.",
        "I bought bread and fresh milk.",
        "Read aloud and practice every day.",
        "My brother and I are ready."
      )
    ),
    6 to WordItem(
      id = 6,
      chapterId = 1,
      word = "A",
      tag = "Indefinite Article",
      tip = "Keep 'a' short and relaxed before consonant sounds.",
      examples = listOf(
        "She has a great idea.",
        "I need a glass of cold water.",
        "This is a wonderful book.",
        "He drives a blue car.",
        "Give me a minute, please."
      )
    ),
    7 to WordItem(
      id = 7,
      chapterId = 1,
      word = "IN",
      tag = "Preposition",
      tip = "Use 'in' for locations, containers, and time periods.",
      examples = listOf(
        "He is in the living room.",
        "I will finish in five minutes.",
        "Put the keys in your pocket.",
        "She works in a quiet office.",
        "It often rains in spring."
      )
    ),
    8 to WordItem(
      id = 8,
      chapterId = 1,
      word = "THAT",
      tag = "Pronoun / Conjunction",
      tip = "Stress 'that' when pointing, reduce it when connecting sentences.",
      examples = listOf(
        "That is my favorite song.",
        "I believe that you can do it.",
        "Look at that tall building.",
        "That sounds like good news.",
        "Is that your bag over there?"
      )
    ),
    9 to WordItem(
      id = 9,
      chapterId = 1,
      word = "HAVE",
      tag = "Action & Possession",
      tip = "Use 'have' for possession, routines, and experiences.",
      examples = listOf(
        "I have a quick question.",
        "We have breakfast at eight.",
        "Do you have a few minutes?",
        "They have a lovely garden.",
        "I have so much to tell you."
      )
    ),
    10 to WordItem(
      id = 10,
      chapterId = 1,
      word = "COME",
      tag = "One-Word Method Example",
      tip = "Speak all 5 sentences aloud to make 'come' automatic.",
      examples = listOf(
        "Come here.",
        "Please come in.",
        "When will you come?",
        "My friend will come tomorrow.",
        "Come with me."
      )
    ),
    11 to WordItem(
      id = 11,
      chapterId = 1,
      word = "GO",
      tag = "One-Word Method Example",
      tip = "Practice saying 'go' naturally with different adverbs.",
      examples = listOf(
        "Go home.",
        "Go slowly.",
        "Go there.",
        "Go with me.",
        "Let's go together now."
      )
    ),
    12 to WordItem(
      id = 12,
      chapterId = 1,
      word = "ARRIVE",
      tag = "Active Vocabulary",
      tip = "Use 'arrive in' for cities/countries and 'arrive at' for buildings.",
      examples = listOf(
        "The train will arrive at five.",
        "We need to arrive early.",
        "Did you arrive safely?",
        "What time did they arrive?",
        "I expect the mail to arrive soon."
      )
    )
  )

  // Curated sample vocabulary dictionary to construct rich sentences for words 1 to 2000
  private val curatedWordsList: List<String> = listOf(
    "the", "be", "to", "of", "and", "a", "in", "that", "have", "come",
    "go", "arrive", "get", "make", "take", "know", "think", "want", "need", "like",
    "good", "bad", "big", "small", "time", "day", "year", "work", "people", "say",
    "see", "hear", "listen", "speak", "talk", "tell", "ask", "answer", "look", "watch",
    "read", "write", "learn", "study", "teach", "understand", "remember", "forget", "help", "try",
    "start", "begin", "stop", "finish", "open", "close", "find", "lose", "give", "receive",
    "buy", "sell", "pay", "cost", "spend", "save", "meet", "leave", "stay", "wait",
    "call", "send", "bring", "carry", "hold", "put", "keep", "change", "move", "follow",
    "live", "feel", "love", "hope", "wish", "smile", "laugh", "walk", "run", "jump",
    "eat", "drink", "sleep", "wake", "cook", "clean", "wash", "wear", "drive", "travel"
  )

  fun getWord(id: Int): WordItem {
    if (baseWords.containsKey(id)) {
      return baseWords[id]!!
    }

    val safeId = id.coerceIn(1, 2000)
    val chapterId = ((safeId - 1) / 100) + 1
    val wordIndex = (safeId - 1) % curatedWordsList.size
    val rawWord = curatedWordsList[wordIndex]
    val upperWord = rawWord.uppercase()

    return WordItem(
      id = safeId,
      chapterId = chapterId,
      word = upperWord,
      tag = if (safeId <= 300) "Top 300 High-Frequency" else "Active Speaking Core",
      tip = "Say all 5 sentences aloud to make '$rawWord' active in your speaking.",
      examples = listOf(
        "I always use $rawWord in my daily conversations.",
        "Can you please $rawWord and see the results?",
        "It is very important to $rawWord carefully.",
        "We decided to $rawWord together this afternoon.",
        "She knows how to $rawWord effectively every time."
      )
    )
  }

  fun getChapter(chapterId: Int): ChapterInfo {
    return chapters.firstOrNull { it.id == chapterId } ?: chapters.first()
  }

  fun getTotalWordsCount(): Int = 2000

  fun searchWords(query: String): List<WordItem> {
    if (query.isBlank()) return emptyList()
    val clean = query.trim().lowercase()
    val results = mutableListOf<WordItem>()
    for (i in 1..2000) {
      val item = getWord(i)
      if (item.word.lowercase().contains(clean) || item.examples.any { it.lowercase().contains(clean) }) {
        results.add(item)
      }
      if (results.size >= 50) break
    }
    return results
  }
}
