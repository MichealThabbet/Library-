package com.app.piapostolos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.facebook.share.internal.ShareConstants;
import com.ibm.icu.util.CopticCalendar;
import com.navdrawer.SimpleSideDrawer;
import java.util.Calendar;

/* loaded from: classes.dex */
public class MainActivity_psalmody_sanawy_menu extends BaseActivity {
    private Calendar mTodayCalendar;
    private CopticCalendar mTodayCopticCalendar;

    private String dayOfWeek(int i) {
        return i == 1 ? "Sun" : i == 2 ? "Mon" : i == 3 ? "Tue" : i == 4 ? "Wed" : i == 5 ? "Thu" : i == 6 ? "Fri" : "Sat";
    }

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_psalmody_sanawy_index);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_apsalomdy_sanawy);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_psalmody_sanawy_menu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        getCalendarForCurrentDay(true);
        Button button2 = (Button) findViewById(R.id.btn_today_psalmody);
        button2.setText(getString(R.string.psalmody_day) + " " + dayOfWeekArabic(dayOfWeek(this.mTodayCalendar.get(7))));
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_psalmody_sanawy_menu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_psalmody_sanawy_menu.this, (Class<?>) MainActivity_psalmody_sanawy.class);
                intent.putExtra(ShareConstants.MEDIA_TYPE, "today");
                MainActivity_psalmody_sanawy_menu.this.startActivity(intent);
            }
        });
        findViewById(R.id.btn_saints_select).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_psalmody_sanawy_menu.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_psalmody_sanawy_menu.this.startActivity(new Intent(MainActivity_psalmody_sanawy_menu.this, (Class<?>) ApsalmodySaintsSelectionActivity.class));
            }
        });
    }

    private String dayOfWeekArabic(String str) {
        if (str.equals("Sun")) {
            return getString(R.string.sun);
        }
        if (str.equals("Mon")) {
            return getString(R.string.mon);
        }
        if (str.equals("Tue")) {
            return getString(R.string.tues);
        }
        if (str.equals("Wed")) {
            return getString(R.string.wed);
        }
        if (str.equals("Thu")) {
            return getString(R.string.thurs);
        }
        if (str.equals("Fri")) {
            return getString(R.string.fri);
        }
        return getString(R.string.satur);
    }

    private void getCalendarForCurrentDay(boolean z) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, calendar.get(11));
        CopticCalendar copticCalendar = new CopticCalendar();
        if (z && calendar.get(11) >= 17) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(11, calendar.get(11));
            CopticCalendar copticCalendar2 = new CopticCalendar();
            calendar2.set(6, calendar2.get(6) + 1);
            copticCalendar2.set(6, copticCalendar2.get(6) + 1);
            this.mTodayCalendar = calendar2;
            this.mTodayCopticCalendar = copticCalendar2;
            return;
        }
        this.mTodayCalendar = calendar;
        this.mTodayCopticCalendar = copticCalendar;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
    }
}
