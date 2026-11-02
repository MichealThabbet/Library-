package com.google.android.apps.dashclock.api;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ExtensionData implements Parcelable {
    public static final Parcelable.Creator<ExtensionData> CREATOR = new Parcelable.Creator<ExtensionData>() { // from class: com.google.android.apps.dashclock.api.ExtensionData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtensionData createFromParcel(Parcel parcel) {
            return new ExtensionData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtensionData[] newArray(int i) {
            return new ExtensionData[i];
        }
    };
    private static final String KEY_CLICK_INTENT = "click_intent";
    private static final String KEY_CONTENT_DESCRIPTION = "content_description";
    private static final String KEY_EXPANDED_BODY = "body";
    private static final String KEY_EXPANDED_TITLE = "title";
    private static final String KEY_ICON = "icon";
    private static final String KEY_ICON_URI = "icon_uri";
    private static final String KEY_STATUS = "status";
    private static final String KEY_VISIBLE = "visible";
    public static final int MAX_CONTENT_DESCRIPTION_LENGTH = 1164;
    public static final int MAX_EXPANDED_BODY_LENGTH = 1000;
    public static final int MAX_EXPANDED_TITLE_LENGTH = 100;
    public static final int MAX_STATUS_LENGTH = 32;
    public static final int PARCELABLE_VERSION = 2;
    private Intent mClickIntent;
    private String mContentDescription;
    private String mExpandedBody;
    private String mExpandedTitle;
    private int mIcon;
    private Uri mIconUri;
    private String mStatus;
    private boolean mVisible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExtensionData() {
        this.mVisible = false;
        this.mIcon = 0;
        this.mIconUri = null;
        this.mStatus = null;
        this.mExpandedTitle = null;
        this.mExpandedBody = null;
        this.mClickIntent = null;
        this.mContentDescription = null;
    }

    public boolean visible() {
        return this.mVisible;
    }

    public ExtensionData visible(boolean z) {
        this.mVisible = z;
        return this;
    }

    public int icon() {
        return this.mIcon;
    }

    public ExtensionData icon(int i) {
        this.mIcon = i;
        return this;
    }

    public Uri iconUri() {
        return this.mIconUri;
    }

    public ExtensionData iconUri(Uri uri) {
        this.mIconUri = uri;
        return this;
    }

    public String status() {
        return this.mStatus;
    }

    public ExtensionData status(String str) {
        this.mStatus = str;
        return this;
    }

    public String expandedTitle() {
        return this.mExpandedTitle;
    }

    public ExtensionData expandedTitle(String str) {
        this.mExpandedTitle = str;
        return this;
    }

    public String expandedBody() {
        return this.mExpandedBody;
    }

    public ExtensionData expandedBody(String str) {
        this.mExpandedBody = str;
        return this;
    }

    public Intent clickIntent() {
        return this.mClickIntent;
    }

    public ExtensionData clickIntent(Intent intent) {
        this.mClickIntent = intent;
        return this;
    }

    public String contentDescription() {
        return this.mContentDescription;
    }

    public ExtensionData contentDescription(String str) {
        this.mContentDescription = str;
        return this;
    }

    public JSONObject serialize() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(KEY_VISIBLE, this.mVisible);
        jSONObject.put("icon", this.mIcon);
        Uri uri = this.mIconUri;
        jSONObject.put(KEY_ICON_URI, uri == null ? null : uri.toString());
        jSONObject.put("status", this.mStatus);
        jSONObject.put("title", this.mExpandedTitle);
        jSONObject.put(KEY_EXPANDED_BODY, this.mExpandedBody);
        Intent intent = this.mClickIntent;
        jSONObject.put(KEY_CLICK_INTENT, intent != null ? intent.toUri(0) : null);
        jSONObject.put(KEY_CONTENT_DESCRIPTION, this.mContentDescription);
        return jSONObject;
    }

    public void deserialize(JSONObject jSONObject) throws JSONException {
        this.mVisible = jSONObject.optBoolean(KEY_VISIBLE);
        this.mIcon = jSONObject.optInt("icon");
        String strOptString = jSONObject.optString(KEY_ICON_URI);
        this.mIconUri = TextUtils.isEmpty(strOptString) ? null : Uri.parse(strOptString);
        this.mStatus = jSONObject.optString("status");
        this.mExpandedTitle = jSONObject.optString("title");
        this.mExpandedBody = jSONObject.optString(KEY_EXPANDED_BODY);
        try {
            this.mClickIntent = Intent.parseUri(jSONObject.optString(KEY_CLICK_INTENT), 0);
        } catch (URISyntaxException unused) {
        }
        this.mContentDescription = jSONObject.optString(KEY_CONTENT_DESCRIPTION);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_VISIBLE, this.mVisible);
        bundle.putInt("icon", this.mIcon);
        Uri uri = this.mIconUri;
        bundle.putString(KEY_ICON_URI, uri == null ? null : uri.toString());
        bundle.putString("status", this.mStatus);
        bundle.putString("title", this.mExpandedTitle);
        bundle.putString(KEY_EXPANDED_BODY, this.mExpandedBody);
        Intent intent = this.mClickIntent;
        bundle.putString(KEY_CLICK_INTENT, intent != null ? intent.toUri(0) : null);
        bundle.putString(KEY_CONTENT_DESCRIPTION, this.mContentDescription);
        return bundle;
    }

    public void fromBundle(Bundle bundle) {
        this.mVisible = bundle.getBoolean(KEY_VISIBLE, true);
        this.mIcon = bundle.getInt("icon");
        String string = bundle.getString(KEY_ICON_URI);
        this.mIconUri = TextUtils.isEmpty(string) ? null : Uri.parse(string);
        this.mStatus = bundle.getString("status");
        this.mExpandedTitle = bundle.getString("title");
        this.mExpandedBody = bundle.getString(KEY_EXPANDED_BODY);
        try {
            this.mClickIntent = Intent.parseUri(bundle.getString(KEY_CLICK_INTENT), 0);
        } catch (URISyntaxException unused) {
        }
        this.mContentDescription = bundle.getString(KEY_CONTENT_DESCRIPTION);
    }

    private ExtensionData(Parcel parcel) {
        this.mVisible = false;
        this.mIcon = 0;
        this.mIconUri = null;
        this.mStatus = null;
        this.mExpandedTitle = null;
        this.mExpandedBody = null;
        this.mClickIntent = null;
        this.mContentDescription = null;
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        int iDataPosition = parcel.dataPosition();
        if (i >= 1) {
            this.mVisible = parcel.readInt() != 0;
            this.mIcon = parcel.readInt();
            String string = parcel.readString();
            this.mStatus = string;
            if (TextUtils.isEmpty(string)) {
                this.mStatus = null;
            }
            String string2 = parcel.readString();
            this.mExpandedTitle = string2;
            if (TextUtils.isEmpty(string2)) {
                this.mExpandedTitle = null;
            }
            String string3 = parcel.readString();
            this.mExpandedBody = string3;
            if (TextUtils.isEmpty(string3)) {
                this.mExpandedBody = null;
            }
            try {
                this.mClickIntent = Intent.parseUri(parcel.readString(), 0);
            } catch (URISyntaxException unused) {
            }
        }
        if (i >= 2) {
            String string4 = parcel.readString();
            this.mContentDescription = string4;
            if (TextUtils.isEmpty(string4)) {
                this.mContentDescription = null;
            }
            String string5 = parcel.readString();
            this.mIconUri = TextUtils.isEmpty(string5) ? null : Uri.parse(string5);
        }
        if (i >= 2) {
            parcel.setDataPosition(iDataPosition + i2);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(2);
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int iDataPosition2 = parcel.dataPosition();
        parcel.writeInt(this.mVisible ? 1 : 0);
        parcel.writeInt(this.mIcon);
        parcel.writeString(TextUtils.isEmpty(this.mStatus) ? "" : this.mStatus);
        parcel.writeString(TextUtils.isEmpty(this.mExpandedTitle) ? "" : this.mExpandedTitle);
        parcel.writeString(TextUtils.isEmpty(this.mExpandedBody) ? "" : this.mExpandedBody);
        Intent intent = this.mClickIntent;
        parcel.writeString(intent == null ? "" : intent.toUri(0));
        parcel.writeString(TextUtils.isEmpty(this.mContentDescription) ? "" : this.mContentDescription);
        Uri uri = this.mIconUri;
        parcel.writeString(uri != null ? uri.toString() : "");
        int iDataPosition3 = parcel.dataPosition() - iDataPosition2;
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition3);
        parcel.setDataPosition(iDataPosition2 + iDataPosition3);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            ExtensionData extensionData = (ExtensionData) obj;
            if (extensionData.mVisible == this.mVisible && extensionData.mIcon == this.mIcon && objectEquals(extensionData.mIconUri, this.mIconUri) && TextUtils.equals(extensionData.mStatus, this.mStatus) && TextUtils.equals(extensionData.mExpandedTitle, this.mExpandedTitle) && TextUtils.equals(extensionData.mExpandedBody, this.mExpandedBody) && objectEquals(extensionData.mClickIntent, this.mClickIntent)) {
                return TextUtils.equals(extensionData.mContentDescription, this.mContentDescription);
            }
            return false;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    private static boolean objectEquals(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return obj == obj2;
        }
        return obj.equals(obj2);
    }

    public static boolean equals(ExtensionData extensionData, ExtensionData extensionData2) {
        if (extensionData == null || extensionData2 == null) {
            return extensionData == extensionData2;
        }
        return extensionData.equals(extensionData2);
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public void clean() {
        if (!TextUtils.isEmpty(this.mStatus) && this.mStatus.length() > 32) {
            this.mStatus = this.mStatus.substring(0, 32);
        }
        if (!TextUtils.isEmpty(this.mExpandedTitle) && this.mExpandedTitle.length() > 100) {
            this.mExpandedTitle = this.mExpandedTitle.substring(0, 100);
        }
        if (!TextUtils.isEmpty(this.mExpandedBody) && this.mExpandedBody.length() > 1000) {
            this.mExpandedBody = this.mExpandedBody.substring(0, 1000);
        }
        if (TextUtils.isEmpty(this.mContentDescription) || this.mContentDescription.length() <= 1000) {
            return;
        }
        this.mContentDescription = this.mContentDescription.substring(0, 1164);
    }
}
