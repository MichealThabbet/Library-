package com.app.piapostolos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.app.piapostolos.Beans.AlamSettings;
import com.app.piapostolos.Others.SavePrefs;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class DallalSettingsActivity extends BaseActivity {
    private AlamSettings alamSettings;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_dallal_settings);
        this.alamSettings = new AlamSettings();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.c_red));
        arrayList.add(getString(R.string.c_gray));
        arrayList.add(getString(R.string.c_white));
        arrayList.add(getString(R.string.c_yellow));
        arrayList.add(getString(R.string.c_green));
        arrayList.add(getString(R.string.c_blue));
        arrayList.add(getString(R.string.c_orange));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.big_spinner_item_white, arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("15px");
        arrayList2.add("20px");
        arrayList2.add("25px");
        arrayList2.add("30px");
        arrayList2.add("35px");
        arrayList2.add("40px");
        arrayList2.add("45px");
        arrayList2.add("50px");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.big_spinner_item_white, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.sp_coptic_font_color);
        Spinner spinner2 = (Spinner) findViewById(R.id.sp_coptic_font_size);
        Spinner spinner3 = (Spinner) findViewById(R.id.sp_arabic_font_color);
        Spinner spinner4 = (Spinner) findViewById(R.id.sp_arabic_font_size);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner3.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapter2);
        spinner4.setAdapter((SpinnerAdapter) arrayAdapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.DallalSettingsActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#FF0033");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                    return;
                }
                if (i == 1) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#949494");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                    return;
                }
                if (i == 2) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#FFFFFF");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                    return;
                }
                if (i == 3) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#EEEE00");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                    return;
                }
                if (i == 4) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#61B329");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                } else if (i == 5) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#6600FF");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                } else if (i == 6) {
                    DallalSettingsActivity.this.alamSettings.setCopColor("#FCB514");
                    DallalSettingsActivity.this.alamSettings.setSpcopcol(i);
                }
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.DallalSettingsActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(15.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                    return;
                }
                if (i == 1) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(20.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                    return;
                }
                if (i == 2) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(25.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                    return;
                }
                if (i == 3) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(30.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                    return;
                }
                if (i == 4) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(35.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                    return;
                }
                if (i == 5) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(40.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                } else if (i == 6) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(45.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                } else if (i == 7) {
                    DallalSettingsActivity.this.alamSettings.setCopSize(50.0f);
                    DallalSettingsActivity.this.alamSettings.setSpcopfon(i);
                }
            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.DallalSettingsActivity.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(15.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                    return;
                }
                if (i == 1) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(20.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                    return;
                }
                if (i == 2) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(25.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                    return;
                }
                if (i == 3) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(30.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                    return;
                }
                if (i == 4) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(35.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                    return;
                }
                if (i == 5) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(40.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                } else if (i == 6) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(45.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                } else if (i == 7) {
                    DallalSettingsActivity.this.alamSettings.setAraSize(50.0f);
                    DallalSettingsActivity.this.alamSettings.setSparafon(i);
                }
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.DallalSettingsActivity.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#FF0033");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                    return;
                }
                if (i == 1) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#949494");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                    return;
                }
                if (i == 2) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#FFFFFF");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                    return;
                }
                if (i == 3) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#EEEE00");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                    return;
                }
                if (i == 4) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#61B329");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                } else if (i == 5) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#6600FF");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                } else if (i == 6) {
                    DallalSettingsActivity.this.alamSettings.setAraColor("#FCB514");
                    DallalSettingsActivity.this.alamSettings.setSparacol(i);
                }
            }
        });
        AlamSettings alamSettings = (AlamSettings) new SavePrefs((Activity) this, (Class<?>) AlamSettings.class).load();
        if (alamSettings != null) {
            spinner.setSelection(alamSettings.getSpcopcol());
            spinner2.setSelection(alamSettings.getSpcopfon());
            spinner3.setSelection(alamSettings.getSparacol());
            spinner4.setSelection(alamSettings.getSparafon());
            return;
        }
        spinner.setSelection(6);
        spinner2.setSelection(2);
        spinner3.setSelection(2);
        spinner4.setSelection(2);
    }

    public void saveButtonAction(View view) {
        new SavePrefs((Activity) this, (Class<?>) AlamSettings.class).save(this.alamSettings);
        finish();
    }

    public void cancelButtonAction(View view) {
        finish();
    }
}
