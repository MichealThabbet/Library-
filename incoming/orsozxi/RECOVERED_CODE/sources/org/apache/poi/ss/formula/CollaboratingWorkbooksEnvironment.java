package org.apache.poi.ss.formula;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public final class CollaboratingWorkbooksEnvironment {
    public static final CollaboratingWorkbooksEnvironment EMPTY = new CollaboratingWorkbooksEnvironment();
    private final WorkbookEvaluator[] _evaluators;
    private final Map<String, WorkbookEvaluator> _evaluatorsByName;
    private boolean _unhooked;

    public static final class WorkbookNotFoundException extends Exception {
        WorkbookNotFoundException(String str) {
            super(str);
        }
    }

    private CollaboratingWorkbooksEnvironment() {
        this._evaluatorsByName = Collections.emptyMap();
        this._evaluators = new WorkbookEvaluator[0];
    }

    public static void setup(String[] strArr, WorkbookEvaluator[] workbookEvaluatorArr) {
        int length = strArr.length;
        if (workbookEvaluatorArr.length == length) {
            if (length < 1) {
                throw new IllegalArgumentException("Must provide at least one collaborating worbook");
            }
            new CollaboratingWorkbooksEnvironment(strArr, workbookEvaluatorArr, length);
        } else {
            throw new IllegalArgumentException("Number of workbook names is " + length + " but number of evaluators is " + workbookEvaluatorArr.length);
        }
    }

    private CollaboratingWorkbooksEnvironment(String[] strArr, WorkbookEvaluator[] workbookEvaluatorArr, int i) {
        int i2 = (i * 3) / 2;
        HashMap map = new HashMap(i2);
        IdentityHashMap identityHashMap = new IdentityHashMap(i2);
        for (int i3 = 0; i3 < i; i3++) {
            String str = strArr[i3];
            WorkbookEvaluator workbookEvaluator = workbookEvaluatorArr[i3];
            if (map.containsKey(str)) {
                throw new IllegalArgumentException("Duplicate workbook name '" + str + "'");
            }
            if (identityHashMap.containsKey(workbookEvaluator)) {
                throw new IllegalArgumentException("Attempted to register same workbook under names '" + ((String) identityHashMap.get(workbookEvaluator)) + "' and '" + str + "'");
            }
            identityHashMap.put(workbookEvaluator, str);
            map.put(str, workbookEvaluator);
        }
        unhookOldEnvironments(workbookEvaluatorArr);
        hookNewEnvironment(workbookEvaluatorArr, this);
        this._unhooked = false;
        this._evaluators = workbookEvaluatorArr;
        this._evaluatorsByName = map;
    }

    private static void hookNewEnvironment(WorkbookEvaluator[] workbookEvaluatorArr, CollaboratingWorkbooksEnvironment collaboratingWorkbooksEnvironment) {
        int length = workbookEvaluatorArr.length;
        IEvaluationListener evaluationListener = workbookEvaluatorArr[0].getEvaluationListener();
        for (WorkbookEvaluator workbookEvaluator : workbookEvaluatorArr) {
            if (evaluationListener != workbookEvaluator.getEvaluationListener()) {
                throw new RuntimeException("Workbook evaluators must all have the same evaluation listener");
            }
        }
        EvaluationCache evaluationCache = new EvaluationCache(evaluationListener);
        for (int i = 0; i < length; i++) {
            workbookEvaluatorArr[i].attachToEnvironment(collaboratingWorkbooksEnvironment, evaluationCache, i);
        }
    }

    private void unhookOldEnvironments(WorkbookEvaluator[] workbookEvaluatorArr) {
        HashSet hashSet = new HashSet();
        for (WorkbookEvaluator workbookEvaluator : workbookEvaluatorArr) {
            hashSet.add(workbookEvaluator.getEnvironment());
        }
        int size = hashSet.size();
        CollaboratingWorkbooksEnvironment[] collaboratingWorkbooksEnvironmentArr = new CollaboratingWorkbooksEnvironment[size];
        hashSet.toArray(collaboratingWorkbooksEnvironmentArr);
        for (int i = 0; i < size; i++) {
            collaboratingWorkbooksEnvironmentArr[i].unhook();
        }
    }

    private void unhook() {
        if (this._evaluators.length < 1) {
            return;
        }
        int i = 0;
        while (true) {
            WorkbookEvaluator[] workbookEvaluatorArr = this._evaluators;
            if (i < workbookEvaluatorArr.length) {
                workbookEvaluatorArr[i].detachFromEnvironment();
                i++;
            } else {
                this._unhooked = true;
                return;
            }
        }
    }

    public WorkbookEvaluator getWorkbookEvaluator(String str) throws WorkbookNotFoundException {
        if (this._unhooked) {
            throw new IllegalStateException("This environment has been unhooked");
        }
        WorkbookEvaluator workbookEvaluator = this._evaluatorsByName.get(str);
        if (workbookEvaluator != null) {
            return workbookEvaluator;
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("Could not resolve external workbook name '");
        stringBuffer.append(str);
        stringBuffer.append("'.");
        if (this._evaluators.length < 1) {
            stringBuffer.append(" Workbook environment has not been set up.");
        } else {
            stringBuffer.append(" The following workbook names are valid: (");
            Iterator<String> it2 = this._evaluatorsByName.keySet().iterator();
            int i = 0;
            while (it2.hasNext()) {
                int i2 = i + 1;
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append("'");
                stringBuffer.append(it2.next());
                stringBuffer.append("'");
                i = i2;
            }
            stringBuffer.append(")");
        }
        throw new WorkbookNotFoundException(stringBuffer.toString());
    }
}
