package com.example.ui.completed

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ConfettiCelebrationGraphic
import com.example.ui.components.GraduationBooksIllustration
import com.example.util.UserPreferences

@Composable
fun CourseCompletedScreen(
  userPreferences: UserPreferences,
  onStartAgain: () -> Unit,
  onViewProgress: () -> Unit,
  onGoHome: () -> Unit
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()

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
      // Top Bar (Image 7): Blue header with Trophy, Title, Home icon
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF0F52BA))
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Trophy",
            tint = Color(0xFFFFD54F),
            modifier = Modifier.size(28.dp)
          )

          Text(
            text = "Course Completed!",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp
          )

          IconButton(
            onClick = onGoHome,
            modifier = Modifier.testTag("completed_home_button")
          ) {
            Icon(
              imageVector = Icons.Default.Home,
              contentDescription = "Home",
              tint = Color.White,
              modifier = Modifier.size(26.dp)
            )
          }
        }
      }

      // Scrollable Body
      Column(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
          .verticalScroll(scrollState)
          .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Confetti Graphic with Green Checkmark
        ConfettiCelebrationGraphic(modifier = Modifier.size(130.dp))

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Congratulations!",
          fontSize = 26.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF0D1B2A),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "You have completed all words.",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF108A4E),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Hero Card: You have learned 2000 words (Image 7)
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.5.dp, Color(0xFFC8E6C9)),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 18.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "You have learned",
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF475569)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "2000",
              fontSize = 48.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF108A4E)
            )
            Text(
              text = "words",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 4 Stat Tiles in 2x2 Grid (Image 7)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              StatTile(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.MenuBook,
                iconBgColor = Color(0xFFE8F5E9),
                iconTint = Color(0xFF2E7D32),
                value = "2000",
                valueColor = Color(0xFF2E7D32),
                label = "Words\nLearned"
              )
              StatTile(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.ChatBubble,
                iconBgColor = Color(0xFFE3F2FD),
                iconTint = Color(0xFF1565C0),
                value = "10000",
                valueColor = Color(0xFF1565C0),
                label = "Sentences\nPracticed"
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              StatTile(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.CalendarMonth,
                iconBgColor = Color(0xFFF3E5F5),
                iconTint = Color(0xFF7B1FA2),
                value = "100",
                valueColor = Color(0xFF7B1FA2),
                label = "Days of\nPractice"
              )
              StatTile(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.TrackChanges,
                iconBgColor = Color(0xFFFFF3E0),
                iconTint = Color(0xFFE65100),
                value = "100%",
                valueColor = Color(0xFFE65100),
                label = "Course\nCompleted"
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Yellow Great Job Card with Graduation Illustration (Image 7)
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
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = Color(0xFFF57F17),
              modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Great Job!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E1C00)
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Keep practicing every day. Consistency makes you fluent!",
                fontSize = 13.sp,
                color = Color(0xFF4A3B18),
                lineHeight = 17.sp,
                fontWeight = FontWeight.Medium
              )
            }
            Spacer(modifier = Modifier.width(6.dp))
            GraduationBooksIllustration(modifier = Modifier.size(54.dp))
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Blue What's Next Card (Image 7)
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF5FE)),
          border = BorderStroke(1.dp, Color(0xFFBBDEFB))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = null,
              tint = Color(0xFF1E66D5),
              modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "What's Next?",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Start a new cycle and strengthen your speaking even more.",
                fontSize = 12.sp,
                color = Color(0xFF1E293B),
                lineHeight = 16.sp
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
              onClick = {
                userPreferences.resetAllProgress()
                onStartAgain()
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E66D5)),
              modifier = Modifier.testTag("completed_start_again_button")
            ) {
              Text(text = "Start Again", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.width(4.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Green Button: View My Progress ->
        Button(
          onClick = onViewProgress,
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("completed_view_progress_button"),
          shape = RoundedCornerShape(26.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF108A4E))
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.BarChart,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "View My Progress",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
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

        Spacer(modifier = Modifier.height(10.dp))

        // White Card / Outlined Button: Share My Achievement (Image 7)
        OutlinedButton(
          onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
              type = "text/plain"
              putExtra(
                Intent.EXTRA_TEXT,
                "🎉 I just completed all 2,000 common English words and 10,000 spoken sentences with English Speaking App! Consistency makes you fluent! 🚀"
              )
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Achievement"))
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("completed_share_button"),
          shape = RoundedCornerShape(25.dp),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
          colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.Share,
              contentDescription = null,
              tint = Color(0xFF1E66D5),
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Share My Achievement",
              color = Color(0xFF1E293B),
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

@Composable
private fun StatTile(
  modifier: Modifier = Modifier,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  iconBgColor: Color,
  iconTint: Color,
  value: String,
  valueColor: Color,
  label: String
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 10.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(iconBgColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconTint,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        color = valueColor
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = label,
        fontSize = 11.sp,
        color = Color(0xFF64748B),
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        lineHeight = 14.sp
      )
    }
  }
}
