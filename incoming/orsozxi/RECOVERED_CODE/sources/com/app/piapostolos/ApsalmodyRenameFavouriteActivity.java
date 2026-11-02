package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.app.piapostolos.ApsalmodyBeans.FavouriteNames;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ApsalmodyRenameFavouriteActivity extends BaseActivity {
    private int id = 0;
    boolean saved = false;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.rename_favourite);
        getWindow().addFlags(128);
        this.id = getIntent().getIntExtra("id", 0);
        ((TextView) findViewById(R.id.textView1)).setTextSize(Float.valueOf(new checksim().checkfontsize(this)).floatValue());
    }

    public void saveButtonAction(View view) {
        String string;
        this.saved = true;
        EditText editText = (EditText) findViewById(R.id.et_name);
        FavouriteNames favouriteNames = (FavouriteNames) new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).load();
        if (favouriteNames != null && favouriteNames.getFavouriteListsNames().size() > 0) {
            if (editText.getText().toString().equals("")) {
                string = "إبصلمودية" + favouriteNames.getFavouriteListsNames().size();
            } else {
                string = editText.getText().toString();
            }
            favouriteNames.getFavouriteListsNames().add(string);
            new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).save(favouriteNames);
        } else {
            String string2 = editText.getText().toString().equals("") ? "إبصلمودية 1" : editText.getText().toString();
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(string2);
            FavouriteNames favouriteNames2 = new FavouriteNames();
            favouriteNames2.setFavouriteListsNames(arrayList);
            new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).save(favouriteNames2);
        }
        Intent intent = new Intent(this, (Class<?>) ApsalmodyArrangeFavouriteActivity.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
        finish();
    }

    public void cancelButtonAction(View view) {
        this.saved = true;
        save();
    }

    private void save() {
        FavouriteNames favouriteNames = (FavouriteNames) new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).load();
        if (favouriteNames != null) {
            favouriteNames.getFavouriteListsNames().add("إبصلمودية " + favouriteNames.getFavouriteListsNames().size());
            new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).save(favouriteNames);
        } else {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("إبصلمودية 1");
            FavouriteNames favouriteNames2 = new FavouriteNames();
            favouriteNames2.setFavouriteListsNames(arrayList);
            new SavePrefs((Activity) this, (Class<?>) FavouriteNames.class).save(favouriteNames2);
        }
        Intent intent = new Intent(this, (Class<?>) ApsalmodyArrangeFavouriteActivity.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        if (!this.saved) {
            save();
        }
        super.onPause();
    }
}
