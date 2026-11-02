package net.fortuna.ical4j.model;

@Deprecated
/* loaded from: classes3.dex */
public final class ParameterFactoryRegistry extends ParameterFactoryImpl {
    private static final long serialVersionUID = -3372324894953715583L;

    public void register(String str, ParameterFactory parameterFactory) {
        registerExtendedFactory(str, parameterFactory);
    }
}
