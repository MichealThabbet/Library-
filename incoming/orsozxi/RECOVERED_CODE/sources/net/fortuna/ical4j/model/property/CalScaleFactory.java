package net.fortuna.ical4j.model.property;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import groovy.lang.MetaClass;
import groovy.util.FactoryBuilderSupport;
import java.lang.ref.SoftReference;
import java.util.Map;
import net.fortuna.ical4j.model.ParameterList;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

/* compiled from: CalScaleFactory.groovy */
/* loaded from: classes3.dex */
public class CalScaleFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[12];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(CalScaleFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "equals";
        strArr[3] = "getValue";
        strArr[4] = "GREGORIAN";
        strArr[5] = "GREGORIAN";
        strArr[6] = "put";
        strArr[7] = "equals";
        strArr[8] = "getValue";
        strArr[9] = "GREGORIAN";
        strArr[10] = "GREGORIAN";
        strArr[11] = "<$constructor$>";
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray$createCallSiteArray;
        SoftReference softReference = $callSiteArray;
        if (softReference == null || (callSiteArray$createCallSiteArray = (CallSiteArray) softReference.get()) == null) {
            callSiteArray$createCallSiteArray = $createCallSiteArray();
            $callSiteArray = new SoftReference(callSiteArray$createCallSiteArray);
        }
        return callSiteArray$createCallSiteArray.array;
    }

    public CalScaleFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != CalScaleFactory.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            classInfo = ClassInfo.getClassInfo(getClass());
            $staticClassInfo = classInfo;
        }
        return classInfo.getMetaClass();
    }

    public /* synthetic */ MetaClass super$3$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    public /* synthetic */ Object super$3$newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) {
        return super.newInstance(factoryBuilderSupport, obj, obj2, map);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) throws IllegalAccessException, InstantiationException {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, CalScale.class))) {
            return (CalScale) ScriptBytecodeAdapter.castToType(obj2, CalScale.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].call(callSiteArr$getCallSiteArray[4].callGetProperty(CalScale.class)), strCastToString))) {
                return (CalScale) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callGetProperty(CalScale.class), CalScale.class);
            }
            callSiteArr$getCallSiteArray[6].call(map, "value", strCastToString);
            return (CalScale) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), CalScale.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[7].call(callSiteArr$getCallSiteArray[8].call(callSiteArr$getCallSiteArray[9].callGetProperty(CalScale.class)), obj2))) {
            return (CalScale) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[10].callGetProperty(CalScale.class), CalScale.class);
        }
        return (CalScale) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), CalScale.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[11].callConstructor(CalScale.class, parameterList, str);
    }
}
