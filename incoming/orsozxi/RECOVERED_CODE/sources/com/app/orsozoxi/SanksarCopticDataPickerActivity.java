package com.app.orsozoxi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.app.orsozoxi.KholgayBeans.CopticDate;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.util.ArrayList;
import net.fortuna.ical4j.model.property.RequestStatus;

/* loaded from: classes.dex */
public class SanksarCopticDataPickerActivity extends BaseActivity {
    Spinner copticDaysSpinner;
    Spinner copticMonths;
    private EditText yearEditText;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sanksar_picker_coptic);
        this.yearEditText = (EditText) findViewById(R.id.ed_coptic_year);
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.c_1));
        arrayList.add(getString(R.string.c_2));
        arrayList.add(getString(R.string.c_3));
        arrayList.add(getString(R.string.c_4));
        arrayList.add(getString(R.string.c_5));
        arrayList.add(getString(R.string.c_6));
        arrayList.add(getString(R.string.c_7));
        arrayList.add(getString(R.string.c_8));
        arrayList.add(getString(R.string.c_9));
        arrayList.add(getString(R.string.c_10));
        arrayList.add(getString(R.string.c_11));
        arrayList.add(getString(R.string.c_12));
        arrayList.add(getString(R.string.c_13));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.big_spinner_item_white, arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("1");
        arrayList2.add(RequestStatus.SUCCESS);
        arrayList2.add(RequestStatus.CLIENT_ERROR);
        arrayList2.add(RequestStatus.SCHEDULING_ERROR);
        arrayList2.add("5");
        arrayList2.add("6");
        arrayList2.add("7");
        arrayList2.add("8");
        arrayList2.add("9");
        arrayList2.add(ConstantsBase.PREF_SNOOZE_DEFAULT);
        arrayList2.add("11");
        arrayList2.add("12");
        arrayList2.add("13");
        arrayList2.add("14");
        arrayList2.add("15");
        arrayList2.add("16");
        arrayList2.add("17");
        arrayList2.add("18");
        arrayList2.add("19");
        arrayList2.add("20");
        arrayList2.add("21");
        arrayList2.add("22");
        arrayList2.add("23");
        arrayList2.add("24");
        arrayList2.add("25");
        arrayList2.add("26");
        arrayList2.add("27");
        arrayList2.add("28");
        arrayList2.add("29");
        arrayList2.add("30");
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("1");
        arrayList3.add(RequestStatus.SUCCESS);
        arrayList3.add(RequestStatus.CLIENT_ERROR);
        arrayList3.add(RequestStatus.SCHEDULING_ERROR);
        arrayList3.add("5");
        arrayList3.add("6");
        final ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.big_spinner_item_white, arrayList2);
        final ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.big_spinner_item_white, arrayList3);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.copticMonths = (Spinner) findViewById(R.id.sp_coptic_month);
        this.copticDaysSpinner = (Spinner) findViewById(R.id.sp_coptic_day);
        this.copticMonths.setAdapter((SpinnerAdapter) arrayAdapter);
        this.copticDaysSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        this.copticMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.SanksarCopticDataPickerActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 12) {
                    SanksarCopticDataPickerActivity.this.copticDaysSpinner.setAdapter((SpinnerAdapter) arrayAdapter3);
                    arrayAdapter3.notifyDataSetChanged();
                } else {
                    SanksarCopticDataPickerActivity.this.copticDaysSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
                    arrayAdapter2.notifyDataSetChanged();
                }
            }
        });
        this.copticDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.SanksarCopticDataPickerActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void saveButtonAction(View view) {
        if (this.yearEditText.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.choose_coptic_date, 1).show();
            return;
        }
        CopticDate copticDate = new CopticDate();
        copticDate.setYear(Integer.valueOf(this.yearEditText.getText().toString()).intValue());
        copticDate.setDay(this.copticDaysSpinner.getSelectedItemPosition() + 1);
        copticDate.setMonth(this.copticMonths.getSelectedItemPosition() + 1);
        app.setCopticDate(copticDate);
        finish();
    }

    public void cancelButtonAction(View view) {
        finish();
    }
}
