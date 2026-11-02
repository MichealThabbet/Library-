package net.fortuna.ical4j.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class CalendarOutputter extends AbstractOutputter {
    public CalendarOutputter() {
    }

    public CalendarOutputter(boolean z) {
        super(z);
    }

    public CalendarOutputter(boolean z, int i) {
        super(z, i);
    }

    public final void output(Calendar calendar, OutputStream outputStream) throws IOException, ValidationException {
        output(calendar, new OutputStreamWriter(outputStream, DEFAULT_CHARSET));
    }

    public final void output(Calendar calendar, Writer writer) throws IOException, ValidationException {
        if (isValidating()) {
            calendar.validate();
        }
        FoldingWriter foldingWriter = new FoldingWriter(writer, this.foldLength);
        try {
            foldingWriter.write(calendar.toString());
            foldingWriter.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    foldingWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }
}
