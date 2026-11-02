package com.google.zxing.oned;

import com.google.zxing.client.result.ExpandedProductParsedResult;
import it.piapostolos.android.pinotes.R2;
import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.Parameter;

/* loaded from: classes2.dex */
final class EANManufacturerOrgSupport {
    private final List<int[]> ranges = new ArrayList();
    private final List<String> countryIdentifiers = new ArrayList();

    EANManufacturerOrgSupport() {
    }

    String lookupCountryIdentifier(String str) throws NumberFormatException {
        int[] iArr;
        int i;
        initIfNeeded();
        int i2 = Integer.parseInt(str.substring(0, 3));
        int size = this.ranges.size();
        for (int i3 = 0; i3 < size && i2 >= (i = (iArr = this.ranges.get(i3))[0]); i3++) {
            if (iArr.length != 1) {
                i = iArr[1];
            }
            if (i2 <= i) {
                return this.countryIdentifiers.get(i3);
            }
        }
        return null;
    }

    private void add(int[] iArr, String str) {
        this.ranges.add(iArr);
        this.countryIdentifiers.add(str);
    }

    private synchronized void initIfNeeded() {
        if (this.ranges.isEmpty()) {
            add(new int[]{0, 19}, "US/CA");
            add(new int[]{30, 39}, "US");
            add(new int[]{60, 139}, "US/CA");
            add(new int[]{300, R2.attr.disableDependentsState}, "FR");
            add(new int[]{R2.attr.displayOptions}, "BG");
            add(new int[]{383}, "SI");
            add(new int[]{R2.attr.dragDirection}, "HR");
            add(new int[]{R2.attr.dragThreshold}, "BA");
            add(new int[]{400, R2.attr.extendMotionSpec}, "DE");
            add(new int[]{R2.attr.fab_addButtonColorPressed, R2.attr.fab_labelStyle}, "JP");
            add(new int[]{R2.attr.fab_labelsPosition, R2.attr.fastScrollVerticalTrackDrawable}, "RU");
            add(new int[]{R2.attr.floatingActionButtonStyle}, "TW");
            add(new int[]{R2.attr.flow_firstVerticalBias}, "EE");
            add(new int[]{R2.attr.flow_firstVerticalStyle}, "LV");
            add(new int[]{R2.attr.flow_horizontalAlign}, "AZ");
            add(new int[]{R2.attr.flow_horizontalBias}, "LT");
            add(new int[]{R2.attr.flow_horizontalGap}, "UZ");
            add(new int[]{R2.attr.flow_horizontalStyle}, "LK");
            add(new int[]{R2.attr.flow_lastHorizontalBias}, "PH");
            add(new int[]{R2.attr.flow_lastHorizontalStyle}, "BY");
            add(new int[]{R2.attr.flow_lastVerticalBias}, "UA");
            add(new int[]{R2.attr.flow_maxElementsWrap}, "MD");
            add(new int[]{R2.attr.flow_padding}, "AM");
            add(new int[]{R2.attr.flow_verticalAlign}, "GE");
            add(new int[]{R2.attr.flow_verticalBias}, "KZ");
            add(new int[]{R2.attr.flow_verticalStyle}, "HK");
            add(new int[]{R2.attr.flow_wrapMode, R2.attr.fontStyle}, "JP");
            add(new int[]{500, R2.attr.haloRadius}, "GB");
            add(new int[]{R2.attr.hintAnimationEnabled}, "GR");
            add(new int[]{R2.attr.icon}, ExpandedProductParsedResult.POUND);
            add(new int[]{R2.attr.iconEndPadding}, "CY");
            add(new int[]{R2.attr.iconPadding}, "MK");
            add(new int[]{R2.attr.iconTint}, "MT");
            add(new int[]{R2.attr.indeterminateAnimationType}, "IE");
            add(new int[]{R2.attr.indeterminateProgressStyle, R2.attr.isLightTheme}, "BE/LU");
            add(new int[]{R2.attr.itemPadding}, "PT");
            add(new int[]{R2.attr.itemSpacing}, "IS");
            add(new int[]{R2.attr.itemStrokeColor, R2.attr.keylines}, "DK");
            add(new int[]{R2.attr.layout_behavior}, "PL");
            add(new int[]{R2.attr.layout_columnSpan}, "RO");
            add(new int[]{R2.attr.layout_constraintBaseline_toBaselineOf}, "HU");
            add(new int[]{R2.attr.layout_constraintBottom_creator, R2.attr.layout_constraintBottom_toBottomOf}, "ZA");
            add(new int[]{R2.attr.layout_constraintCircle}, "GH");
            add(new int[]{R2.attr.layout_constraintEnd_toStartOf}, "BH");
            add(new int[]{R2.attr.layout_constraintGuide_begin}, "MU");
            add(new int[]{R2.attr.layout_constraintGuide_percent}, "MA");
            add(new int[]{R2.attr.layout_constraintHeight_max}, "DZ");
            add(new int[]{R2.attr.layout_constraintHorizontal_bias}, "KE");
            add(new int[]{R2.attr.layout_constraintHorizontal_weight}, "CI");
            add(new int[]{R2.attr.layout_constraintLeft_creator}, "TN");
            add(new int[]{R2.attr.layout_constraintLeft_toRightOf}, "SY");
            add(new int[]{R2.attr.layout_constraintRight_creator}, "EG");
            add(new int[]{R2.attr.layout_constraintRight_toRightOf}, "LY");
            add(new int[]{R2.attr.layout_constraintStart_toEndOf}, "JO");
            add(new int[]{R2.attr.layout_constraintStart_toStartOf}, "IR");
            add(new int[]{R2.attr.layout_constraintTag}, "KW");
            add(new int[]{R2.attr.layout_constraintTop_creator}, "SA");
            add(new int[]{R2.attr.layout_constraintTop_toBottomOf}, "AE");
            add(new int[]{R2.attr.layout_editor_absoluteY, R2.attr.layout_keyline}, "FI");
            add(new int[]{R2.attr.materialAlertDialogTheme, R2.attr.materialButtonStyle}, Parameter.CN);
            add(new int[]{R2.attr.materialCalendarHeaderConfirmButton, R2.attr.materialCalendarTheme}, "NO");
            add(new int[]{R2.attr.md_btn_positive_selector}, "IL");
            add(new int[]{R2.attr.md_btn_ripple_color, R2.attr.md_icon}, "SE");
            add(new int[]{R2.attr.md_icon_limit_icon_to_default_size}, "GT");
            add(new int[]{R2.attr.md_icon_max_size}, "SV");
            add(new int[]{R2.attr.md_item_color}, "HN");
            add(new int[]{R2.attr.md_items_gravity}, "NI");
            add(new int[]{R2.attr.md_link_color}, "CR");
            add(new int[]{R2.attr.md_list_selector}, "PA");
            add(new int[]{R2.attr.md_medium_font}, "DO");
            add(new int[]{R2.attr.md_reduce_padding_no_title_no_buttons}, "MX");
            add(new int[]{R2.attr.md_widget_color, R2.attr.measureWithLargestChild}, "CA");
            add(new int[]{R2.attr.middleBarArrowSize}, "VE");
            add(new int[]{R2.attr.min, R2.attr.mock_labelColor}, "CH");
            add(new int[]{R2.attr.mock_showDiagonals}, "CO");
            add(new int[]{R2.attr.motionInterpolator}, "UY");
            add(new int[]{R2.attr.motionProgress}, "PE");
            add(new int[]{R2.attr.motionTarget}, "BO");
            add(new int[]{R2.attr.motion_triggerOnCollision}, "AR");
            add(new int[]{R2.attr.moveWhenScrollAtTop}, "CL");
            add(new int[]{R2.attr.mpb_progressBackgroundTint}, "PY");
            add(new int[]{R2.attr.mpb_progressBackgroundTintMode}, "PE");
            add(new int[]{R2.attr.mpb_progressStyle}, "EC");
            add(new int[]{R2.attr.mpb_secondaryProgressTint, R2.attr.mpb_secondaryProgressTintMode}, "BR");
            add(new int[]{R2.attr.negativeButtonText, R2.attr.pivotAnchor}, "IT");
            add(new int[]{R2.attr.placeholderText, R2.attr.positiveButtonText}, "ES");
            add(new int[]{R2.attr.preferenceCategoryStyle}, "CU");
            add(new int[]{R2.attr.preferenceTheme}, "SK");
            add(new int[]{R2.attr.prefixText}, "CZ");
            add(new int[]{R2.attr.prefixTextAppearance}, "YU");
            add(new int[]{R2.attr.progressBarStyle}, "MN");
            add(new int[]{R2.attr.queryBackground}, "KP");
            add(new int[]{R2.attr.queryHint, R2.attr.radioButtonStyle}, "TR");
            add(new int[]{R2.attr.rangeFillColor, R2.attr.reverseLayout}, "NL");
            add(new int[]{R2.attr.rippleColor}, "KR");
            add(new int[]{R2.attr.rowLayoutId}, "TH");
            add(new int[]{R2.attr.scrimAnimationDuration}, "SG");
            add(new int[]{R2.attr.scrimVisibleHeightTrigger}, "IN");
            add(new int[]{R2.attr.searchViewStyle}, "VN");
            add(new int[]{896}, "PK");
            add(new int[]{R2.attr.selectableItemBackgroundBorderless}, "ID");
            add(new int[]{R2.attr.selectionRequired, R2.attr.singleLine}, "AT");
            add(new int[]{R2.attr.spButtonLayoutStyle, R2.attr.spCalendarTextColor}, "AU");
            add(new int[]{R2.attr.spCancelActionDrawable, 949}, "AZ");
            add(new int[]{R2.attr.spLandscapeButtonModeSwitcherButtonStyle}, "MY");
            add(new int[]{R2.attr.spMaxDate}, "MO");
        }
    }
}
