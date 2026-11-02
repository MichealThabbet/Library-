package com.ibm.icu.impl;

import com.ibm.icu.text.ConstrainedFieldPosition;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.util.List;

/* loaded from: classes2.dex */
public class FormattedValueFieldPositionIteratorImpl {
    private FormattedValueFieldPositionIteratorImpl() {
    }

    private static class FieldWithValue extends Format.Field {
        private static final long serialVersionUID = -3850076447157793465L;
        public final Format.Field field;
        public final int value;

        public FieldWithValue(Format.Field field, int i) {
            super(field.toString());
            this.field = field;
            this.value = i;
        }
    }

    public static boolean nextPosition(List<FieldPosition> list, ConstrainedFieldPosition constrainedFieldPosition) {
        int size = list.size();
        int int64IterationContext = (int) constrainedFieldPosition.getInt64IterationContext();
        while (true) {
            if (int64IterationContext >= size) {
                break;
            }
            FieldPosition fieldPosition = list.get(int64IterationContext);
            Format.Field fieldAttribute = fieldPosition.getFieldAttribute();
            Integer numValueOf = null;
            if (fieldAttribute instanceof FieldWithValue) {
                FieldWithValue fieldWithValue = (FieldWithValue) fieldAttribute;
                numValueOf = Integer.valueOf(fieldWithValue.value);
                fieldAttribute = fieldWithValue.field;
            }
            if (constrainedFieldPosition.matchesField(fieldAttribute, numValueOf)) {
                constrainedFieldPosition.setState(fieldAttribute, numValueOf, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
                break;
            }
            int64IterationContext++;
        }
        constrainedFieldPosition.setInt64IterationContext(int64IterationContext == size ? int64IterationContext : int64IterationContext + 1);
        return int64IterationContext < size;
    }

    public static AttributedCharacterIterator toCharacterIterator(CharSequence charSequence, List<FieldPosition> list) {
        Object objValueOf;
        AttributedString attributedString = new AttributedString(charSequence.toString());
        for (int i = 0; i < list.size(); i++) {
            FieldPosition fieldPosition = list.get(i);
            Format.Field fieldAttribute = fieldPosition.getFieldAttribute();
            if (fieldAttribute instanceof FieldWithValue) {
                FieldWithValue fieldWithValue = (FieldWithValue) fieldAttribute;
                objValueOf = Integer.valueOf(fieldWithValue.value);
                fieldAttribute = fieldWithValue.field;
            } else {
                objValueOf = fieldAttribute;
            }
            attributedString.addAttribute(fieldAttribute, objValueOf, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
        }
        return attributedString.getIterator();
    }

    public static void addOverlapSpans(List<FieldPosition> list, Format.Field field, int i) {
        int size = list.size();
        int i2 = 0;
        int iMax = 0;
        int iMax2 = 0;
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        while (i2 < size) {
            FieldPosition fieldPosition = list.get(i2);
            i2++;
            int i3 = i2;
            while (true) {
                if (i3 < size) {
                    FieldPosition fieldPosition2 = list.get(i3);
                    if (fieldPosition.getFieldAttribute() == fieldPosition2.getFieldAttribute()) {
                        iMin = Math.min(iMin, fieldPosition.getBeginIndex());
                        iMax = Math.max(iMax, fieldPosition.getEndIndex());
                        iMin2 = Math.min(iMin2, fieldPosition2.getBeginIndex());
                        iMax2 = Math.max(iMax2, fieldPosition2.getEndIndex());
                        break;
                    }
                    i3++;
                }
            }
        }
        if (iMin != Integer.MAX_VALUE) {
            FieldPosition fieldPosition3 = new FieldPosition(new FieldWithValue(field, i));
            fieldPosition3.setBeginIndex(iMin);
            fieldPosition3.setEndIndex(iMax);
            list.add(fieldPosition3);
            FieldPosition fieldPosition4 = new FieldPosition(new FieldWithValue(field, 1 - i));
            fieldPosition4.setBeginIndex(iMin2);
            fieldPosition4.setEndIndex(iMax2);
            list.add(fieldPosition4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void sort(java.util.List<java.text.FieldPosition> r13) {
        /*
            int r0 = r13.size()
        L4:
            r1 = 0
            r2 = 1
            r3 = 0
            r4 = 1
        L8:
            int r5 = r0 + (-1)
            if (r3 >= r5) goto L7f
            java.lang.Object r5 = r13.get(r3)
            java.text.FieldPosition r5 = (java.text.FieldPosition) r5
            int r6 = r3 + 1
            java.lang.Object r7 = r13.get(r6)
            java.text.FieldPosition r7 = (java.text.FieldPosition) r7
            int r8 = r5.getBeginIndex()
            int r9 = r7.getBeginIndex()
            r10 = 0
            if (r8 == r9) goto L31
            int r8 = r7.getBeginIndex()
            int r9 = r5.getBeginIndex()
        L2e:
            int r8 = r8 - r9
            long r8 = (long) r8
            goto L72
        L31:
            int r8 = r5.getEndIndex()
            int r9 = r7.getEndIndex()
            if (r8 == r9) goto L44
            int r8 = r5.getEndIndex()
            int r9 = r7.getEndIndex()
            goto L2e
        L44:
            java.text.Format$Field r8 = r5.getFieldAttribute()
            java.text.Format$Field r9 = r7.getFieldAttribute()
            if (r8 == r9) goto L71
            java.text.Format$Field r8 = r5.getFieldAttribute()
            boolean r8 = r8 instanceof com.ibm.icu.impl.FormattedValueFieldPositionIteratorImpl.FieldWithValue
            java.text.Format$Field r9 = r7.getFieldAttribute()
            boolean r9 = r9 instanceof com.ibm.icu.impl.FormattedValueFieldPositionIteratorImpl.FieldWithValue
            if (r8 == 0) goto L61
            if (r9 != 0) goto L61
            r8 = 1
            goto L72
        L61:
            if (r9 == 0) goto L68
            if (r8 != 0) goto L68
            r8 = -1
            goto L72
        L68:
            int r8 = r5.hashCode()
            int r9 = r7.hashCode()
            goto L2e
        L71:
            r8 = r10
        L72:
            int r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r12 >= 0) goto L7d
            r13.set(r3, r7)
            r13.set(r6, r5)
            r4 = 0
        L7d:
            r3 = r6
            goto L8
        L7f:
            if (r4 == 0) goto L4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.FormattedValueFieldPositionIteratorImpl.sort(java.util.List):void");
    }
}
