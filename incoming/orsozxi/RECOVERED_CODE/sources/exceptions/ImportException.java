package exceptions;

import it.feio.android.springpadimporter.models.SpringpadElement;

/* loaded from: classes2.dex */
public class ImportException extends Exception {
    private static final long serialVersionUID = 1224449299267954878L;
    private SpringpadElement springpadElement;

    public ImportException(String str) {
        this(str, null);
    }

    public ImportException(String str, Throwable th) {
        super(str, th);
        th.getMessage();
    }

    public SpringpadElement getSpringpadElement() {
        return this.springpadElement;
    }

    public void setSpringpadElement(SpringpadElement springpadElement) {
        this.springpadElement = springpadElement;
    }
}
