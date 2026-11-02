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

/* compiled from: StatusFactory.groovy */
/* loaded from: classes3.dex */
public class StatusFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[64];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(StatusFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "value";
        strArr[3] = "VEVENT_CANCELLED";
        strArr[4] = "VEVENT_CANCELLED";
        strArr[5] = "value";
        strArr[6] = "VEVENT_CONFIRMED";
        strArr[7] = "VEVENT_CONFIRMED";
        strArr[8] = "value";
        strArr[9] = "VEVENT_TENTATIVE";
        strArr[10] = "VEVENT_TENTATIVE";
        strArr[11] = "value";
        strArr[12] = "VJOURNAL_CANCELLED";
        strArr[13] = "VJOURNAL_CANCELLED";
        strArr[14] = "value";
        strArr[15] = "VJOURNAL_DRAFT";
        strArr[16] = "VJOURNAL_DRAFT";
        strArr[17] = "value";
        strArr[18] = "VJOURNAL_FINAL";
        strArr[19] = "VJOURNAL_FINAL";
        strArr[20] = "value";
        strArr[21] = "VTODO_CANCELLED";
        strArr[22] = "VTODO_CANCELLED";
        strArr[23] = "value";
        strArr[24] = "VTODO_COMPLETED";
        strArr[25] = "VTODO_COMPLETED";
        strArr[26] = "value";
        strArr[27] = "VTODO_IN_PROCESS";
        strArr[28] = "VTODO_IN_PROCESS";
        strArr[29] = "value";
        strArr[30] = "VTODO_NEEDS_ACTION";
        strArr[31] = "VTODO_NEEDS_ACTION";
        strArr[32] = "put";
        strArr[33] = "value";
        strArr[34] = "VEVENT_CANCELLED";
        strArr[35] = "VEVENT_CANCELLED";
        strArr[36] = "value";
        strArr[37] = "VEVENT_CONFIRMED";
        strArr[38] = "VEVENT_CONFIRMED";
        strArr[39] = "value";
        strArr[40] = "VEVENT_TENTATIVE";
        strArr[41] = "VEVENT_TENTATIVE";
        strArr[42] = "value";
        strArr[43] = "VJOURNAL_CANCELLED";
        strArr[44] = "VJOURNAL_CANCELLED";
        strArr[45] = "value";
        strArr[46] = "VJOURNAL_DRAFT";
        strArr[47] = "VJOURNAL_DRAFT";
        strArr[48] = "value";
        strArr[49] = "VJOURNAL_FINAL";
        strArr[50] = "VJOURNAL_FINAL";
        strArr[51] = "value";
        strArr[52] = "VTODO_CANCELLED";
        strArr[53] = "VTODO_CANCELLED";
        strArr[54] = "value";
        strArr[55] = "VTODO_COMPLETED";
        strArr[56] = "VTODO_COMPLETED";
        strArr[57] = "value";
        strArr[58] = "VTODO_IN_PROCESS";
        strArr[59] = "VTODO_IN_PROCESS";
        strArr[60] = "value";
        strArr[61] = "VTODO_NEEDS_ACTION";
        strArr[62] = "VTODO_NEEDS_ACTION";
        strArr[63] = "<$constructor$>";
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

    public StatusFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != StatusFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Status.class))) {
            return (Status) ScriptBytecodeAdapter.castToType(obj2, Status.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[2].callGetProperty(callSiteArr$getCallSiteArray[3].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[4].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[5].callGetProperty(callSiteArr$getCallSiteArray[6].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[7].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[8].callGetProperty(callSiteArr$getCallSiteArray[9].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[10].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[11].callGetProperty(callSiteArr$getCallSiteArray[12].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[13].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[14].callGetProperty(callSiteArr$getCallSiteArray[15].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[16].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[17].callGetProperty(callSiteArr$getCallSiteArray[18].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[19].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[20].callGetProperty(callSiteArr$getCallSiteArray[21].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[22].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[23].callGetProperty(callSiteArr$getCallSiteArray[24].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[25].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[26].callGetProperty(callSiteArr$getCallSiteArray[27].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[28].callGetProperty(Status.class), Status.class);
            }
            if (ScriptBytecodeAdapter.isCase(strCastToString, callSiteArr$getCallSiteArray[29].callGetProperty(callSiteArr$getCallSiteArray[30].callGetProperty(Status.class)))) {
                return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[31].callGetProperty(Status.class), Status.class);
            }
            callSiteArr$getCallSiteArray[32].call(map, "value", strCastToString);
            return (Status) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[33].callGetProperty(callSiteArr$getCallSiteArray[34].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[35].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[36].callGetProperty(callSiteArr$getCallSiteArray[37].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[38].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[39].callGetProperty(callSiteArr$getCallSiteArray[40].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[41].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[42].callGetProperty(callSiteArr$getCallSiteArray[43].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[44].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[45].callGetProperty(callSiteArr$getCallSiteArray[46].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[47].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[48].callGetProperty(callSiteArr$getCallSiteArray[49].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[50].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[51].callGetProperty(callSiteArr$getCallSiteArray[52].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[53].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[54].callGetProperty(callSiteArr$getCallSiteArray[55].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[56].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[57].callGetProperty(callSiteArr$getCallSiteArray[58].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[59].callGetProperty(Status.class), Status.class);
        }
        if (ScriptBytecodeAdapter.isCase(obj2, callSiteArr$getCallSiteArray[60].callGetProperty(callSiteArr$getCallSiteArray[61].callGetProperty(Status.class)))) {
            return (Status) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[62].callGetProperty(Status.class), Status.class);
        }
        return (Status) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Status.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[63].callConstructor(Status.class, parameterList, str);
    }
}
