package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.chapters.ChaptersScreen
import com.example.ui.completed.CourseCompletedScreen
import com.example.ui.guide.MethodGuideScreen
import com.example.ui.practice.WordPracticeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.util.SpeechManager
import com.example.util.UserPreferences

enum class AppScreen {
  GUIDE,
  PRACTICE,
  CHAPTERS,
  COMPLETED
}

class MainActivity : ComponentActivity() {
  private lateinit var speechManager: SpeechManager
  private lateinit var userPreferences: UserPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    speechManager = SpeechManager(this)
    userPreferences = UserPreferences(this)

    setContent {
      MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
          EnglishSpeakingApp(
            speechManager = speechManager,
            userPreferences = userPreferences
          )
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    speechManager.shutdown()
  }
}

@Composable
fun EnglishSpeakingApp(
  speechManager: SpeechManager,
  userPreferences: UserPreferences
) {
  var currentScreen by remember {
    mutableStateOf(
      if (userPreferences.hasSeenGuide) AppScreen.PRACTICE else AppScreen.GUIDE
    )
  }

  var currentWordId by remember {
    mutableIntStateOf(userPreferences.currentWordId)
  }

  // Handle system back button gracefully
  BackHandler(enabled = currentScreen != AppScreen.PRACTICE) {
    when (currentScreen) {
      AppScreen.GUIDE -> currentScreen = AppScreen.PRACTICE
      AppScreen.CHAPTERS -> currentScreen = AppScreen.PRACTICE
      AppScreen.COMPLETED -> currentScreen = AppScreen.PRACTICE
      AppScreen.PRACTICE -> { /* Handled by OS */ }
    }
  }

  Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
    when (screen) {
      AppScreen.GUIDE -> {
        MethodGuideScreen(
          speechManager = speechManager,
          initialSlide = 1,
          onFinishGuide = {
            userPreferences.hasSeenGuide = true
            currentScreen = AppScreen.PRACTICE
          },
          onClose = if (userPreferences.hasSeenGuide) {
            { currentScreen = AppScreen.PRACTICE }
          } else null
        )
      }

      AppScreen.PRACTICE -> {
        WordPracticeScreen(
          wordId = currentWordId,
          speechManager = speechManager,
          userPreferences = userPreferences,
          onNavigateBack = {
            currentScreen = AppScreen.CHAPTERS
          },
          onOpenChapters = {
            currentScreen = AppScreen.CHAPTERS
          },
          onWordChanged = { newWordId ->
            currentWordId = newWordId
          },
          onFinishCourse = {
            currentScreen = AppScreen.COMPLETED
          }
        )
      }

      AppScreen.CHAPTERS -> {
        ChaptersScreen(
          userPreferences = userPreferences,
          onSelectWord = { selectedWordId ->
            currentWordId = selectedWordId
            userPreferences.currentWordId = selectedWordId
            currentScreen = AppScreen.PRACTICE
          },
          onBackToPractice = {
            currentScreen = AppScreen.PRACTICE
          },
          onOpenGuide = {
            currentScreen = AppScreen.GUIDE
          },
          onOpenCompleted = {
            currentScreen = AppScreen.COMPLETED
          }
        )
      }

      AppScreen.COMPLETED -> {
        CourseCompletedScreen(
          userPreferences = userPreferences,
          onStartAgain = {
            currentWordId = 1
            userPreferences.currentWordId = 1
            currentScreen = AppScreen.PRACTICE
          },
          onViewProgress = {
            currentScreen = AppScreen.CHAPTERS
          },
          onGoHome = {
            currentScreen = AppScreen.PRACTICE
          }
        )
      }
    }
  }
}
