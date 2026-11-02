package net.fortuna.ical4j.model.parameter;

import groovy.lang.MetaClass;
import groovy.util.FactoryBuilderSupport;
import java.lang.ref.SoftReference;
import java.util.Map;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

/* compiled from: CuTypeFactory.groovy */
/* loaded from: classes3.dex */
public class CuTypeFactory extends AbstractParameterFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[14];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(CuTypeFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = "value";
        strArr[2] = "GROUP";
        strArr[3] = "GROUP";
        strArr[4] = "value";
        strArr[5] = "INDIVIDUAL";
        strArr[6] = "INDIVIDUAL";
        strArr[7] = "value";
        strArr[8] = "RESOURCE";
        strArr[9] = "RESOURCE";
        strArr[10] = "value";
        strArr[11] = "ROOM";
        strArr[12] = "ROOM";
        strArr[13] = "<$constructor$>";
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

    public CuTypeFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.parameter.AbstractParameterFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != CuTypeFactory.class) {
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

    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) throws IllegalAccessException, InstantiationException {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, CuType.class))) {
            return (CuType) ScriptBytecodeAdapter.castToType(obj2, CuType.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[1].callGetProperty(callSiteArr$getCallSiteArray[2].callGetProperty(CuType.class)))) {
            return (CuType) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[3].callGetProperty(CuType.class), CuType.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[4].callGetProperty(callSiteArr$getCallSiteArray[5].callGetProperty(CuType.class)))) {
            return (CuType) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[6].callGetProperty(CuType.class), CuType.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[7].callGetProperty(callSiteArr$getCallSiteArray[8].callGetProperty(CuType.class)))) {
            return (CuType) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(CuType.class), CuType.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[10].callGetProperty(callSiteArr$getCallSiteArray[11].callGetProperty(CuType.class)))) {
            return (CuType) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[12].callGetProperty(CuType.class), CuType.class);
        }
        return (CuType) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[13].callConstructor(CuType.class, obj2), CuType.class);
    }
}
