package com.app.piapostolos;

import android.app.Activity;
import android.widget.ExpandableListView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class Slider {
    public static ExpandableListView expListView;
    public static ExpandableListAdapter listAdapter;
    public static HashMap<String, List<String>> listDataChild;
    public static List<String> listDataHeader;
    public static ArrayList<Integer> shareListDrawable;
    public static ArrayList<String> shareListTitle;

    public static void prepareListData(Activity activity, int i) {
        prepareListData(activity);
    }

    public static void prepareListData(Activity activity) {
        listDataHeader = new ArrayList();
        listDataChild = new HashMap<>();
        listDataHeader.add(activity.getString(R.string.m_bible));
        listDataHeader.add(activity.getString(R.string.m_mangalya));
        listDataHeader.add(activity.getString(R.string.m_konoz));
        listDataHeader.add(activity.getString(R.string.m_media));
        listDataHeader.add(activity.getString(R.string.m_follow_us));
        listDataHeader.add(activity.getString(R.string.notes_book));
        listDataHeader.add(activity.getString(R.string.m_settings));
        ArrayList arrayList = new ArrayList();
        arrayList.add(activity.getString(R.string.t_old_bible));
        arrayList.add(activity.getString(R.string.t_new_bible));
        arrayList.add(activity.getString(R.string.t_bible_one_year));
        arrayList.add(activity.getString(R.string.t_bible_two_years));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(activity.getString(R.string.t_kholagy));
        arrayList2.add(activity.getString(R.string.title_activity_agbya));
        arrayList2.add(activity.getString(R.string.t_katamars));
        arrayList2.add(activity.getString(R.string.t_sanksar));
        arrayList2.add(activity.getString(R.string.t_apsalomdy_sanawy));
        arrayList2.add(activity.getString(R.string.t_apsalomdy_kiahk));
        arrayList2.add(activity.getString(R.string.t_week_alam));
        arrayList2.add(activity.getString(R.string.kandil_prays));
        arrayList2.add(activity.getString(R.string.lakan_prays));
        arrayList2.add(activity.getString(R.string.sagda_prays));
        arrayList2.add(activity.getString(R.string.kism_prays));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(activity.getString(R.string.t_church_history));
        arrayList3.add(activity.getString(R.string.taks_church));
        arrayList3.add(activity.getString(R.string.akwal_title));
        arrayList3.add(activity.getString(R.string.tip_in_word));
        arrayList3.add(activity.getString(R.string.t_true_ayah));
        arrayList3.add(activity.getString(R.string.t_mdayh));
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(activity.getString(R.string.eids_cards));
        arrayList4.add(activity.getString(R.string.t_radio));
        arrayList4.add(activity.getString(R.string.t_youtube));
        arrayList4.add(activity.getString(R.string.t_games));
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(activity.getString(R.string.t_facebook));
        arrayList5.add(activity.getString(R.string.t_twitter));
        arrayList5.add(activity.getString(R.string.t_website));
        arrayList5.add(activity.getString(R.string.t_raslna));
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(activity.getString(R.string.notes_book));
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add(activity.getString(R.string.s_font));
        arrayList7.add(activity.getString(R.string.s_etraf));
        arrayList7.add(activity.getString(R.string.s_daily_msg));
        arrayList7.add(activity.getString(R.string.s_receive_news));
        arrayList7.add(activity.getString(R.string.s_background));
        arrayList7.add(activity.getString(R.string.s_agenda));
        arrayList7.add(activity.getString(R.string.s_share));
        listDataChild.put(listDataHeader.get(0), arrayList);
        listDataChild.put(listDataHeader.get(1), arrayList2);
        listDataChild.put(listDataHeader.get(2), arrayList3);
        listDataChild.put(listDataHeader.get(3), arrayList4);
        listDataChild.put(listDataHeader.get(4), arrayList5);
        listDataChild.put(listDataHeader.get(5), arrayList6);
        listDataChild.put(listDataHeader.get(6), arrayList7);
    }
}
