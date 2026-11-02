package com.app.orsozoxi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.app.orsozoxi.Beans.SaintPrefs;
import com.app.orsozoxi.Beans.Saints;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/* loaded from: classes.dex */
public class ApsalmodySaintsSelectionActivity extends BaseActivity {
    private int id;
    private boolean isAdd;
    private mItems[] itemss;
    private ArrayAdapter<mItems> listAdapter;
    private ListView mainListView;
    private ArrayList<mItems> planetList;
    private ArrayList<Saints> saintSavedList;
    ArrayList<String> checked = new ArrayList<>();
    String allSelected = "";
    private int addedSelectId = 0;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_saints_select);
        getWindow().addFlags(128);
        this.mainListView = (ListView) findViewById(R.id.mainListView);
        ((TextView) findViewById(R.id.textView1)).setTextSize(Float.valueOf(new checksim().checkfontsize(this)).floatValue());
        SaintPrefs saintPrefs = (SaintPrefs) new SavePrefs((Activity) this, (Class<?>) SaintPrefs.class).load();
        if (saintPrefs != null && saintPrefs.getSaintsList() != null) {
            this.saintSavedList = saintPrefs.getSaintsList();
        }
        this.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.ApsalmodySaintsSelectionActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                mItems mitems = (mItems) ApsalmodySaintsSelectionActivity.this.listAdapter.getItem(i);
                if (mitems.isByDefault()) {
                    return;
                }
                mitems.toggleChecked();
                ((SelectViewHolder) view.getTag()).getCheckBox().setChecked(mitems.isChecked());
            }
        });
        this.itemss = (mItems[]) getLastNonConfigurationInstance();
        this.planetList = new ArrayList<>();
        addSaints();
        SelectArralAdapter selectArralAdapter = new SelectArralAdapter(this, this.planetList);
        this.listAdapter = selectArralAdapter;
        this.mainListView.setAdapter((ListAdapter) selectArralAdapter);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.ApsalmodySaintsSelectionActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ApsalmodySaintsSelectionActivity.this.saveFavoriteSaintsList();
                ApsalmodySaintsSelectionActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveFavoriteSaintsList() {
        SavePrefs savePrefs = new SavePrefs((Activity) this, (Class<?>) SaintPrefs.class);
        ArrayList<Saints> arrayList = new ArrayList<>();
        for (int i = 0; i < this.planetList.size(); i++) {
            if (!this.planetList.get(i).isByDefault() && this.planetList.get(i).isChecked()) {
                Saints saints = new Saints();
                saints.setSaintId(this.planetList.get(i).getId());
                arrayList.add(saints);
            }
        }
        SaintPrefs saintPrefs = new SaintPrefs();
        saintPrefs.setSaintsList(arrayList);
        savePrefs.save(saintPrefs);
    }

    private void addSaints() {
        try {
            Iterator<Row> itRowIterator = new HSSFWorkbook(new POIFSFileSystem(getAssets().open("psalmodySanawy/Saints_Doxology.xls"))).getSheetAt(0).rowIterator();
            if (itRowIterator.hasNext()) {
                itRowIterator.next();
            }
            if (itRowIterator.hasNext()) {
                itRowIterator.next();
            }
            while (itRowIterator.hasNext()) {
                HSSFRow hSSFRow = (HSSFRow) itRowIterator.next();
                HSSFCell cell = hSSFRow.getCell(3);
                if (!TextUtils.isEmpty(cell.toString())) {
                    String strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), cell.toString());
                    String idFromFloatCell = getIdFromFloatCell(app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(0).toString()));
                    String strDecodeText2 = app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(1).toString());
                    mItems mitems = new mItems();
                    mitems.setId(idFromFloatCell);
                    mitems.setName(strDecodeText2);
                    if (strDecodeText.toLowerCase().contains("all")) {
                        mitems.setChecked(true);
                        mitems.setByDefault(true);
                        this.planetList.add(mitems);
                    } else if (isInPreference(idFromFloatCell)) {
                        mitems.setChecked(true);
                        mitems.setByDefault(false);
                        this.planetList.add(mitems);
                    } else {
                        mitems.setChecked(false);
                        mitems.setByDefault(false);
                        this.planetList.add(mitems);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isInPreference(String str) {
        if (this.saintSavedList == null) {
            return false;
        }
        for (int i = 0; i < this.saintSavedList.size(); i++) {
            if (this.saintSavedList.get(i).getSaintId().equals(str)) {
                return true;
            }
        }
        return false;
    }

    private String getIdFromFloatCell(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return "";
        }
        return ((int) Float.valueOf(str).floatValue()) + "";
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Products");
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 1) {
            for (int i = 0; i < this.checked.size(); i++) {
                Log.d("pos : ", "" + this.checked.get(i));
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private static class mItems {
        private boolean checked;
        private String id;
        private boolean isByDefault;
        private String name;

        public mItems() {
            this.name = "";
            this.checked = false;
        }

        public mItems(String str) {
            this.name = "";
            this.checked = false;
            this.name = str;
        }

        public mItems(String str, boolean z) {
            this.name = "";
            this.checked = false;
            this.name = str;
            this.checked = z;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public boolean isChecked() {
            return this.checked;
        }

        public void setChecked(boolean z) {
            this.checked = z;
        }

        public String toString() {
            return this.name;
        }

        public void toggleChecked() {
            this.checked = !this.checked;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public boolean isByDefault() {
            return this.isByDefault;
        }

        public void setByDefault(boolean z) {
            this.isByDefault = z;
        }
    }

    private static class SelectViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public SelectViewHolder() {
        }

        public SelectViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return this.checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return this.textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    private static class SelectArralAdapter extends ArrayAdapter<mItems> {
        private LayoutInflater inflater;

        public SelectArralAdapter(Context context, List<mItems> list) {
            super(context, R.layout.simplerow, R.id.rowTextView, list);
            this.inflater = LayoutInflater.from(context);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            CheckBox checkBox;
            TextView textView;
            mItems item = getItem(i);
            if (view == null) {
                view = this.inflater.inflate(R.layout.simplerow, (ViewGroup) null);
                textView = (TextView) view.findViewById(R.id.rowTextView);
                checkBox = (CheckBox) view.findViewById(R.id.CheckBox01);
                view.setTag(new SelectViewHolder(textView, checkBox));
                checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.ApsalmodySaintsSelectionActivity.SelectArralAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        CheckBox checkBox2 = (CheckBox) view2;
                        mItems mitems = (mItems) checkBox2.getTag();
                        if (!mitems.isByDefault()) {
                            mitems.setChecked(checkBox2.isChecked());
                        } else {
                            mitems.setChecked(true);
                            checkBox2.setChecked(true);
                        }
                    }
                });
            } else {
                SelectViewHolder selectViewHolder = (SelectViewHolder) view.getTag();
                checkBox = selectViewHolder.getCheckBox();
                textView = selectViewHolder.getTextView();
            }
            checkBox.setTag(item);
            checkBox.setChecked(item.isChecked());
            textView.setText(item.getName());
            return view;
        }
    }
}
