package com.faltenreich.diaguard.feature.export;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.feature.export.job.FileType;
import com.faltenreich.diaguard.test.junit.rule.TestRuleFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.annotation.LooperMode;

@RunWith(AndroidJUnit4.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class ExportCsvTest {

    @Rule
    public final TestRule rule = TestRuleFactory.forFragment(ExportFragment.class);

    @Before
    public void setup() {
        ExportTestUtils.selectFileType(FileType.CSV);
    }

    @Test
    public void selectingPDF_shouldHideStyleSpinner() {
        Espresso.onView(ViewMatchers.withId(R.id.style_spinner))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void selectingPDF_shouldHideCalendarWeekCheckBox() {
        Espresso.onView(ViewMatchers.withId(R.id.include_calendar_week_checkbox))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void selectingPDF_shouldHideGeneratedDateCheckBox() {
        Espresso.onView(ViewMatchers.withId(R.id.include_generated_date_checkbox))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void selectingPDF_shouldHidePageNumberCheckBox() {
        Espresso.onView(ViewMatchers.withId(R.id.include_page_number_checkbox))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void selectingPDF_shouldShowNoteCheckbox() {
        Espresso.onView(ViewMatchers.withId(R.id.note_checkbox))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void selectingPDF_shouldShowTagsCheckbox() {
        Espresso.onView(ViewMatchers.withId(R.id.tags_checkbox))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void selectingPDF_shouldShowEmptyDaysCheckbox() {
        Espresso.onView(ViewMatchers.withId(R.id.empty_days_checkbox))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
