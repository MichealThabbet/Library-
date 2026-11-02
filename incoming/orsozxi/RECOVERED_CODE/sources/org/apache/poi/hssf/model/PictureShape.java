package org.apache.poi.hssf.model;

import it.orsozoxi.android.orsonotes.R2;
import org.apache.poi.ddf.EscherClientDataRecord;
import org.apache.poi.ddf.EscherContainerRecord;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperties;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.ddf.EscherSimpleProperty;
import org.apache.poi.ddf.EscherSpRecord;
import org.apache.poi.hssf.record.CommonObjectDataSubRecord;
import org.apache.poi.hssf.record.EndSubRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.usermodel.HSSFAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;

/* loaded from: classes3.dex */
public class PictureShape extends AbstractShape {
    private ObjRecord objRecord;
    private EscherContainerRecord spContainer;

    PictureShape(HSSFSimpleShape hSSFSimpleShape, int i) {
        this.spContainer = createSpContainer(hSSFSimpleShape, i);
        this.objRecord = createObjRecord(hSSFSimpleShape, i);
    }

    private EscherContainerRecord createSpContainer(HSSFSimpleShape hSSFSimpleShape, int i) {
        HSSFPicture hSSFPicture = (HSSFPicture) hSSFSimpleShape;
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord.setOptions((short) 15);
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions((short) 1202);
        escherSpRecord.setShapeId(i);
        escherSpRecord.setFlags(R2.id.custom);
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.BLIP__BLIPTODISPLAY, false, true, hSSFPicture.getPictureIndex()));
        addStandardOptions(hSSFPicture, escherOptRecord);
        HSSFAnchor anchor = hSSFPicture.getAnchor();
        if (anchor.isHorizontallyFlipped()) {
            escherSpRecord.setFlags(escherSpRecord.getFlags() | 64);
        }
        if (anchor.isVerticallyFlipped()) {
            escherSpRecord.setFlags(escherSpRecord.getFlags() | 128);
        }
        EscherRecord escherRecordCreateAnchor = createAnchor(anchor);
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions((short) 0);
        escherContainerRecord.addChildRecord(escherSpRecord);
        escherContainerRecord.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(escherRecordCreateAnchor);
        escherContainerRecord.addChildRecord(escherClientDataRecord);
        return escherContainerRecord;
    }

    private ObjRecord createObjRecord(HSSFShape hSSFShape, int i) {
        ObjRecord objRecord = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType((short) ((HSSFSimpleShape) hSSFShape).getShapeType());
        commonObjectDataSubRecord.setObjectId(i);
        commonObjectDataSubRecord.setLocked(true);
        commonObjectDataSubRecord.setPrintable(true);
        commonObjectDataSubRecord.setAutofill(true);
        commonObjectDataSubRecord.setAutoline(true);
        commonObjectDataSubRecord.setReserved2(0);
        EndSubRecord endSubRecord = new EndSubRecord();
        objRecord.addSubRecord(commonObjectDataSubRecord);
        objRecord.addSubRecord(endSubRecord);
        return objRecord;
    }

    @Override // org.apache.poi.hssf.model.AbstractShape
    public EscherContainerRecord getSpContainer() {
        return this.spContainer;
    }

    @Override // org.apache.poi.hssf.model.AbstractShape
    public ObjRecord getObjRecord() {
        return this.objRecord;
    }
}
