package com.app.piapostolos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;
import com.app.piapostolos.ApsalmodyBeans.Apsalmody;
import com.app.piapostolos.ApsalmodyBeans.FavouritListEntity;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ApsalmodyMultiSelectListFavouriteActivity extends BaseActivity {
    private int id;
    private boolean isAdd;
    private mItems[] itemss;
    private ArrayAdapter<mItems> listAdapter;
    private ListView mainListView;
    ArrayList<String> checked = new ArrayList<>();
    String allSelected = "";
    private int addedSelectId = 0;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_listmulti_select);
        getWindow().addFlags(128);
        this.mainListView = (ListView) findViewById(R.id.mainListView);
        ((TextView) findViewById(R.id.textView1)).setTextSize(Float.valueOf(new checksim().checkfontsize(this)).floatValue());
        this.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.piapostolos.ApsalmodyMultiSelectListFavouriteActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                mItems mitems = (mItems) ApsalmodyMultiSelectListFavouriteActivity.this.listAdapter.getItem(i);
                mitems.toggleChecked();
                ((SelectViewHolder) view.getTag()).getCheckBox().setChecked(mitems.isChecked());
            }
        });
        this.itemss = (mItems[]) getLastNonConfigurationInstance();
        final ArrayList arrayList = new ArrayList();
        Apsalmody.readsheetAll(this);
        for (int i = 0; i < Apsalmody.allNames.length; i++) {
            arrayList.add(new mItems(Apsalmody.allNames[i]));
        }
        Intent intent = getIntent();
        this.isAdd = intent.getBooleanExtra("isAdd", true);
        if (!intent.getBooleanExtra("isAdd", true)) {
            int intExtra = intent.getIntExtra("id", 0);
            this.addedSelectId = intExtra;
            FavouritListEntity favouritListEntity = (FavouritListEntity) new SavePrefs((Activity) this, (Class<?>) FavouritListEntity.class).load();
            if (favouritListEntity != null) {
                for (int i2 = 0; i2 < favouritListEntity.getFavouriteList().get(intExtra).length; i2++) {
                    ((mItems) arrayList.get(favouritListEntity.getFavouriteList().get(intExtra)[i2] - 1)).setChecked(true);
                }
            }
        }
        SelectArralAdapter selectArralAdapter = new SelectArralAdapter(this, arrayList);
        this.listAdapter = selectArralAdapter;
        this.mainListView.setAdapter((ListAdapter) selectArralAdapter);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyMultiSelectListFavouriteActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FavouritListEntity favouritListEntity2;
                if (!ApsalmodyMultiSelectListFavouriteActivity.this.isAdd) {
                    int i3 = 0;
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        if (((mItems) arrayList.get(i4)).isChecked()) {
                            i3++;
                        }
                    }
                    int[] iArr = new int[i3];
                    int i5 = 0;
                    for (int i6 = 0; i6 < arrayList.size(); i6++) {
                        if (((mItems) arrayList.get(i6)).isChecked()) {
                            iArr[i5] = i6 + 1;
                            i5++;
                        }
                    }
                    if (i5 > 0 && (favouritListEntity2 = (FavouritListEntity) new SavePrefs((Activity) ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) FavouritListEntity.class).load()) != null) {
                        favouritListEntity2.getFavouriteList().set(ApsalmodyMultiSelectListFavouriteActivity.this.addedSelectId, iArr);
                        new SavePrefs((Activity) ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) FavouritListEntity.class).save(favouritListEntity2);
                        ApsalmodyMultiSelectListFavouriteActivity.this.id = favouritListEntity2.getFavouriteList().size() - 1;
                    }
                    Intent intent2 = new Intent(ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) ApsalmodyArrangeFavouriteActivity.class);
                    intent2.putExtra("id", ApsalmodyMultiSelectListFavouriteActivity.this.addedSelectId);
                    ApsalmodyMultiSelectListFavouriteActivity.this.startActivity(intent2);
                    ApsalmodyMultiSelectListFavouriteActivity.this.finish();
                    return;
                }
                int i7 = 0;
                for (int i8 = 0; i8 < arrayList.size(); i8++) {
                    if (((mItems) arrayList.get(i8)).isChecked()) {
                        i7++;
                    }
                }
                int[] iArr2 = new int[i7];
                int i9 = 0;
                for (int i10 = 0; i10 < arrayList.size(); i10++) {
                    if (((mItems) arrayList.get(i10)).isChecked()) {
                        iArr2[i9] = i10 + 1;
                        i9++;
                    }
                }
                if (i9 > 0) {
                    FavouritListEntity favouritListEntity3 = (FavouritListEntity) new SavePrefs((Activity) ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) FavouritListEntity.class).load();
                    if (favouritListEntity3 != null) {
                        favouritListEntity3.getFavouriteList().add(iArr2);
                        new SavePrefs((Activity) ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) FavouritListEntity.class).save(favouritListEntity3);
                        ApsalmodyMultiSelectListFavouriteActivity.this.id = favouritListEntity3.getFavouriteList().size() - 1;
                    } else {
                        ArrayList<int[]> arrayList2 = new ArrayList<>();
                        arrayList2.add(iArr2);
                        FavouritListEntity favouritListEntity4 = new FavouritListEntity();
                        favouritListEntity4.setFavouriteList(arrayList2);
                        new SavePrefs((Activity) ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) FavouritListEntity.class).save(favouritListEntity4);
                        ApsalmodyMultiSelectListFavouriteActivity.this.id = 0;
                    }
                    Intent intent3 = new Intent(ApsalmodyMultiSelectListFavouriteActivity.this, (Class<?>) ApsalmodyRenameFavouriteActivity.class);
                    intent3.putExtra("id", ApsalmodyMultiSelectListFavouriteActivity.this.id);
                    ApsalmodyMultiSelectListFavouriteActivity.this.startActivity(intent3);
                    ApsalmodyMultiSelectListFavouriteActivity.this.finish();
                    return;
                }
                Toast.makeText(ApsalmodyMultiSelectListFavouriteActivity.this.getApplicationContext(), R.string.plz_choose_content, 1).show();
            }
        });
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
                checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyMultiSelectListFavouriteActivity.SelectArralAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        CheckBox checkBox2 = (CheckBox) view2;
                        ((mItems) checkBox2.getTag()).setChecked(checkBox2.isChecked());
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
