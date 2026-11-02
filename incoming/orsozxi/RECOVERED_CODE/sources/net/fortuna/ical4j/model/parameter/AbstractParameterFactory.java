package net.fortuna.ical4j.model.parameter;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.util.AbstractFactory;
import java.lang.ref.SoftReference;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;

/* compiled from: AbstractParameterFactory.groovy */
/* loaded from: classes3.dex */
public abstract class AbstractParameterFactory extends AbstractFactory implements GroovyObject {
    private static /* synthetic */ SoftReference $callSiteArray;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        return new CallSiteArray(AbstractParameterFactory.class, new String[0]);
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

    public AbstractParameterFactory() {
        $getCallSiteArray();
        this.metaClass = $getStaticMetaClass();
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != AbstractParameterFactory.class) {
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
    public MetaClass getMetaClass() {
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
    public Object getProperty(String str) {
        return getMetaClass().getProperty(this, str);
    }

    @Generated
    @Internal
    public Object invokeMethod(String str, Object obj) {
        return getMetaClass().invokeMethod(this, str, obj);
    }

    public boolean isLeaf() {
        $getCallSiteArray();
        return true;
    }

    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    @Generated
    @Internal
    public void setProperty(String str, Object obj) {
        getMetaClass().setProperty(this, str, obj);
    }

    public /* synthetic */ boolean super$2$isLeaf() {
        return super.isLeaf();
    }
}
