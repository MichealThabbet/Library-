package net.fortuna.ical4j.transform;

import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.util.UidGenerator;

/* loaded from: classes3.dex */
public class RefreshTransformer extends AbstractMethodTransformer {
    public RefreshTransformer(UidGenerator uidGenerator) {
        super(Method.REFRESH, uidGenerator, true, false);
    }
}
