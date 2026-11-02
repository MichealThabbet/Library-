package net.fortuna.ical4j.model.component;

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

/* compiled from: VAvailabilityFactory.groovy */
/* loaded from: classes3.dex */
public class VAvailabilityFactory extends AbstractComponentFactory {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[2];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(VAvailabilityFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsType";
        strArr[1] = "<$constructor$>";
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

    public VAvailabilityFactory() {
        $getCallSiteArray();
    }

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != VAvailabilityFactory.class) {
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

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) throws IllegalAccessException, InstantiationException {
        if (DefaultTypeTransformation.booleanUnbox($getCallSiteArray()[0].call(FactoryBuilderSupport.class, obj2, obj, VAvailability.class))) {
            return (VAvailability) ScriptBytecodeAdapter.castToType(obj2, VAvailability.class);
        }
        return (VAvailability) ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnSuperN(AbstractComponentFactory.class, this, "newInstance", new Object[]{factoryBuilderSupport, obj, obj2, map}), VAvailability.class);
    }

    @Override // net.fortuna.ical4j.model.component.AbstractComponentFactory
    protected Object newInstance(PropertyList propertyList) {
        return $getCallSiteArray()[1].callConstructor(VAvailability.class, propertyList);
    }
}
