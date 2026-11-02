package com.app.piapostolos;

import android.app.Activity;
import android.app.Application;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.legacy.app.ActionBarDrawerToggle;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Beans.LeftSliderMenu;
import com.app.piapostolos.Views.ClickListenerForScrolling;
import com.app.piapostolos.Views.MenuDateView;
import com.app.piapostolos.Views.SizeCallbackForMenu;
import com.app.piapostolos.Views.UKHorizontalScrollView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class AppLeftSlider extends Application {
    private static Activity activityApp;
    public static Activity currentActivity;
    static ExpandableListView expListView;
    private static LeftSliderMenu leftSliderMenu;
    static ExpandableListAdapter listAdapter;
    static HashMap<String, List<String>> listDataChild;
    static List<String> listDataHeader;
    private static DrawerLayout mDrawerLayout;
    private static CharSequence mTitle;
    private static ArrayList<String> navDrawerItems;
    private static TypedArray navMenuIcons;
    private static String[] navMenuTitles;

    public static LeftSliderMenu getLeftSliderMenu() {
        return leftSliderMenu;
    }

    public static void setLeftSliderMenu(LeftSliderMenu leftSliderMenu2) {
        leftSliderMenu = leftSliderMenu2;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public static MenuDateView leftMenuSlider(Activity activity, Bundle bundle, View view, boolean z) {
        activityApp = activity;
        navMenuTitles = activity.getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = activityApp.getResources().obtainTypedArray(R.array.nav_drawer_icons);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(activity);
        View viewInflate = layoutInflaterFrom.inflate(R.layout.expandable_listview, (ViewGroup) null);
        mDrawerLayout = (DrawerLayout) activityApp.findViewById(R.id.drawer_layout);
        expListView = (ExpandableListView) viewInflate.findViewById(R.id.lvExp);
        navMenuIcons.recycle();
        prepareListData(activity);
        Activity activity2 = activityApp;
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(activity2, activity2, listDataHeader, listDataChild);
        listAdapter = expandableListAdapter;
        expListView.setAdapter(expandableListAdapter);
        UKHorizontalScrollView uKHorizontalScrollView = (UKHorizontalScrollView) layoutInflaterFrom.inflate(R.layout.home, (ViewGroup) null);
        final Button button = (Button) view.findViewById(R.id.btn_slide);
        new ActionBarDrawerToggle(activityApp, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) { // from class: com.app.piapostolos.AppLeftSlider.1
            @Override // androidx.legacy.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View view2) {
                AppLeftSlider.activityApp.getActionBar().setTitle(AppLeftSlider.mTitle);
                AppLeftSlider.activityApp.invalidateOptionsMenu();
            }

            @Override // androidx.legacy.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View view2) {
                AppLeftSlider.activityApp.getActionBar().setTitle("PiApostolos");
                AppLeftSlider.activityApp.invalidateOptionsMenu();
            }
        };
        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.piapostolos.AppLeftSlider.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                Button button2 = button;
                if (button2 != null) {
                    button2.performClick();
                }
            }
        });
        button.setOnClickListener(new ClickListenerForScrolling(uKHorizontalScrollView, viewInflate));
        uKHorizontalScrollView.initViews(new View[]{viewInflate, view}, 1, new SizeCallbackForMenu(button));
        return new MenuDateView(uKHorizontalScrollView, button);
    }

    private static void prepareListData(Activity activity) {
        listDataHeader = new ArrayList();
        listDataChild = new HashMap<>();
        listDataHeader.add("الكتاب المقدس");
        listDataHeader.add("المنجلية");
        listDataHeader.add("كنوز كنيستنا");
        listDataHeader.add("الميديا");
        listDataHeader.add("تابعنا");
        listDataHeader.add("الإعدادات");
        ArrayList arrayList = new ArrayList();
        arrayList.add("العهد القديم");
        arrayList.add("العهد الجديد");
        arrayList.add("الكتاب المقدس اليومى");
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("الخولاجى المقدس");
        arrayList2.add("الأجبية");
        arrayList2.add("القطمارس");
        arrayList2.add("السنكسار");
        arrayList2.add("الإبصلمودية الكيهكية");
        arrayList2.add("أسبوع الآلام");
        arrayList2.add("صلوات القنديل");
        arrayList2.add("صلوات اللقان");
        arrayList2.add("صلوات السجدة");
        arrayList2.add("صلوات القسم");
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("أقوال الأباء");
        arrayList3.add("طقس الكنيسة");
        arrayList3.add("الآية الصحيحة");
        arrayList3.add("مدايح القديسين");
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add("راديو أرثوذكسى");
        arrayList4.add("قناة اليوتيوب");
        arrayList4.add("الألعاب");
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add("الفيسبوك");
        arrayList5.add("التويتر");
        arrayList5.add("الموقع");
        arrayList5.add("راسلنا");
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add("ضبط الخط");
        arrayList6.add("ضبط منبة القداس");
        arrayList6.add("ضبط منبة الإعتراف");
        arrayList6.add("ضبط منبة الأجبية");
        arrayList6.add("إستقبال الأخبار");
        arrayList6.add("تفعيل الخلفية");
        arrayList6.add("أجندة الأعياد");
        arrayList6.add("نشر التطبيق");
        listDataChild.put(listDataHeader.get(0), arrayList);
        listDataChild.put(listDataHeader.get(1), arrayList2);
        listDataChild.put(listDataHeader.get(2), arrayList3);
        listDataChild.put(listDataHeader.get(3), arrayList4);
        listDataChild.put(listDataHeader.get(4), arrayList5);
        listDataChild.put(listDataHeader.get(5), arrayList6);
    }
}
