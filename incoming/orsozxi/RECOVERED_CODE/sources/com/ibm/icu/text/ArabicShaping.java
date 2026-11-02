package com.ibm.icu.text;

import com.ibm.icu.impl.UBiDiProps;
import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes2.dex */
public final class ArabicShaping {
    private static final int ALEFTYPE = 32;
    private static final int DESHAPE_MODE = 1;
    public static final int DIGITS_AN2EN = 64;
    public static final int DIGITS_EN2AN = 32;
    public static final int DIGITS_EN2AN_INIT_AL = 128;
    public static final int DIGITS_EN2AN_INIT_LR = 96;
    public static final int DIGITS_MASK = 224;
    public static final int DIGITS_NOOP = 0;
    public static final int DIGIT_TYPE_AN = 0;
    public static final int DIGIT_TYPE_AN_EXTENDED = 256;
    public static final int DIGIT_TYPE_MASK = 256;
    private static final char HAMZA06_CHAR = 1569;
    private static final char HAMZAFE_CHAR = 65152;
    private static final int IRRELEVANT = 4;
    public static final int LAMALEF_AUTO = 65536;
    public static final int LAMALEF_BEGIN = 3;
    public static final int LAMALEF_END = 2;
    public static final int LAMALEF_MASK = 65539;
    public static final int LAMALEF_NEAR = 1;
    public static final int LAMALEF_RESIZE = 0;
    private static final char LAMALEF_SPACE_SUB = 65535;
    private static final int LAMTYPE = 16;
    private static final char LAM_CHAR = 1604;
    public static final int LENGTH_FIXED_SPACES_AT_BEGINNING = 3;
    public static final int LENGTH_FIXED_SPACES_AT_END = 2;
    public static final int LENGTH_FIXED_SPACES_NEAR = 1;
    public static final int LENGTH_GROW_SHRINK = 0;
    public static final int LENGTH_MASK = 65539;
    public static final int LETTERS_MASK = 24;
    public static final int LETTERS_NOOP = 0;
    public static final int LETTERS_SHAPE = 8;
    public static final int LETTERS_SHAPE_TASHKEEL_ISOLATED = 24;
    public static final int LETTERS_UNSHAPE = 16;
    private static final int LINKL = 2;
    private static final int LINKR = 1;
    private static final int LINK_MASK = 3;
    private static final char NEW_TAIL_CHAR = 65139;
    private static final char OLD_TAIL_CHAR = 8203;
    public static final int SEEN_MASK = 7340032;
    public static final int SEEN_TWOCELL_NEAR = 2097152;
    private static final char SHADDA06_CHAR = 1617;
    private static final char SHADDA_CHAR = 65148;
    private static final char SHADDA_TATWEEL_CHAR = 65149;
    private static final int SHAPE_MODE = 0;
    public static final int SHAPE_TAIL_NEW_UNICODE = 134217728;
    public static final int SHAPE_TAIL_TYPE_MASK = 134217728;
    public static final int SPACES_RELATIVE_TO_TEXT_BEGIN_END = 67108864;
    public static final int SPACES_RELATIVE_TO_TEXT_MASK = 67108864;
    private static final char SPACE_CHAR = ' ';
    public static final int TASHKEEL_BEGIN = 262144;
    public static final int TASHKEEL_END = 393216;
    public static final int TASHKEEL_MASK = 917504;
    public static final int TASHKEEL_REPLACE_BY_TATWEEL = 786432;
    public static final int TASHKEEL_RESIZE = 524288;
    private static final char TASHKEEL_SPACE_SUB = 65534;
    private static final char TATWEEL_CHAR = 1600;
    public static final int TEXT_DIRECTION_LOGICAL = 0;
    public static final int TEXT_DIRECTION_MASK = 4;
    public static final int TEXT_DIRECTION_VISUAL_LTR = 4;
    public static final int TEXT_DIRECTION_VISUAL_RTL = 0;
    public static final int YEHHAMZA_MASK = 58720256;
    public static final int YEHHAMZA_TWOCELL_NEAR = 16777216;
    private static final char YEH_HAMZAFE_CHAR = 65161;
    private static final char YEH_HAMZA_CHAR = 1574;
    private boolean isLogical;
    private final int options;
    private boolean spacesRelativeToTextBeginEnd;
    private char tailChar;
    private static final int[] irrelevantPos = {0, 2, 4, 6, 8, 10, 12, 14};
    private static final int[] tailFamilyIsolatedFinal = {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
    private static final int[] tashkeelMedial = {0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
    private static final char[] yehHamzaToYeh = {65263, 65264};
    private static final char[] convertNormalizedLamAlef = {1570, 1571, 1573, 1575};
    private static final int[] araLink = {R2.style.ShapeAppearanceOverlay_MaterialComponents_MaterialCalendar_Year, R2.styleable.ActionBarLayout_android_layout_gravity, R2.styleable.Constraint_layout_constraintVertical_chainStyle, R2.styleable.LinearLayoutCompat_showDividers, R2.styleable.SwitchCompat_android_textOn, 7457, 7939, 8961, 9475, 10499, 11523, 12547, 13571, 14593, 15105, 15617, 16129, 16643, 17667, 18691, 19715, 20739, 21763, 22787, 23811, 0, 0, 0, 0, 0, 3, 24835, 25859, 26883, 27923, 28931, 29955, 30979, 32001, 32513, 33027, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 34049, 34561, 35073, 35585, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 33, 33, 0, 33, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 3, 3, 1, 1};
    private static final int[] presLink = {3, 3, 3, 0, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 32, 33, 32, 33, 0, 1, 32, 33, 0, 2, 3, 1, 32, 33, 0, 2, 3, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 16, 18, 19, 17, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1};
    private static int[] convertFEto06 = {R2.dimen.close_drawable_size, R2.dimen.close_drawable_size, R2.dimen.color_center_halo_radius, R2.dimen.color_center_halo_radius, R2.dimen.color_center_radius, R2.dimen.color_center_radius, R2.dimen.color_pointer_halo_radius, R2.dimen.color_pointer_halo_radius, R2.dimen.color_pointer_radius, R2.dimen.color_pointer_radius, R2.dimen.color_wheel_radius, R2.dimen.color_wheel_radius, R2.dimen.color_wheel_thickness, R2.dimen.color_wheel_thickness, R2.dimen.compat_button_inset_horizontal_material, R2.dimen.compat_button_inset_horizontal_material, R2.dimen.abc_text_size_button_material, R2.dimen.abc_text_size_caption_material, R2.dimen.abc_text_size_caption_material, R2.dimen.abc_text_size_display_1_material, R2.dimen.abc_text_size_display_1_material, R2.dimen.abc_text_size_display_2_material, R2.dimen.abc_text_size_display_2_material, R2.dimen.abc_text_size_display_3_material, R2.dimen.abc_text_size_display_3_material, R2.dimen.abc_text_size_display_4_material, R2.dimen.abc_text_size_display_4_material, R2.dimen.abc_text_size_display_4_material, R2.dimen.abc_text_size_display_4_material, R2.dimen.abc_text_size_headline_material, R2.dimen.abc_text_size_headline_material, R2.dimen.abc_text_size_large_material, R2.dimen.abc_text_size_large_material, R2.dimen.abc_text_size_large_material, R2.dimen.abc_text_size_large_material, R2.dimen.abc_text_size_medium_material, R2.dimen.abc_text_size_medium_material, R2.dimen.abc_text_size_menu_header_material, R2.dimen.abc_text_size_menu_header_material, R2.dimen.abc_text_size_menu_header_material, R2.dimen.abc_text_size_menu_header_material, R2.dimen.abc_text_size_menu_material, R2.dimen.abc_text_size_menu_material, R2.dimen.abc_text_size_menu_material, R2.dimen.abc_text_size_menu_material, R2.dimen.abc_text_size_small_material, R2.dimen.abc_text_size_small_material, R2.dimen.abc_text_size_small_material, R2.dimen.abc_text_size_small_material, R2.dimen.abc_text_size_subhead_material, R2.dimen.abc_text_size_subhead_material, R2.dimen.abc_text_size_subhead_material, R2.dimen.abc_text_size_subhead_material, R2.dimen.abc_text_size_subtitle_material_toolbar, R2.dimen.abc_text_size_subtitle_material_toolbar, R2.dimen.abc_text_size_subtitle_material_toolbar, R2.dimen.abc_text_size_subtitle_material_toolbar, R2.dimen.abc_text_size_title_material, R2.dimen.abc_text_size_title_material, R2.dimen.abc_text_size_title_material_toolbar, R2.dimen.abc_text_size_title_material_toolbar, R2.dimen.action_bar_size, R2.dimen.action_bar_size, R2.dimen.actionbar_icon_padding, R2.dimen.actionbar_icon_padding, R2.dimen.activity_additional_margin, R2.dimen.activity_additional_margin, R2.dimen.activity_additional_margin, R2.dimen.activity_additional_margin, R2.dimen.activity_horizontal_margin, R2.dimen.activity_horizontal_margin, R2.dimen.activity_horizontal_margin, R2.dimen.activity_horizontal_margin, R2.dimen.activity_vertical_margin, R2.dimen.activity_vertical_margin, R2.dimen.activity_vertical_margin, R2.dimen.activity_vertical_margin, R2.dimen.appcompat_dialog_background_inset, R2.dimen.appcompat_dialog_background_inset, R2.dimen.appcompat_dialog_background_inset, R2.dimen.appcompat_dialog_background_inset, R2.dimen.attachment_dialog_drawable_padding, R2.dimen.attachment_dialog_drawable_padding, R2.dimen.attachment_dialog_drawable_padding, R2.dimen.attachment_dialog_drawable_padding, R2.dimen.attachment_dialog_items_padding, R2.dimen.attachment_dialog_items_padding, R2.dimen.attachment_dialog_items_padding, R2.dimen.attachment_dialog_items_padding, R2.dimen.attachment_dialog_text_size, R2.dimen.attachment_dialog_text_size, R2.dimen.attachment_dialog_text_size, R2.dimen.attachment_dialog_text_size, R2.dimen.bar_length, R2.dimen.bar_length, R2.dimen.bar_length, R2.dimen.bar_length, R2.dimen.button_padding_vertical_material, R2.dimen.button_padding_vertical_material, R2.dimen.button_padding_vertical_material, R2.dimen.button_padding_vertical_material, R2.dimen.cardview_compat_inset_shadow, R2.dimen.cardview_compat_inset_shadow, R2.dimen.cardview_compat_inset_shadow, R2.dimen.cardview_compat_inset_shadow, R2.dimen.cardview_default_elevation, R2.dimen.cardview_default_elevation, R2.dimen.cardview_default_elevation, R2.dimen.cardview_default_elevation, R2.dimen.cardview_default_radius, R2.dimen.cardview_default_radius, R2.dimen.cardview_default_radius, R2.dimen.cardview_default_radius, R2.dimen.chglib_material_keyline1, R2.dimen.chglib_material_keyline1, R2.dimen.chglib_material_keyline1, R2.dimen.chglib_material_keyline1, R2.dimen.chglib_material_list_fontsize, R2.dimen.chglib_material_list_fontsize, R2.dimen.chglib_material_list_fontsize, R2.dimen.chglib_material_list_fontsize, R2.dimen.chglib_material_list_suheader_fontsize, R2.dimen.chglib_material_list_suheader_fontsize, R2.dimen.chglib_material_list_suheader_fontsize, R2.dimen.chglib_material_list_suheader_fontsize, R2.dimen.chglib_material_minHeight, R2.dimen.chglib_material_minHeight, R2.dimen.circular_progress_border, R2.dimen.circular_progress_border, R2.dimen.clock_face_margin_start, R2.dimen.clock_face_margin_start, R2.dimen.clock_face_margin_start, R2.dimen.clock_face_margin_start, R2.dimen.datepicker_day_number_size, R2.dimen.datepicker_day_number_size, R2.dimen.datepicker_dialog_width, R2.dimen.datepicker_dialog_width, R2.dimen.datepicker_header_height, R2.dimen.datepicker_header_height, R2.dimen.datepicker_header_text_size, R2.dimen.datepicker_header_text_size};
    private static final int[][][] shapeTable = {new int[][]{new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, new int[]{0, 1, 0, 3}, new int[]{0, 1, 0, 1}}, new int[][]{new int[]{0, 0, 2, 2}, new int[]{0, 0, 1, 2}, new int[]{0, 1, 1, 2}, new int[]{0, 1, 1, 3}}, new int[][]{new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, new int[]{0, 1, 0, 3}, new int[]{0, 1, 0, 3}}, new int[][]{new int[]{0, 0, 1, 2}, new int[]{0, 0, 1, 2}, new int[]{0, 1, 1, 2}, new int[]{0, 1, 1, 3}}};

    private static char changeLamAlef(char c) {
        if (c == 1570) {
            return (char) 1628;
        }
        if (c == 1571) {
            return (char) 1629;
        }
        if (c != 1573) {
            return c != 1575 ? (char) 0 : (char) 1631;
        }
        return (char) 1630;
    }

    private static boolean isAlefChar(char c) {
        return c == 1570 || c == 1571 || c == 1573 || c == 1575;
    }

    private static boolean isAlefMaksouraChar(char c) {
        return c == 65263 || c == 65264 || c == 1609;
    }

    private static boolean isLamAlefChar(char c) {
        return c >= 65269 && c <= 65276;
    }

    private static boolean isNormalizedLamAlefChar(char c) {
        return c >= 1628 && c <= 1631;
    }

    private static int isSeenFamilyChar(char c) {
        return (c < 1587 || c > 1590) ? 0 : 1;
    }

    private static boolean isTailChar(char c) {
        return c == 8203 || c == 65139;
    }

    private static boolean isTashkeelChar(char c) {
        return c >= 1611 && c <= 1618;
    }

    private static boolean isTashkeelCharFE(char c) {
        return c != 65141 && c >= 65136 && c <= 65151;
    }

    private static boolean isYehHamzaChar(char c) {
        return c == 65161 || c == 65162;
    }

    private static int specialChar(char c) {
        if ((c > 1569 && c < 1574) || c == 1575) {
            return 1;
        }
        if (c > 1582 && c < 1587) {
            return 1;
        }
        if ((c > 1607 && c < 1610) || c == 1577) {
            return 1;
        }
        if (c >= 1611 && c <= 1618) {
            return 2;
        }
        if ((c < 1619 || c > 1621) && c != 1648) {
            return (c < 65136 || c > 65151) ? 0 : 3;
        }
        return 3;
    }

    public int shape(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4) throws ArabicShapingException {
        if (cArr == null) {
            throw new IllegalArgumentException("source can not be null");
        }
        if (i < 0 || i2 < 0 || i + i2 > cArr.length) {
            throw new IllegalArgumentException("bad source start (" + i + ") or length (" + i2 + ") for buffer of length " + cArr.length);
        }
        if (cArr2 == null && i4 != 0) {
            throw new IllegalArgumentException("null dest requires destSize == 0");
        }
        if (i4 != 0 && (i3 < 0 || i4 < 0 || i3 + i4 > cArr2.length)) {
            throw new IllegalArgumentException("bad dest start (" + i3 + ") or size (" + i4 + ") for buffer of length " + cArr2.length);
        }
        int i5 = this.options;
        if ((i5 & TASHKEEL_MASK) != 0 && (i5 & TASHKEEL_MASK) != 262144 && (i5 & TASHKEEL_MASK) != 393216 && (i5 & TASHKEEL_MASK) != 524288 && (i5 & TASHKEEL_MASK) != 786432) {
            throw new IllegalArgumentException("Wrong Tashkeel argument");
        }
        int i6 = this.options;
        if ((i6 & 65539) != 0 && (i6 & 65539) != 3 && (i6 & 65539) != 2 && (i6 & 65539) != 0 && (i6 & 65539) != 65536 && (i6 & 65539) != 1) {
            throw new IllegalArgumentException("Wrong Lam Alef argument");
        }
        int i7 = this.options;
        if ((917504 & i7) != 0 && (i7 & 24) == 16) {
            throw new IllegalArgumentException("Tashkeel replacement should not be enabled in deshaping mode ");
        }
        return internalShape(cArr, i, i2, cArr2, i3, i4);
    }

    public void shape(char[] cArr, int i, int i2) throws ArabicShapingException {
        if ((this.options & 65539) == 0) {
            throw new ArabicShapingException("Cannot shape in place with length option resize.");
        }
        shape(cArr, i, i2, cArr, i, i2);
    }

    public String shape(String str) throws ArabicShapingException {
        char[] charArray = str.toCharArray();
        int i = this.options;
        char[] cArr = ((65539 & i) == 0 && (i & 24) == 16) ? new char[charArray.length * 2] : charArray;
        return new String(cArr, 0, shape(charArray, 0, charArray.length, cArr, 0, cArr.length));
    }

    public ArabicShaping(int i) {
        this.options = i;
        if ((i & 224) > 128) {
            throw new IllegalArgumentException("bad DIGITS options");
        }
        this.isLogical = (i & 4) == 0;
        this.spacesRelativeToTextBeginEnd = (i & 67108864) == 67108864;
        if ((i & 134217728) == 134217728) {
            this.tailChar = NEW_TAIL_CHAR;
        } else {
            this.tailChar = OLD_TAIL_CHAR;
        }
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == ArabicShaping.class && this.options == ((ArabicShaping) obj).options;
    }

    public int hashCode() {
        return this.options;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('[');
        int i = this.options & 65539;
        if (i == 0) {
            sb.append("LamAlef resize");
        } else if (i == 1) {
            sb.append("LamAlef spaces at near");
        } else if (i == 2) {
            sb.append("LamAlef spaces at end");
        } else if (i == 3) {
            sb.append("LamAlef spaces at begin");
        } else if (i == 65536) {
            sb.append("lamAlef auto");
        }
        int i2 = this.options & 4;
        if (i2 == 0) {
            sb.append(", logical");
        } else if (i2 == 4) {
            sb.append(", visual");
        }
        int i3 = this.options & 24;
        if (i3 == 0) {
            sb.append(", no letter shaping");
        } else if (i3 == 8) {
            sb.append(", shape letters");
        } else if (i3 == 16) {
            sb.append(", unshape letters");
        } else if (i3 == 24) {
            sb.append(", shape letters tashkeel isolated");
        }
        if ((this.options & SEEN_MASK) == 2097152) {
            sb.append(", Seen at near");
        }
        if ((this.options & YEHHAMZA_MASK) == 16777216) {
            sb.append(", Yeh Hamza at near");
        }
        int i4 = this.options & TASHKEEL_MASK;
        if (i4 == 262144) {
            sb.append(", Tashkeel at begin");
        } else if (i4 == 393216) {
            sb.append(", Tashkeel at end");
        } else if (i4 == 524288) {
            sb.append(", Tashkeel resize");
        } else if (i4 == 786432) {
            sb.append(", Tashkeel replace with tatweel");
        }
        int i5 = this.options & 224;
        if (i5 == 0) {
            sb.append(", no digit shaping");
        } else if (i5 == 32) {
            sb.append(", shape digits to AN");
        } else if (i5 == 64) {
            sb.append(", shape digits to EN");
        } else if (i5 == 96) {
            sb.append(", shape digits to AN contextually: default EN");
        } else if (i5 == 128) {
            sb.append(", shape digits to AN contextually: default AL");
        }
        int i6 = this.options & 256;
        if (i6 == 0) {
            sb.append(", standard Arabic-Indic digits");
        } else if (i6 == 256) {
            sb.append(", extended Arabic-Indic digits");
        }
        sb.append("]");
        return sb.toString();
    }

    private void shapeToArabicDigitsWithContext(char[] cArr, int i, int i2, char c, boolean z) {
        UBiDiProps uBiDiProps = UBiDiProps.INSTANCE;
        char c2 = (char) (c - '0');
        int i3 = i2 + i;
        while (true) {
            i3--;
            if (i3 < i) {
                return;
            }
            char c3 = cArr[i3];
            int i4 = uBiDiProps.getClass(c3);
            if (i4 == 0 || i4 == 1) {
                z = false;
            } else if (i4 != 2) {
                if (i4 == 13) {
                    z = true;
                }
            } else if (z && c3 <= '9') {
                cArr[i3] = (char) (c3 + c2);
            }
        }
    }

    private static void invertBuffer(char[] cArr, int i, int i2) {
        for (int i3 = (i2 + i) - 1; i < i3; i3--) {
            char c = cArr[i];
            cArr[i] = cArr[i3];
            cArr[i3] = c;
            i++;
        }
    }

    private static int getLink(char c) {
        if (c >= 1570 && c <= 1747) {
            return araLink[c - R2.dimen.abc_text_size_caption_material];
        }
        if (c == 8205) {
            return 3;
        }
        if (c >= 8301 && c <= 8303) {
            return 4;
        }
        if (c < 65136 || c > 65276) {
            return 0;
        }
        return presLink[c - 65136];
    }

    private static int countSpacesLeft(char[] cArr, int i, int i2) {
        int i3 = i + i2;
        for (int i4 = i; i4 < i3; i4++) {
            if (cArr[i4] != ' ') {
                return i4 - i;
            }
        }
        return i2;
    }

    private static int countSpacesRight(char[] cArr, int i, int i2) {
        int i3 = i + i2;
        int i4 = i3;
        do {
            i4--;
            if (i4 < i) {
                return i2;
            }
        } while (cArr[i4] == ' ');
        return (i3 - 1) - i4;
    }

    private static int isSeenTailFamilyChar(char c) {
        if (c < 65201 || c >= 65215) {
            return 0;
        }
        return tailFamilyIsolatedFinal[c - 65201];
    }

    private static int isTashkeelOnTatweelChar(char c) {
        if (c < 65136 || c > 65151 || c == 65139 || c == 65141 || c == 65149) {
            return ((c < 64754 || c > 64756) && c != 65149) ? 0 : 2;
        }
        return tashkeelMedial[c - 65136];
    }

    private static int isIsolatedTashkeelChar(char c) {
        if (c < 65136 || c > 65151 || c == 65139 || c == 65141) {
            return (c < 64606 || c > 64611) ? 0 : 1;
        }
        return 1 - tashkeelMedial[c - 65136];
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int calculateSize(char[] r4, int r5, int r6) {
        /*
            r3 = this;
            int r0 = r3.options
            r1 = 24
            r0 = r0 & r1
            r2 = 8
            if (r0 == r2) goto L21
            r2 = 16
            if (r0 == r2) goto L10
            if (r0 == r1) goto L21
            goto L68
        L10:
            int r0 = r5 + r6
        L12:
            if (r5 >= r0) goto L68
            char r1 = r4[r5]
            boolean r1 = isLamAlefChar(r1)
            if (r1 == 0) goto L1e
            int r6 = r6 + 1
        L1e:
            int r5 = r5 + 1
            goto L12
        L21:
            boolean r0 = r3.isLogical
            r1 = 1604(0x644, float:2.248E-42)
            if (r0 == 0) goto L48
            int r0 = r5 + r6
            int r0 = r0 + (-1)
        L2b:
            if (r5 >= r0) goto L68
            char r2 = r4[r5]
            if (r2 != r1) goto L3b
            int r2 = r5 + 1
            char r2 = r4[r2]
            boolean r2 = isAlefChar(r2)
            if (r2 != 0) goto L43
        L3b:
            char r2 = r4[r5]
            boolean r2 = isTashkeelCharFE(r2)
            if (r2 == 0) goto L45
        L43:
            int r6 = r6 + (-1)
        L45:
            int r5 = r5 + 1
            goto L2b
        L48:
            int r0 = r5 + 1
            int r5 = r5 + r6
        L4b:
            if (r0 >= r5) goto L68
            char r2 = r4[r0]
            if (r2 != r1) goto L5b
            int r2 = r0 + (-1)
            char r2 = r4[r2]
            boolean r2 = isAlefChar(r2)
            if (r2 != 0) goto L63
        L5b:
            char r2 = r4[r0]
            boolean r2 = isTashkeelCharFE(r2)
            if (r2 == 0) goto L65
        L63:
            int r6 = r6 + (-1)
        L65:
            int r0 = r0 + 1
            goto L4b
        L68:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.ArabicShaping.calculateSize(char[], int, int):int");
    }

    private static int countSpaceSub(char[] cArr, int i, char c) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (cArr[i3] == c) {
                i2++;
            }
        }
        return i2;
    }

    private static void shiftArray(char[] cArr, int i, int i2, char c) {
        int i3 = i2;
        while (true) {
            i2--;
            if (i2 < i) {
                return;
            }
            char c2 = cArr[i2];
            if (c2 != c && i3 - 1 != i2) {
                cArr[i3] = c2;
            }
        }
    }

    private static int flipArray(char[] cArr, int i, int i2, int i3) {
        if (i3 <= i) {
            return i2;
        }
        while (i3 < i2) {
            cArr[i] = cArr[i3];
            i++;
            i3++;
        }
        return i;
    }

    private static int handleTashkeelWithTatweel(char[] cArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (isTashkeelOnTatweelChar(cArr[i2]) == 1) {
                cArr[i2] = TATWEEL_CHAR;
            } else if (isTashkeelOnTatweelChar(cArr[i2]) == 2) {
                cArr[i2] = SHADDA_TATWEEL_CHAR;
            } else if (isIsolatedTashkeelChar(cArr[i2]) == 1 && cArr[i2] != 65148) {
                cArr[i2] = ' ';
            }
        }
        return i;
    }

    private int handleGeneratedSpaces(char[] cArr, int i, int i2) {
        int iFlipArray;
        int i3 = i;
        int i4 = i2;
        int i5 = this.options;
        int i6 = 65539 & i5;
        int i7 = i5 & TASHKEEL_MASK;
        if ((!this.isLogical) & (!this.spacesRelativeToTextBeginEnd)) {
            if (i6 == 2) {
                i6 = 3;
            } else if (i6 == 3) {
                i6 = 2;
            }
            if (i7 == 262144) {
                i7 = TASHKEEL_END;
            } else if (i7 == 393216) {
                i7 = 262144;
            }
        }
        if (i6 == 1) {
            int i8 = i3 + i4;
            while (i3 < i8) {
                if (cArr[i3] == 65535) {
                    cArr[i3] = ' ';
                }
                i3++;
            }
        } else {
            int i9 = i3 + i4;
            int iCountSpaceSub = countSpaceSub(cArr, i4, (char) 65535);
            int iCountSpaceSub2 = countSpaceSub(cArr, i4, TASHKEEL_SPACE_SUB);
            boolean z = i6 == 2;
            boolean z2 = i7 == 393216;
            if (z && i6 == 2) {
                shiftArray(cArr, i3, i9, (char) 65535);
                while (iCountSpaceSub > i3) {
                    iCountSpaceSub--;
                    cArr[iCountSpaceSub] = ' ';
                }
            }
            if (z2 && i7 == 393216) {
                shiftArray(cArr, i3, i9, TASHKEEL_SPACE_SUB);
                iFlipArray = iCountSpaceSub2;
                while (iFlipArray > i3) {
                    iFlipArray--;
                    cArr[iFlipArray] = ' ';
                }
            } else {
                iFlipArray = iCountSpaceSub2;
            }
            boolean z3 = i6 == 0;
            boolean z4 = i7 == 524288;
            if (z3 && i6 == 0) {
                shiftArray(cArr, i3, i9, (char) 65535);
                iCountSpaceSub = flipArray(cArr, i3, i9, iCountSpaceSub);
                i4 = iCountSpaceSub - i3;
            }
            if (z4 && i7 == 524288) {
                shiftArray(cArr, i3, i9, TASHKEEL_SPACE_SUB);
                iFlipArray = flipArray(cArr, i3, i9, iFlipArray);
                i4 = iFlipArray - i3;
            }
            boolean z5 = i6 == 3 || i6 == 65536;
            boolean z6 = i7 == 262144;
            if (z5 && (i6 == 3 || i6 == 65536)) {
                shiftArray(cArr, i3, i9, (char) 65535);
                for (int iFlipArray2 = flipArray(cArr, i3, i9, iCountSpaceSub); iFlipArray2 < i9; iFlipArray2++) {
                    cArr[iFlipArray2] = ' ';
                }
            }
            if (z6 && i7 == 262144) {
                shiftArray(cArr, i3, i9, TASHKEEL_SPACE_SUB);
                for (int iFlipArray3 = flipArray(cArr, i3, i9, iFlipArray); iFlipArray3 < i9; iFlipArray3++) {
                    cArr[iFlipArray3] = ' ';
                }
            }
        }
        return i4;
    }

    private boolean expandCompositCharAtBegin(char[] cArr, int i, int i2, int i3) {
        if (i3 > countSpacesRight(cArr, i, i2)) {
            return true;
        }
        int i4 = i2 + i;
        int i5 = i4 - i3;
        while (true) {
            i5--;
            if (i5 < i) {
                return false;
            }
            char c = cArr[i5];
            if (isNormalizedLamAlefChar(c)) {
                int i6 = i4 - 1;
                cArr[i6] = LAM_CHAR;
                i4 = i6 - 1;
                cArr[i4] = convertNormalizedLamAlef[c - 1628];
            } else {
                i4--;
                cArr[i4] = c;
            }
        }
    }

    private boolean expandCompositCharAtEnd(char[] cArr, int i, int i2, int i3) {
        if (i3 > countSpacesLeft(cArr, i, i2)) {
            return true;
        }
        int i4 = i2 + i;
        for (int i5 = i3 + i; i5 < i4; i5++) {
            char c = cArr[i5];
            if (isNormalizedLamAlefChar(c)) {
                int i6 = i + 1;
                cArr[i] = convertNormalizedLamAlef[c - 1628];
                i = i6 + 1;
                cArr[i6] = LAM_CHAR;
            } else {
                cArr[i] = c;
                i++;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0048, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean expandCompositCharAtNear(char[] r6, int r7, int r8, int r9, int r10, int r11) {
        /*
            r5 = this;
            char r0 = r6[r7]
            boolean r0 = isNormalizedLamAlefChar(r0)
            r1 = 1
            if (r0 == 0) goto La
            return r1
        La:
            int r8 = r8 + r7
        Lb:
            int r8 = r8 + (-1)
            if (r8 < r7) goto L6a
            char r0 = r6[r8]
            r2 = 32
            if (r11 != r1) goto L33
            boolean r3 = isNormalizedLamAlefChar(r0)
            if (r3 == 0) goto L33
            if (r8 <= r7) goto L32
            int r3 = r8 + (-1)
            char r3 = r6[r3]
            if (r3 != r2) goto L32
            r2 = 1604(0x644, float:2.248E-42)
            r6[r8] = r2
            int r8 = r8 + (-1)
            char[] r2 = com.ibm.icu.text.ArabicShaping.convertNormalizedLamAlef
            int r0 = r0 + (-1628)
            char r0 = r2[r0]
            r6[r8] = r0
            goto Lb
        L32:
            return r1
        L33:
            if (r10 != r1) goto L49
            int r3 = isSeenTailFamilyChar(r0)
            if (r3 != r1) goto L49
            if (r8 <= r7) goto L48
            int r0 = r8 + (-1)
            char r3 = r6[r0]
            if (r3 != r2) goto L48
            char r2 = r5.tailChar
            r6[r0] = r2
            goto Lb
        L48:
            return r1
        L49:
            if (r9 != r1) goto Lb
            boolean r3 = isYehHamzaChar(r0)
            if (r3 == 0) goto Lb
            if (r8 <= r7) goto L69
            int r3 = r8 + (-1)
            char r4 = r6[r3]
            if (r4 != r2) goto L69
            char[] r2 = com.ibm.icu.text.ArabicShaping.yehHamzaToYeh
            r4 = 65161(0xfe89, float:9.131E-41)
            int r0 = r0 - r4
            char r0 = r2[r0]
            r6[r8] = r0
            r0 = 65152(0xfe80, float:9.1297E-41)
            r6[r3] = r0
            goto Lb
        L69:
            return r1
        L6a:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.ArabicShaping.expandCompositCharAtNear(char[], int, int, int, int, int):boolean");
    }

    private int expandCompositChar(char[] cArr, int i, int i2, int i3, int i4) throws ArabicShapingException {
        int i5 = this.options;
        int i6 = 65539 & i5;
        int i7 = 7340032 & i5;
        int i8 = i5 & YEHHAMZA_MASK;
        if (!this.isLogical && !this.spacesRelativeToTextBeginEnd) {
            if (i6 == 2) {
                i6 = 3;
            } else if (i6 == 3) {
                i6 = 2;
            }
        }
        if (i4 != 1) {
            if (i7 == 2097152 && expandCompositCharAtNear(cArr, i, i2, 0, 1, 0)) {
                throw new ArabicShapingException("No space for Seen tail expansion");
            }
            if (i8 == 16777216 && expandCompositCharAtNear(cArr, i, i2, 1, 0, 0)) {
                throw new ArabicShapingException("No space for YehHamza expansion");
            }
            return i2;
        }
        if (i6 == 65536) {
            if (this.isLogical) {
                boolean zExpandCompositCharAtEnd = expandCompositCharAtEnd(cArr, i, i2, i3);
                if (zExpandCompositCharAtEnd) {
                    zExpandCompositCharAtEnd = expandCompositCharAtBegin(cArr, i, i2, i3);
                }
                if (zExpandCompositCharAtEnd) {
                    zExpandCompositCharAtEnd = expandCompositCharAtNear(cArr, i, i2, 0, 0, 1);
                }
                if (zExpandCompositCharAtEnd) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
                return i2;
            }
            boolean zExpandCompositCharAtBegin = expandCompositCharAtBegin(cArr, i, i2, i3);
            if (zExpandCompositCharAtBegin) {
                zExpandCompositCharAtBegin = expandCompositCharAtEnd(cArr, i, i2, i3);
            }
            if (zExpandCompositCharAtBegin) {
                zExpandCompositCharAtBegin = expandCompositCharAtNear(cArr, i, i2, 0, 0, 1);
            }
            if (zExpandCompositCharAtBegin) {
                throw new ArabicShapingException("No spacefor lamalef");
            }
            return i2;
        }
        if (i6 == 2) {
            if (expandCompositCharAtEnd(cArr, i, i2, i3)) {
                throw new ArabicShapingException("No spacefor lamalef");
            }
            return i2;
        }
        if (i6 == 3) {
            if (expandCompositCharAtBegin(cArr, i, i2, i3)) {
                throw new ArabicShapingException("No spacefor lamalef");
            }
            return i2;
        }
        if (i6 == 1) {
            if (expandCompositCharAtNear(cArr, i, i2, 0, 0, 1)) {
                throw new ArabicShapingException("No spacefor lamalef");
            }
            return i2;
        }
        if (i6 != 0) {
            return i2;
        }
        int i9 = i + i2;
        int i10 = i9 + i3;
        while (true) {
            i9--;
            if (i9 < i) {
                return i2 + i3;
            }
            char c = cArr[i9];
            if (isNormalizedLamAlefChar(c)) {
                int i11 = i10 - 1;
                cArr[i11] = LAM_CHAR;
                i10 = i11 - 1;
                cArr[i10] = convertNormalizedLamAlef[c - 1628];
            } else {
                i10--;
                cArr[i10] = c;
            }
        }
    }

    private int normalize(char[] cArr, int i, int i2) {
        int i3 = i2 + i;
        int i4 = 0;
        while (i < i3) {
            char c = cArr[i];
            if (c >= 65136 && c <= 65276) {
                if (isLamAlefChar(c)) {
                    i4++;
                }
                cArr[i] = (char) convertFEto06[c - 65136];
            }
            i++;
        }
        return i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int deshapeNormalize(char[] r10, int r11, int r12) {
        /*
            r9 = this;
            int r0 = r9.options
            r1 = 58720256(0x3800000, float:7.523164E-37)
            r0 = r0 & r1
            r1 = 0
            r2 = 1
            r3 = 16777216(0x1000000, float:2.3509887E-38)
            if (r0 != r3) goto Ld
            r0 = 1
            goto Le
        Ld:
            r0 = 0
        Le:
            int r3 = r9.options
            r4 = 7340032(0x700000, float:1.0285576E-38)
            r3 = r3 & r4
            r4 = 2097152(0x200000, float:2.938736E-39)
            if (r3 != r4) goto L19
            r3 = 1
            goto L1a
        L19:
            r3 = 0
        L1a:
            int r4 = r11 + r12
        L1c:
            if (r11 >= r4) goto L78
            char r5 = r10[r11]
            r6 = 32
            if (r0 != r2) goto L42
            r7 = 1569(0x621, float:2.199E-42)
            if (r5 == r7) goto L2d
            r7 = 65152(0xfe80, float:9.1297E-41)
            if (r5 != r7) goto L42
        L2d:
            int r7 = r12 + (-1)
            if (r11 >= r7) goto L42
            int r7 = r11 + 1
            char r8 = r10[r7]
            boolean r8 = isAlefMaksouraChar(r8)
            if (r8 == 0) goto L42
            r10[r11] = r6
            r5 = 1574(0x626, float:2.206E-42)
            r10[r7] = r5
            goto L75
        L42:
            if (r3 != r2) goto L5b
            boolean r7 = isTailChar(r5)
            if (r7 == 0) goto L5b
            int r7 = r12 + (-1)
            if (r11 >= r7) goto L5b
            int r7 = r11 + 1
            char r7 = r10[r7]
            int r7 = isSeenTailFamilyChar(r7)
            if (r7 != r2) goto L5b
            r10[r11] = r6
            goto L75
        L5b:
            r6 = 65136(0xfe70, float:9.1275E-41)
            if (r5 < r6) goto L75
            r7 = 65276(0xfefc, float:9.1471E-41)
            if (r5 > r7) goto L75
            boolean r7 = isLamAlefChar(r5)
            if (r7 == 0) goto L6d
            int r1 = r1 + 1
        L6d:
            int[] r7 = com.ibm.icu.text.ArabicShaping.convertFEto06
            int r5 = r5 - r6
            r5 = r7[r5]
            char r5 = (char) r5
            r10[r11] = r5
        L75:
            int r11 = r11 + 1
            goto L1c
        L78:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.ArabicShaping.deshapeNormalize(char[], int, int):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int shapeUnicode(char[] r21, int r22, int r23, int r24, int r25) throws com.ibm.icu.text.ArabicShapingException {
        /*
            Method dump skipped, instructions count: 338
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.ArabicShaping.shapeUnicode(char[], int, int, int, int):int");
    }

    private int deShapeUnicode(char[] cArr, int i, int i2, int i3) throws ArabicShapingException {
        int iDeshapeNormalize = deshapeNormalize(cArr, i, i2);
        return iDeshapeNormalize != 0 ? expandCompositChar(cArr, i, i2, iDeshapeNormalize, 1) : i2;
    }

    private int internalShape(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4) throws ArabicShapingException {
        if (i2 == 0) {
            return 0;
        }
        if (i4 == 0) {
            int i5 = this.options;
            return ((i5 & 24) == 0 || (i5 & 65539) != 0) ? i2 : calculateSize(cArr, i, i2);
        }
        char[] cArr3 = new char[i2 * 2];
        System.arraycopy(cArr, i, cArr3, 0, i2);
        if (this.isLogical) {
            invertBuffer(cArr3, 0, i2);
        }
        int i6 = this.options;
        int i7 = i6 & 24;
        if (i7 != 8) {
            if (i7 == 16) {
                i2 = deShapeUnicode(cArr3, 0, i2, i4);
            } else if (i7 == 24) {
                i2 = shapeUnicode(cArr3, 0, i2, i4, 1);
            }
        } else if ((i6 & TASHKEEL_MASK) != 0 && (i6 & TASHKEEL_MASK) != 786432) {
            i2 = shapeUnicode(cArr3, 0, i2, i4, 2);
        } else {
            i2 = (917504 & this.options) == 786432 ? handleTashkeelWithTatweel(cArr3, i2) : shapeUnicode(cArr3, 0, i2, i4, 0);
        }
        if (i2 > i4) {
            throw new ArabicShapingException("not enough room for result data");
        }
        int i8 = this.options;
        if ((i8 & 224) != 0) {
            int i9 = i8 & 256;
            char c = i9 != 0 ? i9 != 256 ? '0' : (char) 1776 : (char) 1632;
            int i10 = this.options & 224;
            if (i10 == 32) {
                int i11 = c - '0';
                for (int i12 = 0; i12 < i2; i12++) {
                    char c2 = cArr3[i12];
                    if (c2 <= '9' && c2 >= '0') {
                        cArr3[i12] = (char) (cArr3[i12] + i11);
                    }
                }
            } else if (i10 == 64) {
                char c3 = (char) (c + '\t');
                int i13 = '0' - c;
                for (int i14 = 0; i14 < i2; i14++) {
                    char c4 = cArr3[i14];
                    if (c4 <= c3 && c4 >= c) {
                        cArr3[i14] = (char) (cArr3[i14] + i13);
                    }
                }
            } else if (i10 == 96) {
                shapeToArabicDigitsWithContext(cArr3, 0, i2, c, false);
            } else if (i10 == 128) {
                shapeToArabicDigitsWithContext(cArr3, 0, i2, c, true);
            }
        }
        if (this.isLogical) {
            invertBuffer(cArr3, 0, i2);
        }
        System.arraycopy(cArr3, 0, cArr2, i3, i2);
        return i2;
    }
}
