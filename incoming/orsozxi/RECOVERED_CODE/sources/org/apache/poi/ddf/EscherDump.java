package org.apache.poi.ddf;

import com.app.piapostolos.Views.CustomCopticTextView;
import it.orsozoxi.android.orsonotes.R2;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.InflaterInputStream;
import kotlin.jvm.internal.ShortCompanionObject;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.HexRead;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public final class EscherDump {
    public void dump(byte[] bArr, int i, int i2, PrintStream printStream) {
        EscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        int i3 = i;
        while (i3 < i + i2) {
            EscherRecord escherRecordCreateRecord = defaultEscherRecordFactory.createRecord(bArr, i3);
            int iFillFields = escherRecordCreateRecord.fillFields(bArr, i3, defaultEscherRecordFactory);
            printStream.println(escherRecordCreateRecord.toString());
            i3 += iFillFields;
        }
    }

    public void dumpOld(long j, InputStream inputStream, PrintStream printStream) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        String str;
        long j2;
        new StringBuffer();
        long j3 = j;
        while (j3 > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            short s = LittleEndian.readShort(inputStream);
            short s2 = LittleEndian.readShort(inputStream);
            int i = LittleEndian.readInt(inputStream);
            j3 -= 8;
            if (s2 != -3806) {
                switch (s2) {
                    case -4096:
                        str = "MsofbtDggContainer";
                        break;
                    case -4095:
                        str = "MsofbtBstoreContainer";
                        break;
                    case -4094:
                        str = "MsofbtDgContainer";
                        break;
                    case -4093:
                        str = "MsofbtSpgrContainer";
                        break;
                    case -4092:
                        str = "MsofbtSpContainer";
                        break;
                    case -4091:
                        str = "MsofbtSolverContainer";
                        break;
                    case -4090:
                        str = EscherDggRecord.RECORD_DESCRIPTION;
                        break;
                    case -4089:
                        str = EscherBSERecord.RECORD_DESCRIPTION;
                        break;
                    case -4088:
                        str = EscherDgRecord.RECORD_DESCRIPTION;
                        break;
                    case -4087:
                        str = EscherSpgrRecord.RECORD_DESCRIPTION;
                        break;
                    case -4086:
                        str = EscherSpRecord.RECORD_DESCRIPTION;
                        break;
                    case -4085:
                        str = "MsofbtOPT";
                        break;
                    case -4084:
                        str = "MsofbtTextbox";
                        break;
                    case -4083:
                        str = "MsofbtClientTextbox";
                        break;
                    case -4082:
                        str = "MsofbtAnchor";
                        break;
                    case -4081:
                        str = EscherChildAnchorRecord.RECORD_DESCRIPTION;
                        break;
                    case -4080:
                        str = EscherClientAnchorRecord.RECORD_DESCRIPTION;
                        break;
                    case -4079:
                        str = EscherClientDataRecord.RECORD_DESCRIPTION;
                        break;
                    case -4078:
                        str = "MsofbtConnectorRule";
                        break;
                    case -4077:
                        str = "MsofbtAlignRule";
                        break;
                    case -4076:
                        str = "MsofbtArcRule";
                        break;
                    case -4075:
                        str = "MsofbtClientRule";
                        break;
                    case -4074:
                        str = "MsofbtCLSID";
                        break;
                    case -4073:
                        str = "MsofbtCalloutRule";
                        break;
                    default:
                        switch (s2) {
                            case -3816:
                                str = "MsofbtRegroupItem";
                                break;
                            case -3815:
                                str = "MsofbtSelection";
                                break;
                            case -3814:
                                str = "MsofbtColorMRU";
                                break;
                            default:
                                switch (s2) {
                                    case -3811:
                                        str = "MsofbtDeletedPspl";
                                        break;
                                    case -3810:
                                        str = EscherSplitMenuColorsRecord.RECORD_DESCRIPTION;
                                        break;
                                    case -3809:
                                        str = "MsofbtOleObject";
                                        break;
                                    case -3808:
                                        str = "MsofbtColorScheme";
                                        break;
                                    default:
                                        if (s2 >= -4072 && s2 <= -3817) {
                                            str = "MsofbtBLIP";
                                            break;
                                        } else if ((s & 15) != 15) {
                                            str = "UNKNOWN ID";
                                            break;
                                        } else {
                                            str = "UNKNOWN container";
                                            break;
                                        }
                                }
                        }
                }
            } else {
                str = "MsofbtUDefProp";
            }
            stringBuffer.append(CustomCopticTextView.TWO_CHINESE_BLANK);
            stringBuffer.append(HexDump.toHex(s2));
            stringBuffer.append(CustomCopticTextView.TWO_CHINESE_BLANK);
            stringBuffer.append(str);
            stringBuffer.append(" [");
            stringBuffer.append(HexDump.toHex(s));
            stringBuffer.append(',');
            stringBuffer.append(HexDump.toHex(i));
            stringBuffer.append("]  instance: ");
            stringBuffer.append(HexDump.toHex((short) (s >> 4)));
            printStream.println(stringBuffer.toString());
            if (s2 == -4089 && 36 <= j3 && 36 <= i) {
                StringBuffer stringBuffer2 = new StringBuffer("    btWin32: ");
                byte b = (byte) inputStream.read();
                stringBuffer2.append(HexDump.toHex(b));
                stringBuffer2.append(getBlipType(b));
                stringBuffer2.append("  btMacOS: ");
                byte b2 = (byte) inputStream.read();
                stringBuffer2.append(HexDump.toHex(b2));
                stringBuffer2.append(getBlipType(b2));
                printStream.println(stringBuffer2.toString());
                printStream.println("    rgbUid:");
                HexDump.dump(inputStream, printStream, 0, 16);
                printStream.print("    tag: ");
                outHex(2, inputStream, printStream);
                printStream.println();
                printStream.print("    size: ");
                outHex(4, inputStream, printStream);
                printStream.println();
                printStream.print("    cRef: ");
                outHex(4, inputStream, printStream);
                printStream.println();
                printStream.print("    offs: ");
                outHex(4, inputStream, printStream);
                printStream.println();
                printStream.print("    usage: ");
                outHex(1, inputStream, printStream);
                printStream.println();
                printStream.print("    cbName: ");
                outHex(1, inputStream, printStream);
                printStream.println();
                printStream.print("    unused2: ");
                outHex(1, inputStream, printStream);
                printStream.println();
                printStream.print("    unused3: ");
                outHex(1, inputStream, printStream);
                printStream.println();
                j3 -= 36;
                i = 0;
            } else if (s2 == -4080 && 18 <= j3 && 18 <= i) {
                printStream.print("    Flag: ");
                outHex(2, inputStream, printStream);
                printStream.println();
                printStream.print("    Col1: ");
                outHex(2, inputStream, printStream);
                printStream.print("    dX1: ");
                outHex(2, inputStream, printStream);
                printStream.print("    Row1: ");
                outHex(2, inputStream, printStream);
                printStream.print("    dY1: ");
                outHex(2, inputStream, printStream);
                printStream.println();
                printStream.print("    Col2: ");
                outHex(2, inputStream, printStream);
                printStream.print("    dX2: ");
                outHex(2, inputStream, printStream);
                printStream.print("    Row2: ");
                outHex(2, inputStream, printStream);
                printStream.print("    dY2: ");
                outHex(2, inputStream, printStream);
                printStream.println();
                j3 -= 18;
                i -= 18;
            } else if (s2 == -4085 || s2 == -3806) {
                printStream.println("    PROPID        VALUE");
                int i2 = 0;
                while (true) {
                    int i3 = i2 + 6;
                    if (i >= i3 && j3 >= i3) {
                        short s3 = LittleEndian.readShort(inputStream);
                        int i4 = LittleEndian.readInt(inputStream);
                        i -= 6;
                        j3 -= 6;
                        printStream.print("    ");
                        printStream.print(HexDump.toHex(s3));
                        printStream.print(" (");
                        int i5 = s3 & 16383;
                        printStream.print(" " + i5);
                        if ((s3 & ShortCompanionObject.MIN_VALUE) == 0) {
                            int i6 = s3 & 16384;
                            if (i6 != 0) {
                                printStream.print(", fBlipID");
                            }
                            printStream.print(")  ");
                            printStream.print(HexDump.toHex(i4));
                            if (i6 == 0) {
                                printStream.print(" (");
                                printStream.print(dec1616(i4));
                                printStream.print(')');
                                printStream.print(" {" + propName((short) i5) + "}");
                            }
                            printStream.println();
                        } else {
                            printStream.print(", fComplex)  ");
                            printStream.print(HexDump.toHex(i4));
                            printStream.print(" - Complex prop len");
                            printStream.println(" {" + propName((short) i5) + "}");
                            i2 += i4;
                        }
                    }
                }
                while ((i2 & j3) > 0) {
                    int i7 = (int) j3;
                    short s4 = i2 > i7 ? (short) i7 : (short) i2;
                    HexDump.dump(inputStream, printStream, 0, s4);
                    i2 -= s4;
                    i -= s4;
                    j3 -= s4;
                }
            } else {
                if (s2 == -4078) {
                    printStream.print("    Connector rule: ");
                    printStream.print(LittleEndian.readInt(inputStream));
                    printStream.print("    ShapeID A: ");
                    printStream.print(LittleEndian.readInt(inputStream));
                    printStream.print("   ShapeID B: ");
                    printStream.print(LittleEndian.readInt(inputStream));
                    printStream.print("    ShapeID connector: ");
                    printStream.print(LittleEndian.readInt(inputStream));
                    printStream.print("   Connect pt A: ");
                    printStream.print(LittleEndian.readInt(inputStream));
                    printStream.print("   Connect pt B: ");
                    printStream.println(LittleEndian.readInt(inputStream));
                    i -= 24;
                    j2 = 24;
                } else if (s2 >= -4072 && s2 < -3817) {
                    printStream.println("    Secondary UID: ");
                    HexDump.dump(inputStream, printStream, 0, 16);
                    printStream.println("    Cache of size: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Boundary top: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Boundary left: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Boundary width: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Boundary height: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    X: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Y: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Cache of saved size: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream.println("    Compression Flag: " + HexDump.toHex((byte) inputStream.read()));
                    printStream.println("    Filter: " + HexDump.toHex((byte) inputStream.read()));
                    printStream.println("    Data (after decompression): ");
                    int i8 = i + (-50);
                    j3 -= 50;
                    int i9 = (int) j3;
                    int i10 = i8 > i9 ? (short) i9 : (short) i8;
                    byte[] bArr = new byte[i10];
                    int i11 = inputStream.read(bArr);
                    while (i11 != -1 && i11 < i10) {
                        i11 += inputStream.read(bArr, i11, i10);
                    }
                    HexDump.dump(new InflaterInputStream(new ByteArrayInputStream(bArr)), printStream, 0, -1);
                    i = i8 - i10;
                    j2 = i10;
                }
                j3 -= j2;
            }
            if (!((s & 15) == 15) || j3 < 0) {
                if (j3 >= 0) {
                    int i12 = (int) j3;
                    short s5 = i > i12 ? (short) i12 : (short) i;
                    if (s5 != 0) {
                        HexDump.dump(inputStream, printStream, 0, s5);
                        j3 -= s5;
                    }
                } else {
                    printStream.println(" >> OVERRUN <<");
                }
            } else if (i <= ((int) j3)) {
                printStream.println("            completed within");
            } else {
                printStream.println("            continued elsewhere");
            }
        }
    }

    /* renamed from: org.apache.poi.ddf.EscherDump$1PropName, reason: invalid class name */
    final class C1PropName {
        final int _id;
        final String _name;

        public C1PropName(int i, String str) {
            this._id = i;
            this._name = str;
        }
    }

    private String propName(short s) {
        C1PropName[] c1PropNameArr = {new C1PropName(4, "transform.rotation"), new C1PropName(119, "protection.lockrotation"), new C1PropName(120, "protection.lockaspectratio"), new C1PropName(121, "protection.lockposition"), new C1PropName(122, "protection.lockagainstselect"), new C1PropName(123, "protection.lockcropping"), new C1PropName(124, "protection.lockvertices"), new C1PropName(125, "protection.locktext"), new C1PropName(126, "protection.lockadjusthandles"), new C1PropName(127, "protection.lockagainstgrouping"), new C1PropName(128, "text.textid"), new C1PropName(129, "text.textleft"), new C1PropName(130, "text.texttop"), new C1PropName(131, "text.textright"), new C1PropName(132, "text.textbottom"), new C1PropName(133, "text.wraptext"), new C1PropName(134, "text.scaletext"), new C1PropName(135, "text.anchortext"), new C1PropName(136, "text.textflow"), new C1PropName(137, "text.fontrotation"), new C1PropName(138, "text.idofnextshape"), new C1PropName(139, "text.bidir"), new C1PropName(187, "text.singleclickselects"), new C1PropName(188, "text.usehostmargins"), new C1PropName(189, "text.rotatetextwithshape"), new C1PropName(190, "text.sizeshapetofittext"), new C1PropName(191, "text.sizetexttofitshape"), new C1PropName(192, "geotext.unicode"), new C1PropName(193, "geotext.rtftext"), new C1PropName(194, "geotext.alignmentoncurve"), new C1PropName(195, "geotext.defaultpointsize"), new C1PropName(196, "geotext.textspacing"), new C1PropName(197, "geotext.fontfamilyname"), new C1PropName(240, "geotext.reverseroworder"), new C1PropName(241, "geotext.hastexteffect"), new C1PropName(242, "geotext.rotatecharacters"), new C1PropName(243, "geotext.kerncharacters"), new C1PropName(244, "geotext.tightortrack"), new C1PropName(245, "geotext.stretchtofitshape"), new C1PropName(246, "geotext.charboundingbox"), new C1PropName(247, "geotext.scaletextonpath"), new C1PropName(248, "geotext.stretchcharheight"), new C1PropName(249, "geotext.nomeasurealongpath"), new C1PropName(250, "geotext.boldfont"), new C1PropName(251, "geotext.italicfont"), new C1PropName(252, "geotext.underlinefont"), new C1PropName(253, "geotext.shadowfont"), new C1PropName(254, "geotext.smallcapsfont"), new C1PropName(255, "geotext.strikethroughfont"), new C1PropName(256, "blip.cropfromtop"), new C1PropName(257, "blip.cropfrombottom"), new C1PropName(258, "blip.cropfromleft"), new C1PropName(259, "blip.cropfromright"), new C1PropName(260, "blip.bliptodisplay"), new C1PropName(261, "blip.blipfilename"), new C1PropName(262, "blip.blipflags"), new C1PropName(263, "blip.transparentcolor"), new C1PropName(264, "blip.contrastsetting"), new C1PropName(265, "blip.brightnesssetting"), new C1PropName(266, "blip.gamma"), new C1PropName(267, "blip.pictureid"), new C1PropName(268, "blip.doublemod"), new C1PropName(269, "blip.picturefillmod"), new C1PropName(270, "blip.pictureline"), new C1PropName(271, "blip.printblip"), new C1PropName(272, "blip.printblipfilename"), new C1PropName(273, "blip.printflags"), new C1PropName(R2.attr.contentInsetStart, "blip.nohittestpicture"), new C1PropName(R2.attr.contentInsetStartWithNavigation, "blip.picturegray"), new C1PropName(R2.attr.contentPadding, "blip.picturebilevel"), new C1PropName(R2.attr.contentPaddingBottom, "blip.pictureactive"), new C1PropName(R2.attr.contentPaddingEnd, "geometry.left"), new C1PropName(R2.attr.contentPaddingLeft, "geometry.top"), new C1PropName(R2.attr.contentPaddingRight, "geometry.right"), new C1PropName(R2.attr.contentPaddingStart, "geometry.bottom"), new C1PropName(R2.attr.contentPaddingTop, "geometry.shapepath"), new C1PropName(R2.attr.contentScrim, "geometry.vertices"), new C1PropName(R2.attr.contrast, "geometry.segmentinfo"), new C1PropName(R2.attr.controlBackground, "geometry.adjustvalue"), new C1PropName(R2.attr.coordinatorLayoutStyle, "geometry.adjust2value"), new C1PropName(R2.attr.cornerFamily, "geometry.adjust3value"), new C1PropName(R2.attr.cornerFamilyBottomLeft, "geometry.adjust4value"), new C1PropName(R2.attr.cornerFamilyBottomRight, "geometry.adjust5value"), new C1PropName(R2.attr.cornerFamilyTopLeft, "geometry.adjust6value"), new C1PropName(R2.attr.cornerFamilyTopRight, "geometry.adjust7value"), new C1PropName(R2.attr.cornerRadius, "geometry.adjust8value"), new C1PropName(R2.attr.cornerSize, "geometry.adjust9value"), new C1PropName(R2.attr.cornerSizeBottomLeft, "geometry.adjust10value"), new C1PropName(R2.attr.disableChildrenWhenDisabled, "geometry.shadowOK"), new C1PropName(R2.attr.disableDependentsState, "geometry.3dok"), new C1PropName(R2.attr.displayOptions, "geometry.lineok"), new C1PropName(R2.attr.divider, "geometry.geotextok"), new C1PropName(R2.attr.dividerHorizontal, "geometry.fillshadeshapeok"), new C1PropName(383, "geometry.fillok"), new C1PropName(384, "fill.filltype"), new C1PropName(R2.attr.dragDirection, "fill.fillcolor"), new C1PropName(R2.attr.dragScale, "fill.fillopacity"), new C1PropName(R2.attr.dragThreshold, "fill.fillbackcolor"), new C1PropName(R2.attr.drawPath, "fill.backopacity"), new C1PropName(R2.attr.drawableBottomCompat, "fill.crmod"), new C1PropName(R2.attr.drawableEndCompat, "fill.patterntexture"), new C1PropName(R2.attr.drawableLeftCompat, "fill.blipfilename"), new C1PropName(R2.attr.drawableRightCompat, "fill.blipflags"), new C1PropName(R2.attr.drawableSize, "fill.width"), new C1PropName(R2.attr.drawableStartCompat, "fill.height"), new C1PropName(R2.attr.drawableTint, "fill.angle"), new C1PropName(R2.attr.drawableTintMode, "fill.focus"), new C1PropName(R2.attr.drawableTopCompat, "fill.toleft"), new C1PropName(R2.attr.drawerArrowStyle, "fill.totop"), new C1PropName(R2.attr.dropDownListViewStyle, "fill.toright"), new C1PropName(400, "fill.tobottom"), new C1PropName(401, "fill.rectleft"), new C1PropName(402, "fill.recttop"), new C1PropName(403, "fill.rectright"), new C1PropName(404, "fill.rectbottom"), new C1PropName(405, "fill.dztype"), new C1PropName(406, "fill.shadepreset"), new C1PropName(407, "fill.shadecolors"), new C1PropName(408, "fill.originx"), new C1PropName(409, "fill.originy"), new C1PropName(410, "fill.shapeoriginx"), new C1PropName(411, "fill.shapeoriginy"), new C1PropName(412, "fill.shadetype"), new C1PropName(R2.attr.fabAnimationMode, "fill.filled"), new C1PropName(R2.attr.fabCradleMargin, "fill.hittestfill"), new C1PropName(R2.attr.fabCradleRoundedCornerRadius, "fill.shape"), new C1PropName(R2.attr.fabCradleVerticalOffset, "fill.userect"), new C1PropName(R2.attr.fabCustomSize, "fill.nofillhittest"), new C1PropName(R2.attr.fabSize, "linestyle.color"), new C1PropName(R2.attr.fab_addButtonColorNormal, "linestyle.opacity"), new C1PropName(R2.attr.fab_addButtonColorPressed, "linestyle.backcolor"), new C1PropName(R2.attr.fab_addButtonPlusIconColor, "linestyle.crmod"), new C1PropName(R2.attr.fab_addButtonSize, "linestyle.linetype"), new C1PropName(R2.attr.fab_addButtonStrokeVisible, "linestyle.fillblip"), new C1PropName(R2.attr.fab_colorDisabled, "linestyle.fillblipname"), new C1PropName(R2.attr.fab_colorNormal, "linestyle.fillblipflags"), new C1PropName(R2.attr.fab_colorPressed, "linestyle.fillwidth"), new C1PropName(R2.attr.fab_expandDirection, "linestyle.fillheight"), new C1PropName(R2.attr.fab_icon, "linestyle.filldztype"), new C1PropName(R2.attr.fab_labelStyle, "linestyle.linewidth"), new C1PropName(R2.attr.fab_labelsPosition, "linestyle.linemiterlimit"), new C1PropName(R2.attr.fab_plusIconColor, "linestyle.linestyle"), new C1PropName(R2.attr.fab_size, "linestyle.linedashing"), new C1PropName(R2.attr.fab_stroke_visible, "linestyle.linedashstyle"), new C1PropName(R2.attr.fab_title, "linestyle.linestartarrowhead"), new C1PropName(R2.attr.fastScrollEnabled, "linestyle.lineendarrowhead"), new C1PropName(R2.attr.fastScrollHorizontalThumbDrawable, "linestyle.linestartarrowwidth"), new C1PropName(R2.attr.fastScrollHorizontalTrackDrawable, "linestyle.lineestartarrowlength"), new C1PropName(R2.attr.fastScrollVerticalThumbDrawable, "linestyle.lineendarrowwidth"), new C1PropName(R2.attr.fastScrollVerticalTrackDrawable, "linestyle.lineendarrowlength"), new C1PropName(R2.attr.firstBaselineToTopHeight, "linestyle.linejoinstyle"), new C1PropName(R2.attr.floatingActionButtonStyle, "linestyle.lineendcapstyle"), new C1PropName(507, "linestyle.arrowheadsok"), new C1PropName(R2.attr.haloColor, "linestyle.anyline"), new C1PropName(R2.attr.haloRadius, "linestyle.hitlinetest"), new C1PropName(R2.attr.headerLayout, "linestyle.linefillshape"), new C1PropName(511, "linestyle.nolinedrawdash"), new C1PropName(512, "shadowstyle.type"), new C1PropName(513, "shadowstyle.color"), new C1PropName(514, "shadowstyle.highlight"), new C1PropName(515, "shadowstyle.crmod"), new C1PropName(R2.attr.hideAnimationBehavior, "shadowstyle.opacity"), new C1PropName(R2.attr.hideMotionSpec, "shadowstyle.offsetx"), new C1PropName(R2.attr.hideOnContentScroll, "shadowstyle.offsety"), new C1PropName(R2.attr.hideOnScroll, "shadowstyle.secondoffsetx"), new C1PropName(R2.attr.hintAnimationEnabled, "shadowstyle.secondoffsety"), new C1PropName(R2.attr.hintEnabled, "shadowstyle.scalextox"), new C1PropName(R2.attr.hintTextAppearance, "shadowstyle.scaleytox"), new C1PropName(R2.attr.hintTextColor, "shadowstyle.scalextoy"), new C1PropName(R2.attr.homeAsUpIndicator, "shadowstyle.scaleytoy"), new C1PropName(R2.attr.homeLayout, "shadowstyle.perspectivex"), new C1PropName(R2.attr.horizontalOffset, "shadowstyle.perspectivey"), new C1PropName(R2.attr.hoveredFocusedTranslationZ, "shadowstyle.weight"), new C1PropName(R2.attr.icon, "shadowstyle.originx"), new C1PropName(R2.attr.iconEndPadding, "shadowstyle.originy"), new C1PropName(R2.attr.itemTextAppearanceInactive, "shadowstyle.shadow"), new C1PropName(R2.attr.itemTextColor, "shadowstyle.shadowobsured"), new C1PropName(R2.attr.key, "perspective.type"), new C1PropName(R2.attr.keyPositionType, "perspective.offsetx"), new C1PropName(R2.attr.keyboardIcon, "perspective.offsety"), new C1PropName(R2.attr.keylines, "perspective.scalextox"), new C1PropName(R2.attr.labelBehavior, "perspective.scaleytox"), new C1PropName(R2.attr.labelStyle, "perspective.scalextoy"), new C1PropName(R2.attr.labelVisibilityMode, "perspective.scaleytox"), new C1PropName(R2.attr.lastBaselineToBottomHeight, "perspective.perspectivex"), new C1PropName(R2.attr.layout, "perspective.perspectivey"), new C1PropName(R2.attr.layoutDescription, "perspective.weight"), new C1PropName(R2.attr.layoutDuringTransition, "perspective.originx"), new C1PropName(R2.attr.layoutManager, "perspective.originy"), new C1PropName(R2.attr.layout_editor_absoluteX, "perspective.perspectiveon"), new C1PropName(R2.attr.layout_editor_absoluteY, "3d.specularamount"), new C1PropName(R2.attr.linearProgressIndicatorStyle, "3d.diffuseamount"), new C1PropName(R2.attr.listChoiceBackgroundIndicator, "3d.shininess"), new C1PropName(R2.attr.listChoiceIndicatorMultipleAnimated, "3d.edgethickness"), new C1PropName(R2.attr.listChoiceIndicatorSingleAnimated, "3d.extrudeforward"), new C1PropName(R2.attr.listDividerAlertDialog, "3d.extrudebackward"), new C1PropName(R2.attr.listItemLayout, "3d.extrudeplane"), new C1PropName(R2.attr.listLayout, "3d.extrusioncolor"), new C1PropName(R2.attr.layout_insetEdge, "3d.crmod"), new C1PropName(R2.attr.materialCalendarHeaderConfirmButton, "3d.3deffect"), new C1PropName(R2.attr.materialCalendarHeaderDivider, "3d.metallic"), new C1PropName(R2.attr.materialCalendarHeaderLayout, "3d.useextrusioncolor"), new C1PropName(R2.attr.materialCalendarHeaderSelection, "3d.lightface"), new C1PropName(R2.attr.materialCalendarHeaderTitle, "3dstyle.yrotationangle"), new C1PropName(R2.attr.materialCalendarHeaderToggleButton, "3dstyle.xrotationangle"), new C1PropName(R2.attr.materialCalendarMonth, "3dstyle.rotationaxisx"), new C1PropName(R2.attr.materialCalendarMonthNavigationButton, "3dstyle.rotationaxisy"), new C1PropName(R2.attr.materialCalendarStyle, "3dstyle.rotationaxisz"), new C1PropName(R2.attr.materialCalendarTheme, "3dstyle.rotationangle"), new C1PropName(R2.attr.materialCalendarYearNavigationButton, "3dstyle.rotationcenterx"), new C1PropName(R2.attr.materialCardViewStyle, "3dstyle.rotationcentery"), new C1PropName(R2.attr.materialCircleRadius, "3dstyle.rotationcenterz"), new C1PropName(R2.attr.materialClockStyle, "3dstyle.rendermode"), new C1PropName(R2.attr.materialThemeOverlay, "3dstyle.tolerance"), new C1PropName(R2.attr.materialTimePickerStyle, "3dstyle.xviewpoint"), new C1PropName(R2.attr.materialTimePickerTheme, "3dstyle.yviewpoint"), new C1PropName(R2.attr.maxAcceleration, "3dstyle.zviewpoint"), new C1PropName(R2.attr.maxActionInlineWidth, "3dstyle.originx"), new C1PropName(R2.attr.maxButtonHeight, "3dstyle.originy"), new C1PropName(R2.attr.maxCharacterCount, "3dstyle.skewangle"), new C1PropName(R2.attr.maxHeight, "3dstyle.skewamount"), new C1PropName(R2.attr.maxImageSize, "3dstyle.ambientintensity"), new C1PropName(R2.attr.maxLines, "3dstyle.keyx"), new C1PropName(R2.attr.maxVelocity, "3dstyle.keyy"), new C1PropName(R2.attr.maxWidth, "3dstyle.keyz"), new C1PropName(R2.attr.md_background_color, "3dstyle.keyintensity"), new C1PropName(R2.attr.md_btn_negative_selector, "3dstyle.fillx"), new C1PropName(R2.attr.md_btn_neutral_selector, "3dstyle.filly"), new C1PropName(R2.attr.md_btn_positive_selector, "3dstyle.fillz"), new C1PropName(R2.attr.md_btn_ripple_color, "3dstyle.fillintensity"), new C1PropName(R2.attr.minSeparation, "3dstyle.constrainrotation"), new C1PropName(R2.attr.minTouchTargetSize, "3dstyle.rotationcenterauto"), new C1PropName(R2.attr.minWidth, "3dstyle.parallel"), new C1PropName(R2.attr.mock_diagonalsColor, "3dstyle.keyharsh"), new C1PropName(R2.attr.mock_label, "3dstyle.fillharsh"), new C1PropName(R2.attr.mock_labelColor, "shape.master"), new C1PropName(R2.attr.mock_showLabel, "shape.connectorstyle"), new C1PropName(R2.attr.motionDebug, "shape.blackandwhitesettings"), new C1PropName(R2.attr.motionInterpolator, "shape.wmodepurebw"), new C1PropName(R2.attr.motionPathRotate, "shape.wmodebw"), new C1PropName(R2.attr.passwordToggleContentDescription, "shape.oleicon"), new C1PropName(R2.attr.passwordToggleDrawable, "shape.preferrelativeresize"), new C1PropName(R2.attr.passwordToggleEnabled, "shape.lockshapetype"), new C1PropName(R2.attr.passwordToggleTintMode, "shape.deleteattachedobject"), new C1PropName(R2.attr.pathMotionArc, "shape.backgroundshape"), new C1PropName(R2.attr.path_percent, "callout.callouttype"), new C1PropName(R2.attr.percentHeight, "callout.xycalloutgap"), new C1PropName(R2.attr.percentWidth, "callout.calloutangle"), new C1PropName(R2.attr.percentX, "callout.calloutdroptype"), new C1PropName(R2.attr.percentY, "callout.calloutdropspecified"), new C1PropName(R2.attr.perpendicularPath_percent, "callout.calloutlengthspecified"), new C1PropName(R2.attr.scrimBackground, "callout.iscallout"), new C1PropName(R2.attr.scrimVisibleHeightTrigger, "callout.calloutaccentbar"), new C1PropName(R2.attr.searchHintIcon, "callout.callouttextborder"), new C1PropName(R2.attr.searchIcon, "callout.calloutminusx"), new C1PropName(R2.attr.searchViewStyle, "callout.calloutminusy"), new C1PropName(R2.attr.seekBarIncrement, "callout.dropauto"), new C1PropName(R2.attr.seekBarPreferenceStyle, "callout.lengthspecified"), new C1PropName(896, "groupshape.shapename"), new C1PropName(R2.attr.selectable, "groupshape.description"), new C1PropName(R2.attr.selectableItemBackground, "groupshape.hyperlink"), new C1PropName(R2.attr.selectableItemBackgroundBorderless, "groupshape.wrappolygonvertices"), new C1PropName(R2.attr.selectionRequired, "groupshape.wrapdistleft"), new C1PropName(R2.attr.selectorSize, "groupshape.wrapdisttop"), new C1PropName(R2.attr.shapeAppearance, "groupshape.wrapdistright"), new C1PropName(R2.attr.shapeAppearanceLargeComponent, "groupshape.wrapdistbottom"), new C1PropName(R2.attr.shapeAppearanceMediumComponent, "groupshape.regroupid"), new C1PropName(R2.attr.spIconColor, "groupshape.editedwrap"), new C1PropName(R2.attr.spLandscapeButtonModeOkCancelButtonStyle, "groupshape.behinddocument"), new C1PropName(R2.attr.spLandscapeButtonModeSwitcherButtonStyle, "groupshape.ondblclicknotify"), new C1PropName(R2.attr.spLandscapeCancelActionDrawable, "groupshape.isbutton"), new C1PropName(R2.attr.spLandscapeOkActionDrawable, "groupshape.1dadjustment"), new C1PropName(R2.attr.spMaxDate, "groupshape.hidden"), new C1PropName(R2.attr.spMinDate, "groupshape.print")};
        for (int i = 0; i < 274; i++) {
            if (c1PropNameArr[i]._id == s) {
                return c1PropNameArr[i]._name;
            }
        }
        return "unknown property";
    }

    private static String getBlipType(byte b) {
        return EscherBSERecord.getBlipType(b);
    }

    private String dec1616(int i) {
        return (("" + ((int) ((short) (i >> 16)))) + '.') + ((int) ((short) (i & (-1))));
    }

    private void outHex(int i, InputStream inputStream, PrintStream printStream) throws IOException {
        if (i == 1) {
            printStream.print(HexDump.toHex((byte) inputStream.read()));
        } else if (i == 2) {
            printStream.print(HexDump.toHex(LittleEndian.readShort(inputStream)));
        } else {
            if (i == 4) {
                printStream.print(HexDump.toHex(LittleEndian.readInt(inputStream)));
                return;
            }
            throw new IOException("Unable to output variable of that width");
        }
    }

    public static void main(String[] strArr) {
        byte[] fromString = HexRead.readFromString("0F 00 00 F0 89 07 00 00 00 00 06 F0 18 00 00 00 05 04 00 00 02 00 00 00 05 00 00 00 01 00 00 00 01 00 00 00 05 00 00 00 4F 00 01 F0 2F 07 00 00 42 00 07 F0 B7 01 00 00 03 04 3F 14 AE 6B 0F 65 B0 48 BF 5E 94 63 80 E8 91 73 FF 00 93 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 8B 01 00 00 3F 14 AE 6B 0F 65 B0 48 BF 5E 94 63 80 E8 91 73 92 0E 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 59 01 00 00 00 FE 78 9C E3 9B C4 00 04 AC 77 D9 2F 32 08 32 FD E7 61 F8 FF 0F C8 FD 05 C5 30 19 10 90 63 90 FA 0F 06 0C 8C 0C 5C 70 19 43 30 EB 0E FB 05 86 85 0C DB 18 58 80 72 8C 70 16 0B 83 05 56 51 29 88 C9 60 D9 69 0C 6C 20 26 23 03 C8 74 B0 A8 0E 03 07 FB 45 56 C7 A2 CC C4 1C 06 66 A0 0D 2C 40 39 5E 86 4C 06 3D A0 4E 10 D0 60 D9 C8 58 CC E8 CF B0 80 61 3A 8A 7E 0D C6 23 AC 4F E0 E2 98 B6 12 2B 06 73 9D 12 E3 52 56 59 F6 08 8A CC 52 66 A3 50 FF 96 2B 94 E9 DF 4C A1 FE 2D 3A 03 AB 9F 81 C2 F0 A3 54 BF 0F 85 EE A7 54 FF 40 FB 7F A0 E3 9F D2 F4 4F 71 FE 19 58 FF 2B 31 7F 67 36 3B 25 4F 99 1B 4E 53 A6 5F 89 25 95 E9 C4 00 C7 83 12 F3 1F 26 35 4A D3 D2 47 0E 0A C3 41 8E C9 8A 52 37 DC 15 A1 D0 0D BC 4C 06 0C 2B 28 2C 13 28 D4 EF 43 61 5A A0 58 3F 85 71 E0 4B 69 9E 64 65 FE 39 C0 E5 22 30 1D 30 27 0E 74 3A 18 60 FD 4A CC B1 2C 13 7D 07 36 2D 2A 31 85 B2 6A 0D 74 1D 1D 22 4D 99 FE 60 0A F5 9B EC 1C 58 FD 67 06 56 3F 38 0D 84 3C A5 30 0E 28 D3 AF C4 A4 CA FA 44 7A 0D 65 6E 60 7F 4D A1 1B 24 58 F7 49 AF A5 CC 0D CC DF 19 FE 03 00 F0 B1 25 4D 42 00 07 F0 E1 01 00 00 03 04 39 50 BE 98 B0 6F 57 24 31 70 5D 23 2F 9F 10 66 FF 00 BD 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 B5 01 00 00 39 50 BE 98 B0 6F 57 24 31 70 5D 23 2F 9F 10 66 DA 03 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 83 01 00 00 00 FE 78 9C A5 52 BF 4B 42 51 14 3E F7 DC 77 7A 16 45 48 8B 3C 48 A8 16 15 0D 6C 88 D0 04 C3 40 A3 32 1C 84 96 08 21 04 A1 C5 5C A2 35 82 C0 35 6A AB 1C 6A 6B A8 24 5A 83 68 08 84 84 96 A2 86 A0 7F C2 86 5E E7 5E F5 41 E4 10 BC 03 1F E7 FB F1 CE B9 F7 F1 9E 7C 05 2E 7A 37 9B E0 45 7B 10 EC 6F 96 5F 1D 74 13 55 7E B0 6C 5D 20 60 C0 49 A2 9A BD 99 4F 50 83 1B 30 38 13 0E 33 60 A6 A7 6B B5 37 EB F4 10 FA 14 15 A0 B6 6B 37 0C 1E B3 49 73 5B A5 C2 26 48 3E C1 E0 6C 08 4A 30 C9 93 AA 02 B8 20 13 62 05 4E E1 E8 D7 7C C0 B8 14 95 5E BE B8 A7 CF 1E BE 55 2C 56 B9 78 DF 08 7E 88 4C 27 FF 7B DB FF 7A DD B7 1A 17 67 34 6A AE BA DA 35 D1 E7 72 BE FE EC 6E FE DA E5 7C 3D EC 7A DE 03 FD 50 06 0B 23 F2 0E F3 B2 A5 11 91 0D 4C B5 B5 F3 BF 94 C1 8F 24 F7 D9 6F 60 94 3B C9 9A F3 1C 6B E7 BB F0 2E 49 B2 25 2B C6 B1 EE 69 EE 15 63 4F 71 7D CE 85 CC C8 35 B9 C3 28 28 CE D0 5C 67 79 F2 4A A2 14 23 A4 38 43 73 9D 2D 69 2F C1 08 31 9F C5 5C 9B EB 7B C5 69 19 B3 B4 81 F3 DC E3 B4 8E 8B CC B3 94 53 5A E7 41 2A 63 9A AA 38 C5 3D 48 BB EC 57 59 6F 2B AD 73 1F 1D 60 92 AE 70 8C BB 8F CE 31 C1 3C 49 27 4A EB DC A4 5B 8C D1 0B 0E 73 37 E9 11 A7 99 C7 E8 41 69 B0 7F 00 96 F2 A7 E8 42 00 07 F0 B4 01 00 00 03 04 1A BA F9 D6 A9 B9 3A 03 08 61 E9 90 FF 7B 9E E6 FF 00 90 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 88 01 00 00 1A BA F9 D6 A9 B9 3A 03 08 61 E9 90 FF 7B 9E E6 12 0E 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 56 01 00 00 00 FE 78 9C E3 13 62 00 02 D6 BB EC 17 19 04 99 FE F3 30 FC FF 07 E4 FE 82 62 98 0C 08 C8 31 48 FD 07 03 06 46 06 2E B8 8C 21 98 75 87 FD 02 C3 42 86 6D 0C 2C 40 39 46 38 8B 85 C1 02 AB A8 14 C4 64 B0 EC 34 06 36 10 93 91 01 64 3A 58 54 87 81 83 FD 22 AB 63 51 66 62 0E 03 33 D0 06 16 A0 1C 2F 43 26 83 1E 50 27 08 68 B0 6C 64 2C 66 F4 67 58 C0 30 1D 45 BF 06 E3 11 D6 27 70 71 4C 5B 89 15 83 B9 4E 89 71 29 AB 2C 7B 04 45 66 29 B3 51 A8 7F CB 15 CA F4 6F A6 50 FF 16 9D 81 D5 CF 40 61 F8 51 AA DF 87 42 F7 53 AA 7F A0 FD 3F D0 F1 4F 69 FA A7 38 FF 0C AC FF 95 98 BF 33 9B 9D 92 A7 CC 0D A7 29 D3 AF C4 92 CA 74 62 80 E3 41 89 F9 0F 93 1A A5 69 E9 23 07 85 E1 20 C7 64 45 A9 1B EE 8A 50 E8 06 5E 26 03 86 15 14 96 09 14 EA F7 A1 30 2D 50 AC 9F C2 38 F0 A5 34 4F B2 32 FF 1C E0 72 11 98 0E 98 13 07 38 1D 28 31 C7 B2 4C F4 1D D8 B4 A0 C4 14 CA AA 35 D0 75 64 88 34 65 FA 83 29 D4 6F B2 73 60 F5 9F A1 54 FF 0E CA D3 40 C8 53 0A E3 E0 09 85 6E 50 65 7D 22 BD 86 32 37 B0 BF A6 D0 0D 12 AC FB A4 D7 52 E6 06 E6 EF 0C FF 01 97 1D 12 C7 42 00 07 F0 C3 01 00 00 03 04 BA 4C B6 23 BA 8B 27 BE C8 55 59 86 24 9F 89 D4 FF 00 9F 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 97 01 00 00 BA 4C B6 23 BA 8B 27 BE C8 55 59 86 24 9F 89 D4 AE 0E 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 65 01 00 00 00 FE 78 9C E3 5B C7 00 04 AC 77 D9 2F 32 08 32 FD E7 61 F8 FF 0F C8 FD 05 C5 30 19 10 90 63 90 FA 0F 06 0C 8C 0C 5C 70 19 43 30 EB 0E FB 05 86 85 0C DB 18 58 80 72 8C 70 16 0B 83 05 56 51 29 88 C9 60 D9 69 0C 6C 20 26 23 03 C8 74 B0 A8 0E 03 07 FB 45 56 C7 A2 CC C4 1C 06 66 A0 0D 2C 40 39 5E 86 4C 06 3D A0 4E 10 D0 60 99 C6 B8 98 D1 9F 61 01 C3 74 14 FD 1A 8C 2B D8 84 B1 88 4B A5 A5 75 03 01 50 DF 59 46 77 46 0F A8 3C A6 AB 88 15 83 B9 5E 89 B1 8B D5 97 2D 82 22 B3 94 29 D5 BF E5 CA C0 EA DF AC 43 A1 FD 14 EA 67 A0 30 FC 28 D5 EF 43 A1 FB 7D 87 B8 FF 07 3A FE 07 3A FD 53 EA 7E 0A C3 4F 89 F9 0E 73 EA 69 79 CA DC 70 8A 32 FD 4A 2C 5E 4C DF 87 7A 3C BC E0 A5 30 1E 3E 31 C5 33 AC A0 30 2F 52 A8 DF 87 C2 30 A4 54 3F A5 65 19 85 65 A9 12 D3 2B 16 0D 8A CB 13 4A F3 E3 27 E6 09 03 9D 0E 06 58 BF 12 B3 13 CB C1 01 4E 8B 4A 4C 56 AC 91 03 5D 37 86 48 53 A6 3F 98 42 FD 26 3B 07 56 FF 99 1D 14 EA A7 CC 7E 70 1A 08 79 42 61 1C 3C A5 D0 0D 9C 6C C2 32 6B 29 73 03 DB 6B CA DC C0 F8 97 F5 AD CC 1A CA DC C0 F4 83 32 37 B0 A4 30 CE FC C7 48 99 1B FE 33 32 FC 07 00 6C CC 2E 23 33 00 0B F0 12 00 00 00 BF 00 08 00 08 00 81 01 09 00 00 08 C0 01 40 00 00 08 40 00 1E F1 10 00 00 00 0D 00 00 08 0C 00 00 08 17 00 00 08 F7 00 00 10                                              ");
        new EscherDump().dump(fromString, 0, fromString.length, System.out);
    }

    public void dump(int i, byte[] bArr, PrintStream printStream) {
        dump(bArr, 0, i, printStream);
    }
}
