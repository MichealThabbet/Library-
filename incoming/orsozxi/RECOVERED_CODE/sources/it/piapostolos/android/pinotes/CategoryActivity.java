package it.piapostolos.android.pinotes;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import de.greenrobot.event.EventBus;
import it.feio.android.simplegallery.util.BitmapUtils;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.async.bus.CategoriesUpdatedEvent;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Category;
import java.util.Calendar;
import java.util.Random;

/* loaded from: classes3.dex */
public class CategoryActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback {
    Category category;

    @BindView(R2.id.color_chooser)
    ImageView colorChooser;

    @BindView(R2.id.delete)
    Button deleteBtn;

    @BindView(R2.id.category_description)
    EditText description;

    @BindView(R2.id.save)
    Button saveBtn;
    private int selectedColor;

    @BindView(R2.id.category_title)
    EditText title;

    @Override // com.afollestad.materialdialogs.color.ColorChooserDialog.ColorCallback
    public void onColorChooserDismissed(ColorChooserDialog colorChooserDialog) {
    }

    @Override // android.view.Window.Callback
    public void onPointerCaptureChanged(boolean z) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        Category category = (Category) getIntent().getParcelableExtra("category");
        this.category = category;
        if (category == null) {
            LogDelegate.d("Adding new category");
            Category category2 = new Category();
            this.category = category2;
            category2.setColor(String.valueOf(getRandomPaletteColor()));
        } else {
            LogDelegate.d("Editing category " + this.category.getName());
        }
        this.selectedColor = Integer.parseInt(this.category.getColor());
        populateViews();
    }

    private int getRandomPaletteColor() throws Resources.NotFoundException {
        int[] intArray = getResources().getIntArray(R.array.material_colors);
        return intArray[new Random().nextInt(intArray.length)];
    }

    @OnClick({R2.id.color_chooser})
    public void showColorChooserCustomColors() {
        new ColorChooserDialog.Builder(this, R.string.colors).dynamicButtonColor(false).preselect(this.selectedColor).show(this);
    }

    @Override // com.afollestad.materialdialogs.color.ColorChooserDialog.ColorCallback
    public void onColorSelection(ColorChooserDialog colorChooserDialog, int i) {
        BitmapUtils.changeImageViewDrawableColor(this.colorChooser, i);
        this.selectedColor = i;
    }

    private void populateViews() {
        this.title.setText(this.category.getName());
        this.description.setText(this.category.getDescription());
        String color = this.category.getColor();
        if (color != null && color.length() > 0) {
            this.colorChooser.getDrawable().mutate().setColorFilter(Integer.parseInt(color), PorterDuff.Mode.SRC_ATOP);
        }
        this.deleteBtn.setVisibility(TextUtils.isEmpty(this.category.getName()) ? 4 : 0);
    }

    @OnClick({R2.id.save})
    public void saveCategory() {
        if (this.title.getText().toString().length() == 0) {
            this.title.setError(getString(R.string.category_missing_title));
            return;
        }
        this.category.setId(Long.valueOf(this.category.getId() != null ? this.category.getId().longValue() : Calendar.getInstance().getTimeInMillis()));
        this.category.setName(this.title.getText().toString());
        this.category.setDescription(this.description.getText().toString());
        if (this.selectedColor != 0 || this.category.getColor() == null) {
            this.category.setColor(String.valueOf(this.selectedColor));
        }
        this.category = DbHelper.getInstance().updateCategory(this.category);
        getIntent().putExtra("category", this.category);
        setResult(-1, getIntent());
        finish();
    }

    @OnClick({R2.id.delete})
    public void deleteCategory() {
        new MaterialDialog.Builder(this).title(R.string.delete_unused_category_confirmation).content(R.string.delete_category_confirmation).positiveText(R.string.confirm).positiveColorRes(R.color.colorAccent).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$CategoryActivity$6SPT-VYFBpH7vFtuyygRuhnIybs
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$deleteCategory$0$CategoryActivity(materialDialog, dialogAction);
            }
        }).build().show();
    }

    public /* synthetic */ void lambda$deleteCategory$0$CategoryActivity(MaterialDialog materialDialog, DialogAction dialogAction) {
        SharedPreferences sharedPreferences = getSharedPreferences("PiNotes", 4);
        String str = getResources().getStringArray(R.array.navigation_list_codes)[0];
        if (String.valueOf(this.category.getId()).equals(sharedPreferences.getString("navigation", str))) {
            sharedPreferences.edit().putString("navigation", str).apply();
        }
        DbHelper.getInstance().deleteCategory(this.category);
        EventBus.getDefault().post(new CategoriesUpdatedEvent());
        BaseActivity.notifyAppWidgets(PiNotes.getAppContext());
        setResult(1);
        finish();
    }
}
