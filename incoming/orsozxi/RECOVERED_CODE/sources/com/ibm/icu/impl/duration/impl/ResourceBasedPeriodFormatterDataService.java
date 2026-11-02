package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class ResourceBasedPeriodFormatterDataService extends PeriodFormatterDataService {
    private static final String PATH = "data/";
    private static final ResourceBasedPeriodFormatterDataService singleton = new ResourceBasedPeriodFormatterDataService();
    private Collection<String> availableLocales;
    private PeriodFormatterData lastData = null;
    private String lastLocale = null;
    private Map<String, PeriodFormatterData> cache = new HashMap();

    public static ResourceBasedPeriodFormatterDataService getInstance() {
        return singleton;
    }

    private ResourceBasedPeriodFormatterDataService() throws IOException {
        ArrayList arrayList = new ArrayList();
        InputStream requiredStream = ICUData.getRequiredStream(getClass(), "data/index.txt");
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(requiredStream, "UTF-8"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    String strTrim = line.trim();
                    if (!strTrim.startsWith("#") && strTrim.length() != 0) {
                        arrayList.add(strTrim);
                    }
                }
                bufferedReader.close();
                this.availableLocales = Collections.unmodifiableList(arrayList);
            } catch (IOException e) {
                throw new IllegalStateException("IO Error reading data/index.txt: " + e.toString());
            }
        } finally {
            try {
                requiredStream.close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.PeriodFormatterDataService
    public PeriodFormatterData get(String str) {
        int iIndexOf = str.indexOf(64);
        if (iIndexOf != -1) {
            str = str.substring(0, iIndexOf);
        }
        synchronized (this) {
            if (this.lastLocale != null && this.lastLocale.equals(str)) {
                return this.lastData;
            }
            PeriodFormatterData periodFormatterData = this.cache.get(str);
            if (periodFormatterData == null) {
                String strSubstring = str;
                while (true) {
                    if (!this.availableLocales.contains(strSubstring)) {
                        int iLastIndexOf = strSubstring.lastIndexOf(BaseLocale.SEP);
                        if (iLastIndexOf > -1) {
                            strSubstring = strSubstring.substring(0, iLastIndexOf);
                        } else {
                            if ("test".equals(strSubstring)) {
                                strSubstring = null;
                                break;
                            }
                            strSubstring = "test";
                        }
                    } else {
                        break;
                    }
                }
                if (strSubstring != null) {
                    String str2 = "data/pfd_" + strSubstring + ".xml";
                    try {
                        InputStreamReader inputStreamReader = new InputStreamReader(ICUData.getRequiredStream(getClass(), str2), "UTF-8");
                        DataRecord dataRecord = DataRecord.read(strSubstring, new XMLRecordReader(inputStreamReader));
                        inputStreamReader.close();
                        if (dataRecord != null) {
                            periodFormatterData = new PeriodFormatterData(str, dataRecord);
                        }
                        this.cache.put(str, periodFormatterData);
                    } catch (UnsupportedEncodingException unused) {
                        throw new MissingResourceException("Unhandled encoding for resource " + str2, str2, "");
                    } catch (IOException e) {
                        throw new ICUUncheckedIOException("Failed to close() resource " + str2, e);
                    }
                } else {
                    throw new MissingResourceException("Duration data not found for  " + str, PATH, str);
                }
            }
            this.lastData = periodFormatterData;
            this.lastLocale = str;
            return periodFormatterData;
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.PeriodFormatterDataService
    public Collection<String> getAvailableLocales() {
        return this.availableLocales;
    }
}
