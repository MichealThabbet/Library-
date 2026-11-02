package org.apache.poi.hssf.model;

import android.R;
import it.piapostolos.android.pinotes.R2;
import org.apache.poi.ddf.EscherBoolProperty;
import org.apache.poi.ddf.EscherClientDataRecord;
import org.apache.poi.ddf.EscherContainerRecord;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperties;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.ddf.EscherSimpleProperty;
import org.apache.poi.ddf.EscherSpRecord;
import org.apache.poi.hssf.record.CommonObjectDataSubRecord;
import org.apache.poi.hssf.record.EndSubRecord;
import org.apache.poi.hssf.record.LbsDataSubRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;

/* loaded from: classes3.dex */
public class ComboboxShape extends AbstractShape {
    private ObjRecord objRecord;
    private EscherContainerRecord spContainer;

    ComboboxShape(HSSFSimpleShape hSSFSimpleShape, int i) {
        this.spContainer = createSpContainer(hSSFSimpleShape, i);
        this.objRecord = createObjRecord(hSSFSimpleShape, i);
    }

    private ObjRecord createObjRecord(HSSFSimpleShape hSSFSimpleShape, int i) {
        ObjRecord objRecord = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType((short) 20);
        commonObjectDataSubRecord.setObjectId(i);
        commonObjectDataSubRecord.setLocked(true);
        commonObjectDataSubRecord.setPrintable(false);
        commonObjectDataSubRecord.setAutofill(true);
        commonObjectDataSubRecord.setAutoline(false);
        LbsDataSubRecord lbsDataSubRecordNewAutoFilterInstance = LbsDataSubRecord.newAutoFilterInstance();
        EndSubRecord endSubRecord = new EndSubRecord();
        objRecord.addSubRecord(commonObjectDataSubRecord);
        objRecord.addSubRecord(lbsDataSubRecordNewAutoFilterInstance);
        objRecord.addSubRecord(endSubRecord);
        return objRecord;
    }

    private EscherContainerRecord createSpContainer(HSSFSimpleShape hSSFSimpleShape, int i) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord.setOptions((short) 15);
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions((short) 3218);
        escherSpRecord.setShapeId(i);
        escherSpRecord.setFlags(R2.id.custom);
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.addEscherProperty(new EscherBoolProperty((short) 127, R.string.accessibility_system_action_dpad_left_label));
        escherOptRecord.addEscherProperty(new EscherBoolProperty((short) 191, 524296));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524288));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GROUPSHAPE__PRINT, 131072));
        HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) hSSFSimpleShape.getAnchor();
        hSSFClientAnchor.setAnchorType(1);
        EscherRecord escherRecordCreateAnchor = createAnchor(hSSFClientAnchor);
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions((short) 0);
        escherContainerRecord.addChildRecord(escherSpRecord);
        escherContainerRecord.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(escherRecordCreateAnchor);
        escherContainerRecord.addChildRecord(escherClientDataRecord);
        return escherContainerRecord;
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
