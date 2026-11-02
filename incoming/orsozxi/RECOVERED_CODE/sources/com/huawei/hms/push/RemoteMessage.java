package com.huawei.hms.push;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.push.utils.DateUtil;
import com.huawei.hms.push.utils.JsonUtil;
import com.huawei.hms.support.api.push.PushException;
import com.huawei.hms.support.log.HMSLog;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RemoteMessage implements Parcelable {
    public static final Parcelable.Creator<RemoteMessage> CREATOR;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;
    public static final String[] a = new String[0];
    public static final int[] b = new int[0];
    public static final long[] c = new long[0];
    public static final HashMap<String, Object> d;
    public static final HashMap<String, Object> e;
    public static final HashMap<String, Object> f;
    public static final HashMap<String, Object> g;
    public static final HashMap<String, Object> h;
    public Bundle i;
    public Notification j;

    public static class Builder {
        public final Bundle a = new Bundle();
        public final Map<String, String> b = new HashMap();

        public Builder(String str) {
            this.a.putString("to", str);
        }

        public Builder addData(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("add data failed, key is null.");
            }
            this.b.put(str, str2);
            return this;
        }

        public RemoteMessage build() throws JSONException {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            try {
                for (Map.Entry<String, String> entry : this.b.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
                try {
                    String string = jSONObject.toString();
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("collapseKey", this.a.getString("collapseKey"));
                    jSONObject2.put("ttl", this.a.getInt("ttl"));
                    jSONObject2.put(RemoteMessageConst.SEND_MODE, this.a.getInt(RemoteMessageConst.SEND_MODE));
                    jSONObject2.put(RemoteMessageConst.RECEIPT_MODE, this.a.getInt(RemoteMessageConst.RECEIPT_MODE));
                    JSONObject jSONObject3 = new JSONObject();
                    if (jSONObject.length() != 0) {
                        jSONObject3.put("data", string);
                    }
                    jSONObject3.put(RemoteMessageConst.MSGID, this.a.getString(RemoteMessageConst.MSGID));
                    jSONObject2.put(RemoteMessageConst.MessageBody.MSG_CONTENT, jSONObject3);
                    bundle.putByteArray(RemoteMessageConst.MSGBODY, jSONObject2.toString().getBytes(x.a));
                    bundle.putString("to", this.a.getString("to"));
                    bundle.putString("message_type", this.a.getString("message_type"));
                    return new RemoteMessage(bundle);
                } catch (JSONException unused) {
                    HMSLog.w("RemoteMessage", "JSONException: parse message body failed.");
                    throw new PushException(PushException.EXCEPTION_SEND_FAILED);
                }
            } catch (JSONException unused2) {
                HMSLog.w("RemoteMessage", "JSONException: parse data to json failed.");
                throw new PushException(PushException.EXCEPTION_SEND_FAILED);
            }
        }

        public Builder clearData() {
            this.b.clear();
            return this;
        }

        public Builder setCollapseKey(String str) {
            this.a.putString("collapseKey", str);
            return this;
        }

        public Builder setData(Map<String, String> map) {
            this.b.clear();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.b.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public Builder setMessageId(String str) {
            this.a.putString(RemoteMessageConst.MSGID, str);
            return this;
        }

        public Builder setMessageType(String str) {
            this.a.putString("message_type", str);
            return this;
        }

        public Builder setReceiptMode(int i) {
            if (i != 1 && i != 0) {
                throw new IllegalArgumentException("receipt mode can only be 0 or 1.");
            }
            this.a.putInt(RemoteMessageConst.RECEIPT_MODE, i);
            return this;
        }

        public Builder setSendMode(int i) {
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException("send mode can only be 0 or 1.");
            }
            this.a.putInt(RemoteMessageConst.SEND_MODE, i);
            return this;
        }

        public Builder setTtl(int i) {
            if (i < 1 || i > 1296000) {
                throw new IllegalArgumentException("ttl must be greater than or equal to 1 and less than or equal to 1296000");
            }
            this.a.putInt("ttl", i);
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    public static class Notification implements Serializable {
        public final long[] A;
        public final String B;
        public final String a;
        public final String b;
        public final String[] c;
        public final String d;
        public final String e;
        public final String[] f;
        public final String g;
        public final String h;
        public final String i;
        public final String j;
        public final String k;
        public final String l;
        public final String m;
        public final Uri n;
        public final int o;
        public final String p;
        public final int q;
        public final int r;
        public final int s;
        public final int[] t;
        public final String u;
        public final int v;
        public final String w;
        public final int x;
        public final String y;
        public final String z;

        public /* synthetic */ Notification(Bundle bundle, b bVar) {
            this(bundle);
        }

        public final Integer a(String str) {
            if (str != null) {
                try {
                    return Integer.valueOf(str);
                } catch (NumberFormatException unused) {
                    HMSLog.w("RemoteMessage", "NumberFormatException: get " + str + " failed.");
                }
            }
            return null;
        }

        public Integer getBadgeNumber() {
            return a(this.w);
        }

        public String getBody() {
            return this.d;
        }

        public String[] getBodyLocalizationArgs() {
            String[] strArr = this.f;
            return strArr == null ? new String[0] : (String[]) strArr.clone();
        }

        public String getBodyLocalizationKey() {
            return this.e;
        }

        public String getChannelId() {
            return this.m;
        }

        public String getClickAction() {
            return this.k;
        }

        public String getColor() {
            return this.j;
        }

        public String getIcon() {
            return this.g;
        }

        public Uri getImageUrl() {
            String str = this.p;
            if (str == null) {
                return null;
            }
            return Uri.parse(str);
        }

        public Integer getImportance() {
            return a(this.y);
        }

        public String getIntentUri() {
            return this.l;
        }

        public int[] getLightSettings() {
            int[] iArr = this.t;
            return iArr == null ? new int[0] : (int[]) iArr.clone();
        }

        public Uri getLink() {
            return this.n;
        }

        public int getNotifyId() {
            return this.o;
        }

        public String getSound() {
            return this.h;
        }

        public String getTag() {
            return this.i;
        }

        public String getTicker() {
            return this.z;
        }

        public String getTitle() {
            return this.a;
        }

        public String[] getTitleLocalizationArgs() {
            String[] strArr = this.c;
            return strArr == null ? new String[0] : (String[]) strArr.clone();
        }

        public String getTitleLocalizationKey() {
            return this.b;
        }

        public long[] getVibrateConfig() {
            long[] jArr = this.A;
            return jArr == null ? new long[0] : (long[]) jArr.clone();
        }

        public Integer getVisibility() {
            return a(this.B);
        }

        public Long getWhen() {
            if (!TextUtils.isEmpty(this.u)) {
                try {
                    return Long.valueOf(DateUtil.parseUtcToMillisecond(this.u));
                } catch (StringIndexOutOfBoundsException unused) {
                    HMSLog.w("RemoteMessage", "StringIndexOutOfBoundsException: parse when failed.");
                } catch (ParseException unused2) {
                    HMSLog.w("RemoteMessage", "ParseException: parse when failed.");
                }
            }
            return null;
        }

        public boolean isAutoCancel() {
            return this.x == 1;
        }

        public boolean isDefaultLight() {
            return this.q == 1;
        }

        public boolean isDefaultSound() {
            return this.r == 1;
        }

        public boolean isDefaultVibrate() {
            return this.s == 1;
        }

        public boolean isLocalOnly() {
            return this.v == 1;
        }

        public Notification(Bundle bundle) {
            this.a = bundle.getString(RemoteMessageConst.Notification.NOTIFY_TITLE);
            this.d = bundle.getString("content");
            this.b = bundle.getString(RemoteMessageConst.Notification.TITLE_LOC_KEY);
            this.e = bundle.getString(RemoteMessageConst.Notification.BODY_LOC_KEY);
            this.c = bundle.getStringArray(RemoteMessageConst.Notification.TITLE_LOC_ARGS);
            this.f = bundle.getStringArray(RemoteMessageConst.Notification.BODY_LOC_ARGS);
            this.g = bundle.getString(RemoteMessageConst.Notification.ICON);
            this.j = bundle.getString("color");
            this.h = bundle.getString(RemoteMessageConst.Notification.SOUND);
            this.i = bundle.getString(RemoteMessageConst.Notification.TAG);
            this.m = bundle.getString(RemoteMessageConst.Notification.CHANNEL_ID);
            this.k = bundle.getString(RemoteMessageConst.Notification.CLICK_ACTION);
            this.l = bundle.getString(RemoteMessageConst.Notification.INTENT_URI);
            this.o = bundle.getInt(RemoteMessageConst.Notification.NOTIFY_ID);
            String string = bundle.getString("url");
            this.n = !TextUtils.isEmpty(string) ? Uri.parse(string) : null;
            this.p = bundle.getString(RemoteMessageConst.Notification.NOTIFY_ICON);
            this.q = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_LIGHT_SETTINGS);
            this.r = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_SOUND);
            this.s = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_VIBRATE_TIMINGS);
            this.t = bundle.getIntArray(RemoteMessageConst.Notification.LIGHT_SETTINGS);
            this.u = bundle.getString(RemoteMessageConst.Notification.WHEN);
            this.v = bundle.getInt(RemoteMessageConst.Notification.LOCAL_ONLY);
            this.w = bundle.getString(RemoteMessageConst.Notification.BADGE_SET_NUM, null);
            this.x = bundle.getInt(RemoteMessageConst.Notification.AUTO_CANCEL);
            this.y = bundle.getString("priority", null);
            this.z = bundle.getString(RemoteMessageConst.Notification.TICKER);
            this.A = bundle.getLongArray(RemoteMessageConst.Notification.VIBRATE_TIMINGS);
            this.B = bundle.getString(RemoteMessageConst.Notification.VISIBILITY, null);
        }
    }

    public RemoteMessage(Bundle bundle) {
        this.i = a(bundle);
    }

    public static JSONObject b(Bundle bundle) {
        try {
            return new JSONObject(w.a(bundle.getByteArray(RemoteMessageConst.MSGBODY)));
        } catch (JSONException unused) {
            HMSLog.w("RemoteMessage", "JSONException:parse message body failed.");
            return null;
        }
    }

    public static JSONObject c(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PARAM);
        }
        return null;
    }

    public static JSONObject d(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
        }
        return null;
    }

    public final Bundle a(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        JSONObject jSONObjectB = b(bundle);
        JSONObject jSONObjectA = a(jSONObjectB);
        String string = JsonUtil.getString(jSONObjectA, "data", null);
        bundle2.putString(RemoteMessageConst.ANALYTIC_INFO, JsonUtil.getString(jSONObjectA, RemoteMessageConst.ANALYTIC_INFO, null));
        bundle2.putString(RemoteMessageConst.DEVICE_TOKEN, bundle.getString(RemoteMessageConst.DEVICE_TOKEN));
        JSONObject jSONObjectD = d(jSONObjectA);
        JSONObject jSONObjectB2 = b(jSONObjectD);
        JSONObject jSONObjectC = c(jSONObjectD);
        if (bundle.getInt(RemoteMessageConst.INPUT_TYPE) == 1 && s.a(jSONObjectA, jSONObjectD, string)) {
            bundle2.putString("data", w.a(bundle.getByteArray(RemoteMessageConst.MSGBODY)));
            return bundle2;
        }
        String string2 = bundle.getString("to");
        String string3 = bundle.getString("message_type");
        String string4 = JsonUtil.getString(jSONObjectA, RemoteMessageConst.MSGID, null);
        bundle2.putString("to", string2);
        bundle2.putString("data", string);
        bundle2.putString(RemoteMessageConst.MSGID, string4);
        bundle2.putString("message_type", string3);
        JsonUtil.transferJsonObjectToBundle(jSONObjectB, bundle2, d);
        bundle2.putBundle(RemoteMessageConst.NOTIFICATION, a(jSONObjectB, jSONObjectA, jSONObjectD, jSONObjectB2, jSONObjectC));
        return bundle2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public String getAnalyticInfo() {
        return this.i.getString(RemoteMessageConst.ANALYTIC_INFO);
    }

    public Map<String, String> getAnalyticInfoMap() {
        HashMap map = new HashMap();
        String string = this.i.getString(RemoteMessageConst.ANALYTIC_INFO);
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String strValueOf = String.valueOf(itKeys.next());
                    map.put(strValueOf, String.valueOf(jSONObject.get(strValueOf)));
                }
            } catch (JSONException unused) {
                HMSLog.w("RemoteMessage", "JSONException: get analyticInfo from map failed.");
            }
        }
        return map;
    }

    public String getCollapseKey() {
        return this.i.getString("collapseKey");
    }

    public String getData() {
        return this.i.getString("data");
    }

    public Map<String, String> getDataOfMap() {
        HashMap map = new HashMap();
        String string = this.i.getString("data");
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String strValueOf = String.valueOf(itKeys.next());
                    map.put(strValueOf, String.valueOf(jSONObject.get(strValueOf)));
                }
            } catch (JSONException unused) {
                HMSLog.w("RemoteMessage", "JSONException: get data from map failed");
            }
        }
        return map;
    }

    public String getFrom() {
        return this.i.getString("from");
    }

    public String getMessageId() {
        return this.i.getString(RemoteMessageConst.MSGID);
    }

    public String getMessageType() {
        return this.i.getString("message_type");
    }

    public Notification getNotification() {
        Bundle bundle = this.i.getBundle(RemoteMessageConst.NOTIFICATION);
        b bVar = null;
        if (this.j == null && bundle != null) {
            this.j = new Notification(bundle, bVar);
        }
        if (this.j == null) {
            this.j = new Notification(new Bundle(), bVar);
        }
        return this.j;
    }

    public int getOriginalUrgency() {
        int i = this.i.getInt(RemoteMessageConst.ORI_URGENCY);
        if (i == 1 || i == 2) {
            return i;
        }
        return 0;
    }

    public int getReceiptMode() {
        return this.i.getInt(RemoteMessageConst.RECEIPT_MODE);
    }

    public int getSendMode() {
        return this.i.getInt(RemoteMessageConst.SEND_MODE);
    }

    public long getSentTime() {
        try {
            String string = this.i.getString(RemoteMessageConst.SEND_TIME);
            if (TextUtils.isEmpty(string)) {
                return 0L;
            }
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            HMSLog.w("RemoteMessage", "NumberFormatException: get sendTime error.");
            return 0L;
        }
    }

    public String getTo() {
        return this.i.getString("to");
    }

    public String getToken() {
        return this.i.getString(RemoteMessageConst.DEVICE_TOKEN);
    }

    public int getTtl() {
        return this.i.getInt("ttl");
    }

    public int getUrgency() {
        int i = this.i.getInt(RemoteMessageConst.URGENCY);
        if (i == 1 || i == 2) {
            return i;
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.i);
        parcel.writeSerializable(this.j);
    }

    public RemoteMessage(Parcel parcel) {
        this.i = parcel.readBundle();
        this.j = (Notification) parcel.readSerializable();
    }

    public static JSONObject b(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.NOTIFY_DETAIL);
        }
        return null;
    }

    static {
        HashMap<String, Object> map = new HashMap<>(8);
        d = map;
        map.put("from", "");
        d.put("collapseKey", "");
        d.put(RemoteMessageConst.SEND_TIME, "");
        d.put("ttl", Integer.valueOf(RemoteMessageConst.DEFAULT_TTL));
        d.put(RemoteMessageConst.URGENCY, 2);
        d.put(RemoteMessageConst.ORI_URGENCY, 2);
        d.put(RemoteMessageConst.SEND_MODE, 0);
        d.put(RemoteMessageConst.RECEIPT_MODE, 0);
        HashMap<String, Object> map2 = new HashMap<>(8);
        e = map2;
        map2.put(RemoteMessageConst.Notification.TITLE_LOC_KEY, "");
        e.put(RemoteMessageConst.Notification.BODY_LOC_KEY, "");
        e.put(RemoteMessageConst.Notification.NOTIFY_ICON, "");
        e.put(RemoteMessageConst.Notification.TITLE_LOC_ARGS, a);
        e.put(RemoteMessageConst.Notification.BODY_LOC_ARGS, a);
        e.put(RemoteMessageConst.Notification.TICKER, "");
        e.put(RemoteMessageConst.Notification.NOTIFY_TITLE, "");
        e.put("content", "");
        HashMap<String, Object> map3 = new HashMap<>(8);
        f = map3;
        map3.put(RemoteMessageConst.Notification.ICON, "");
        f.put("color", "");
        f.put(RemoteMessageConst.Notification.SOUND, "");
        f.put(RemoteMessageConst.Notification.DEFAULT_LIGHT_SETTINGS, 1);
        f.put(RemoteMessageConst.Notification.LIGHT_SETTINGS, b);
        f.put(RemoteMessageConst.Notification.DEFAULT_SOUND, 1);
        f.put(RemoteMessageConst.Notification.DEFAULT_VIBRATE_TIMINGS, 1);
        f.put(RemoteMessageConst.Notification.VIBRATE_TIMINGS, c);
        HashMap<String, Object> map4 = new HashMap<>(8);
        g = map4;
        map4.put(RemoteMessageConst.Notification.TAG, "");
        g.put(RemoteMessageConst.Notification.WHEN, "");
        g.put(RemoteMessageConst.Notification.LOCAL_ONLY, 1);
        g.put(RemoteMessageConst.Notification.BADGE_SET_NUM, "");
        g.put("priority", "");
        g.put(RemoteMessageConst.Notification.AUTO_CANCEL, 1);
        g.put(RemoteMessageConst.Notification.VISIBILITY, "");
        g.put(RemoteMessageConst.Notification.CHANNEL_ID, "");
        HashMap<String, Object> map5 = new HashMap<>(3);
        h = map5;
        map5.put(RemoteMessageConst.Notification.CLICK_ACTION, "");
        h.put(RemoteMessageConst.Notification.INTENT_URI, "");
        h.put("url", "");
        CREATOR = new b();
    }

    public final Bundle a(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, JSONObject jSONObject4, JSONObject jSONObject5) {
        Bundle bundle = new Bundle();
        JsonUtil.transferJsonObjectToBundle(jSONObject3, bundle, e);
        JsonUtil.transferJsonObjectToBundle(jSONObject4, bundle, f);
        JsonUtil.transferJsonObjectToBundle(jSONObject, bundle, g);
        JsonUtil.transferJsonObjectToBundle(jSONObject5, bundle, h);
        bundle.putInt(RemoteMessageConst.Notification.NOTIFY_ID, JsonUtil.getInt(jSONObject2, RemoteMessageConst.Notification.NOTIFY_ID, 0));
        return bundle;
    }

    public static JSONObject a(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
        }
        return null;
    }
}
