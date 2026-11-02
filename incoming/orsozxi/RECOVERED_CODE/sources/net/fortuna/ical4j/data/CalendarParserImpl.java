package net.fortuna.ical4j.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class CalendarParserImpl implements CalendarParser {
    private static final int IGNORE_BEGINNING_NON_WORD_COUNT = 10;
    private static final String UNEXPECTED_TOKEN_MESSAGE = "Expected [{0}], read [{1}]";
    private static final int WHITESPACE_CHAR_END = 20;
    private static final int WHITESPACE_CHAR_START = 0;
    private static final int WORD_CHAR_END = 255;
    private static final int WORD_CHAR_START = 32;
    private final ComponentListParser componentListParser;
    private final ComponentParser componentParser;
    private Logger log = LoggerFactory.getLogger((Class<?>) CalendarParserImpl.class);
    private final ParameterListParser paramListParser;
    private final ParameterParser paramParser;
    private final PropertyListParser propertyListParser;
    private final PropertyParser propertyParser;

    public CalendarParserImpl() {
        this.componentListParser = new ComponentListParser();
        this.componentParser = new ComponentParser();
        this.propertyListParser = new PropertyListParser();
        this.propertyParser = new PropertyParser();
        this.paramListParser = new ParameterListParser();
        this.paramParser = new ParameterParser();
    }

    @Override // net.fortuna.ical4j.data.CalendarParser
    public final void parse(InputStream inputStream, ContentHandler contentHandler) throws ParserException, IOException {
        parse(new InputStreamReader(inputStream), contentHandler);
    }

    private void parseCalendar(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException, ParseException {
        assertToken(streamTokenizer, reader, 58);
        assertToken(streamTokenizer, reader, Calendar.VCALENDAR, true, false);
        assertToken(streamTokenizer, reader, 10);
        contentHandler.startCalendar();
        this.propertyListParser.parse(streamTokenizer, reader, contentHandler);
        this.componentListParser.parse(streamTokenizer, reader, contentHandler);
        assertToken(streamTokenizer, reader, 58);
        assertToken(streamTokenizer, reader, Calendar.VCALENDAR, true, false);
        contentHandler.endCalendar();
    }

    @Override // net.fortuna.ical4j.data.CalendarParser
    public final void parse(Reader reader, ContentHandler contentHandler) throws ParserException, IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        try {
            streamTokenizer.resetSyntax();
            streamTokenizer.wordChars(32, 255);
            streamTokenizer.whitespaceChars(0, 20);
            streamTokenizer.ordinaryChar(58);
            streamTokenizer.ordinaryChar(59);
            streamTokenizer.ordinaryChar(61);
            streamTokenizer.ordinaryChar(9);
            streamTokenizer.eolIsSignificant(true);
            streamTokenizer.whitespaceChars(0, 0);
            streamTokenizer.quoteChar(34);
            parseCalendarList(streamTokenizer, reader, contentHandler);
        } catch (IOException | RuntimeException | URISyntaxException | ParseException e) {
            if (e instanceof IOException) {
                throw ((IOException) e);
            }
            if (e instanceof ParserException) {
                throw ((ParserException) e);
            }
            throw new ParserException(e.getMessage(), getLineNumber(streamTokenizer, reader), e);
        }
    }

    private void parseCalendarList(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException, ParseException {
        int iAssertToken = assertToken(streamTokenizer, reader, "BEGIN", false, true);
        while (iAssertToken != -1) {
            parseCalendar(streamTokenizer, reader, contentHandler);
            iAssertToken = absorbWhitespace(streamTokenizer, reader, true);
        }
    }

    private class PropertyListParser {
        private PropertyListParser() {
        }

        public void parse(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException, ParseException {
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, -3);
            while (!"END".equals(streamTokenizer.sval)) {
                if (!"BEGIN".equals(streamTokenizer.sval)) {
                    if (streamTokenizer.sval == null) {
                        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                            throw new ParserException("Invalid property name", CalendarParserImpl.this.getLineNumber(streamTokenizer, reader));
                        }
                    } else {
                        CalendarParserImpl.this.propertyParser.parse(streamTokenizer, reader, contentHandler);
                    }
                } else {
                    CalendarParserImpl.this.componentParser.parse(streamTokenizer, reader, contentHandler);
                }
                CalendarParserImpl.this.absorbWhitespace(streamTokenizer, reader, false);
            }
        }
    }

    private class PropertyParser {
        private static final String PARSE_DEBUG_MESSAGE = "Property [{0}]";
        private static final String PARSE_EXCEPTION_MESSAGE = "Property [{0}]";

        private PropertyParser() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parse(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException, ParseException {
            String str = streamTokenizer.sval;
            if (CalendarParserImpl.this.log.isDebugEnabled()) {
                CalendarParserImpl.this.log.debug(MessageFormat.format("Property [{0}]", str));
            }
            contentHandler.startProperty(str);
            CalendarParserImpl.this.paramListParser.parse(streamTokenizer, reader, contentHandler);
            StringBuilder sb = new StringBuilder();
            streamTokenizer.ordinaryChar(34);
            int iNextToken = CalendarParserImpl.this.nextToken(streamTokenizer, reader);
            while (iNextToken != 10) {
                if (streamTokenizer.ttype == -3) {
                    sb.append(streamTokenizer.sval);
                } else {
                    sb.append((char) streamTokenizer.ttype);
                }
                iNextToken = CalendarParserImpl.this.nextToken(streamTokenizer, reader);
            }
            streamTokenizer.quoteChar(34);
            try {
                contentHandler.propertyValue(sb.toString());
                contentHandler.endProperty(str);
            } catch (ParseException e) {
                ParseException parseException = new ParseException("[" + str + "] " + e.getMessage(), e.getErrorOffset());
                parseException.initCause(e);
                throw parseException;
            }
        }
    }

    private class ParameterListParser {
        private ParameterListParser() {
        }

        public void parse(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException {
            while (CalendarParserImpl.this.nextToken(streamTokenizer, reader) == 59) {
                CalendarParserImpl.this.paramParser.parse(streamTokenizer, reader, contentHandler);
            }
        }
    }

    private class ParameterParser {
        private ParameterParser() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parse(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException {
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, -3);
            String str = streamTokenizer.sval;
            if (CalendarParserImpl.this.log.isDebugEnabled()) {
                CalendarParserImpl.this.log.debug("Parameter [" + str + "]");
            }
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, 61);
            StringBuilder sb = new StringBuilder();
            if (CalendarParserImpl.this.nextToken(streamTokenizer, reader) == 34) {
                sb.append('\"');
                sb.append(streamTokenizer.sval);
                sb.append('\"');
            } else if (streamTokenizer.sval != null) {
                sb.append(streamTokenizer.sval);
                int iNextToken = CalendarParserImpl.this.nextToken(streamTokenizer, reader);
                while (iNextToken != 59 && iNextToken != 58 && iNextToken != 44) {
                    if (streamTokenizer.ttype == -3) {
                        sb.append(streamTokenizer.sval);
                    } else {
                        sb.append((char) streamTokenizer.ttype);
                    }
                    iNextToken = CalendarParserImpl.this.nextToken(streamTokenizer, reader);
                }
                streamTokenizer.pushBack();
            } else if (streamTokenizer.sval == null) {
                streamTokenizer.pushBack();
            }
            try {
                contentHandler.parameter(str, sb.toString());
            } catch (ClassCastException e) {
                throw new ParserException("Error parsing parameter", CalendarParserImpl.this.getLineNumber(streamTokenizer, reader), e);
            }
        }
    }

    private class ComponentListParser {
        private ComponentListParser() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parse(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException, ParseException {
            while ("BEGIN".equals(streamTokenizer.sval)) {
                CalendarParserImpl.this.componentParser.parse(streamTokenizer, reader, contentHandler);
                CalendarParserImpl.this.absorbWhitespace(streamTokenizer, reader, false);
            }
        }
    }

    private class ComponentParser {
        private ComponentParser() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parse(StreamTokenizer streamTokenizer, Reader reader, ContentHandler contentHandler) throws ParserException, URISyntaxException, IOException, ParseException {
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, 58);
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, -3);
            String str = streamTokenizer.sval;
            contentHandler.startComponent(str);
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, 10);
            CalendarParserImpl.this.propertyListParser.parse(streamTokenizer, reader, contentHandler);
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, 58);
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, str);
            CalendarParserImpl.this.assertToken(streamTokenizer, reader, 10);
            contentHandler.endComponent(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int assertToken(StreamTokenizer streamTokenizer, Reader reader, int i) throws ParserException, IOException {
        int iNextToken = nextToken(streamTokenizer, reader);
        if (iNextToken != i) {
            throw new ParserException(MessageFormat.format(UNEXPECTED_TOKEN_MESSAGE, Integer.valueOf(i), Integer.valueOf(streamTokenizer.ttype)), getLineNumber(streamTokenizer, reader));
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("[" + i + "]");
        }
        return iNextToken;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int assertToken(StreamTokenizer streamTokenizer, Reader reader, String str) throws ParserException, IOException {
        return assertToken(streamTokenizer, reader, str, false, false);
    }

    private int assertToken(StreamTokenizer streamTokenizer, Reader reader, String str, boolean z, boolean z2) throws ParserException, IOException {
        int iAssertToken;
        String svalIgnoringBom;
        if (z2) {
            iAssertToken = skipNewLines(streamTokenizer, reader, str);
            svalIgnoringBom = getSvalIgnoringBom(streamTokenizer, reader, str);
        } else {
            iAssertToken = assertToken(streamTokenizer, reader, -3);
            svalIgnoringBom = streamTokenizer.sval;
        }
        if (z) {
            if (!str.equalsIgnoreCase(svalIgnoringBom)) {
                throw new ParserException(MessageFormat.format(UNEXPECTED_TOKEN_MESSAGE, str, svalIgnoringBom), getLineNumber(streamTokenizer, reader));
            }
        } else if (!str.equals(svalIgnoringBom)) {
            throw new ParserException(MessageFormat.format(UNEXPECTED_TOKEN_MESSAGE, str, svalIgnoringBom), getLineNumber(streamTokenizer, reader));
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("[" + str + "]");
        }
        return iAssertToken;
    }

    private int skipNewLines(StreamTokenizer streamTokenizer, Reader reader, String str) throws ParserException, IOException {
        int i = 0;
        while (true) {
            try {
                return assertToken(streamTokenizer, reader, -3);
            } catch (ParserException e) {
                if (i == 10) {
                    throw e;
                }
                i++;
            }
        }
    }

    private String getSvalIgnoringBom(StreamTokenizer streamTokenizer, Reader reader, String str) {
        return (streamTokenizer.sval == null || !streamTokenizer.sval.contains(str)) ? streamTokenizer.sval : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int absorbWhitespace(StreamTokenizer streamTokenizer, Reader reader, boolean z) throws ParserException, IOException {
        int iNextToken;
        while (true) {
            iNextToken = nextToken(streamTokenizer, reader, z);
            if (iNextToken != 10) {
                break;
            }
            if (this.log.isTraceEnabled()) {
                this.log.trace("Absorbing extra whitespace..");
            }
        }
        if (this.log.isTraceEnabled()) {
            this.log.trace("Aborting: absorbing extra whitespace complete");
        }
        return iNextToken;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLineNumber(StreamTokenizer streamTokenizer, Reader reader) {
        int iLineno = streamTokenizer.lineno();
        if (streamTokenizer.ttype == 10) {
            iLineno--;
        }
        return reader instanceof UnfoldingReader ? iLineno + ((UnfoldingReader) reader).getLinesUnfolded() : iLineno;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int nextToken(StreamTokenizer streamTokenizer, Reader reader) throws ParserException, IOException {
        return nextToken(streamTokenizer, reader, false);
    }

    private int nextToken(StreamTokenizer streamTokenizer, Reader reader, boolean z) throws ParserException, IOException {
        int iNextToken = streamTokenizer.nextToken();
        if (z || iNextToken != -1) {
            return iNextToken;
        }
        throw new ParserException("Unexpected end of file", getLineNumber(streamTokenizer, reader));
    }
}
