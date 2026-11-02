package org.apache.poi.hssf.dev;

import com.app.piapostolos.Views.CustomCopticTextView;
import com.facebook.internal.ServerProtocol;
import it.piapostolos.android.pinotes.R2;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.record.ArrayRecord;
import org.apache.poi.hssf.record.AutoFilterInfoRecord;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BackupRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BookBoolRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BottomMarginRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.CFHeaderRecord;
import org.apache.poi.hssf.record.CFRuleRecord;
import org.apache.poi.hssf.record.CalcCountRecord;
import org.apache.poi.hssf.record.CalcModeRecord;
import org.apache.poi.hssf.record.CodepageRecord;
import org.apache.poi.hssf.record.ColumnInfoRecord;
import org.apache.poi.hssf.record.ContinueRecord;
import org.apache.poi.hssf.record.CountryRecord;
import org.apache.poi.hssf.record.DBCellRecord;
import org.apache.poi.hssf.record.DSFRecord;
import org.apache.poi.hssf.record.DVALRecord;
import org.apache.poi.hssf.record.DVRecord;
import org.apache.poi.hssf.record.DateWindow1904Record;
import org.apache.poi.hssf.record.DefaultColWidthRecord;
import org.apache.poi.hssf.record.DefaultRowHeightRecord;
import org.apache.poi.hssf.record.DeltaRecord;
import org.apache.poi.hssf.record.DimensionsRecord;
import org.apache.poi.hssf.record.DrawingGroupRecord;
import org.apache.poi.hssf.record.DrawingRecordForBiffViewer;
import org.apache.poi.hssf.record.DrawingSelectionRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.ExtSSTRecord;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.ExternSheetRecord;
import org.apache.poi.hssf.record.ExternalNameRecord;
import org.apache.poi.hssf.record.FeatHdrRecord;
import org.apache.poi.hssf.record.FeatRecord;
import org.apache.poi.hssf.record.FilePassRecord;
import org.apache.poi.hssf.record.FileSharingRecord;
import org.apache.poi.hssf.record.FnGroupCountRecord;
import org.apache.poi.hssf.record.FontRecord;
import org.apache.poi.hssf.record.FooterRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.GridsetRecord;
import org.apache.poi.hssf.record.GutsRecord;
import org.apache.poi.hssf.record.HCenterRecord;
import org.apache.poi.hssf.record.HeaderRecord;
import org.apache.poi.hssf.record.HideObjRecord;
import org.apache.poi.hssf.record.HorizontalPageBreakRecord;
import org.apache.poi.hssf.record.HyperlinkRecord;
import org.apache.poi.hssf.record.IndexRecord;
import org.apache.poi.hssf.record.InterfaceEndRecord;
import org.apache.poi.hssf.record.InterfaceHdrRecord;
import org.apache.poi.hssf.record.IterationRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.LeftMarginRecord;
import org.apache.poi.hssf.record.MMSRecord;
import org.apache.poi.hssf.record.MergeCellsRecord;
import org.apache.poi.hssf.record.MulBlankRecord;
import org.apache.poi.hssf.record.MulRKRecord;
import org.apache.poi.hssf.record.NameCommentRecord;
import org.apache.poi.hssf.record.NameRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.record.PaneRecord;
import org.apache.poi.hssf.record.PasswordRecord;
import org.apache.poi.hssf.record.PasswordRev4Record;
import org.apache.poi.hssf.record.PrecisionRecord;
import org.apache.poi.hssf.record.PrintGridlinesRecord;
import org.apache.poi.hssf.record.PrintHeadersRecord;
import org.apache.poi.hssf.record.PrintSetupRecord;
import org.apache.poi.hssf.record.ProtectRecord;
import org.apache.poi.hssf.record.ProtectionRev4Record;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.RecalcIdRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RecordFormatException;
import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.RefModeRecord;
import org.apache.poi.hssf.record.RefreshAllRecord;
import org.apache.poi.hssf.record.RightMarginRecord;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SCLRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.SaveRecalcRecord;
import org.apache.poi.hssf.record.SelectionRecord;
import org.apache.poi.hssf.record.SharedFormulaRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.record.StyleRecord;
import org.apache.poi.hssf.record.SupBookRecord;
import org.apache.poi.hssf.record.TabIdRecord;
import org.apache.poi.hssf.record.TableRecord;
import org.apache.poi.hssf.record.TableStylesRecord;
import org.apache.poi.hssf.record.TextObjectRecord;
import org.apache.poi.hssf.record.TopMarginRecord;
import org.apache.poi.hssf.record.UnknownRecord;
import org.apache.poi.hssf.record.UseSelFSRecord;
import org.apache.poi.hssf.record.VCenterRecord;
import org.apache.poi.hssf.record.VerticalPageBreakRecord;
import org.apache.poi.hssf.record.WSBoolRecord;
import org.apache.poi.hssf.record.WindowOneRecord;
import org.apache.poi.hssf.record.WindowProtectRecord;
import org.apache.poi.hssf.record.WindowTwoRecord;
import org.apache.poi.hssf.record.WriteAccessRecord;
import org.apache.poi.hssf.record.WriteProtectRecord;
import org.apache.poi.hssf.record.chart.AreaFormatRecord;
import org.apache.poi.hssf.record.chart.AreaRecord;
import org.apache.poi.hssf.record.chart.AxisLineFormatRecord;
import org.apache.poi.hssf.record.chart.AxisOptionsRecord;
import org.apache.poi.hssf.record.chart.AxisParentRecord;
import org.apache.poi.hssf.record.chart.AxisRecord;
import org.apache.poi.hssf.record.chart.AxisUsedRecord;
import org.apache.poi.hssf.record.chart.BarRecord;
import org.apache.poi.hssf.record.chart.BeginRecord;
import org.apache.poi.hssf.record.chart.CatLabRecord;
import org.apache.poi.hssf.record.chart.CategorySeriesAxisRecord;
import org.apache.poi.hssf.record.chart.ChartEndBlockRecord;
import org.apache.poi.hssf.record.chart.ChartEndObjectRecord;
import org.apache.poi.hssf.record.chart.ChartFRTInfoRecord;
import org.apache.poi.hssf.record.chart.ChartFormatRecord;
import org.apache.poi.hssf.record.chart.ChartRecord;
import org.apache.poi.hssf.record.chart.ChartStartBlockRecord;
import org.apache.poi.hssf.record.chart.ChartStartObjectRecord;
import org.apache.poi.hssf.record.chart.DatRecord;
import org.apache.poi.hssf.record.chart.DataFormatRecord;
import org.apache.poi.hssf.record.chart.DefaultDataLabelTextPropertiesRecord;
import org.apache.poi.hssf.record.chart.EndRecord;
import org.apache.poi.hssf.record.chart.FontBasisRecord;
import org.apache.poi.hssf.record.chart.FontIndexRecord;
import org.apache.poi.hssf.record.chart.FrameRecord;
import org.apache.poi.hssf.record.chart.LegendRecord;
import org.apache.poi.hssf.record.chart.LineFormatRecord;
import org.apache.poi.hssf.record.chart.LinkedDataRecord;
import org.apache.poi.hssf.record.chart.ObjectLinkRecord;
import org.apache.poi.hssf.record.chart.PlotAreaRecord;
import org.apache.poi.hssf.record.chart.PlotGrowthRecord;
import org.apache.poi.hssf.record.chart.SeriesIndexRecord;
import org.apache.poi.hssf.record.chart.SeriesListRecord;
import org.apache.poi.hssf.record.chart.SeriesRecord;
import org.apache.poi.hssf.record.chart.SeriesTextRecord;
import org.apache.poi.hssf.record.chart.SeriesToChartGroupRecord;
import org.apache.poi.hssf.record.chart.SheetPropertiesRecord;
import org.apache.poi.hssf.record.chart.TextRecord;
import org.apache.poi.hssf.record.chart.TickRecord;
import org.apache.poi.hssf.record.chart.UnitsRecord;
import org.apache.poi.hssf.record.chart.ValueRangeRecord;
import org.apache.poi.hssf.record.pivottable.DataItemRecord;
import org.apache.poi.hssf.record.pivottable.ExtendedPivotTableViewFieldsRecord;
import org.apache.poi.hssf.record.pivottable.PageItemRecord;
import org.apache.poi.hssf.record.pivottable.StreamIDRecord;
import org.apache.poi.hssf.record.pivottable.ViewDefinitionRecord;
import org.apache.poi.hssf.record.pivottable.ViewFieldsRecord;
import org.apache.poi.hssf.record.pivottable.ViewSourceRecord;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public final class BiffViewer {
    private static final int DUMP_LINE_LEN = 16;
    static final char[] NEW_LINE_CHARS = System.getProperty("line.separator").toCharArray();
    private static final char[] COLUMN_SEPARATOR = " | ".toCharArray();

    private interface IBiffRecordListener {
        void processRecord(int i, int i2, int i3, int i4, byte[] bArr);
    }

    private static char getPrintableChar(byte b) {
        char c = (char) (b & 255);
        if (c < ' ' || c > '~') {
            return '.';
        }
        return c;
    }

    private BiffViewer() {
    }

    public static Record[] createRecords(InputStream inputStream, PrintStream printStream, BiffRecordListener biffRecordListener, boolean z) throws RecordFormatException, RecordInputStream.LeftoverDataException {
        boolean zHasNextRecord;
        ArrayList arrayList = new ArrayList();
        RecordInputStream recordInputStream = new RecordInputStream(inputStream);
        while (true) {
            try {
                zHasNextRecord = recordInputStream.hasNextRecord();
            } catch (RecordInputStream.LeftoverDataException e) {
                e.printStackTrace();
                System.err.println("Discarding " + recordInputStream.remaining() + " bytes and continuing");
                recordInputStream.readRemainder();
                zHasNextRecord = recordInputStream.hasNextRecord();
            }
            if (zHasNextRecord) {
                recordInputStream.nextRecord();
                if (recordInputStream.getSid() != 0) {
                    if (z) {
                        Record recordCreateRecord = createRecord(recordInputStream);
                        if (recordCreateRecord.getSid() != 60) {
                            arrayList.add(recordCreateRecord);
                            if (z) {
                                for (String str : biffRecordListener.getRecentHeaders()) {
                                    printStream.println(str);
                                }
                                printStream.print(recordCreateRecord.toString());
                            }
                        }
                    } else {
                        recordInputStream.readRemainder();
                    }
                    printStream.println();
                }
            } else {
                Record[] recordArr = new Record[arrayList.size()];
                arrayList.toArray(recordArr);
                return recordArr;
            }
        }
    }

    private static Record createRecord(RecordInputStream recordInputStream) {
        short sid = recordInputStream.getSid();
        if (sid == 34) {
            return new DateWindow1904Record(recordInputStream);
        }
        if (sid == 35) {
            return new ExternalNameRecord(recordInputStream);
        }
        if (sid == 60) {
            return new ContinueRecord(recordInputStream);
        }
        if (sid == 61) {
            return new WindowOneRecord(recordInputStream);
        }
        if (sid == 140) {
            return new CountryRecord(recordInputStream);
        }
        if (sid == 141) {
            return new HideObjRecord(recordInputStream);
        }
        if (sid == 156) {
            return new FnGroupCountRecord(recordInputStream);
        }
        if (sid == 157) {
            return new AutoFilterInfoRecord(recordInputStream);
        }
        switch (sid) {
            case 6:
                return new FormulaRecord(recordInputStream);
            case 10:
                return new EOFRecord(recordInputStream);
            case 12:
                return new CalcCountRecord(recordInputStream);
            case 13:
                return new CalcModeRecord(recordInputStream);
            case 14:
                return new PrecisionRecord(recordInputStream);
            case 15:
                return new RefModeRecord(recordInputStream);
            case 16:
                return new DeltaRecord(recordInputStream);
            case 17:
                return new IterationRecord(recordInputStream);
            case 18:
                return new ProtectRecord(recordInputStream);
            case 19:
                return new PasswordRecord(recordInputStream);
            case 20:
                return new HeaderRecord(recordInputStream);
            case 21:
                return new FooterRecord(recordInputStream);
            case 47:
                return new FilePassRecord(recordInputStream);
            case 49:
                return new FontRecord(recordInputStream);
            case 85:
                return new DefaultColWidthRecord(recordInputStream);
            case 95:
                return new SaveRecalcRecord(recordInputStream);
            case 125:
                return new ColumnInfoRecord(recordInputStream);
            case 146:
                return new PaletteRecord(recordInputStream);
            case 160:
                return new SCLRecord(recordInputStream);
            case 161:
                return new PrintSetupRecord(recordInputStream);
            case 176:
                return new ViewDefinitionRecord(recordInputStream);
            case 177:
                return new ViewFieldsRecord(recordInputStream);
            case 182:
                return new PageItemRecord(recordInputStream);
            case 189:
                return new MulRKRecord(recordInputStream);
            case 190:
                return new MulBlankRecord(recordInputStream);
            case 193:
                return new MMSRecord(recordInputStream);
            case 197:
                return new DataItemRecord(recordInputStream);
            case 213:
                return new StreamIDRecord(recordInputStream);
            case 215:
                return new DBCellRecord(recordInputStream);
            case 218:
                return new BookBoolRecord(recordInputStream);
            case 224:
                return new ExtendedFormatRecord(recordInputStream);
            case 225:
                return new InterfaceHdrRecord(recordInputStream);
            case 226:
                return InterfaceEndRecord.create(recordInputStream);
            case 227:
                return new ViewSourceRecord(recordInputStream);
            case 229:
                return new MergeCellsRecord(recordInputStream);
            case 235:
                return new DrawingGroupRecord(recordInputStream);
            case 236:
                return new DrawingRecordForBiffViewer(recordInputStream);
            case 237:
                return new DrawingSelectionRecord(recordInputStream);
            case 252:
                return new SSTRecord(recordInputStream);
            case 253:
                return new LabelSSTRecord(recordInputStream);
            case 255:
                return new ExtSSTRecord(recordInputStream);
            case 256:
                return new ExtendedPivotTableViewFieldsRecord(recordInputStream);
            case R2.attr.contentInsetStartWithNavigation /* 317 */:
                return new TabIdRecord(recordInputStream);
            case R2.attr.customDimension /* 352 */:
                return new UseSelFSRecord(recordInputStream);
            case R2.attr.customFloatValue /* 353 */:
                return new DSFRecord(recordInputStream);
            case R2.attr.expandActivityOverflowButtonDrawable /* 430 */:
                return new SupBookRecord(recordInputStream);
            case R2.attr.expanded /* 431 */:
                return new ProtectionRev4Record(recordInputStream);
            case R2.attr.expandedHintEnabled /* 432 */:
                return new CFHeaderRecord(recordInputStream);
            case R2.attr.expandedTitleGravity /* 433 */:
                return new CFRuleRecord(recordInputStream);
            case R2.attr.expandedTitleMargin /* 434 */:
                return new DVALRecord(recordInputStream);
            case R2.attr.expandedTitleMarginTop /* 438 */:
                return new TextObjectRecord(recordInputStream);
            case R2.attr.expandedTitleTextAppearance /* 439 */:
                return new RefreshAllRecord(recordInputStream);
            case R2.attr.extendMotionSpec /* 440 */:
                return new HyperlinkRecord(recordInputStream);
            case R2.attr.fabCradleMargin /* 444 */:
                return new PasswordRev4Record(recordInputStream);
            case R2.attr.fabCradleVerticalOffset /* 446 */:
                return new DVRecord(recordInputStream);
            case R2.attr.fab_addButtonColorNormal /* 449 */:
                return new RecalcIdRecord(recordInputStream);
            case 512:
                return new DimensionsRecord(recordInputStream);
            case 513:
                return new BlankRecord(recordInputStream);
            case 515:
                return new NumberRecord(recordInputStream);
            case R2.attr.hideAnimationBehavior /* 516 */:
                return new LabelRecord(recordInputStream);
            case R2.attr.hideMotionSpec /* 517 */:
                return new BoolErrRecord(recordInputStream);
            case R2.attr.hideOnScroll /* 519 */:
                return new StringRecord(recordInputStream);
            case R2.attr.hintAnimationEnabled /* 520 */:
                return new RowRecord(recordInputStream);
            case R2.attr.hintTextColor /* 523 */:
                return new IndexRecord(recordInputStream);
            case R2.attr.indicatorSize /* 545 */:
                return new ArrayRecord(recordInputStream);
            case R2.attr.isLightTheme /* 549 */:
                return new DefaultRowHeightRecord(recordInputStream);
            case R2.attr.itemShapeInsetEnd /* 566 */:
                return new TableRecord(recordInputStream);
            case R2.attr.itemTextAppearanceInactive /* 574 */:
                return new WindowTwoRecord(recordInputStream);
            case R2.attr.layout_dodgeInsetEdges /* 638 */:
                return new RKRecord(recordInputStream);
            case R2.attr.lineHeight /* 659 */:
                return new StyleRecord(recordInputStream);
            case R2.attr.tabIconTintMode /* 1054 */:
                return new FormatRecord(recordInputStream);
            case R2.bool.mtrl_btn_textappearance_all_caps /* 1212 */:
                return new SharedFormulaRecord(recordInputStream);
            case R2.dimen.sp_timepicker_text_inset_inner /* 2057 */:
                return new BOFRecord(recordInputStream);
            case R2.drawable.abc_ic_menu_overflow_material /* 2128 */:
                return new ChartFRTInfoRecord(recordInputStream);
            case R2.drawable.abc_ic_menu_selectall_mtrl_alpha /* 2130 */:
                return new ChartStartBlockRecord(recordInputStream);
            case R2.drawable.abc_ic_menu_share_mtrl_alpha /* 2131 */:
                return new ChartEndBlockRecord(recordInputStream);
            case R2.drawable.abc_ic_search_api_material /* 2132 */:
                return new ChartStartObjectRecord(recordInputStream);
            case R2.drawable.abc_ic_search_api_mtrl_alpha /* 2133 */:
                return new ChartEndObjectRecord(recordInputStream);
            case R2.drawable.abc_ic_star_black_16dp /* 2134 */:
                return new CatLabRecord(recordInputStream);
            case 2151:
                return new FeatHdrRecord(recordInputStream);
            case R2.drawable.abc_list_selector_disabled_holo_dark /* 2152 */:
                return new FeatRecord(recordInputStream);
            case R2.drawable.abc_textfield_search_material /* 2190 */:
                return new TableStylesRecord(recordInputStream);
            case R2.drawable.bg_card /* 2196 */:
                return new NameCommentRecord(recordInputStream);
            case 4097:
                return new UnitsRecord(recordInputStream);
            case 4098:
                return new ChartRecord(recordInputStream);
            case 4099:
                return new SeriesRecord(recordInputStream);
            case 4102:
                return new DataFormatRecord(recordInputStream);
            case 4103:
                return new LineFormatRecord(recordInputStream);
            case 4106:
                return new AreaFormatRecord(recordInputStream);
            case 4109:
                return new SeriesTextRecord(recordInputStream);
            case 4116:
                return new ChartFormatRecord(recordInputStream);
            case 4117:
                return new LegendRecord(recordInputStream);
            case 4118:
                return new SeriesListRecord(recordInputStream);
            case 4119:
                return new BarRecord(recordInputStream);
            case R2.style.Base_V21_ThemeOverlay_AppCompat_Dialog /* 4122 */:
                return new AreaRecord(recordInputStream);
            case R2.style.Base_V21_Theme_AppCompat_Light /* 4125 */:
                return new AxisRecord(recordInputStream);
            case R2.style.Base_V21_Theme_AppCompat_Light_Dialog /* 4126 */:
                return new TickRecord(recordInputStream);
            case R2.style.Base_V21_Theme_MaterialComponents /* 4127 */:
                return new ValueRangeRecord(recordInputStream);
            case R2.style.Base_V21_Theme_MaterialComponents_Dialog /* 4128 */:
                return new CategorySeriesAxisRecord(recordInputStream);
            case R2.style.Base_V21_Theme_MaterialComponents_Light /* 4129 */:
                return new AxisLineFormatRecord(recordInputStream);
            case R2.style.Base_V22_Theme_AppCompat_Light /* 4132 */:
                return new DefaultDataLabelTextPropertiesRecord(recordInputStream);
            case R2.style.Base_V23_Theme_AppCompat /* 4133 */:
                return new TextRecord(recordInputStream);
            case R2.style.Base_V23_Theme_AppCompat_Light /* 4134 */:
                return new FontIndexRecord(recordInputStream);
            case R2.style.Base_V26_Theme_AppCompat /* 4135 */:
                return new ObjectLinkRecord(recordInputStream);
            case R2.style.Base_V7_Widget_AppCompat_EditText /* 4146 */:
                return new FrameRecord(recordInputStream);
            case R2.style.Base_V7_Widget_AppCompat_Toolbar /* 4147 */:
                return new BeginRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_ActionBar /* 4148 */:
                return new EndRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_ActionBar_Solid /* 4149 */:
                return new PlotAreaRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_ButtonBar_AlertDialog /* 4161 */:
                return new AxisParentRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_Button_ButtonBar_AlertDialog /* 4164 */:
                return new SheetPropertiesRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_Button_Colored /* 4165 */:
                return new SeriesToChartGroupRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_Button_Small /* 4166 */:
                return new AxisUsedRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_Light_ActionBar_TabBar /* 4177 */:
                return new LinkedDataRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_ProgressBar_Horizontal /* 4192 */:
                return new FontBasisRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_RatingBar_Indicator /* 4194 */:
                return new AxisOptionsRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_RatingBar_Small /* 4195 */:
                return new DatRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_SearchView /* 4196 */:
                return new PlotGrowthRecord(recordInputStream);
            case R2.style.Base_Widget_AppCompat_SearchView_ActionBar /* 4197 */:
                return new SeriesIndexRecord(recordInputStream);
            default:
                switch (sid) {
                    case 23:
                        return new ExternSheetRecord(recordInputStream);
                    case 24:
                        return new NameRecord(recordInputStream);
                    case 25:
                        return new WindowProtectRecord(recordInputStream);
                    case 26:
                        return new VerticalPageBreakRecord(recordInputStream);
                    case 27:
                        return new HorizontalPageBreakRecord(recordInputStream);
                    case 28:
                        return new NoteRecord(recordInputStream);
                    case 29:
                        return new SelectionRecord(recordInputStream);
                    default:
                        switch (sid) {
                            case 38:
                                return new LeftMarginRecord(recordInputStream);
                            case 39:
                                return new RightMarginRecord(recordInputStream);
                            case 40:
                                return new TopMarginRecord(recordInputStream);
                            case 41:
                                return new BottomMarginRecord(recordInputStream);
                            case 42:
                                return new PrintHeadersRecord(recordInputStream);
                            case 43:
                                return new PrintGridlinesRecord(recordInputStream);
                            default:
                                switch (sid) {
                                    case 64:
                                        return new BackupRecord(recordInputStream);
                                    case 65:
                                        return new PaneRecord(recordInputStream);
                                    case 66:
                                        return new CodepageRecord(recordInputStream);
                                    default:
                                        switch (sid) {
                                            case 91:
                                                return new FileSharingRecord(recordInputStream);
                                            case 92:
                                                return new WriteAccessRecord(recordInputStream);
                                            case 93:
                                                return new ObjRecord(recordInputStream);
                                            default:
                                                switch (sid) {
                                                    case 128:
                                                        return new GutsRecord(recordInputStream);
                                                    case 129:
                                                        return new WSBoolRecord(recordInputStream);
                                                    case 130:
                                                        return new GridsetRecord(recordInputStream);
                                                    case 131:
                                                        return new HCenterRecord(recordInputStream);
                                                    case 132:
                                                        return new VCenterRecord(recordInputStream);
                                                    case 133:
                                                        return new BoundSheetRecord(recordInputStream);
                                                    case 134:
                                                        return new WriteProtectRecord(recordInputStream);
                                                    default:
                                                        return new UnknownRecord(recordInputStream);
                                                }
                                        }
                                }
                        }
                }
        }
    }

    private static final class CommandArgs {
        private final boolean _biffhex;
        private final File _file;
        private final boolean _noHeader;
        private final boolean _noint;
        private final boolean _out;
        private final boolean _rawhex;

        private CommandArgs(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, File file) {
            this._biffhex = z;
            this._noint = z2;
            this._out = z3;
            this._rawhex = z4;
            this._file = file;
            this._noHeader = z5;
        }

        public static CommandArgs parse(String[] strArr) throws CommandParseException {
            int length = strArr.length;
            File file = null;
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                if (str.startsWith("--")) {
                    if ("--biffhex".equals(str)) {
                        z = true;
                    } else if ("--noint".equals(str)) {
                        z2 = true;
                    } else if ("--out".equals(str)) {
                        z3 = true;
                    } else if ("--escher".equals(str)) {
                        System.setProperty("poi.deserialize.escher", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    } else if ("--rawhex".equals(str)) {
                        z4 = true;
                    } else {
                        if (!"--noheader".equals(str)) {
                            throw new CommandParseException("Unexpected option '" + str + "'");
                        }
                        z5 = true;
                    }
                } else {
                    file = new File(str);
                    if (!file.exists()) {
                        throw new CommandParseException("Specified file '" + str + "' does not exist");
                    }
                    if (i + 1 < length) {
                        throw new CommandParseException("File name must be the last arg");
                    }
                }
            }
            if (file == null) {
                throw new CommandParseException("Biff viewer needs a filename");
            }
            return new CommandArgs(z, z2, z3, z4, z5, file);
        }

        public boolean shouldDumpBiffHex() {
            return this._biffhex;
        }

        public boolean shouldDumpRecordInterpretations() {
            return !this._noint;
        }

        public boolean shouldOutputToFile() {
            return this._out;
        }

        public boolean shouldOutputRawHexOnly() {
            return this._rawhex;
        }

        public boolean suppressHeader() {
            return this._noHeader;
        }

        public File getFile() {
            return this._file;
        }
    }

    private static final class CommandParseException extends Exception {
        public CommandParseException(String str) {
            super(str);
        }
    }

    public static void main(String[] strArr) throws IOException {
        PrintStream printStream;
        try {
            CommandArgs commandArgs = CommandArgs.parse(strArr);
            try {
                if (commandArgs.shouldOutputToFile()) {
                    printStream = new PrintStream(new FileOutputStream(commandArgs.getFile().getAbsolutePath() + ".out"));
                } else {
                    printStream = System.out;
                }
                DocumentInputStream documentInputStreamCreateDocumentInputStream = new POIFSFileSystem(new FileInputStream(commandArgs.getFile())).createDocumentInputStream("Workbook");
                if (commandArgs.shouldOutputRawHexOnly()) {
                    byte[] bArr = new byte[documentInputStreamCreateDocumentInputStream.available()];
                    documentInputStreamCreateDocumentInputStream.read(bArr);
                    HexDump.dump(bArr, 0L, System.out, 0);
                } else {
                    boolean zShouldDumpRecordInterpretations = commandArgs.shouldDumpRecordInterpretations();
                    BiffRecordListener biffRecordListener = new BiffRecordListener(commandArgs.shouldDumpBiffHex() ? new OutputStreamWriter(printStream) : null, zShouldDumpRecordInterpretations, commandArgs.suppressHeader());
                    createRecords(new BiffDumpingStream(documentInputStreamCreateDocumentInputStream, biffRecordListener), printStream, biffRecordListener, zShouldDumpRecordInterpretations);
                }
                printStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (CommandParseException e2) {
            e2.printStackTrace();
        }
    }

    private static final class BiffRecordListener implements IBiffRecordListener {
        private final List<String> _headers = new ArrayList();
        private final Writer _hexDumpWriter;
        private final boolean _noHeader;
        private final boolean _zeroAlignEachRecord;

        public BiffRecordListener(Writer writer, boolean z, boolean z2) {
            this._hexDumpWriter = writer;
            this._zeroAlignEachRecord = z;
            this._noHeader = z2;
        }

        @Override // org.apache.poi.hssf.dev.BiffViewer.IBiffRecordListener
        public void processRecord(int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
            String recordDetails = formatRecordDetails(i, i3, i4, i2);
            if (!this._noHeader) {
                this._headers.add(recordDetails);
            }
            Writer writer = this._hexDumpWriter;
            if (writer != null) {
                try {
                    writer.write(recordDetails);
                    writer.write(BiffViewer.NEW_LINE_CHARS);
                    BiffViewer.hexDumpAligned(writer, bArr, i4 + 4, i, this._zeroAlignEachRecord);
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public String[] getRecentHeaders() {
            String[] strArr = new String[this._headers.size()];
            this._headers.toArray(strArr);
            this._headers.clear();
            return strArr;
        }

        private static String formatRecordDetails(int i, int i2, int i3, int i4) {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append("Offset=");
            stringBuffer.append(HexDump.intToHex(i));
            stringBuffer.append("(");
            stringBuffer.append(i);
            stringBuffer.append(")");
            stringBuffer.append(" recno=");
            stringBuffer.append(i4);
            stringBuffer.append(" sid=");
            stringBuffer.append(HexDump.shortToHex(i2));
            stringBuffer.append(" size=");
            stringBuffer.append(HexDump.shortToHex(i3));
            stringBuffer.append("(");
            stringBuffer.append(i3);
            stringBuffer.append(")");
            return stringBuffer.toString();
        }
    }

    private static final class BiffDumpingStream extends InputStream {
        private boolean _innerHasReachedEOF;
        private final DataInputStream _is;
        private final IBiffRecordListener _listener;
        private final byte[] _data = new byte[8228];
        private int _recordCounter = 0;
        private int _overallStreamPos = 0;
        private int _currentSize = 0;
        private int _currentPos = 0;

        public BiffDumpingStream(InputStream inputStream, IBiffRecordListener iBiffRecordListener) {
            this._is = new DataInputStream(inputStream);
            this._listener = iBiffRecordListener;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (this._currentPos >= this._currentSize) {
                fillNextBuffer();
            }
            int i = this._currentPos;
            if (i >= this._currentSize) {
                return -1;
            }
            int i2 = this._data[i] & 255;
            this._currentPos = i + 1;
            this._overallStreamPos++;
            formatBufferIfAtEndOfRec();
            return i2;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (this._currentPos >= this._currentSize) {
                fillNextBuffer();
            }
            int i3 = this._currentPos;
            int i4 = this._currentSize;
            if (i3 >= i4) {
                return -1;
            }
            int i5 = i4 - i3;
            if (i2 > i5) {
                System.err.println("Unexpected request to read past end of current biff record");
                i2 = i5;
            }
            System.arraycopy(this._data, this._currentPos, bArr, i, i2);
            this._currentPos += i2;
            this._overallStreamPos += i2;
            formatBufferIfAtEndOfRec();
            return i2;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return (this._currentSize - this._currentPos) + this._is.available();
        }

        private void fillNextBuffer() throws IOException {
            if (this._innerHasReachedEOF) {
                return;
            }
            int i = this._is.read();
            if (i == -1) {
                this._innerHasReachedEOF = true;
                return;
            }
            byte[] bArr = this._data;
            bArr[0] = (byte) i;
            this._is.readFully(bArr, 1, 3);
            short s = LittleEndian.getShort(this._data, 2);
            this._is.readFully(this._data, 4, s);
            this._currentPos = 0;
            this._currentSize = s + 4;
            this._recordCounter++;
        }

        private void formatBufferIfAtEndOfRec() {
            int i = this._currentPos;
            int i2 = this._currentSize;
            if (i != i2) {
                return;
            }
            short s = LittleEndian.getShort(this._data, 0);
            this._listener.processRecord(this._overallStreamPos - this._currentSize, this._recordCounter, s, i2 - 4, this._data);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this._is.close();
        }
    }

    static void hexDumpAligned(Writer writer, byte[] bArr, int i, int i2, boolean z) throws IOException {
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        int i7 = i2 + 0;
        int i8 = i + i7;
        int i9 = i7 % 16;
        int i10 = i8 % 16;
        if (z) {
            int i11 = i10 - i9;
            if (i11 < 0) {
                i11 += 16;
            }
            i4 = i11;
            i3 = 0;
        } else {
            i3 = i9;
            i4 = i10;
        }
        if (z) {
            i5 = (i8 - i4) - (i7 - i3);
        } else {
            i6 = i7 - i3;
            i5 = i8 - i4;
        }
        int i12 = 0 - i3;
        if (i6 == i5) {
            hexDumpLine(writer, bArr, i6, i12, i3, i4);
            return;
        }
        hexDumpLine(writer, bArr, i6, i12, i3, 16);
        while (true) {
            i6 += 16;
            i12 += 16;
            if (i6 >= i5) {
                break;
            } else {
                hexDumpLine(writer, bArr, i6, i12, 0, 16);
            }
        }
        if (i4 != 0) {
            hexDumpLine(writer, bArr, i6, i12, 0, i4);
        }
    }

    private static void hexDumpLine(Writer writer, byte[] bArr, int i, int i2, int i3, int i4) throws IOException {
        if (i3 >= i4) {
            throw new IllegalArgumentException("Bad start/end delta");
        }
        try {
            writeHex(writer, i, 8);
            writer.write(COLUMN_SEPARATOR);
            for (int i5 = 0; i5 < 16; i5++) {
                if (i5 > 0) {
                    writer.write(" ");
                }
                if (i5 >= i3 && i5 < i4) {
                    writeHex(writer, bArr[i2 + i5], 2);
                } else {
                    writer.write(CustomCopticTextView.TWO_CHINESE_BLANK);
                }
            }
            writer.write(COLUMN_SEPARATOR);
            for (int i6 = 0; i6 < 16; i6++) {
                if (i6 >= i3 && i6 < i4) {
                    writer.write(getPrintableChar(bArr[i2 + i6]));
                } else {
                    writer.write(" ");
                }
            }
            writer.write(NEW_LINE_CHARS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeHex(Writer writer, int i, int i2) throws IOException {
        char[] cArr = new char[i2];
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            int i4 = i & 15;
            cArr[i3] = (char) (i4 < 10 ? i4 + 48 : (i4 + 65) - 10);
            i >>= 4;
        }
        writer.write(cArr);
    }
}
