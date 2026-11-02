package org.apache.poi.ss.usermodel;

/* loaded from: classes3.dex */
public enum FontCharset {
    ANSI(0),
    DEFAULT(1),
    SYMBOL(2),
    MAC(77),
    SHIFTJIS(128),
    HANGEUL(129),
    JOHAB(130),
    GB2312(134),
    CHINESEBIG5(136),
    GREEK(161),
    TURKISH(162),
    VIETNAMESE(163),
    HEBREW(177),
    ARABIC(178),
    BALTIC(186),
    RUSSIAN(204),
    THAI(222),
    EASTEUROPE(238),
    OEM(255);

    private static FontCharset[] _table = new FontCharset[256];
    private int charset;

    static {
        for (FontCharset fontCharset : values()) {
            _table[fontCharset.getValue()] = fontCharset;
        }
    }

    FontCharset(int i) {
        this.charset = i;
    }

    public int getValue() {
        return this.charset;
    }

    public static FontCharset valueOf(int i) {
        return _table[i];
    }
}
