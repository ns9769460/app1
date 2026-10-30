package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BrainHappyIllustration(modifier: Modifier = Modifier) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    Canvas(modifier = Modifier.size(160.dp)) {
      val w = size.width
      val h = size.height

      // Soft circular aura behind brain
      drawCircle(
        color = Color(0xFFF1EBFD),
        radius = w * 0.46f,
        center = Offset(w * 0.5f, h * 0.52f)
      )

      // Light bulb at top right
      val bulbCenter = Offset(w * 0.76f, h * 0.22f)
      // Glow rays
      val rayColor = Color(0xFFFFB300)
      for (angle in 0 until 6) {
        val rad = Math.toRadians((angle * 60 - 30).toDouble())
        val r1 = 22f
        val r2 = 32f
        drawLine(
          color = rayColor,
          start = Offset(bulbCenter.x + (Math.cos(rad) * r1).toFloat(), bulbCenter.y + (Math.sin(rad) * r1).toFloat()),
          end = Offset(bulbCenter.x + (Math.cos(rad) * r2).toFloat(), bulbCenter.y + (Math.sin(rad) * r2).toFloat()),
          strokeWidth = 3f,
          cap = StrokeCap.Round
        )
      }
      // Bulb body
      drawCircle(
        color = Color(0xFFFFD54F),
        radius = 16f,
        center = bulbCenter
      )
      drawRect(
        color = Color(0xFF78909C),
        topLeft = Offset(bulbCenter.x - 7f, bulbCenter.y + 12f),
        size = Size(14f, 8f)
      )

      // Main brain lobes (warm coral-pink)
      val brainColor = Color(0xFFFF8A80)
      val brainOutline = Color(0xFFD84343)
      val leftCenter = Offset(w * 0.38f, h * 0.54f)
      val rightCenter = Offset(w * 0.62f, h * 0.54f)

      // Brain lobes
      drawCircle(color = brainColor, radius = w * 0.23f, center = leftCenter)
      drawCircle(color = brainColor, radius = w * 0.23f, center = rightCenter)
      drawCircle(color = brainColor, radius = w * 0.18f, center = Offset(w * 0.5f, h * 0.44f))

      // Brain folds
      val foldPath = Path().apply {
        // Left lobes folds
        moveTo(w * 0.32f, h * 0.44f)
        cubicTo(w * 0.25f, h * 0.50f, w * 0.35f, h * 0.58f, w * 0.30f, h * 0.64f)
        // Right lobes folds
        moveTo(w * 0.68f, h * 0.44f)
        cubicTo(w * 0.75f, h * 0.50f, w * 0.65f, h * 0.58f, w * 0.70f, h * 0.64f)
        // Center fissure
        moveTo(w * 0.5f, h * 0.36f)
        lineTo(w * 0.5f, h * 0.68f)
      }
      drawPath(foldPath, color = brainOutline, style = Stroke(width = 4f, cap = StrokeCap.Round))

      // Happy face
      // Left eye
      drawCircle(color = Color(0xFF37474F), radius = 5f, center = Offset(w * 0.38f, h * 0.56f))
      // Right eye
      drawCircle(color = Color(0xFF37474F), radius = 5f, center = Offset(w * 0.62f, h * 0.56f))
      // Rosy cheeks
      drawCircle(color = Color(0xFFFF5252).copy(alpha = 0.4f), radius = 8f, center = Offset(w * 0.32f, h * 0.60f))
      drawCircle(color = Color(0xFFFF5252).copy(alpha = 0.4f), radius = 8f, center = Offset(w * 0.68f, h * 0.60f))

      // Smile mouth
      val smilePath = Path().apply {
        moveTo(w * 0.44f, h * 0.62f)
        cubicTo(w * 0.47f, h * 0.68f, w * 0.53f, h * 0.68f, w * 0.56f, h * 0.62f)
      }
      drawPath(smilePath, color = Color(0xFF37474F), style = Stroke(width = 4f, cap = StrokeCap.Round))
    }
  }
}

@Composable
fun BrainTiredIllustration(modifier: Modifier = Modifier) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    Canvas(modifier = Modifier.size(160.dp)) {
      val w = size.width
      val h = size.height

      // Soft circular aura behind brain
      drawCircle(
        color = Color(0xFFFFF3E0),
        radius = w * 0.46f,
        center = Offset(w * 0.5f, h * 0.52f)
      )

      // Main brain lobes (stressed coral-pink)
      val brainColor = Color(0xFFFFAB91)
      val brainOutline = Color(0xFFD84315)
      val leftCenter = Offset(w * 0.38f, h * 0.54f)
      val rightCenter = Offset(w * 0.62f, h * 0.54f)

      drawCircle(color = brainColor, radius = w * 0.23f, center = leftCenter)
      drawCircle(color = brainColor, radius = w * 0.23f, center = rightCenter)
      drawCircle(color = brainColor, radius = w * 0.18f, center = Offset(w * 0.5f, h * 0.44f))

      // Center fissure
      drawLine(
        color = brainOutline,
        start = Offset(w * 0.5f, h * 0.36f),
        end = Offset(w * 0.5f, h * 0.68f),
        strokeWidth = 4f,
        cap = StrokeCap.Round
      )

      // Sweat droplets at top right
      val sweatColor = Color(0xFF4FC3F7)
      drawCircle(color = sweatColor, radius = 9f, center = Offset(w * 0.78f, h * 0.36f))
      drawCircle(color = sweatColor, radius = 6f, center = Offset(w * 0.84f, h * 0.44f))

      // Worried sloped eyebrows
      drawLine(
        color = Color(0xFF37474F),
        start = Offset(w * 0.33f, h * 0.50f),
        end = Offset(w * 0.42f, h * 0.54f),
        strokeWidth = 4f,
        cap = StrokeCap.Round
      )
      drawLine(
        color = Color(0xFF37474F),
        start = Offset(w * 0.67f, h * 0.50f),
        end = Offset(w * 0.58f, h * 0.54f),
        strokeWidth = 4f,
        cap = StrokeCap.Round
      )

      // Worried eyes
      drawCircle(color = Color(0xFF37474F), radius = 5f, center = Offset(w * 0.38f, h * 0.56f))
      drawCircle(color = Color(0xFF37474F), radius = 5f, center = Offset(w * 0.62f, h * 0.56f))

      // Open tired mouth
      val mouthPath = Path().apply {
        moveTo(w * 0.44f, h * 0.68f)
        cubicTo(w * 0.46f, h * 0.62f, w * 0.54f, h * 0.62f, w * 0.56f, h * 0.68f)
        close()
      }
      drawPath(mouthPath, color = Color(0xFF37474F))
    }
  }
}

@Composable
fun HeadSilhouetteGraphic(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier.size(64.dp)) {
    val w = size.width
    val h = size.height

    // Purple head profile
    val headPath = Path().apply {
      moveTo(w * 0.7f, h * 0.1f)
      cubicTo(w * 0.3f, h * 0.1f, w * 0.2f, h * 0.4f, w * 0.2f, h * 0.55f)
      lineTo(w * 0.15f, h * 0.60f)
      lineTo(w * 0.22f, h * 0.65f)
      cubicTo(w * 0.22f, h * 0.75f, w * 0.35f, h * 0.85f, w * 0.45f, h * 0.88f)
      lineTo(w * 0.45f, h * 0.98f)
      lineTo(w * 0.85f, h * 0.98f)
      lineTo(w * 0.85f, h * 0.75f)
      cubicTo(w * 0.95f, h * 0.55f, w * 0.95f, h * 0.2f, w * 0.7f, h * 0.1f)
      close()
    }
    drawPath(headPath, color = Color(0xFF7E57C2))

    // Brain pattern inside silhouette
    drawCircle(
      color = Color.White.copy(alpha = 0.85f),
      radius = w * 0.12f,
      center = Offset(w * 0.52f, h * 0.36f),
      style = Stroke(width = 3f)
    )
    drawCircle(
      color = Color.White.copy(alpha = 0.85f),
      radius = w * 0.10f,
      center = Offset(w * 0.68f, h * 0.38f),
      style = Stroke(width = 3f)
    )
  }
}

@Composable
fun GraduationBooksIllustration(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier.size(72.dp)) {
    val w = size.width
    val h = size.height

    // Book 1 (bottom green book)
    drawRoundRect(
      color = Color(0xFF2E7D32),
      topLeft = Offset(w * 0.12f, h * 0.72f),
      size = Size(w * 0.76f, h * 0.16f),
      cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )
    drawRect(
      color = Color(0xFFFFF9C4),
      topLeft = Offset(w * 0.82f, h * 0.74f),
      size = Size(w * 0.04f, h * 0.12f)
    )

    // Book 2 (middle orange book)
    drawRoundRect(
      color = Color(0xFFE65100),
      topLeft = Offset(w * 0.18f, h * 0.58f),
      size = Size(w * 0.68f, h * 0.15f),
      cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )
    drawRect(
      color = Color(0xFFFFF9C4),
      topLeft = Offset(w * 0.80f, h * 0.60f),
      size = Size(w * 0.04f, h * 0.11f)
    )

    // Mortarboard (graduation cap on top)
    val capPath = Path().apply {
      moveTo(w * 0.5f, h * 0.22f)
      lineTo(w * 0.86f, h * 0.36f)
      lineTo(w * 0.5f, h * 0.50f)
      lineTo(w * 0.14f, h * 0.36f)
      close()
    }
    drawPath(capPath, color = Color(0xFF212121))

    // Cap skullcap beneath
    drawRoundRect(
      color = Color(0xFF263238),
      topLeft = Offset(w * 0.32f, h * 0.44f),
      size = Size(w * 0.36f, h * 0.12f),
      cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
    )

    // Golden tassel
    val tasselPath = Path().apply {
      moveTo(w * 0.5f, h * 0.36f)
      lineTo(w * 0.78f, h * 0.44f)
      lineTo(w * 0.80f, h * 0.60f)
    }
    drawPath(tasselPath, color = Color(0xFFFFB300), style = Stroke(width = 4f, cap = StrokeCap.Round))
  }
}

@Composable
fun ConfettiCelebrationGraphic(modifier: Modifier = Modifier) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    Canvas(modifier = Modifier.size(140.dp)) {
      val w = size.width
      val h = size.height
      val center = Offset(w * 0.5f, h * 0.5f)

      // Confetti ribbons & specks
      val confettiColors = listOf(
        Color(0xFFFFB300),
        Color(0xFF00ACC1),
        Color(0xFFE91E63),
        Color(0xFF43A047),
        Color(0xFF7E57C2),
        Color(0xFFFF7043)
      )

      val offsets = listOf(
        Offset(w * 0.20f, h * 0.25f),
        Offset(w * 0.35f, h * 0.15f),
        Offset(w * 0.65f, h * 0.14f),
        Offset(w * 0.82f, h * 0.24f),
        Offset(w * 0.15f, h * 0.45f),
        Offset(w * 0.88f, h * 0.48f),
        Offset(w * 0.22f, h * 0.75f),
        Offset(w * 0.78f, h * 0.72f)
      )

      for (i in offsets.indices) {
        val color = confettiColors[i % confettiColors.size]
        val pos = offsets[i]
        if (i % 2 == 0) {
          drawCircle(color = color, radius = 5f, center = pos)
        } else {
          drawRoundRect(
            color = color,
            topLeft = pos,
            size = Size(10f, 6f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
          )
        }
      }

      // Big green checkmark circle
      drawCircle(
        color = Color(0xFF2E7D32),
        radius = w * 0.28f,
        center = center
      )

      // Crisp white checkmark
      val checkPath = Path().apply {
        moveTo(center.x - 18f, center.y + 2f)
        lineTo(center.x - 4f, center.y + 16f)
        lineTo(center.x + 20f, center.y - 14f)
      }
      drawPath(
        path = checkPath,
        color = Color.White,
        style = Stroke(width = 8f, cap = StrokeCap.Round)
      )
    }
  }
}

@Composable
fun SlidePillBadge(
  text: String,
  backgroundColor: Color,
  contentColor: Color = Color.White
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(backgroundColor),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      color = contentColor,
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
      modifier = Modifier.background(Color.Transparent)
    )
  }
}
