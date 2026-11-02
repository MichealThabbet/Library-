package it.piapostolos.android.pinotes.utils;

import android.content.Context;

/* loaded from: classes3.dex */
public class ResourcesUtils {

    public enum ResourceIdentifiers {
        XML,
        ID,
        ARRAY
    }

    public static int getXmlId(Context context, ResourceIdentifiers resourceIdentifiers, String str) {
        return context.getResources().getIdentifier(str, resourceIdentifiers.name().toLowerCase(), context.getPackageName());
    }
}
