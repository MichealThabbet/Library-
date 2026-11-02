package org.apache.poi.hssf.record.formula.atp;

import java.util.HashMap;
import java.util.Map;
import net.fortuna.ical4j.model.Property;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.hssf.record.formula.udf.UDFFinder;
import org.apache.poi.ss.formula.OperationEvaluationContext;
import org.apache.poi.ss.formula.eval.NotImplementedException;

/* loaded from: classes3.dex */
public final class AnalysisToolPak implements UDFFinder {
    public static final UDFFinder instance = new AnalysisToolPak();
    private final Map<String, FreeRefFunction> _functionsByName = createFunctionsMap();

    private static final class NotImplemented implements FreeRefFunction {
        private final String _functionName;

        public NotImplemented(String str) {
            this._functionName = str;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.FreeRefFunction
        public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
            throw new NotImplementedException(this._functionName);
        }
    }

    private AnalysisToolPak() {
    }

    @Override // org.apache.poi.hssf.record.formula.udf.UDFFinder
    public FreeRefFunction findFunction(String str) {
        return this._functionsByName.get(str);
    }

    private Map<String, FreeRefFunction> createFunctionsMap() {
        HashMap map = new HashMap(100);
        r(map, "ACCRINT", null);
        r(map, "ACCRINTM", null);
        r(map, "AMORDEGRC", null);
        r(map, "AMORLINC", null);
        r(map, "BESSELI", null);
        r(map, "BESSELJ", null);
        r(map, "BESSELK", null);
        r(map, "BESSELY", null);
        r(map, "BIN2DEC", null);
        r(map, "BIN2HEX", null);
        r(map, "BIN2OCT", null);
        r(map, "CO MPLEX", null);
        r(map, "CONVERT", null);
        r(map, "COUPDAYBS", null);
        r(map, "COUPDAYS", null);
        r(map, "COUPDAYSNC", null);
        r(map, "COUPNCD", null);
        r(map, "COUPNUM", null);
        r(map, "COUPPCD", null);
        r(map, "CUMIPMT", null);
        r(map, "CUMPRINC", null);
        r(map, "DEC2BIN", null);
        r(map, "DEC2HEX", null);
        r(map, "DEC2OCT", null);
        r(map, "DELTA", null);
        r(map, "DISC", null);
        r(map, "DOLLARDE", null);
        r(map, "DOLLARFR", null);
        r(map, Property.DURATION, null);
        r(map, "EDATE", null);
        r(map, "EFFECT", null);
        r(map, "EOMONTH", null);
        r(map, "ERF", null);
        r(map, "ERFC", null);
        r(map, "FACTDOUBLE", null);
        r(map, "FVSCHEDULE", null);
        r(map, "GCD", null);
        r(map, "GESTEP", null);
        r(map, "HEX2BIN", null);
        r(map, "HEX2DEC", null);
        r(map, "HEX2OCT", null);
        r(map, "IMABS", null);
        r(map, "IMAGINARY", null);
        r(map, "IMARGUMENT", null);
        r(map, "IMCONJUGATE", null);
        r(map, "IMCOS", null);
        r(map, "IMDIV", null);
        r(map, "IMEXP", null);
        r(map, "IMLN", null);
        r(map, "IMLOG10", null);
        r(map, "IMLOG2", null);
        r(map, "IMPOWER", null);
        r(map, "IMPRODUCT", null);
        r(map, "IMREAL", null);
        r(map, "IMSIN", null);
        r(map, "IMSQRT", null);
        r(map, "IMSUB", null);
        r(map, "IMSUM", null);
        r(map, "INTRATE", null);
        r(map, "ISEVEN", ParityFunction.IS_EVEN);
        r(map, "ISODD", ParityFunction.IS_ODD);
        r(map, "LCM", null);
        r(map, "MDURATION", null);
        r(map, "MROUND", null);
        r(map, "MULTINOMIAL", null);
        r(map, "NETWORKDAYS", null);
        r(map, "NOMINAL", null);
        r(map, "OCT2BIN", null);
        r(map, "OCT2DEC", null);
        r(map, "OCT2HEX", null);
        r(map, "ODDFPRICE", null);
        r(map, "ODDFYIELD", null);
        r(map, "ODDLPRICE", null);
        r(map, "ODDLYIELD", null);
        r(map, "PRICE", null);
        r(map, "PRICEDISC", null);
        r(map, "PRICEMAT", null);
        r(map, "QUOTIENT", null);
        r(map, "RANDBETWEEN", RandBetween.instance);
        r(map, "RECEIVED", null);
        r(map, "SERIESSUM", null);
        r(map, "SQRTPI", null);
        r(map, "TBILLEQ", null);
        r(map, "TBILLPRICE", null);
        r(map, "TBILLYIELD", null);
        r(map, "WEEKNUM", null);
        r(map, "WORKDAY", null);
        r(map, "XIRR", null);
        r(map, "XNPV", null);
        r(map, "YEARFRAC", YearFrac.instance);
        r(map, "YIELD", null);
        r(map, "YIELDDISC", null);
        r(map, "YIELDMAT", null);
        return map;
    }

    private static void r(Map<String, FreeRefFunction> map, String str, FreeRefFunction freeRefFunction) {
        if (freeRefFunction == null) {
            freeRefFunction = new NotImplemented(str);
        }
        map.put(str, freeRefFunction);
    }
}
