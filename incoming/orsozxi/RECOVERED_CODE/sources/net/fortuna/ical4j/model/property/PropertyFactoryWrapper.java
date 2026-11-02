package net.fortuna.ical4j.model.property;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import java.lang.ref.SoftReference;
import java.util.Map;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.PropertyFactory;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

/* compiled from: PropertyFactoryWrapper.groovy */
/* loaded from: classes3.dex */
public class PropertyFactoryWrapper extends AbstractFactory implements GroovyObject {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private PropertyFactory factory;
    private transient /* synthetic */ MetaClass metaClass;
    private Class propertyClass;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[8];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(PropertyFactoryWrapper.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "checkValueIsTypeNotString";
        strArr[1] = ProductAction.ACTION_REMOVE;
        strArr[2] = "<$constructor$>";
        strArr[3] = ProductAction.ACTION_REMOVE;
        strArr[4] = "createProperty";
        strArr[5] = "createProperty";
        strArr[6] = ProductAction.ACTION_ADD;
        strArr[7] = "parameters";
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

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != PropertyFactoryWrapper.class) {
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
    public PropertyFactory getFactory() {
        return this.factory;
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
    public Class getPropertyClass() {
        return this.propertyClass;
    }

    @Generated
    @Internal
    public /* synthetic */ Object invokeMethod(String str, Object obj) {
        return getMetaClass().invokeMethod(this, str, obj);
    }

    @Generated
    public void setFactory(PropertyFactory propertyFactory) {
        this.factory = propertyFactory;
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

    @Generated
    public void setPropertyClass(Class cls) {
        this.propertyClass = cls;
    }

    public /* synthetic */ void super$2$setChild(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2) {
        super.setChild(factoryBuilderSupport, obj, obj2);
    }

    public PropertyFactoryWrapper(Class cls, PropertyFactory propertyFactory) {
        $getCallSiteArray();
        this.metaClass = $getStaticMetaClass();
        this.propertyClass = ShortTypeHandling.castToClass(cls);
        this.factory = (PropertyFactory) ScriptBytecodeAdapter.castToType(propertyFactory, PropertyFactory.class);
    }

    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2, Map map) throws IllegalAccessException, InstantiationException {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(callSiteArr$getCallSiteArray[0].call(FactoryBuilderSupport.class, obj2, obj, this.propertyClass))) {
            return obj2;
        }
        ParameterList parameterList = (ParameterList) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[1].call(map, "parameters"), ParameterList.class);
        if (ScriptBytecodeAdapter.compareEqual(parameterList, (Object) null)) {
            parameterList = (ParameterList) ScriptBytecodeAdapter.castToType(callSiteArr$getCallSiteArray[2].callConstructor(ParameterList.class), ParameterList.class);
        }
        String strCastToString = ShortTypeHandling.castToString(callSiteArr$getCallSiteArray[3].call(map, "value"));
        if (ScriptBytecodeAdapter.compareNotEqual(strCastToString, (Object) null)) {
            return callSiteArr$getCallSiteArray[4].call(this.factory, parameterList, strCastToString);
        }
        return callSiteArr$getCallSiteArray[5].call(this.factory, parameterList, obj2);
    }

    public void setChild(FactoryBuilderSupport factoryBuilderSupport, Object obj, Object obj2) {
        CallSite[] callSiteArr$getCallSiteArray = $getCallSiteArray();
        if (obj2 instanceof Parameter) {
            callSiteArr$getCallSiteArray[6].call(callSiteArr$getCallSiteArray[7].callGetProperty(obj), obj2);
        }
    }
}
