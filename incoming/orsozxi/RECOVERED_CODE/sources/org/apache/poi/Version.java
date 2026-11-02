package org.apache.poi;

/* loaded from: classes3.dex */
public class Version {
    private static final String RELEASE_DATE = "20101029";
    private static final String VERSION_STRING = "3.7";

    public static String getImplementationLanguage() {
        return "Java";
    }

    public static String getProduct() {
        return "POI";
    }

    public static String getReleaseDate() {
        return RELEASE_DATE;
    }

    public static String getVersion() {
        return VERSION_STRING;
    }

    public static void main(String[] strArr) {
        System.out.println("Apache " + getProduct() + " " + getVersion() + " (" + getReleaseDate() + ")");
    }
}
