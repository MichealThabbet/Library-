package com.app.piapostolos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.app.piapostolos.Adapters.DallalAdapter;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.Items;
import com.app.piapostolos.Beans.item_details;
import com.app.piapostolos.KholgayBeans.ApplicationDialogs;
import com.app.piapostolos.Views.MenuDateView;
import com.app.piapostolos.Views.WrapContentHeightViewPager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.huawei.hms.framework.common.ContainerUtils;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Callable;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class MainActivity_dallal extends BaseActivity {
    private static ArrayList<Items> contentItem;
    private ArrayList<String> books;
    private View content;
    private String currentInputStream;
    private ArrayList<String> dayBooks;
    private Spinner daysSpinner;
    private ArrayAdapter<String> daysadapter;
    public Typeface font;
    private GestureDetector gestureDetector;
    private ProgressDialog loadingDialog;
    private MenuDateView menuDateView;
    private WrapContentHeightViewPager newCategoriesViewPager;
    private DallalAdapter newPagerAdapter;
    private ArrayAdapter<String> praysAdapter;
    private Spinner praysSpinner;
    private int selectedDay;
    private String selectedFileName;
    private ArrayAdapter<String> titlesAdapter;
    private ArrayList<String> titlesBooks;
    private Spinner titlesSpinner;
    String xmlString = "";

    public int getSelectedDay() {
        return this.selectedDay;
    }

    public void setSelectedDay(int i) {
        this.selectedDay = i;
    }

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dallal);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_dallal.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), (NestedScrollView2) findViewById(R.id.v_nested));
        new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity_dallal.2
            @Override // java.lang.Runnable
            public void run() throws IOException {
                MainActivity_dallal mainActivity_dallal = MainActivity_dallal.this;
                mainActivity_dallal.daysSpinner = (Spinner) mainActivity_dallal.findViewById(R.id.sp_main_day);
                MainActivity_dallal mainActivity_dallal2 = MainActivity_dallal.this;
                mainActivity_dallal2.praysSpinner = (Spinner) mainActivity_dallal2.findViewById(R.id.sp_prays);
                MainActivity_dallal mainActivity_dallal3 = MainActivity_dallal.this;
                mainActivity_dallal3.titlesSpinner = (Spinner) mainActivity_dallal3.findViewById(R.id.sp_content_title);
                ((CollapsingToolbarLayout) MainActivity_dallal.this.findViewById(R.id.toolbar_layout)).setTitle(MainActivity_dallal.this.getString(R.string.t_week_alam));
                katamarsReaders katamarsreaders = new katamarsReaders(MainActivity_dallal.this, false);
                if (katamarsreaders.eidName.equals("a7d_shaaneen") || katamarsreaders.eidName.equals("7ames_el_ahd") || katamarsreaders.eidName.equals("sabt_farah") || katamarsreaders.eidName.equals("gomaa_azema")) {
                    Button button2 = (Button) MainActivity_dallal.this.findViewById(R.id.btn_goto);
                    button2.setText(R.string.t_kholagy);
                    button2.setVisibility(0);
                    Calendar calendar = Calendar.getInstance();
                    if (katamarsreaders.eidName.equals("a7d_shaaneen") && calendar.get(11) >= 15) {
                        button2.setVisibility(8);
                    }
                    if (katamarsreaders.eidName.equals("7ames_el_ahd") && calendar.get(11) >= 15) {
                        button2.setVisibility(8);
                    }
                    if (katamarsreaders.eidName.equals("sabt_farah") && calendar.get(11) >= 8) {
                        button2.setVisibility(8);
                    }
                    if (katamarsreaders.eidName.equals("gomaa_azema") && calendar.get(11) < 20) {
                        button2.setVisibility(8);
                    }
                    button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_dallal.2.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            MainActivity_dallal.this.startActivity(new Intent(MainActivity_dallal.this, (Class<?>) MainActivity_kholagy_menu.class));
                        }
                    });
                }
                ArrayList unused = MainActivity_dallal.contentItem = new ArrayList();
                MainActivity_dallal mainActivity_dallal4 = MainActivity_dallal.this;
                mainActivity_dallal4.newPagerAdapter = new DallalAdapter(mainActivity_dallal4, MainActivity_dallal.contentItem);
                MainActivity_dallal mainActivity_dallal5 = MainActivity_dallal.this;
                mainActivity_dallal5.newCategoriesViewPager = (WrapContentHeightViewPager) mainActivity_dallal5.findViewById(R.id.pager_top_rated);
                MainActivity_dallal.this.newCategoriesViewPager.setAdapter(MainActivity_dallal.this.newPagerAdapter);
                MainActivity_dallal.this.dayBooks = new ArrayList();
                MainActivity_dallal mainActivity_dallal6 = MainActivity_dallal.this;
                MainActivity_dallal mainActivity_dallal7 = MainActivity_dallal.this;
                mainActivity_dallal6.daysadapter = new ArrayAdapter(mainActivity_dallal7, R.layout.big_spinner_item_white, mainActivity_dallal7.dayBooks);
                MainActivity_dallal.this.daysadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                MainActivity_dallal.this.daysSpinner.setAdapter((SpinnerAdapter) MainActivity_dallal.this.daysadapter);
                MainActivity_dallal.this.daysSpinner.setPrompt(MainActivity_dallal.this.getString(R.string.bas7a));
                MainActivity_dallal.this.titlesBooks = new ArrayList();
                MainActivity_dallal mainActivity_dallal8 = MainActivity_dallal.this;
                MainActivity_dallal mainActivity_dallal9 = MainActivity_dallal.this;
                mainActivity_dallal8.titlesAdapter = new ArrayAdapter(mainActivity_dallal9, R.layout.big_spinner_item_white, mainActivity_dallal9.titlesBooks);
                MainActivity_dallal.this.titlesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                MainActivity_dallal.this.titlesSpinner.setAdapter((SpinnerAdapter) MainActivity_dallal.this.titlesAdapter);
                MainActivity_dallal.this.titlesSpinner.setPrompt(MainActivity_dallal.this.getString(R.string.prays));
                MainActivity_dallal.this.titlesAdapter.notifyDataSetChanged();
                MainActivity_dallal.this.newCategoriesViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.app.piapostolos.MainActivity_dallal.2.2
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public void onPageScrollStateChanged(int i) {
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public void onPageScrolled(int i, float f, int i2) {
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public void onPageSelected(int i) {
                        MainActivity_dallal.this.titlesSpinner.setSelection(i);
                        ((NestedScrollView2) MainActivity_dallal.this.findViewById(R.id.v_nested)).scrollTo(0, 0);
                    }
                });
                MainActivity_dallal.this.mainDaysScreen();
                MainActivity_dallal.this.daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_dallal.2.3
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) throws IOException {
                        MainActivity_dallal.this.praysScreen(i);
                        MainActivity_dallal.this.setSelectedDay(i);
                    }
                });
                MainActivity_dallal.this.praysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_dallal.2.4
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        MainActivity_dallal.this.fillContent(MainActivity_dallal.this.getSelectedDay(), i);
                        MainActivity_dallal.this.titlesSpinner.setSelection(0);
                    }
                });
                MainActivity_dallal.this.titlesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_dallal.2.5
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        MainActivity_dallal.this.newCategoriesViewPager.setCurrentItem(i);
                    }
                });
            }
        }, 200L);
        setTitle("أسبوع الآلام");
    }

    public void fillContent(int i, int i2) {
        if (i == 0 && i2 == 0) {
            this.selectedFileName = "suab.txt";
        } else if (i == 0 && i2 == 1) {
            this.selectedFileName = "suk.txt";
        } else if (i == 0 && i2 == 2) {
            this.selectedFileName = "sug.txt";
        } else if (i == 0 && i2 == 3) {
            this.selectedFileName = "su9.txt";
        } else if (i == 0 && i2 == 4) {
            this.selectedFileName = "su11.txt";
        } else if (i == 1 && i2 == 0) {
            this.selectedFileName = "sun1.txt";
        } else if (i == 1 && i2 == 1) {
            this.selectedFileName = "sun3.txt";
        } else if (i == 1 && i2 == 2) {
            this.selectedFileName = "sun6.txt";
        } else if (i == 1 && i2 == 3) {
            this.selectedFileName = "sun9.txt";
        } else if (i == 1 && i2 == 4) {
            this.selectedFileName = "sun11.txt";
        } else if (i == 1 && i2 == 5) {
            this.selectedFileName = "tan.txt";
        } else if (i == 2 && i2 == 0) {
            this.selectedFileName = "mmb.txt";
        } else if (i == 2 && i2 == 1) {
            this.selectedFileName = "mm1.txt";
        } else if (i == 2 && i2 == 2) {
            this.selectedFileName = "mm3.txt";
        } else if (i == 2 && i2 == 3) {
            this.selectedFileName = "mm6.txt";
        } else if (i == 2 && i2 == 4) {
            this.selectedFileName = "mm9.txt";
        } else if (i == 2 && i2 == 5) {
            this.selectedFileName = "mm11.txt";
        } else if (i == 2 && i2 == 6) {
            this.selectedFileName = "tam.txt";
        } else if (i == 3 && i2 == 0) {
            this.selectedFileName = "mn1.txt";
        } else if (i == 3 && i2 == 1) {
            this.selectedFileName = "mn3.txt";
        } else if (i == 3 && i2 == 2) {
            this.selectedFileName = "mn6.txt";
        } else if (i == 3 && i2 == 3) {
            this.selectedFileName = "mn9.txt";
        } else if (i == 3 && i2 == 4) {
            this.selectedFileName = "mn11.txt";
        } else if (i == 3 && i2 == 5) {
            this.selectedFileName = "tan.txt";
        } else if (i == 4 && i2 == 0) {
            this.selectedFileName = "tmb.txt";
        } else if (i == 4 && i2 == 1) {
            this.selectedFileName = "tm1.txt";
        } else if (i == 4 && i2 == 2) {
            this.selectedFileName = "tm3.txt";
        } else if (i == 4 && i2 == 3) {
            this.selectedFileName = "tm6.txt";
        } else if (i == 4 && i2 == 4) {
            this.selectedFileName = "tm9.txt";
        } else if (i == 4 && i2 == 5) {
            this.selectedFileName = "tm11.txt";
        } else if (i == 4 && i2 == 6) {
            this.selectedFileName = "tam.txt";
        } else if (i == 5 && i2 == 0) {
            this.selectedFileName = "tn1.txt";
        } else if (i == 5 && i2 == 1) {
            this.selectedFileName = "tn3.txt";
        } else if (i == 5 && i2 == 2) {
            this.selectedFileName = "tn6.txt";
        } else if (i == 5 && i2 == 3) {
            this.selectedFileName = "tn9.txt";
        } else if (i == 5 && i2 == 4) {
            this.selectedFileName = "tn11.txt";
        } else if (i == 5 && i2 == 5) {
            this.selectedFileName = "tan.txt";
        } else if (i == 6 && i2 == 0) {
            this.selectedFileName = "wmb.txt";
        } else if (i == 6 && i2 == 1) {
            this.selectedFileName = "wm1.txt";
        } else if (i == 6 && i2 == 2) {
            this.selectedFileName = "wm3.txt";
        } else if (i == 6 && i2 == 3) {
            this.selectedFileName = "wm6.txt";
        } else if (i == 6 && i2 == 4) {
            this.selectedFileName = "wm9.txt";
        } else if (i == 6 && i2 == 5) {
            this.selectedFileName = "wm11.txt";
        } else if (i == 6 && i2 == 6) {
            this.selectedFileName = "tam.txt";
        } else if (i == 7 && i2 == 0) {
            this.selectedFileName = "wn1.txt";
        } else if (i == 7 && i2 == 1) {
            this.selectedFileName = "wn3.txt";
        } else if (i == 7 && i2 == 2) {
            this.selectedFileName = "wn6.txt";
        } else if (i == 7 && i2 == 3) {
            this.selectedFileName = "wn9.txt";
        } else if (i == 7 && i2 == 4) {
            this.selectedFileName = "wn11.txt";
        } else if (i == 7 && i2 == 5) {
            this.selectedFileName = "tan.txt";
        } else if (i == 8 && i2 == 0) {
            this.selectedFileName = "thmb.txt";
        } else if (i == 8 && i2 == 1) {
            this.selectedFileName = "thm1.txt";
        } else if (i == 8 && i2 == 2) {
            this.selectedFileName = "thm3.txt";
        } else if (i == 8 && i2 == 3) {
            this.selectedFileName = "thm6.txt";
        } else if (i == 8 && i2 == 4) {
            this.selectedFileName = "thm9.txt";
        } else if (i == 8 && i2 == 5) {
            this.selectedFileName = "thl.txt";
        } else if (i == 8 && i2 == 6) {
            this.selectedFileName = "thk.txt";
        } else if (i == 8 && i2 == 7) {
            this.selectedFileName = "thm11.txt";
        } else if (i == 8 && i2 == 8) {
            this.selectedFileName = "tam.txt";
        } else if (i == 9 && i2 == 0) {
            this.selectedFileName = "thn1.txt";
        } else if (i == 9 && i2 == 1) {
            this.selectedFileName = "thn3.txt";
        } else if (i == 9 && i2 == 2) {
            this.selectedFileName = "thn6.txt";
        } else if (i == 9 && i2 == 3) {
            this.selectedFileName = "thn9.txt";
        } else if (i == 9 && i2 == 4) {
            this.selectedFileName = "thn11.txt";
        } else if (i == 9 && i2 == 5) {
            this.selectedFileName = "tan.txt";
        } else if (i == 10 && i2 == 0) {
            this.selectedFileName = "fb.txt";
        } else if (i == 10 && i2 == 1) {
            this.selectedFileName = "f1.txt";
        } else if (i == 10 && i2 == 2) {
            this.selectedFileName = "f3.txt";
        } else if (i == 10 && i2 == 3) {
            this.selectedFileName = "f6.txt";
        } else if (i == 10 && i2 == 4) {
            this.selectedFileName = "amana.txt";
        } else if (i == 10 && i2 == 5) {
            this.selectedFileName = "f9.txt";
        } else if (i == 10 && i2 == 6) {
            this.selectedFileName = "f11.txt";
        } else if (i == 10 && i2 == 7) {
            this.selectedFileName = "f12.txt";
        } else if (i == 10 && i2 == 8) {
            this.selectedFileName = "tam.txt";
        } else if (i == 11 && i2 == 0) {
            this.selectedFileName = "sap151.txt";
        } else if (i == 11 && i2 == 1) {
            this.selectedFileName = "sah2l.txt";
        } else if (i == 11 && i2 == 2) {
            this.selectedFileName = "sah1.txt";
        } else if (i == 11 && i2 == 3) {
            this.selectedFileName = "sah1l.txt";
        } else if (i == 11 && i2 == 4) {
            this.selectedFileName = "sats1.txt";
        } else if (i == 11 && i2 == 5) {
            this.selectedFileName = "sah3.txt";
        } else if (i == 11 && i2 == 6) {
            this.selectedFileName = "saar.txt";
        } else if (i == 11 && i2 == 7) {
            this.selectedFileName = "sate.txt";
        } else if (i == 11 && i2 == 8) {
            this.selectedFileName = "sats2.txt";
        } else if (i == 11 && i2 == 9) {
            this.selectedFileName = "sasu.txt";
        } else if (i == 11 && i2 == 10) {
            this.selectedFileName = "sato.txt";
        } else if (i == 11 && i2 == 11) {
            this.selectedFileName = "sab.txt";
        } else if (i == 11 && i2 == 12) {
            this.selectedFileName = "sa3.txt";
        } else if (i == 11 && i2 == 13) {
            this.selectedFileName = "sa6.txt";
        } else if (i == 11 && i2 == 14) {
            this.selectedFileName = "sar.txt";
        } else if (i == 11 && i2 == 15) {
            this.selectedFileName = "sa9.txt";
        } else if (i == 11 && i2 == 16) {
            this.selectedFileName = "sak.txt";
        }
        contentItem.clear();
        this.newPagerAdapter.notifyDataSetChanged();
        testAsynch();
    }

    private void testAsynch() {
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        this.titlesBooks.clear();
        this.titlesAdapter.notifyDataSetChanged();
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$MainActivity_dallal$zWw1Rge5ekst9tIJ8YF9dOajleE
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$testAsynch$0$MainActivity_dallal();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$MainActivity_dallal$1oGhPLJhnRPxJF4HoibEzJbu99M
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$testAsynch$1$MainActivity_dallal((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$testAsynch$0$MainActivity_dallal() throws Exception {
        Log.d("app", "dallal/" + this.selectedFileName);
        try {
            this.xmlString = new String(app.decodeFile2(this, "piapostolos", "dallal/" + this.selectedFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCurrentInputStream(this.xmlString);
        return false;
    }

    public /* synthetic */ void lambda$testAsynch$1$MainActivity_dallal(Boolean bool) throws Exception {
        String[] strArrSplit = new String(getCurrentInputStream()).split("\n");
        ArrayList<item_details> arrayList = new ArrayList<>();
        String strReplace = "";
        boolean z = true;
        for (int i = 0; i < strArrSplit.length; i++) {
            Items items = new Items();
            if (strArrSplit[i].contains(Marker.ANY_NON_NULL_MARKER) && !strArrSplit[i].contains("+++")) {
                strReplace = strArrSplit[i].replace(Marker.ANY_NON_NULL_MARKER, "");
            } else if (!strArrSplit[i].equals("")) {
                if (strArrSplit[i].contains("+++") || strArrSplit[i].equals("+++")) {
                    items.setTitle(strReplace);
                    items.setItem_details(arrayList);
                    contentItem.add(items);
                    arrayList = new ArrayList<>();
                    new Items();
                    strReplace = "";
                } else if (strArrSplit[i].contains(ContainerUtils.FIELD_DELIMITER) && !strArrSplit[i].contains("&&")) {
                    z = false;
                } else if (!strArrSplit[i].contains("&&") || strArrSplit[i].equals(ContainerUtils.FIELD_DELIMITER)) {
                    item_details item_detailsVar = new item_details();
                    item_detailsVar.setContent(strArrSplit[i]);
                    item_detailsVar.setIsarabic(z);
                    arrayList.add(item_detailsVar);
                } else {
                    z = true;
                }
            }
        }
        for (int i2 = 0; i2 < contentItem.size(); i2++) {
            this.titlesBooks.add(contentItem.get(i2).getTitle());
        }
        this.titlesAdapter.notifyDataSetChanged();
        this.newPagerAdapter.notifyDataSetChanged();
        this.titlesSpinner.setSelection(0);
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
    }

    public void praysScreen(int i) throws IOException {
        try {
            this.books = new ArrayList<>();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.big_spinner_item_white, this.books);
            this.praysAdapter = arrayAdapter;
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.praysSpinner.setAdapter((SpinnerAdapter) this.praysAdapter);
            this.praysSpinner.setPrompt(getString(R.string.prays));
            InputStream inputStreamOpen = null;
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9) {
                inputStreamOpen = getAssets().open("alamNight.xml");
            } else if (i == 2 || i == 4 || i == 6) {
                inputStreamOpen = getAssets().open("alam.xml");
            } else if (i == 0) {
                inputStreamOpen = getAssets().open("alam7ad.xml");
            } else if (i == 8) {
                inputStreamOpen = getAssets().open("alamkames.xml");
            } else if (i == 10) {
                inputStreamOpen = getAssets().open("alamGomaa.xml");
            } else if (i == 11) {
                inputStreamOpen = getAssets().open("alamsabt.xml");
            }
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            for (String str : new String(bArr).split("\n")) {
                this.books.add(str);
            }
            this.praysAdapter.notifyDataSetChanged();
        } catch (IOException unused) {
        }
    }

    public void mainDaysScreen() throws IOException {
        try {
            InputStream inputStreamOpen = getAssets().open("alamMainTitles.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            for (String str : strArrSplit) {
                this.dayBooks.add(str);
            }
            this.daysadapter.notifyDataSetChanged();
        } catch (IOException unused) {
        }
    }

    public void settingsButtonAction(View view) {
        startActivity(new Intent(this, (Class<?>) DallalSettingsActivity.class));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity_dallal.3
            @Override // java.lang.Runnable
            public void run() {
                MainActivity_dallal.this.newPagerAdapter.notifyDataSetChanged();
                AppLeftSlider.setCurrentActivity(MainActivity_dallal.this);
            }
        }, 300L);
        super.onResume();
    }

    public String getCurrentInputStream() {
        return this.currentInputStream;
    }

    public void setCurrentInputStream(String str) {
        this.currentInputStream = str;
    }
}
