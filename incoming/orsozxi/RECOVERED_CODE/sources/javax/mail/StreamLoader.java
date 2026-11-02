package javax.mail;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: Session.java */
/* loaded from: classes3.dex */
interface StreamLoader {
    void load(InputStream inputStream) throws IOException;
}
