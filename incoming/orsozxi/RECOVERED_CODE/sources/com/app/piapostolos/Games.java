package com.app.piapostolos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class Games extends BaseActivity {
    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_games);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_games);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Games.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        findViewById(R.id.puzzle).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Games.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Games.this.startActivity(new Intent(Games.this, (Class<?>) PuzzleActivity.class));
            }
        });
        findViewById(R.id.menory).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Games.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Games.this.startActivity(new Intent(Games.this, (Class<?>) Manager.class));
            }
        });
    }
}
