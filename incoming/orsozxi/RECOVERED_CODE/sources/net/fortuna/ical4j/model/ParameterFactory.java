package net.fortuna.ical4j.model;

import java.io.Serializable;
import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Parameter;

/* loaded from: classes3.dex */
public interface ParameterFactory<T extends Parameter> extends Serializable {
    T createParameter(String str) throws URISyntaxException;

    boolean supports(String str);
}
