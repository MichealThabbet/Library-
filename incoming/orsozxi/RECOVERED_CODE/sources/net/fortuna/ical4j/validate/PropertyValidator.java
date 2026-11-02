package net.fortuna.ical4j.validate;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public final class PropertyValidator implements Validator<Property> {
    public static final String ASSERT_NONE_MESSAGE = "Property [{0}] is not applicable";
    public static final String ASSERT_ONE_MESSAGE = "Property [{0}] must be specified once";
    public static final String ASSERT_ONE_OR_LESS_MESSAGE = "Property [{0}] must only be specified once";
    public static final String ASSERT_ONE_OR_MORE_MESSAGE = "Property [{0}] must be specified at least once";
    private final List<ValidationRule> rules;

    public PropertyValidator(List<ValidationRule> list) {
        this.rules = list;
    }

    @Override // net.fortuna.ical4j.validate.Validator
    public void validate(final Property property) throws ValidationException {
        for (ValidationRule validationRule : this.rules) {
            final boolean z = CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION) && validationRule.isRelaxedModeSupported();
            int i = AnonymousClass1.$SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[validationRule.getType().ordinal()];
            if (i == 1) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$UvXiOeFG2EHcL0HNTiA1ptjHeDc
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$Z3UlKnVsFGpTiWDcBWobzxNTh9U
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return PropertyValidator.lambda$validate$0(str, (ParameterList) obj2);
                            }
                        }, ParameterValidator.ASSERT_NONE_MESSAGE, z, property.getParameters(), str);
                    }
                });
            } else if (i == 2) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$icKHPsVFX1qM76gz9XogxVLPV0Q
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$atLpZEW9S98w0sftA5QyQFVHd3g
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return PropertyValidator.lambda$validate$2(str, (ParameterList) obj2);
                            }
                        }, ParameterValidator.ASSERT_ONE_MESSAGE, z, property.getParameters(), str);
                    }
                });
            } else if (i == 3) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$paAPSMrTslv6izJ6OTfiFvbt-C8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$7JRAjZsu410k1B-BnwFggYpIngI
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return PropertyValidator.lambda$validate$4(str, (ParameterList) obj2);
                            }
                        }, ParameterValidator.ASSERT_ONE_OR_LESS_MESSAGE, z, property.getParameters(), str);
                    }
                });
            }
        }
    }

    /* renamed from: net.fortuna.ical4j.validate.PropertyValidator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType;

        static {
            int[] iArr = new int[ValidationRule.ValidationType.values().length];
            $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType = iArr;
            try {
                iArr[ValidationRule.ValidationType.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[ValidationRule.ValidationType.One.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[ValidationRule.ValidationType.OneOrLess.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static /* synthetic */ boolean lambda$validate$0(String str, ParameterList parameterList) {
        return parameterList.getParameter(str) != null;
    }

    static /* synthetic */ boolean lambda$validate$2(String str, ParameterList parameterList) {
        return parameterList.getParameters(str).size() != 1;
    }

    static /* synthetic */ boolean lambda$validate$4(String str, ParameterList parameterList) {
        return parameterList.getParameters(str).size() > 1;
    }

    public static void assertOneOrLess(final String str, PropertyList propertyList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$UVYj5wvLrlpLzsIP9RE6CPnANTg
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PropertyValidator.lambda$assertOneOrLess$6(str, (PropertyList) obj);
            }
        }, ASSERT_ONE_OR_LESS_MESSAGE, false, propertyList, str);
    }

    static /* synthetic */ boolean lambda$assertOneOrLess$6(String str, PropertyList propertyList) {
        return propertyList.getProperties(str).size() > 1;
    }

    public static void assertOneOrMore(final String str, PropertyList propertyList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$HGowKWXqI_WI9fBiTQ8fNjAiZMY
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PropertyValidator.lambda$assertOneOrMore$7(str, (PropertyList) obj);
            }
        }, ASSERT_ONE_OR_MORE_MESSAGE, false, propertyList, str);
    }

    static /* synthetic */ boolean lambda$assertOneOrMore$7(String str, PropertyList propertyList) {
        return propertyList.getProperties(str).size() < 1;
    }

    public static void assertOne(final String str, PropertyList propertyList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$5YC2rZ5Fi1hyCRn1I4RIATZa8Gs
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PropertyValidator.lambda$assertOne$8(str, (PropertyList) obj);
            }
        }, ASSERT_ONE_MESSAGE, false, propertyList, str);
    }

    static /* synthetic */ boolean lambda$assertOne$8(String str, PropertyList propertyList) {
        return propertyList.getProperties(str).size() != 1;
    }

    public static void assertNone(final String str, PropertyList propertyList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$PropertyValidator$SwBAS-mf499-mERDcHw4ln2xRE0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PropertyValidator.lambda$assertNone$9(str, (PropertyList) obj);
            }
        }, ASSERT_NONE_MESSAGE, false, propertyList, str);
    }

    static /* synthetic */ boolean lambda$assertNone$9(String str, PropertyList propertyList) {
        return propertyList.getProperty(str) != null;
    }
}
