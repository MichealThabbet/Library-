package net.fortuna.ical4j.validate.component;

import java.util.function.Consumer;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

/* compiled from: lambda */
/* renamed from: net.fortuna.ical4j.validate.component.-$$Lambda$7-JfRWWrEauuGDNRiuQY32dyh3o, reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$7JfRWWrEauuGDNRiuQY32dyh3o implements Consumer {
    public final /* synthetic */ Validator f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) throws ValidationException {
        this.f$0.validate((VAlarm) obj);
    }
}
