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

/* compiled from: PartStatFactory.groovy */
/* loaded from: classes3.dex */
public class PartStatFactory extends AbstractParameterFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[22];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(PartStatFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = "value";
        strArr[2] = "ACCEPTED";
        strArr[3] = "ACCEPTED";
        strArr[4] = "value";
        strArr[5] = Property.COMPLETED;
        strArr[6] = Property.COMPLETED;
        strArr[7] = "value";
        strArr[8] = "DECLINED";
        strArr[9] = "DECLINED";
        strArr[10] = "value";
        strArr[11] = "DELEGATED";
        strArr[12] = "DELEGATED";
        strArr[13] = "value";
        strArr[14] = "IN_PROCESS";
        strArr[15] = "IN_PROCESS";
        strArr[16] = "value";
        strArr[17] = "NEEDS_ACTION";
        strArr[18] = "NEEDS_ACTION";
        strArr[19] = "value";
        strArr[20] = "TENTATIVE";
        strArr[21] = "TENTATIVE";
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

    public PartStatFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.parameter.AbstractParameterFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != PartStatFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, PartStat.class))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(obj2, PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[1].callGetProperty(callSiteArr$getCallSiteArray[2].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[3].callGetProperty(PartStat.class), PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[4].callGetProperty(callSiteArr$getCallSiteArray[5].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[6].callGetProperty(PartStat.class), PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[7].callGetProperty(callSiteArr$getCallSiteArray[8].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(PartStat.class), PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[10].callGetProperty(callSiteArr$getCallSiteArray[11].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[12].callGetProperty(PartStat.class), PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[13].callGetProperty(callSiteArr$getCallSiteArray[14].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[15].callGetProperty(PartStat.class), PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[16].callGetProperty(callSiteArr$getCallSiteArray[17].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[18].callGetProperty(PartStat.class), PartStat.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[19].callGetProperty(callSiteArr$getCallSiteArray[20].callGetProperty(PartStat.class)))) {
            return (PartStat) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[21].callGetProperty(PartStat.class), PartStat.class);
        }
        return (PartStat) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createList(new Object[]{obj2}), PartStat.class);
    }
}
