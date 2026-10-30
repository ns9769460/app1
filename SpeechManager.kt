package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

class SpeechManager(context: Context) : TextToSpeech.OnInitListener {
  private var tts: TextToSpeech? = null
  private var isInitialized = false

  val currentlySpeakingText = mutableStateOf<String?>(null)

  init {
    tts = TextToSpeech(context.applicationContext, this)
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = tts?.setLanguage(Locale.US)
      if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
        tts?.setSpeechRate(0.92f) // slightly relaxed speaking rate for clear learner comprehension
        tts?.setPitch(1.0f)
        isInitialized = true

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
          override fun onStart(utteranceId: String?) {}

          override fun onDone(utteranceId: String?) {
            currentlySpeakingText.value = null
          }

          override fun onError(utteranceId: String?) {
            currentlySpeakingText.value = null
          }
        })
      }
    }
  }

  fun speak(text: String) {
    if (!isInitialized || tts == null) return
    currentlySpeakingText.value = text
    val utteranceId = text.hashCode().toString()
    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
  }

  fun stop() {
    tts?.stop()
    currentlySpeakingText.value = null
  }

  fun shutdown() {
    tts?.stop()
    tts?.shutdown()
    tts = null
    isInitialized = false
  }
}
