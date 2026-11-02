package org.apache.poi.ss.formula;

import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.record.constant.ErrorConstant;
import org.apache.poi.hssf.record.formula.AbstractFunctionPtg;
import org.apache.poi.hssf.record.formula.AddPtg;
import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.AreaPtg;
import org.apache.poi.hssf.record.formula.ArrayPtg;
import org.apache.poi.hssf.record.formula.AttrPtg;
import org.apache.poi.hssf.record.formula.BoolPtg;
import org.apache.poi.hssf.record.formula.ConcatPtg;
import org.apache.poi.hssf.record.formula.DividePtg;
import org.apache.poi.hssf.record.formula.EqualPtg;
import org.apache.poi.hssf.record.formula.ErrPtg;
import org.apache.poi.hssf.record.formula.FuncPtg;
import org.apache.poi.hssf.record.formula.FuncVarPtg;
import org.apache.poi.hssf.record.formula.GreaterEqualPtg;
import org.apache.poi.hssf.record.formula.GreaterThanPtg;
import org.apache.poi.hssf.record.formula.IntPtg;
import org.apache.poi.hssf.record.formula.LessEqualPtg;
import org.apache.poi.hssf.record.formula.LessThanPtg;
import org.apache.poi.hssf.record.formula.MemAreaPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.MissingArgPtg;
import org.apache.poi.hssf.record.formula.MultiplyPtg;
import org.apache.poi.hssf.record.formula.NamePtg;
import org.apache.poi.hssf.record.formula.NameXPtg;
import org.apache.poi.hssf.record.formula.NotEqualPtg;
import org.apache.poi.hssf.record.formula.NumberPtg;
import org.apache.poi.hssf.record.formula.OperandPtg;
import org.apache.poi.hssf.record.formula.OperationPtg;
import org.apache.poi.hssf.record.formula.ParenthesisPtg;
import org.apache.poi.hssf.record.formula.PercentPtg;
import org.apache.poi.hssf.record.formula.PowerPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.RangePtg;
import org.apache.poi.hssf.record.formula.Ref3DPtg;
import org.apache.poi.hssf.record.formula.RefPtg;
import org.apache.poi.hssf.record.formula.StringPtg;
import org.apache.poi.hssf.record.formula.SubtractPtg;
import org.apache.poi.hssf.record.formula.UnaryMinusPtg;
import org.apache.poi.hssf.record.formula.UnaryPlusPtg;
import org.apache.poi.hssf.record.formula.UnionPtg;
import org.apache.poi.hssf.record.formula.ValueOperatorPtg;
import org.apache.poi.hssf.record.formula.function.FunctionMetadata;
import org.apache.poi.hssf.record.formula.function.FunctionMetadataRegistry;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

/* loaded from: classes3.dex */
public final class FormulaParser {
    private static final Pattern CELL_REF_PATTERN = Pattern.compile("(\\$?[A-Za-z]+)?(\\$?[0-9]+)?");
    private static char TAB = '\t';
    private FormulaParsingWorkbook _book;
    private final int _formulaLength;
    private final String _formulaString;
    private int _pointer = 0;
    private ParseNode _rootNode;
    private int _sheetIndex;
    private SpreadsheetVersion _ssVersion;
    private char look;

    private static boolean isArgumentDelimiter(char c) {
        return c == ',' || c == ')';
    }

    private static final class Identifier {
        private final boolean _isQuoted;
        private final String _name;

        public Identifier(String str, boolean z) {
            this._name = str;
            this._isQuoted = z;
        }

        public String getName() {
            return this._name;
        }

        public boolean isQuoted() {
            return this._isQuoted;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            if (this._isQuoted) {
                stringBuffer.append("'");
                stringBuffer.append(this._name);
                stringBuffer.append("'");
            } else {
                stringBuffer.append(this._name);
            }
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private static final class SheetIdentifier {
        private final String _bookName;
        private final Identifier _sheetIdentifier;

        public SheetIdentifier(String str, Identifier identifier) {
            this._bookName = str;
            this._sheetIdentifier = identifier;
        }

        public String getBookName() {
            return this._bookName;
        }

        public Identifier getSheetIdentifier() {
            return this._sheetIdentifier;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            if (this._bookName != null) {
                stringBuffer.append(" [");
                stringBuffer.append(this._sheetIdentifier.getName());
                stringBuffer.append("]");
            }
            if (this._sheetIdentifier.isQuoted()) {
                stringBuffer.append("'");
                stringBuffer.append(this._sheetIdentifier.getName());
                stringBuffer.append("'");
            } else {
                stringBuffer.append(this._sheetIdentifier.getName());
            }
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private FormulaParser(String str, FormulaParsingWorkbook formulaParsingWorkbook, int i) {
        this._formulaString = str;
        this._book = formulaParsingWorkbook;
        this._ssVersion = formulaParsingWorkbook == null ? SpreadsheetVersion.EXCEL97 : formulaParsingWorkbook.getSpreadsheetVersion();
        this._formulaLength = this._formulaString.length();
        this._sheetIndex = i;
    }

    public static Ptg[] parse(String str, FormulaParsingWorkbook formulaParsingWorkbook, int i, int i2) {
        FormulaParser formulaParser = new FormulaParser(str, formulaParsingWorkbook, i2);
        formulaParser.parse();
        return formulaParser.getRPNPtg(i);
    }

    private void GetChar() {
        int i = this._pointer;
        int i2 = this._formulaLength;
        if (i > i2) {
            throw new RuntimeException("too far");
        }
        if (i < i2) {
            this.look = this._formulaString.charAt(i);
        } else {
            this.look = (char) 0;
        }
        this._pointer++;
    }

    private void resetPointer(int i) {
        this._pointer = i;
        if (i <= this._formulaLength) {
            this.look = this._formulaString.charAt(i - 1);
        } else {
            this.look = (char) 0;
        }
    }

    private RuntimeException expected(String str) {
        String str2;
        if (this.look == '=' && this._formulaString.substring(0, this._pointer - 1).trim().length() < 1) {
            str2 = "The specified formula '" + this._formulaString + "' starts with an equals sign which is not allowed.";
        } else {
            str2 = "Parse error near char " + (this._pointer - 1) + " '" + this.look + "' in specified formula '" + this._formulaString + "'. Expected " + str;
        }
        return new FormulaParseException(str2);
    }

    private static boolean IsAlpha(char c) {
        return Character.isLetter(c) || c == '$' || c == '_';
    }

    private static boolean IsDigit(char c) {
        return Character.isDigit(c);
    }

    private static boolean IsWhite(char c) {
        return c == ' ' || c == TAB;
    }

    private void SkipWhite() {
        while (IsWhite(this.look)) {
            GetChar();
        }
    }

    private void Match(char c) {
        if (this.look != c) {
            throw expected("'" + c + "'");
        }
        GetChar();
    }

    private String GetNum() {
        StringBuffer stringBuffer = new StringBuffer();
        while (IsDigit(this.look)) {
            stringBuffer.append(this.look);
            GetChar();
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        return stringBuffer.toString();
    }

    private ParseNode parseRangeExpression() throws NumberFormatException {
        ParseNode rangeable = parseRangeable();
        boolean z = false;
        while (this.look == ':') {
            int i = this._pointer;
            GetChar();
            ParseNode rangeable2 = parseRangeable();
            checkValidRangeOperand("LHS", i, rangeable);
            checkValidRangeOperand("RHS", i, rangeable2);
            rangeable = new ParseNode(RangePtg.instance, new ParseNode[]{rangeable, rangeable2});
            z = true;
        }
        return z ? augmentWithMemPtg(rangeable) : rangeable;
    }

    private static ParseNode augmentWithMemPtg(ParseNode parseNode) {
        Ptg memAreaPtg;
        if (needsMemFunc(parseNode)) {
            memAreaPtg = new MemFuncPtg(parseNode.getEncodedSize());
        } else {
            memAreaPtg = new MemAreaPtg(parseNode.getEncodedSize());
        }
        return new ParseNode(memAreaPtg, parseNode);
    }

    private static boolean needsMemFunc(ParseNode parseNode) {
        Ptg token = parseNode.getToken();
        if ((token instanceof AbstractFunctionPtg) || (token instanceof ExternSheetReferenceToken) || (token instanceof NamePtg) || (token instanceof NameXPtg)) {
            return true;
        }
        boolean z = token instanceof OperationPtg;
        if (!z && !(token instanceof ParenthesisPtg)) {
            return !(token instanceof OperandPtg) && z;
        }
        for (ParseNode parseNode2 : parseNode.getChildren()) {
            if (needsMemFunc(parseNode2)) {
                return true;
            }
        }
        return false;
    }

    private static void checkValidRangeOperand(String str, int i, ParseNode parseNode) {
        if (isValidRangeOperand(parseNode)) {
            return;
        }
        throw new FormulaParseException("The " + str + " of the range operator ':' at position " + i + " is not a proper reference.");
    }

    private static boolean isValidRangeOperand(ParseNode parseNode) {
        Ptg token = parseNode.getToken();
        if (token instanceof OperandPtg) {
            return true;
        }
        if (token instanceof AbstractFunctionPtg) {
            return ((AbstractFunctionPtg) token).getDefaultOperandClass() == 0;
        }
        if (token instanceof ValueOperatorPtg) {
            return false;
        }
        if (token instanceof OperationPtg) {
            return true;
        }
        if (token instanceof ParenthesisPtg) {
            return isValidRangeOperand(parseNode.getChildren()[0]);
        }
        return token == ErrPtg.REF_INVALID;
    }

    private ParseNode parseRangeable() throws NumberFormatException {
        char c;
        String str;
        SkipWhite();
        int i = this._pointer;
        SheetIdentifier sheetName = parseSheetName();
        if (sheetName == null) {
            resetPointer(i);
        } else {
            SkipWhite();
            i = this._pointer;
        }
        SimpleRangePart simpleRangePart = parseSimpleRangePart();
        if (simpleRangePart == null) {
            if (sheetName != null) {
                if (this.look == '#') {
                    return new ParseNode(ErrPtg.valueOf(parseErrorLiteral()));
                }
                throw new FormulaParseException("Cell reference expected after sheet name at index " + this._pointer + ".");
            }
            return parseNonRange(i);
        }
        boolean zIsWhite = IsWhite(this.look);
        if (zIsWhite) {
            SkipWhite();
        }
        char c2 = this.look;
        if (c2 == ':') {
            int i2 = this._pointer;
            GetChar();
            SkipWhite();
            SimpleRangePart simpleRangePart2 = parseSimpleRangePart();
            SimpleRangePart simpleRangePart3 = (simpleRangePart2 == null || simpleRangePart.isCompatibleForArea(simpleRangePart2)) ? simpleRangePart2 : null;
            if (simpleRangePart3 == null) {
                resetPointer(i2);
                if (!simpleRangePart.isCell()) {
                    if (sheetName == null) {
                        str = "";
                    } else {
                        str = "'" + sheetName.getSheetIdentifier().getName() + '!';
                    }
                    throw new FormulaParseException(str + simpleRangePart.getRep() + "' is not a proper reference.");
                }
                return createAreaRefParseNode(sheetName, simpleRangePart, simpleRangePart3);
            }
            return createAreaRefParseNode(sheetName, simpleRangePart, simpleRangePart3);
        }
        if (c2 == '.') {
            GetChar();
            int i3 = 1;
            while (true) {
                c = this.look;
                if (c != '.') {
                    break;
                }
                i3++;
                GetChar();
            }
            boolean zIsWhite2 = IsWhite(c);
            SkipWhite();
            SimpleRangePart simpleRangePart4 = parseSimpleRangePart();
            String strSubstring = this._formulaString.substring(i - 1, this._pointer - 1);
            if (simpleRangePart4 == null) {
                if (sheetName != null) {
                    throw new FormulaParseException("Complete area reference expected after sheet name at index " + this._pointer + ".");
                }
                return parseNonRange(i);
            }
            if (zIsWhite || zIsWhite2) {
                if (simpleRangePart.isRowOrColumn() || simpleRangePart4.isRowOrColumn()) {
                    throw new FormulaParseException("Dotted range (full row or column) expression '" + strSubstring + "' must not contain whitespace.");
                }
                return createAreaRefParseNode(sheetName, simpleRangePart, simpleRangePart4);
            }
            if (i3 == 1 && simpleRangePart.isRow() && simpleRangePart4.isRow()) {
                return parseNonRange(i);
            }
            if ((simpleRangePart.isRowOrColumn() || simpleRangePart4.isRowOrColumn()) && i3 != 2) {
                throw new FormulaParseException("Dotted range (full row or column) expression '" + strSubstring + "' must have exactly 2 dots.");
            }
            return createAreaRefParseNode(sheetName, simpleRangePart, simpleRangePart4);
        }
        if (simpleRangePart.isCell() && isValidCellReference(simpleRangePart.getRep())) {
            return createAreaRefParseNode(sheetName, simpleRangePart, null);
        }
        if (sheetName != null) {
            throw new FormulaParseException("Second part of cell reference expected after sheet name at index " + this._pointer + ".");
        }
        return parseNonRange(i);
    }

    private ParseNode parseNonRange(int i) {
        resetPointer(i);
        if (Character.isDigit(this.look)) {
            return new ParseNode(parseNumber());
        }
        if (this.look == '\"') {
            return new ParseNode(new StringPtg(parseStringLiteral()));
        }
        StringBuilder sb = new StringBuilder();
        if (!Character.isLetter(this.look) && this.look != '_') {
            throw expected("number, string, or defined name");
        }
        while (isValidDefinedNameChar(this.look)) {
            sb.append(this.look);
            GetChar();
        }
        SkipWhite();
        String string = sb.toString();
        if (this.look == '(') {
            return function(string);
        }
        if (string.equalsIgnoreCase("TRUE") || string.equalsIgnoreCase("FALSE")) {
            return new ParseNode(BoolPtg.valueOf(string.equalsIgnoreCase("TRUE")));
        }
        FormulaParsingWorkbook formulaParsingWorkbook = this._book;
        if (formulaParsingWorkbook == null) {
            throw new IllegalStateException("Need book to evaluate name '" + string + "'");
        }
        EvaluationName name = formulaParsingWorkbook.getName(string, this._sheetIndex);
        if (name == null) {
            throw new FormulaParseException("Specified named range '" + string + "' does not exist in the current workbook.");
        }
        if (name.isRange()) {
            return new ParseNode(name.createPtg());
        }
        throw new FormulaParseException("Specified name '" + string + "' is not a range as expected.");
    }

    private static boolean isValidDefinedNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '.' || c == '?' || c == '\\' || c == '_';
    }

    private ParseNode createAreaRefParseNode(SheetIdentifier sheetIdentifier, SimpleRangePart simpleRangePart, SimpleRangePart simpleRangePart2) throws FormulaParseException {
        int externalSheetIndex;
        Ptg area3DPtg;
        if (sheetIdentifier == null) {
            externalSheetIndex = Integer.MIN_VALUE;
        } else {
            String name = sheetIdentifier.getSheetIdentifier().getName();
            if (sheetIdentifier.getBookName() == null) {
                externalSheetIndex = this._book.getExternalSheetIndex(name);
            } else {
                externalSheetIndex = this._book.getExternalSheetIndex(sheetIdentifier.getBookName(), name);
            }
        }
        if (simpleRangePart2 == null) {
            CellReference cellReference = simpleRangePart.getCellReference();
            if (sheetIdentifier == null) {
                area3DPtg = new RefPtg(cellReference);
            } else {
                area3DPtg = new Ref3DPtg(cellReference, externalSheetIndex);
            }
        } else {
            AreaReference areaReferenceCreateAreaRef = createAreaRef(simpleRangePart, simpleRangePart2);
            if (sheetIdentifier == null) {
                area3DPtg = new AreaPtg(areaReferenceCreateAreaRef);
            } else {
                area3DPtg = new Area3DPtg(areaReferenceCreateAreaRef, externalSheetIndex);
            }
        }
        return new ParseNode(area3DPtg);
    }

    private static AreaReference createAreaRef(SimpleRangePart simpleRangePart, SimpleRangePart simpleRangePart2) {
        if (!simpleRangePart.isCompatibleForArea(simpleRangePart2)) {
            throw new FormulaParseException("has incompatible parts: '" + simpleRangePart.getRep() + "' and '" + simpleRangePart2.getRep() + "'.");
        }
        if (simpleRangePart.isRow()) {
            return AreaReference.getWholeRow(simpleRangePart.getRep(), simpleRangePart2.getRep());
        }
        if (simpleRangePart.isColumn()) {
            return AreaReference.getWholeColumn(simpleRangePart.getRep(), simpleRangePart2.getRep());
        }
        return new AreaReference(simpleRangePart.getCellReference(), simpleRangePart2.getCellReference());
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0073, code lost:
    
        if (r5 <= 65536) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.apache.poi.ss.formula.FormulaParser.SimpleRangePart parseSimpleRangePart() throws java.lang.NumberFormatException {
        /*
            r8 = this;
            int r0 = r8._pointer
            r1 = 1
            int r0 = r0 - r1
            r2 = 0
            r3 = 0
        L6:
            int r4 = r8._formulaLength
            if (r0 >= r4) goto L2b
            java.lang.String r4 = r8._formulaString
            char r4 = r4.charAt(r0)
            boolean r5 = java.lang.Character.isDigit(r4)
            if (r5 == 0) goto L18
            r2 = 1
            goto L28
        L18:
            boolean r5 = java.lang.Character.isLetter(r4)
            if (r5 == 0) goto L20
            r3 = 1
            goto L28
        L20:
            r5 = 36
            if (r4 == r5) goto L28
            r5 = 95
            if (r4 != r5) goto L2b
        L28:
            int r0 = r0 + 1
            goto L6
        L2b:
            int r4 = r8._pointer
            int r5 = r4 + (-1)
            r6 = 0
            if (r0 > r5) goto L33
            return r6
        L33:
            java.lang.String r5 = r8._formulaString
            int r4 = r4 - r1
            java.lang.String r4 = r5.substring(r4, r0)
            java.util.regex.Pattern r5 = org.apache.poi.ss.formula.FormulaParser.CELL_REF_PATTERN
            java.util.regex.Matcher r5 = r5.matcher(r4)
            boolean r5 = r5.matches()
            if (r5 != 0) goto L47
            return r6
        L47:
            if (r3 == 0) goto L52
            if (r2 == 0) goto L52
            boolean r5 = r8.isValidCellReference(r4)
            if (r5 != 0) goto L76
            return r6
        L52:
            java.lang.String r5 = ""
            java.lang.String r7 = "$"
            if (r3 == 0) goto L65
            java.lang.String r5 = r4.replace(r7, r5)
            org.apache.poi.ss.SpreadsheetVersion r7 = r8._ssVersion
            boolean r5 = org.apache.poi.ss.util.CellReference.isColumnWithnRange(r5, r7)
            if (r5 != 0) goto L76
            return r6
        L65:
            if (r2 == 0) goto L80
            java.lang.String r5 = r4.replace(r7, r5)     // Catch: java.lang.NumberFormatException -> L80
            int r5 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.NumberFormatException -> L80
            if (r5 < r1) goto L80
            r7 = 65536(0x10000, float:9.1835E-41)
            if (r5 <= r7) goto L76
            goto L80
        L76:
            int r0 = r0 + r1
            r8.resetPointer(r0)
            org.apache.poi.ss.formula.FormulaParser$SimpleRangePart r0 = new org.apache.poi.ss.formula.FormulaParser$SimpleRangePart
            r0.<init>(r4, r3, r2)
            return r0
        L80:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.formula.FormulaParser.parseSimpleRangePart():org.apache.poi.ss.formula.FormulaParser$SimpleRangePart");
    }

    private static final class SimpleRangePart {
        private final String _rep;
        private final Type _type;

        private enum Type {
            CELL,
            ROW,
            COLUMN;

            public static Type get(boolean z, boolean z2) {
                if (z) {
                    return z2 ? CELL : COLUMN;
                }
                if (!z2) {
                    throw new IllegalArgumentException("must have either letters or numbers");
                }
                return ROW;
            }
        }

        public SimpleRangePart(String str, boolean z, boolean z2) {
            this._rep = str;
            this._type = Type.get(z, z2);
        }

        public boolean isCell() {
            return this._type == Type.CELL;
        }

        public boolean isRowOrColumn() {
            return this._type != Type.CELL;
        }

        public CellReference getCellReference() {
            if (this._type != Type.CELL) {
                throw new IllegalStateException("Not applicable to this type");
            }
            return new CellReference(this._rep);
        }

        public boolean isColumn() {
            return this._type == Type.COLUMN;
        }

        public boolean isRow() {
            return this._type == Type.ROW;
        }

        public String getRep() {
            return this._rep;
        }

        public boolean isCompatibleForArea(SimpleRangePart simpleRangePart) {
            return this._type == simpleRangePart._type;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append(getClass().getName());
            sb.append(" [");
            sb.append(this._rep);
            sb.append("]");
            return sb.toString();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:
    
        if (r9.look == '\'') goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003b, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003e, code lost:
    
        if (r6 != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0040, code lost:
    
        r2.append(r9.look);
        GetChar();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004a, code lost:
    
        if (r9.look != '\'') goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
    
        Match(com.ibm.icu.impl.PatternTokenizer.SINGLE_QUOTE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
    
        if (r9.look == '\'') goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
    
        r4 = new org.apache.poi.ss.formula.FormulaParser.Identifier(r2.toString(), true);
        SkipWhite();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0062, code lost:
    
        if (r9.look != '!') goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0064, code lost:
    
        GetChar();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006c, code lost:
    
        return new org.apache.poi.ss.formula.FormulaParser.SheetIdentifier(r0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x003e, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:?, code lost:
    
        return null;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0051 -> B:14:0x003b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.apache.poi.ss.formula.FormulaParser.SheetIdentifier parseSheetName() {
        /*
            r9 = this;
            char r0 = r9.look
            r1 = 0
            r2 = 91
            if (r0 != r2) goto L24
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r9.GetChar()
        Lf:
            char r2 = r9.look
            r3 = 93
            if (r2 == r3) goto L1c
            r0.append(r2)
            r9.GetChar()
            goto Lf
        L1c:
            r9.GetChar()
            java.lang.String r0 = r0.toString()
            goto L25
        L24:
            r0 = r1
        L25:
            char r2 = r9.look
            r3 = 33
            r4 = 0
            r5 = 39
            if (r2 != r5) goto L6d
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            r9.Match(r5)
            char r6 = r9.look
            r7 = 1
            if (r6 != r5) goto L3d
        L3b:
            r6 = 1
            goto L3e
        L3d:
            r6 = 0
        L3e:
            if (r6 != 0) goto L54
            char r8 = r9.look
            r2.append(r8)
            r9.GetChar()
            char r8 = r9.look
            if (r8 != r5) goto L3e
            r9.Match(r5)
            char r6 = r9.look
            if (r6 == r5) goto L3d
            goto L3b
        L54:
            org.apache.poi.ss.formula.FormulaParser$Identifier r4 = new org.apache.poi.ss.formula.FormulaParser$Identifier
            java.lang.String r2 = r2.toString()
            r4.<init>(r2, r7)
            r9.SkipWhite()
            char r2 = r9.look
            if (r2 != r3) goto L6c
            r9.GetChar()
            org.apache.poi.ss.formula.FormulaParser$SheetIdentifier r1 = new org.apache.poi.ss.formula.FormulaParser$SheetIdentifier
            r1.<init>(r0, r4)
        L6c:
            return r1
        L6d:
            r5 = 95
            if (r2 == r5) goto L79
            boolean r2 = java.lang.Character.isLetter(r2)
            if (r2 == 0) goto L78
            goto L79
        L78:
            return r1
        L79:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L7e:
            char r5 = r9.look
            boolean r5 = isUnquotedSheetNameChar(r5)
            if (r5 == 0) goto L8f
            char r5 = r9.look
            r2.append(r5)
            r9.GetChar()
            goto L7e
        L8f:
            r9.SkipWhite()
            char r5 = r9.look
            if (r5 != r3) goto La7
            r9.GetChar()
            org.apache.poi.ss.formula.FormulaParser$SheetIdentifier r1 = new org.apache.poi.ss.formula.FormulaParser$SheetIdentifier
            org.apache.poi.ss.formula.FormulaParser$Identifier r3 = new org.apache.poi.ss.formula.FormulaParser$Identifier
            java.lang.String r2 = r2.toString()
            r3.<init>(r2, r4)
            r1.<init>(r0, r3)
        La7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.formula.FormulaParser.parseSheetName():org.apache.poi.ss.formula.FormulaParser$SheetIdentifier");
    }

    private static boolean isUnquotedSheetNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '.' || c == '_';
    }

    private boolean isValidCellReference(String str) {
        boolean z = CellReference.classifyCellReference(str, this._ssVersion) == CellReference.NameType.CELL;
        if (!z) {
            return z;
        }
        if (!(FunctionMetadataRegistry.getFunctionByName(str.toUpperCase()) != null)) {
            return z;
        }
        int i = this._pointer;
        resetPointer(str.length() + i);
        SkipWhite();
        boolean z2 = this.look != '(';
        resetPointer(i);
        return z2;
    }

    private ParseNode function(String str) {
        Ptg ptgCreatePtg;
        if (AbstractFunctionPtg.isBuiltInFunctionName(str)) {
            ptgCreatePtg = null;
        } else {
            FormulaParsingWorkbook formulaParsingWorkbook = this._book;
            if (formulaParsingWorkbook == null) {
                throw new IllegalStateException("Need book to evaluate name '" + str + "'");
            }
            EvaluationName name = formulaParsingWorkbook.getName(str, this._sheetIndex);
            if (name == null) {
                ptgCreatePtg = this._book.getNameXPtg(str);
                if (ptgCreatePtg == null) {
                    throw new FormulaParseException("Name '" + str + "' is completely unknown in the current workbook");
                }
            } else {
                if (!name.isFunctionName()) {
                    throw new FormulaParseException("Attempt to use name '" + str + "' as a function, but defined name in workbook does not refer to a function");
                }
                ptgCreatePtg = name.createPtg();
            }
        }
        Match('(');
        ParseNode[] parseNodeArrArguments = Arguments();
        Match(')');
        return getFunction(str, ptgCreatePtg, parseNodeArrArguments);
    }

    private ParseNode getFunction(String str, Ptg ptg, ParseNode[] parseNodeArr) {
        Ptg ptgCreate;
        FunctionMetadata functionByName = FunctionMetadataRegistry.getFunctionByName(str.toUpperCase());
        int length = parseNodeArr.length;
        if (functionByName == null) {
            if (ptg == null) {
                throw new IllegalStateException("NamePtg must be supplied for external functions");
            }
            int i = length + 1;
            ParseNode[] parseNodeArr2 = new ParseNode[i];
            parseNodeArr2[0] = new ParseNode(ptg);
            System.arraycopy(parseNodeArr, 0, parseNodeArr2, 1, length);
            return new ParseNode(FuncVarPtg.create(str, i), parseNodeArr2);
        }
        if (ptg != null) {
            throw new IllegalStateException("NamePtg no applicable to internal functions");
        }
        boolean z = !functionByName.hasFixedArgsLength();
        int index = functionByName.getIndex();
        if (index == 4 && parseNodeArr.length == 1) {
            return new ParseNode(AttrPtg.getSumSingle(), parseNodeArr);
        }
        validateNumArgs(parseNodeArr.length, functionByName);
        if (z) {
            ptgCreate = FuncVarPtg.create(str, length);
        } else {
            ptgCreate = FuncPtg.create(index);
        }
        return new ParseNode(ptgCreate, parseNodeArr);
    }

    private void validateNumArgs(int i, FunctionMetadata functionMetadata) {
        int maxParams;
        String str;
        FormulaParsingWorkbook formulaParsingWorkbook;
        String str2;
        if (i < functionMetadata.getMinParams()) {
            String str3 = "Too few arguments to function '" + functionMetadata.getName() + "'. ";
            if (functionMetadata.hasFixedArgsLength()) {
                str2 = str3 + "Expected " + functionMetadata.getMinParams();
            } else {
                str2 = str3 + "At least " + functionMetadata.getMinParams() + " were expected";
            }
            throw new FormulaParseException(str2 + " but got " + i + ".");
        }
        if (functionMetadata.hasUnlimitedVarags() && (formulaParsingWorkbook = this._book) != null) {
            maxParams = formulaParsingWorkbook.getSpreadsheetVersion().getMaxFunctionArgs();
        } else {
            maxParams = functionMetadata.getMaxParams();
        }
        if (i > maxParams) {
            String str4 = "Too many arguments to function '" + functionMetadata.getName() + "'. ";
            if (functionMetadata.hasFixedArgsLength()) {
                str = str4 + "Expected " + maxParams;
            } else {
                str = str4 + "At most " + maxParams + " were expected";
            }
            throw new FormulaParseException(str + " but got " + i + ".");
        }
    }

    private ParseNode[] Arguments() {
        ArrayList arrayList = new ArrayList(2);
        SkipWhite();
        if (this.look == ')') {
            return ParseNode.EMPTY_ARRAY;
        }
        while (true) {
            boolean z = true;
            do {
                SkipWhite();
                if (isArgumentDelimiter(this.look)) {
                    if (z) {
                        arrayList.add(new ParseNode(MissingArgPtg.instance));
                    }
                    if (this.look != ')') {
                        Match(',');
                    } else {
                        ParseNode[] parseNodeArr = new ParseNode[arrayList.size()];
                        arrayList.toArray(parseNodeArr);
                        return parseNodeArr;
                    }
                } else {
                    arrayList.add(comparisonExpression());
                    z = false;
                    SkipWhite();
                }
            } while (isArgumentDelimiter(this.look));
            throw expected("',' or ')'");
        }
    }

    private ParseNode powerFactor() {
        ParseNode parseNodePercentFactor = percentFactor();
        while (true) {
            SkipWhite();
            if (this.look != '^') {
                return parseNodePercentFactor;
            }
            Match('^');
            parseNodePercentFactor = new ParseNode(PowerPtg.instance, parseNodePercentFactor, percentFactor());
        }
    }

    private ParseNode percentFactor() {
        ParseNode simpleFactor = parseSimpleFactor();
        while (true) {
            SkipWhite();
            if (this.look != '%') {
                return simpleFactor;
            }
            Match('%');
            simpleFactor = new ParseNode(PercentPtg.instance, simpleFactor);
        }
    }

    private ParseNode parseSimpleFactor() {
        char c;
        SkipWhite();
        char c2 = this.look;
        if (c2 == '\"') {
            return new ParseNode(new StringPtg(parseStringLiteral()));
        }
        if (c2 == '#') {
            return new ParseNode(ErrPtg.valueOf(parseErrorLiteral()));
        }
        if (c2 == '(') {
            Match('(');
            ParseNode parseNodeComparisonExpression = comparisonExpression();
            Match(')');
            return new ParseNode(ParenthesisPtg.instance, parseNodeComparisonExpression);
        }
        if (c2 == '+') {
            Match('+');
            return parseUnary(true);
        }
        if (c2 == '-') {
            Match(Soundex.SILENT_MARKER);
            return parseUnary(false);
        }
        if (c2 == '{') {
            Match('{');
            ParseNode array = parseArray();
            Match('}');
            return array;
        }
        if (IsAlpha(c2) || Character.isDigit(this.look) || (c = this.look) == '\'' || c == '[') {
            return parseRangeExpression();
        }
        if (c == '.') {
            return new ParseNode(parseNumber());
        }
        throw expected("cell ref or constant literal");
    }

    private ParseNode parseUnary(boolean z) {
        boolean z2 = IsDigit(this.look) || this.look == '.';
        ParseNode parseNodePowerFactor = powerFactor();
        if (z2) {
            Ptg token = parseNodePowerFactor.getToken();
            if (token instanceof NumberPtg) {
                return z ? parseNodePowerFactor : new ParseNode(new NumberPtg(-((NumberPtg) token).getValue()));
            }
            if (token instanceof IntPtg) {
                return z ? parseNodePowerFactor : new ParseNode(new NumberPtg(-((IntPtg) token).getValue()));
            }
        }
        return new ParseNode(z ? UnaryPlusPtg.instance : UnaryMinusPtg.instance, parseNodePowerFactor);
    }

    private ParseNode parseArray() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(parseArrayRow());
            char c = this.look;
            if (c == '}') {
                Object[][] objArr = new Object[arrayList.size()][];
                arrayList.toArray(objArr);
                checkRowLengths(objArr, objArr[0].length);
                return new ParseNode(new ArrayPtg(objArr));
            }
            if (c != ';') {
                throw expected("'}' or ';'");
            }
            Match(';');
        }
    }

    private void checkRowLengths(Object[][] objArr, int i) {
        for (int i2 = 0; i2 < objArr.length; i2++) {
            int length = objArr[i2].length;
            if (length != i) {
                throw new FormulaParseException("Array row " + i2 + " has length " + length + " but row 0 has length " + i);
            }
        }
    }

    private Object[] parseArrayRow() {
        char c;
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(parseArrayItem());
            SkipWhite();
            c = this.look;
            if (c != ',') {
                break;
            }
            Match(',');
        }
        if (c != ';' && c != '}') {
            throw expected("'}' or ','");
        }
        Object[] objArr = new Object[arrayList.size()];
        arrayList.toArray(objArr);
        return objArr;
    }

    private Object parseArrayItem() {
        SkipWhite();
        char c = this.look;
        if (c == '\"') {
            return parseStringLiteral();
        }
        if (c == '#') {
            return ErrorConstant.valueOf(parseErrorLiteral());
        }
        if (c == '-') {
            Match(Soundex.SILENT_MARKER);
            SkipWhite();
            return convertArrayNumber(parseNumber(), false);
        }
        if (c == 'F' || c == 'T' || c == 'f' || c == 't') {
            return parseBooleanLiteral();
        }
        return convertArrayNumber(parseNumber(), true);
    }

    private Boolean parseBooleanLiteral() {
        String unquotedIdentifier = parseUnquotedIdentifier();
        if ("TRUE".equalsIgnoreCase(unquotedIdentifier)) {
            return Boolean.TRUE;
        }
        if ("FALSE".equalsIgnoreCase(unquotedIdentifier)) {
            return Boolean.FALSE;
        }
        throw expected("'TRUE' or 'FALSE'");
    }

    private static Double convertArrayNumber(Ptg ptg, boolean z) {
        double value;
        if (ptg instanceof IntPtg) {
            value = ((IntPtg) ptg).getValue();
        } else if (ptg instanceof NumberPtg) {
            value = ((NumberPtg) ptg).getValue();
        } else {
            throw new RuntimeException("Unexpected ptg (" + ptg.getClass().getName() + ")");
        }
        if (!z) {
            value = -value;
        }
        return new Double(value);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.apache.poi.hssf.record.formula.Ptg parseNumber() {
        /*
            r6 = this;
            java.lang.String r0 = r6.GetNum()
            char r1 = r6.look
            r2 = 0
            r3 = 46
            if (r1 != r3) goto L13
            r6.GetChar()
            java.lang.String r1 = r6.GetNum()
            goto L14
        L13:
            r1 = r2
        L14:
            char r3 = r6.look
            r4 = 69
            java.lang.String r5 = "Integer"
            if (r3 != r4) goto L50
            r6.GetChar()
            char r2 = r6.look
            r3 = 43
            if (r2 != r3) goto L29
            r6.GetChar()
            goto L33
        L29:
            r3 = 45
            if (r2 != r3) goto L33
            r6.GetChar()
            java.lang.String r2 = "-"
            goto L35
        L33:
            java.lang.String r2 = ""
        L35:
            java.lang.String r3 = r6.GetNum()
            if (r3 == 0) goto L4b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            r4.append(r3)
            java.lang.String r2 = r4.toString()
            goto L50
        L4b:
            java.lang.RuntimeException r0 = r6.expected(r5)
            throw r0
        L50:
            if (r0 != 0) goto L5a
            if (r1 == 0) goto L55
            goto L5a
        L55:
            java.lang.RuntimeException r0 = r6.expected(r5)
            throw r0
        L5a:
            org.apache.poi.hssf.record.formula.Ptg r0 = getNumberPtgFromString(r0, r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.formula.FormulaParser.parseNumber():org.apache.poi.hssf.record.formula.Ptg");
    }

    private int parseErrorLiteral() {
        Match('#');
        String upperCase = parseUnquotedIdentifier().toUpperCase();
        if (upperCase == null) {
            throw expected("remainder of error constant literal");
        }
        char cCharAt = upperCase.charAt(0);
        if (cCharAt == 'D') {
            if (upperCase.equals("DIV")) {
                Match(IOUtils.DIR_SEPARATOR_UNIX);
                Match('0');
                Match('!');
                return 7;
            }
            throw expected("#DIV/0!");
        }
        if (cCharAt != 'N') {
            if (cCharAt == 'R') {
                if (upperCase.equals(ShareConstants.REF)) {
                    Match('!');
                    return 23;
                }
                throw expected("#REF!");
            }
            if (cCharAt == 'V') {
                if (upperCase.equals(Parameter.VALUE)) {
                    Match('!');
                    return 15;
                }
                throw expected("#VALUE!");
            }
            throw expected("#VALUE!, #REF!, #DIV/0!, #NAME?, #NUM!, #NULL! or #N/A");
        }
        if (upperCase.equals(Property.NAME)) {
            Match('?');
            return 29;
        }
        if (upperCase.equals("NUM")) {
            Match('!');
            return 36;
        }
        if (upperCase.equals("NULL")) {
            Match('!');
            return 0;
        }
        if (upperCase.equals("N")) {
            Match(IOUtils.DIR_SEPARATOR_UNIX);
            char c = this.look;
            if (c != 'A' && c != 'a') {
                throw expected("#N/A");
            }
            Match(this.look);
            return 42;
        }
        throw expected("#NAME?, #NUM!, #NULL! or #N/A");
    }

    private String parseUnquotedIdentifier() {
        if (this.look == '\'') {
            throw expected("unquoted identifier");
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!Character.isLetterOrDigit(this.look) && this.look != '.') {
                break;
            }
            sb.append(this.look);
            GetChar();
        }
        if (sb.length() < 1) {
            return null;
        }
        return sb.toString();
    }

    private static Ptg getNumberPtgFromString(String str, String str2, String str3) throws NumberFormatException {
        StringBuffer stringBuffer = new StringBuffer();
        if (str2 == null) {
            stringBuffer.append(str);
            if (str3 != null) {
                stringBuffer.append('E');
                stringBuffer.append(str3);
            }
            String string = stringBuffer.toString();
            try {
                int i = Integer.parseInt(string);
                if (IntPtg.isInRange(i)) {
                    return new IntPtg(i);
                }
                return new NumberPtg(string);
            } catch (NumberFormatException unused) {
                return new NumberPtg(string);
            }
        }
        if (str != null) {
            stringBuffer.append(str);
        }
        stringBuffer.append('.');
        stringBuffer.append(str2);
        if (str3 != null) {
            stringBuffer.append('E');
            stringBuffer.append(str3);
        }
        return new NumberPtg(stringBuffer.toString());
    }

    private String parseStringLiteral() {
        Match('\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (this.look == '\"') {
                GetChar();
                if (this.look != '\"') {
                    return stringBuffer.toString();
                }
            }
            stringBuffer.append(this.look);
            GetChar();
        }
    }

    private ParseNode Term() {
        ValueOperatorPtg valueOperatorPtg;
        ParseNode parseNodePowerFactor = powerFactor();
        while (true) {
            SkipWhite();
            char c = this.look;
            if (c == '*') {
                Match('*');
                valueOperatorPtg = MultiplyPtg.instance;
            } else {
                if (c != '/') {
                    return parseNodePowerFactor;
                }
                Match(IOUtils.DIR_SEPARATOR_UNIX);
                valueOperatorPtg = DividePtg.instance;
            }
            parseNodePowerFactor = new ParseNode(valueOperatorPtg, parseNodePowerFactor, powerFactor());
        }
    }

    private ParseNode unionExpression() {
        ParseNode parseNodeComparisonExpression = comparisonExpression();
        boolean z = false;
        while (true) {
            SkipWhite();
            if (this.look != ',') {
                break;
            }
            GetChar();
            z = true;
            parseNodeComparisonExpression = new ParseNode(UnionPtg.instance, parseNodeComparisonExpression, comparisonExpression());
        }
        return z ? augmentWithMemPtg(parseNodeComparisonExpression) : parseNodeComparisonExpression;
    }

    private ParseNode comparisonExpression() {
        ParseNode parseNodeConcatExpression = concatExpression();
        while (true) {
            SkipWhite();
            switch (this.look) {
                case '<':
                case '=':
                case '>':
                    parseNodeConcatExpression = new ParseNode(getComparisonToken(), parseNodeConcatExpression, concatExpression());
                default:
                    return parseNodeConcatExpression;
            }
        }
    }

    private Ptg getComparisonToken() {
        char c = this.look;
        if (c == '=') {
            Match(c);
            return EqualPtg.instance;
        }
        boolean z = c == '>';
        Match(this.look);
        if (z) {
            if (this.look == '=') {
                Match('=');
                return GreaterEqualPtg.instance;
            }
            return GreaterThanPtg.instance;
        }
        char c2 = this.look;
        if (c2 == '=') {
            Match('=');
            return LessEqualPtg.instance;
        }
        if (c2 == '>') {
            Match(Typography.greater);
            return NotEqualPtg.instance;
        }
        return LessThanPtg.instance;
    }

    private ParseNode concatExpression() {
        ParseNode parseNodeAdditiveExpression = additiveExpression();
        while (true) {
            SkipWhite();
            if (this.look != '&') {
                return parseNodeAdditiveExpression;
            }
            Match(Typography.amp);
            parseNodeAdditiveExpression = new ParseNode(ConcatPtg.instance, parseNodeAdditiveExpression, additiveExpression());
        }
    }

    private ParseNode additiveExpression() {
        ValueOperatorPtg valueOperatorPtg;
        ParseNode parseNodeTerm = Term();
        while (true) {
            SkipWhite();
            char c = this.look;
            if (c == '+') {
                Match('+');
                valueOperatorPtg = AddPtg.instance;
            } else {
                if (c != '-') {
                    return parseNodeTerm;
                }
                Match(Soundex.SILENT_MARKER);
                valueOperatorPtg = SubtractPtg.instance;
            }
            parseNodeTerm = new ParseNode(valueOperatorPtg, parseNodeTerm, Term());
        }
    }

    private void parse() {
        this._pointer = 0;
        GetChar();
        this._rootNode = unionExpression();
        if (this._pointer > this._formulaLength) {
            return;
        }
        throw new FormulaParseException("Unused input [" + this._formulaString.substring(this._pointer - 1) + "] after attempting to parse the formula [" + this._formulaString + "]");
    }

    private Ptg[] getRPNPtg(int i) {
        new OperandClassTransformer(i).transformFormula(this._rootNode);
        return ParseNode.toTokenArray(this._rootNode);
    }
}
