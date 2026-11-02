package org.apache.commons.codec.language;

import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import java.util.Locale;
import net.fortuna.ical4j.model.property.RequestStatus;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes3.dex */
public class Caverphone2 extends AbstractCaverphone {
    private static final String TEN_1 = "1111111111";

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        if (str == null || str.length() == 0) {
            return TEN_1;
        }
        return (str.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z]", "").replaceAll("e$", "").replaceAll("^cough", "cou2f").replaceAll("^rough", "rou2f").replaceAll("^tough", "tou2f").replaceAll("^enough", "enou2f").replaceAll("^trough", "trou2f").replaceAll("^gn", "2n").replaceAll("mb$", "m2").replaceAll("cq", "2q").replaceAll("ci", "si").replaceAll("ce", "se").replaceAll("cy", "sy").replaceAll("tch", "2ch").replaceAll("c", "k").replaceAll("q", "k").replaceAll(LanguageTag.PRIVATEUSE, "k").replaceAll(DateFormat.ABBR_GENERIC_TZ, "f").replaceAll("dg", "2g").replaceAll("tio", "sio").replaceAll("tia", "sia").replaceAll(DateFormat.DAY, "t").replaceAll("ph", "fh").replaceAll("b", "p").replaceAll("sh", "s2").replaceAll(DateFormat.ABBR_SPECIFIC_TZ, DateFormat.SECOND).replaceAll("^[aeiou]", "A").replaceAll("[aeiou]", RequestStatus.CLIENT_ERROR).replaceAll(DateFormat.HOUR, DateFormat.YEAR).replaceAll("^y3", "Y3").replaceAll("^y", "A").replaceAll(DateFormat.YEAR, RequestStatus.CLIENT_ERROR).replaceAll("3gh3", "3kh3").replaceAll("gh", "22").replaceAll("g", "k").replaceAll("s+", "S").replaceAll("t+", "T").replaceAll("p+", "P").replaceAll("k+", "K").replaceAll("f+", "F").replaceAll("m+", DateFormat.NUM_MONTH).replaceAll("n+", "N").replaceAll("w3", "W3").replaceAll("wh3", "Wh3").replaceAll("w$", RequestStatus.CLIENT_ERROR).replaceAll("w", RequestStatus.SUCCESS).replaceAll("^h", "A").replaceAll("h", RequestStatus.SUCCESS).replaceAll("r3", "R3").replaceAll("r$", RequestStatus.CLIENT_ERROR).replaceAll(InternalZipConstants.READ_MODE, RequestStatus.SUCCESS).replaceAll("l3", "L3").replaceAll("l$", RequestStatus.CLIENT_ERROR).replaceAll("l", RequestStatus.SUCCESS).replaceAll(RequestStatus.SUCCESS, "").replaceAll("3$", "A").replaceAll(RequestStatus.CLIENT_ERROR, "") + TEN_1).substring(0, 10);
    }
}
