package net.fortuna.ical4j.model.parameter;

import groovy.lang.MetaClass;
import groovy.util.FactoryBuilderSupport;
import java.lang.ref.SoftReference;
import java.util.Map;
import net.fortuna.ical4j.model.Property;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

/* compiled from: ValueFactory.groovy */
/* loaded from: classes3.dex */
public class ValueFactory extends AbstractParameterFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[43];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(ValueFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = "value";
        strArr[2] = "BINARY";
        strArr[3] = "BINARY";
        strArr[4] = "value";
        strArr[5] = "BOOLEAN";
        strArr[6] = "BOOLEAN";
        strArr[7] = "value";
        strArr[8] = "CAL_ADDRESS";
        strArr[9] = "CAL_ADDRESS";
        strArr[10] = "value";
        strArr[11] = "DATE";
        strArr[12] = "DATE";
        strArr[13] = "value";
        strArr[14] = "DATE_TIME";
        strArr[15] = "DATE_TIME";
        strArr[16] = "value";
        strArr[17] = Property.DURATION;
        strArr[18] = Property.DURATION;
        strArr[19] = "value";
        strArr[20] = "FLOAT";
        strArr[21] = "FLOAT";
        strArr[22] = "value";
        strArr[23] = "INTEGER";
        strArr[24] = "INTEGER";
        strArr[25] = "value";
        strArr[26] = "PERIOD";
        strArr[27] = "PERIOD";
        strArr[28] = "value";
        strArr[29] = "RECUR";
        strArr[30] = "RECUR";
        strArr[31] = "value";
        strArr[32] = "TEXT";
        strArr[33] = "TEXT";
        strArr[34] = "value";
        strArr[35] = "TIME";
        strArr[36] = "TIME";
        strArr[37] = "value";
        strArr[38] = "URI";
        strArr[39] = "URI";
        strArr[40] = "value";
        strArr[41] = "UTC_OFFSET";
        strArr[42] = "UTC_OFFSET";
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

    public ValueFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.parameter.AbstractParameterFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != ValueFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Value.class))) {
            return (Value) ScriptBytecodeAdapter.castToType(obj2, Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[1].callGetProperty(callSiteArr$getCallSiteArray[2].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[3].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[4].callGetProperty(callSiteArr$getCallSiteArray[5].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[6].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[7].callGetProperty(callSiteArr$getCallSiteArray[8].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[10].callGetProperty(callSiteArr$getCallSiteArray[11].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[12].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[13].callGetProperty(callSiteArr$getCallSiteArray[14].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[15].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[16].callGetProperty(callSiteArr$getCallSiteArray[17].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[18].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[19].callGetProperty(callSiteArr$getCallSiteArray[20].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[21].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[22].callGetProperty(callSiteArr$getCallSiteArray[23].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[24].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[25].callGetProperty(callSiteArr$getCallSiteArray[26].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[27].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[28].callGetProperty(callSiteArr$getCallSiteArray[29].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[30].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[31].callGetProperty(callSiteArr$getCallSiteArray[32].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[33].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[34].callGetProperty(callSiteArr$getCallSiteArray[35].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[36].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[37].callGetProperty(callSiteArr$getCallSiteArray[38].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[39].callGetProperty(Value.class), Value.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[40].callGetProperty(callSiteArr$getCallSiteArray[41].callGetProperty(Value.class)))) {
            return (Value) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[42].callGetProperty(Value.class), Value.class);
        }
        return (Value) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createList(new Object[]{obj2}), Value.class);
    }
}
