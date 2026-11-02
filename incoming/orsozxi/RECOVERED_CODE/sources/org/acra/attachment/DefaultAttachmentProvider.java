package org.acra.attachment;

import android.content.Context;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public class DefaultAttachmentProvider implements AttachmentUriProvider {
    @Override // org.acra.attachment.AttachmentUriProvider
    public List<Uri> getAttachments(Context context, CoreConfiguration coreConfiguration) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it2 = coreConfiguration.attachmentUris().iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            try {
                arrayList.add(Uri.parse(next));
            } catch (Exception e) {
                ACRA.log.e(ACRA.LOG_TAG, "Failed to parse Uri " + next, e);
            }
        }
        return arrayList;
    }
}
