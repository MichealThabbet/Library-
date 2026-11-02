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
import org.jsoup.nodes.DocumentType;

/* compiled from: ClazzFactory.groovy */
/* loaded from: classes3.dex */
public class ClazzFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[28];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(ClazzFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "equals";
        strArr[3] = "getValue";
        strArr[4] = "CONFIDENTIAL";
        strArr[5] = "CONFIDENTIAL";
        strArr[6] = "equals";
        strArr[7] = "getValue";
        strArr[8] = "PRIVATE";
        strArr[9] = "PRIVATE";
        strArr[10] = "equals";
        strArr[11] = "getValue";
        strArr[12] = DocumentType.PUBLIC_KEY;
        strArr[13] = DocumentType.PUBLIC_KEY;
        strArr[14] = "put";
        strArr[15] = "equals";
        strArr[16] = "getValue";
        strArr[17] = "CONFIDENTIAL";
        strArr[18] = "CONFIDENTIAL";
        strArr[19] = "equals";
        strArr[20] = "getValue";
        strArr[21] = "PRIVATE";
        strArr[22] = "PRIVATE";
        strArr[23] = "equals";
        strArr[24] = "getValue";
        strArr[25] = DocumentType.PUBLIC_KEY;
        strArr[26] = DocumentType.PUBLIC_KEY;
        strArr[27] = "<$constructor$>";
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

    public ClazzFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != ClazzFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Clazz.class))) {
            return (Clazz) ScriptBytecodeAdapter.castToType(obj2, Clazz.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].call(callSiteArr$getCallSiteArray[4].callGetProperty(Clazz.class)), strCastToString))) {
                return (Clazz) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callGetProperty(Clazz.class), Clazz.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].call(callSiteArr$getCallSiteArray[8].callGetProperty(Clazz.class)), strCastToString))) {
                return (Clazz) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Clazz.class), Clazz.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[10].call(callSiteArr$getCallSiteArray[11].call(callSiteArr$getCallSiteArray[12].callGetProperty(Clazz.class)), strCastToString))) {
                return (Clazz) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[13].callGetProperty(Clazz.class), Clazz.class);
            }
            callSiteArr$getCallSiteArray[14].call(map, "value", strCastToString);
            return (Clazz) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Clazz.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[15].call(callSiteArr$getCallSiteArray[16].call(callSiteArr$getCallSiteArray[17].callGetProperty(Clazz.class)), obj2))) {
            return (Clazz) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[18].callGetProperty(Clazz.class), Clazz.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[19].call(callSiteArr$getCallSiteArray[20].call(callSiteArr$getCallSiteArray[21].callGetProperty(Clazz.class)), obj2))) {
            return (Clazz) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[22].callGetProperty(Clazz.class), Clazz.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[23].call(callSiteArr$getCallSiteArray[24].call(callSiteArr$getCallSiteArray[25].callGetProperty(Clazz.class)), obj2))) {
            return (Clazz) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[26].callGetProperty(Clazz.class), Clazz.class);
        }
        return (Clazz) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Clazz.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[27].callConstructor(Clazz.class, parameterList, str);
    }
}
