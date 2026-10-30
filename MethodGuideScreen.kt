package com.example.ui.guide

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BrainHappyIllustration
import com.example.ui.components.BrainTiredIllustration
import com.example.ui.components.HeadSilhouetteGraphic
import com.example.util.SpeechManager

@Composable
fun MethodGuideScreen(
  speechManager: SpeechManager,
  initialSlide: Int = 1,
  onFinishGuide: () -> Unit,
  onClose: (() -> Unit)? = null
) {
  var currentSlide by remember { mutableIntStateOf(initialSlide.coerceIn(1, 5)) }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color(0xFFFCFCFE)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // Top Navigation bar: Close / Skip
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (onClose != null) {
          IconButton(
            onClick = onClose,
            modifier = Modifier.testTag("guide_close_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close Guide",
              tint = Color(0xFF455A64)
            )
          }
        } else {
          Spacer(modifier = Modifier.width(48.dp))
        }

        // Slide Badge
        val badgeColor = when (currentSlide) {
          1 -> Color(0xFF5E35B1)
          2 -> Color(0xFF108A4E)
          3 -> Color(0xFFE65100)
          4 -> Color(0xFF1E66D5)
          else -> Color(0xFF6748D8)
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(badgeColor)
            .padding(horizontal = 14.dp, vertical = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "$currentSlide/5",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        }

        TextButton(
          onClick = onFinishGuide,
          modifier = Modifier.testTag("guide_skip_button")
        ) {
          Text(
            text = "Skip",
            color = Color(0xFF757575),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
          )
        }
      }

      // Slide Content with Smooth Transition
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
      ) {
        AnimatedContent(
          targetState = currentSlide,
          transitionSpec = {
            if (targetState > initialState) {
              slideInHorizontally { width -> width } + fadeIn() togetherWith
                slideOutHorizontally { width -> -width } + fadeOut()
            } else {
              slideInHorizontally { width -> -width } + fadeIn() togetherWith
                slideOutHorizontally { width -> width } + fadeOut()
            }
          },
          label = "GuideSlideAnimation"
        ) { slide ->
          when (slide) {
            1 -> Slide1WhyYouCantUseWords()
            2 -> Slide2TheOneWordMethod(speechManager)
            3 -> Slide3DontLearnTooMuchAtOnce()
            4 -> Slide4DailySpeakingPlan()
            5 -> Slide5HowToMaster2000Words()
          }
        }
      }

      // Dots Indicator
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        val activeDotColor = when (currentSlide) {
          1 -> Color(0xFF5E35B1)
          2 -> Color(0xFF108A4E)
          3 -> Color(0xFFE65100)
          4 -> Color(0xFF1E66D5)
          else -> Color(0xFF6748D8)
        }

        for (i in 1..5) {
          val isActive = i == currentSlide
          Box(
            modifier = Modifier
              .padding(horizontal = 4.dp)
              .size(if (isActive) 10.dp else 8.dp)
              .clip(CircleShape)
              .background(if (isActive) activeDotColor else Color(0xFFE0E0E0))
          )
        }
      }

      // Next / Start Button
      val buttonColor = when (currentSlide) {
        1 -> Color(0xFF5E35B1)
        2 -> Color(0xFF108A4E)
        3 -> Color(0xFFE65100)
        4 -> Color(0xFF1E66D5)
        else -> Color(0xFF6748D8)
      }

      Button(
        onClick = {
          if (currentSlide < 5) {
            currentSlide++
          } else {
            onFinishGuide()
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("guide_next_button"),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Text(
            text = if (currentSlide == 5) "Start Learning 🚀" else "Next",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          if (currentSlide < 5) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              imageVector = Icons.Default.ArrowForward,
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

// ---------------- Slide 1: Why You Can't Use the Words You Know (Image 1) ----------------
@Composable
fun Slide1WhyYouCantUseWords() {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Why You Can't\nUse the Words\nYou Know",
      fontSize = 24.sp,
      lineHeight = 30.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF341D6B),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))
    BrainHappyIllustration(modifier = Modifier.size(140.dp))

    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "You already know many\nEnglish words.",
      fontSize = 16.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF263238),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "The problem is not memory.",
      fontSize = 15.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF263238),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))
    val problemUsageText = buildAnnotatedString {
      append("The problem is\n")
      withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF5E35B1), fontSize = 17.sp)) {
        append("active usage.")
      }
    }
    Text(
      text = problemUsageText,
      fontSize = 16.sp,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(18.dp))
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EBFD))
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "You know:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFF1E1E24)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(text = "• come", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF212121))
          Text(text = "• go", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF212121))
          Text(text = "• arrive", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF212121))
        }
        HeadSilhouetteGraphic(modifier = Modifier.size(60.dp))
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "But while speaking, your\nbrain chooses other\nfamiliar words.",
      fontSize = 15.sp,
      fontWeight = FontWeight.Normal,
      color = Color(0xFF37474F),
      textAlign = TextAlign.Center,
      lineHeight = 22.sp
    )

    Spacer(modifier = Modifier.height(10.dp))
    Text(
      text = "A word becomes active only after you use it many times in real situations.",
      fontSize = 14.sp,
      fontWeight = FontWeight.Normal,
      color = Color(0xFF455A64),
      textAlign = TextAlign.Center,
      lineHeight = 20.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
  }
}

// ---------------- Slide 2: The One-Word Method (Image 2) ----------------
@Composable
fun Slide2TheOneWordMethod(speechManager: SpeechManager) {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "The One-Word\nMethod",
      fontSize = 24.sp,
      lineHeight = 30.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF0B5C35),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = "Pick one word for the\nwhole day.",
      fontSize = 15.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF2E3A33),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(12.dp))
    // Pill: Example: come
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(Color(0xFFC8E6C9))
        .padding(horizontal = 18.dp, vertical = 6.dp)
    ) {
      Text(
        text = "Example: come",
        color = Color(0xFF1B5E20),
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
      )
    }

    Spacer(modifier = Modifier.height(10.dp))
    // Green Card: Sentences with 'come'
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
      border = BorderStroke(1.dp, Color(0xFFA5D6A7))
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        val comeSentences = listOf(
          "Come here.",
          "Please come in.",
          "When will you come?",
          "My friend will come tomorrow.",
          "Come with me."
        )
        comeSentences.forEach { sentence ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .clickable { speechManager.speak(sentence) }
              .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Speak",
              tint = Color(0xFF2E7D32),
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "• $sentence",
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF1B5E20)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.Default.Mic,
        contentDescription = null,
        tint = Color(0xFF2E7D32),
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "Say them aloud.",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2E7D32)
      )
    }

    Spacer(modifier = Modifier.height(10.dp))
    Text(
      text = "Then practice with:",
      fontSize = 14.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF37474F)
    )

    Spacer(modifier = Modifier.height(6.dp))
    // Blue Pill: go
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xFFBBDEFB))
        .padding(horizontal = 24.dp, vertical = 4.dp)
    ) {
      Text(
        text = "go",
        color = Color(0xFF0D47A1),
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
      )
    }

    Spacer(modifier = Modifier.height(8.dp))
    // Blue Card: Sentences with 'go'
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
      border = BorderStroke(1.dp, Color(0xFF90CAF9))
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        val goSentences = listOf(
          "Go home.",
          "Go slowly.",
          "Go there.",
          "Go with me."
        )
        goSentences.forEach { sentence ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .clickable { speechManager.speak(sentence) }
              .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Speak",
              tint = Color(0xFF1565C0),
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "• $sentence",
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF0D47A1)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))
    // Gold Star Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
      border = BorderStroke(1.dp, Color(0xFFFFE082))
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.Top
      ) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null,
          tint = Color(0xFFF57F17),
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "The goal is not to memorize the word. The goal is to make your mouth automatically choose it.",
          fontSize = 13.sp,
          color = Color(0xFF4E342E),
          lineHeight = 18.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
    Spacer(modifier = Modifier.height(12.dp))
  }
}

// ---------------- Slide 3: Don't Learn Too Much at Once (Image 3) ----------------
@Composable
fun Slide3DontLearnTooMuchAtOnce() {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Don't Learn Too\nMuch at Once",
      fontSize = 24.sp,
      lineHeight = 30.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFFBF360C),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(20.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = "❌",
        fontSize = 18.sp
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "100 words × 10 sentences",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF212121)
      )
    }

    Spacer(modifier = Modifier.height(14.dp))
    // Coral equation box
    Card(
      modifier = Modifier.fillMaxWidth(0.9f),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2)),
      border = BorderStroke(1.dp, Color(0xFFEF9A9A))
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "= 1,000 sentences",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFFB71C1C)
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
    BrainTiredIllustration(modifier = Modifier.size(140.dp))

    Spacer(modifier = Modifier.height(20.dp))
    Text(
      text = "Most people cannot make\n100 new words active in\none day.",
      fontSize = 16.sp,
      fontWeight = FontWeight.SemiBold,
      color = Color(0xFF212121),
      textAlign = TextAlign.Center,
      lineHeight = 24.sp
    )

    Spacer(modifier = Modifier.height(20.dp))
    // Tip card: Active speaking needs repetition
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
      border = BorderStroke(1.dp, Color(0xFFFFE082))
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Lightbulb,
          contentDescription = null,
          tint = Color(0xFFF57F17),
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Active speaking needs repetition.",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF3E2723)
        )
      }
    }
    Spacer(modifier = Modifier.height(12.dp))
  }
}

// ---------------- Slide 4: Daily Speaking Plan (Image 4) ----------------
@Composable
fun Slide4DailySpeakingPlan() {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Daily\nSpeaking Plan",
      fontSize = 26.sp,
      lineHeight = 32.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF0D47A1),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))
    // Plan stack card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4FF)),
      border = BorderStroke(1.dp, Color(0xFFBBDEFB))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        PlanItemRow(icon = Icons.Default.Book, text = "20 new words")
        Spacer(modifier = Modifier.height(12.dp))
        PlanItemRow(icon = Icons.Default.Create, text = "5 sentences per word")
        Spacer(modifier = Modifier.height(12.dp))
        PlanItemRow(icon = Icons.Default.VolumeUp, text = "Speak aloud")
      }
    }

    Spacer(modifier = Modifier.height(12.dp))
    // Equals 100 spoken sentences total
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
      border = BorderStroke(1.dp, Color(0xFF90CAF9))
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "= 100 spoken sentences total",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF0D47A1)
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "Learn:",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF212121)
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(verticalAlignment = Alignment.Top) {
        Text(text = "🟢", fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "100 words per day\nif you only want recognition.",
          fontSize = 14.sp,
          color = Color(0xFF263238),
          lineHeight = 20.sp,
          fontWeight = FontWeight.Medium
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Row(verticalAlignment = Alignment.Top) {
        Text(text = "🔵", fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "20–30 words per day\nif you want to actually\nuse them in conversation.",
          fontSize = 14.sp,
          color = Color(0xFF263238),
          lineHeight = 20.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))
    // Purple Star Note
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
      border = BorderStroke(1.dp, Color(0xFFCE93D8))
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.Top
      ) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null,
          tint = Color(0xFF8E24AA),
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Most fluent speakers actively use a few thousand words, but they built them gradually through repeated use, not by activating 100 new words every day.",
          fontSize = 13.sp,
          color = Color(0xFF4A148C),
          lineHeight = 18.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
    Spacer(modifier = Modifier.height(12.dp))
  }
}

@Composable
private fun PlanItemRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Box(
      modifier = Modifier
        .size(36.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Color(0xFFBBDEFB)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color(0xFF0D47A1),
        modifier = Modifier.size(20.dp)
      )
    }
    Spacer(modifier = Modifier.width(14.dp))
    Text(
      text = text,
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF1E293B)
    )
  }
}

// ---------------- Slide 5: How to Master 2,000 Common Words (Image 5) ----------------
@Composable
fun Slide5HowToMaster2000Words() {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "How to Master\n2,000 Common Words",
      fontSize = 24.sp,
      lineHeight = 30.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF341D6B),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Stepper Timeline
    StageItem(
      number = "1",
      circleColor = Color(0xFF2E7D32),
      title = "Stage 1",
      subtitle = "Master the top 300 words",
      description = "Examples: come, go, get, make, take, know, think, want, need, like, good, bad, big, small, time, day, year, work, people.\nThese 300 words appear constantly in everyday English."
    )

    StageItem(
      number = "2",
      circleColor = Color(0xFF1976D2),
      title = "Stage 2",
      subtitle = "Use them in speaking",
      description = "Every day:\n• Pick 20 words.\n• Make 5 spoken sentences for each.\n• Use them while talking to yourself for 5–10 minutes."
    )

    StageItem(
      number = "3",
      circleColor = Color(0xFFE65100),
      title = "Stage 4", // Exact matching user screenshot
      subtitle = "Read simple English daily",
      description = "When you repeatedly encounter a word in real use, it becomes automatic."
    )

    StageItem(
      number = "4",
      circleColor = Color(0xFF5E35B1),
      title = "Stage 5",
      subtitle = "Don't chase 2,000 at once",
      description = "• A rough rule:\n20 new active words/day = excellent pace.\n• In 100 days, that's about 2,000 words exposed to and practiced.",
      isLast = true
    )

    Spacer(modifier = Modifier.height(14.dp))
    // Red Warning Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
      border = BorderStroke(1.dp, Color(0xFFEF9A9A))
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.Top
      ) {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          tint = Color(0xFFD32F2F),
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "The biggest mistake is:",
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC62828),
            fontSize = 14.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "• Learning 2,000 meanings.\n• Never speaking or writing with them.",
            fontSize = 13.sp,
            color = Color(0xFF263238),
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Active vocabulary grows from retrieval and usage, not just memorization.",
            fontSize = 12.sp,
            color = Color(0xFF37474F),
            fontWeight = FontWeight.Medium,
            lineHeight = 17.sp
          )
        }
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
  }
}

@Composable
private fun StageItem(
  number: String,
  circleColor: Color,
  title: String,
  subtitle: String,
  description: String,
  isLast: Boolean = false
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.width(36.dp)
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(circleColor),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = number,
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      }
      if (!isLast) {
        Box(
          modifier = Modifier
            .width(2.dp)
            .height(70.dp)
            .background(Color(0xFFCFD8DC))
        )
      }
    }

    Spacer(modifier = Modifier.width(12.dp))
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = if (isLast) 4.dp else 14.dp)
    ) {
      Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = circleColor
      )
      Text(
        text = subtitle,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1E293B)
      )
      Spacer(modifier = Modifier.height(3.dp))
      Text(
        text = description,
        fontSize = 13.sp,
        color = Color(0xFF475569),
        lineHeight = 18.sp
      )
    }
  }
}
