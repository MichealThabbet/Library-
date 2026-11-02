package com.google.firebase.iid;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-iid@@21.1.0 */
/* loaded from: classes2.dex */
final /* synthetic */ class Registrar$FIIDInternalAdapter$$Lambda$0 implements Continuation {
    static final Continuation $instance = new Registrar$FIIDInternalAdapter$$Lambda$0();

    private Registrar$FIIDInternalAdapter$$Lambda$0() {
    }

    @Override // com.google.android.gms.tasks.Continuation
    public Object then(Task task) {
        return ((InstanceIdResult) task.getResult()).getToken();
    }
}
