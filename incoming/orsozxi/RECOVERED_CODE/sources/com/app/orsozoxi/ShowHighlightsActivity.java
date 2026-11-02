package com.app.orsozoxi;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.ShowHighLightsAdapter;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Beans.highlightAyat;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.Views.MenuDateView;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class ShowHighlightsActivity extends BaseActivity {
    Map<String, String> actionsMap;
    ArrayAdapter<String> adapterglav;
    String[] arabibleglaves;
    ShowHighLightsAdapter bibleAdapter;
    int bookin;
    String[] books;
    int chapterin;
    int chaptersnumbers;
    private View content;
    int counterr;
    private Context ctx;
    Dictionary<Integer, String> d;
    int def;
    AdapterView.AdapterContextMenuInfo info;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private ListView listViewFootballLegend;
    private MenuDateView menuDateView;
    private ArrayList<String> searchResultBeans;
    TextView t;
    private int current_book_number = 0;
    private int current_chapter_number = 0;
    boolean bookmarked = false;
    private int firsttime = 0;
    int pos = 0;
    private final int CONTEXT_MENU_ID = 1;
    private String message = "";
    private int selectedToShare = 0;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rshow_highlighted);
        this.searchResultBeans = new ArrayList<>();
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        }
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.ShowHighlightsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.ctx = this;
        new Handler().postDelayed(new Runnable() { // from class: com.app.orsozoxi.ShowHighlightsActivity.2
            @Override // java.lang.Runnable
            public void run() {
                ShowHighlightsActivity.this.counterr = 0;
                ShowHighlightsActivity.this.getWindow().addFlags(128);
                new checksim();
                ShowHighlightsActivity.this.d = new Hashtable();
                ShowHighlightsActivity.this.levelsMap = new HashMap<>();
                ShowHighlightsActivity.this.levels = new ArrayList<>();
                ShowHighlightsActivity showHighlightsActivity = ShowHighlightsActivity.this;
                showHighlightsActivity.t = (TextView) showHighlightsActivity.findViewById(R.id.gcm_msg);
                ShowHighlightsActivity showHighlightsActivity2 = ShowHighlightsActivity.this;
                showHighlightsActivity2.listViewFootballLegend = (ListView) showHighlightsActivity2.findViewById(R.id.liss);
                ShowHighlightsActivity.this.listViewFootballLegend.setBackgroundColor(0);
                ShowHighlightsActivity.this.listViewFootballLegend.setCacheColorHint(0);
                if (Build.VERSION.SDK_INT >= 8) {
                    ShowHighlightsActivity.this.getData();
                }
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$ShowHighlightsActivity$kdwpnUvNbAiGqDS72vs7kCdEDUU
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$getData$0$ShowHighlightsActivity();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$ShowHighlightsActivity$Xjaj9NM6xYtz2jrLoqoLzjliIL8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$getData$1$ShowHighlightsActivity(progressBar, (String) obj);
            }
        });
    }

    public /* synthetic */ String lambda$getData$0$ShowHighlightsActivity() throws Exception {
        highlightAyat highlightayat = (highlightAyat) new SavePrefs((Activity) this, (Class<?>) highlightAyat.class).load();
        if (highlightayat == null) {
            return "Executed";
        }
        this.searchResultBeans = highlightayat.getAyat();
        return "Executed";
    }

    public /* synthetic */ void lambda$getData$1$ShowHighlightsActivity(ProgressBar progressBar, String str) throws Exception {
        progressBar.setVisibility(8);
        ShowHighLightsAdapter showHighLightsAdapter = new ShowHighLightsAdapter(this.ctx, R.layout.highlight_row_item, this.searchResultBeans);
        this.bibleAdapter = showHighLightsAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) showHighLightsAdapter);
        if (this.searchResultBeans.size() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_content, 1).show();
            finish();
        }
        this.bibleAdapter.notifyDataSetChanged();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        AppLeftSlider.setCurrentActivity(this);
        super.onResume();
    }
}
