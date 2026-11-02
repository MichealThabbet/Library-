package net.fortuna.ical4j.data;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.component.Available;
import net.fortuna.ical4j.model.component.Daylight;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VAvailability;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.component.VJournal;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.component.VVenue;

/* loaded from: classes3.dex */
class DefaultComponentFactorySupplier implements Supplier<List<ComponentFactory>> {
    DefaultComponentFactorySupplier() {
    }

    @Override // java.util.function.Supplier
    public List<ComponentFactory> get() {
        return Arrays.asList(new Available.Factory(), new Daylight.Factory(), new Standard.Factory(), new VAlarm.Factory(), new VAvailability.Factory(), new VEvent.Factory(), new VFreeBusy.Factory(), new VJournal.Factory(), new VTimeZone.Factory(), new VToDo.Factory(), new VVenue.Factory());
    }
}
