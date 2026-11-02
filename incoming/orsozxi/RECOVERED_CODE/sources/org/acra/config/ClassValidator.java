package org.acra.config;

import java.lang.reflect.Modifier;

/* loaded from: classes3.dex */
public final class ClassValidator {
    private ClassValidator() {
    }

    public static void check(Class<?>... clsArr) throws ACRAConfigurationException, NoSuchMethodException, SecurityException {
        for (Class<?> cls : clsArr) {
            if (cls.isInterface()) {
                throw new ACRAConfigurationException("Expected class, but found interface " + cls.getName() + ".");
            }
            if (Modifier.isAbstract(cls.getModifiers())) {
                throw new ACRAConfigurationException("Class " + cls.getName() + " cannot be abstract.");
            }
            if (cls.getEnclosingClass() != null && !Modifier.isStatic(cls.getModifiers())) {
                throw new ACRAConfigurationException("Class " + cls.getName() + " has to be static.");
            }
            try {
                cls.getConstructor(new Class[0]);
            } catch (NoSuchMethodException e) {
                throw new ACRAConfigurationException("Class " + cls.getName() + " is missing a no-args Constructor.", e);
            }
        }
    }
}
