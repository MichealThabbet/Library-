package net.fortuna.ical4j.model.component;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import groovy.lang.MetaClass;
import groovy.util.FactoryBuilderSupport;
import java.lang.ref.SoftReference;
import java.util.Map;
import net.fortuna.ical4j.model.PropertyList;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

/* compiled from: VToDoFactory.groovy */
/* loaded from: classes3.dex */
public class VToDoFactory extends AbstractComponentFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[4];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(VToDoFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsType";
        strArr[1] = "<$constructor$>";
        strArr[2] = ProductAction.ACTION_ADD;
        strArr[3] = "alarms";
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

    public VToDoFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != VToDoFactory.class) {
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

    public /* synthetic */ void super$3$setChild(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2) {
        super.setChild(factoryBuilderSupport, obj, obj2);
    }

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) throws IllegalAccessException, InstantiationException {
        if (DefaultTypeTransformation.booleanUnbox($getCallSiteArray()[0].call(FactoryBuilderSupport.class, obj2, obj, VToDo.class))) {
            return (VToDo) ScriptBytecodeAdapter.castToType(obj2, VToDo.class);
        }
        return (VToDo) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractComponentFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), VToDo.class);
    }

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    protected Object newInstance(PropertyList propertyList) {
        return $getCallSiteArray()[1].callConstructor(VToDo.class, propertyList);
    }

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    public void setChild(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2) {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (obj2 instanceof VAlarm) {
            callSiteArr$getCallSiteArray[2].call(callSiteArr$getCallSiteArray[3].callGetProperty(obj), obj2);
        } else {
            ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractComponentFactory.class, this, "setChild", new Object[]{factoryBuilderSupport, obj, obj2});
        }
    }
}
