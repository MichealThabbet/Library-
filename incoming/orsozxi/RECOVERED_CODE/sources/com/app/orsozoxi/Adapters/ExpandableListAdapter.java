package com.app.orsozoxi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.app.orsozoxi.AgpyaActivity;
import com.app.orsozoxi.AkwalActivity;
import com.app.orsozoxi.Alarm.AlarmActivity;
import com.app.orsozoxi.AppLeftSlider;
import com.app.orsozoxi.Atraf;
import com.app.orsozoxi.BackgroundActivationActivity;
import com.app.orsozoxi.Beans.FontPrefs;
import com.app.orsozoxi.Daily_bible;
import com.app.orsozoxi.Daily_bible_twoyears;
import com.app.orsozoxi.Games;
import com.app.orsozoxi.Katamaras;
import com.app.orsozoxi.MainActivityCards;
import com.app.orsozoxi.MainActivity_Bible;
import com.app.orsozoxi.MainActivity_PsalList;
import com.app.orsozoxi.MainActivity_dallal;
import com.app.orsozoxi.MainActivity_kholagy_menu;
import com.app.orsozoxi.MainActivity_mdaya7;
import com.app.orsozoxi.MainActivity_psalmody_sanawy_menu;
import com.app.orsozoxi.MainActivity_sanksar;
import com.app.orsozoxi.MainActivitysite_aya3t;
import com.app.orsozoxi.NewRadioActivity;
import com.app.orsozoxi.NewsActivationActivity;
import com.app.orsozoxi.Orsozoxi;
import com.app.orsozoxi.Others.AlertDialogManager;
import com.app.orsozoxi.Others.ConnectionDetector;
import com.app.orsozoxi.Others.Constants;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.R;
import com.app.orsozoxi.SendEmailActivityOptions;
import com.app.orsozoxi.TipInWordActivity;
import com.app.orsozoxi.Youtube.MainActivityYoutube;
import com.app.orsozoxi.zweilawebsite;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import java.util.HashMap;
import java.util.List;
import org.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private HashMap<String, List<String>> _listDataChild;
    private List<String> _listDataHeader;
    private Activity activity;
    private int def;
    private TextView text;

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        return i;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public ExpandableListAdapter(Activity activity, Context context, List<String> list, HashMap<String, List<String>> map) {
        this._context = context;
        this._listDataHeader = list;
        this._listDataChild = map;
        this.activity = activity;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int i, int i2) {
        return this._listDataChild.get(this._listDataHeader.get(i)).get(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(final int i, final int i2, boolean z, View view, ViewGroup viewGroup) {
        String str = (String) getChild(i, i2);
        if (view == null) {
            view = ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(R.layout.expandable_item, (ViewGroup) null);
        }
        ((TextView) view.findViewById(R.id.lblListItem)).setText(str);
        final AlertDialogManager alertDialogManager = new AlertDialogManager();
        view.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Adapters.ExpandableListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                switch (i) {
                    case 0:
                        int i3 = i2;
                        if (i3 != 0) {
                            if (i3 != 1) {
                                if (i3 != 2) {
                                    if (i3 == 3) {
                                        ExpandableListAdapter.this.closeMenu();
                                        ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) Daily_bible_twoyears.class));
                                        break;
                                    }
                                } else {
                                    ExpandableListAdapter.this.closeMenu();
                                    ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) Daily_bible.class));
                                    break;
                                }
                            } else {
                                ExpandableListAdapter.this.closeMenu();
                                Intent intent = new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_Bible.class);
                                intent.putExtra("isOld", false);
                                ExpandableListAdapter.this.activity.startActivity(intent);
                                break;
                            }
                        } else {
                            ExpandableListAdapter.this.closeMenu();
                            Intent intent2 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_Bible.class);
                            intent2.putExtra("isOld", true);
                            ExpandableListAdapter.this.activity.startActivity(intent2);
                            break;
                        }
                        break;
                    case 1:
                        int i4 = i2;
                        if (i4 != 0) {
                            if (i4 != 1) {
                                if (i4 != 2) {
                                    if (i4 != 3) {
                                        if (i4 != 4) {
                                            if (i4 != 5) {
                                                if (i4 != 6) {
                                                    if (i4 != 7) {
                                                        if (i4 != 8) {
                                                            if (i4 != 9) {
                                                                if (i4 == 10) {
                                                                    ExpandableListAdapter.this.closeMenu();
                                                                    Intent intent3 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AgpyaActivity.class);
                                                                    intent3.putExtra(ShareConstants.MEDIA_TYPE, "kism");
                                                                    ExpandableListAdapter.this.activity.startActivity(intent3);
                                                                    break;
                                                                }
                                                            } else {
                                                                ExpandableListAdapter.this.closeMenu();
                                                                Intent intent4 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AgpyaActivity.class);
                                                                intent4.putExtra(ShareConstants.MEDIA_TYPE, "sagda");
                                                                ExpandableListAdapter.this.activity.startActivity(intent4);
                                                                break;
                                                            }
                                                        } else {
                                                            ExpandableListAdapter.this.closeMenu();
                                                            Intent intent5 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AgpyaActivity.class);
                                                            intent5.putExtra(ShareConstants.MEDIA_TYPE, "lakan");
                                                            ExpandableListAdapter.this.activity.startActivity(intent5);
                                                            break;
                                                        }
                                                    } else {
                                                        ExpandableListAdapter.this.closeMenu();
                                                        Intent intent6 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AgpyaActivity.class);
                                                        intent6.putExtra(ShareConstants.MEDIA_TYPE, "kandil");
                                                        ExpandableListAdapter.this.activity.startActivity(intent6);
                                                        break;
                                                    }
                                                } else {
                                                    ExpandableListAdapter.this.closeMenu();
                                                    ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_dallal.class));
                                                    break;
                                                }
                                            } else {
                                                ExpandableListAdapter.this.closeMenu();
                                                ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_PsalList.class));
                                                break;
                                            }
                                        } else {
                                            ExpandableListAdapter.this.closeMenu();
                                            ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_psalmody_sanawy_menu.class));
                                            break;
                                        }
                                    } else {
                                        ExpandableListAdapter.this.closeMenu();
                                        ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_sanksar.class));
                                        break;
                                    }
                                } else {
                                    ExpandableListAdapter.this.closeMenu();
                                    ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) Katamaras.class));
                                    break;
                                }
                            } else {
                                ExpandableListAdapter.this.closeMenu();
                                Intent intent7 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AgpyaActivity.class);
                                intent7.putExtra(ShareConstants.MEDIA_TYPE, "agypa");
                                ExpandableListAdapter.this.activity.startActivity(intent7);
                                break;
                            }
                        } else {
                            ExpandableListAdapter.this.closeMenu();
                            ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_kholagy_menu.class));
                            break;
                        }
                        break;
                    case 2:
                        if (i2 == 0 && ExpandableListAdapter.this.checkinternet()) {
                            ExpandableListAdapter.this.closeMenu();
                            if (Orsozoxi.isPlay_Store()) {
                                Intent intent8 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivityYoutube.class);
                                intent8.putExtra("isHistory", true);
                                ExpandableListAdapter.this.activity.startActivity(intent8);
                                break;
                            } else {
                                Intent intent9 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) zweilawebsite.class);
                                intent9.putExtra("url", "https://www.youtube.com/channel/UCAga3fiKOaUy0xVAl2f856Q");
                                intent9.putExtra("preact", "برنامج H120");
                                intent9.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                                ExpandableListAdapter.this.activity.startActivity(intent9);
                                break;
                            }
                        } else {
                            int i5 = i2;
                            if (i5 != 1) {
                                if (i5 != 2) {
                                    if (i5 != 3) {
                                        if (i5 != 4) {
                                            if (i5 == 5) {
                                                ExpandableListAdapter.this.closeMenu();
                                                ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivity_mdaya7.class));
                                                break;
                                            } else if (!ExpandableListAdapter.this.checkinternet()) {
                                                alertDialogManager.showAlertDialog(ExpandableListAdapter.this.activity, "No Internet Connection", "يجب الإتصال بالإنترنت لمشاهدة هذا المحتوى", false);
                                                break;
                                            }
                                        } else {
                                            ExpandableListAdapter.this.closeMenu();
                                            Intent intent10 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivitysite_aya3t.class);
                                            intent10.putExtra("url", "https://orsozoxi.net/archives/category/trueaya/feed");
                                            ExpandableListAdapter.this.activity.startActivity(intent10);
                                            break;
                                        }
                                    } else {
                                        ExpandableListAdapter.this.closeMenu();
                                        ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) TipInWordActivity.class));
                                        break;
                                    }
                                } else {
                                    ExpandableListAdapter.this.closeMenu();
                                    Intent intent11 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AkwalActivity.class);
                                    intent11.putExtra("url", "https://www.orsozoxi.net/archives/category/konuz/akwaal/feed");
                                    ExpandableListAdapter.this.activity.startActivity(intent11);
                                    break;
                                }
                            } else {
                                ExpandableListAdapter.this.closeMenu();
                                Intent intent12 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) AgpyaActivity.class);
                                intent12.putExtra(ShareConstants.MEDIA_TYPE, "taks");
                                ExpandableListAdapter.this.activity.startActivity(intent12);
                                break;
                            }
                        }
                        break;
                    case 3:
                        int i6 = i2;
                        if (i6 == 0) {
                            Intent intent13 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivityCards.class);
                            intent13.putExtra("url", "https://www.orsozoxi.net/archives/category/cards/feed");
                            ExpandableListAdapter.this.activity.startActivity(intent13);
                            break;
                        } else if (i6 == 1 && ExpandableListAdapter.this.checkinternet()) {
                            ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) NewRadioActivity.class));
                            break;
                        } else if (i2 == 2 && ExpandableListAdapter.this.checkinternet()) {
                            if (Orsozoxi.isPlay_Store()) {
                                ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) MainActivityYoutube.class));
                                break;
                            } else {
                                Intent intent14 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) zweilawebsite.class);
                                intent14.putExtra("url", "https://www.youtube.com/channel/UCU6j31zEB-Bo-1vDWlQOF2g");
                                intent14.putExtra("preact", "قناة اليوتيوب");
                                intent14.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                                ExpandableListAdapter.this.activity.startActivity(intent14);
                                break;
                            }
                        } else if (i2 == 3) {
                            ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) Games.class));
                            break;
                        } else {
                            alertDialogManager.showAlertDialog(ExpandableListAdapter.this.activity, "No Internet Connection", "يجب الإتصال بالإنترنت لمشاهدة هذا المحتوى", false);
                            break;
                        }
                        break;
                    case 4:
                        if (i2 == 0 && ExpandableListAdapter.this.checkinternet()) {
                            Intent intent15 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) zweilawebsite.class);
                            intent15.putExtra("url", Constants.FACEBOOK_SHARE_LINK);
                            intent15.putExtra("preact", "فيسبوك");
                            intent15.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                            ExpandableListAdapter.this.activity.startActivity(intent15);
                            break;
                        } else if (i2 == 1 && ExpandableListAdapter.this.checkinternet()) {
                            Intent intent16 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) zweilawebsite.class);
                            intent16.putExtra("url", "https://mobile.twitter.com/orsozoxi");
                            intent16.putExtra("preact", "تويتر");
                            intent16.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                            ExpandableListAdapter.this.activity.startActivity(intent16);
                            break;
                        } else if (i2 == 2 && ExpandableListAdapter.this.checkinternet()) {
                            Intent intent17 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) zweilawebsite.class);
                            intent17.putExtra("url", "https://orsozoxi.net/");
                            intent17.putExtra("preact", "موقعنا");
                            intent17.putExtra(ShareConstants.MEDIA_TYPE, AppSettingsData.STATUS_NEW);
                            ExpandableListAdapter.this.activity.startActivity(intent17);
                            break;
                        } else if (i2 != 3 || !ExpandableListAdapter.this.checkinternet()) {
                            alertDialogManager.showAlertDialog(ExpandableListAdapter.this.activity, "No Internet Connection", "يجب الإتصال بالإنترنت لمشاهدة هذا المحتوى", false);
                            break;
                        } else {
                            Intent intent18 = new Intent(ExpandableListAdapter.this.activity, (Class<?>) SendEmailActivityOptions.class);
                            intent18.putExtra("email", "orsozoxi.app@orsozoxi.net");
                            intent18.putExtra("preact", "راسلنا");
                            ExpandableListAdapter.this.activity.startActivity(intent18);
                            break;
                        }
                        break;
                    case 5:
                        if (i2 == 0) {
                            ExpandableListAdapter.this.activity.startActivity(new Intent(ExpandableListAdapter.this.activity, (Class<?>) NotesMainActivity.class));
                            break;
                        }
                        break;
                    case 6:
                        int i7 = i2;
                        if (i7 != 0) {
                            if (i7 != 1) {
                                if (i7 != 2) {
                                    if (i7 != 3) {
                                        if (i7 != 4) {
                                            if (i7 != 5) {
                                                if (i7 == 6) {
                                                    ExpandableListAdapter.this.getUrl(8);
                                                    break;
                                                }
                                            } else {
                                                ExpandableListAdapter.this.getUrl(6);
                                                break;
                                            }
                                        } else {
                                            ExpandableListAdapter.this.getUrl(9);
                                            break;
                                        }
                                    } else {
                                        ExpandableListAdapter.this.getUrl(7);
                                        break;
                                    }
                                } else {
                                    ExpandableListAdapter.this.getUrl(10);
                                    break;
                                }
                            } else {
                                ExpandableListAdapter.this.getUrl(2);
                                break;
                            }
                        } else {
                            ExpandableListAdapter.this.getUrl(0);
                            break;
                        }
                        break;
                }
            }
        });
        return view;
    }

    public boolean checkinternet() {
        return new ConnectionDetector(this.activity.getApplicationContext()).isConnectingToInternet();
    }

    public void getUrl(int i) {
        String str;
        new DBAdapter_sim(this.activity.getApplicationContext());
        this.def = 25;
        if (i != 0) {
            if (i == 2) {
                this.activity.startActivity(new Intent(this.activity, (Class<?>) Atraf.class));
                return;
            }
            switch (i) {
                case 6:
                    Intent intent = new Intent(this.activity, (Class<?>) zweilawebsite.class);
                    intent.putExtra("url", "file:///android_asset/Agenda/2014.htm");
                    intent.putExtra(ShareConstants.MEDIA_TYPE, "agenda");
                    intent.putExtra("preact", "أجندة الأعياد");
                    this.activity.startActivity(intent);
                    break;
                case 7:
                    this.activity.startActivity(new Intent(this.activity, (Class<?>) NewsActivationActivity.class));
                    break;
                case 8:
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.SEND");
                    intent2.putExtra("android.intent.extra.TEXT", "حمل تطبيق أرثوذكسى+القطمارس على أجهزة الأندرويد وقريبا على أجهزة الأيفون وتابع معنا الإبصلمودية الكيهكية (قبطى - عربى - قبطى معرب )،وأيضا  ويحتوى على الكتاب المقدس كامل، الأجبية، السنكسار، قطمارس أسبوع الإلام (قبطى - عربى - قبطى معرب )، معلومات طقسية وأقوال آباء إلى جانب أخبار الكنيسة ورسائل الأنبا رافائيل اليومية\nhttps://play.google.com/store/apps/details?id=com.app.orsozoxi&hl=en");
                    intent2.setType(HTTP.PLAIN_TEXT_TYPE);
                    this.activity.startActivity(Intent.createChooser(intent2, "أرثوذكسى+القطمارس"));
                    break;
                case 9:
                    this.activity.startActivity(new Intent(this.activity, (Class<?>) BackgroundActivationActivity.class));
                    break;
                case 10:
                    this.activity.startActivity(new Intent(this.activity, (Class<?>) AlarmActivity.class));
                    break;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setTitle("تغيير الخط");
        LinearLayout linearLayout = new LinearLayout(this.activity);
        FontPrefs fontPrefs = (FontPrefs) new SavePrefs(this.activity, (Class<?>) FontPrefs.class).load();
        if (fontPrefs != null) {
            str = fontPrefs.getFontSize() + "";
        } else {
            FontPrefs fontPrefs2 = new FontPrefs();
            fontPrefs2.setFontSize(20);
            new SavePrefs(this.activity, (Class<?>) FontPrefs.class).save(fontPrefs2);
            str = "20";
        }
        linearLayout.setOrientation(1);
        TextView textView = new TextView(this.activity);
        this.text = textView;
        textView.setText("أرثوذكسى");
        this.text.setPadding(10, 10, 10, 10);
        this.text.setTextColor(SupportMenu.CATEGORY_MASK);
        SeekBar seekBar = new SeekBar(this.activity);
        if (!str.equals("")) {
            seekBar.setProgress(Integer.valueOf(str).intValue());
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.app.orsozoxi.Adapters.ExpandableListAdapter.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i2, boolean z) {
                ExpandableListAdapter.this.text.setTextSize(i2);
                ExpandableListAdapter.this.def = i2;
            }
        });
        linearLayout.addView(seekBar);
        linearLayout.addView(this.text);
        builder.setView(linearLayout);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.Adapters.ExpandableListAdapter.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (ExpandableListAdapter.this.def < 8) {
                    ExpandableListAdapter.this.def = 20;
                }
                FontPrefs fontPrefs3 = new FontPrefs();
                fontPrefs3.setFontSize(ExpandableListAdapter.this.def);
                new SavePrefs(ExpandableListAdapter.this.activity, (Class<?>) FontPrefs.class).save(fontPrefs3);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.Adapters.ExpandableListAdapter.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
            }
        });
        builder.show();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        return this._listDataChild.get(this._listDataHeader.get(i)).size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        String str = (String) getGroup(i);
        if (view == null) {
            view = ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(R.layout.expandable_group, (ViewGroup) null);
        }
        TextView textView = (TextView) view.findViewById(R.id.lblListHeader);
        textView.setTypeface(null, 1);
        textView.setText(str);
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeMenu() {
        if (AppLeftSlider.getCurrentActivity() != null) {
            AppLeftSlider.getCurrentActivity().openOptionsMenu();
        }
    }
}
