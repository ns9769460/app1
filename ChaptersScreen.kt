package com.example.ui.chapters

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChapterInfo
import com.example.data.WordItem
import com.example.data.WordRepository
import com.example.util.UserPreferences

@Composable
fun ChaptersScreen(
  userPreferences: UserPreferences,
  onSelectWord: (Int) -> Unit,
  onBackToPractice: () -> Unit,
  onOpenGuide: () -> Unit,
  onOpenCompleted: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedTab by remember { mutableIntStateOf(0) } // 0: All Chapters, 1: Bookmarks
  var selectedChapterId by remember { mutableStateOf<Int?>(null) }
  val focusManager = LocalFocusManager.current

  val completedWords = remember { userPreferences.getCompletedWords() }
  val bookmarks = remember { userPreferences.getBookmarks() }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color(0xFFF8FAFC)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
    ) {
      // Top Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackToPractice,
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .testTag("chapters_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back to Practice",
            tint = Color(0xFF1E293B)
          )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "Course Progress",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
          )
          Text(
            text = "20 Chapters • 2,000 Words",
            fontSize = 12.sp,
            color = Color(0xFF64748B)
          )
        }

        // Trophy button to view Course Completed screen
        IconButton(
          onClick = onOpenCompleted,
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFFFEF3C7))
            .testTag("chapters_trophy_button")
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Completed Summary",
            tint = Color(0xFFD97706)
          )
        }
      }

      // Search Field
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp)
          .testTag("search_word_field"),
        placeholder = { Text("Search 2,000 words or jump to #...", fontSize = 14.sp) },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color(0xFF64748B)
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
            }
          }
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = Color.White,
          unfocusedContainerColor = Color.White,
          focusedBorderColor = Color(0xFF108A4E),
          unfocusedBorderColor = Color(0xFFE2E8F0)
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
      )

      // Filter Chips Row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterChip(
          selected = selectedTab == 0 && selectedChapterId == null,
          onClick = {
            selectedTab = 0
            selectedChapterId = null
          },
          label = { Text("All 20 Chapters") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF108A4E),
            selectedLabelColor = Color.White
          )
        )

        FilterChip(
          selected = selectedTab == 1,
          onClick = {
            selectedTab = 1
            selectedChapterId = null
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Bookmark,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
          },
          label = { Text("Bookmarks (${bookmarks.size})") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF1E66D5),
            selectedLabelColor = Color.White
          )
        )

        FilterChip(
          selected = false,
          onClick = onOpenGuide,
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.MenuBook,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
          },
          label = { Text("Method Guide") }
        )
      }

      // Content List
      LazyColumn(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        if (searchQuery.isNotBlank()) {
          // If searching for a number, offer direct jump
          val searchNum = searchQuery.trim().toIntOrNull()
          if (searchNum != null && searchNum in 1..2000) {
            item {
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(14.dp))
                  .clickable { onSelectWord(searchNum) },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                border = BorderStroke(1.dp, Color(0xFF81C784))
              ) {
                Row(
                  modifier = Modifier.padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "🎯 Jump directly to Word #$searchNum",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                  )
                }
              }
            }
          }

          val searchResults = WordRepository.searchWords(searchQuery)
          if (searchResults.isEmpty()) {
            item {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "No words matching \"$searchQuery\"",
                  color = Color(0xFF94A3B8),
                  fontSize = 15.sp
                )
              }
            }
          } else {
            items(searchResults) { word ->
              WordRowItem(
                word = word,
                isCompleted = completedWords.contains(word.id),
                isBookmarked = bookmarks.contains(word.id),
                onClick = { onSelectWord(word.id) }
              )
            }
          }
        } else if (selectedTab == 1) {
          // Bookmarks List
          if (bookmarks.isEmpty()) {
            item {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = null,
                    tint = Color(0xFFCBD5E1),
                    modifier = Modifier.size(48.dp)
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = "No bookmarked words yet",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    fontSize = 16.sp
                  )
                  Text(
                    text = "Tap the bookmark icon on any word to save it here.",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp
                  )
                }
              }
            }
          } else {
            items(bookmarks.toList().sorted()) { wordId ->
              val word = WordRepository.getWord(wordId)
              WordRowItem(
                word = word,
                isCompleted = completedWords.contains(word.id),
                isBookmarked = true,
                onClick = { onSelectWord(word.id) }
              )
            }
          }
        } else if (selectedChapterId != null) {
          // Words inside Selected Chapter
          val chapter = WordRepository.getChapter(selectedChapterId!!)
          item {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              TextButton(onClick = { selectedChapterId = null }) {
                Text("← Back to Chapters", fontWeight = FontWeight.Bold, color = Color(0xFF108A4E))
              }
              Spacer(modifier = Modifier.weight(1f))
              Text(
                text = "Chapter ${chapter.id}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
              )
            }
          }

          items((chapter.startWordId..chapter.endWordId).toList()) { wordId ->
            val word = WordRepository.getWord(wordId)
            WordRowItem(
              word = word,
              isCompleted = completedWords.contains(word.id),
              isBookmarked = bookmarks.contains(word.id),
              onClick = { onSelectWord(word.id) }
            )
          }
        } else {
          // 20 Chapters List (All 20 Chapters!)
          items(WordRepository.chapters) { chapter ->
            ChapterCardItem(
              chapter = chapter,
              completedCount = (chapter.startWordId..chapter.endWordId).count { completedWords.contains(it) },
              onChapterClick = {
                selectedChapterId = chapter.id
              },
              onStartChapter = {
                onSelectWord(chapter.startWordId)
              }
            )
          }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
      }
    }
  }
}

@Composable
private fun ChapterCardItem(
  chapter: ChapterInfo,
  completedCount: Int,
  onChapterClick: () -> Unit,
  onStartChapter: () -> Unit
) {
  val progress = completedCount.toFloat() / chapter.wordsCount.toFloat()
  val accentColor = Color(chapter.accentColorHex)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable { onChapterClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(accentColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${chapter.id}",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = accentColor
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Chapter ${chapter.id}: ${chapter.title}",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = Color(0xFF0F172A)
            )
            Text(
              text = chapter.subtitle,
              fontSize = 12.sp,
              color = Color(0xFF64748B)
            )
          }
        }

        IconButton(onClick = onStartChapter) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Start Chapter",
            tint = accentColor
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .weight(1f)
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = accentColor,
          trackColor = Color(0xFFF1F5F9),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
          text = "$completedCount / ${chapter.wordsCount}",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF475569)
        )
      }
    }
  }
}

@Composable
private fun WordRowItem(
  word: WordItem,
  isCompleted: Boolean,
  isBookmarked: Boolean,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() },
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(Color(0xFFF1F5F9)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "${word.id}",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF475569)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = word.word,
          fontSize = 16.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF108A4E)
        )
        Text(
          text = word.examples.firstOrNull() ?: "",
          fontSize = 12.sp,
          color = Color(0xFF64748B),
          maxLines = 1
        )
      }

      if (isBookmarked) {
        Icon(
          imageVector = Icons.Default.Bookmark,
          contentDescription = "Bookmarked",
          tint = Color(0xFF1E66D5),
          modifier = Modifier
            .size(18.dp)
            .padding(end = 4.dp)
        )
      }

      if (isCompleted) {
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = "Completed",
          tint = Color(0xFF108A4E),
          modifier = Modifier.size(18.dp)
        )
      } else {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = null,
          tint = Color(0xFFCBD5E1),
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}
