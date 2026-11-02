package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.util.ULocale;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/* loaded from: classes2.dex */
public class LocalizedNumberFormatterAsFormat extends Format {
    private static final long serialVersionUID = 1;
    private final transient LocalizedNumberFormatter formatter;
    private final transient ULocale locale;

    public LocalizedNumberFormatterAsFormat(LocalizedNumberFormatter localizedNumberFormatter, ULocale uLocale) {
        this.formatter = localizedNumberFormatter;
        this.locale = uLocale;
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) throws IOException {
        if (!(obj instanceof Number)) {
            throw new IllegalArgumentException();
        }
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD((Number) obj);
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        this.formatter.formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (FormattedValueStringBuilderImpl.nextFieldPosition(formattedStringBuilder, fieldPosition) && stringBuffer.length() != 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + stringBuffer.length());
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + stringBuffer.length());
        }
        Utility.appendTo(formattedStringBuilder, stringBuffer);
        return stringBuffer;
    }

    @Override // java.text.Format
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        if (!(obj instanceof Number)) {
            throw new IllegalArgumentException();
        }
        return this.formatter.format((Number) obj).toCharacterIterator();
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public LocalizedNumberFormatter getNumberFormatter() {
        return this.formatter;
    }

    public int hashCode() {
        return this.formatter.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof LocalizedNumberFormatterAsFormat)) {
            return this.formatter.equals(((LocalizedNumberFormatterAsFormat) obj).getNumberFormatter());
        }
        return false;
    }

    private Object writeReplace() throws ObjectStreamException {
        Proxy proxy = new Proxy();
        proxy.languageTag = this.locale.toLanguageTag();
        proxy.skeleton = this.formatter.toSkeleton();
        return proxy;
    }

    static class Proxy implements Externalizable {
        private static final long serialVersionUID = 1;
        String languageTag;
        String skeleton;

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.languageTag);
            objectOutput.writeUTF(this.skeleton);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.languageTag = objectInput.readUTF();
            this.skeleton = objectInput.readUTF();
        }

        private Object readResolve() throws ObjectStreamException {
            return NumberFormatter.forSkeleton(this.skeleton).locale(ULocale.forLanguageTag(this.languageTag)).toFormat();
        }
    }
}
