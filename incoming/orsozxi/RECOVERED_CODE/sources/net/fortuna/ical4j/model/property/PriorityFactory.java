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

/* compiled from: PriorityFactory.groovy */
/* loaded from: classes3.dex */
public class PriorityFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[36];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(PriorityFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "equals";
        strArr[3] = "getValue";
        strArr[4] = "HIGH";
        strArr[5] = "HIGH";
        strArr[6] = "equals";
        strArr[7] = "getValue";
        strArr[8] = "MEDIUM";
        strArr[9] = "MEDIUM";
        strArr[10] = "equals";
        strArr[11] = "getValue";
        strArr[12] = "LOW";
        strArr[13] = "LOW";
        strArr[14] = "equals";
        strArr[15] = "getValue";
        strArr[16] = "UNDEFINED";
        strArr[17] = "UNDEFINED";
        strArr[18] = "put";
        strArr[19] = "equals";
        strArr[20] = "getValue";
        strArr[21] = "HIGH";
        strArr[22] = "HIGH";
        strArr[23] = "equals";
        strArr[24] = "getValue";
        strArr[25] = "MEDIUM";
        strArr[26] = "MEDIUM";
        strArr[27] = "equals";
        strArr[28] = "getValue";
        strArr[29] = "LOW";
        strArr[30] = "LOW";
        strArr[31] = "equals";
        strArr[32] = "getValue";
        strArr[33] = "UNDEFINED";
        strArr[34] = "UNDEFINED";
        strArr[35] = "<$constructor$>";
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

    public PriorityFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != PriorityFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Priority.class))) {
            return (Priority) ScriptBytecodeAdapter.castToType(obj2, Priority.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].call(callSiteArr$getCallSiteArray[4].callGetProperty(Priority.class)), strCastToString))) {
                return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callGetProperty(Priority.class), Priority.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].call(callSiteArr$getCallSiteArray[8].callGetProperty(Priority.class)), strCastToString))) {
                return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Priority.class), Priority.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[10].call(callSiteArr$getCallSiteArray[11].call(callSiteArr$getCallSiteArray[12].callGetProperty(Priority.class)), strCastToString))) {
                return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[13].callGetProperty(Priority.class), Priority.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[14].call(callSiteArr$getCallSiteArray[15].call(callSiteArr$getCallSiteArray[16].callGetProperty(Priority.class)), strCastToString))) {
                return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[17].callGetProperty(Priority.class), Priority.class);
            }
            callSiteArr$getCallSiteArray[18].call(map, "value", strCastToString);
            return (Priority) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Priority.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[19].call(callSiteArr$getCallSiteArray[20].call(callSiteArr$getCallSiteArray[21].callGetProperty(Priority.class)), obj2))) {
            return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[22].callGetProperty(Priority.class), Priority.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[23].call(callSiteArr$getCallSiteArray[24].call(callSiteArr$getCallSiteArray[25].callGetProperty(Priority.class)), obj2))) {
            return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[26].callGetProperty(Priority.class), Priority.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[27].call(callSiteArr$getCallSiteArray[28].call(callSiteArr$getCallSiteArray[29].callGetProperty(Priority.class)), obj2))) {
            return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[30].callGetProperty(Priority.class), Priority.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[31].call(callSiteArr$getCallSiteArray[32].call(callSiteArr$getCallSiteArray[33].callGetProperty(Priority.class)), obj2))) {
            return (Priority) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[34].callGetProperty(Priority.class), Priority.class);
        }
        return (Priority) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Priority.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[35].callConstructor(Priority.class, parameterList, str);
    }
}
