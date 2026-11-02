package com.ibm.icu.impl;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.ListFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.text.UnicodeSet;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;

/* loaded from: classes2.dex */
public class FormattedValueStringBuilderImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static class SpanFieldPlaceholder implements FormattedStringBuilder.FieldWrapper {
        public int length;
        public Format.Field normalField;
        public UFormat.SpanField spanField;
        public int start;
        public Object value;

        @Override // com.ibm.icu.impl.FormattedStringBuilder.FieldWrapper
        public Format.Field unwrap() {
            return this.normalField;
        }
    }

    public static int findSpan(FormattedStringBuilder formattedStringBuilder, Object obj) {
        for (int i = formattedStringBuilder.zero; i < formattedStringBuilder.zero + formattedStringBuilder.length; i++) {
            if ((formattedStringBuilder.fields[i] instanceof SpanFieldPlaceholder) && ((SpanFieldPlaceholder) formattedStringBuilder.fields[i]).value.equals(obj)) {
                return i - formattedStringBuilder.zero;
            }
        }
        return -1;
    }

    public static void applySpanRange(FormattedStringBuilder formattedStringBuilder, UFormat.SpanField spanField, Object obj, int i, int i2) {
        for (int i3 = formattedStringBuilder.zero + i; i3 < formattedStringBuilder.zero + i2; i3++) {
            Object obj2 = formattedStringBuilder.fields[i3];
            SpanFieldPlaceholder spanFieldPlaceholder = new SpanFieldPlaceholder();
            spanFieldPlaceholder.spanField = spanField;
            spanFieldPlaceholder.normalField = (Format.Field) obj2;
            spanFieldPlaceholder.value = obj;
            spanFieldPlaceholder.start = i;
            spanFieldPlaceholder.length = i2 - i;
            formattedStringBuilder.fields[i3] = spanFieldPlaceholder;
        }
    }

    public static boolean nextFieldPosition(FormattedStringBuilder formattedStringBuilder, FieldPosition fieldPosition) {
        Format.Field fieldAttribute = fieldPosition.getFieldAttribute();
        if (fieldAttribute == null) {
            if (fieldPosition.getField() == 0) {
                fieldAttribute = NumberFormat.Field.INTEGER;
            } else {
                if (fieldPosition.getField() != 1) {
                    return false;
                }
                fieldAttribute = NumberFormat.Field.FRACTION;
            }
        }
        if (!(fieldAttribute instanceof NumberFormat.Field)) {
            throw new IllegalArgumentException("You must pass an instance of com.ibm.icu.text.NumberFormat.Field as your FieldPosition attribute.  You passed: " + fieldAttribute.getClass().toString());
        }
        ConstrainedFieldPosition constrainedFieldPosition = new ConstrainedFieldPosition();
        constrainedFieldPosition.constrainField(fieldAttribute);
        constrainedFieldPosition.setState(fieldAttribute, null, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
        if (nextPosition(formattedStringBuilder, constrainedFieldPosition, null)) {
            fieldPosition.setBeginIndex(constrainedFieldPosition.getStart());
            fieldPosition.setEndIndex(constrainedFieldPosition.getLimit());
            return true;
        }
        if (fieldAttribute == NumberFormat.Field.FRACTION && fieldPosition.getEndIndex() == 0) {
            int i = formattedStringBuilder.zero;
            boolean z = false;
            while (i < formattedStringBuilder.zero + formattedStringBuilder.length) {
                if (isIntOrGroup(formattedStringBuilder.fields[i]) || formattedStringBuilder.fields[i] == NumberFormat.Field.DECIMAL_SEPARATOR) {
                    z = true;
                } else if (z) {
                    break;
                }
                i++;
            }
            fieldPosition.setBeginIndex(i - formattedStringBuilder.zero);
            fieldPosition.setEndIndex(i - formattedStringBuilder.zero);
        }
        return false;
    }

    public static AttributedCharacterIterator toCharacterIterator(FormattedStringBuilder formattedStringBuilder, Format.Field field) {
        ConstrainedFieldPosition constrainedFieldPosition = new ConstrainedFieldPosition();
        AttributedString attributedString = new AttributedString(formattedStringBuilder.toString());
        while (nextPosition(formattedStringBuilder, constrainedFieldPosition, field)) {
            Object fieldValue = constrainedFieldPosition.getFieldValue();
            if (fieldValue == null) {
                fieldValue = constrainedFieldPosition.getField();
            }
            attributedString.addAttribute(constrainedFieldPosition.getField(), fieldValue, constrainedFieldPosition.getStart(), constrainedFieldPosition.getLimit());
        }
        return attributedString.getIterator();
    }

    static class NullField extends Format.Field {
        static final NullField END = new NullField("end");
        private static final long serialVersionUID = 1;

        private NullField(String str) {
            super(str);
        }
    }

    public static boolean nextPosition(FormattedStringBuilder formattedStringBuilder, ConstrainedFieldPosition constrainedFieldPosition, Format.Field field) {
        Object obj;
        SpanFieldPlaceholder spanFieldPlaceholder;
        boolean z = constrainedFieldPosition.getLimit() > 0 && (constrainedFieldPosition.getField() instanceof UFormat.SpanField) && constrainedFieldPosition.getStart() < constrainedFieldPosition.getLimit();
        boolean z2 = field != null && constrainedFieldPosition.getField() == field;
        boolean z3 = constrainedFieldPosition.getField() == NumberFormat.Field.INTEGER;
        int limit = formattedStringBuilder.zero + constrainedFieldPosition.getLimit();
        Object obj2 = null;
        int iTrimFront = -1;
        while (limit <= formattedStringBuilder.zero + formattedStringBuilder.length) {
            Object obj3 = limit < formattedStringBuilder.zero + formattedStringBuilder.length ? formattedStringBuilder.fields[limit] : NullField.END;
            if (obj2 == null) {
                if (limit > formattedStringBuilder.zero && z) {
                    SpanFieldPlaceholder spanFieldPlaceholder2 = (SpanFieldPlaceholder) formattedStringBuilder.fields[limit - 1];
                    if (spanFieldPlaceholder2.normalField == ListFormatter.Field.ELEMENT) {
                        if (constrainedFieldPosition.matchesField(ListFormatter.Field.ELEMENT, null)) {
                            int i = (limit - formattedStringBuilder.zero) - spanFieldPlaceholder2.length;
                            constrainedFieldPosition.setState(ListFormatter.Field.ELEMENT, null, i, spanFieldPlaceholder2.length + i);
                            return true;
                        }
                    } else {
                        limit -= spanFieldPlaceholder2.length;
                        obj3 = ((SpanFieldPlaceholder) formattedStringBuilder.fields[limit]).normalField;
                    }
                }
                if (constrainedFieldPosition.matchesField(NumberFormat.Field.INTEGER, null) && limit > formattedStringBuilder.zero && !z3 && !z2) {
                    int i2 = limit - 1;
                    if (isIntOrGroup(formattedStringBuilder.fields[i2]) && !isIntOrGroup(obj3)) {
                        while (i2 >= formattedStringBuilder.zero && isIntOrGroup(formattedStringBuilder.fields[i2])) {
                            i2--;
                        }
                        constrainedFieldPosition.setState(NumberFormat.Field.INTEGER, null, (i2 - formattedStringBuilder.zero) + 1, limit - formattedStringBuilder.zero);
                        return true;
                    }
                }
                if (field != null && constrainedFieldPosition.matchesField(field, null) && limit > formattedStringBuilder.zero && !z2) {
                    int i3 = limit - 1;
                    if (isNumericField(formattedStringBuilder.fields[i3]) && !isNumericField(obj3)) {
                        while (i3 >= formattedStringBuilder.zero && isNumericField(formattedStringBuilder.fields[i3])) {
                            i3--;
                        }
                        constrainedFieldPosition.setState(field, null, (i3 - formattedStringBuilder.zero) + 1, limit - formattedStringBuilder.zero);
                        return true;
                    }
                }
                if (obj3 instanceof SpanFieldPlaceholder) {
                    spanFieldPlaceholder = (SpanFieldPlaceholder) obj3;
                    obj = spanFieldPlaceholder.normalField;
                } else {
                    obj = obj3;
                    spanFieldPlaceholder = null;
                }
                if (spanFieldPlaceholder != null && (spanFieldPlaceholder.start == -1 || spanFieldPlaceholder.start == limit - formattedStringBuilder.zero)) {
                    if (constrainedFieldPosition.matchesField(spanFieldPlaceholder.spanField, spanFieldPlaceholder.value)) {
                        int i4 = limit - formattedStringBuilder.zero;
                        constrainedFieldPosition.setState(spanFieldPlaceholder.spanField, spanFieldPlaceholder.value, i4, spanFieldPlaceholder.length + i4);
                        return true;
                    }
                    if (spanFieldPlaceholder.normalField == ListFormatter.Field.ELEMENT) {
                        if (constrainedFieldPosition.matchesField(ListFormatter.Field.ELEMENT, null)) {
                            int i5 = limit - formattedStringBuilder.zero;
                            constrainedFieldPosition.setState(ListFormatter.Field.ELEMENT, null, i5, spanFieldPlaceholder.length + i5);
                            return true;
                        }
                        limit += spanFieldPlaceholder.length - 1;
                    }
                } else if (obj != NumberFormat.Field.INTEGER && obj != null && obj != NullField.END && constrainedFieldPosition.matchesField((Format.Field) obj, null)) {
                    obj2 = obj;
                    iTrimFront = limit - formattedStringBuilder.zero;
                }
                z = false;
                z2 = false;
                z3 = false;
            } else if (obj2 != obj3) {
                int iTrimBack = limit - formattedStringBuilder.zero;
                if (isTrimmable(obj2)) {
                    iTrimBack = trimBack(formattedStringBuilder, iTrimBack);
                }
                if (iTrimBack > iTrimFront) {
                    if (isTrimmable(obj2)) {
                        iTrimFront = trimFront(formattedStringBuilder, iTrimFront);
                    }
                    constrainedFieldPosition.setState((Format.Field) obj2, null, iTrimFront, iTrimBack);
                    return true;
                }
                limit--;
                obj2 = null;
                iTrimFront = -1;
            } else {
                continue;
            }
            limit++;
        }
        constrainedFieldPosition.setState(constrainedFieldPosition.getField(), constrainedFieldPosition.getFieldValue(), formattedStringBuilder.length, formattedStringBuilder.length);
        return false;
    }

    private static boolean isIntOrGroup(Object obj) {
        Format.Field fieldUnwrapField = FormattedStringBuilder.unwrapField(obj);
        return fieldUnwrapField == NumberFormat.Field.INTEGER || fieldUnwrapField == NumberFormat.Field.GROUPING_SEPARATOR;
    }

    private static boolean isNumericField(Object obj) {
        Format.Field fieldUnwrapField = FormattedStringBuilder.unwrapField(obj);
        return fieldUnwrapField == null || NumberFormat.Field.class.isAssignableFrom(fieldUnwrapField.getClass());
    }

    private static boolean isTrimmable(Object obj) {
        return (obj == NumberFormat.Field.GROUPING_SEPARATOR || (obj instanceof ListFormatter.Field)) ? false : true;
    }

    private static int trimBack(FormattedStringBuilder formattedStringBuilder, int i) {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES).spanBack(formattedStringBuilder, i, UnicodeSet.SpanCondition.CONTAINED);
    }

    private static int trimFront(FormattedStringBuilder formattedStringBuilder, int i) {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES).span(formattedStringBuilder, i, UnicodeSet.SpanCondition.CONTAINED);
    }
}
