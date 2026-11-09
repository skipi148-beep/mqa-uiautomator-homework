package ru.netology.testing.uiautomator;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.netology.testing.uiautomator.MainActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // Используем ActivityTestRule для запуска MainActivity из этого проекта
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testMainScreenText() {
        // Находим элемент текстового поля на главном экране по его реальному ID
        ViewInteraction mainText = onView(
                withId(R.id.textToBeChanged)
        );

        // Проверяем, что изначально на экране отображается пустая строка или текст по умолчанию
        mainText.check(
                matches(
                        withText("Hello UIAutomator")
                )
        );
    }
}
