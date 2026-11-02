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

/* compiled from: EncodingFactory.groovy */
/* loaded from: classes3.dex */
public class EncodingFactory extends AbstractParameterFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[16];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(EncodingFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = "value";
        strArr[2] = "BASE64";
        strArr[3] = "BASE64";
        strArr[4] = "value";
        strArr[5] = "BINARY";
        strArr[6] = "BINARY";
        strArr[7] = "value";
        strArr[8] = "EIGHT_BIT";
        strArr[9] = "EIGHT_BIT";
        strArr[10] = "value";
        strArr[11] = "QUOTED_PRINTABLE";
        strArr[12] = "QUOTED_PRINTABLE";
        strArr[13] = "value";
        strArr[14] = "SEVEN_BIT";
        strArr[15] = "SEVEN_BIT";
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

    public EncodingFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.parameter.AbstractParameterFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != EncodingFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Encoding.class))) {
            return (Encoding) ScriptBytecodeAdapter.castToType(obj2, Encoding.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[1].callGetProperty(callSiteArr$getCallSiteArray[2].callGetProperty(Encoding.class)))) {
            return (Encoding) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[3].callGetProperty(Encoding.class), Encoding.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[4].callGetProperty(callSiteArr$getCallSiteArray[5].callGetProperty(Encoding.class)))) {
            return (Encoding) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[6].callGetProperty(Encoding.class), Encoding.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[7].callGetProperty(callSiteArr$getCallSiteArray[8].callGetProperty(Encoding.class)))) {
            return (Encoding) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Encoding.class), Encoding.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[10].callGetProperty(callSiteArr$getCallSiteArray[11].callGetProperty(Encoding.class)))) {
            return (Encoding) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[12].callGetProperty(Encoding.class), Encoding.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[13].callGetProperty(callSiteArr$getCallSiteArray[14].callGetProperty(Encoding.class)))) {
            return (Encoding) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[15].callGetProperty(Encoding.class), Encoding.class);
        }
        return (Encoding) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createList(new Object[]{obj2}), Encoding.class);
    }
}
