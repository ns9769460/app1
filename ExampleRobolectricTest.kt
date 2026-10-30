package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("English Speaking", appName)
  }

  @Test
  fun `verify word repository and chapters`() {
    val word1 = com.example.data.WordRepository.getWord(1)
    assertEquals("THE", word1.word)
    assertEquals(5, word1.examples.size)
    assertEquals(20, com.example.data.WordRepository.chapters.size)
  }
}
