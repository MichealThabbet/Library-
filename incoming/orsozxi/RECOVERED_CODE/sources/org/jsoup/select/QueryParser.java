package org.jsoup.select;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.message.TokenParser;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jsoup.select.StructuralEvaluator;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class QueryParser {
    private List<Evaluator> evals = new ArrayList();
    private String query;
    private TokenQueue tq;
    private static final String[] combinators = {ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, ">", Marker.ANY_NON_NULL_MARKER, "~", " "};
    private static final String[] AttributeEvals = {ContainerUtils.KEY_VALUE_DELIMITER, "!=", "^=", "$=", "*=", "~="};
    private static final Pattern NTH_AB = Pattern.compile("(([+-])?(\\d+)?)n(\\s*([+-])?\\s*\\d+)?", 2);
    private static final Pattern NTH_B = Pattern.compile("([+-])?(\\d+)");

    private QueryParser(String str) {
        Validate.notEmpty(str);
        String strTrim = str.trim();
        this.query = strTrim;
        this.tq = new TokenQueue(strTrim);
    }

    public static Evaluator parse(String str) {
        try {
            return new QueryParser(str).parse();
        } catch (IllegalArgumentException e) {
            throw new Selector.SelectorParseException(e.getMessage(), new Object[0]);
        }
    }

    Evaluator parse() throws NumberFormatException {
        this.tq.consumeWhitespace();
        if (this.tq.matchesAny(combinators)) {
            this.evals.add(new StructuralEvaluator.Root());
            combinator(this.tq.consume());
        } else {
            findElements();
        }
        while (!this.tq.isEmpty()) {
            boolean zConsumeWhitespace = this.tq.consumeWhitespace();
            if (this.tq.matchesAny(combinators)) {
                combinator(this.tq.consume());
            } else if (zConsumeWhitespace) {
                combinator(TokenParser.SP);
            } else {
                findElements();
            }
        }
        if (this.evals.size() == 1) {
            return this.evals.get(0);
        }
        return new CombiningEvaluator.And(this.evals);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void combinator(char r11) {
        /*
            Method dump skipped, instructions count: 215
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.select.QueryParser.combinator(char):void");
    }

    private String consumeSubQuery() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        while (!this.tq.isEmpty()) {
            if (this.tq.matches("(")) {
                sbBorrowBuilder.append("(");
                sbBorrowBuilder.append(this.tq.chompBalanced('(', ')'));
                sbBorrowBuilder.append(")");
            } else if (this.tq.matches("[")) {
                sbBorrowBuilder.append("[");
                sbBorrowBuilder.append(this.tq.chompBalanced('[', ']'));
                sbBorrowBuilder.append("]");
            } else {
                if (this.tq.matchesAny(combinators)) {
                    break;
                }
                sbBorrowBuilder.append(this.tq.consume());
            }
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    private void findElements() throws NumberFormatException {
        if (this.tq.matchChomp("#")) {
            byId();
            return;
        }
        if (this.tq.matchChomp(".")) {
            byClass();
            return;
        }
        if (this.tq.matchesWord() || this.tq.matches("*|")) {
            byTag();
            return;
        }
        if (this.tq.matches("[")) {
            byAttribute();
            return;
        }
        if (this.tq.matchChomp("*")) {
            allElements();
            return;
        }
        if (this.tq.matchChomp(":lt(")) {
            indexLessThan();
            return;
        }
        if (this.tq.matchChomp(":gt(")) {
            indexGreaterThan();
            return;
        }
        if (this.tq.matchChomp(":eq(")) {
            indexEquals();
            return;
        }
        if (this.tq.matches(":has(")) {
            has();
            return;
        }
        if (this.tq.matches(":contains(")) {
            contains(false);
            return;
        }
        if (this.tq.matches(":containsOwn(")) {
            contains(true);
            return;
        }
        if (this.tq.matches(":containsData(")) {
            containsData();
            return;
        }
        if (this.tq.matches(":matches(")) {
            matches(false);
            return;
        }
        if (this.tq.matches(":matchesOwn(")) {
            matches(true);
            return;
        }
        if (this.tq.matches(":not(")) {
            not();
            return;
        }
        if (this.tq.matchChomp(":nth-child(")) {
            cssNthChild(false, false);
            return;
        }
        if (this.tq.matchChomp(":nth-last-child(")) {
            cssNthChild(true, false);
            return;
        }
        if (this.tq.matchChomp(":nth-of-type(")) {
            cssNthChild(false, true);
            return;
        }
        if (this.tq.matchChomp(":nth-last-of-type(")) {
            cssNthChild(true, true);
            return;
        }
        if (this.tq.matchChomp(":first-child")) {
            this.evals.add(new Evaluator.IsFirstChild());
            return;
        }
        if (this.tq.matchChomp(":last-child")) {
            this.evals.add(new Evaluator.IsLastChild());
            return;
        }
        if (this.tq.matchChomp(":first-of-type")) {
            this.evals.add(new Evaluator.IsFirstOfType());
            return;
        }
        if (this.tq.matchChomp(":last-of-type")) {
            this.evals.add(new Evaluator.IsLastOfType());
            return;
        }
        if (this.tq.matchChomp(":only-child")) {
            this.evals.add(new Evaluator.IsOnlyChild());
            return;
        }
        if (this.tq.matchChomp(":only-of-type")) {
            this.evals.add(new Evaluator.IsOnlyOfType());
            return;
        }
        if (this.tq.matchChomp(":empty")) {
            this.evals.add(new Evaluator.IsEmpty());
        } else if (this.tq.matchChomp(":root")) {
            this.evals.add(new Evaluator.IsRoot());
        } else {
            if (this.tq.matchChomp(":matchText")) {
                this.evals.add(new Evaluator.MatchText());
                return;
            }
            throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
        }
    }

    private void byId() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        Validate.notEmpty(strConsumeCssIdentifier);
        this.evals.add(new Evaluator.Id(strConsumeCssIdentifier));
    }

    private void byClass() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        Validate.notEmpty(strConsumeCssIdentifier);
        this.evals.add(new Evaluator.Class(strConsumeCssIdentifier.trim()));
    }

    private void byTag() {
        String strNormalize = Normalizer.normalize(this.tq.consumeElementSelector());
        Validate.notEmpty(strNormalize);
        if (strNormalize.startsWith("*|")) {
            this.evals.add(new CombiningEvaluator.Or(new Evaluator.Tag(strNormalize), new Evaluator.TagEndsWith(strNormalize.replace("*|", ":"))));
            return;
        }
        if (strNormalize.contains(HiAnalyticsConstant.REPORT_VAL_SEPARATOR)) {
            strNormalize = strNormalize.replace(HiAnalyticsConstant.REPORT_VAL_SEPARATOR, ":");
        }
        this.evals.add(new Evaluator.Tag(strNormalize));
    }

    private void byAttribute() {
        TokenQueue tokenQueue = new TokenQueue(this.tq.chompBalanced('[', ']'));
        String strConsumeToAny = tokenQueue.consumeToAny(AttributeEvals);
        Validate.notEmpty(strConsumeToAny);
        tokenQueue.consumeWhitespace();
        if (tokenQueue.isEmpty()) {
            if (strConsumeToAny.startsWith("^")) {
                this.evals.add(new Evaluator.AttributeStarting(strConsumeToAny.substring(1)));
                return;
            } else {
                this.evals.add(new Evaluator.Attribute(strConsumeToAny));
                return;
            }
        }
        if (tokenQueue.matchChomp(ContainerUtils.KEY_VALUE_DELIMITER)) {
            this.evals.add(new Evaluator.AttributeWithValue(strConsumeToAny, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("!=")) {
            this.evals.add(new Evaluator.AttributeWithValueNot(strConsumeToAny, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("^=")) {
            this.evals.add(new Evaluator.AttributeWithValueStarting(strConsumeToAny, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("$=")) {
            this.evals.add(new Evaluator.AttributeWithValueEnding(strConsumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("*=")) {
            this.evals.add(new Evaluator.AttributeWithValueContaining(strConsumeToAny, tokenQueue.remainder()));
        } else {
            if (tokenQueue.matchChomp("~=")) {
                this.evals.add(new Evaluator.AttributeWithValueMatching(strConsumeToAny, Pattern.compile(tokenQueue.remainder())));
                return;
            }
            throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, tokenQueue.remainder());
        }
    }

    private void allElements() {
        this.evals.add(new Evaluator.AllElements());
    }

    private void indexLessThan() {
        this.evals.add(new Evaluator.IndexLessThan(consumeIndex()));
    }

    private void indexGreaterThan() {
        this.evals.add(new Evaluator.IndexGreaterThan(consumeIndex()));
    }

    private void indexEquals() {
        this.evals.add(new Evaluator.IndexEquals(consumeIndex()));
    }

    private void cssNthChild(boolean z, boolean z2) throws NumberFormatException {
        String strNormalize = Normalizer.normalize(this.tq.chompTo(")"));
        Matcher matcher = NTH_AB.matcher(strNormalize);
        Matcher matcher2 = NTH_B.matcher(strNormalize);
        int i = 2;
        if ("odd".equals(strNormalize)) {
            i = 1;
        } else if (!"even".equals(strNormalize)) {
            if (matcher.matches()) {
                int i2 = matcher.group(3) != null ? Integer.parseInt(matcher.group(1).replaceFirst("^\\+", "")) : 1;
                i = matcher.group(4) != null ? Integer.parseInt(matcher.group(4).replaceFirst("^\\+", "")) : 0;
                i = i2;
            } else if (matcher2.matches()) {
                i = Integer.parseInt(matcher2.group().replaceFirst("^\\+", ""));
                i = 0;
            } else {
                throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", strNormalize);
            }
        }
        if (z2) {
            if (z) {
                this.evals.add(new Evaluator.IsNthLastOfType(i, i));
                return;
            } else {
                this.evals.add(new Evaluator.IsNthOfType(i, i));
                return;
            }
        }
        if (z) {
            this.evals.add(new Evaluator.IsNthLastChild(i, i));
        } else {
            this.evals.add(new Evaluator.IsNthChild(i, i));
        }
    }

    private int consumeIndex() {
        String strTrim = this.tq.chompTo(")").trim();
        Validate.isTrue(StringUtil.isNumeric(strTrim), "Index must be numeric");
        return Integer.parseInt(strTrim);
    }

    private void has() {
        this.tq.consume(":has");
        String strChompBalanced = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(strChompBalanced, ":has(el) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Has(parse(strChompBalanced)));
    }

    private void contains(boolean z) {
        this.tq.consume(z ? ":containsOwn" : ":contains");
        String strUnescape = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
        Validate.notEmpty(strUnescape, ":contains(text) query must not be empty");
        if (z) {
            this.evals.add(new Evaluator.ContainsOwnText(strUnescape));
        } else {
            this.evals.add(new Evaluator.ContainsText(strUnescape));
        }
    }

    private void containsData() {
        this.tq.consume(":containsData");
        String strUnescape = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
        Validate.notEmpty(strUnescape, ":containsData(text) query must not be empty");
        this.evals.add(new Evaluator.ContainsData(strUnescape));
    }

    private void matches(boolean z) {
        this.tq.consume(z ? ":matchesOwn" : ":matches");
        String strChompBalanced = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(strChompBalanced, ":matches(regex) query must not be empty");
        if (z) {
            this.evals.add(new Evaluator.MatchesOwn(Pattern.compile(strChompBalanced)));
        } else {
            this.evals.add(new Evaluator.Matches(Pattern.compile(strChompBalanced)));
        }
    }

    private void not() {
        this.tq.consume(":not");
        String strChompBalanced = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(strChompBalanced, ":not(selector) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Not(parse(strChompBalanced)));
    }
}
