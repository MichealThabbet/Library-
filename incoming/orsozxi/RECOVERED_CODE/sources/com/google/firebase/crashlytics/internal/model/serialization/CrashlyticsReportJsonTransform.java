package com.google.firebase.crashlytics.internal.model.serialization;

import android.util.Base64;
import android.util.JsonReader;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.crashlytics.internal.model.AutoCrashlyticsReportEncoder;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.crashlytics.internal.model.ImmutableList;
import com.google.firebase.encoders.DataEncoder;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import it.orsozoxi.android.orsonotes.R2;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes2.dex */
public class CrashlyticsReportJsonTransform {
    private static final DataEncoder CRASHLYTICS_REPORT_JSON_ENCODER = new JsonDataEncoderBuilder().configureWith(AutoCrashlyticsReportEncoder.CONFIG).ignoreNullValues(true).build();

    /* JADX INFO: Access modifiers changed from: private */
    interface ObjectParser<T> {
        T parse(JsonReader jsonReader) throws IOException;
    }

    public String reportToJson(CrashlyticsReport crashlyticsReport) {
        return CRASHLYTICS_REPORT_JSON_ENCODER.encode(crashlyticsReport);
    }

    public String eventToJson(CrashlyticsReport.Session.Event event) {
        return CRASHLYTICS_REPORT_JSON_ENCODER.encode(event);
    }

    public CrashlyticsReport reportFromJson(String str) throws IOException {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(str));
            try {
                CrashlyticsReport report = parseReport(jsonReader);
                jsonReader.close();
                return report;
            } finally {
            }
        } catch (IllegalStateException e) {
            throw new IOException(e);
        }
    }

    public CrashlyticsReport.Session.Event eventFromJson(String str) throws IOException {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(str));
            try {
                CrashlyticsReport.Session.Event event = parseEvent(jsonReader);
                jsonReader.close();
                return event;
            } finally {
            }
        } catch (IllegalStateException e) {
            throw new IOException(e);
        }
    }

    private static CrashlyticsReport parseReport(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Builder builder = CrashlyticsReport.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "sdkVersion":
                    builder.setSdkVersion(jsonReader.nextString());
                    break;
                case "gmpAppId":
                    builder.setGmpAppId(jsonReader.nextString());
                    break;
                case "platform":
                    builder.setPlatform(jsonReader.nextInt());
                    break;
                case "installationUuid":
                    builder.setInstallationUuid(jsonReader.nextString());
                    break;
                case "buildVersion":
                    builder.setBuildVersion(jsonReader.nextString());
                    break;
                case "displayVersion":
                    builder.setDisplayVersion(jsonReader.nextString());
                    break;
                case "session":
                    builder.setSession(parseSession(jsonReader));
                    break;
                case "ndkPayload":
                    builder.setNdkPayload(parseNdkPayload(jsonReader));
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static CrashlyticsReport.Session parseSession(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Builder builder = CrashlyticsReport.Session.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "generator":
                    builder.setGenerator(jsonReader.nextString());
                    break;
                case "identifier":
                    builder.setIdentifierFromUtf8Bytes(Base64.decode(jsonReader.nextString(), 2));
                    break;
                case "startedAt":
                    builder.setStartedAt(jsonReader.nextLong());
                    break;
                case "endedAt":
                    builder.setEndedAt(Long.valueOf(jsonReader.nextLong()));
                    break;
                case "crashed":
                    builder.setCrashed(jsonReader.nextBoolean());
                    break;
                case "user":
                    builder.setUser(parseUser(jsonReader));
                    break;
                case "app":
                    builder.setApp(parseApp(jsonReader));
                    break;
                case "os":
                    builder.setOs(parseOs(jsonReader));
                    break;
                case "device":
                    builder.setDevice(parseDevice(jsonReader));
                    break;
                case "events":
                    builder.setEvents(parseArray(jsonReader, new ObjectParser() { // from class: com.google.firebase.crashlytics.internal.model.serialization.-$$Lambda$CrashlyticsReportJsonTransform$Ckb34QcYctmy_Q7QdvSnBmK1U98
                        @Override // com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform.ObjectParser
                        public final Object parse(JsonReader jsonReader2) {
                            return CrashlyticsReportJsonTransform.parseEvent(jsonReader2);
                        }
                    }));
                    break;
                case "generatorType":
                    builder.setGeneratorType(jsonReader.nextInt());
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static CrashlyticsReport.FilesPayload parseNdkPayload(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.FilesPayload.Builder builder = CrashlyticsReport.FilesPayload.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            int iHashCode = strNextName.hashCode();
            if (iHashCode != 97434231) {
                if (iHashCode == 106008351 && strNextName.equals("orgId")) {
                    c = 1;
                }
            } else if (strNextName.equals("files")) {
                c = 0;
            }
            if (c == 0) {
                builder.setFiles(parseArray(jsonReader, new ObjectParser() { // from class: com.google.firebase.crashlytics.internal.model.serialization.-$$Lambda$CrashlyticsReportJsonTransform$PcYtebWm8LLEihGxNBWQex7movQ
                    @Override // com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform.ObjectParser
                    public final Object parse(JsonReader jsonReader2) {
                        return CrashlyticsReportJsonTransform.parseFile(jsonReader2);
                    }
                }));
            } else if (c == 1) {
                builder.setOrgId(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CrashlyticsReport.FilesPayload.File parseFile(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.FilesPayload.File.Builder builder = CrashlyticsReport.FilesPayload.File.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            int iHashCode = strNextName.hashCode();
            if (iHashCode != -734768633) {
                if (iHashCode == -567321830 && strNextName.equals("contents")) {
                    c = 1;
                }
            } else if (strNextName.equals(FFmpegMediaMetadataRetriever.METADATA_KEY_FILENAME)) {
                c = 0;
            }
            if (c == 0) {
                builder.setFilename(jsonReader.nextString());
            } else if (c == 1) {
                builder.setContents(Base64.decode(jsonReader.nextString(), 2));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static CrashlyticsReport.Session.User parseUser(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.User.Builder builder = CrashlyticsReport.Session.User.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            if (strNextName.hashCode() == -1618432855 && strNextName.equals("identifier")) {
                c = 0;
            }
            if (c == 0) {
                builder.setIdentifier(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static CrashlyticsReport.Session.Application parseApp(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Application.Builder builder = CrashlyticsReport.Session.Application.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1618432855:
                    if (strNextName.equals("identifier")) {
                        c = 0;
                        break;
                    }
                    break;
                case -519438642:
                    if (strNextName.equals("developmentPlatform")) {
                        c = 4;
                        break;
                    }
                    break;
                case 213652010:
                    if (strNextName.equals("developmentPlatformVersion")) {
                        c = 5;
                        break;
                    }
                    break;
                case 351608024:
                    if (strNextName.equals("version")) {
                        c = 1;
                        break;
                    }
                    break;
                case 719853845:
                    if (strNextName.equals("installationUuid")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1975623094:
                    if (strNextName.equals("displayVersion")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setIdentifier(jsonReader.nextString());
            } else if (c == 1) {
                builder.setVersion(jsonReader.nextString());
            } else if (c == 2) {
                builder.setDisplayVersion(jsonReader.nextString());
            } else if (c == 3) {
                builder.setInstallationUuid(jsonReader.nextString());
            } else if (c == 4) {
                builder.setDevelopmentPlatform(jsonReader.nextString());
            } else if (c == 5) {
                builder.setDevelopmentPlatformVersion(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static CrashlyticsReport.Session.OperatingSystem parseOs(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.OperatingSystem.Builder builder = CrashlyticsReport.Session.OperatingSystem.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -911706486:
                    if (strNextName.equals("buildVersion")) {
                        c = 2;
                        break;
                    }
                    break;
                case -293026577:
                    if (strNextName.equals("jailbroken")) {
                        c = 3;
                        break;
                    }
                    break;
                case 351608024:
                    if (strNextName.equals("version")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1874684019:
                    if (strNextName.equals("platform")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setPlatform(jsonReader.nextInt());
            } else if (c == 1) {
                builder.setVersion(jsonReader.nextString());
            } else if (c == 2) {
                builder.setBuildVersion(jsonReader.nextString());
            } else if (c == 3) {
                builder.setJailbroken(jsonReader.nextBoolean());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static CrashlyticsReport.Session.Device parseDevice(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Device.Builder builder = CrashlyticsReport.Session.Device.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "arch":
                    builder.setArch(jsonReader.nextInt());
                    break;
                case "model":
                    builder.setModel(jsonReader.nextString());
                    break;
                case "cores":
                    builder.setCores(jsonReader.nextInt());
                    break;
                case "ram":
                    builder.setRam(jsonReader.nextLong());
                    break;
                case "diskSpace":
                    builder.setDiskSpace(jsonReader.nextLong());
                    break;
                case "simulator":
                    builder.setSimulator(jsonReader.nextBoolean());
                    break;
                case "state":
                    builder.setState(jsonReader.nextInt());
                    break;
                case "manufacturer":
                    builder.setManufacturer(jsonReader.nextString());
                    break;
                case "modelClass":
                    builder.setModelClass(jsonReader.nextString());
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static CrashlyticsReport.Session.Event parseEvent(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Builder builder = CrashlyticsReport.Session.Event.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1335157162:
                    if (strNextName.equals("device")) {
                        c = 3;
                        break;
                    }
                    break;
                case 96801:
                    if (strNextName.equals("app")) {
                        c = 2;
                        break;
                    }
                    break;
                case 107332:
                    if (strNextName.equals("log")) {
                        c = 4;
                        break;
                    }
                    break;
                case 3575610:
                    if (strNextName.equals(ShareConstants.MEDIA_TYPE)) {
                        c = 1;
                        break;
                    }
                    break;
                case 55126294:
                    if (strNextName.equals("timestamp")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setTimestamp(jsonReader.nextLong());
            } else if (c == 1) {
                builder.setType(jsonReader.nextString());
            } else if (c == 2) {
                builder.setApp(parseEventApp(jsonReader));
            } else if (c == 3) {
                builder.setDevice(parseEventDevice(jsonReader));
            } else if (c == 4) {
                builder.setLog(parseEventLog(jsonReader));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static CrashlyticsReport.Session.Event.Application parseEventApp(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Builder builder = CrashlyticsReport.Session.Event.Application.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1332194002:
                    if (strNextName.equals("background")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1090974952:
                    if (strNextName.equals("execution")) {
                        c = 2;
                        break;
                    }
                    break;
                case 555169704:
                    if (strNextName.equals("customAttributes")) {
                        c = 3;
                        break;
                    }
                    break;
                case 928737948:
                    if (strNextName.equals("uiOrientation")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setBackground(Boolean.valueOf(jsonReader.nextBoolean()));
            } else if (c == 1) {
                builder.setUiOrientation(jsonReader.nextInt());
            } else if (c == 2) {
                builder.setExecution(parseEventExecution(jsonReader));
            } else if (c == 3) {
                builder.setCustomAttributes(parseArray(jsonReader, new ObjectParser() { // from class: com.google.firebase.crashlytics.internal.model.serialization.-$$Lambda$CrashlyticsReportJsonTransform$NhzBXeLMMIRDAHr-yokwvoZKlwo
                    @Override // com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform.ObjectParser
                    public final Object parse(JsonReader jsonReader2) {
                        return CrashlyticsReportJsonTransform.parseCustomAttribute(jsonReader2);
                    }
                }));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static CrashlyticsReport.Session.Event.Application.Execution parseEventExecution(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Execution.Builder builder = CrashlyticsReport.Session.Event.Application.Execution.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1337936983:
                    if (strNextName.equals("threads")) {
                        c = 0;
                        break;
                    }
                    break;
                case -902467928:
                    if (strNextName.equals("signal")) {
                        c = 2;
                        break;
                    }
                    break;
                case 937615455:
                    if (strNextName.equals("binaries")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1481625679:
                    if (strNextName.equals("exception")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setThreads(parseArray(jsonReader, new ObjectParser() { // from class: com.google.firebase.crashlytics.internal.model.serialization.-$$Lambda$CrashlyticsReportJsonTransform$NcslUolmMJ2rhykSKHnzkuM2hYY
                    @Override // com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform.ObjectParser
                    public final Object parse(JsonReader jsonReader2) {
                        return CrashlyticsReportJsonTransform.parseEventThread(jsonReader2);
                    }
                }));
            } else if (c == 1) {
                builder.setException(parseEventExecutionException(jsonReader));
            } else if (c == 2) {
                builder.setSignal(parseEventSignal(jsonReader));
            } else if (c == 3) {
                builder.setBinaries(parseArray(jsonReader, new ObjectParser() { // from class: com.google.firebase.crashlytics.internal.model.serialization.-$$Lambda$CrashlyticsReportJsonTransform$CDavwmIaUoxPoo5Upyr-1hcsJXE
                    @Override // com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform.ObjectParser
                    public final Object parse(JsonReader jsonReader2) {
                        return CrashlyticsReportJsonTransform.parseEventBinaryImage(jsonReader2);
                    }
                }));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static CrashlyticsReport.Session.Event.Application.Execution.Exception parseEventExecutionException(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Execution.Exception.Builder builder = CrashlyticsReport.Session.Event.Application.Execution.Exception.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1266514778:
                    if (strNextName.equals("frames")) {
                        c = 1;
                        break;
                    }
                    break;
                case -934964668:
                    if (strNextName.equals("reason")) {
                        c = 4;
                        break;
                    }
                    break;
                case 3575610:
                    if (strNextName.equals(ShareConstants.MEDIA_TYPE)) {
                        c = 3;
                        break;
                    }
                    break;
                case 91997906:
                    if (strNextName.equals("causedBy")) {
                        c = 0;
                        break;
                    }
                    break;
                case 581754413:
                    if (strNextName.equals("overflowCount")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setCausedBy(parseEventExecutionException(jsonReader));
            } else if (c == 1) {
                builder.setFrames(parseArray(jsonReader, $$Lambda$CrashlyticsReportJsonTransform$dIOx2087eNrDPBVbiDN535D2ZNE.INSTANCE));
            } else if (c == 2) {
                builder.setOverflowCount(jsonReader.nextInt());
            } else if (c == 3) {
                builder.setType(jsonReader.nextString());
            } else if (c == 4) {
                builder.setReason(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static CrashlyticsReport.Session.Event.Application.Execution.Signal parseEventSignal(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder builder = CrashlyticsReport.Session.Event.Application.Execution.Signal.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            int iHashCode = strNextName.hashCode();
            if (iHashCode != -1147692044) {
                if (iHashCode != 3059181) {
                    if (iHashCode == 3373707 && strNextName.equals("name")) {
                        c = 0;
                    }
                } else if (strNextName.equals("code")) {
                    c = 1;
                }
            } else if (strNextName.equals("address")) {
                c = 2;
            }
            if (c == 0) {
                builder.setName(jsonReader.nextString());
            } else if (c == 1) {
                builder.setCode(jsonReader.nextString());
            } else if (c == 2) {
                builder.setAddress(jsonReader.nextLong());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static CrashlyticsReport.Session.Event.Application.Execution.BinaryImage parseEventBinaryImage(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Execution.BinaryImage.Builder builder = CrashlyticsReport.Session.Event.Application.Execution.BinaryImage.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case 3373707:
                    if (strNextName.equals("name")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3530753:
                    if (strNextName.equals(DbHelper.KEY_ATTACHMENT_SIZE)) {
                        c = 2;
                        break;
                    }
                    break;
                case 3601339:
                    if (strNextName.equals("uuid")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1153765347:
                    if (strNextName.equals("baseAddress")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setName(jsonReader.nextString());
            } else if (c == 1) {
                builder.setBaseAddress(jsonReader.nextLong());
            } else if (c == 2) {
                builder.setSize(jsonReader.nextLong());
            } else if (c == 3) {
                builder.setUuidFromUtf8Bytes(Base64.decode(jsonReader.nextString(), 2));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CrashlyticsReport.Session.Event.Application.Execution.Thread parseEventThread(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Execution.Thread.Builder builder = CrashlyticsReport.Session.Event.Application.Execution.Thread.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            int iHashCode = strNextName.hashCode();
            if (iHashCode != -1266514778) {
                if (iHashCode != 3373707) {
                    if (iHashCode == 2125650548 && strNextName.equals("importance")) {
                        c = 0;
                    }
                } else if (strNextName.equals("name")) {
                    c = 1;
                }
            } else if (strNextName.equals("frames")) {
                c = 2;
            }
            if (c == 0) {
                builder.setImportance(jsonReader.nextInt());
            } else if (c == 1) {
                builder.setName(jsonReader.nextString());
            } else if (c == 2) {
                builder.setFrames(parseArray(jsonReader, $$Lambda$CrashlyticsReportJsonTransform$dIOx2087eNrDPBVbiDN535D2ZNE.INSTANCE));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static CrashlyticsReport.Session.Event.Application.Execution.Thread.Frame parseEventFrame(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Application.Execution.Thread.Frame.Builder builder = CrashlyticsReport.Session.Event.Application.Execution.Thread.Frame.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1019779949:
                    if (strNextName.equals("offset")) {
                        c = 2;
                        break;
                    }
                    break;
                case -887523944:
                    if (strNextName.equals("symbol")) {
                        c = 4;
                        break;
                    }
                    break;
                case R2.string.done_button /* 3571 */:
                    if (strNextName.equals("pc")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3143036:
                    if (strNextName.equals("file")) {
                        c = 1;
                        break;
                    }
                    break;
                case 2125650548:
                    if (strNextName.equals("importance")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setImportance(jsonReader.nextInt());
            } else if (c == 1) {
                builder.setFile(jsonReader.nextString());
            } else if (c == 2) {
                builder.setOffset(jsonReader.nextLong());
            } else if (c == 3) {
                builder.setPc(jsonReader.nextLong());
            } else if (c == 4) {
                builder.setSymbol(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static CrashlyticsReport.Session.Event.Device parseEventDevice(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Device.Builder builder = CrashlyticsReport.Session.Event.Device.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            switch (strNextName.hashCode()) {
                case -1708606089:
                    if (strNextName.equals("batteryLevel")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1455558134:
                    if (strNextName.equals("batteryVelocity")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1439500848:
                    if (strNextName.equals("orientation")) {
                        c = 4;
                        break;
                    }
                    break;
                case 279795450:
                    if (strNextName.equals("diskUsed")) {
                        c = 2;
                        break;
                    }
                    break;
                case 976541947:
                    if (strNextName.equals("ramUsed")) {
                        c = 5;
                        break;
                    }
                    break;
                case 1516795582:
                    if (strNextName.equals("proximityOn")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                builder.setBatteryLevel(Double.valueOf(jsonReader.nextDouble()));
            } else if (c == 1) {
                builder.setBatteryVelocity(jsonReader.nextInt());
            } else if (c == 2) {
                builder.setDiskUsed(jsonReader.nextLong());
            } else if (c == 3) {
                builder.setProximityOn(jsonReader.nextBoolean());
            } else if (c == 4) {
                builder.setOrientation(jsonReader.nextInt());
            } else if (c == 5) {
                builder.setRamUsed(jsonReader.nextLong());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static CrashlyticsReport.Session.Event.Log parseEventLog(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.Session.Event.Log.Builder builder = CrashlyticsReport.Session.Event.Log.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            if (strNextName.hashCode() == 951530617 && strNextName.equals("content")) {
                c = 0;
            }
            if (c == 0) {
                builder.setContent(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CrashlyticsReport.CustomAttribute parseCustomAttribute(JsonReader jsonReader) throws IOException {
        CrashlyticsReport.CustomAttribute.Builder builder = CrashlyticsReport.CustomAttribute.builder();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            char c = 65535;
            int iHashCode = strNextName.hashCode();
            if (iHashCode != 106079) {
                if (iHashCode == 111972721 && strNextName.equals("value")) {
                    c = 1;
                }
            } else if (strNextName.equals("key")) {
                c = 0;
            }
            if (c == 0) {
                builder.setKey(jsonReader.nextString());
            } else if (c == 1) {
                builder.setValue(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private static <T> ImmutableList<T> parseArray(JsonReader jsonReader, ObjectParser<T> objectParser) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(objectParser.parse(jsonReader));
        }
        jsonReader.endArray();
        return ImmutableList.from(arrayList);
    }
}
