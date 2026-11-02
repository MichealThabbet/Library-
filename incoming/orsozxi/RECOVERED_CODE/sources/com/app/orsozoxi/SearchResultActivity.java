package com.app.orsozoxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.Result_BibleAdapter;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.BibleDataBase.UseDatabaseHandler;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class SearchResultActivity extends BaseActivity {
    ArrayAdapter<String> adapterglav;
    Result_BibleAdapter bibleAdapter;
    String[] books;
    int chaptersnumbers;
    private Context ctx;
    Dictionary<Integer, String> d;
    AdapterView.AdapterContextMenuInfo info;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private ListView listViewFootballLegend;
    private TextView mResultNumTextView;
    TextView t;
    int pos = 0;
    private ArrayList<BibleDataBase> searchResultBeans = new ArrayList<>();
    private String message = "";

    static /* synthetic */ String access$284(SearchResultActivity searchResultActivity, Object obj) {
        String str = searchResultActivity.message + obj;
        searchResultActivity.message = str;
        return str;
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result_search);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        this.mResultNumTextView = (TextView) findViewById(R.id.tv_result_num);
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        } else {
            this.mResultNumTextView.setTextColor(Color.parseColor("#000000"));
        }
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.search_result);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.SearchResultActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.ctx = this;
        new Handler().postDelayed(new AnonymousClass2(), 200L);
    }

    /* renamed from: com.app.orsozoxi.SearchResultActivity$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SearchResultActivity.this.getWindow().addFlags(128);
            new checksim();
            SearchResultActivity.this.d = new Hashtable();
            SearchResultActivity.this.levelsMap = new HashMap<>();
            SearchResultActivity.this.levels = new ArrayList<>();
            SearchResultActivity searchResultActivity = SearchResultActivity.this;
            searchResultActivity.t = (TextView) searchResultActivity.findViewById(R.id.gcm_msg);
            SearchResultActivity searchResultActivity2 = SearchResultActivity.this;
            searchResultActivity2.listViewFootballLegend = (ListView) searchResultActivity2.findViewById(R.id.liss);
            SearchResultActivity.this.listViewFootballLegend.setBackgroundColor(0);
            SearchResultActivity.this.listViewFootballLegend.setCacheColorHint(0);
            SearchResultActivity.this.listViewFootballLegend.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.SearchResultActivity.2.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
                    String str = ((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getBookName() + " " + ((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getChapterName() + " اية " + (((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getAyahIndex() + 1);
                    SearchResultActivity.this.message = "";
                    SearchResultActivity.access$284(SearchResultActivity.this, str + "\n" + ((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getAyah());
                    new AlertDialog.Builder(SearchResultActivity.this).setTitle(str).setMessage(((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getAyah()).setPositiveButton(SearchResultActivity.this.getString(R.string.go_shahed), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.SearchResultActivity.2.1.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            Intent intent = new Intent(SearchResultActivity.this, (Class<?>) MainActivity_Bible.class);
                            if (((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getBookIndex() >= 46) {
                                intent.putExtra("isOld", false);
                            } else {
                                intent.putExtra("isOld", true);
                            }
                            intent.putExtra("safr", ((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getBookIndex() + 1);
                            intent.putExtra("as7a7", ((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getChapterIndex());
                            intent.putExtra("ayah", ((BibleDataBase) SearchResultActivity.this.searchResultBeans.get(i)).getAyahIndex());
                            SearchResultActivity.this.startActivity(intent);
                        }
                    }).setNeutralButton(SearchResultActivity.this.getString(R.string.fb_copy), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.SearchResultActivity.2.1.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            app.copyText(SearchResultActivity.this, SearchResultActivity.this.message);
                        }
                    }).setNegativeButton(SearchResultActivity.this.getString(R.string.options_exit), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.SearchResultActivity.2.1.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                        }
                    }).show();
                }
            });
            SearchResultActivity searchResultActivity3 = SearchResultActivity.this;
            searchResultActivity3.getResult(searchResultActivity3.getIntent().getExtras().getString(FirebaseAnalytics.Event.SEARCH));
            final EditText editText = (EditText) SearchResultActivity.this.findViewById(R.id.et_search);
            editText.setText(SearchResultActivity.this.getIntent().getExtras().getString(FirebaseAnalytics.Event.SEARCH));
            SearchResultActivity.this.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.SearchResultActivity.2.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(editText.getText().toString())) {
                        SearchResultActivity.this.getResult(editText.getText().toString());
                    } else {
                        Toast.makeText(SearchResultActivity.this.getApplicationContext(), R.string.search_keyword, 1).show();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getResult(final String str) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Log.d("Index", "start=" + Calendar.getInstance().getTime().toString());
        progressBar.setVisibility(0);
        this.searchResultBeans.clear();
        this.mResultNumTextView.setText(getString(R.string.search_result) + " : ");
        Result_BibleAdapter result_BibleAdapter = this.bibleAdapter;
        if (result_BibleAdapter != null) {
            result_BibleAdapter.notifyDataSetChanged();
        }
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$SearchResultActivity$2YSiBjd0pqVOX2XV1vbK0q65XvM
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$getResult$0$SearchResultActivity(str);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$SearchResultActivity$qIGiO1fhs67gd6DfplGdYJx9iGQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$getResult$1$SearchResultActivity(progressBar, (List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$getResult$0$SearchResultActivity(String str) throws Exception {
        List<BibleDataBase> searchResult;
        try {
            SQLiteDatabase.loadLibs(this);
            new UseDatabaseHandler(this);
            UseDatabaseHandler useDatabaseHandler = UseDatabaseHandler.getInstance(this);
            useDatabaseHandler.open(app.getSqKey(this));
            if (getIntent().getExtras().getInt("category") == -1) {
                searchResult = useDatabaseHandler.getSearchResult(new ArabicNormalizer(str).getOutput(), getIntent().getExtras().getInt("bible_id"));
            } else if (getIntent().getExtras().getInt("category") == 0) {
                searchResult = useDatabaseHandler.getSearchResult(new ArabicNormalizer(str).getOutput());
            } else if (getIntent().getExtras().getInt("category") == 1) {
                searchResult = useDatabaseHandler.getSearchResult(new ArabicNormalizer(str).getOutput(), true);
            } else {
                searchResult = getIntent().getExtras().getInt("category") == 2 ? useDatabaseHandler.getSearchResult(new ArabicNormalizer(str).getOutput(), false) : null;
            }
            Log.d("result=", "len=" + searchResult.size());
            useDatabaseHandler.close();
            return searchResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public /* synthetic */ void lambda$getResult$1$SearchResultActivity(ProgressBar progressBar, List list) throws Exception {
        Log.d("Index", "end=" + Calendar.getInstance().getTime().toString());
        progressBar.setVisibility(8);
        if (list == null) {
            Toast.makeText(getApplicationContext(), R.string.no_search_result, 0).show();
            this.mResultNumTextView.setText(getString(R.string.no_search_result));
            return;
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            this.searchResultBeans.add((BibleDataBase) it2.next());
        }
        progressBar.setVisibility(8);
        Result_BibleAdapter result_BibleAdapter = new Result_BibleAdapter(this.ctx, R.layout.bible_result_row_item, this.searchResultBeans);
        this.bibleAdapter = result_BibleAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) result_BibleAdapter);
        if (this.searchResultBeans.size() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_search_result, 1).show();
            this.mResultNumTextView.setText(getString(R.string.no_search_result));
        } else {
            this.mResultNumTextView.setText(getString(R.string.search_result) + " : " + this.searchResultBeans.size());
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

    public final class ArabicNormalizer {
        private String input;
        private final String output = normalize();

        public ArabicNormalizer(String str) {
            this.input = str;
        }

        private String normalize() {
            String strReplaceAll = this.input.replaceAll("ؕ", "");
            this.input = strReplaceAll;
            String strReplaceAll2 = strReplaceAll.replaceAll("ؖ", "");
            this.input = strReplaceAll2;
            String strReplaceAll3 = strReplaceAll2.replaceAll("ؗ", "");
            this.input = strReplaceAll3;
            String strReplaceAll4 = strReplaceAll3.replaceAll("ؘ", "");
            this.input = strReplaceAll4;
            String strReplaceAll5 = strReplaceAll4.replaceAll("ؙ", "");
            this.input = strReplaceAll5;
            String strReplaceAll6 = strReplaceAll5.replaceAll("ؚ", "");
            this.input = strReplaceAll6;
            String strReplaceAll7 = strReplaceAll6.replaceAll("ۖ", "");
            this.input = strReplaceAll7;
            String strReplaceAll8 = strReplaceAll7.replaceAll("ۗ", "");
            this.input = strReplaceAll8;
            String strReplaceAll9 = strReplaceAll8.replaceAll("ۘ", "");
            this.input = strReplaceAll9;
            String strReplaceAll10 = strReplaceAll9.replaceAll("ۙ", "");
            this.input = strReplaceAll10;
            String strReplaceAll11 = strReplaceAll10.replaceAll("ۚ", "");
            this.input = strReplaceAll11;
            String strReplaceAll12 = strReplaceAll11.replaceAll("ۛ", "");
            this.input = strReplaceAll12;
            String strReplaceAll13 = strReplaceAll12.replaceAll("ۜ", "");
            this.input = strReplaceAll13;
            String strReplaceAll14 = strReplaceAll13.replaceAll("\u06dd", "");
            this.input = strReplaceAll14;
            String strReplaceAll15 = strReplaceAll14.replaceAll("۞", "");
            this.input = strReplaceAll15;
            String strReplaceAll16 = strReplaceAll15.replaceAll("۟", "");
            this.input = strReplaceAll16;
            String strReplaceAll17 = strReplaceAll16.replaceAll("۠", "");
            this.input = strReplaceAll17;
            String strReplaceAll18 = strReplaceAll17.replaceAll("ۡ", "");
            this.input = strReplaceAll18;
            String strReplaceAll19 = strReplaceAll18.replaceAll("ۢ", "");
            this.input = strReplaceAll19;
            String strReplaceAll20 = strReplaceAll19.replaceAll("ۣ", "");
            this.input = strReplaceAll20;
            String strReplaceAll21 = strReplaceAll20.replaceAll("ۤ", "");
            this.input = strReplaceAll21;
            String strReplaceAll22 = strReplaceAll21.replaceAll("ۥ", "");
            this.input = strReplaceAll22;
            String strReplaceAll23 = strReplaceAll22.replaceAll("ۦ", "");
            this.input = strReplaceAll23;
            String strReplaceAll24 = strReplaceAll23.replaceAll("ۧ", "");
            this.input = strReplaceAll24;
            String strReplaceAll25 = strReplaceAll24.replaceAll("ۨ", "");
            this.input = strReplaceAll25;
            String strReplaceAll26 = strReplaceAll25.replaceAll("۩", "");
            this.input = strReplaceAll26;
            String strReplaceAll27 = strReplaceAll26.replaceAll("۪", "");
            this.input = strReplaceAll27;
            String strReplaceAll28 = strReplaceAll27.replaceAll("۫", "");
            this.input = strReplaceAll28;
            String strReplaceAll29 = strReplaceAll28.replaceAll("۬", "");
            this.input = strReplaceAll29;
            String strReplaceAll30 = strReplaceAll29.replaceAll("ۭ", "");
            this.input = strReplaceAll30;
            String strReplaceAll31 = strReplaceAll30.replaceAll("ـ", "");
            this.input = strReplaceAll31;
            String strReplaceAll32 = strReplaceAll31.replaceAll("ً", "");
            this.input = strReplaceAll32;
            String strReplaceAll33 = strReplaceAll32.replaceAll("ٌ", "");
            this.input = strReplaceAll33;
            String strReplaceAll34 = strReplaceAll33.replaceAll("ٍ", "");
            this.input = strReplaceAll34;
            String strReplaceAll35 = strReplaceAll34.replaceAll("َ", "");
            this.input = strReplaceAll35;
            String strReplaceAll36 = strReplaceAll35.replaceAll("ُ", "");
            this.input = strReplaceAll36;
            String strReplaceAll37 = strReplaceAll36.replaceAll("ِ", "");
            this.input = strReplaceAll37;
            String strReplaceAll38 = strReplaceAll37.replaceAll("ّ", "");
            this.input = strReplaceAll38;
            String strReplaceAll39 = strReplaceAll38.replaceAll("ْ", "");
            this.input = strReplaceAll39;
            String strReplaceAll40 = strReplaceAll39.replaceAll("ٓ", "");
            this.input = strReplaceAll40;
            String strReplaceAll41 = strReplaceAll40.replaceAll("ٔ", "");
            this.input = strReplaceAll41;
            String strReplaceAll42 = strReplaceAll41.replaceAll("ٕ", "");
            this.input = strReplaceAll42;
            String strReplaceAll43 = strReplaceAll42.replaceAll("ٖ", "");
            this.input = strReplaceAll43;
            String strReplaceAll44 = strReplaceAll43.replaceAll("ٗ", "");
            this.input = strReplaceAll44;
            String strReplaceAll45 = strReplaceAll44.replaceAll("٘", "");
            this.input = strReplaceAll45;
            String strReplaceAll46 = strReplaceAll45.replaceAll("ٙ", "");
            this.input = strReplaceAll46;
            String strReplaceAll47 = strReplaceAll46.replaceAll("ٚ", "");
            this.input = strReplaceAll47;
            String strReplaceAll48 = strReplaceAll47.replaceAll("ٛ", "");
            this.input = strReplaceAll48;
            String strReplaceAll49 = strReplaceAll48.replaceAll("ٜ", "");
            this.input = strReplaceAll49;
            String strReplaceAll50 = strReplaceAll49.replaceAll("ٝ", "");
            this.input = strReplaceAll50;
            String strReplaceAll51 = strReplaceAll50.replaceAll("ٞ", "");
            this.input = strReplaceAll51;
            String strReplaceAll52 = strReplaceAll51.replaceAll("ٟ", "");
            this.input = strReplaceAll52;
            String strReplaceAll53 = strReplaceAll52.replaceAll("ٰ", "");
            this.input = strReplaceAll53;
            String strReplaceAll54 = strReplaceAll53.replaceAll("ؤ", "و");
            this.input = strReplaceAll54;
            String strReplaceAll55 = strReplaceAll54.replaceAll("ة", "ه");
            this.input = strReplaceAll55;
            String strReplaceAll56 = strReplaceAll55.replaceAll("ي", "ى");
            this.input = strReplaceAll56;
            String strReplaceAll57 = strReplaceAll56.replaceAll("ئ", "ى");
            this.input = strReplaceAll57;
            String strReplaceAll58 = strReplaceAll57.replaceAll("آ", "ا");
            this.input = strReplaceAll58;
            String strReplaceAll59 = strReplaceAll58.replaceAll("أ", "ا");
            this.input = strReplaceAll59;
            String strReplaceAll60 = strReplaceAll59.replaceAll("إ", "ا");
            this.input = strReplaceAll60;
            return strReplaceAll60;
        }

        public String getOutput() {
            return this.output;
        }
    }
}
