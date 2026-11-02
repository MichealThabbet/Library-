package org.acra.collector;

import android.content.Context;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.collector.Collector;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.apache.commons.codec.language.Soundex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class MediaCodecListCollector extends BaseReportFieldCollector {
    private static final String COLOR_FORMAT_PREFIX = "COLOR_";
    private final SparseArray<String> mAACProfileValues;
    private final SparseArray<String> mAVCLevelValues;
    private final SparseArray<String> mAVCProfileValues;
    private final SparseArray<String> mColorFormatValues;
    private final SparseArray<String> mH263LevelValues;
    private final SparseArray<String> mH263ProfileValues;
    private final SparseArray<String> mMPEG4LevelValues;
    private final SparseArray<String> mMPEG4ProfileValues;
    private static final String[] MPEG4_TYPES = {"mp4", "mpeg4", "MP4", "MPEG4"};
    private static final String[] AVC_TYPES = {"avc", "h264", "AVC", "H264"};
    private static final String[] H263_TYPES = {"h263", "H263"};
    private static final String[] AAC_TYPES = {"aac", "AAC"};

    private enum CodecType {
        AVC,
        H263,
        MPEG4,
        AAC
    }

    public MediaCodecListCollector() {
        super(ReportField.MEDIA_CODEC_LIST, new ReportField[0]);
        this.mColorFormatValues = new SparseArray<>();
        this.mAVCLevelValues = new SparseArray<>();
        this.mAVCProfileValues = new SparseArray<>();
        this.mH263LevelValues = new SparseArray<>();
        this.mH263ProfileValues = new SparseArray<>();
        this.mMPEG4LevelValues = new SparseArray<>();
        this.mMPEG4ProfileValues = new SparseArray<>();
        this.mAACProfileValues = new SparseArray<>();
    }

    @Override // org.acra.collector.BaseReportFieldCollector, org.acra.collector.Collector
    public Collector.Order getOrder() {
        return Collector.Order.LATE;
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws JSONException {
        crashReportData.put(ReportField.MEDIA_CODEC_LIST, collectMediaCodecList());
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    boolean shouldCollect(Context context, CoreConfiguration coreConfiguration, ReportField reportField, ReportBuilder reportBuilder) {
        return super.shouldCollect(context, coreConfiguration, reportField, reportBuilder) && Build.VERSION.SDK_INT >= 16;
    }

    private void prepare() throws SecurityException {
        try {
            for (Field field : Class.forName("android.media.MediaCodecInfo$CodecCapabilities").getFields()) {
                if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getName().startsWith(COLOR_FORMAT_PREFIX)) {
                    this.mColorFormatValues.put(field.getInt(null), field.getName());
                }
            }
            for (Field field2 : Class.forName("android.media.MediaCodecInfo$CodecProfileLevel").getFields()) {
                if (Modifier.isStatic(field2.getModifiers()) && Modifier.isFinal(field2.getModifiers())) {
                    if (field2.getName().startsWith("AVCLevel")) {
                        this.mAVCLevelValues.put(field2.getInt(null), field2.getName());
                    } else if (field2.getName().startsWith("AVCProfile")) {
                        this.mAVCProfileValues.put(field2.getInt(null), field2.getName());
                    } else if (field2.getName().startsWith("H263Level")) {
                        this.mH263LevelValues.put(field2.getInt(null), field2.getName());
                    } else if (field2.getName().startsWith("H263Profile")) {
                        this.mH263ProfileValues.put(field2.getInt(null), field2.getName());
                    } else if (field2.getName().startsWith("MPEG4Level")) {
                        this.mMPEG4LevelValues.put(field2.getInt(null), field2.getName());
                    } else if (field2.getName().startsWith("MPEG4Profile")) {
                        this.mMPEG4ProfileValues.put(field2.getInt(null), field2.getName());
                    } else if (field2.getName().startsWith("AAC")) {
                        this.mAACProfileValues.put(field2.getInt(null), field2.getName());
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | SecurityException unused) {
        }
    }

    private JSONObject collectMediaCodecList() throws JSONException, SecurityException {
        MediaCodecInfo[] codecInfos;
        prepare();
        if (Build.VERSION.SDK_INT < 21) {
            int codecCount = MediaCodecList.getCodecCount();
            codecInfos = new MediaCodecInfo[codecCount];
            for (int i = 0; i < codecCount; i++) {
                codecInfos[i] = MediaCodecList.getCodecInfoAt(i);
            }
        } else {
            codecInfos = new MediaCodecList(1).getCodecInfos();
        }
        JSONObject jSONObject = new JSONObject();
        for (int i2 = 0; i2 < codecInfos.length; i2++) {
            MediaCodecInfo mediaCodecInfo = codecInfos[i2];
            JSONObject jSONObject2 = new JSONObject();
            String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
            jSONObject2.put("name", mediaCodecInfo.getName()).put("isEncoder", mediaCodecInfo.isEncoder());
            JSONObject jSONObject3 = new JSONObject();
            for (String str : supportedTypes) {
                jSONObject3.put(str, collectCapabilitiesForType(mediaCodecInfo, str));
            }
            jSONObject2.put("supportedTypes", jSONObject3);
            jSONObject.put(String.valueOf(i2), jSONObject2);
        }
        return jSONObject;
    }

    private JSONObject collectCapabilitiesForType(MediaCodecInfo mediaCodecInfo, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
        int[] iArr = capabilitiesForType.colorFormats;
        int i = 0;
        if (iArr.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (int i2 : iArr) {
                jSONArray.put(this.mColorFormatValues.get(i2));
            }
            jSONObject.put("colorFormats", jSONArray);
        }
        CodecType codecTypeIdentifyCodecType = identifyCodecType(mediaCodecInfo);
        MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = capabilitiesForType.profileLevels;
        if (codecProfileLevelArr.length > 0) {
            JSONArray jSONArray2 = new JSONArray();
            int length = codecProfileLevelArr.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                MediaCodecInfo.CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i];
                int i3 = codecProfileLevel.profile;
                int i4 = codecProfileLevel.level;
                if (codecTypeIdentifyCodecType == null) {
                    jSONArray2.put(i3 + 45 + i4);
                    break;
                }
                int i5 = AnonymousClass1.$SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType[codecTypeIdentifyCodecType.ordinal()];
                if (i5 == 1) {
                    jSONArray2.put(i3 + this.mAVCProfileValues.get(i3) + Soundex.SILENT_MARKER + this.mAVCLevelValues.get(i4));
                } else if (i5 == 2) {
                    jSONArray2.put(this.mH263ProfileValues.get(i3) + Soundex.SILENT_MARKER + this.mH263LevelValues.get(i4));
                } else if (i5 == 3) {
                    jSONArray2.put(this.mMPEG4ProfileValues.get(i3) + Soundex.SILENT_MARKER + this.mMPEG4LevelValues.get(i4));
                } else if (i5 == 4) {
                    jSONArray2.put(this.mAACProfileValues.get(i3));
                }
                i++;
            }
            jSONObject.put("profileLevels", jSONArray2);
        }
        return jSONObject;
    }

    /* renamed from: org.acra.collector.MediaCodecListCollector$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType;

        static {
            int[] iArr = new int[CodecType.values().length];
            $SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType = iArr;
            try {
                iArr[CodecType.AVC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType[CodecType.H263.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType[CodecType.MPEG4.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType[CodecType.AAC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private CodecType identifyCodecType(MediaCodecInfo mediaCodecInfo) {
        String name = mediaCodecInfo.getName();
        for (String str : AVC_TYPES) {
            if (name.contains(str)) {
                return CodecType.AVC;
            }
        }
        for (String str2 : H263_TYPES) {
            if (name.contains(str2)) {
                return CodecType.H263;
            }
        }
        for (String str3 : MPEG4_TYPES) {
            if (name.contains(str3)) {
                return CodecType.MPEG4;
            }
        }
        for (String str4 : AAC_TYPES) {
            if (name.contains(str4)) {
                return CodecType.AAC;
            }
        }
        return null;
    }
}
