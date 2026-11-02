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

/* compiled from: XPropertyFactory.groovy */
/* loaded from: classes3.dex */
public class XPropertyFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[7];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(XPropertyFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = ProductAction.ACTION_REMOVE;
        strArr[3] = ProductAction.ACTION_REMOVE;
        strArr[4] = "<$constructor$>";
        strArr[5] = "newInstance";
        strArr[6] = "<$constructor$>";
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

    public XPropertyFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != XPropertyFactory.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            classInfo = ClassInfo.getClassInfo(getClass());
            $staticClassInfo = classInfo;
        }
        return classInfo.getMetaClass();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        $getCallSiteArray();
        return null;
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, XProperty.class))) {
            return (XProperty) ScriptBytecodeAdapter.castToType(obj2, XProperty.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "name"));
        if (ScriptBytecodeAdapter.compareEqual(strCastToString, (Object) null)) {
            strCastToString = ShortTypeHandling.castToString(obj2);
        }
        String strCastToString2 = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[2].call(map, "value"));
        ParameterList parameterList = (ParameterList) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[3].call(map, "parameters"), ParameterList.class);
        if (ScriptBytecodeAdapter.compareEqual(parameterList, (Object) null)) {
            parameterList = (ParameterList) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[4].callConstructor(ParameterList.class), ParameterList.class);
        }
        return (XProperty) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callStatic(XPropertyFactory.class, strCastToString, parameterList, strCastToString2), XProperty.class);
    }

    protected static Object newInstance(String str, ParameterList parameterList, String str2) {
        return $getCallSiteArray()[6].callConstructor(XProperty.class, str, parameterList, str2);
    }
}
