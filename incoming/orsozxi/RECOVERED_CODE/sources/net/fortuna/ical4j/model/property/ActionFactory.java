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

/* compiled from: ActionFactory.groovy */
/* loaded from: classes3.dex */
public class ActionFactory extends AbstractPropertyFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[36];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(ActionFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "equals";
        strArr[3] = "getValue";
        strArr[4] = "AUDIO";
        strArr[5] = "AUDIO";
        strArr[6] = "equals";
        strArr[7] = "getValue";
        strArr[8] = "DISPLAY";
        strArr[9] = "DISPLAY";
        strArr[10] = "equals";
        strArr[11] = "getValue";
        strArr[12] = "EMAIL";
        strArr[13] = "EMAIL";
        strArr[14] = "equals";
        strArr[15] = "getValue";
        strArr[16] = "PROCEDURE";
        strArr[17] = "PROCEDURE";
        strArr[18] = "put";
        strArr[19] = "equals";
        strArr[20] = "getValue";
        strArr[21] = "AUDIO";
        strArr[22] = "AUDIO";
        strArr[23] = "equals";
        strArr[24] = "getValue";
        strArr[25] = "DISPLAY";
        strArr[26] = "DISPLAY";
        strArr[27] = "equals";
        strArr[28] = "getValue";
        strArr[29] = "EMAIL";
        strArr[30] = "EMAIL";
        strArr[31] = "equals";
        strArr[32] = "getValue";
        strArr[33] = "PROCEDURE";
        strArr[34] = "PROCEDURE";
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

    public ActionFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != ActionFactory.class) {
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
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, Action.class))) {
            return (Action) ScriptBytecodeAdapter.castToType(obj2, Action.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[1].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].call(callSiteArr$getCallSiteArray[4].callGetProperty(Action.class)), strCastToString))) {
                return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[5].callGetProperty(Action.class), Action.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].call(callSiteArr$getCallSiteArray[8].callGetProperty(Action.class)), strCastToString))) {
                return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[9].callGetProperty(Action.class), Action.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[10].call(callSiteArr$getCallSiteArray[11].call(callSiteArr$getCallSiteArray[12].callGetProperty(Action.class)), strCastToString))) {
                return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[13].callGetProperty(Action.class), Action.class);
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[14].call(callSiteArr$getCallSiteArray[15].call(callSiteArr$getCallSiteArray[16].callGetProperty(Action.class)), strCastToString))) {
                return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[17].callGetProperty(Action.class), Action.class);
            }
            callSiteArr$getCallSiteArray[18].call(map, "value", strCastToString);
            return (Action) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Action.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[19].call(callSiteArr$getCallSiteArray[20].call(callSiteArr$getCallSiteArray[21].callGetProperty(Action.class)), obj2))) {
            return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[22].callGetProperty(Action.class), Action.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[23].call(callSiteArr$getCallSiteArray[24].call(callSiteArr$getCallSiteArray[25].callGetProperty(Action.class)), obj2))) {
            return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[26].callGetProperty(Action.class), Action.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[27].call(callSiteArr$getCallSiteArray[28].call(callSiteArr$getCallSiteArray[29].callGetProperty(Action.class)), obj2))) {
            return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[30].callGetProperty(Action.class), Action.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[31].call(callSiteArr$getCallSiteArray[32].call(callSiteArr$getCallSiteArray[33].callGetProperty(Action.class)), obj2))) {
            return (Action) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[34].callGetProperty(Action.class), Action.class);
        }
        return (Action) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractPropertyFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), Action.class);
    }

    @Override // net.fortuna.ical4j.model.property.AbstractPropertyFactory
    protected Object newInstance(ParameterList parameterList, String str) {
        return $getCallSiteArray()[35].callConstructor(Action.class, parameterList, str);
    }
}
