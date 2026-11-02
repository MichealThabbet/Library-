package net.fortuna.ical4j.transform.rfc5545;

import androidx.core.net.MailTo;
import java.net.URI;
import java.net.URISyntaxException;
import net.fortuna.ical4j.model.property.Attendee;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AttendeePropertyRule implements Rfc5545PropertyRule<Attendee> {
    private static final String APOSTROPHE = "'";
    private static final String MAILTO = "mailto";
    private static final int MIN_LENGTH = 3;

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(Attendee attendee) {
        URI calAddress;
        String scheme;
        String schemeSpecificPart;
        if (attendee != null && (calAddress = attendee.getCalAddress()) != null && (scheme = calAddress.getScheme()) != null && StringUtils.startsWithIgnoreCase(scheme, MAILTO) && (schemeSpecificPart = calAddress.getSchemeSpecificPart()) != null && schemeSpecificPart.length() >= 3 && StringUtils.startsWith(schemeSpecificPart, APOSTROPHE) && StringUtils.endsWith(schemeSpecificPart, APOSTROPHE)) {
            safelySetNewValue(attendee, schemeSpecificPart.substring(1, schemeSpecificPart.length() - 1));
        }
    }

    private static void safelySetNewValue(Attendee attendee, String str) {
        try {
            attendee.setValue(MailTo.MAILTO_SCHEME + str);
        } catch (URISyntaxException unused) {
        }
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<Attendee> getSupportedType() {
        return Attendee.class;
    }
}
