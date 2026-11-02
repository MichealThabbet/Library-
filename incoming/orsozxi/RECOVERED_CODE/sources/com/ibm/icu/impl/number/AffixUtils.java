package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class AffixUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int STATE_AFTER_QUOTE = 3;
    private static final int STATE_BASE = 0;
    private static final int STATE_FIFTH_CURR = 8;
    private static final int STATE_FIRST_CURR = 4;
    private static final int STATE_FIRST_QUOTE = 1;
    private static final int STATE_FOURTH_CURR = 7;
    private static final int STATE_INSIDE_QUOTE = 2;
    private static final int STATE_OVERFLOW_CURR = 9;
    private static final int STATE_SECOND_CURR = 5;
    private static final int STATE_THIRD_CURR = 6;
    private static final int TYPE_CODEPOINT = 0;
    public static final int TYPE_CURRENCY_DOUBLE = -6;
    public static final int TYPE_CURRENCY_OVERFLOW = -15;
    public static final int TYPE_CURRENCY_QUAD = -8;
    public static final int TYPE_CURRENCY_QUINT = -9;
    public static final int TYPE_CURRENCY_SINGLE = -5;
    public static final int TYPE_CURRENCY_TRIPLE = -7;
    public static final int TYPE_MINUS_SIGN = -1;
    public static final int TYPE_PERCENT = -3;
    public static final int TYPE_PERMILLE = -4;
    public static final int TYPE_PLUS_SIGN = -2;

    public interface SymbolProvider {
        CharSequence getSymbol(int i);
    }

    public interface TokenConsumer {
        void consumeToken(int i);
    }

    private static int getCodePoint(long j) {
        return (int) (j >>> 40);
    }

    private static int getOffset(long j) {
        return (int) (j & (-1));
    }

    private static int getState(long j) {
        return (int) ((j >>> 36) & 15);
    }

    private static int getType(long j) {
        return (int) ((j >>> 32) & 15);
    }

    private static long makeTag(int i, int i2, int i3, int i4) {
        return ((-i2) << 32) | i | 0 | (i3 << 36) | (i4 << 40);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int estimateLength(java.lang.CharSequence r9) {
        /*
            r0 = 0
            if (r9 != 0) goto L4
            return r0
        L4:
            r1 = 0
            r2 = 0
            r3 = 0
        L7:
            int r4 = r9.length()
            r5 = 2
            r6 = 1
            if (r1 >= r4) goto L41
            int r4 = java.lang.Character.codePointAt(r9, r1)
            r7 = 3
            r8 = 39
            if (r2 == 0) goto L35
            if (r2 == r6) goto L2b
            if (r2 == r5) goto L27
            if (r2 != r7) goto L21
            if (r4 != r8) goto L39
            goto L31
        L21:
            java.lang.AssertionError r9 = new java.lang.AssertionError
            r9.<init>()
            throw r9
        L27:
            if (r4 != r8) goto L39
            r2 = 3
            goto L3b
        L2b:
            if (r4 != r8) goto L31
            int r3 = r3 + 1
            r2 = 0
            goto L3b
        L31:
            int r3 = r3 + 1
            r2 = 2
            goto L3b
        L35:
            if (r4 != r8) goto L39
            r2 = 1
            goto L3b
        L39:
            int r3 = r3 + 1
        L3b:
            int r4 = java.lang.Character.charCount(r4)
            int r1 = r1 + r4
            goto L7
        L41:
            if (r2 == r6) goto L46
            if (r2 == r5) goto L46
            return r3
        L46:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unterminated quote: \""
            r1.append(r2)
            r1.append(r9)
            java.lang.String r9 = "\""
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.<init>(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.AffixUtils.estimateLength(java.lang.CharSequence):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int escape(java.lang.CharSequence r8, java.lang.StringBuilder r9) {
        /*
            r0 = 0
            if (r8 != 0) goto L4
            return r0
        L4:
            int r1 = r9.length()
            r2 = 0
            r3 = 0
        La:
            int r4 = r8.length()
            r5 = 2
            r6 = 39
            if (r2 >= r4) goto L54
            int r4 = java.lang.Character.codePointAt(r8, r2)
            r7 = 37
            if (r4 == r7) goto L41
            if (r4 == r6) goto L3b
            r7 = 43
            if (r4 == r7) goto L41
            r7 = 45
            if (r4 == r7) goto L41
            r7 = 164(0xa4, float:2.3E-43)
            if (r4 == r7) goto L41
            r7 = 8240(0x2030, float:1.1547E-41)
            if (r4 == r7) goto L41
            if (r3 != r5) goto L37
            r9.append(r6)
            r9.appendCodePoint(r4)
            r3 = 0
            goto L4e
        L37:
            r9.appendCodePoint(r4)
            goto L4e
        L3b:
            java.lang.String r5 = "''"
            r9.append(r5)
            goto L4e
        L41:
            if (r3 != 0) goto L4b
            r9.append(r6)
            r9.appendCodePoint(r4)
            r3 = 2
            goto L4e
        L4b:
            r9.appendCodePoint(r4)
        L4e:
            int r4 = java.lang.Character.charCount(r4)
            int r2 = r2 + r4
            goto La
        L54:
            if (r3 != r5) goto L59
            r9.append(r6)
        L59:
            int r8 = r9.length()
            int r8 = r8 - r1
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.AffixUtils.escape(java.lang.CharSequence, java.lang.StringBuilder):int");
    }

    public static String escape(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        escape(charSequence, sb);
        return sb.toString();
    }

    public static final NumberFormat.Field getFieldForType(int i) {
        if (i != -15) {
            switch (i) {
                case -9:
                    return NumberFormat.Field.CURRENCY;
                case -8:
                    return NumberFormat.Field.CURRENCY;
                case -7:
                    return NumberFormat.Field.CURRENCY;
                case -6:
                    return NumberFormat.Field.CURRENCY;
                case -5:
                    return NumberFormat.Field.CURRENCY;
                case -4:
                    return NumberFormat.Field.PERMILLE;
                case -3:
                    return NumberFormat.Field.PERCENT;
                case -2:
                    return NumberFormat.Field.SIGN;
                case -1:
                    return NumberFormat.Field.SIGN;
                default:
                    throw new AssertionError();
            }
        }
        return NumberFormat.Field.CURRENCY;
    }

    public static int unescape(CharSequence charSequence, FormattedStringBuilder formattedStringBuilder, int i, SymbolProvider symbolProvider, NumberFormat.Field field) {
        int iInsertCodePoint;
        int i2 = 0;
        long jNextToken = 0;
        while (hasNext(jNextToken, charSequence)) {
            jNextToken = nextToken(jNextToken, charSequence);
            int typeOrCp = getTypeOrCp(jNextToken);
            if (typeOrCp == -15) {
                iInsertCodePoint = formattedStringBuilder.insertCodePoint(i + i2, UCharacter.REPLACEMENT_CHAR, NumberFormat.Field.CURRENCY);
            } else if (typeOrCp < 0) {
                iInsertCodePoint = formattedStringBuilder.insert(i + i2, symbolProvider.getSymbol(typeOrCp), getFieldForType(typeOrCp));
            } else {
                iInsertCodePoint = formattedStringBuilder.insertCodePoint(i + i2, typeOrCp, field);
            }
            i2 += iInsertCodePoint;
        }
        return i2;
    }

    public static int unescapedCount(CharSequence charSequence, boolean z, SymbolProvider symbolProvider) {
        int iCharCount;
        long jNextToken = 0;
        int i = 0;
        while (hasNext(jNextToken, charSequence)) {
            jNextToken = nextToken(jNextToken, charSequence);
            int typeOrCp = getTypeOrCp(jNextToken);
            if (typeOrCp == -15) {
                i++;
            } else {
                if (typeOrCp < 0) {
                    CharSequence symbol = symbolProvider.getSymbol(typeOrCp);
                    if (z) {
                        iCharCount = symbol.length();
                    } else {
                        iCharCount = Character.codePointCount(symbol, 0, symbol.length());
                    }
                } else {
                    iCharCount = z ? Character.charCount(typeOrCp) : 1;
                }
                i += iCharCount;
            }
        }
        return i;
    }

    public static boolean containsType(CharSequence charSequence, int i) {
        if (charSequence != null && charSequence.length() != 0) {
            long jNextToken = 0;
            while (hasNext(jNextToken, charSequence)) {
                jNextToken = nextToken(jNextToken, charSequence);
                if (getTypeOrCp(jNextToken) == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasCurrencySymbols(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() != 0) {
            long jNextToken = 0;
            while (hasNext(jNextToken, charSequence)) {
                jNextToken = nextToken(jNextToken, charSequence);
                int typeOrCp = getTypeOrCp(jNextToken);
                if (typeOrCp < 0 && getFieldForType(typeOrCp) == NumberFormat.Field.CURRENCY) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String replaceType(CharSequence charSequence, int i, char c) {
        if (charSequence == null || charSequence.length() == 0) {
            return "";
        }
        char[] charArray = charSequence.toString().toCharArray();
        long jNextToken = 0;
        while (hasNext(jNextToken, charSequence)) {
            jNextToken = nextToken(jNextToken, charSequence);
            if (getTypeOrCp(jNextToken) == i) {
                charArray[getOffset(jNextToken) - 1] = c;
            }
        }
        return new String(charArray);
    }

    public static boolean containsOnlySymbolsAndIgnorables(CharSequence charSequence, UnicodeSet unicodeSet) {
        if (charSequence == null) {
            return true;
        }
        long jNextToken = 0;
        while (hasNext(jNextToken, charSequence)) {
            jNextToken = nextToken(jNextToken, charSequence);
            int typeOrCp = getTypeOrCp(jNextToken);
            if (typeOrCp >= 0 && !unicodeSet.contains(typeOrCp)) {
                return false;
            }
        }
        return true;
    }

    public static void iterateWithConsumer(CharSequence charSequence, TokenConsumer tokenConsumer) {
        long jNextToken = 0;
        while (hasNext(jNextToken, charSequence)) {
            jNextToken = nextToken(jNextToken, charSequence);
            tokenConsumer.consumeToken(getTypeOrCp(jNextToken));
        }
    }

    private static long nextToken(long j, CharSequence charSequence) {
        int offset = getOffset(j);
        int state = getState(j);
        while (offset < charSequence.length()) {
            int iCodePointAt = Character.codePointAt(charSequence, offset);
            int iCharCount = Character.charCount(iCodePointAt);
            switch (state) {
                case 0:
                    if (iCodePointAt == 37) {
                        return makeTag(offset + iCharCount, -3, 0, 0);
                    }
                    if (iCodePointAt == 39) {
                        state = 1;
                        break;
                    } else {
                        if (iCodePointAt == 43) {
                            return makeTag(offset + iCharCount, -2, 0, 0);
                        }
                        if (iCodePointAt == 45) {
                            return makeTag(offset + iCharCount, -1, 0, 0);
                        }
                        if (iCodePointAt == 164) {
                            state = 4;
                            break;
                        } else {
                            if (iCodePointAt == 8240) {
                                return makeTag(offset + iCharCount, -4, 0, 0);
                            }
                            return makeTag(offset + iCharCount, 0, 0, iCodePointAt);
                        }
                    }
                case 1:
                    if (iCodePointAt == 39) {
                        return makeTag(offset + iCharCount, 0, 0, iCodePointAt);
                    }
                    return makeTag(offset + iCharCount, 0, 2, iCodePointAt);
                case 2:
                    if (iCodePointAt != 39) {
                        return makeTag(offset + iCharCount, 0, 2, iCodePointAt);
                    }
                    state = 3;
                    break;
                case 3:
                    if (iCodePointAt == 39) {
                        return makeTag(offset + iCharCount, 0, 2, iCodePointAt);
                    }
                    state = 0;
                    continue;
                case 4:
                    if (iCodePointAt != 164) {
                        return makeTag(offset, -5, 0, 0);
                    }
                    state = 5;
                    break;
                case 5:
                    if (iCodePointAt != 164) {
                        return makeTag(offset, -6, 0, 0);
                    }
                    state = 6;
                    break;
                case 6:
                    if (iCodePointAt != 164) {
                        return makeTag(offset, -7, 0, 0);
                    }
                    state = 7;
                    break;
                case 7:
                    if (iCodePointAt != 164) {
                        return makeTag(offset, -8, 0, 0);
                    }
                    state = 8;
                    break;
                case 8:
                    if (iCodePointAt != 164) {
                        return makeTag(offset, -9, 0, 0);
                    }
                    state = 9;
                    break;
                case 9:
                    if (iCodePointAt != 164) {
                        return makeTag(offset, -15, 0, 0);
                    }
                    break;
                default:
                    throw new AssertionError();
            }
            offset += iCharCount;
        }
        switch (state) {
            case 0:
                return -1L;
            case 1:
            case 2:
                throw new IllegalArgumentException("Unterminated quote in pattern affix: \"" + ((Object) charSequence) + "\"");
            case 3:
                return -1L;
            case 4:
                return makeTag(offset, -5, 0, 0);
            case 5:
                return makeTag(offset, -6, 0, 0);
            case 6:
                return makeTag(offset, -7, 0, 0);
            case 7:
                return makeTag(offset, -8, 0, 0);
            case 8:
                return makeTag(offset, -9, 0, 0);
            case 9:
                return makeTag(offset, -15, 0, 0);
            default:
                throw new AssertionError();
        }
    }

    private static boolean hasNext(long j, CharSequence charSequence) {
        int state = getState(j);
        int offset = getOffset(j);
        if (state == 2 && offset == charSequence.length() - 1 && charSequence.charAt(offset) == '\'') {
            return false;
        }
        return state != 0 || offset < charSequence.length();
    }

    private static int getTypeOrCp(long j) {
        int type = getType(j);
        return type == 0 ? getCodePoint(j) : -type;
    }
}
