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

/* compiled from: TranspFactory.groovy */
/* loaded from: classes3.dex */
public class TranspFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[20];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(TranspFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "equals";
        strArr[3] = "getValue";
        strArr[4] = "OPAQUE";
        strArr[5] = "OPAQUE";
        strArr[6] = "equals";
        strArr[7] = "getValue";
        strArr[8] = "TRANSPARENT";
        strArr[9] = "TRANSPARENT";
        strArr[10] = "put";
        strArr[11] = "equals";
        strArr[12] = "getValue";
        strArr[13] = "OPAQUE";
        strArr[14] = "OPAQUE";
        strArr[15] = "equals";
        strArr[16] = "getValue";
        strArr[17] = "TRANSPARENT";
        strArr[18] = "TRANSPARENT";
        strArr[19] = "<$constructor$>";
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

    public TranspFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != TranspFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Transp.class))) {
            return (Transp) ScriptBytecodeAdapter.castToType(obj2, Transp.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].call(callSiteArr$getCallSiteArray[4].callGetProperty(Transp.class)), strCastToString))) {
                return (Transp) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callGetProperty(Transp.class), Transp.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].call(callSiteArr$getCallSiteArray[8].callGetProperty(Transp.class)), strCastToString))) {
                return (Transp) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Transp.class), Transp.class);
            }
            callSiteArr$getCallSiteArray[10].call(map, "value", strCastToString);
            return (Transp) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Transp.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[11].call(callSiteArr$getCallSiteArray[12].call(callSiteArr$getCallSiteArray[13].callGetProperty(Transp.class)), obj2))) {
            return (Transp) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[14].callGetProperty(Transp.class), Transp.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[15].call(callSiteArr$getCallSiteArray[16].call(callSiteArr$getCallSiteArray[17].callGetProperty(Transp.class)), obj2))) {
            return (Transp) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[18].callGetProperty(Transp.class), Transp.class);
        }
        return (Transp) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Transp.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[19].callConstructor(Transp.class, parameterList, str);
    }
}
