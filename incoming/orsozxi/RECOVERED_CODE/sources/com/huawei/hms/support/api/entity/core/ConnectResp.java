package com.huawei.hms.support.api.entity.core;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.annotation.Packed;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ConnectResp implements IMessageEntity {

    @Packed
    public List<Integer> protocolVersion = Arrays.asList(1, 2);

    @Packed
    public String sessionId;

    public String toString() {
        StringBuilder sb = new StringBuilder("protocol version:");
        Iterator<Integer> it2 = this.protocolVersion.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            sb.append(',');
        }
        return sb.toString();
    }
}
