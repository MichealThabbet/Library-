package com.sun.mail.handlers;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.awt.Image;
import javax.activation.ActivationDataFlavor;

/* loaded from: classes2.dex */
public class image_jpeg extends image_gif {
    private static ActivationDataFlavor[] myDF = {new ActivationDataFlavor(Image.class, ConstantsBase.MIME_TYPE_IMAGE, "JPEG Image")};

    @Override // com.sun.mail.handlers.image_gif, com.sun.mail.handlers.handler_base
    protected ActivationDataFlavor[] getDataFlavors() {
        return myDF;
    }
}
