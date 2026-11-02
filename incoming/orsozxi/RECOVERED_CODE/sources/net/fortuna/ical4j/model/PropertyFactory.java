package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Property;

/* loaded from: classes3.dex */
public interface PropertyFactory<T extends Property> extends Serializable {
    T createProperty();

    T createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException;

    boolean supports(String str);
}
