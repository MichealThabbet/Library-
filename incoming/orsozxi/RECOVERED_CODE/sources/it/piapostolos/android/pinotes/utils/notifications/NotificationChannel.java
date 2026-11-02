package it.piapostolos.android.pinotes.utils.notifications;

/* loaded from: classes3.dex */
public class NotificationChannel {
    String description;
    String id;
    int importance;
    String name;

    NotificationChannel(int i, String str, String str2, String str3) {
        this.importance = i;
        this.name = str;
        this.description = str2;
        this.id = str3;
    }
}
