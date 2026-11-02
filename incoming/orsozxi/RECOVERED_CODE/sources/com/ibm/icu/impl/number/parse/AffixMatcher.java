package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.PatternStringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes2.dex */
public class AffixMatcher implements NumberParseMatcher {
    public static final Comparator<AffixMatcher> COMPARATOR = new Comparator<AffixMatcher>() { // from class: com.ibm.icu.impl.number.parse.AffixMatcher.1
        @Override // java.util.Comparator
        public int compare(AffixMatcher affixMatcher, AffixMatcher affixMatcher2) {
            if (AffixMatcher.length(affixMatcher.prefix) != AffixMatcher.length(affixMatcher2.prefix)) {
                return AffixMatcher.length(affixMatcher.prefix) > AffixMatcher.length(affixMatcher2.prefix) ? -1 : 1;
            }
            if (AffixMatcher.length(affixMatcher.suffix) != AffixMatcher.length(affixMatcher2.suffix)) {
                return AffixMatcher.length(affixMatcher.suffix) > AffixMatcher.length(affixMatcher2.suffix) ? -1 : 1;
            }
            if (affixMatcher.equals(affixMatcher2)) {
                return 0;
            }
            return affixMatcher.hashCode() > affixMatcher2.hashCode() ? -1 : 1;
        }
    };
    private final int flags;
    private final AffixPatternMatcher prefix;
    private final AffixPatternMatcher suffix;

    private static boolean isInteresting(AffixPatternProvider affixPatternProvider, IgnorablesMatcher ignorablesMatcher, int i) {
        String string;
        String string2 = affixPatternProvider.getString(256);
        String string3 = affixPatternProvider.getString(0);
        String string4 = null;
        if (affixPatternProvider.hasNegativeSubpattern()) {
            string4 = affixPatternProvider.getString(768);
            string = affixPatternProvider.getString(512);
        } else {
            string = null;
        }
        return ((i & 256) == 0 && AffixUtils.containsOnlySymbolsAndIgnorables(string2, ignorablesMatcher.getSet()) && AffixUtils.containsOnlySymbolsAndIgnorables(string3, ignorablesMatcher.getSet()) && AffixUtils.containsOnlySymbolsAndIgnorables(string4, ignorablesMatcher.getSet()) && AffixUtils.containsOnlySymbolsAndIgnorables(string, ignorablesMatcher.getSet()) && !AffixUtils.containsType(string3, -2) && !AffixUtils.containsType(string3, -1) && !AffixUtils.containsType(string, -2) && !AffixUtils.containsType(string, -1)) ? false : true;
    }

    public static void createMatchers(AffixPatternProvider affixPatternProvider, NumberParserImpl numberParserImpl, AffixTokenMatcherFactory affixTokenMatcherFactory, IgnorablesMatcher ignorablesMatcher, int i) {
        AffixPatternMatcher affixPatternMatcher;
        AffixPatternMatcher affixPatternMatcher2;
        int i2;
        AffixPatternMatcher affixPatternMatcher3;
        if (isInteresting(affixPatternProvider, ignorablesMatcher, i)) {
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = new ArrayList(6);
            boolean z = (i & 128) != 0;
            PatternStringUtils.PatternSignType[] patternSignTypeArr = PatternStringUtils.PatternSignType.VALUES;
            int length = patternSignTypeArr.length;
            AffixPatternMatcher affixPatternMatcher4 = null;
            AffixPatternMatcher affixPatternMatcher5 = null;
            AffixPatternMatcher affixPatternMatcher6 = null;
            int i3 = 0;
            while (i3 < length) {
                PatternStringUtils.PatternSignType patternSignType = patternSignTypeArr[i3];
                if ((patternSignType != PatternStringUtils.PatternSignType.POS || (i & 1024) == 0) && !(patternSignType == PatternStringUtils.PatternSignType.POS_SIGN && (i & 1024) == 0)) {
                    affixPatternMatcher = affixPatternMatcher5;
                    affixPatternMatcher2 = affixPatternMatcher6;
                    i2 = i3;
                    PatternStringUtils.patternInfoToStringBuilder(affixPatternProvider, true, patternSignType, StandardPlural.OTHER, false, sb);
                    AffixPatternMatcher affixPatternMatcherFromAffixPattern = AffixPatternMatcher.fromAffixPattern(sb.toString(), affixTokenMatcherFactory, i);
                    PatternStringUtils.patternInfoToStringBuilder(affixPatternProvider, false, patternSignType, StandardPlural.OTHER, false, sb);
                    AffixPatternMatcher affixPatternMatcherFromAffixPattern2 = AffixPatternMatcher.fromAffixPattern(sb.toString(), affixTokenMatcherFactory, i);
                    if (patternSignType == PatternStringUtils.PatternSignType.POS) {
                        affixPatternMatcher = affixPatternMatcherFromAffixPattern2;
                        affixPatternMatcher6 = affixPatternMatcherFromAffixPattern;
                    } else if (!Objects.equals(affixPatternMatcherFromAffixPattern, affixPatternMatcher2) || !Objects.equals(affixPatternMatcherFromAffixPattern2, affixPatternMatcher)) {
                        affixPatternMatcher6 = affixPatternMatcher2;
                    }
                    int i4 = patternSignType == PatternStringUtils.PatternSignType.NEG ? 1 : 0;
                    arrayList.add(getInstance(affixPatternMatcherFromAffixPattern, affixPatternMatcherFromAffixPattern2, i4));
                    if (!z || affixPatternMatcherFromAffixPattern == null || affixPatternMatcherFromAffixPattern2 == null) {
                        affixPatternMatcher3 = null;
                    } else {
                        if (patternSignType == PatternStringUtils.PatternSignType.POS || !Objects.equals(affixPatternMatcherFromAffixPattern, affixPatternMatcher6)) {
                            affixPatternMatcher3 = null;
                            arrayList.add(getInstance(affixPatternMatcherFromAffixPattern, null, i4));
                        } else {
                            affixPatternMatcher3 = null;
                        }
                        if (patternSignType == PatternStringUtils.PatternSignType.POS || !Objects.equals(affixPatternMatcherFromAffixPattern2, affixPatternMatcher)) {
                            arrayList.add(getInstance(affixPatternMatcher3, affixPatternMatcherFromAffixPattern2, i4));
                        }
                    }
                    affixPatternMatcher5 = affixPatternMatcher;
                    affixPatternMatcher4 = affixPatternMatcher3;
                    i3 = i2 + 1;
                } else {
                    affixPatternMatcher = affixPatternMatcher5;
                    affixPatternMatcher2 = affixPatternMatcher6;
                    i2 = i3;
                }
                affixPatternMatcher5 = affixPatternMatcher;
                affixPatternMatcher6 = affixPatternMatcher2;
                affixPatternMatcher3 = null;
                affixPatternMatcher4 = affixPatternMatcher3;
                i3 = i2 + 1;
            }
            Collections.sort(arrayList, COMPARATOR);
            numberParserImpl.addMatchers(arrayList);
        }
    }

    private static final AffixMatcher getInstance(AffixPatternMatcher affixPatternMatcher, AffixPatternMatcher affixPatternMatcher2, int i) {
        return new AffixMatcher(affixPatternMatcher, affixPatternMatcher2, i);
    }

    private AffixMatcher(AffixPatternMatcher affixPatternMatcher, AffixPatternMatcher affixPatternMatcher2, int i) {
        this.prefix = affixPatternMatcher;
        this.suffix = affixPatternMatcher2;
        this.flags = i;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        boolean zMatch = false;
        if (!parsedNumber.seenNumber()) {
            if (parsedNumber.prefix == null && this.prefix != null) {
                int offset = stringSegment.getOffset();
                zMatch = this.prefix.match(stringSegment, parsedNumber);
                if (offset != stringSegment.getOffset()) {
                    parsedNumber.prefix = this.prefix.getPattern();
                }
            }
            return zMatch;
        }
        if (parsedNumber.suffix == null && this.suffix != null && matched(this.prefix, parsedNumber.prefix)) {
            int offset2 = stringSegment.getOffset();
            zMatch = this.suffix.match(stringSegment, parsedNumber);
            if (offset2 != stringSegment.getOffset()) {
                parsedNumber.suffix = this.suffix.getPattern();
            }
        }
        return zMatch;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean smokeTest(StringSegment stringSegment) {
        AffixPatternMatcher affixPatternMatcher;
        AffixPatternMatcher affixPatternMatcher2 = this.prefix;
        return (affixPatternMatcher2 != null && affixPatternMatcher2.smokeTest(stringSegment)) || ((affixPatternMatcher = this.suffix) != null && affixPatternMatcher.smokeTest(stringSegment));
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
        if (matched(this.prefix, parsedNumber.prefix) && matched(this.suffix, parsedNumber.suffix)) {
            if (parsedNumber.prefix == null) {
                parsedNumber.prefix = "";
            }
            if (parsedNumber.suffix == null) {
                parsedNumber.suffix = "";
            }
            parsedNumber.flags |= this.flags;
            AffixPatternMatcher affixPatternMatcher = this.prefix;
            if (affixPatternMatcher != null) {
                affixPatternMatcher.postProcess(parsedNumber);
            }
            AffixPatternMatcher affixPatternMatcher2 = this.suffix;
            if (affixPatternMatcher2 != null) {
                affixPatternMatcher2.postProcess(parsedNumber);
            }
        }
    }

    static boolean matched(AffixPatternMatcher affixPatternMatcher, String str) {
        return (affixPatternMatcher == null && str == null) || (affixPatternMatcher != null && affixPatternMatcher.getPattern().equals(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int length(AffixPatternMatcher affixPatternMatcher) {
        if (affixPatternMatcher == null) {
            return 0;
        }
        return affixPatternMatcher.getPattern().length();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AffixMatcher)) {
            return false;
        }
        AffixMatcher affixMatcher = (AffixMatcher) obj;
        return Objects.equals(this.prefix, affixMatcher.prefix) && Objects.equals(this.suffix, affixMatcher.suffix) && this.flags == affixMatcher.flags;
    }

    public int hashCode() {
        return (Objects.hashCode(this.prefix) ^ Objects.hashCode(this.suffix)) ^ this.flags;
    }

    public String toString() {
        boolean z = (this.flags & 1) != 0;
        StringBuilder sb = new StringBuilder();
        sb.append("<AffixMatcher");
        sb.append(z ? ":negative " : " ");
        sb.append(this.prefix);
        sb.append("#");
        sb.append(this.suffix);
        sb.append(">");
        return sb.toString();
    }
}
