package org.apache.poi.hssf.dev;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.http.message.TokenParser;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RecordFactory;
import org.apache.poi.hssf.record.RecordFormatException;
import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/* loaded from: classes3.dex */
public class RecordLister {
    String file;

    public void run() throws RecordFormatException, IOException {
        RecordInputStream recordInputStream = new RecordInputStream(new POIFSFileSystem(new FileInputStream(this.file)).createDocumentInputStream("Workbook"));
        while (recordInputStream.hasNextRecord()) {
            int nextSid = recordInputStream.getNextSid();
            recordInputStream.nextRecord();
            int iAvailable = recordInputStream.available();
            Class<? extends Record> recordClass = RecordFactory.getRecordClass(nextSid);
            System.out.print(formatSID(nextSid) + " - " + formatSize(iAvailable) + " bytes");
            if (recordClass != null) {
                System.out.print("  \t");
                System.out.print(recordClass.getName().replace("org.apache.poi.hssf.record.", ""));
            }
            System.out.println();
            byte[] remainder = recordInputStream.readRemainder();
            if (remainder.length > 0) {
                System.out.print("   ");
                System.out.println(formatData(remainder));
            }
        }
    }

    private static String formatSID(int i) {
        String hexString = Integer.toHexString(i);
        String string = Integer.toString(i);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0x");
        for (int length = hexString.length(); length < 4; length++) {
            stringBuffer.append('0');
        }
        stringBuffer.append(hexString);
        stringBuffer.append(" (");
        for (int length2 = string.length(); length2 < 4; length2++) {
            stringBuffer.append('0');
        }
        stringBuffer.append(string);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    private static String formatSize(int i) {
        String hexString = Integer.toHexString(i);
        String string = Integer.toString(i);
        StringBuffer stringBuffer = new StringBuffer();
        for (int length = hexString.length(); length < 3; length++) {
            stringBuffer.append('0');
        }
        stringBuffer.append(hexString);
        stringBuffer.append(" (");
        for (int length2 = string.length(); length2 < 3; length2++) {
            stringBuffer.append('0');
        }
        stringBuffer.append(string);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    private static String formatData(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (bArr.length > 9) {
            stringBuffer.append(byteToHex(bArr[0]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[1]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[2]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[3]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(" .... ");
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[bArr.length - 4]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[bArr.length - 3]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[bArr.length - 2]));
            stringBuffer.append(TokenParser.SP);
            stringBuffer.append(byteToHex(bArr[bArr.length - 1]));
        } else {
            for (byte b : bArr) {
                stringBuffer.append(byteToHex(b));
                stringBuffer.append(TokenParser.SP);
            }
        }
        return stringBuffer.toString();
    }

    private static String byteToHex(byte b) {
        int i = b;
        if (b < 0) {
            i = b + 256;
        }
        String hexString = Integer.toHexString(i);
        if (i >= 16) {
            return hexString;
        }
        return "0" + hexString;
    }

    public void setFile(String str) {
        this.file = str;
    }

    public static void main(String[] strArr) throws RecordFormatException {
        if (strArr.length == 1 && !strArr[0].equals("--help")) {
            try {
                RecordLister recordLister = new RecordLister();
                recordLister.setFile(strArr[0]);
                recordLister.run();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("RecordLister");
        System.out.println("Outputs the summary of the records in file order");
        System.out.println("usage: java org.apache.poi.hssf.dev.RecordLister filename");
    }
}
