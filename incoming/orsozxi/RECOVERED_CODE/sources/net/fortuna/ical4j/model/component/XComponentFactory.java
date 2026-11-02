package net.fortuna.ical4j.model.component;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import java.lang.ref.SoftReference;
import java.util.Map;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

/* compiled from: XComponentFactory.groovy */
/* loaded from: classes3.dex */
public class XComponentFactory extends AbstractFactory implements GroovyObject {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[8];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(XComponentFactory.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = ProductAction.ACTION_REMOVE;
        strArr[3] = "<$constructor$>";
        strArr[4] = "newInstance";
        strArr[5] = "<$constructor$>";
        strArr[6] = ProductAction.ACTION_ADD;
        strArr[7] = "properties";
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

    public XComponentFactory() {
        $getCallSiteArray();
        this.metaClass = $getStaticMetaClass();
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != XComponentFactory.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            classInfo = ClassInfo.getClassInfo(getClass());
            $staticClassInfo = classInfo;
        }
        return classInfo.getMetaClass();
    }

    @Generated
    @Internal
    public /* synthetic */ MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        MetaClass metaClass$getStaticMetaClass = $getStaticMetaClass();
        this.metaClass = metaClass$getStaticMetaClass;
        return metaClass$getStaticMetaClass;
    }

    @Generated
    @Internal
    public /* synthetic */ Object getProperty(String str) {
        return getMetaClass().getProperty(this, str);
    }

    @Generated
    @Internal
    public /* synthetic */ Object invokeMethod(String str, Object obj) {
        return getMetaClass().invokeMethod(this, str, obj);
    }

    @Generated
    @Internal
    public /* synthetic */ void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    @Generated
    @Internal
    public /* synthetic */ void setProperty(String str, Object obj) {
        getMetaClass().setProperty(this, str, obj);
    }

    public /* synthetic */ void super$2$setChild(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2) {
        super.setChild(factoryBuilderSupport, obj, obj2);
    }

    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) throws IllegalAccessException, InstantiationException {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, XComponent.class))) {
            return (XComponent) ScriptBytecodeAdapter.castToType(obj2, XComponent.class);
        }
        Object objCall = callSiteArr$getCallSiteArray[1].call(map, "name");
        if (!ScriptBytecodeAdapter.compareEqual(objCall, (Object) null)) {
            obj2 = objCall;
        }
        PropertyList propertyList = (PropertyList) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[2].call(map, "properties"), PropertyList.class);
        if (ScriptBytecodeAdapter.compareEqual(propertyList, (Object) null)) {
            propertyList = (PropertyList) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[3].callConstructor(PropertyList.class), PropertyList.class);
        }
        return (XComponent) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[4].callStatic(XComponentFactory.class, obj2, propertyList), XComponent.class);
    }

    protected static Object newInstance(String str, PropertyList propertyList) {
        return $getCallSiteArray()[5].callConstructor(XComponent.class, str, propertyList);
    }

    public void setChild(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2) {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (obj2 instanceof Property) {
            callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].callGetProperty(obj), obj2);
        }
    }
}
