package com.ibm.icu.impl.data;

import com.ibm.icu.util.EasterHoliday;
import com.ibm.icu.util.Holiday;
import com.ibm.icu.util.SimpleHoliday;
import it.piapostolos.android.pinotes.R2;
import java.util.ListResourceBundle;

/* loaded from: classes2.dex */
public class HolidayBundle_en_US extends ListResourceBundle {
    private static final Object[][] fContents;
    private static final Holiday[] fHolidays;

    static {
        Holiday[] holidayArr = {SimpleHoliday.NEW_YEARS_DAY, new SimpleHoliday(0, 15, 2, "Martin Luther King Day", R2.dimen.navigation_drawer_header_height), new SimpleHoliday(1, 15, 2, "Presidents' Day", R2.dimen.mtrl_textinput_outline_box_expanded_padding), new SimpleHoliday(1, 22, "Washington's Birthday", R2.dimen.md_content_textsize, R2.dimen.mtrl_textinput_end_icon_margin_start), EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, new SimpleHoliday(4, 8, 1, "Mother's Day", R2.dimen.mtrl_extended_fab_translation_z_pressed), new SimpleHoliday(4, 31, -2, "Memorial Day", R2.dimen.mtrl_textinput_box_label_cutout_padding), new SimpleHoliday(4, 30, "Memorial Day", R2.dimen.mtrl_calendar_month_horizontal_padding, R2.dimen.mtrl_textinput_box_corner_radius_small), new SimpleHoliday(5, 15, 1, "Father's Day", R2.dimen.mtrl_slider_thumb_elevation), new SimpleHoliday(6, 4, "Independence Day", R2.dimen.md_content_textsize), new SimpleHoliday(8, 1, 2, "Labor Day", R2.dimen.mtrl_edittext_rectangle_top_offset), new SimpleHoliday(10, 2, 3, "Election Day"), new SimpleHoliday(9, 8, 2, "Columbus Day", R2.dimen.mtrl_textinput_box_label_cutout_padding), new SimpleHoliday(9, 31, "Halloween"), new SimpleHoliday(10, 11, "Veterans' Day", R2.dimen.mtrl_fab_translation_z_pressed), new SimpleHoliday(10, 22, 5, "Thanksgiving", R2.dimen.mtrl_calendar_header_text_padding), SimpleHoliday.CHRISTMAS};
        fHolidays = holidayArr;
        fContents = new Object[][]{new Object[]{"holidays", holidayArr}};
    }

    @Override // java.util.ListResourceBundle
    public synchronized Object[][] getContents() {
        return fContents;
    }
}
