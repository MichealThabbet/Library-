package com.app.piapostolos;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.checksim;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class SendEmailActivity extends BaseActivity {
    Button buttonSend;
    private GestureDetector gestureDetector;
    EditText textMessage;
    EditText textSubject;
    String type;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.raselnamain);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        Intent intent = getIntent();
        this.type = intent.getStringExtra("email");
        textView.setText(intent.getStringExtra("preact"));
        getWindow().addFlags(128);
        new checksim();
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.SendEmailActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.buttonSend = (Button) findViewById(R.id.buttonSend);
        this.textSubject = (EditText) findViewById(R.id.editTextSubject);
        this.textMessage = (EditText) findViewById(R.id.editTextMessage);
        this.buttonSend.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.SendEmailActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String str = SendEmailActivity.this.type;
                String string = SendEmailActivity.this.textSubject.getText().toString();
                String string2 = SendEmailActivity.this.textMessage.getText().toString();
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.putExtra("android.intent.extra.EMAIL", new String[]{str});
                intent2.putExtra("android.intent.extra.SUBJECT", string);
                intent2.putExtra("android.intent.extra.TEXT", string2);
                intent2.setType("message/rfc822");
                Boolean.valueOf(false);
                if (Boolean.valueOf(new ConnectionDetector(SendEmailActivity.this.getApplicationContext()).isConnectingToInternet()).booleanValue()) {
                    SendEmailActivity.this.startActivity(Intent.createChooser(intent2, "Choose an Email client :"));
                    SendEmailActivity.this.finish();
                } else {
                    new AlertDialogManager().showAlertDialog(SendEmailActivity.this, "No Internet Connection", "You don't have internet connection.", false);
                    SendEmailActivity.this.startActivity(new Intent(SendEmailActivity.this, (Class<?>) MainActivity.class));
                }
            }
        });
    }
}
