package org.apache.poi.hssf.record;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ddf.DefaultEscherRecordFactory;
import org.apache.poi.ddf.EscherBoolProperty;
import org.apache.poi.ddf.EscherClientAnchorRecord;
import org.apache.poi.ddf.EscherClientDataRecord;
import org.apache.poi.ddf.EscherContainerRecord;
import org.apache.poi.ddf.EscherDgRecord;
import org.apache.poi.ddf.EscherDggRecord;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperties;
import org.apache.poi.ddf.EscherProperty;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.ddf.EscherRecordFactory;
import org.apache.poi.ddf.EscherSerializationListener;
import org.apache.poi.ddf.EscherSimpleProperty;
import org.apache.poi.ddf.EscherSpRecord;
import org.apache.poi.ddf.EscherSpgrRecord;
import org.apache.poi.ddf.EscherTextboxRecord;
import org.apache.poi.hssf.model.AbstractShape;
import org.apache.poi.hssf.model.CommentShape;
import org.apache.poi.hssf.model.ConvertAnchor;
import org.apache.poi.hssf.model.DrawingManager2;
import org.apache.poi.hssf.model.TextboxShape;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFShapeContainer;
import org.apache.poi.hssf.usermodel.HSSFShapeGroup;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/* loaded from: classes3.dex */
public final class EscherAggregate extends AbstractEscherHolderRecord {
    public static final short ST_ACCENTBORDERCALLOUT1 = 50;
    public static final short ST_ACCENTBORDERCALLOUT2 = 51;
    public static final short ST_ACCENTBORDERCALLOUT3 = 52;
    public static final short ST_ACCENTBORDERCALLOUT90 = 181;
    public static final short ST_ACCENTCALLOUT1 = 44;
    public static final short ST_ACCENTCALLOUT2 = 45;
    public static final short ST_ACCENTCALLOUT3 = 46;
    public static final short ST_ACCENTCALLOUT90 = 179;
    public static final short ST_ACTIONBUTTONBACKPREVIOUS = 194;
    public static final short ST_ACTIONBUTTONBEGINNING = 196;
    public static final short ST_ACTIONBUTTONBLANK = 189;
    public static final short ST_ACTIONBUTTONDOCUMENT = 198;
    public static final short ST_ACTIONBUTTONEND = 195;
    public static final short ST_ACTIONBUTTONFORWARDNEXT = 193;
    public static final short ST_ACTIONBUTTONHELP = 191;
    public static final short ST_ACTIONBUTTONHOME = 190;
    public static final short ST_ACTIONBUTTONINFORMATION = 192;
    public static final short ST_ACTIONBUTTONMOVIE = 200;
    public static final short ST_ACTIONBUTTONRETURN = 197;
    public static final short ST_ACTIONBUTTONSOUND = 199;
    public static final short ST_ARC = 19;
    public static final short ST_ARROW = 13;
    public static final short ST_BALLOON = 17;
    public static final short ST_BENTARROW = 91;
    public static final short ST_BENTCONNECTOR2 = 33;
    public static final short ST_BENTCONNECTOR3 = 34;
    public static final short ST_BENTCONNECTOR4 = 35;
    public static final short ST_BENTCONNECTOR5 = 36;
    public static final short ST_BENTUPARROW = 90;
    public static final short ST_BEVEL = 84;
    public static final short ST_BLOCKARC = 95;
    public static final short ST_BORDERCALLOUT1 = 47;
    public static final short ST_BORDERCALLOUT2 = 48;
    public static final short ST_BORDERCALLOUT3 = 49;
    public static final short ST_BORDERCALLOUT90 = 180;
    public static final short ST_BRACEPAIR = 186;
    public static final short ST_BRACKETPAIR = 185;
    public static final short ST_CALLOUT1 = 41;
    public static final short ST_CALLOUT2 = 42;
    public static final short ST_CALLOUT3 = 43;
    public static final short ST_CALLOUT90 = 178;
    public static final short ST_CAN = 22;
    public static final short ST_CHEVRON = 55;
    public static final short ST_CIRCULARARROW = 99;
    public static final short ST_CLOUDCALLOUT = 106;
    public static final short ST_CUBE = 16;
    public static final short ST_CURVEDCONNECTOR2 = 37;
    public static final short ST_CURVEDCONNECTOR3 = 38;
    public static final short ST_CURVEDCONNECTOR4 = 39;
    public static final short ST_CURVEDCONNECTOR5 = 40;
    public static final short ST_CURVEDDOWNARROW = 105;
    public static final short ST_CURVEDLEFTARROW = 103;
    public static final short ST_CURVEDRIGHTARROW = 102;
    public static final short ST_CURVEDUPARROW = 104;
    public static final short ST_DIAMOND = 4;
    public static final short ST_DONUT = 23;
    public static final short ST_DOUBLEWAVE = 188;
    public static final short ST_DOWNARROW = 67;
    public static final short ST_DOWNARROWCALLOUT = 80;
    public static final short ST_ELLIPSE = 3;
    public static final short ST_ELLIPSERIBBON = 107;
    public static final short ST_ELLIPSERIBBON2 = 108;
    public static final short ST_FLOWCHARTALTERNATEPROCESS = 176;
    public static final short ST_FLOWCHARTCOLLATE = 125;
    public static final short ST_FLOWCHARTCONNECTOR = 120;
    public static final short ST_FLOWCHARTDECISION = 110;
    public static final short ST_FLOWCHARTDELAY = 135;
    public static final short ST_FLOWCHARTDISPLAY = 134;
    public static final short ST_FLOWCHARTDOCUMENT = 114;
    public static final short ST_FLOWCHARTEXTRACT = 127;
    public static final short ST_FLOWCHARTINPUTOUTPUT = 111;
    public static final short ST_FLOWCHARTINTERNALSTORAGE = 113;
    public static final short ST_FLOWCHARTMAGNETICDISK = 132;
    public static final short ST_FLOWCHARTMAGNETICDRUM = 133;
    public static final short ST_FLOWCHARTMAGNETICTAPE = 131;
    public static final short ST_FLOWCHARTMANUALINPUT = 118;
    public static final short ST_FLOWCHARTMANUALOPERATION = 119;
    public static final short ST_FLOWCHARTMERGE = 128;
    public static final short ST_FLOWCHARTMULTIDOCUMENT = 115;
    public static final short ST_FLOWCHARTOFFLINESTORAGE = 129;
    public static final short ST_FLOWCHARTOFFPAGECONNECTOR = 177;
    public static final short ST_FLOWCHARTONLINESTORAGE = 130;
    public static final short ST_FLOWCHARTOR = 124;
    public static final short ST_FLOWCHARTPREDEFINEDPROCESS = 112;
    public static final short ST_FLOWCHARTPREPARATION = 117;
    public static final short ST_FLOWCHARTPROCESS = 109;
    public static final short ST_FLOWCHARTPUNCHEDCARD = 121;
    public static final short ST_FLOWCHARTPUNCHEDTAPE = 122;
    public static final short ST_FLOWCHARTSORT = 126;
    public static final short ST_FLOWCHARTSUMMINGJUNCTION = 123;
    public static final short ST_FLOWCHARTTERMINATOR = 116;
    public static final short ST_FOLDEDCORNER = 65;
    public static final short ST_HEART = 74;
    public static final short ST_HEXAGON = 9;
    public static final short ST_HOMEPLATE = 15;
    public static final short ST_HORIZONTALSCROLL = 98;
    public static final short ST_HOSTCONTROL = 201;
    public static final short ST_IRREGULARSEAL1 = 71;
    public static final short ST_IRREGULARSEAL2 = 72;
    public static final short ST_ISOCELESTRIANGLE = 5;
    public static final short ST_LEFTARROW = 66;
    public static final short ST_LEFTARROWCALLOUT = 77;
    public static final short ST_LEFTBRACE = 87;
    public static final short ST_LEFTBRACKET = 85;
    public static final short ST_LEFTRIGHTARROW = 69;
    public static final short ST_LEFTRIGHTARROWCALLOUT = 81;
    public static final short ST_LEFTRIGHTUPARROW = 182;
    public static final short ST_LEFTUPARROW = 89;
    public static final short ST_LIGHTNINGBOLT = 73;
    public static final short ST_LINE = 20;
    public static final short ST_MIN = 0;
    public static final short ST_MOON = 184;
    public static final short ST_NIL = 4095;
    public static final short ST_NOSMOKING = 57;
    public static final short ST_NOTCHEDCIRCULARARROW = 100;
    public static final short ST_NOTCHEDRIGHTARROW = 94;
    public static final short ST_NOT_PRIMATIVE = 0;
    public static final short ST_OCTAGON = 10;
    public static final short ST_PARALLELOGRAM = 7;
    public static final short ST_PENTAGON = 56;
    public static final short ST_PICTUREFRAME = 75;
    public static final short ST_PLAQUE = 21;
    public static final short ST_PLUS = 11;
    public static final short ST_QUADARROW = 76;
    public static final short ST_QUADARROWCALLOUT = 83;
    public static final short ST_RECTANGLE = 1;
    public static final short ST_RIBBON = 53;
    public static final short ST_RIBBON2 = 54;
    public static final short ST_RIGHTARROWCALLOUT = 78;
    public static final short ST_RIGHTBRACE = 88;
    public static final short ST_RIGHTBRACKET = 86;
    public static final short ST_RIGHTTRIANGLE = 6;
    public static final short ST_ROUNDRECTANGLE = 2;
    public static final short ST_SEAL = 18;
    public static final short ST_SEAL16 = 59;
    public static final short ST_SEAL24 = 92;
    public static final short ST_SEAL32 = 60;
    public static final short ST_SEAL4 = 187;
    public static final short ST_SEAL8 = 58;
    public static final short ST_SMILEYFACE = 96;
    public static final short ST_STAR = 12;
    public static final short ST_STRAIGHTCONNECTOR1 = 32;
    public static final short ST_STRIPEDRIGHTARROW = 93;
    public static final short ST_SUN = 183;
    public static final short ST_TEXTARCHDOWNCURVE = 145;
    public static final short ST_TEXTARCHDOWNPOUR = 149;
    public static final short ST_TEXTARCHUPCURVE = 144;
    public static final short ST_TEXTARCHUPPOUR = 148;
    public static final short ST_TEXTBOX = 202;
    public static final short ST_TEXTBUTTONCURVE = 147;
    public static final short ST_TEXTBUTTONPOUR = 151;
    public static final short ST_TEXTCANDOWN = 175;
    public static final short ST_TEXTCANUP = 174;
    public static final short ST_TEXTCASCADEDOWN = 155;
    public static final short ST_TEXTCASCADEUP = 154;
    public static final short ST_TEXTCHEVRON = 140;
    public static final short ST_TEXTCHEVRONINVERTED = 141;
    public static final short ST_TEXTCIRCLECURVE = 146;
    public static final short ST_TEXTCIRCLEPOUR = 150;
    public static final short ST_TEXTCURVE = 27;
    public static final short ST_TEXTCURVEDOWN = 153;
    public static final short ST_TEXTCURVEUP = 152;
    public static final short ST_TEXTDEFLATE = 161;
    public static final short ST_TEXTDEFLATEBOTTOM = 163;
    public static final short ST_TEXTDEFLATEINFLATE = 166;
    public static final short ST_TEXTDEFLATEINFLATEDEFLATE = 167;
    public static final short ST_TEXTDEFLATETOP = 165;
    public static final short ST_TEXTFADEDOWN = 171;
    public static final short ST_TEXTFADELEFT = 169;
    public static final short ST_TEXTFADERIGHT = 168;
    public static final short ST_TEXTFADEUP = 170;
    public static final short ST_TEXTHEXAGON = 26;
    public static final short ST_TEXTINFLATE = 160;
    public static final short ST_TEXTINFLATEBOTTOM = 162;
    public static final short ST_TEXTINFLATETOP = 164;
    public static final short ST_TEXTOCTAGON = 25;
    public static final short ST_TEXTONCURVE = 30;
    public static final short ST_TEXTONRING = 31;
    public static final short ST_TEXTPLAINTEXT = 136;
    public static final short ST_TEXTRING = 29;
    public static final short ST_TEXTRINGINSIDE = 142;
    public static final short ST_TEXTRINGOUTSIDE = 143;
    public static final short ST_TEXTSIMPLE = 24;
    public static final short ST_TEXTSLANTDOWN = 173;
    public static final short ST_TEXTSLANTUP = 172;
    public static final short ST_TEXTSTOP = 137;
    public static final short ST_TEXTTRIANGLE = 138;
    public static final short ST_TEXTTRIANGLEINVERTED = 139;
    public static final short ST_TEXTWAVE = 28;
    public static final short ST_TEXTWAVE1 = 156;
    public static final short ST_TEXTWAVE2 = 157;
    public static final short ST_TEXTWAVE3 = 158;
    public static final short ST_TEXTWAVE4 = 159;
    public static final short ST_THICKARROW = 14;
    public static final short ST_TRAPEZOID = 8;
    public static final short ST_UPARROW = 68;
    public static final short ST_UPARROWCALLOUT = 79;
    public static final short ST_UPDOWNARROW = 70;
    public static final short ST_UPDOWNARROWCALLOUT = 82;
    public static final short ST_UTURNARROW = 101;
    public static final short ST_VERTICALSCROLL = 97;
    public static final short ST_WAVE = 64;
    public static final short ST_WEDGEELLIPSECALLOUT = 63;
    public static final short ST_WEDGERECTCALLOUT = 61;
    public static final short ST_WEDGERRECTCALLOUT = 62;
    private static POILogger log = POILogFactory.getLogger(EscherAggregate.class);
    public static final short sid = 9876;
    private short drawingGroupId;
    private DrawingManager2 drawingManager;
    protected HSSFPatriarch patriarch;
    private Map<EscherRecord, Record> shapeToObj = new HashMap();
    private List tailRec = new ArrayList();

    @Override // org.apache.poi.hssf.record.AbstractEscherHolderRecord
    protected String getRecordName() {
        return "ESCHERAGGREGATE";
    }

    @Override // org.apache.poi.hssf.record.AbstractEscherHolderRecord, org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public EscherAggregate(DrawingManager2 drawingManager2) {
        this.drawingManager = drawingManager2;
    }

    @Override // org.apache.poi.hssf.record.AbstractEscherHolderRecord, org.apache.poi.hssf.record.Record
    public String toString() {
        String property = System.getProperty("line.separtor");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        stringBuffer.append(getRecordName());
        stringBuffer.append(']' + property);
        Iterator<EscherRecord> it2 = getEscherRecords().iterator();
        while (it2.hasNext()) {
            stringBuffer.append(it2.next().toString());
        }
        stringBuffer.append("[/");
        stringBuffer.append(getRecordName());
        stringBuffer.append(']' + property);
        return stringBuffer.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static EscherAggregate createAggregate(List list, int i, DrawingManager2 drawingManager2) {
        final ArrayList arrayList = new ArrayList();
        EscherRecordFactory escherRecordFactory = new DefaultEscherRecordFactory() { // from class: org.apache.poi.hssf.record.EscherAggregate.1
            @Override // org.apache.poi.ddf.DefaultEscherRecordFactory, org.apache.poi.ddf.EscherRecordFactory
            public EscherRecord createRecord(byte[] bArr, int i2) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
                EscherRecord escherRecordCreateRecord = super.createRecord(bArr, i2);
                if (escherRecordCreateRecord.getRecordId() == -4079 || escherRecordCreateRecord.getRecordId() == -4083) {
                    arrayList.add(escherRecordCreateRecord);
                }
                return escherRecordCreateRecord;
            }
        };
        EscherAggregate escherAggregate = new EscherAggregate(drawingManager2);
        int i2 = 0;
        int i3 = i;
        int length = 0;
        while (true) {
            int i4 = i3 + 1;
            if (i4 >= list.size() || sid(list, i3) != 236 || !isObjectRecord(list, i4)) {
                break;
            }
            length += ((DrawingRecord) list.get(i3)).getData().length;
            i3 += 2;
        }
        byte[] bArr = new byte[length];
        int i5 = i;
        int length2 = 0;
        while (true) {
            int i6 = i5 + 1;
            if (i6 >= list.size() || sid(list, i5) != 236 || !isObjectRecord(list, i6)) {
                break;
            }
            DrawingRecord drawingRecord = (DrawingRecord) list.get(i5);
            System.arraycopy(drawingRecord.getData(), 0, bArr, length2, drawingRecord.getData().length);
            length2 += drawingRecord.getData().length;
            i5 += 2;
        }
        int i7 = 0;
        while (i7 < length) {
            EscherRecord escherRecordCreateRecord = escherRecordFactory.createRecord(bArr, i7);
            int iFillFields = escherRecordCreateRecord.fillFields(bArr, i7, escherRecordFactory);
            escherAggregate.addEscherRecord(escherRecordCreateRecord);
            i7 += iFillFields;
        }
        escherAggregate.shapeToObj = new HashMap();
        while (true) {
            int i8 = i + 1;
            if (i8 >= list.size() || sid(list, i) != 236 || !isObjectRecord(list, i8)) {
                break;
            }
            escherAggregate.shapeToObj.put(arrayList.get(i2), (Record) list.get(i8));
            i += 2;
            i2++;
        }
        return escherAggregate;
    }

    @Override // org.apache.poi.hssf.record.AbstractEscherHolderRecord, org.apache.poi.hssf.record.RecordBase
    public int serialize(int i, byte[] bArr) {
        convertUserModelToRecords();
        List<EscherRecord> escherRecords = getEscherRecords();
        byte[] bArr2 = new byte[getEscherRecordSize(escherRecords)];
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        Iterator<EscherRecord> it2 = escherRecords.iterator();
        int iSerialize = 0;
        while (it2.hasNext()) {
            iSerialize += it2.next().serialize(iSerialize, bArr2, new EscherSerializationListener() { // from class: org.apache.poi.hssf.record.EscherAggregate.2
                @Override // org.apache.poi.ddf.EscherSerializationListener
                public void beforeRecordSerialize(int i2, short s, EscherRecord escherRecord) {
                }

                @Override // org.apache.poi.ddf.EscherSerializationListener
                public void afterRecordSerialize(int i2, short s, int i3, EscherRecord escherRecord) {
                    if (s == -4079 || s == -4083) {
                        arrayList.add(Integer.valueOf(i2));
                        arrayList2.add(escherRecord);
                    }
                }
            });
        }
        arrayList2.add(0, null);
        arrayList.add(0, null);
        int iSerialize2 = i;
        int i2 = 1;
        while (i2 < arrayList2.size()) {
            int iIntValue = ((Integer) arrayList.get(i2)).intValue() - 1;
            int iIntValue2 = i2 == 1 ? 0 : ((Integer) arrayList.get(i2 - 1)).intValue();
            DrawingRecord drawingRecord = new DrawingRecord();
            int i3 = (iIntValue - iIntValue2) + 1;
            byte[] bArr3 = new byte[i3];
            System.arraycopy(bArr2, iIntValue2, bArr3, 0, i3);
            drawingRecord.setData(bArr3);
            int iSerialize3 = iSerialize2 + drawingRecord.serialize(iSerialize2, bArr);
            iSerialize2 = iSerialize3 + this.shapeToObj.get(arrayList2.get(i2)).serialize(iSerialize3, bArr);
            i2++;
        }
        for (int i4 = 0; i4 < this.tailRec.size(); i4++) {
            iSerialize2 += ((Record) this.tailRec.get(i4)).serialize(iSerialize2, bArr);
        }
        int i5 = iSerialize2 - i;
        if (i5 == getRecordSize()) {
            return i5;
        }
        throw new RecordFormatException(i5 + " bytes written but getRecordSize() reports " + getRecordSize());
    }

    private int getEscherRecordSize(List list) {
        Iterator it2 = list.iterator();
        int recordSize = 0;
        while (it2.hasNext()) {
            recordSize += ((EscherRecord) it2.next()).getRecordSize();
        }
        return recordSize;
    }

    @Override // org.apache.poi.hssf.record.AbstractEscherHolderRecord, org.apache.poi.hssf.record.RecordBase
    public int getRecordSize() {
        convertUserModelToRecords();
        int escherRecordSize = getEscherRecordSize(getEscherRecords()) + (this.shapeToObj.size() * 4);
        Iterator<Record> it2 = this.shapeToObj.values().iterator();
        int recordSize = 0;
        int recordSize2 = 0;
        while (it2.hasNext()) {
            recordSize2 += it2.next().getRecordSize();
        }
        Iterator it3 = this.tailRec.iterator();
        while (it3.hasNext()) {
            recordSize += ((Record) it3.next()).getRecordSize();
        }
        return escherRecordSize + recordSize2 + recordSize;
    }

    Object associateShapeToObjRecord(EscherRecord escherRecord, ObjRecord objRecord) {
        return this.shapeToObj.put(escherRecord, objRecord);
    }

    public HSSFPatriarch getPatriarch() {
        return this.patriarch;
    }

    public void setPatriarch(HSSFPatriarch hSSFPatriarch) {
        this.patriarch = hSSFPatriarch;
    }

    public void convertRecordsToUserModel() {
        EscherSpgrRecord escherSpgrRecord;
        if (this.patriarch == null) {
            throw new IllegalStateException("Must call setPatriarch() first");
        }
        EscherContainerRecord escherContainer = getEscherContainer();
        if (escherContainer == null) {
            return;
        }
        List<EscherContainerRecord> childContainers = escherContainer.getChildContainers().get(0).getChildContainers();
        if (childContainers.size() == 0) {
            throw new IllegalStateException("No child escher containers at the point that should hold the patriach data, and one container per top level shape!");
        }
        Iterator<EscherRecord> childIterator = childContainers.get(0).getChildIterator();
        while (true) {
            if (!childIterator.hasNext()) {
                escherSpgrRecord = null;
                break;
            }
            EscherRecord next = childIterator.next();
            if (next instanceof EscherSpgrRecord) {
                escherSpgrRecord = (EscherSpgrRecord) next;
                break;
            }
        }
        if (escherSpgrRecord != null) {
            this.patriarch.setCoordinates(escherSpgrRecord.getRectX1(), escherSpgrRecord.getRectY1(), escherSpgrRecord.getRectX2(), escherSpgrRecord.getRectY2());
        }
        for (int i = 1; i < childContainers.size(); i++) {
            EscherContainerRecord escherContainerRecord = childContainers.get(i);
            if (escherContainerRecord.getRecordId() == -4093) {
                if (escherContainerRecord.getChildRecords().size() > 0) {
                    HSSFShapeGroup hSSFShapeGroup = new HSSFShapeGroup(null, new HSSFClientAnchor());
                    this.patriarch.getChildren().add(hSSFShapeGroup);
                    convertRecordsToUserModel((EscherContainerRecord) escherContainerRecord.getChild(0), hSSFShapeGroup);
                } else {
                    log.log(POILogger.WARN, "Found drawing group without children.");
                }
            } else if (escherContainerRecord.getRecordId() == -4092) {
                int options = escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID).getOptions() >> 4;
                if (options == 75) {
                    EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 260);
                    if (escherSimpleProperty == null) {
                        log.log(POILogger.WARN, "Picture index for picture shape not found.");
                    } else {
                        int propertyValue = escherSimpleProperty.getPropertyValue();
                        EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) getEscherChild(escherContainerRecord, -4080);
                        HSSFClientAnchor hSSFClientAnchor = new HSSFClientAnchor();
                        hSSFClientAnchor.setCol1(escherClientAnchorRecord.getCol1());
                        hSSFClientAnchor.setCol2(escherClientAnchorRecord.getCol2());
                        hSSFClientAnchor.setDx1(escherClientAnchorRecord.getDx1());
                        hSSFClientAnchor.setDx2(escherClientAnchorRecord.getDx2());
                        hSSFClientAnchor.setDy1(escherClientAnchorRecord.getDy1());
                        hSSFClientAnchor.setDy2(escherClientAnchorRecord.getDy2());
                        hSSFClientAnchor.setRow1(escherClientAnchorRecord.getRow1());
                        hSSFClientAnchor.setRow2(escherClientAnchorRecord.getRow2());
                        HSSFPicture hSSFPicture = new HSSFPicture(null, hSSFClientAnchor);
                        hSSFPicture.setPictureIndex(propertyValue);
                        this.patriarch.getChildren().add(hSSFPicture);
                    }
                } else if (options == 202) {
                    HSSFTextbox hSSFTextbox = new HSSFTextbox(null, new HSSFClientAnchor());
                    this.patriarch.getChildren().add(hSSFTextbox);
                    convertRecordsToUserModel(escherContainerRecord, hSSFTextbox);
                } else {
                    log.log(POILogger.WARN, "Unhandled shape type: " + options);
                }
            } else {
                log.log(POILogger.WARN, "Unexpected record id of shape group.");
            }
        }
        this.drawingManager.getDgg().setFileIdClusters(new EscherDggRecord.FileIdCluster[0]);
    }

    private void convertRecordsToUserModel(EscherContainerRecord escherContainerRecord, Object obj) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next instanceof EscherSpgrRecord) {
                EscherSpgrRecord escherSpgrRecord = (EscherSpgrRecord) next;
                if (obj instanceof HSSFShapeGroup) {
                    ((HSSFShapeGroup) obj).setCoordinates(escherSpgrRecord.getRectX1(), escherSpgrRecord.getRectY1(), escherSpgrRecord.getRectX2(), escherSpgrRecord.getRectY2());
                } else {
                    throw new IllegalStateException("Got top level anchor but not processing a group");
                }
            } else if (next instanceof EscherClientAnchorRecord) {
                EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) next;
                if (obj instanceof HSSFShape) {
                    HSSFShape hSSFShape = (HSSFShape) obj;
                    hSSFShape.getAnchor().setDx1(escherClientAnchorRecord.getDx1());
                    hSSFShape.getAnchor().setDx2(escherClientAnchorRecord.getDx2());
                    hSSFShape.getAnchor().setDy1(escherClientAnchorRecord.getDy1());
                    hSSFShape.getAnchor().setDy2(escherClientAnchorRecord.getDy2());
                } else {
                    throw new IllegalStateException("Got top level anchor but not processing a group or shape");
                }
            } else if (next instanceof EscherTextboxRecord) {
            } else if (!(next instanceof EscherSpRecord)) {
                boolean z = next instanceof EscherOptRecord;
            }
        }
    }

    public void clear() {
        clearEscherRecords();
        this.shapeToObj.clear();
    }

    private static boolean isObjectRecord(List list, int i) {
        return sid(list, i) == 93 || sid(list, i) == 438;
    }

    private void convertUserModelToRecords() {
        if (this.patriarch != null) {
            this.shapeToObj.clear();
            this.tailRec.clear();
            clearEscherRecords();
            if (this.patriarch.getChildren().size() != 0) {
                convertPatriarch(this.patriarch);
                Iterator<EscherRecord> childIterator = ((EscherContainerRecord) getEscherRecord(0)).getChildIterator();
                EscherContainerRecord escherContainerRecord = null;
                while (childIterator.hasNext()) {
                    EscherRecord next = childIterator.next();
                    if (next.getRecordId() == -4093) {
                        escherContainerRecord = (EscherContainerRecord) next;
                    }
                }
                convertShapes(this.patriarch, escherContainerRecord, this.shapeToObj);
                this.patriarch = null;
            }
        }
    }

    private void convertShapes(HSSFShapeContainer hSSFShapeContainer, EscherContainerRecord escherContainerRecord, Map map) {
        if (escherContainerRecord == null) {
            throw new IllegalArgumentException("Parent record required");
        }
        for (HSSFShape hSSFShape : hSSFShapeContainer.getChildren()) {
            if (hSSFShape instanceof HSSFShapeGroup) {
                convertGroup((HSSFShapeGroup) hSSFShape, escherContainerRecord, map);
            } else {
                AbstractShape abstractShapeCreateShape = AbstractShape.createShape(hSSFShape, this.drawingManager.allocateShapeId(this.drawingGroupId));
                map.put(findClientData(abstractShapeCreateShape.getSpContainer()), abstractShapeCreateShape.getObjRecord());
                if (abstractShapeCreateShape instanceof TextboxShape) {
                    TextboxShape textboxShape = (TextboxShape) abstractShapeCreateShape;
                    map.put(textboxShape.getEscherTextbox(), textboxShape.getTextObjectRecord());
                    if (abstractShapeCreateShape instanceof CommentShape) {
                        this.tailRec.add(((CommentShape) abstractShapeCreateShape).getNoteRecord());
                    }
                }
                escherContainerRecord.addChildRecord(abstractShapeCreateShape.getSpContainer());
            }
        }
    }

    private void convertGroup(HSSFShapeGroup hSSFShapeGroup, EscherContainerRecord escherContainerRecord, Map map) {
        EscherContainerRecord escherContainerRecord2 = new EscherContainerRecord();
        EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
        EscherSpgrRecord escherSpgrRecord = new EscherSpgrRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord2.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        escherContainerRecord2.setOptions((short) 15);
        escherContainerRecord3.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord3.setOptions((short) 15);
        escherSpgrRecord.setRecordId(EscherSpgrRecord.RECORD_ID);
        escherSpgrRecord.setOptions((short) 1);
        escherSpgrRecord.setRectX1(hSSFShapeGroup.getX1());
        escherSpgrRecord.setRectY1(hSSFShapeGroup.getY1());
        escherSpgrRecord.setRectX2(hSSFShapeGroup.getX2());
        escherSpgrRecord.setRectY2(hSSFShapeGroup.getY2());
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions((short) 2);
        int iAllocateShapeId = this.drawingManager.allocateShapeId(this.drawingGroupId);
        escherSpRecord.setShapeId(iAllocateShapeId);
        if (hSSFShapeGroup.getAnchor() instanceof HSSFClientAnchor) {
            escherSpRecord.setFlags(513);
        } else {
            escherSpRecord.setFlags(515);
        }
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.setOptions((short) 35);
        escherOptRecord.addEscherProperty(new EscherBoolProperty((short) 127, 262148));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.GROUPSHAPE__PRINT, 524288));
        EscherRecord escherRecordCreateAnchor = ConvertAnchor.createAnchor(hSSFShapeGroup.getAnchor());
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions((short) 0);
        escherContainerRecord2.addChildRecord(escherContainerRecord3);
        escherContainerRecord3.addChildRecord(escherSpgrRecord);
        escherContainerRecord3.addChildRecord(escherSpRecord);
        escherContainerRecord3.addChildRecord(escherOptRecord);
        escherContainerRecord3.addChildRecord(escherRecordCreateAnchor);
        escherContainerRecord3.addChildRecord(escherClientDataRecord);
        ObjRecord objRecord = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType((short) 0);
        commonObjectDataSubRecord.setObjectId(iAllocateShapeId);
        commonObjectDataSubRecord.setLocked(true);
        commonObjectDataSubRecord.setPrintable(true);
        commonObjectDataSubRecord.setAutofill(true);
        commonObjectDataSubRecord.setAutoline(true);
        GroupMarkerSubRecord groupMarkerSubRecord = new GroupMarkerSubRecord();
        EndSubRecord endSubRecord = new EndSubRecord();
        objRecord.addSubRecord(commonObjectDataSubRecord);
        objRecord.addSubRecord(groupMarkerSubRecord);
        objRecord.addSubRecord(endSubRecord);
        map.put(escherClientDataRecord, objRecord);
        escherContainerRecord.addChildRecord(escherContainerRecord2);
        convertShapes(hSSFShapeGroup, escherContainerRecord2, map);
    }

    private EscherRecord findClientData(EscherContainerRecord escherContainerRecord) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4079) {
                return next;
            }
        }
        throw new IllegalArgumentException("Can not find client data record");
    }

    private void convertPatriarch(HSSFPatriarch hSSFPatriarch) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherContainerRecord escherContainerRecord2 = new EscherContainerRecord();
        EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
        EscherSpgrRecord escherSpgrRecord = new EscherSpgrRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.DG_CONTAINER);
        escherContainerRecord.setOptions((short) 15);
        EscherDgRecord escherDgRecordCreateDgRecord = this.drawingManager.createDgRecord();
        this.drawingGroupId = escherDgRecordCreateDgRecord.getDrawingGroupId();
        escherContainerRecord2.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        escherContainerRecord2.setOptions((short) 15);
        escherContainerRecord3.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord3.setOptions((short) 15);
        escherSpgrRecord.setRecordId(EscherSpgrRecord.RECORD_ID);
        escherSpgrRecord.setOptions((short) 1);
        escherSpgrRecord.setRectX1(hSSFPatriarch.getX1());
        escherSpgrRecord.setRectY1(hSSFPatriarch.getY1());
        escherSpgrRecord.setRectX2(hSSFPatriarch.getX2());
        escherSpgrRecord.setRectY2(hSSFPatriarch.getY2());
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions((short) 2);
        escherSpRecord.setShapeId(this.drawingManager.allocateShapeId(escherDgRecordCreateDgRecord.getDrawingGroupId()));
        escherSpRecord.setFlags(5);
        escherContainerRecord.addChildRecord(escherDgRecordCreateDgRecord);
        escherContainerRecord.addChildRecord(escherContainerRecord2);
        escherContainerRecord2.addChildRecord(escherContainerRecord3);
        escherContainerRecord3.addChildRecord(escherSpgrRecord);
        escherContainerRecord3.addChildRecord(escherSpRecord);
        addEscherRecord(escherContainerRecord);
    }

    private static short sid(List list, int i) {
        return ((Record) list.get(i)).getSid();
    }

    private static EscherRecord getEscherChild(EscherContainerRecord escherContainerRecord, int i) {
        for (EscherRecord escherRecord : escherContainerRecord.getChildRecords()) {
            if (escherRecord.getRecordId() == i) {
                return escherRecord;
            }
        }
        return null;
    }

    private static EscherProperty getEscherProperty(EscherOptRecord escherOptRecord, int i) {
        if (escherOptRecord == null) {
            return null;
        }
        for (EscherProperty escherProperty : escherOptRecord.getEscherProperties()) {
            if (escherProperty.getPropertyNumber() == i) {
                return escherProperty;
            }
        }
        return null;
    }
}
