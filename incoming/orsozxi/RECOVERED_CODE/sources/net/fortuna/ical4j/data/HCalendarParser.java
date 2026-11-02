package net.fortuna.ical4j.data;

import com.facebook.share.internal.ShareConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/* loaded from: classes3.dex */
public class HCalendarParser implements CalendarParser {
    private static final XPathExpression XPATH_ATTACH;
    private static final XPathExpression XPATH_ATTENDEE;
    private static final XPathExpression XPATH_CATEGORY;
    private static final XPathExpression XPATH_CLASS;
    private static final XPathExpression XPATH_CONTACT;
    private static final XPathExpression XPATH_DESCRIPTION;
    private static final XPathExpression XPATH_DTEND;
    private static final XPathExpression XPATH_DTSTAMP;
    private static final XPathExpression XPATH_DTSTART;
    private static final XPathExpression XPATH_DURATION;
    private static final XPathExpression XPATH_LAST_MODIFIED;
    private static final XPathExpression XPATH_LOCATION;
    private static final XPathExpression XPATH_METHOD;
    private static final XPathExpression XPATH_ORGANIZER;
    private static final XPathExpression XPATH_SEQUENCE;
    private static final XPathExpression XPATH_STATUS;
    private static final XPathExpression XPATH_SUMMARY;
    private static final XPathExpression XPATH_UID;
    private static final XPathExpression XPATH_URL;
    private static final XPathExpression XPATH_VEVENTS;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) HCalendarParser.class);
    private static final DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private static final XPath XPATH = XPathFactory.newInstance().newXPath();
    private static final String HCAL_DATE_PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat HCAL_DATE_FORMAT = new SimpleDateFormat(HCAL_DATE_PATTERN);
    private static final String HCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssz";
    private static final SimpleDateFormat HCAL_DATE_TIME_FORMAT = new SimpleDateFormat(HCAL_DATE_TIME_PATTERN);

    static {
        BUILDER_FACTORY.setNamespaceAware(true);
        BUILDER_FACTORY.setIgnoringComments(true);
        XPATH_METHOD = compileExpression("//*[contains(@class, 'method')]");
        XPATH_VEVENTS = compileExpression("//*[contains(@class, 'vevent')]");
        XPATH_DTSTART = compileExpression(".//*[contains(@class, 'dtstart')]");
        XPATH_DTEND = compileExpression(".//*[contains(@class, 'dtend')]");
        XPATH_DURATION = compileExpression(".//*[contains(@class, 'duration')]");
        XPATH_SUMMARY = compileExpression(".//*[contains(@class, 'summary')]");
        XPATH_UID = compileExpression(".//*[contains(@class, 'uid')]");
        XPATH_DTSTAMP = compileExpression(".//*[contains(@class, 'dtstamp')]");
        XPATH_CATEGORY = compileExpression(".//*[contains(@class, 'category')]");
        XPATH_LOCATION = compileExpression(".//*[contains(@class, 'location')]");
        XPATH_URL = compileExpression(".//*[contains(@class, 'url')]");
        XPATH_DESCRIPTION = compileExpression(".//*[contains(@class, 'description')]");
        XPATH_LAST_MODIFIED = compileExpression(".//*[contains(@class, 'last-modified')]");
        XPATH_STATUS = compileExpression(".//*[contains(@class, 'status')]");
        XPATH_CLASS = compileExpression(".//*[contains(@class, 'class')]");
        XPATH_ATTENDEE = compileExpression(".//*[contains(@class, 'attendee')]");
        XPATH_CONTACT = compileExpression(".//*[contains(@class, 'contact')]");
        XPATH_ORGANIZER = compileExpression(".//*[contains(@class, 'organizer')]");
        XPATH_SEQUENCE = compileExpression(".//*[contains(@class, 'sequence')]");
        XPATH_ATTACH = compileExpression(".//*[contains(@class, 'attach')]");
    }

    private static XPathExpression compileExpression(String str) {
        try {
            return XPATH.compile(str);
        } catch (XPathException e) {
            throw new CalendarException(e);
        }
    }

    @Override // net.fortuna.ical4j.data.CalendarParser
    public void parse(InputStream inputStream, ContentHandler contentHandler) throws ParserException, DOMException, IOException {
        parse(new InputSource(inputStream), contentHandler);
    }

    @Override // net.fortuna.ical4j.data.CalendarParser
    public void parse(Reader reader, ContentHandler contentHandler) throws ParserException, DOMException, IOException {
        parse(new InputSource(reader), contentHandler);
    }

    private void parse(InputSource inputSource, ContentHandler contentHandler) throws ParserException, DOMException, IOException {
        try {
            buildCalendar(BUILDER_FACTORY.newDocumentBuilder().parse(inputSource), contentHandler);
        } catch (ParserConfigurationException e) {
            throw new CalendarException(e);
        } catch (SAXException e2) {
            if (e2 instanceof SAXParseException) {
                throw new ParserException("Could not parse XML", ((SAXParseException) e2).getLineNumber(), e2);
            }
            throw new ParserException(e2.getMessage(), -1, e2);
        }
    }

    private static NodeList findNodes(XPathExpression xPathExpression, Object obj) throws ParserException {
        try {
            return (NodeList) xPathExpression.evaluate(obj, XPathConstants.NODESET);
        } catch (XPathException e) {
            throw new ParserException("Unable to find nodes", -1, e);
        }
    }

    private static Node findNode(XPathExpression xPathExpression, Object obj) throws ParserException {
        try {
            return (Node) xPathExpression.evaluate(obj, XPathConstants.NODE);
        } catch (XPathException e) {
            throw new ParserException("Unable to find node", -1, e);
        }
    }

    private static List<Element> findElements(XPathExpression xPathExpression, Object obj) throws ParserException {
        NodeList nodeListFindNodes = findNodes(xPathExpression, obj);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < nodeListFindNodes.getLength(); i++) {
            Node nodeItem = nodeListFindNodes.item(i);
            if (nodeItem instanceof Element) {
                arrayList.add((Element) nodeItem);
            }
        }
        return arrayList;
    }

    private static Element findElement(XPathExpression xPathExpression, Object obj) throws ParserException {
        Node nodeFindNode = findNode(xPathExpression, obj);
        if (nodeFindNode instanceof Element) {
            return (Element) nodeFindNode;
        }
        return null;
    }

    private static String getTextContent(Element element) throws DOMException, ParserException {
        try {
            String nodeValue = element.getFirstChild().getNodeValue();
            if (nodeValue != null) {
                return nodeValue.trim().replaceAll("\\s+", " ");
            }
            return null;
        } catch (DOMException e) {
            throw new ParserException("Unable to get text content for element " + element.getNodeName(), -1, e);
        }
    }

    private void buildCalendar(Document document, ContentHandler contentHandler) throws ParserException, DOMException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Building calendar");
        }
        contentHandler.startCalendar();
        contentHandler.startProperty(Property.VERSION);
        try {
            contentHandler.propertyValue(Version.VERSION_2_0.getValue());
            contentHandler.endProperty(Property.VERSION);
        } catch (IOException | URISyntaxException | ParseException e) {
            LOG.warn("Caught exception", e);
        }
        Element elementFindElement = findElement(XPATH_METHOD, document);
        if (elementFindElement != null) {
            buildProperty(elementFindElement, Property.METHOD, contentHandler);
        }
        Iterator<Element> it2 = findElements(XPATH_VEVENTS, document).iterator();
        while (it2.hasNext()) {
            buildEvent(it2.next(), contentHandler);
        }
        contentHandler.endCalendar();
    }

    private void buildEvent(Element element, ContentHandler contentHandler) throws DOMException, ParserException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Building event");
        }
        contentHandler.startComponent(Component.VEVENT);
        buildProperty(findElement(XPATH_DTSTART, element), Property.DTSTART, contentHandler);
        buildProperty(findElement(XPATH_DTEND, element), Property.DTEND, contentHandler);
        buildProperty(findElement(XPATH_DURATION, element), Property.DURATION, contentHandler);
        buildProperty(findElement(XPATH_SUMMARY, element), Property.SUMMARY, contentHandler);
        buildProperty(findElement(XPATH_UID, element), Property.UID, contentHandler);
        buildProperty(findElement(XPATH_DTSTAMP, element), Property.DTSTAMP, contentHandler);
        Iterator<Element> it2 = findElements(XPATH_CATEGORY, element).iterator();
        while (it2.hasNext()) {
            buildProperty(it2.next(), Property.CATEGORIES, contentHandler);
        }
        buildProperty(findElement(XPATH_LOCATION, element), "LOCATION", contentHandler);
        buildProperty(findElement(XPATH_URL, element), Property.URL, contentHandler);
        buildProperty(findElement(XPATH_DESCRIPTION, element), "DESCRIPTION", contentHandler);
        buildProperty(findElement(XPATH_LAST_MODIFIED, element), Property.LAST_MODIFIED, contentHandler);
        buildProperty(findElement(XPATH_STATUS, element), Property.STATUS, contentHandler);
        buildProperty(findElement(XPATH_CLASS, element), Property.CLASS, contentHandler);
        Iterator<Element> it3 = findElements(XPATH_ATTENDEE, element).iterator();
        while (it3.hasNext()) {
            buildProperty(it3.next(), Property.ATTENDEE, contentHandler);
        }
        buildProperty(findElement(XPATH_CONTACT, element), Property.CONTACT, contentHandler);
        buildProperty(findElement(XPATH_ORGANIZER, element), Property.ORGANIZER, contentHandler);
        buildProperty(findElement(XPATH_SEQUENCE, element), Property.SEQUENCE, contentHandler);
        buildProperty(findElement(XPATH_ATTACH, element), Property.ATTACH, contentHandler);
        contentHandler.endComponent(Component.VEVENT);
    }

    private void buildProperty(Element element, String str, ContentHandler contentHandler) throws DOMException, ParserException {
        String textContent;
        if (element == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Building property " + str);
        }
        String strClassName = className(str);
        String lowerCase = element.getLocalName().toLowerCase();
        if (lowerCase.equals("abbr")) {
            textContent = element.getAttribute("title");
            if (StringUtils.isBlank(textContent)) {
                throw new ParserException("Abbr element '" + strClassName + "' requires a non-empty title", -1);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting value '" + textContent + "' from title attribute");
            }
        } else if (isHeaderElement(lowerCase)) {
            textContent = element.getAttribute("title");
            if (!StringUtils.isBlank(textContent)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Setting value '" + textContent + "' from title attribute");
                }
            } else {
                textContent = getTextContent(element);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Setting value '" + textContent + "' from text content");
                }
            }
        } else if (lowerCase.equals("a") && isUrlProperty(str)) {
            textContent = element.getAttribute(ShareConstants.WEB_DIALOG_PARAM_HREF);
            if (StringUtils.isBlank(textContent)) {
                throw new ParserException("A element '" + strClassName + "' requires a non-empty href", -1);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting value '" + textContent + "' from href attribute");
            }
        } else if (lowerCase.equals("img")) {
            if (isUrlProperty(str)) {
                textContent = element.getAttribute("src");
                if (StringUtils.isBlank(textContent)) {
                    throw new ParserException("Img element '" + strClassName + "' requires a non-empty src", -1);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Setting value '" + textContent + "' from src attribute");
                }
            } else {
                textContent = element.getAttribute("alt");
                if (StringUtils.isBlank(textContent)) {
                    throw new ParserException("Img element '" + strClassName + "' requires a non-empty alt", -1);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Setting value '" + textContent + "' from alt attribute");
                }
            }
        } else {
            textContent = getTextContent(element);
            if (!StringUtils.isBlank(textContent) && LOG.isDebugEnabled()) {
                LOG.debug("Setting value '" + textContent + "' from text content");
            }
        }
        if (StringUtils.isBlank(textContent)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Skipping property with empty value");
                return;
            }
            return;
        }
        contentHandler.startProperty(str);
        if (isDateProperty(str)) {
            try {
                Date dateIcalDate = icalDate(textContent);
                String string = dateIcalDate.toString();
                if (!(dateIcalDate instanceof DateTime)) {
                    try {
                        contentHandler.parameter(Parameter.VALUE, Value.DATE.getValue());
                    } catch (URISyntaxException e) {
                        LOG.warn("Caught exception", (Throwable) e);
                    }
                }
                textContent = string;
            } catch (ParseException e2) {
                throw new ParserException("Malformed date value for element '" + strClassName + "'", -1, e2);
            }
        }
        if (isTextProperty(str)) {
            String attributeNS = element.getAttributeNS("http://www.w3.org/XML/1998/namespace", "lang");
            if (!StringUtils.isBlank(attributeNS)) {
                try {
                    contentHandler.parameter(Parameter.LANGUAGE, attributeNS);
                } catch (URISyntaxException e3) {
                    LOG.warn("Caught exception", (Throwable) e3);
                }
            }
        }
        try {
            contentHandler.propertyValue(textContent);
            contentHandler.endProperty(str);
        } catch (IOException e4) {
            throw new CalendarException(e4);
        } catch (URISyntaxException e5) {
            throw new ParserException("Malformed URI value for element '" + strClassName + "'", -1, e5);
        } catch (ParseException e6) {
            throw new ParserException("Malformed value for element '" + strClassName + "'", -1, e6);
        }
    }

    private static String className(String str) {
        return str.toLowerCase();
    }

    private static boolean isHeaderElement(String str) {
        return str.equals("h1") || str.equals("h2") || str.equals("h3") || str.equals("h4") || str.equals("h5") || str.equals("h6");
    }

    private static boolean isDateProperty(String str) {
        return str.equals(Property.DTSTART) || str.equals(Property.DTEND) || str.equals(Property.DTSTAMP) || str.equals(Property.LAST_MODIFIED);
    }

    private static boolean isUrlProperty(String str) {
        return str.equals(Property.URL);
    }

    private static boolean isTextProperty(String str) {
        return str.equals(Property.SUMMARY) || str.equals("LOCATION") || str.equals(Property.CATEGORIES) || str.equals("DESCRIPTION") || str.equals(Property.ATTENDEE) || str.equals(Property.CONTACT) || str.equals(Property.ORGANIZER);
    }

    private static Date icalDate(String str) throws ParseException {
        if (str.indexOf(84) == -1) {
            try {
                if (str.indexOf(45) == -1) {
                    return new Date(str);
                }
            } catch (ParseException e) {
                LOG.warn("Caught exception", (Throwable) e);
            }
            return new Date(HCAL_DATE_FORMAT.parse(str));
        }
        try {
            return new DateTime(str);
        } catch (ParseException e2) {
            LOG.warn("Caught exception", (Throwable) e2);
            if (LOG.isDebugEnabled()) {
                LOG.debug("normalizing date-time " + str);
            }
            if (str.charAt(str.length() - 1) == 'Z') {
                str = str.replaceAll("Z", "GMT-00:00");
            } else if (!str.contains("GMT") && (str.charAt(str.length() - 6) == '+' || str.charAt(str.length() - 6) == '-')) {
                str = str.substring(0, str.length() - 6) + ("GMT" + str.substring(str.length() - 6));
            }
            DateTime dateTime = new DateTime(HCAL_DATE_TIME_FORMAT.parse(str));
            dateTime.setUtc(true);
            return dateTime;
        }
    }
}
