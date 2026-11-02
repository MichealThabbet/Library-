package com.huawei.hms.audiokit.soundeffect.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HwAudioEffectItem implements Parcelable {
    public static final Parcelable.Creator<HwAudioEffectItem> CREATOR = new Parcelable.Creator<HwAudioEffectItem>() { // from class: com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HwAudioEffectItem createFromParcel(Parcel parcel) {
            return new HwAudioEffectItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HwAudioEffectItem[] newArray(int i) {
            return new HwAudioEffectItem[i];
        }
    };
    private String createTime;
    private int eq125;
    private int eq16k;
    private int eq1k;
    private int eq250;
    private int eq2k;
    private int eq31;
    private int eq4k;
    private int eq500;
    private int eq62;
    private int eq8k;
    private int eqSuperChannelBalance;
    private int eqSuperEnvironmentalSoundEffect;
    private int eqUltraLowStress;
    private String name;
    private String resId;
    private int type;
    private String updateTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HwAudioEffectItem() {
    }

    public HwAudioEffectItem(Parcel parcel) {
        this.resId = parcel.readString();
        this.type = parcel.readInt();
        this.name = parcel.readString();
        this.createTime = parcel.readString();
        this.updateTime = parcel.readString();
        this.eq31 = parcel.readInt();
        this.eq62 = parcel.readInt();
        this.eq125 = parcel.readInt();
        this.eq250 = parcel.readInt();
        this.eq500 = parcel.readInt();
        this.eq1k = parcel.readInt();
        this.eq2k = parcel.readInt();
        this.eq4k = parcel.readInt();
        this.eq8k = parcel.readInt();
        this.eq16k = parcel.readInt();
        this.eqUltraLowStress = parcel.readInt();
        this.eqSuperEnvironmentalSoundEffect = parcel.readInt();
        this.eqSuperChannelBalance = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resId);
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeString(this.createTime);
        parcel.writeString(this.updateTime);
        parcel.writeInt(this.eq31);
        parcel.writeInt(this.eq62);
        parcel.writeInt(this.eq125);
        parcel.writeInt(this.eq250);
        parcel.writeInt(this.eq500);
        parcel.writeInt(this.eq1k);
        parcel.writeInt(this.eq2k);
        parcel.writeInt(this.eq4k);
        parcel.writeInt(this.eq8k);
        parcel.writeInt(this.eq16k);
        parcel.writeInt(this.eqUltraLowStress);
        parcel.writeInt(this.eqSuperEnvironmentalSoundEffect);
        parcel.writeInt(this.eqSuperChannelBalance);
    }

    public void readFromParcel(Parcel parcel) {
        this.resId = parcel.readString();
        this.type = parcel.readInt();
        this.name = parcel.readString();
        this.createTime = parcel.readString();
        this.updateTime = parcel.readString();
        this.eq31 = parcel.readInt();
        this.eq62 = parcel.readInt();
        this.eq125 = parcel.readInt();
        this.eq250 = parcel.readInt();
        this.eq500 = parcel.readInt();
        this.eq1k = parcel.readInt();
        this.eq2k = parcel.readInt();
        this.eq4k = parcel.readInt();
        this.eq8k = parcel.readInt();
        this.eq16k = parcel.readInt();
        this.eqUltraLowStress = parcel.readInt();
        this.eqSuperEnvironmentalSoundEffect = parcel.readInt();
        this.eqSuperChannelBalance = parcel.readInt();
    }

    public String getResId() {
        return this.resId;
    }

    public void setResId(String str) {
        this.resId = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    public int getEq31() {
        return this.eq31;
    }

    public void setEq31(int i) {
        this.eq31 = i;
    }

    public int getEq62() {
        return this.eq62;
    }

    public void setEq62(int i) {
        this.eq62 = i;
    }

    public int getEq125() {
        return this.eq125;
    }

    public void setEq125(int i) {
        this.eq125 = i;
    }

    public int getEq250() {
        return this.eq250;
    }

    public void setEq250(int i) {
        this.eq250 = i;
    }

    public int getEq500() {
        return this.eq500;
    }

    public void setEq500(int i) {
        this.eq500 = i;
    }

    public int getEq1k() {
        return this.eq1k;
    }

    public void setEq1k(int i) {
        this.eq1k = i;
    }

    public int getEq2k() {
        return this.eq2k;
    }

    public void setEq2k(int i) {
        this.eq2k = i;
    }

    public int getEq4k() {
        return this.eq4k;
    }

    public void setEq4k(int i) {
        this.eq4k = i;
    }

    public int getEq8k() {
        return this.eq8k;
    }

    public void setEq8k(int i) {
        this.eq8k = i;
    }

    public int getEq16k() {
        return this.eq16k;
    }

    public void setEq16k(int i) {
        this.eq16k = i;
    }

    public int getEqUltraLowStress() {
        return this.eqUltraLowStress;
    }

    public void setEqUltraLowStress(int i) {
        this.eqUltraLowStress = i;
    }

    public int getEqSuperEnvironmentalSoundEffect() {
        return this.eqSuperEnvironmentalSoundEffect;
    }

    public void setEqSuperEnvironmentalSoundEffect(int i) {
        this.eqSuperEnvironmentalSoundEffect = i;
    }

    public int getEqSuperChannelBalance() {
        return this.eqSuperChannelBalance;
    }

    public void setEqSuperChannelBalance(int i) {
        this.eqSuperChannelBalance = i;
    }
}
