package com.app.orsozoxi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.app.orsozoxi.Beans.KholagyShamas;
import com.app.orsozoxi.Others.checksim;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class KholagyShamasMenuActivity extends BaseActivity {
    private ArrayAdapter<KholagyShamas> listAdapter;
    ArrayList<KholagyShamas> mKholagyShamasList = new ArrayList<>();
    private ListView mainListView;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_kholagy_shamas_menu);
        getWindow().addFlags(128);
        this.mainListView = (ListView) findViewById(R.id.mainListView);
        ((TextView) findViewById(R.id.textView1)).setTextSize(Float.valueOf(new checksim().checkfontsize(this)).floatValue());
        this.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.KholagyShamasMenuActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(KholagyShamasMenuActivity.this, (Class<?>) MainActivity_kholagy.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "all");
                intent.putExtra("eid_index_file_name", KholagyShamasMenuActivity.this.mKholagyShamasList.get(i).getFileName());
                KholagyShamasMenuActivity.this.startActivity(intent);
            }
        });
        setList();
        SelectArralAdapter selectArralAdapter = new SelectArralAdapter(this, this.mKholagyShamasList);
        this.listAdapter = selectArralAdapter;
        this.mainListView.setAdapter((ListAdapter) selectArralAdapter);
    }

    public void setList() {
        KholagyShamas kholagyShamas = new KholagyShamas();
        kholagyShamas.setDisplayName(getString(R.string.e_sanway));
        kholagyShamas.setFileName("mon_sanawy.xls");
        this.mKholagyShamasList.add(kholagyShamas);
        KholagyShamas kholagyShamas2 = new KholagyShamas();
        kholagyShamas2.setDisplayName(getString(R.string.e_nayroom_mon));
        kholagyShamas2.setFileName("mon_nayrouz.xls");
        this.mKholagyShamasList.add(kholagyShamas2);
        KholagyShamas kholagyShamas3 = new KholagyShamas();
        kholagyShamas3.setDisplayName(getString(R.string.e_saleb_mon));
        kholagyShamas3.setFileName("mon_salib.xls");
        this.mKholagyShamasList.add(kholagyShamas3);
        KholagyShamas kholagyShamas4 = new KholagyShamas();
        kholagyShamas4.setDisplayName(getString(R.string.e_kiahk_mon));
        kholagyShamas4.setFileName("mon_Kiak.xls");
        this.mKholagyShamasList.add(kholagyShamas4);
        KholagyShamas kholagyShamas5 = new KholagyShamas();
        kholagyShamas5.setDisplayName(getString(R.string.e_barmoon_melad_mon));
        kholagyShamas5.setFileName("mon_baramon_milad.xls");
        this.mKholagyShamasList.add(kholagyShamas5);
        KholagyShamas kholagyShamas6 = new KholagyShamas();
        kholagyShamas6.setDisplayName(getString(R.string.e_melad_mon));
        kholagyShamas6.setFileName("mon_basic_milad.xls");
        this.mKholagyShamasList.add(kholagyShamas6);
        KholagyShamas kholagyShamas7 = new KholagyShamas();
        kholagyShamas7.setDisplayName(getString(R.string.e_7etan_mon));
        kholagyShamas7.setFileName("mon_5etan.xls");
        this.mKholagyShamasList.add(kholagyShamas7);
        KholagyShamas kholagyShamas8 = new KholagyShamas();
        kholagyShamas8.setDisplayName(getString(R.string.e_barmoon_gotas));
        kholagyShamas8.setFileName("mon_baramon_ghetas.xls");
        this.mKholagyShamasList.add(kholagyShamas8);
        KholagyShamas kholagyShamas9 = new KholagyShamas();
        kholagyShamas9.setDisplayName(getString(R.string.e_gotas));
        kholagyShamas9.setFileName("mon_ghetas.xls");
        this.mKholagyShamasList.add(kholagyShamas9);
        KholagyShamas kholagyShamas10 = new KholagyShamas();
        kholagyShamas10.setDisplayName(getString(R.string.e_ors_kana_mon));
        kholagyShamas10.setFileName("mon_kana_elgalil.xls");
        this.mKholagyShamasList.add(kholagyShamas10);
        KholagyShamas kholagyShamas11 = new KholagyShamas();
        kholagyShamas11.setDisplayName(getString(R.string.e_jesus_inside_hekal_mon));
        kholagyShamas11.setFileName("mon_basic_Hakal.xls");
        this.mKholagyShamasList.add(kholagyShamas11);
        KholagyShamas kholagyShamas12 = new KholagyShamas();
        kholagyShamas12.setDisplayName(getString(R.string.e_younan_mon));
        kholagyShamas12.setFileName("mon_Younah.xls");
        this.mKholagyShamasList.add(kholagyShamas12);
        KholagyShamas kholagyShamas13 = new KholagyShamas();
        kholagyShamas13.setDisplayName(getString(R.string.e_soom_kbier_mon));
        kholagyShamas13.setFileName("mon_Kabir.xls");
        this.mKholagyShamasList.add(kholagyShamas13);
        KholagyShamas kholagyShamas14 = new KholagyShamas();
        kholagyShamas14.setDisplayName(getString(R.string.e_sabt_laazer));
        kholagyShamas14.setFileName("mon_laazer.xls");
        this.mKholagyShamasList.add(kholagyShamas14);
        KholagyShamas kholagyShamas15 = new KholagyShamas();
        kholagyShamas15.setDisplayName(getString(R.string.e_a7ad_shaneen_mon));
        kholagyShamas15.setFileName("mon_shaaneen.xls");
        this.mKholagyShamasList.add(kholagyShamas15);
        KholagyShamas kholagyShamas16 = new KholagyShamas();
        kholagyShamas16.setDisplayName(getString(R.string.e_khames_ahd));
        kholagyShamas16.setFileName("mon_el_aahd.xls");
        this.mKholagyShamasList.add(kholagyShamas16);
        KholagyShamas kholagyShamas17 = new KholagyShamas();
        kholagyShamas17.setDisplayName(getString(R.string.e_sabt_farah));
        kholagyShamas17.setFileName("mon_sabt_el_farah.xls");
        this.mKholagyShamasList.add(kholagyShamas17);
        KholagyShamas kholagyShamas18 = new KholagyShamas();
        kholagyShamas18.setDisplayName(getString(R.string.e_kyama_sood_ansara_mon));
        kholagyShamas18.setFileName("mon_kyama.xls");
        this.mKholagyShamasList.add(kholagyShamas18);
        KholagyShamas kholagyShamas19 = new KholagyShamas();
        kholagyShamas19.setDisplayName(getString(R.string.e_jesus_inside_masr_mon));
        kholagyShamas19.setFileName("mon_Masr.xls");
        this.mKholagyShamasList.add(kholagyShamas19);
        KholagyShamas kholagyShamas20 = new KholagyShamas();
        kholagyShamas20.setDisplayName(getString(R.string.e_soom_rosol_mon));
        kholagyShamas20.setFileName("mon_rosol.xls");
        this.mKholagyShamasList.add(kholagyShamas20);
        KholagyShamas kholagyShamas21 = new KholagyShamas();
        kholagyShamas21.setDisplayName(getString(R.string.e_rosol_mon));
        kholagyShamas21.setFileName("mon_3id_rosol.xls");
        this.mKholagyShamasList.add(kholagyShamas21);
        KholagyShamas kholagyShamas22 = new KholagyShamas();
        kholagyShamas22.setDisplayName(getString(R.string.e_beshara_mon));
        kholagyShamas22.setFileName("mon_bashara.xls");
        this.mKholagyShamasList.add(kholagyShamas22);
        KholagyShamas kholagyShamas23 = new KholagyShamas();
        kholagyShamas23.setDisplayName(getString(R.string.e_mary_mon));
        kholagyShamas23.setFileName("mon_Mary.xls");
        this.mKholagyShamasList.add(kholagyShamas23);
        KholagyShamas kholagyShamas24 = new KholagyShamas();
        kholagyShamas24.setDisplayName(getString(R.string.e_29));
        kholagyShamas24.setFileName("mon_29.xls");
        this.mKholagyShamasList.add(kholagyShamas24);
        KholagyShamas kholagyShamas25 = new KholagyShamas();
        kholagyShamas25.setDisplayName(getString(R.string.e_tagaly));
        kholagyShamas25.setFileName("mon_tagali.xls");
        this.mKholagyShamasList.add(kholagyShamas25);
    }

    private static class SelectArralAdapter extends ArrayAdapter<KholagyShamas> {
        private LayoutInflater inflater;

        public SelectArralAdapter(Context context, ArrayList<KholagyShamas> arrayList) {
            super(context, R.layout.simplerow, R.id.rowTextView, arrayList);
            this.inflater = LayoutInflater.from(context);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            KholagyShamas item = getItem(i);
            View viewInflate = this.inflater.inflate(R.layout.simplerow, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.rowTextView);
            ((CheckBox) viewInflate.findViewById(R.id.CheckBox01)).setVisibility(8);
            textView.setText(item.getDisplayName());
            return viewInflate;
        }
    }
}
