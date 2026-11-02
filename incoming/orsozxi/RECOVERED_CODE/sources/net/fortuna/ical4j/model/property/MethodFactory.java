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

/* compiled from: MethodFactory.groovy */
/* loaded from: classes3.dex */
public class MethodFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[68];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(MethodFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "equals";
        strArr[3] = "getValue";
        strArr[4] = "ADD";
        strArr[5] = "ADD";
        strArr[6] = "equals";
        strArr[7] = "getValue";
        strArr[8] = "CANCEL";
        strArr[9] = "CANCEL";
        strArr[10] = "equals";
        strArr[11] = "getValue";
        strArr[12] = "COUNTER";
        strArr[13] = "COUNTER";
        strArr[14] = "equals";
        strArr[15] = "getValue";
        strArr[16] = "DECLINE_COUNTER";
        strArr[17] = "DECLINE_COUNTER";
        strArr[18] = "equals";
        strArr[19] = "getValue";
        strArr[20] = "PUBLISH";
        strArr[21] = "PUBLISH";
        strArr[22] = "equals";
        strArr[23] = "getValue";
        strArr[24] = "REFRESH";
        strArr[25] = "REFRESH";
        strArr[26] = "equals";
        strArr[27] = "getValue";
        strArr[28] = "REPLY";
        strArr[29] = "REPLY";
        strArr[30] = "equals";
        strArr[31] = "getValue";
        strArr[32] = "REQUEST";
        strArr[33] = "REQUEST";
        strArr[34] = "put";
        strArr[35] = "equals";
        strArr[36] = "getValue";
        strArr[37] = "ADD";
        strArr[38] = "ADD";
        strArr[39] = "equals";
        strArr[40] = "getValue";
        strArr[41] = "CANCEL";
        strArr[42] = "CANCEL";
        strArr[43] = "equals";
        strArr[44] = "getValue";
        strArr[45] = "COUNTER";
        strArr[46] = "COUNTER";
        strArr[47] = "equals";
        strArr[48] = "getValue";
        strArr[49] = "DECLINE_COUNTER";
        strArr[50] = "DECLINE_COUNTER";
        strArr[51] = "equals";
        strArr[52] = "getValue";
        strArr[53] = "PUBLISH";
        strArr[54] = "PUBLISH";
        strArr[55] = "equals";
        strArr[56] = "getValue";
        strArr[57] = "REFRESH";
        strArr[58] = "REFRESH";
        strArr[59] = "equals";
        strArr[60] = "getValue";
        strArr[61] = "REPLY";
        strArr[62] = "REPLY";
        strArr[63] = "equals";
        strArr[64] = "getValue";
        strArr[65] = "REQUEST";
        strArr[66] = "REQUEST";
        strArr[67] = "<$constructor$>";
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

    public MethodFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != MethodFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Method.class))) {
            return (Method) ScriptBytecodeAdapter.castToType(obj2, Method.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].call(callSiteArr$getCallSiteArray[4].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].call(callSiteArr$getCallSiteArray[8].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[10].call(callSiteArr$getCallSiteArray[11].call(callSiteArr$getCallSiteArray[12].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[13].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[14].call(callSiteArr$getCallSiteArray[15].call(callSiteArr$getCallSiteArray[16].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[17].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[18].call(callSiteArr$getCallSiteArray[19].call(callSiteArr$getCallSiteArray[20].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[21].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[22].call(callSiteArr$getCallSiteArray[23].call(callSiteArr$getCallSiteArray[24].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[25].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[26].call(callSiteArr$getCallSiteArray[27].call(callSiteArr$getCallSiteArray[28].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[29].callGetProperty(Method.class), Method.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[30].call(callSiteArr$getCallSiteArray[31].call(callSiteArr$getCallSiteArray[32].callGetProperty(Method.class)), strCastToString))) {
                return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[33].callGetProperty(Method.class), Method.class);
            }
            callSiteArr$getCallSiteArray[34].call(map, "value", strCastToString);
            return (Method) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[35].call(callSiteArr$getCallSiteArray[36].call(callSiteArr$getCallSiteArray[37].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[38].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[39].call(callSiteArr$getCallSiteArray[40].call(callSiteArr$getCallSiteArray[41].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[42].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[43].call(callSiteArr$getCallSiteArray[44].call(callSiteArr$getCallSiteArray[45].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[46].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[47].call(callSiteArr$getCallSiteArray[48].call(callSiteArr$getCallSiteArray[49].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[50].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[51].call(callSiteArr$getCallSiteArray[52].call(callSiteArr$getCallSiteArray[53].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[54].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[55].call(callSiteArr$getCallSiteArray[56].call(callSiteArr$getCallSiteArray[57].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[58].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[59].call(callSiteArr$getCallSiteArray[60].call(callSiteArr$getCallSiteArray[61].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[62].callGetProperty(Method.class), Method.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[63].call(callSiteArr$getCallSiteArray[64].call(callSiteArr$getCallSiteArray[65].callGetProperty(Method.class)), obj2))) {
            return (Method) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[66].callGetProperty(Method.class), Method.class);
        }
        return (Method) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Method.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[67].callConstructor(Method.class, parameterList, str);
    }
}
