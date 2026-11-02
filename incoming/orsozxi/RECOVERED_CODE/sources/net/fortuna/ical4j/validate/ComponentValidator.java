package net.fortuna.ical4j.validate;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class ComponentValidator<T extends Component> implements Validator<T> {
    private static final String ASSERT_NONE_MESSAGE = "Component [{0}] is not applicable";
    private static final String ASSERT_ONE_OR_LESS_MESSAGE = "Component [{0}] must only be specified once";
    private final List<ValidationRule> rules;

    public ComponentValidator(List<ValidationRule> list) {
        this.rules = list;
    }

    @Override // net.fortuna.ical4j.validate.Validator
    public void validate(final T t) throws ValidationException {
        for (ValidationRule validationRule : this.rules) {
            final boolean z = CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION) && validationRule.isRelaxedModeSupported();
            int i = AnonymousClass1.$SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[validationRule.getType().ordinal()];
            if (i == 1) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$bzrFUF8Xue1GRhAjm3Jmd0XCQvY
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$7gQso9vnmgVQj-g7hM64bCqW2-A
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return ComponentValidator.lambda$validate$0(str, (PropertyList) obj2);
                            }
                        }, PropertyValidator.ASSERT_NONE_MESSAGE, z, t.getProperties(), str);
                    }
                });
            } else if (i == 2) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$5fVvCxDrtReQgTZZf9b64b6Di1E
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$8YcqJLKcOhR_XGUs0stf_1gk_-s
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return ComponentValidator.lambda$validate$2(str, (PropertyList) obj2);
                            }
                        }, PropertyValidator.ASSERT_ONE_MESSAGE, z, t.getProperties(), str);
                    }
                });
            } else if (i == 3) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$4wgciSp_a4uYDcC6VcCnmOh47xA
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$GvyDm3tmd4mEWkSG71HBnk-oMt4
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return ComponentValidator.lambda$validate$4(str, (PropertyList) obj2);
                            }
                        }, PropertyValidator.ASSERT_ONE_OR_LESS_MESSAGE, z, t.getProperties(), str);
                    }
                });
            } else if (i == 4) {
                validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$9Ig49qtAOodVQttLJONZvU-1bGE
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = (String) obj;
                        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$YgtDwr1xSqQ2puSXaP5M9FpVkrI
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return ComponentValidator.lambda$validate$6(str, (PropertyList) obj2);
                            }
                        }, PropertyValidator.ASSERT_ONE_OR_MORE_MESSAGE, z, t.getProperties(), str);
                    }
                });
            }
        }
    }

    /* renamed from: net.fortuna.ical4j.validate.ComponentValidator$1, reason: invalid class name */
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
            try {
                $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[ValidationRule.ValidationType.OneOrMore.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static /* synthetic */ boolean lambda$validate$0(String str, PropertyList propertyList) {
        return propertyList.getProperty(str) != null;
    }

    static /* synthetic */ boolean lambda$validate$2(String str, PropertyList propertyList) {
        return propertyList.getProperties(str).size() != 1;
    }

    static /* synthetic */ boolean lambda$validate$4(String str, PropertyList propertyList) {
        return propertyList.getProperties(str).size() > 1;
    }

    static /* synthetic */ boolean lambda$validate$6(String str, PropertyList propertyList) {
        return propertyList.getProperties(str).size() < 1;
    }

    public static void assertNone(final String str, ComponentList<?> componentList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$8oGfDM9arW2d38GuEt-iJ2NU348
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ComponentValidator.lambda$assertNone$8(str, (ComponentList) obj);
            }
        }, ASSERT_NONE_MESSAGE, false, componentList, str);
    }

    static /* synthetic */ boolean lambda$assertNone$8(String str, ComponentList componentList) {
        return componentList.getComponent(str) != null;
    }

    public static void assertOneOrLess(final String str, ComponentList<?> componentList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ComponentValidator$rZjByOInu3BzKo_bVYJqQGAJ6D0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ComponentValidator.lambda$assertOneOrLess$9(str, (ComponentList) obj);
            }
        }, ASSERT_ONE_OR_LESS_MESSAGE, false, componentList, str);
    }

    static /* synthetic */ boolean lambda$assertOneOrLess$9(String str, ComponentList componentList) {
        return componentList.getComponents(str).size() > 1;
    }
}
