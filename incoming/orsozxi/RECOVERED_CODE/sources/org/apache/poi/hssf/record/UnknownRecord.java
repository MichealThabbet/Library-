package org.apache.poi.hssf.record;

import it.orsozoxi.android.orsonotes.R2;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class UnknownRecord extends StandardRecord {
    public static final int BITMAP_00E9 = 233;
    public static final int CODENAME_1BA = 442;
    public static final int HEADER_FOOTER_089C = 2204;
    public static final int LABELRANGES_015F = 351;
    public static final int PHONETICPR_00EF = 239;
    public static final int PLS_004D = 77;
    public static final int PRINTSIZE_0033 = 51;
    public static final int QUICKTIP_0800 = 2048;
    public static final int SCL_00A0 = 160;
    public static final int SHEETEXT_0862 = 2146;
    public static final int SHEETPROTECTION_0867 = 2151;
    public static final int SHEETPR_0081 = 129;
    public static final int SORT_0090 = 144;
    public static final int STANDARDWIDTH_0099 = 153;
    private byte[] _rawData;
    private int _sid;

    private static boolean isObservedButUnknown(int i) {
        if (i == 51 || i == 52 || i == 2205 || i == 2206 || i == 4102 || i == 4103 || i == 4170 || i == 4171) {
            return true;
        }
        switch (i) {
            case R2.attr.fabCradleRoundedCornerRadius /* 445 */:
            case R2.attr.fab_addButtonColorPressed /* 450 */:
            case R2.drawable.design_password_eye /* 2215 */:
            case 4097:
            case 4116:
            case R2.style.Base_V21_Theme_AppCompat_Light /* 4125 */:
            case R2.style.Base_V21_Theme_AppCompat_Light_Dialog /* 4126 */:
            case R2.style.Base_V21_Theme_MaterialComponents /* 4127 */:
            case R2.style.Base_V21_Theme_MaterialComponents_Dialog /* 4128 */:
            case R2.style.Base_V21_Theme_MaterialComponents_Light /* 4129 */:
            case R2.style.Base_V21_Theme_MaterialComponents_Light_Dialog /* 4130 */:
            case R2.style.Base_Widget_AppCompat_ActionButton_CloseMode /* 4154 */:
            case R2.style.Base_Widget_AppCompat_ButtonBar_AlertDialog /* 4161 */:
            case R2.style.Base_Widget_AppCompat_ImageButton /* 4174 */:
            case R2.style.Base_Widget_AppCompat_Light_ActionBar /* 4175 */:
            case R2.style.Base_Widget_AppCompat_Light_ActionBar_TabBar /* 4177 */:
            case R2.style.Base_Widget_AppCompat_PopupMenu /* 4188 */:
            case R2.style.Base_Widget_AppCompat_PopupMenu_Overflow /* 4189 */:
            case R2.style.Base_Widget_AppCompat_ProgressBar /* 4191 */:
            case R2.style.Base_Widget_AppCompat_ProgressBar_Horizontal /* 4192 */:
            case R2.style.Base_Widget_AppCompat_RatingBar_Indicator /* 4194 */:
            case R2.style.Base_Widget_AppCompat_RatingBar_Small /* 4195 */:
            case R2.style.Base_Widget_AppCompat_SearchView /* 4196 */:
            case R2.style.Base_Widget_AppCompat_SearchView_ActionBar /* 4197 */:
            case R2.style.Base_Widget_AppCompat_SeekBar /* 4198 */:
                return true;
            default:
                switch (i) {
                    case 4105:
                    case 4106:
                    case 4107:
                    case 4108:
                        return true;
                    default:
                        switch (i) {
                            case 4119:
                            case 4120:
                            case 4121:
                            case R2.style.Base_V21_ThemeOverlay_AppCompat_Dialog /* 4122 */:
                            case R2.style.Base_V21_Theme_AppCompat /* 4123 */:
                                return true;
                            default:
                                switch (i) {
                                    case R2.style.Base_V22_Theme_AppCompat_Light /* 4132 */:
                                    case R2.style.Base_V23_Theme_AppCompat /* 4133 */:
                                    case R2.style.Base_V23_Theme_AppCompat_Light /* 4134 */:
                                    case R2.style.Base_V26_Theme_AppCompat /* 4135 */:
                                        return true;
                                    default:
                                        switch (i) {
                                            case R2.style.Base_V7_Widget_AppCompat_EditText /* 4146 */:
                                            case R2.style.Base_V7_Widget_AppCompat_Toolbar /* 4147 */:
                                            case R2.style.Base_Widget_AppCompat_ActionBar /* 4148 */:
                                            case R2.style.Base_Widget_AppCompat_ActionBar_Solid /* 4149 */:
                                                return true;
                                            default:
                                                switch (i) {
                                                    case R2.style.Base_Widget_AppCompat_Button_Borderless_Colored /* 4163 */:
                                                    case R2.style.Base_Widget_AppCompat_Button_ButtonBar_AlertDialog /* 4164 */:
                                                    case R2.style.Base_Widget_AppCompat_Button_Colored /* 4165 */:
                                                    case R2.style.Base_Widget_AppCompat_Button_Small /* 4166 */:
                                                        return true;
                                                    default:
                                                        return false;
                                                }
                                        }
                                }
                        }
                }
        }
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        return this;
    }

    public UnknownRecord(int i, byte[] bArr) {
        this._sid = i & 65535;
        this._rawData = bArr;
    }

    public UnknownRecord(RecordInputStream recordInputStream) {
        this._sid = recordInputStream.getSid();
        this._rawData = recordInputStream.readRemainder();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this._rawData);
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return this._rawData.length;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        String biffName = getBiffName(this._sid);
        if (biffName == null) {
            biffName = "UNKNOWNRECORD";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append(biffName);
        stringBuffer.append("] (0x");
        stringBuffer.append(Integer.toHexString(this._sid).toUpperCase() + ")\n");
        if (this._rawData.length > 0) {
            stringBuffer.append("  rawData=");
            stringBuffer.append(HexDump.toHex(this._rawData));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/");
        stringBuffer.append(biffName);
        stringBuffer.append("]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) this._sid;
    }

    public static String getBiffName(int i) {
        switch (i) {
            case 51:
                return "PRINTSIZE";
            case 77:
                return "PLS";
            case 80:
                return "DCON";
            case 127:
                return "IMDATA";
            case 129:
                return "SHEETPR";
            case 144:
                return "SORT";
            case 148:
                return "LHRECORD";
            case 153:
                return "STANDARDWIDTH";
            case 160:
                return "SCL";
            case 174:
                return "SCENMAN";
            case 178:
                return "SXVI";
            case 180:
                return "SXIVD";
            case 181:
                return "SXLI";
            case 211:
                return "OBPROJ";
            case 220:
                return "PARAMQRY";
            case 222:
                return "OLESIZE";
            case 233:
                return "BITMAP";
            case 239:
                return "PHONETICPR";
            case 241:
                return "SXEX";
            case 351:
                return "LABELRANGES";
            case R2.attr.errorIconDrawable /* 425 */:
                return "USERBVIEW";
            case R2.attr.errorTextColor /* 429 */:
                return "QSI";
            case 442:
                return "CODENAME";
            case R2.attr.fabSize /* 448 */:
                return "EXCEL9FILE";
            case 2048:
                return "QUICKTIP";
            case R2.dimen.sp_timepicker_radial_picker_dimen /* 2050 */:
                return "QSISXTAG";
            case R2.dimen.sp_timepicker_radial_picker_horizontal_margin /* 2051 */:
                return "DBQUERYEXT";
            case R2.dimen.sp_timepicker_selector_dot_radius /* 2053 */:
                return "TXTQUERY";
            case R2.dimen.subtitle_outline_width /* 2064 */:
                return "SXVIEWEX9";
            case R2.dimen.subtitle_shadow_radius /* 2066 */:
                return "CONTINUEFRT";
            case 2146:
                return "SHEETEXT";
            case R2.drawable.abc_list_longpressed_holo /* 2147 */:
                return "BOOKEXT";
            case R2.drawable.abc_list_pressed_holo_dark /* 2148 */:
                return "SXADDL";
            case 2151:
                return "SHEETPROTECTION";
            case R2.drawable.abc_list_selector_holo_light /* 2155 */:
                return "DATALABEXTCONTENTS";
            case R2.drawable.abc_menu_hardkey_panel_mtrl_mult /* 2156 */:
                return "CELLWATCH";
            case R2.drawable.abc_scrubber_control_to_pressed_mtrl_005 /* 2164 */:
                return "DROPDOWNOBJIDS";
            case R2.drawable.abc_scrubber_track_mtrl_alpha /* 2166 */:
                return "DCONN";
            case R2.drawable.abc_spinner_textfield_background_material /* 2171 */:
                return "CFEX";
            case R2.drawable.abc_switch_thumb_material /* 2172 */:
                return "XFCRC";
            case R2.drawable.abc_switch_track_mtrl_alpha /* 2173 */:
                return "XFEXT";
            case R2.drawable.abc_tab_indicator_mtrl_alpha /* 2175 */:
                return "CONTINUEFRT12";
            case R2.drawable.abc_textfield_default_mtrl_alpha /* 2187 */:
                return "PLV";
            case R2.drawable.abc_textfield_search_activated_mtrl_alpha /* 2188 */:
                return "COMPAT12";
            case R2.drawable.abc_textfield_search_default_mtrl_alpha /* 2189 */:
                return "DXF";
            case R2.drawable.avd_show_password /* 2194 */:
                return "STYLEEXT";
            case R2.drawable.branded_background /* 2198 */:
                return "THEME";
            case R2.drawable.btn_checkbox_checked_mtrl /* 2199 */:
                return "GUIDTYPELIB";
            case R2.drawable.btn_checkbox_unchecked_to_checked_mtrl_animation /* 2202 */:
                return "MTRSETTINGS";
            case R2.drawable.btn_radio_off_mtrl /* 2203 */:
                return "COMPRESSPICTURES";
            case 2204:
                return "HEADERFOOTER";
            case R2.drawable.checkmark_medium_ff /* 2209 */:
                return "SHAPEPROPSSTREAM";
            case R2.drawable.design_bottom_navigation_item_background /* 2211 */:
                return "FORCEFULLCALCULATION";
            case R2.drawable.design_fab_background /* 2212 */:
                return "SHAPEPROPSSTREAM";
            case R2.drawable.design_ic_visibility /* 2213 */:
                return "TEXTPROPSSTREAM";
            case R2.drawable.design_ic_visibility_off /* 2214 */:
                return "RICHTEXTSTREAM";
            case R2.drawable.ic_clock_outline_black_24dp /* 2248 */:
                return "PLV{Mac Excel}";
            default:
                if (!isObservedButUnknown(i)) {
                    return null;
                }
                return "UNKNOWN-" + Integer.toHexString(i).toUpperCase();
        }
    }
}
