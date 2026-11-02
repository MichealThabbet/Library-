package com.google.zxing.client.result;

import androidx.core.net.MailTo;
import com.google.zxing.Result;
import java.util.List;
import net.fortuna.ical4j.model.Property;

/* loaded from: classes2.dex */
public final class VEventResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public CalendarParsedResult parse(Result result) throws NumberFormatException {
        double d;
        String massagedText = getMassagedText(result);
        if (massagedText.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        String strMatchSingleVCardPrefixedField = matchSingleVCardPrefixedField(Property.SUMMARY, massagedText, true);
        String strMatchSingleVCardPrefixedField2 = matchSingleVCardPrefixedField(Property.DTSTART, massagedText, true);
        if (strMatchSingleVCardPrefixedField2 == null) {
            return null;
        }
        String strMatchSingleVCardPrefixedField3 = matchSingleVCardPrefixedField(Property.DTEND, massagedText, true);
        String strMatchSingleVCardPrefixedField4 = matchSingleVCardPrefixedField(Property.DURATION, massagedText, true);
        String strMatchSingleVCardPrefixedField5 = matchSingleVCardPrefixedField("LOCATION", massagedText, true);
        String strStripMailto = stripMailto(matchSingleVCardPrefixedField(Property.ORGANIZER, massagedText, true));
        String[] strArrMatchVCardPrefixedField = matchVCardPrefixedField(Property.ATTENDEE, massagedText, true);
        if (strArrMatchVCardPrefixedField != null) {
            for (int i = 0; i < strArrMatchVCardPrefixedField.length; i++) {
                strArrMatchVCardPrefixedField[i] = stripMailto(strArrMatchVCardPrefixedField[i]);
            }
        }
        String strMatchSingleVCardPrefixedField6 = matchSingleVCardPrefixedField("DESCRIPTION", massagedText, true);
        String strMatchSingleVCardPrefixedField7 = matchSingleVCardPrefixedField(Property.GEO, massagedText, true);
        double d2 = Double.NaN;
        if (strMatchSingleVCardPrefixedField7 == null) {
            d = Double.NaN;
        } else {
            int iIndexOf = strMatchSingleVCardPrefixedField7.indexOf(59);
            if (iIndexOf < 0) {
                return null;
            }
            try {
                d2 = Double.parseDouble(strMatchSingleVCardPrefixedField7.substring(0, iIndexOf));
                d = Double.parseDouble(strMatchSingleVCardPrefixedField7.substring(iIndexOf + 1));
            } catch (NumberFormatException | IllegalArgumentException unused) {
                return null;
            }
        }
        return new CalendarParsedResult(strMatchSingleVCardPrefixedField, strMatchSingleVCardPrefixedField2, strMatchSingleVCardPrefixedField3, strMatchSingleVCardPrefixedField4, strMatchSingleVCardPrefixedField5, strStripMailto, strArrMatchVCardPrefixedField, strMatchSingleVCardPrefixedField6, d2, d);
    }

    private static String matchSingleVCardPrefixedField(CharSequence charSequence, String str, boolean z) {
        List<String> listMatchSingleVCardPrefixedField = VCardResultParser.matchSingleVCardPrefixedField(charSequence, str, z, false);
        if (listMatchSingleVCardPrefixedField == null || listMatchSingleVCardPrefixedField.isEmpty()) {
            return null;
        }
        return listMatchSingleVCardPrefixedField.get(0);
    }

    private static String[] matchVCardPrefixedField(CharSequence charSequence, String str, boolean z) {
        List<List<String>> listMatchVCardPrefixedField = VCardResultParser.matchVCardPrefixedField(charSequence, str, z, false);
        if (listMatchVCardPrefixedField == null || listMatchVCardPrefixedField.isEmpty()) {
            return null;
        }
        int size = listMatchVCardPrefixedField.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = listMatchVCardPrefixedField.get(i).get(0);
        }
        return strArr;
    }

    private static String stripMailto(String str) {
        return str != null ? (str.startsWith(MailTo.MAILTO_SCHEME) || str.startsWith("MAILTO:")) ? str.substring(7) : str : str;
    }
}
