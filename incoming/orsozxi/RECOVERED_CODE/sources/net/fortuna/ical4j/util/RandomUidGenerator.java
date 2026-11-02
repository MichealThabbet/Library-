package net.fortuna.ical4j.util;

import java.util.UUID;
import net.fortuna.ical4j.model.property.Uid;

/* loaded from: classes3.dex */
public class RandomUidGenerator implements UidGenerator {
    @Override // net.fortuna.ical4j.util.UidGenerator
    public Uid generateUid() {
        return new Uid(UUID.randomUUID().toString());
    }
}
