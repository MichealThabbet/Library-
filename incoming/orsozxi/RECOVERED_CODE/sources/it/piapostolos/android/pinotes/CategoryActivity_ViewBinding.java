package it.piapostolos.android.pinotes;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;

/* loaded from: classes3.dex */
public class CategoryActivity_ViewBinding implements Unbinder {
    private CategoryActivity target;
    private View view9f2;
    private View viewa10;
    private View viewb75;

    public CategoryActivity_ViewBinding(CategoryActivity categoryActivity) {
        this(categoryActivity, categoryActivity.getWindow().getDecorView());
    }

    public CategoryActivity_ViewBinding(final CategoryActivity categoryActivity, View view) {
        this.target = categoryActivity;
        categoryActivity.title = (EditText) Utils.findRequiredViewAsType(view, R.id.category_title, "field 'title'", EditText.class);
        categoryActivity.description = (EditText) Utils.findRequiredViewAsType(view, R.id.category_description, "field 'description'", EditText.class);
        View viewFindRequiredView = Utils.findRequiredView(view, R.id.delete, "field 'deleteBtn' and method 'deleteCategory'");
        categoryActivity.deleteBtn = (Button) Utils.castView(viewFindRequiredView, R.id.delete, "field 'deleteBtn'", Button.class);
        this.viewa10 = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: it.piapostolos.android.pinotes.CategoryActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                categoryActivity.deleteCategory();
            }
        });
        View viewFindRequiredView2 = Utils.findRequiredView(view, R.id.save, "field 'saveBtn' and method 'saveCategory'");
        categoryActivity.saveBtn = (Button) Utils.castView(viewFindRequiredView2, R.id.save, "field 'saveBtn'", Button.class);
        this.viewb75 = viewFindRequiredView2;
        viewFindRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: it.piapostolos.android.pinotes.CategoryActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                categoryActivity.saveCategory();
            }
        });
        View viewFindRequiredView3 = Utils.findRequiredView(view, R.id.color_chooser, "field 'colorChooser' and method 'showColorChooserCustomColors'");
        categoryActivity.colorChooser = (ImageView) Utils.castView(viewFindRequiredView3, R.id.color_chooser, "field 'colorChooser'", ImageView.class);
        this.view9f2 = viewFindRequiredView3;
        viewFindRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: it.piapostolos.android.pinotes.CategoryActivity_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                categoryActivity.showColorChooserCustomColors();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        CategoryActivity categoryActivity = this.target;
        if (categoryActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        categoryActivity.title = null;
        categoryActivity.description = null;
        categoryActivity.deleteBtn = null;
        categoryActivity.saveBtn = null;
        categoryActivity.colorChooser = null;
        this.viewa10.setOnClickListener(null);
        this.viewa10 = null;
        this.viewb75.setOnClickListener(null);
        this.viewb75 = null;
        this.view9f2.setOnClickListener(null);
        this.view9f2 = null;
    }
}
