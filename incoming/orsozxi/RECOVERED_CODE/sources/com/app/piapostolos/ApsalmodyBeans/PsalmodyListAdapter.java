package com.app.piapostolos.ApsalmodyBeans;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.piapostolos.ApsalmodyMultiSelectListFavouriteActivity;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.R;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class PsalmodyListAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<String> daysList;
    private ArrayList<int[]> favouriteList;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public PsalmodyListAdapter(Context context, ArrayList<String> arrayList, ArrayList<int[]> arrayList2) {
        this.daysList = arrayList;
        this.activity = context;
        this.favouriteList = arrayList2;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.daysList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.daysList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.psaltem, viewGroup, false);
        TextView textView = (TextView) viewGroup2.findViewById(R.id.tv_title);
        textView.setText(this.daysList.get(i));
        textView.setTextSize(Float.valueOf(new checksim().checkfontsize(this.activity)).floatValue());
        if (i > 7) {
            ImageView imageView = (ImageView) viewGroup2.findViewById(R.id.btn_edit);
            ImageView imageView2 = (ImageView) viewGroup2.findViewById(R.id.btn_share);
            ImageView imageView3 = (ImageView) viewGroup2.findViewById(R.id.btn_delete);
            imageView.setVisibility(0);
            imageView2.setVisibility(0);
            imageView3.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyBeans.PsalmodyListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Intent intent = new Intent(PsalmodyListAdapter.this.activity, (Class<?>) ApsalmodyMultiSelectListFavouriteActivity.class);
                    intent.putExtra("isAdd", false);
                    intent.putExtra("id", i - 8);
                    PsalmodyListAdapter.this.activity.startActivity(intent);
                }
            });
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyBeans.PsalmodyListAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PsalmodyListAdapter.this.activity);
                    builder.setTitle("هل تريد حذف القائمة ؟");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyBeans.PsalmodyListAdapter.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            FavouritListEntity favouritListEntity = (FavouritListEntity) new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouritListEntity.class).load();
                            if (favouritListEntity != null) {
                                favouritListEntity.getFavouriteList().remove(i - 8);
                            }
                            FavouriteNames favouriteNames = (FavouriteNames) new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouriteNames.class).load();
                            if (favouriteNames != null) {
                                favouriteNames.getFavouriteListsNames().remove(i - 8);
                            }
                            new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouritListEntity.class).save(favouritListEntity);
                            new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouriteNames.class).save(favouriteNames);
                            ArrayList arrayList = new ArrayList();
                            arrayList.add("إبصلمودية الأحد");
                            arrayList.add("إبصلمودية الأثنين");
                            arrayList.add("إبصلمودية الثلاثاء");
                            arrayList.add("إبصلمودية الأربعاء");
                            arrayList.add("إبصلمودية الخميس");
                            arrayList.add("إبصلمودية الجمعة");
                            arrayList.add("إبصلمودية السبت");
                            arrayList.add("الإبصلمودية الكيهكية");
                            FavouritListEntity favouritListEntity2 = (FavouritListEntity) new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouritListEntity.class).load();
                            FavouriteNames favouriteNames2 = (FavouriteNames) new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouriteNames.class).load();
                            if (favouritListEntity2 != null && favouritListEntity2.getFavouriteList().size() > 0) {
                                for (int i3 = 0; i3 < favouritListEntity2.getFavouriteList().size(); i3++) {
                                    if (favouriteNames2 != null) {
                                        arrayList.add(favouriteNames2.getFavouriteListsNames().get(i3));
                                    } else {
                                        arrayList.add("إبصلمودية " + (i3 + 1));
                                    }
                                }
                            }
                            PsalmodyListAdapter.this.daysList.remove(i);
                            PsalmodyListAdapter.this.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyBeans.PsalmodyListAdapter.2.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                        }
                    });
                    builder.show();
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.ApsalmodyBeans.PsalmodyListAdapter.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) throws IOException {
                    Apsalmody.readsheetAll(PsalmodyListAdapter.this.activity);
                    FavouritListEntity favouritListEntity = (FavouritListEntity) new SavePrefs(PsalmodyListAdapter.this.activity, (Class<?>) FavouritListEntity.class).load();
                    String str = "قائمتى المفضلة للإبصلمودية الكيهكية هى\n";
                    if (favouritListEntity != null) {
                        for (int i2 = 0; i2 < favouritListEntity.getFavouriteList().get(i - 8).length; i2++) {
                            str = str + Apsalmody.allNames[favouritListEntity.getFavouriteList().get(i - 8)[i2] - 1] + "\n";
                        }
                    }
                    String str2 = (str + "الأن حمل تطبيق PI APOSTOLOS و تابع معنا الإبصلمودية الكيهكية\n") + "www.orsozoxi.net";
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.SEND");
                    intent.setType(HTTP.PLAIN_TEXT_TYPE);
                    intent.putExtra("android.intent.extra.TEXT", str2);
                    PsalmodyListAdapter.this.activity.startActivity(Intent.createChooser(intent, "نشر القائمة المفضلة"));
                }
            });
        }
        return viewGroup2;
    }
}
