package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

/* loaded from: classes3.dex */
class Utils {
    private static final int RADIX = 16;

    Utils() {
    }

    static int digit16(byte b) throws DecoderException {
        int iDigit = Character.digit((char) b, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + ((int) b));
    }

    static char hexDigit(int i) {
        return Character.toUpperCase(Character.forDigit(i & 15, 16));
    }
}
