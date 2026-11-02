package net.fortuna.ical4j.util;

import java.io.UnsupportedEncodingException;
import net.fortuna.ical4j.model.parameter.Encoding;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.StringDecoder;

/* loaded from: classes3.dex */
public abstract class DecoderFactory {
    public static final String KEY_FACTORY_CLASS = "net.fortuna.ical4j.factory.decoder";
    private static DecoderFactory instance = (DecoderFactory) Configurator.getObjectProperty(KEY_FACTORY_CLASS).orElse(new DefaultDecoderFactory());

    public abstract BinaryDecoder createBinaryDecoder(Encoding encoding) throws UnsupportedEncodingException;

    public abstract StringDecoder createStringDecoder(Encoding encoding) throws UnsupportedEncodingException;

    public static DecoderFactory getInstance() {
        return instance;
    }
}
