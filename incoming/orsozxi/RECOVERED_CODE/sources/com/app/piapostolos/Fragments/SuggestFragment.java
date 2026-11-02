package com.app.piapostolos.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.app.piapostolos.R;

/* loaded from: classes.dex */
public class SuggestFragment extends Fragment {
    Button buttonSend;
    private View rootView;
    EditText textMessage;
    EditText textSubject;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.fragment_suggest, viewGroup, false);
        initView();
        return this.rootView;
    }

    private void initView() {
        this.buttonSend = (Button) this.rootView.findViewById(R.id.buttonSend);
        this.textSubject = (EditText) this.rootView.findViewById(R.id.editTextSubject);
        this.textMessage = (EditText) this.rootView.findViewById(R.id.editTextMessage);
        this.buttonSend.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Fragments.SuggestFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = SuggestFragment.this.textSubject.getText().toString();
                String string2 = SuggestFragment.this.textMessage.getText().toString();
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                    Toast.makeText(SuggestFragment.this.getActivity(), R.string.please_fill, 1).show();
                    return;
                }
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"contact@example.invalid"});
                intent.putExtra("android.intent.extra.SUBJECT", string);
                intent.putExtra("android.intent.extra.TEXT", string2);
                intent.setType("message/rfc822");
                Boolean.valueOf(false);
                SuggestFragment.this.startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
    }
}
