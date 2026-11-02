package de.keyboardsurfer.android.widget.crouton;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.keyboardsurfer.android.widget.crouton.Style;

/* loaded from: classes2.dex */
public final class Crouton {
    private static final int IMAGE_ID = 256;
    private static final String NULL_PARAMETERS_ARE_NOT_ACCEPTED = "Null parameters are not accepted";
    private static final int TEXT_ID = 257;
    private Activity activity;
    private Configuration configuration;
    private FrameLayout croutonView;
    private final View customView;
    private Animation inAnimation;
    private LifecycleCallback lifecycleCallback;
    private View.OnClickListener onClickListener;
    private Animation outAnimation;
    private final Style style;
    private final CharSequence text;
    private ViewGroup viewGroup;

    public static String getLicenseText() {
        return "";
    }

    private Crouton(Activity activity, CharSequence charSequence, Style style) {
        this.configuration = null;
        this.lifecycleCallback = null;
        if (activity == null || charSequence == null || style == null) {
            throw new IllegalArgumentException(NULL_PARAMETERS_ARE_NOT_ACCEPTED);
        }
        this.activity = activity;
        this.viewGroup = null;
        this.text = charSequence;
        this.style = style;
        this.customView = null;
    }

    private Crouton(Activity activity, CharSequence charSequence, Style style, ViewGroup viewGroup) {
        this.configuration = null;
        this.lifecycleCallback = null;
        if (activity == null || charSequence == null || style == null) {
            throw new IllegalArgumentException(NULL_PARAMETERS_ARE_NOT_ACCEPTED);
        }
        this.activity = activity;
        this.text = charSequence;
        this.style = style;
        this.viewGroup = viewGroup;
        this.customView = null;
    }

    private Crouton(Activity activity, View view) {
        this.configuration = null;
        this.lifecycleCallback = null;
        if (activity == null || view == null) {
            throw new IllegalArgumentException(NULL_PARAMETERS_ARE_NOT_ACCEPTED);
        }
        this.activity = activity;
        this.viewGroup = null;
        this.customView = view;
        this.style = new Style.Builder().build();
        this.text = null;
    }

    private Crouton(Activity activity, View view, ViewGroup viewGroup) {
        this(activity, view, viewGroup, Configuration.DEFAULT);
    }

    private Crouton(Activity activity, View view, ViewGroup viewGroup, Configuration configuration) {
        this.configuration = null;
        this.lifecycleCallback = null;
        if (activity == null || view == null) {
            throw new IllegalArgumentException(NULL_PARAMETERS_ARE_NOT_ACCEPTED);
        }
        this.activity = activity;
        this.customView = view;
        this.viewGroup = viewGroup;
        this.style = new Style.Builder().build();
        this.text = null;
        this.configuration = configuration;
    }

    public static Crouton makeText(Activity activity, CharSequence charSequence, Style style) {
        return new Crouton(activity, charSequence, style);
    }

    public static Crouton makeText(Activity activity, CharSequence charSequence, Style style, ViewGroup viewGroup) {
        return new Crouton(activity, charSequence, style, viewGroup);
    }

    public static Crouton makeText(Activity activity, CharSequence charSequence, Style style, int i) {
        return new Crouton(activity, charSequence, style, (ViewGroup) activity.findViewById(i));
    }

    public static Crouton makeText(Activity activity, int i, Style style) {
        return makeText(activity, activity.getString(i), style);
    }

    public static Crouton makeText(Activity activity, int i, Style style, ViewGroup viewGroup) {
        return makeText(activity, activity.getString(i), style, viewGroup);
    }

    public static Crouton makeText(Activity activity, int i, Style style, int i2) {
        return makeText(activity, activity.getString(i), style, (ViewGroup) activity.findViewById(i2));
    }

    public static Crouton make(Activity activity, View view) {
        return new Crouton(activity, view);
    }

    public static Crouton make(Activity activity, View view, ViewGroup viewGroup) {
        return new Crouton(activity, view, viewGroup);
    }

    public static Crouton make(Activity activity, View view, int i) {
        return new Crouton(activity, view, (ViewGroup) activity.findViewById(i));
    }

    public static Crouton make(Activity activity, View view, int i, Configuration configuration) {
        return new Crouton(activity, view, (ViewGroup) activity.findViewById(i), configuration);
    }

    public static void showText(Activity activity, CharSequence charSequence, Style style) {
        makeText(activity, charSequence, style).show();
    }

    public static void showText(Activity activity, CharSequence charSequence, Style style, ViewGroup viewGroup) {
        makeText(activity, charSequence, style, viewGroup).show();
    }

    public static void showText(Activity activity, CharSequence charSequence, Style style, int i) {
        makeText(activity, charSequence, style, (ViewGroup) activity.findViewById(i)).show();
    }

    public static void showText(Activity activity, CharSequence charSequence, Style style, int i, Configuration configuration) {
        makeText(activity, charSequence, style, (ViewGroup) activity.findViewById(i)).setConfiguration(configuration).show();
    }

    public static void show(Activity activity, View view) {
        make(activity, view).show();
    }

    public static void show(Activity activity, View view, ViewGroup viewGroup) {
        make(activity, view, viewGroup).show();
    }

    public static void show(Activity activity, View view, int i) {
        make(activity, view, i).show();
    }

    public static void showText(Activity activity, int i, Style style) {
        showText(activity, activity.getString(i), style);
    }

    public static void showText(Activity activity, int i, Style style, ViewGroup viewGroup) {
        showText(activity, activity.getString(i), style, viewGroup);
    }

    public static void showText(Activity activity, int i, Style style, int i2) {
        showText(activity, activity.getString(i), style, i2);
    }

    public static void hide(Crouton crouton) {
        crouton.hide();
    }

    public static void cancelAllCroutons() {
        Manager.getInstance().clearCroutonQueue();
    }

    public static void clearCroutonsForActivity(Activity activity) {
        Manager.getInstance().clearCroutonsForActivity(activity);
    }

    public void cancel() {
        Manager.getInstance().removeCroutonImmediately(this);
    }

    public void show() {
        Manager.getInstance().add(this);
    }

    public Animation getInAnimation() {
        if (this.inAnimation == null && this.activity != null) {
            if (getConfiguration().inAnimationResId > 0) {
                this.inAnimation = AnimationUtils.loadAnimation(getActivity(), getConfiguration().inAnimationResId);
            } else {
                measureCroutonView();
                this.inAnimation = DefaultAnimationsBuilder.buildDefaultSlideInDownAnimation(getView());
            }
        }
        return this.inAnimation;
    }

    public Animation getOutAnimation() {
        if (this.outAnimation == null && this.activity != null) {
            if (getConfiguration().outAnimationResId > 0) {
                this.outAnimation = AnimationUtils.loadAnimation(getActivity(), getConfiguration().outAnimationResId);
            } else {
                this.outAnimation = DefaultAnimationsBuilder.buildDefaultSlideOutUpAnimation(getView());
            }
        }
        return this.outAnimation;
    }

    public void setLifecycleCallback(LifecycleCallback lifecycleCallback) {
        this.lifecycleCallback = lifecycleCallback;
    }

    public void hide() {
        Manager.getInstance().removeCrouton(this);
    }

    public Crouton setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public Crouton setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public String toString() {
        return "Crouton{text=" + ((Object) this.text) + ", style=" + this.style + ", configuration=" + this.configuration + ", customView=" + this.customView + ", onClickListener=" + this.onClickListener + ", activity=" + this.activity + ", viewGroup=" + this.viewGroup + ", croutonView=" + this.croutonView + ", inAnimation=" + this.inAnimation + ", outAnimation=" + this.outAnimation + ", lifecycleCallback=" + this.lifecycleCallback + '}';
    }

    boolean isShowing() {
        return this.activity != null && (isCroutonViewNotNull() || isCustomViewNotNull());
    }

    private boolean isCroutonViewNotNull() {
        FrameLayout frameLayout = this.croutonView;
        return (frameLayout == null || frameLayout.getParent() == null) ? false : true;
    }

    private boolean isCustomViewNotNull() {
        View view = this.customView;
        return (view == null || view.getParent() == null) ? false : true;
    }

    void detachActivity() {
        this.activity = null;
    }

    void detachViewGroup() {
        this.viewGroup = null;
    }

    void detachLifecycleCallback() {
        this.lifecycleCallback = null;
    }

    LifecycleCallback getLifecycleCallback() {
        return this.lifecycleCallback;
    }

    Style getStyle() {
        return this.style;
    }

    Configuration getConfiguration() {
        if (this.configuration == null) {
            this.configuration = getStyle().configuration;
        }
        return this.configuration;
    }

    Activity getActivity() {
        return this.activity;
    }

    ViewGroup getViewGroup() {
        return this.viewGroup;
    }

    CharSequence getText() {
        return this.text;
    }

    View getView() {
        View view = this.customView;
        if (view != null) {
            return view;
        }
        if (this.croutonView == null) {
            initializeCroutonView();
        }
        return this.croutonView;
    }

    private void measureCroutonView() {
        int iMakeMeasureSpec;
        View view = getView();
        ViewGroup viewGroup = this.viewGroup;
        if (viewGroup != null) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredWidth(), Integer.MIN_VALUE);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.activity.getWindow().getDecorView().getMeasuredWidth(), Integer.MIN_VALUE);
        }
        view.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void initializeCroutonView() throws Resources.NotFoundException {
        Resources resources = this.activity.getResources();
        this.croutonView = initializeCroutonViewGroup(resources);
        this.croutonView.addView(initializeContentView(resources));
    }

    private FrameLayout initializeCroutonViewGroup(Resources resources) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        FrameLayout frameLayout = new FrameLayout(this.activity);
        View.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            frameLayout.setOnClickListener(onClickListener);
        }
        if (this.style.heightDimensionResId > 0) {
            dimensionPixelSize = resources.getDimensionPixelSize(this.style.heightDimensionResId);
        } else {
            dimensionPixelSize = this.style.heightInPixels;
        }
        if (this.style.widthDimensionResId > 0) {
            dimensionPixelSize2 = resources.getDimensionPixelSize(this.style.widthDimensionResId);
        } else {
            dimensionPixelSize2 = this.style.widthInPixels;
        }
        if (dimensionPixelSize2 == 0) {
            dimensionPixelSize2 = -1;
        }
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize));
        if (this.style.backgroundColorValue != -1) {
            frameLayout.setBackgroundColor(this.style.backgroundColorValue);
        } else {
            frameLayout.setBackgroundColor(resources.getColor(this.style.backgroundColorResourceId));
        }
        if (this.style.backgroundDrawableResourceId != 0) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, BitmapFactory.decodeResource(resources, this.style.backgroundDrawableResourceId));
            if (this.style.isTileEnabled) {
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
            frameLayout.setBackgroundDrawable(bitmapDrawable);
        }
        return frameLayout;
    }

    private RelativeLayout initializeContentView(Resources resources) throws Resources.NotFoundException {
        RelativeLayout relativeLayout = new RelativeLayout(this.activity);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        int dimensionPixelSize = this.style.paddingInPixels;
        if (this.style.paddingDimensionResId > 0) {
            dimensionPixelSize = resources.getDimensionPixelSize(this.style.paddingDimensionResId);
        }
        relativeLayout.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        ImageView imageViewInitializeImageView = null;
        if (this.style.imageDrawable != null || this.style.imageResId != 0) {
            imageViewInitializeImageView = initializeImageView();
            relativeLayout.addView(imageViewInitializeImageView, imageViewInitializeImageView.getLayoutParams());
        }
        TextView textViewInitializeTextView = initializeTextView(resources);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        if (imageViewInitializeImageView != null) {
            layoutParams.addRule(1, imageViewInitializeImageView.getId());
        }
        if ((this.style.gravity & 17) != 0) {
            layoutParams.addRule(13);
        } else if ((this.style.gravity & 16) != 0) {
            layoutParams.addRule(15);
        } else if ((this.style.gravity & 1) != 0) {
            layoutParams.addRule(14);
        }
        relativeLayout.addView(textViewInitializeTextView, layoutParams);
        return relativeLayout;
    }

    private TextView initializeTextView(Resources resources) throws Resources.NotFoundException {
        TextView textView = new TextView(this.activity);
        textView.setId(257);
        textView.setText(this.text);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setGravity(this.style.gravity);
        if (this.style.textColorValue != -1) {
            textView.setTextColor(this.style.textColorValue);
        } else if (this.style.textColorResourceId != 0) {
            textView.setTextColor(resources.getColor(this.style.textColorResourceId));
        }
        if (this.style.textSize != 0) {
            textView.setTextSize(2, this.style.textSize);
        }
        if (this.style.textShadowColorResId != 0) {
            initializeTextViewShadow(resources, textView);
        }
        if (this.style.textAppearanceResId != 0) {
            textView.setTextAppearance(this.activity, this.style.textAppearanceResId);
        }
        return textView;
    }

    private void initializeTextViewShadow(Resources resources, TextView textView) throws Resources.NotFoundException {
        textView.setShadowLayer(this.style.textShadowRadius, this.style.textShadowDx, this.style.textShadowDy, resources.getColor(this.style.textShadowColorResId));
    }

    private ImageView initializeImageView() {
        ImageView imageView = new ImageView(this.activity);
        imageView.setId(256);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(this.style.imageScaleType);
        if (this.style.imageDrawable != null) {
            imageView.setImageDrawable(this.style.imageDrawable);
        }
        if (this.style.imageResId != 0) {
            imageView.setImageResource(this.style.imageResId);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(9, -1);
        layoutParams.addRule(15, -1);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }
}
