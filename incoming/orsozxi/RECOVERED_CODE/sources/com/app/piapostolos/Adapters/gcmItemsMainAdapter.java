package com.app.piapostolos.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.piapostolos.Beans.gcm_msgs;
import com.app.piapostolos.R;
import java.util.ArrayList;
import org.jsoup.Jsoup;

/* loaded from: classes.dex */
public class gcmItemsMainAdapter extends BaseAdapter {
    private Activity activity;
    private gcm_msgs comment;
    private ArrayList<gcm_msgs> comments;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public gcmItemsMainAdapter(Activity activity, ArrayList<gcm_msgs> arrayList) {
        this.comments = arrayList;
        this.activity = activity;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.comments.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.comments.get(i);
    }

    public static String html2text(String str) {
        return Jsoup.parse(str).text();
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.activity).inflate(R.layout.gcmmsgs, viewGroup, false);
        this.comment = this.comments.get(i);
        TextView textView = (TextView) viewInflate.findViewById(R.id.gcm_msg);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.gcmimg);
        textView.setText(html2text(this.comment.getMsg()));
        System.out.println("comment:-" + ((Object) textView.getText()));
        if (this.comment.getImagelink() != null) {
            if (this.comment.getImagelink().equals("https://www.orsozoxi.net/bprsms/images/bpr.jpg")) {
                imageView.setImageResource(R.drawable.saydnalogo);
            }
            if (this.comment.getImagelink().equals("https://www.orsozoxi.net/gcmsms/images/orsozoxisms.jpg")) {
                imageView.setImageResource(R.drawable.orsologo);
            }
            if (this.comment.getImagelink().equals("https://www.orsozoxi.net/gcmnews/images/news.jpg")) {
                imageView.setImageResource(R.drawable.newsslogo);
            }
            viewInflate.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Adapters.gcmItemsMainAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(gcmItemsMainAdapter.this.activity);
                    builder.setTitle("هل تريد حذف الرسالة ؟");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Adapters.gcmItemsMainAdapter.1.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) throws SQLException {
                            gcmItemsMainAdapter.this.comment = (gcm_msgs) gcmItemsMainAdapter.this.comments.get(i);
                            gcmItemsMainAdapter.this.getallmsgsAnddeleteold(gcmItemsMainAdapter.this.comment.getMsg(), i);
                        }
                    });
                    builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Adapters.gcmItemsMainAdapter.1.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                        }
                    });
                    builder.show();
                }
            });
        }
        return viewInflate;
    }

    public void getallmsgsAnddeleteold(String str, int i) throws SQLException {
        String string;
        int i2;
        DBAdapter dBAdapter = new DBAdapter(this.activity);
        dBAdapter.open();
        Cursor cursorFetchAll = dBAdapter.fetchAll();
        if (cursorFetchAll.moveToFirst()) {
            int i3 = 0;
            string = "";
            i2 = -1;
            do {
                if (cursorFetchAll.getString(1).equals(str)) {
                    string = cursorFetchAll.getString(1);
                    i2 = i3;
                }
                i3++;
            } while (cursorFetchAll.moveToNext());
        } else {
            string = "";
            i2 = -1;
        }
        dBAdapter.close();
        if (i2 != -1) {
            DBAdapter dBAdapter2 = new DBAdapter(this.activity);
            dBAdapter2.open();
            if (!dBAdapter2.fetchOne(string).equals("")) {
                dBAdapter2.deleteone(Integer.valueOf(dBAdapter2.fetchOne(string)).intValue());
            }
            Log.d("orso2", "delete=" + string);
            dBAdapter2.close();
            this.comments.remove(i);
            notifyDataSetChanged();
        }
    }
}
