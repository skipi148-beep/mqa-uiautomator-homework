package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"
const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testInternetSettings() {
        waitForPackage(SETTINGS_PACKAGE)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }

    @Test
    fun testChangeText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, textToSet)
    }

    @Test
    fun testSetEmptyStringDoesNotChangeText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        // 1. Запоминаем изначальный текст в TextView
        val initialText = device.findObject(By.res(packageName, "textToBeChanged")).text

        // 2. Находим поле ввода и устанавливаем пустую строку из пробелов
        device.findObject(By.res(packageName, "userInput")).text = "   "

        // 3. Нажимаем кнопку изменения текста
        device.findObject(By.res(packageName, "buttonChange")).click()

        // 4. Получаем обновленный текст из TextView
        val updatedText = device.findObject(By.res(packageName, "textToBeChanged")).text

        // 5. Проверяем, что текст НЕ изменился
        assertEquals(initialText, updatedText)
    }

    @Test
    fun testOpenTextInNewActivity() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)
        val textToType = "Netology UI Automator"

        // 1. Находим поле ввода и пишем непустой текст
        device.findObject(By.res(packageName, "userInput")).text = textToType

        // 2. Нажимаем кнопку открытия новой Activity
        device.findObject(By.res(packageName, "buttonActivity")).click()

        // 3. Ждем появления TextView из новой Activity по его ID (textActivity2)
        device.wait(Until.hasObject(By.res(packageName, "textActivity2")), 5000)

        // 4. Находим TextView на новом экране и берем его значение
        val textInNewActivity = device.findObject(By.res(packageName, "textActivity2")).text

        // 5. Проверяем совпадение текста
        assertEquals(textToType, textInNewActivity)
    }
}
