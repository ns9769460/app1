package com.example.ui.practice

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.WordItem
import com.example.data.WordRepository
import com.example.util.SpeechManager
import com.example.util.UserPreferences

@Composable
fun WordPracticeScreen(
  wordId: Int,
  speechManager: SpeechManager,
  userPreferences: UserPreferences,
  onNavigateBack: () -> Unit,
  onOpenChapters: () -> Unit,
  onWordChanged: (Int) -> Unit,
  onFinishCourse: () -> Unit
) {
  val context = LocalContext.current
  val wordItem = remember(wordId) { WordRepository.getWord(wordId) }
  var isBookmarked by remember(wordId) { mutableStateOf(userPreferences.isBookmarked(wordId)) }
  val scrollState = rememberScrollState()

  val totalWords = WordRepository.getTotalWordsCount()
  val progressPercent = wordId.toFloat() / totalWords.toFloat()

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color(0xFFF9FAFC)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
    ) {
      // Top Navigation Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Back Button
        IconButton(
          onClick = onNavigateBack,
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(Color.White)
            .testTag("practice_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color(0xFF1E293B)
          )
        }

        // Center: Word X of 2000 & mini progress bar
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.weight(1f)
        ) {
          val headerAnnotated = buildAnnotatedString {
            append("Word ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF1E66D5))) {
              append("$wordId")
            }
            append(" of $totalWords")
          }
          Text(
            text = headerAnnotated,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1E293B)
          )
          Spacer(modifier = Modifier.height(4.dp))
          LinearProgressIndicator(
            progress = { progressPercent },
            modifier = Modifier
              .width(100.dp)
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp)),
            color = Color(0xFF108A4E),
            trackColor = Color(0xFFE2E8F0),
          )
        }

        // Bookmark Button
        IconButton(
          onClick = {
            isBookmarked = userPreferences.toggleBookmark(wordId)
            val msg = if (isBookmarked) "Saved to Bookmarks" else "Removed from Bookmarks"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(Color.White)
            .testTag("practice_bookmark_button")
        ) {
          Icon(
            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = "Bookmark",
            tint = if (isBookmarked) Color(0xFF1E66D5) else Color(0xFF64748B)
          )
        }
      }

      // Scrollable Content
      Column(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
          .verticalScroll(scrollState)
          .padding(horizontal = 16.dp, vertical = 6.dp)
      ) {
        // Hero Word Card (Image 6)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable {
              speechManager.speak(wordItem.word)
            }
            .testTag("hero_word_card"),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF8ED)),
          border = BorderStroke(1.dp, Color(0xFFC8E6C9))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = wordItem.word,
              fontSize = 46.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF0F8A43),
              letterSpacing = 1.sp,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFBC02D),
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = wordItem.tag,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5 Numbered Examples (Image 6)
        wordItem.examples.forEachIndexed { index, sentence ->
          val exampleNum = index + 1
          ExampleSentenceCard(
            number = exampleNum,
            sentence = sentence,
            targetWord = wordItem.word,
            onSpeak = {
              speechManager.speak(sentence)
            },
            onCopy = {
              val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
              val clip = ClipData.newPlainText("English Sentence", sentence)
              clipboard.setPrimaryClip(clip)
              speechManager.speak(sentence)
              Toast.makeText(context, "Copied & Speaking aloud!", Toast.LENGTH_SHORT).show()
            }
          )
          Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Yellow Practice Tip Card (Image 6)
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
          border = BorderStroke(1.dp, Color(0xFFFFE082))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFA000)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Practice Tip",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF2E1C00)
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = wordItem.tip,
                fontSize = 13.sp,
                color = Color(0xFF4A3B18),
                lineHeight = 17.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
      }

      // Bottom Control Bar (Image 6)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Previous Button
        OutlinedButton(
          onClick = {
            if (wordId > 1) {
              userPreferences.currentWordId = wordId - 1
              onWordChanged(wordId - 1)
            }
          },
          enabled = wordId > 1,
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("practice_prev_button"),
          shape = RoundedCornerShape(14.dp),
          border = BorderStroke(1.dp, if (wordId > 1) Color(0xFFBBDEFB) else Color(0xFFE2E8F0)),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (wordId > 1) Color(0xFFEEF5FE) else Color(0xFFF8FAFC)
          )
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
              tint = if (wordId > 1) Color(0xFF1E66D5) else Color(0xFF94A3B8),
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Previous",
              color = if (wordId > 1) Color(0xFF1E66D5) else Color(0xFF94A3B8),
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Center Progress Button (Tappable to open 20 Chapters Navigator)
        Card(
          modifier = Modifier
            .weight(1.1f)
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onOpenChapters() }
            .testTag("practice_progress_button"),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
          Row(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.BarChart,
              contentDescription = null,
              tint = Color(0xFF1E66D5),
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(horizontalAlignment = Alignment.Start) {
              Text(
                text = "Progress",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF64748B)
              )
              Text(
                text = "$wordId / $totalWords",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Next Word Button (Solid green)
        Button(
          onClick = {
            userPreferences.markWordCompleted(wordId)
            if (wordId < totalWords) {
              userPreferences.currentWordId = wordId + 1
              onWordChanged(wordId + 1)
            } else {
              onFinishCourse()
            }
          },
          modifier = Modifier
            .weight(1.3f)
            .height(50.dp)
            .testTag("practice_next_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF108A4E))
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = if (wordId >= totalWords) "Finish 🎉" else "Next Word",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ExampleSentenceCard(
  number: Int,
  sentence: String,
  targetWord: String,
  onSpeak: () -> Unit,
  onCopy: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable { onSpeak() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Number Badge (1, 2, 3, 4, 5 in solid green circle)
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(Color(0xFF108A4E)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "$number",
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Center: Example N pill + sentence with highlighted word
      Column(modifier = Modifier.weight(1f)) {
        // Example N pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF108A4E))
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(
            text = "Example $number",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Sentence with target word highlighted in bold green
        val highlightedSentence = highlightWordInSentence(sentence, targetWord)
        Text(
          text = highlightedSentence,
          fontSize = 16.sp,
          color = Color(0xFF1E293B),
          lineHeight = 22.sp
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      // Copy / Speaker Icon Button (Image 6 shows document copy icon)
      IconButton(
        onClick = onCopy,
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(Color(0xFFF1F8F4))
      ) {
        Icon(
          imageVector = Icons.Default.ContentCopy,
          contentDescription = "Copy and Speak",
          tint = Color(0xFF108A4E),
          modifier = Modifier.size(19.dp)
        )
      }
    }
  }
}

private fun highlightWordInSentence(sentence: String, word: String) = buildAnnotatedString {
  val cleanWord = word.trim()
  val regex = "(?i)\\b${Regex.escape(cleanWord)}\\b".toRegex()
  var lastIndex = 0

  val matches = regex.findAll(sentence).toList()
  if (matches.isEmpty()) {
    // If exact regex match wasn't found (e.g. prefix or punct), check simple contains
    val lowerSentence = sentence.lowercase()
    val lowerWord = cleanWord.lowercase()
    val idx = lowerSentence.indexOf(lowerWord)
    if (idx >= 0) {
      append(sentence.substring(0, idx))
      withStyle(SpanStyle(color = Color(0xFF108A4E), fontWeight = FontWeight.ExtraBold)) {
        append(sentence.substring(idx, idx + cleanWord.length))
      }
      append(sentence.substring(idx + cleanWord.length))
      return@buildAnnotatedString
    } else {
      append(sentence)
      return@buildAnnotatedString
    }
  }

  for (match in matches) {
    if (match.range.first > lastIndex) {
      append(sentence.substring(lastIndex, match.range.first))
    }
    withStyle(SpanStyle(color = Color(0xFF108A4E), fontWeight = FontWeight.ExtraBold)) {
      append(sentence.substring(match.range))
    }
    lastIndex = match.range.last + 1
  }

  if (lastIndex < sentence.length) {
    append(sentence.substring(lastIndex))
  }
}
