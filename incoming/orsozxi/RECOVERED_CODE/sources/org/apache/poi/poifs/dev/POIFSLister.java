package org.apache.poi.poifs.dev;

import com.app.orsozoxi.Views.CustomCopticTextView;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentNode;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/* loaded from: classes3.dex */
public class POIFSLister {
    public static void main(String[] strArr) throws IOException {
        if (strArr.length == 0) {
            System.err.println("Must specify at least one file to view");
            System.exit(1);
        }
        boolean z = false;
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equalsIgnoreCase("-size") || strArr[i].equalsIgnoreCase("-sizes")) {
                z = true;
            } else {
                viewFile(strArr[i], z);
            }
        }
    }

    public static void viewFile(String str, boolean z) throws IOException {
        displayDirectory(new POIFSFileSystem(new FileInputStream(str)).getRoot(), "", z);
    }

    public static void displayDirectory(DirectoryNode directoryNode, String str, boolean z) {
        String str2;
        System.out.println(str + directoryNode.getName() + " -");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(CustomCopticTextView.TWO_CHINESE_BLANK);
        String string = sb.toString();
        Iterator<Entry> entries = directoryNode.getEntries();
        boolean z2 = false;
        while (entries.hasNext()) {
            Entry next = entries.next();
            if (next instanceof DirectoryNode) {
                displayDirectory((DirectoryNode) next, string, z);
            } else {
                DocumentNode documentNode = (DocumentNode) next;
                String name = documentNode.getName();
                if (name.charAt(0) < '\n') {
                    name = name.substring(1) + " <" + ("(0x0" + ((int) name.charAt(0)) + ")" + name.substring(1)) + ">";
                }
                if (z) {
                    str2 = " [" + documentNode.getSize() + " / 0x" + Integer.toHexString(documentNode.getSize()) + "]";
                } else {
                    str2 = "";
                }
                System.out.println(string + name + str2);
            }
            z2 = true;
        }
        if (z2) {
            return;
        }
        System.out.println(string + "(no children)");
    }
}
