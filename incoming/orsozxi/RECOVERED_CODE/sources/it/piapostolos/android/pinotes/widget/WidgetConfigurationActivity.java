package it.piapostolos.android.pinotes.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Category;
import it.piapostolos.android.pinotes.models.adapters.CategoryBaseAdapter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class WidgetConfigurationActivity extends Activity {
    private Spinner categorySpinner;
    private int mAppWidgetId = 0;
    private RadioGroup mRadioGroup;
    private String sqlCondition;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(0);
        setContentView(R.layout.activity_widget_configuration);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.widget_config_radiogroup);
        this.mRadioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: it.piapostolos.android.pinotes.widget.-$$Lambda$WidgetConfigurationActivity$XKBn0rzRxOX0utHpUx9ZDINul5k
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                this.f$0.lambda$onCreate$0$WidgetConfigurationActivity(radioGroup2, i);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.widget_config_spinner);
        this.categorySpinner = spinner;
        spinner.setEnabled(false);
        ArrayList<Category> categories = DbHelper.getInstance().getCategories();
        this.categorySpinner.setAdapter((SpinnerAdapter) new CategoryBaseAdapter(this, categories));
        ((Button) findViewById(R.id.widget_config_confirm)).setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.widget.-$$Lambda$WidgetConfigurationActivity$5UFxHUUnBTGPSGTOwhdTBLDN5P0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$1$WidgetConfigurationActivity(view);
            }
        });
        if (categories.size() == 0) {
            this.mRadioGroup.setVisibility(8);
            this.categorySpinner.setVisibility(8);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mAppWidgetId = extras.getInt("appWidgetId", 0);
        }
        if (this.mAppWidgetId == 0) {
            finish();
        }
    }

    public /* synthetic */ void lambda$onCreate$0$WidgetConfigurationActivity(RadioGroup radioGroup, int i) {
        if (i == 3130) {
            this.categorySpinner.setEnabled(true);
            return;
        }
        if (i == 3132) {
            this.categorySpinner.setEnabled(false);
            return;
        }
        LogDelegate.e("Wrong element choosen: " + i);
    }

    public /* synthetic */ void lambda$onCreate$1$WidgetConfigurationActivity(View view) {
        if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.widget_config_notes) {
            this.sqlCondition = " WHERE archived IS NOT 1 AND trashed IS NOT 1 ";
        } else {
            this.sqlCondition = " WHERE notes.category_id = " + ((Category) this.categorySpinner.getSelectedItem()).getId() + " AND " + DbHelper.KEY_ARCHIVED + " IS NOT 1 AND " + DbHelper.KEY_TRASHED + " IS NOT 1";
        }
        ListRemoteViewsFactory.updateConfiguration(getApplicationContext(), this.mAppWidgetId, this.sqlCondition, ((CheckBox) findViewById(R.id.show_thumbnails)).isChecked(), ((CheckBox) findViewById(R.id.show_timestamps)).isChecked());
        Intent intent = new Intent();
        intent.putExtra("appWidgetId", this.mAppWidgetId);
        setResult(-1, intent);
        finish();
    }
}
