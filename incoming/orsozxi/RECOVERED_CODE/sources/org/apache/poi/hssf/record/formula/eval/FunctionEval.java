package org.apache.poi.hssf.record.formula.eval;

import it.orsozoxi.android.orsonotes.R2;
import org.apache.poi.hssf.record.formula.function.FunctionMetadata;
import org.apache.poi.hssf.record.formula.function.FunctionMetadataRegistry;
import org.apache.poi.hssf.record.formula.functions.AggregateFunction;
import org.apache.poi.hssf.record.formula.functions.BooleanFunction;
import org.apache.poi.hssf.record.formula.functions.CalendarFieldFunction;
import org.apache.poi.hssf.record.formula.functions.Choose;
import org.apache.poi.hssf.record.formula.functions.Column;
import org.apache.poi.hssf.record.formula.functions.Columns;
import org.apache.poi.hssf.record.formula.functions.Count;
import org.apache.poi.hssf.record.formula.functions.Counta;
import org.apache.poi.hssf.record.formula.functions.Countblank;
import org.apache.poi.hssf.record.formula.functions.Countif;
import org.apache.poi.hssf.record.formula.functions.DateFunc;
import org.apache.poi.hssf.record.formula.functions.Days360;
import org.apache.poi.hssf.record.formula.functions.Errortype;
import org.apache.poi.hssf.record.formula.functions.Even;
import org.apache.poi.hssf.record.formula.functions.FinanceFunction;
import org.apache.poi.hssf.record.formula.functions.Function;
import org.apache.poi.hssf.record.formula.functions.Hlookup;
import org.apache.poi.hssf.record.formula.functions.Hyperlink;
import org.apache.poi.hssf.record.formula.functions.IfFunc;
import org.apache.poi.hssf.record.formula.functions.Index;
import org.apache.poi.hssf.record.formula.functions.LogicalFunction;
import org.apache.poi.hssf.record.formula.functions.Lookup;
import org.apache.poi.hssf.record.formula.functions.Match;
import org.apache.poi.hssf.record.formula.functions.MinaMaxa;
import org.apache.poi.hssf.record.formula.functions.Mode;
import org.apache.poi.hssf.record.formula.functions.Na;
import org.apache.poi.hssf.record.formula.functions.NotImplementedFunction;
import org.apache.poi.hssf.record.formula.functions.Now;
import org.apache.poi.hssf.record.formula.functions.Npv;
import org.apache.poi.hssf.record.formula.functions.NumericFunction;
import org.apache.poi.hssf.record.formula.functions.Odd;
import org.apache.poi.hssf.record.formula.functions.Offset;
import org.apache.poi.hssf.record.formula.functions.Replace;
import org.apache.poi.hssf.record.formula.functions.RowFunc;
import org.apache.poi.hssf.record.formula.functions.Rows;
import org.apache.poi.hssf.record.formula.functions.Substitute;
import org.apache.poi.hssf.record.formula.functions.Subtotal;
import org.apache.poi.hssf.record.formula.functions.Sumif;
import org.apache.poi.hssf.record.formula.functions.Sumproduct;
import org.apache.poi.hssf.record.formula.functions.Sumx2my2;
import org.apache.poi.hssf.record.formula.functions.Sumx2py2;
import org.apache.poi.hssf.record.formula.functions.Sumxmy2;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.record.formula.functions.TextFunction;
import org.apache.poi.hssf.record.formula.functions.TimeFunc;
import org.apache.poi.hssf.record.formula.functions.Today;
import org.apache.poi.hssf.record.formula.functions.Value;
import org.apache.poi.hssf.record.formula.functions.Vlookup;
import org.apache.poi.ss.formula.eval.NotImplementedException;

/* loaded from: classes3.dex */
public final class FunctionEval {
    private static final FunctionID ID = null;
    protected static final Function[] functions = produceFunctions();

    private static final class FunctionID {
        public static final int CHOOSE = 100;
        public static final int EXTERNAL_FUNC = 255;
        public static final int IF = 1;
        public static final int INDIRECT = 148;
        public static final int OFFSET = 78;
        public static final int SUM = 4;

        private FunctionID() {
        }
    }

    private static Function[] produceFunctions() {
        FunctionMetadata functionByIndex;
        Function[] functionArr = new Function[R2.attr.dependency];
        functionArr[0] = new Count();
        functionArr[1] = new IfFunc();
        functionArr[2] = LogicalFunction.ISNA;
        functionArr[3] = LogicalFunction.ISERROR;
        functionArr[4] = AggregateFunction.SUM;
        functionArr[5] = AggregateFunction.AVERAGE;
        functionArr[6] = AggregateFunction.MIN;
        functionArr[7] = AggregateFunction.MAX;
        functionArr[8] = new RowFunc();
        functionArr[9] = new Column();
        functionArr[10] = new Na();
        functionArr[11] = new Npv();
        functionArr[12] = AggregateFunction.STDEV;
        functionArr[13] = NumericFunction.DOLLAR;
        functionArr[15] = NumericFunction.SIN;
        functionArr[16] = NumericFunction.COS;
        functionArr[17] = NumericFunction.TAN;
        functionArr[18] = NumericFunction.ATAN;
        functionArr[19] = NumericFunction.PI;
        functionArr[20] = NumericFunction.SQRT;
        functionArr[21] = NumericFunction.EXP;
        functionArr[22] = NumericFunction.LN;
        functionArr[23] = NumericFunction.LOG10;
        functionArr[24] = NumericFunction.ABS;
        functionArr[25] = NumericFunction.INT;
        functionArr[26] = NumericFunction.SIGN;
        functionArr[27] = NumericFunction.ROUND;
        functionArr[28] = new Lookup();
        functionArr[29] = new Index();
        functionArr[31] = TextFunction.MID;
        functionArr[32] = TextFunction.LEN;
        functionArr[33] = new Value();
        functionArr[34] = BooleanFunction.TRUE;
        functionArr[35] = BooleanFunction.FALSE;
        functionArr[36] = BooleanFunction.AND;
        functionArr[37] = BooleanFunction.OR;
        functionArr[38] = BooleanFunction.NOT;
        functionArr[39] = NumericFunction.MOD;
        functionArr[48] = TextFunction.TEXT;
        functionArr[56] = FinanceFunction.PV;
        functionArr[57] = FinanceFunction.FV;
        functionArr[58] = FinanceFunction.NPER;
        functionArr[59] = FinanceFunction.PMT;
        functionArr[63] = NumericFunction.RAND;
        functionArr[64] = new Match();
        functionArr[65] = DateFunc.instance;
        functionArr[66] = new TimeFunc();
        functionArr[67] = CalendarFieldFunction.DAY;
        functionArr[68] = CalendarFieldFunction.MONTH;
        functionArr[69] = CalendarFieldFunction.YEAR;
        functionArr[74] = new Now();
        functionArr[76] = new Rows();
        functionArr[77] = new Columns();
        functionArr[82] = TextFunction.SEARCH;
        functionArr[78] = new Offset();
        functionArr[82] = TextFunction.SEARCH;
        functionArr[97] = NumericFunction.ATAN2;
        functionArr[98] = NumericFunction.ASIN;
        functionArr[99] = NumericFunction.ACOS;
        functionArr[100] = new Choose();
        functionArr[101] = new Hlookup();
        functionArr[102] = new Vlookup();
        functionArr[105] = LogicalFunction.ISREF;
        functionArr[109] = NumericFunction.LOG;
        functionArr[112] = TextFunction.LOWER;
        functionArr[113] = TextFunction.UPPER;
        functionArr[115] = TextFunction.LEFT;
        functionArr[116] = TextFunction.RIGHT;
        functionArr[117] = TextFunction.EXACT;
        functionArr[118] = TextFunction.TRIM;
        functionArr[119] = new Replace();
        functionArr[120] = new Substitute();
        functionArr[124] = TextFunction.FIND;
        functionArr[127] = LogicalFunction.ISTEXT;
        functionArr[128] = LogicalFunction.ISNUMBER;
        functionArr[129] = LogicalFunction.ISBLANK;
        functionArr[130] = new T();
        functionArr[148] = null;
        functionArr[169] = new Counta();
        functionArr[183] = AggregateFunction.PRODUCT;
        functionArr[184] = NumericFunction.FACT;
        functionArr[190] = LogicalFunction.ISNONTEXT;
        functionArr[197] = NumericFunction.TRUNC;
        functionArr[198] = LogicalFunction.ISLOGICAL;
        functionArr[212] = NumericFunction.ROUNDUP;
        functionArr[213] = NumericFunction.ROUNDDOWN;
        functionArr[220] = new Days360();
        functionArr[221] = new Today();
        functionArr[227] = AggregateFunction.MEDIAN;
        functionArr[228] = new Sumproduct();
        functionArr[229] = NumericFunction.SINH;
        functionArr[230] = NumericFunction.COSH;
        functionArr[231] = NumericFunction.TANH;
        functionArr[232] = NumericFunction.ASINH;
        functionArr[233] = NumericFunction.ACOSH;
        functionArr[234] = NumericFunction.ATANH;
        functionArr[255] = null;
        functionArr[261] = new Errortype();
        functionArr[269] = AggregateFunction.AVEDEV;
        functionArr[276] = NumericFunction.COMBIN;
        functionArr[279] = new Even();
        functionArr[285] = NumericFunction.FLOOR;
        functionArr[288] = NumericFunction.CEILING;
        functionArr[298] = new Odd();
        functionArr[300] = NumericFunction.POISSON;
        functionArr[303] = new Sumxmy2();
        functionArr[304] = new Sumx2my2();
        functionArr[305] = new Sumx2py2();
        functionArr[318] = AggregateFunction.DEVSQ;
        functionArr[321] = AggregateFunction.SUMSQ;
        functionArr[325] = AggregateFunction.LARGE;
        functionArr[326] = AggregateFunction.SMALL;
        functionArr[330] = new Mode();
        functionArr[336] = TextFunction.CONCATENATE;
        functionArr[337] = NumericFunction.POWER;
        functionArr[342] = NumericFunction.RADIANS;
        functionArr[343] = NumericFunction.DEGREES;
        functionArr[344] = new Subtotal();
        functionArr[345] = new Sumif();
        functionArr[346] = new Countif();
        functionArr[347] = new Countblank();
        functionArr[359] = new Hyperlink();
        functionArr[362] = MinaMaxa.MAXA;
        functionArr[363] = MinaMaxa.MINA;
        for (int i = 0; i < 368; i++) {
            if (functionArr[i] == null && (functionByIndex = FunctionMetadataRegistry.getFunctionByIndex(i)) != null) {
                functionArr[i] = new NotImplementedFunction(functionByIndex.getName());
            }
        }
        return functionArr;
    }

    public static Function getBasicFunction(int i) {
        if (i == 148 || i == 255) {
            return null;
        }
        Function function = functions[i];
        if (function != null) {
            return function;
        }
        throw new NotImplementedException("FuncIx=" + i);
    }
}
