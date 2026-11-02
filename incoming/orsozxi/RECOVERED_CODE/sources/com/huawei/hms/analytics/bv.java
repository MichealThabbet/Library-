package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.config.RomAttributeCollector;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class bv implements RomAttributeCollector {
    int A;
    int B;
    String a;
    String abc;
    String b;
    String bcd;
    int c;
    String cde;
    int d;
    String def;
    String e;
    String efg;
    boolean f;
    String fgh;
    Boolean g;
    String ghi;
    String h;
    String hij;
    String i;
    String ijk;
    String ikl;
    String j;
    long k;
    String klm;
    long l;
    String lmn;
    long m;
    long n;
    int o;
    String p;
    boolean q;
    String r;
    String s;
    String t;
    Integer u;
    Integer v;
    Boolean w;
    Boolean x;
    String y;
    int z;

    @Override // com.huawei.hms.analytics.framework.config.RomAttributeCollector
    public final JSONObject doCollector() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("_is_testdevice", this.A);
            jSONObject.put("_new_user_flag", this.z);
            jSONObject.put("_signed", this.B);
            jSONObject.put("_rom_ver", this.lmn);
            jSONObject.put("_emui_ver", this.klm);
            jSONObject.put("_user_agent", this.s);
            jSONObject.put("_model", this.ikl);
            jSONObject.put("_package_name", this.ijk);
            jSONObject.put("_app_ver", this.hij);
            jSONObject.put("_lib_ver", this.ghi);
            jSONObject.put("_lib_name", this.def);
            jSONObject.put("_lib_type", this.cde);
            jSONObject.put("_channel", this.efg);
            jSONObject.put("_restriction_enabled", this.f);
            jSONObject.put("_restriction_shared", this.g);
            jSONObject.put("_terminal_name", this.fgh);
            jSONObject.put("_sys_language", this.a);
            jSONObject.put("_manufacturer", this.b);
            jSONObject.put("_os", this.bcd);
            jSONObject.put("_os_ver", this.abc);
            jSONObject.put("_screen_height", this.c);
            jSONObject.put("_screen_width", this.d);
            jSONObject.put("_device_category", this.e);
            jSONObject.put("_ab_info", this.h);
            jSONObject.put("_oaid_tracking_flag", this.i);
            jSONObject.put("_network_type", this.j);
            jSONObject.put("_total_storage", this.k);
            jSONObject.put("_free_storage", this.l);
            jSONObject.put("_free_memory", this.n);
            jSONObject.put("_total_memory", this.m);
            jSONObject.put("_battery_level", this.o);
            jSONObject.put("_battery_state", this.p);
            jSONObject.put("_allow_push", this.q);
            jSONObject.put("_first_open_time", this.r);
            jSONObject.put("_font_family", "");
            jSONObject.put("_font_size", this.t);
            jSONObject.put("_font_weight", "");
            jSONObject.put("_font_style", "");
            jSONObject.put("_screen_brightness", this.u);
            jSONObject.put("_ringer_mode", this.v);
            jSONObject.put("_dark_mode", this.w);
            jSONObject.put("_auto_rotate", this.x);
            jSONObject.put("_campaign_info", this.y);
        } catch (JSONException unused) {
            HiLog.w("OpennessRomGathering", "doCollector JSONException");
        }
        return jSONObject;
    }
}
