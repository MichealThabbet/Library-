package net.fortuna.ical4j.validate;

import java.util.function.Predicate;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public final class ParameterValidator {
    public static final String ASSERT_NONE_MESSAGE = "Parameter [{0}] is not applicable";
    private static final String ASSERT_NULL_OR_EQUAL_MESSAGE = "Parameter [{0}] is invalid";
    public static final String ASSERT_ONE_MESSAGE = "Parameter [{0}] must be specified once";
    public static final String ASSERT_ONE_OR_LESS_MESSAGE = "Parameter [{0}] must only be specified once";

    private ParameterValidator() {
    }

    public static void assertOneOrLess(final String str, ParameterList parameterList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ParameterValidator$x58K8K9ZC7pykhfCChjiSCjeSn4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ParameterValidator.lambda$assertOneOrLess$0(str, (ParameterList) obj);
            }
        }, ASSERT_ONE_OR_LESS_MESSAGE, false, parameterList, str);
    }

    static /* synthetic */ boolean lambda$assertOneOrLess$0(String str, ParameterList parameterList) {
        return parameterList.getParameters(str).size() > 1;
    }

    public static void assertOne(final String str, ParameterList parameterList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ParameterValidator$DNN4jzj0p4X7EP_cqGeui31QDjU
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ParameterValidator.lambda$assertOne$1(str, (ParameterList) obj);
            }
        }, ASSERT_ONE_MESSAGE, false, parameterList, str);
    }

    static /* synthetic */ boolean lambda$assertOne$1(String str, ParameterList parameterList) {
        return parameterList.getParameters(str).size() != 1;
    }

    public static void assertNone(final String str, ParameterList parameterList) throws ValidationException {
        Validator.CC.assertFalse(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$ParameterValidator$SC97QqGGDzlM-rvHUgyzIb89_ek
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ParameterValidator.lambda$assertNone$2(str, (ParameterList) obj);
            }
        }, ASSERT_NONE_MESSAGE, false, parameterList, str);
    }

    static /* synthetic */ boolean lambda$assertNone$2(String str, ParameterList parameterList) {
        return parameterList.getParameter(str) != null;
    }

    public static void assertNullOrEqual(Parameter parameter, ParameterList parameterList) throws ValidationException {
        Parameter parameter2 = parameterList.getParameter(parameter.getName());
        if (parameter2 != null && !parameter.equals(parameter2)) {
            throw new ValidationException(ASSERT_NULL_OR_EQUAL_MESSAGE, new Object[]{parameter2});
        }
    }
}
