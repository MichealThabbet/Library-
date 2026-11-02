package net.fortuna.ical4j.util;

import java.io.UnsupportedEncodingException;
import net.fortuna.ical4j.model.parameter.Encoding;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes3.dex */
public abstract class EncoderFactory {
    public static final String KEY_FACTORY_CLASS = "net.fortuna.ical4j.factory.encoder";
    private static EncoderFactory instance = (EncoderFactory) Configurator.getObjectProperty(KEY_FACTORY_CLASS).orElse(new DefaultEncoderFactory());

    public abstract BinaryEncoder createBinaryEncoder(Encoding encoding) throws UnsupportedEncodingException;

    public abstract StringEncoder createStringEncoder(Encoding encoding) throws UnsupportedEncodingException;

    public static EncoderFactory getInstance() {
        return instance;
    }
}
