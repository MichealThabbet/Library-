package com.app.piapostolos;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Others.AlertDialogManager;
import com.app.piapostolos.Others.ConnectionDetector;
import com.app.piapostolos.Others.checksim;
import com.navdrawer.SimpleSideDrawer;

/* loaded from: classes.dex */
public class SendEmailActivityOptions extends BaseActivity {
    Button buttonSend;
    private GestureDetector gestureDetector;
    EditText textMessage;
    EditText textSubject;
    EditText textTo;
    String type;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.raselnamainoptions);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        getWindow().addFlags(128);
        new checksim();
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        Intent intent = getIntent();
        this.type = intent.getStringExtra("email");
        this.type = "contact@example.invalid";
        textView.setText(intent.getStringExtra("preact"));
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.SendEmailActivityOptions.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        ((RadioGroup) findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.app.piapostolos.SendEmailActivityOptions.2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.r_report) {
                    SendEmailActivityOptions.this.type = "contact@example.invalid";
                    return;
                }
                if (i == R.id.r_suggest) {
                    SendEmailActivityOptions.this.type = "contact@example.invalid";
                } else if (i == R.id.r_ask) {
                    SendEmailActivityOptions.this.type = "contact@example.invalid";
                } else {
                    SendEmailActivityOptions.this.type = "contact@example.invalid";
                }
            }
        });
        this.buttonSend = (Button) findViewById(R.id.buttonSend);
        this.textSubject = (EditText) findViewById(R.id.editTextSubject);
        this.textMessage = (EditText) findViewById(R.id.editTextMessage);
        this.buttonSend.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.SendEmailActivityOptions.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String str = SendEmailActivityOptions.this.type;
                String string = SendEmailActivityOptions.this.textSubject.getText().toString();
                String string2 = SendEmailActivityOptions.this.textMessage.getText().toString();
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.putExtra("android.intent.extra.EMAIL", new String[]{str});
                intent2.putExtra("android.intent.extra.SUBJECT", string);
                intent2.putExtra("android.intent.extra.TEXT", string2);
                intent2.setType("message/rfc822");
                Boolean.valueOf(false);
                if (Boolean.valueOf(new ConnectionDetector(SendEmailActivityOptions.this.getApplicationContext()).isConnectingToInternet()).booleanValue()) {
                    SendEmailActivityOptions.this.startActivity(Intent.createChooser(intent2, "Choose an Email client :"));
                    SendEmailActivityOptions.this.finish();
                } else {
                    new AlertDialogManager().showAlertDialog(SendEmailActivityOptions.this, "No Internet Connection", "You don't have internet connection.", false);
                    SendEmailActivityOptions.this.startActivity(new Intent(SendEmailActivityOptions.this, (Class<?>) MainActivity.class));
                }
            }
        });
    }
}
