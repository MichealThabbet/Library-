package com.app.piapostolos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class SearchActivity extends BaseActivity {
    private ArrayAdapter<String> adapter;
    private String[] books;
    private Button searchButton;
    private Button searchButton2;
    private EditText searchEditText;
    private Spinner spinner;
    private int id = 0;
    private boolean firstTime = true;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bible_search);
        this.searchButton = (Button) findViewById(R.id.btn_search);
        this.searchEditText = (EditText) findViewById(R.id.et_search);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner11);
        this.spinner = spinner;
        spinner.setPrompt(getString(R.string.bible_content));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.app.piapostolos.SearchActivity.1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup2, int i) {
                switch (i) {
                    case R.id.rb_all /* 2131297127 */:
                        SearchActivity.this.id = 0;
                        break;
                    case R.id.rb_new /* 2131297128 */:
                        SearchActivity.this.id = 2;
                        break;
                    case R.id.rb_old /* 2131297129 */:
                        SearchActivity.this.id = 1;
                        break;
                    case R.id.rb_private /* 2131297130 */:
                        SearchActivity.this.id = 3;
                        break;
                }
            }
        });
        try {
            InputStream inputStreamOpen = getAssets().open("biblenames.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            this.books = new String[73];
            for (int i = 0; i < strArrSplit.length; i++) {
                if (i < 73) {
                    this.books[i] = strArrSplit[i];
                }
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, this.books);
            this.adapter = arrayAdapter;
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinner.setAdapter((SpinnerAdapter) this.adapter);
        } catch (IOException unused) {
        }
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.SearchActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                if (SearchActivity.this.firstTime) {
                    SearchActivity.this.firstTime = false;
                } else {
                    ((RadioButton) SearchActivity.this.findViewById(R.id.rb_private)).setChecked(true);
                }
            }
        });
        this.searchButton.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.SearchActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!SearchActivity.this.searchEditText.getText().equals("")) {
                    if (SearchActivity.this.id == 3) {
                        Intent intent = new Intent(SearchActivity.this, (Class<?>) SearchResultActivity.class);
                        intent.putExtra(FirebaseAnalytics.Event.SEARCH, SearchActivity.this.searchEditText.getText().toString());
                        intent.putExtra("category", -1);
                        intent.putExtra("bible_id", SearchActivity.this.spinner.getSelectedItemPosition());
                        SearchActivity.this.startActivity(intent);
                        return;
                    }
                    Intent intent2 = new Intent(SearchActivity.this, (Class<?>) SearchResultActivity.class);
                    intent2.putExtra(FirebaseAnalytics.Event.SEARCH, SearchActivity.this.searchEditText.getText().toString());
                    intent2.putExtra("category", SearchActivity.this.id);
                    SearchActivity.this.startActivity(intent2);
                    return;
                }
                Toast.makeText(SearchActivity.this.getApplicationContext(), R.string.search_keyword, 1).show();
            }
        });
    }
}
