package com.ibm.icu.util;

import com.google.android.gms.common.util.GmsVersion;
import com.ibm.icu.impl.CalendarAstronomer;
import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.util.ULocale;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class IslamicCalendar extends Calendar {
    private static final long ASTRONOMICAL_EPOC = 1948439;
    private static final long CIVIL_EPOC = 1948440;
    public static final int DHU_AL_HIJJAH = 11;
    public static final int DHU_AL_QIDAH = 10;
    private static final long HIJRA_MILLIS = -42521587200000L;
    public static final int JUMADA_1 = 4;
    public static final int JUMADA_2 = 5;
    public static final int MUHARRAM = 0;
    public static final int RABI_1 = 2;
    public static final int RABI_2 = 3;
    public static final int RAJAB = 6;
    public static final int RAMADAN = 8;
    public static final int SAFAR = 1;
    public static final int SHABAN = 7;
    public static final int SHAWWAL = 9;
    private static final int UMALQURA_YEAR_END = 1600;
    private static final int UMALQURA_YEAR_START = 1300;
    private static final long serialVersionUID = -6253365474073869325L;
    private CalculationType cType;
    private boolean civil;
    private static final int[][] LIMITS = {new int[]{0, 0, 0, 0}, new int[]{1, 1, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[]{0, 0, 11, 11}, new int[]{1, 1, 50, 51}, new int[0], new int[]{1, 1, 29, 30}, new int[]{1, 1, R2.attr.customIntegerValue, R2.attr.customNavigationLayout}, new int[0], new int[]{-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{1, 1, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[]{1, 1, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[0]};
    private static final int[] UMALQURA_MONTHLENGTH = {R2.id.material_clock_period_am_button, R2.layout.tooltip, R2.string.settings_about, R2.dimen.material_filled_edittext_font_1_3_padding_top, R2.dimen.md_button_padding_vertical, R2.attr.region_heightMoreThan, R2.id.material_hour_text_input, R2.color.material_grey_600, R2.dimen.fab_size_mini, R2.dimen.mtrl_progress_circular_inset_small, R2.id.snap, R2.color.text_color, R2.id.media_actions, R2.color.material_on_primary_disabled, R2.layout.preference_widget_switch_compat, R2.dimen.design_snackbar_text_size, R2.dimen.mtrl_calendar_landscape_header_width, R2.id.postLayout, R2.id.repeat_option_picker, R2.color.overflow_icon_pressed_state_bg_color_light, R2.attr.windowMinWidthMinor, R2.id.fade, R2.color.design_default_color_on_surface, R2.dimen.design_snackbar_action_inline_max_width, R2.dimen.fab_plus_icon_stroke, R2.id.md_minMax, R2.attr.md_background_color, R2.drawable.widget_list, R2.attr.visibilityMode, R2.id.fab_label, R2.layout.preference_list_fragment, R2.string.action_settings, R2.color.overflow_icon_pressed_state_bg_color_dark, R2.id.attachment_dialog_root, R2.attr.materialCalendarFullscreenTheme, R2.id.freqSpinner, R2.color.dim_foreground_disabled_material_dark, R2.id.left, R2.dimen.material_clock_period_toggle_margin_left, R2.id.menu_filter_category_remove, R2.attr.menu, R2.id.accessibility_custom_action_17, R2.attr.materialButtonOutlinedStyle, R2.drawable.undobar_button, R2.id.md_colorRValue, R2.id.slide, R2.id.stat_tags, R2.color.tooltip_background_light, R2.attr.md_btnstacked_gravity, R2.id.accessibility_custom_action_0, R2.color.material_blue_grey_900, R2.id.lockedIcon, R2.id.position, R2.id.smallLabel, R2.color.primary_text_default_material_light, R2.id.attachment_dialog_root, R2.color.material_grey_850, R2.attr.listChoiceIndicatorMultipleAnimated, R2.color.location_text, R2.dimen.fab_labels_margin, R2.dimen.mtrl_calendar_selection_baseline_to_top_fullscreen, R2.id.redp_view_pager, R2.color.material_timepicker_button_stroke, R2.id.material_clock_period_pm_button, R2.color.dim_foreground_disabled_material_dark, R2.layout.crouton_handle, R2.layout.test_toolbar_elevation, R2.string.backup_include_settings, R2.color.sp_text_color_secondary_activated_light, R2.id.md_promptCheckbox, R2.drawable.undobar_button_focused, R2.attr.windowFixedWidthMajor, R2.drawable.scroll_bar, R2.id.lockedIcon, R2.id.position, R2.id.repeat_option_picker, R2.color.mtrl_bottom_nav_colored_item_tint, R2.attr.layout_constraintTop_toTopOf, R2.drawable.ic_archive_black_18dp, R2.attr.thumbColor, R2.color.material_grey_600, R2.color.notification_action_color_filter, R2.color.primary_text_default_material_light, R2.id.chain, R2.color.action_color_pressed, R2.attr.layout_constraintRight_creator, R2.drawable.mtrl_tabs_default_indicator, R2.id.material_clock_period_am_button, R2.layout.tooltip, R2.string.category_title, R2.color.text_color_lighter, R2.attr.md_btn_ripple_color, R2.drawable.widget_list, R2.attr.windowFixedWidthMajor, R2.id.fixed, R2.id.password_remove, R2.id.redp_decision_button_layout, R2.id.roc_decision_button_layout, R2.color.primary_text_default_material_light, R2.id.material_timepicker_edit_text, R2.id.fixed, R2.layout.preference_information_material, R2.string.password_successfully_removed, R2.string.settings_about, R2.dimen.material_filled_edittext_font_1_3_padding_top, R2.id.menu_filter_category_remove, R2.id.accelerate, R2.attr.windowFixedWidthMajor, R2.id.layout, R2.layout.test_toolbar_custom_background, R2.string.backup_existing, R2.string.category_title, R2.id.material_value_index, R2.bool.abc_config_showMenuShortcutsWhenKeyboardPresent, R2.id.freqSpinner, R2.color.dim_foreground_disabled_material_dark, R2.id.left, R2.id.note_infos_audiorecordings, R2.id.prev, R2.color.material_on_primary_disabled, R2.bool.transparent_nav, R2.attr.itemTextAppearanceActive, R2.drawable.material_ic_keyboard_arrow_right_black_24dp, R2.id.left, R2.id.password_request, R2.id.radial_picker, R2.color.material_timepicker_modebutton_tint, R2.attr.materialButtonOutlinedStyle, R2.drawable.notification_bg, R2.attr.verticalOffset, R2.dimen.compat_button_padding_vertical_material, R2.dimen.fab_size_mini, R2.dimen.mtrl_calendar_selection_text_baseline_to_bottom_fullscreen, R2.id.repeat_option_picker, R2.color.material_timepicker_clockface, R2.id.material_hour_text_input, R2.color.material_grey_600, R2.id.note_date, R2.id.selection_type, R2.id.snap, R2.color.text_color, R2.id.media_actions, R2.color.material_on_background_emphasis_high_type, R2.id.material_clock_period_pm_button, R2.color.mtrl_navigation_item_icon_tint, R2.dimen.mtrl_calendar_header_toggle_margin_top, R2.dimen.mtrl_chip_pressed_translation_z, R2.id.spButton, R2.color.primary_text_disabled_material_dark, R2.attr.materialButtonOutlinedStyle, R2.id.flip, R2.string.mtrl_chip_close_icon_content_description, R2.id.next, R2.id.position, R2.id.repeat_option_picker, R2.color.overflow_icon_pressed_state_bg_color_light, R2.attr.matProg_rimColor, R2.drawable.mtrl_dropdown_arrow, R2.attr.useMaterialThemeColors, R2.dimen.close_drawable_size, R2.dimen.fab_plus_icon_stroke, R2.dimen.fake_shadow_inset, R2.id.md_promptCheckbox, R2.color.material_on_primary_emphasis_high_type, R2.attr.visibilityMode, R2.id.fab_label, R2.layout.notification_template_media_custom, R2.string.action_settings, R2.color.notification_icon_bg_color, R2.color.primary_text_disabled_material_dark, R2.attr.md_btn_ripple_color, R2.drawable.widget_list, R2.attr.windowMinWidthMajor, R2.color.mtrl_navigation_item_icon_tint, R2.dimen.material_clock_period_toggle_margin_left, R2.dimen.md_button_inset_horizontal, R2.id.menu_filter_remove, R2.color.cardview_shadow_end_color, R2.attr.materialButtonOutlinedStyle, R2.drawable.undobar_button, R2.id.material_clock_period_am_button, R2.id.postLayout, R2.id.stat_tags, R2.color.tooltip_background_light, R2.attr.md_medium_font, R2.id.accessibility_custom_action_0, R2.attr.windowMinWidthMajor, R2.id.left, R2.id.password_request, R2.id.smallLabel, R2.color.primary_material_light, R2.id.attachmentThumbnail, R2.color.abc_tint_spinner, R2.id.left_drawer, R2.color.list_bg_pressed, R2.dimen.design_snackbar_padding_vertical, R2.dimen.mtrl_calendar_header_toggle_margin_top, R2.id.prev, R2.color.material_timepicker_button_stroke, R2.id.honorRequest, R2.color.dim_foreground_disabled_material_dark, R2.id.ivRecurrenceOptionsTP, R2.layout.test_design_radiobutton, R2.string.attachment, R2.color.sp_text_color_secondary_activated_light, R2.id.md_promptCheckbox, R2.color.accent_material_light, R2.attr.layout_constraintLeft_creator, R2.drawable.scroll_bar, R2.id.lockedIcon, R2.id.position, R2.id.repeatMonthlyByNthDayOfTheWeek, R2.color.mtrl_bottom_nav_colored_item_tint, R2.attr.dialogPreferenceStyle, R2.drawable.ic_archive_black_18dp, R2.attr.layout_constraintCircle, R2.color.dim_foreground_disabled_material_dark, R2.color.material_slider_halo_color, R2.color.primary_text_default_material_light, R2.id.chain, R2.color.button_material_dark, R2.attr.defaultValue, R2.drawable.ic_alarm_white_24dp, R2.id.masked, R2.layout.time_picker_layout, R2.string.cancel, R2.color.text_color, R2.id.media_actions, R2.drawable.widget_list, R2.attr.windowFixedWidthMajor, R2.dimen.compat_button_inset_vertical_material, R2.dimen.mtrl_btn_padding_left, R2.dimen.mtrl_card_elevation, R2.id.snap, R2.color.primary_material_light, R2.id.material_timepicker_edit_text, R2.color.material_grey_600, R2.id.next, R2.string.abc_shareactionprovider_share_with_application, R2.string.settings_about, R2.dimen.material_emphasis_medium, R2.id.menu_filter_category_remove, R2.color.material_timepicker_clock_text_color, R2.attr.windowFixedWidthMajor, R2.id.fixed, R2.layout.preference_widget_checkbox, R2.layout.tooltip, R2.string.categories, R2.id.attachmentThumbnail, R2.bool.abc_config_showMenuShortcutsWhenKeyboardPresent, R2.id.end, R2.attr.verticalOffset, R2.id.fab_label, R2.id.material_clock_period_am_button, R2.id.md_minMax, R2.attr.md_btn_ripple_color, R2.id.ALT, R2.attr.thumbStrokeColor, R2.id.drawer_nav_list, R2.layout.date_picker_view_animator, R2.layout.undobar, R2.dimen.highlight_alpha_material_dark, R2.dimen.icon_text_margin, R2.bool.abc_config_showMenuShortcutsWhenKeyboardPresent, R2.id.fullscreen_content, R2.color.dim_foreground_material_dark, R2.id.left, R2.id.position, R2.id.snackbar_text, R2.id.spread_inside, R2.color.ripple_material_dark, R2.attr.md_btn_ripple_color, R2.drawable.white, R2.id.password_request, R2.string.backup_existing, R2.string.settings_backup_summary, R2.dimen.md_button_padding_horizontal, R2.id.repeat_option_picker, R2.color.material_timepicker_modebutton_tint, R2.color.fake_shadow_start_color, R2.dimen.design_snackbar_text_size, R2.layout.test_toolbar_elevation, R2.string.camera, R2.string.content, R2.dimen.material_helper_text_font_1_3_padding_horizontal, R2.color.material_on_background_emphasis_medium, R2.attr.listPopupWindowStyle, R2.dimen.abc_text_size_menu_material, R2.id.mtrl_picker_fullscreen, R2.id.password_request, R2.id.settings_view, R2.color.notification_icon_bg_color, R2.id.material_hour_tv, R2.drawable.mtrl_dialog_background, R2.layout.attachment_dialog, R2.color.design_snackbar_background_color, R2.dimen.design_snackbar_text_size, R2.dimen.fab_size_normal, R2.id.md_promptCheckbox, R2.color.material_on_primary_emphasis_high_type, R2.attr.listPopupWindowStyle};
    private static final byte[] UMALQURA_YEAR_START_ESTIMATE_FIX = {0, 0, -1, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, -1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, 0, 1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1, 0, -1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, 0, 1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    private static CalendarAstronomer astro = new CalendarAstronomer();
    private static CalendarCache cache = new CalendarCache();

    public IslamicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IslamicCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IslamicCalendar(Locale locale) {
        this(TimeZone.forLocaleOrDefault(locale), locale);
    }

    public IslamicCalendar(ULocale uLocale) {
        this(TimeZone.forULocaleOrDefault(uLocale), uLocale);
    }

    public IslamicCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    public IslamicCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.civil = true;
        this.cType = CalculationType.ISLAMIC_CIVIL;
        setCalcTypeForLocale(uLocale);
        setTimeInMillis(System.currentTimeMillis());
    }

    public IslamicCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.civil = true;
        this.cType = CalculationType.ISLAMIC_CIVIL;
        setTime(date);
    }

    public IslamicCalendar(int i, int i2, int i3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.civil = true;
        this.cType = CalculationType.ISLAMIC_CIVIL;
        set(1, i);
        set(2, i2);
        set(5, i3);
    }

    public IslamicCalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.civil = true;
        this.cType = CalculationType.ISLAMIC_CIVIL;
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
        set(13, i6);
    }

    public void setCivil(boolean z) {
        this.civil = z;
        if (z && this.cType != CalculationType.ISLAMIC_CIVIL) {
            long timeInMillis = getTimeInMillis();
            this.cType = CalculationType.ISLAMIC_CIVIL;
            clear();
            setTimeInMillis(timeInMillis);
            return;
        }
        if (z || this.cType == CalculationType.ISLAMIC) {
            return;
        }
        long timeInMillis2 = getTimeInMillis();
        this.cType = CalculationType.ISLAMIC;
        clear();
        setTimeInMillis(timeInMillis2);
    }

    public boolean isCivil() {
        return this.cType == CalculationType.ISLAMIC_CIVIL;
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetLimit(int i, int i2) {
        return LIMITS[i][i2];
    }

    private static final boolean civilLeapYear(int i) {
        return ((i * 11) + 14) % 30 < 11;
    }

    private long yearStart(int i) {
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || (this.cType == CalculationType.ISLAMIC_UMALQURA && (i < 1300 || i > 1600))) {
            return ((i - 1) * R2.attr.customIntegerValue) + ((long) Math.floor(((i * 11) + 3) / 30.0d));
        }
        if (this.cType == CalculationType.ISLAMIC) {
            return trueMonthStart((i - 1) * 12);
        }
        if (this.cType != CalculationType.ISLAMIC_UMALQURA) {
            return 0L;
        }
        int i2 = i - 1300;
        return ((int) ((i2 * 354.3672d) + 460322.05d + 0.5d)) + UMALQURA_YEAR_START_ESTIMATE_FIX[i2];
    }

    private long monthStart(int i, int i2) {
        int i3 = (i2 / 12) + i;
        int i4 = i2 % 12;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || (this.cType == CalculationType.ISLAMIC_UMALQURA && i < 1300)) {
            return ((long) Math.ceil(i4 * 29.5d)) + ((i3 - 1) * R2.attr.customIntegerValue) + ((long) Math.floor(((i3 * 11) + 3) / 30.0d));
        }
        if (this.cType == CalculationType.ISLAMIC) {
            return trueMonthStart(((i3 - 1) * 12) + i4);
        }
        if (this.cType != CalculationType.ISLAMIC_UMALQURA) {
            return 0L;
        }
        long jYearStart = yearStart(i);
        for (int i5 = 0; i5 < i2; i5++) {
            jYearStart += handleGetMonthLength(i, i5);
        }
        return jYearStart;
    }

    private static final long trueMonthStart(long j) {
        long j2 = cache.get(j);
        if (j2 != CalendarCache.EMPTY) {
            return j2;
        }
        long jFloor = (((long) Math.floor(j * 29.530588853d)) * 86400000) + HIJRA_MILLIS;
        moonAge(jFloor);
        if (moonAge(jFloor) >= 0.0d) {
            do {
                jFloor -= 86400000;
            } while (moonAge(jFloor) >= 0.0d);
        } else {
            do {
                jFloor += 86400000;
            } while (moonAge(jFloor) < 0.0d);
        }
        long j3 = ((jFloor - HIJRA_MILLIS) / 86400000) + 1;
        cache.put(j, j3);
        return j3;
    }

    static final double moonAge(long j) {
        double moonAge;
        synchronized (astro) {
            astro.setTime(j);
            moonAge = astro.getMoonAge();
        }
        double d = (moonAge * 180.0d) / 3.141592653589793d;
        return d > 180.0d ? d - 360.0d : d;
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetMonthLength(int i, int i2) {
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || (this.cType == CalculationType.ISLAMIC_UMALQURA && (i < 1300 || i > 1600))) {
            int i3 = 29 + ((i2 + 1) % 2);
            return (i2 == 11 && civilLeapYear(i)) ? i3 + 1 : i3;
        }
        if (this.cType != CalculationType.ISLAMIC) {
            return (UMALQURA_MONTHLENGTH[i - 1300] & (1 << (11 - i2))) == 0 ? 29 : 30;
        }
        return (int) (trueMonthStart(r7 + 1) - trueMonthStart(((i - 1) * 12) + i2));
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetYearLength(int i) {
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA || (this.cType == CalculationType.ISLAMIC_UMALQURA && (i < 1300 || i > 1600))) {
            return (civilLeapYear(i) ? 1 : 0) + R2.attr.customIntegerValue;
        }
        if (this.cType == CalculationType.ISLAMIC) {
            return (int) (trueMonthStart(r5 + 12) - trueMonthStart((i - 1) * 12));
        }
        if (this.cType != CalculationType.ISLAMIC_UMALQURA) {
            return 0;
        }
        int iHandleGetMonthLength = 0;
        for (int i2 = 0; i2 < 12; i2++) {
            iHandleGetMonthLength += handleGetMonthLength(i, i2);
        }
        return iHandleGetMonthLength;
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleComputeMonthStart(int i, int i2, boolean z) {
        return (int) ((monthStart(i, i2) + (this.cType == CalculationType.ISLAMIC_TBLA ? ASTRONOMICAL_EPOC : CIVIL_EPOC)) - 1);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetExtendedYear() {
        if (newerField(19, 1) == 19) {
            return internalGet(19, 1);
        }
        return internalGet(1, 1);
    }

    @Override // com.ibm.icu.util.Calendar
    protected void handleComputeFields(int i) {
        int iFloor;
        int iMin;
        int i2;
        long j = i;
        long j2 = j - CIVIL_EPOC;
        if (this.cType == CalculationType.ISLAMIC_CIVIL || this.cType == CalculationType.ISLAMIC_TBLA) {
            if (this.cType == CalculationType.ISLAMIC_TBLA) {
                j2 = j - ASTRONOMICAL_EPOC;
            }
            Long.signum(j2);
            iFloor = (int) Math.floor(((30 * j2) + 10646) / 10631.0d);
            iMin = Math.min((int) Math.ceil(((j2 - 29) - yearStart(iFloor)) / 29.5d), 11);
        } else if (this.cType == CalculationType.ISLAMIC) {
            int iFloor2 = (int) Math.floor(j2 / 29.530588853d);
            if (j2 - ((long) Math.floor((iFloor2 * 29.530588853d) - 1.0d)) >= 25 && moonAge(internalGetTimeInMillis()) > 0.0d) {
                iFloor2++;
            }
            while (trueMonthStart(iFloor2) > j2) {
                iFloor2--;
            }
            iFloor = iFloor2 >= 0 ? (iFloor2 / 12) + 1 : (iFloor2 + 1) / 12;
            iMin = ((iFloor2 % 12) + 12) % 12;
        } else if (this.cType != CalculationType.ISLAMIC_UMALQURA) {
            iMin = 0;
            iFloor = 0;
        } else if (j2 < yearStart(1300)) {
            iFloor = (int) Math.floor(((30 * j2) + 10646) / 10631.0d);
            iMin = Math.min((int) Math.ceil(((j2 - 29) - yearStart(iFloor)) / 29.5d), 11);
        } else {
            int i3 = R2.color.design_default_color_on_background;
            long jYearStart = 1;
            while (true) {
                if (jYearStart <= 0) {
                    iFloor = i3;
                    i2 = 0;
                    break;
                }
                i3++;
                jYearStart = (j2 - yearStart(i3)) + 1;
                if (jYearStart == handleGetYearLength(i3)) {
                    iFloor = i3;
                    i2 = 11;
                    break;
                } else if (jYearStart < handleGetYearLength(i3)) {
                    int iHandleGetMonthLength = handleGetMonthLength(i3, 0);
                    i2 = 0;
                    while (true) {
                        long j3 = iHandleGetMonthLength;
                        if (jYearStart <= j3) {
                            break;
                        }
                        jYearStart -= j3;
                        i2++;
                        iHandleGetMonthLength = handleGetMonthLength(i3, i2);
                    }
                    iFloor = i3;
                }
            }
            iMin = i2;
        }
        int iMonthStart = ((int) (j2 - monthStart(iFloor, iMin))) + 1;
        int iMonthStart2 = (int) ((j2 - monthStart(iFloor, 0)) + 1);
        internalSet(0, 0);
        internalSet(1, iFloor);
        internalSet(19, iFloor);
        internalSet(2, iMin);
        internalSet(5, iMonthStart);
        internalSet(6, iMonthStart2);
    }

    public enum CalculationType {
        ISLAMIC("islamic"),
        ISLAMIC_CIVIL("islamic-civil"),
        ISLAMIC_UMALQURA("islamic-umalqura"),
        ISLAMIC_TBLA("islamic-tbla");

        private String bcpType;

        CalculationType(String str) {
            this.bcpType = str;
        }

        String bcpType() {
            return this.bcpType;
        }
    }

    public void setCalculationType(CalculationType calculationType) {
        this.cType = calculationType;
        if (calculationType == CalculationType.ISLAMIC_CIVIL) {
            this.civil = true;
        } else {
            this.civil = false;
        }
    }

    public CalculationType getCalculationType() {
        return this.cType;
    }

    private void setCalcTypeForLocale(ULocale uLocale) {
        String calendarType = CalendarUtil.getCalendarType(uLocale);
        if ("islamic-civil".equals(calendarType)) {
            setCalculationType(CalculationType.ISLAMIC_CIVIL);
            return;
        }
        if ("islamic-umalqura".equals(calendarType)) {
            setCalculationType(CalculationType.ISLAMIC_UMALQURA);
            return;
        }
        if ("islamic-tbla".equals(calendarType)) {
            setCalculationType(CalculationType.ISLAMIC_TBLA);
        } else if (calendarType.startsWith("islamic")) {
            setCalculationType(CalculationType.ISLAMIC);
        } else {
            setCalculationType(CalculationType.ISLAMIC_CIVIL);
        }
    }

    @Override // com.ibm.icu.util.Calendar
    public String getType() {
        CalculationType calculationType = this.cType;
        return calculationType == null ? "islamic" : calculationType.bcpType();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        CalculationType calculationType = this.cType;
        if (calculationType == null) {
            this.cType = this.civil ? CalculationType.ISLAMIC_CIVIL : CalculationType.ISLAMIC;
        } else {
            this.civil = calculationType == CalculationType.ISLAMIC_CIVIL;
        }
    }
}
