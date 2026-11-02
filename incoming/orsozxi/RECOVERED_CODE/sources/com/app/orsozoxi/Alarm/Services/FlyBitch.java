package com.app.orsozoxi.Alarm.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.app.orsozoxi.Alarm.Alarm;
import com.app.orsozoxi.Alarm.AlarmActivity;
import com.app.orsozoxi.Alarm.Bubbles.BubbleBaseLayout;
import com.app.orsozoxi.Alarm.Bubbles.BubbleLayout;
import com.app.orsozoxi.Alarm.Bubbles.BubbleTrashLayout;
import com.app.orsozoxi.Alarm.Bubbles.BubblesLayoutCoordinator;
import com.app.orsozoxi.Others.FileExcel;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.R;
import com.facebook.places.model.PlaceFields;
import com.google.android.material.badge.BadgeDrawable;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.TimedUndoAdapter;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/* loaded from: classes.dex */
public class FlyBitch extends Service {
    private Alarm alarm;
    private BubblesServiceBinder binder = new BubblesServiceBinder();
    private List<BubbleLayout> bubbles = new ArrayList();
    private BubbleTrashLayout bubblesTrash;
    private Context context;
    private BubblesLayoutCoordinator layoutCoordinator;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private WindowManager windowManager;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Iterator<BubbleLayout> it2 = this.bubbles.iterator();
        while (it2.hasNext()) {
            recycleBubble(it2.next());
        }
        this.bubbles.clear();
        return super.onUnbind(intent);
    }

    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 27) {
            return Settings.canDrawOverlays(context);
        }
        if (Settings.canDrawOverlays(context)) {
            return true;
        }
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                return false;
            }
            View view = new View(context);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(0, 0, Build.VERSION.SDK_INT >= 26 ? R2.dimen.sp_datepicker_list_year_label_size : R2.dimen.notification_right_icon_size, 24, -2);
            view.setLayoutParams(layoutParams);
            windowManager.addView(view, layoutParams);
            windowManager.removeView(view);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (canDrawOverlays(this)) {
            getWindowManager();
            configureBubblesService();
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.context = this;
        if (canDrawOverlays(this)) {
            if (intent != null && !intent.getExtras().isEmpty()) {
                sendNotification("", i2);
                addNewBubble(i2);
                this.alarm = (Alarm) intent.getExtras().getSerializable("alarm");
                ((TelephonyManager) getSystemService(PlaceFields.PHONE)).listen(new PhoneStateListener() { // from class: com.app.orsozoxi.Alarm.Services.FlyBitch.1
                    @Override // android.telephony.PhoneStateListener
                    public void onCallStateChanged(int i3, String str) throws IllegalStateException {
                        try {
                            if (i3 == 0) {
                                Log.d(getClass().getSimpleName(), "Call State Idle");
                                if (FlyBitch.this.mediaPlayer != null) {
                                    FlyBitch.this.mediaPlayer.start();
                                }
                            } else if (i3 == 1) {
                                Log.d(getClass().getSimpleName(), "Incoming call: " + str);
                                if (FlyBitch.this.mediaPlayer != null) {
                                    FlyBitch.this.mediaPlayer.pause();
                                }
                            }
                        } catch (IllegalStateException unused) {
                        }
                        super.onCallStateChanged(i3, str);
                    }
                }, 32);
                startAlarm();
            }
        } else {
            stopService(new Intent(this, (Class<?>) FlyBitch.class));
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void startAlarm() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Alarm alarm = this.alarm;
        if (alarm != null && alarm.getVibrate().booleanValue()) {
            Vibrator vibrator = (Vibrator) getSystemService("vibrator");
            this.vibrator = vibrator;
            vibrator.vibrate(TimedUndoAdapter.DEFAULT_TIMEOUT_MS);
        }
        Alarm alarm2 = this.alarm;
        if (alarm2 == null || alarm2.getAlarmTonePath().equals("") || this.alarm.getAlarmTonePath().equals(null)) {
            return;
        }
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mediaPlayer = mediaPlayer;
        try {
            mediaPlayer.setVolume(1.0f, 1.0f);
            this.mediaPlayer.setDataSource(this, Uri.parse(this.alarm.getAlarmTonePath()));
            this.mediaPlayer.setAudioStreamType(4);
            this.mediaPlayer.setLooping(false);
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
        } catch (Exception unused) {
            this.mediaPlayer.release();
        }
    }

    private void sendNotification(String str, int i) {
        ((NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION)).notify(1, new NotificationCompat.Builder(this).setCategory(NotificationCompat.CATEGORY_PROMO).setContentTitle("أرثوذكسى+القطمارس").setContentText("الرسالة اليومية").setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setVisibility(1).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) AlarmActivity.class), 0)).setPriority(1).setDefaults(-1).build());
    }

    private void configureBubblesService() {
        addTrash(R.layout.bubble_trash_layout);
    }

    private void addNewBubble(int i) {
        final String excelFileAndReturnRandom = readExcelFileAndReturnRandom();
        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.bubble_layout, (ViewGroup) null);
        final ScrollView scrollView = (ScrollView) bubbleLayout.findViewById(R.id.scrollView1);
        bubbleLayout.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Alarm.Services.FlyBitch.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", excelFileAndReturnRandom + "\n\nتم ارسال الرسالة من تطبيق أرثوذكسى+القطمارس\nhttps://www.orsozoxi.net/");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                scrollView.setVisibility(8);
                FlyBitch.this.startActivity(Intent.createChooser(intent, "أرثوذكسى+القطمارس").setFlags(268468224));
            }
        });
        bubbleLayout.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Alarm.Services.FlyBitch.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (scrollView.getVisibility() == 0) {
                    scrollView.setVisibility(8);
                    return;
                }
                if (bubbleLayout.isShown()) {
                    FlyBitch.this.getWindowManager().updateViewLayout(bubbleLayout, FlyBitch.this.buildLayoutParamsForBubble(60, 20));
                    scrollView.setVisibility(0);
                    TextView textView = (TextView) bubbleLayout.findViewById(R.id.tv_message);
                    textView.setText(excelFileAndReturnRandom);
                    checksim checksimVar = new checksim();
                    if (FlyBitch.this.context != null) {
                        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(FlyBitch.this.context)).floatValue());
                    }
                    ((TextView) bubbleLayout.findViewById(R.id.tv_counter)).setVisibility(8);
                    try {
                        if (FlyBitch.this.vibrator != null) {
                            FlyBitch.this.vibrator.cancel();
                        }
                    } catch (Exception unused) {
                    }
                    if (FlyBitch.this.mediaPlayer != null) {
                        try {
                            FlyBitch.this.mediaPlayer.stop();
                        } catch (Exception unused2) {
                        }
                        try {
                            FlyBitch.this.mediaPlayer.release();
                            return;
                        } catch (Exception unused3) {
                            return;
                        }
                    }
                    return;
                }
                Log.d("chathead", "is show false");
            }
        });
        bubbleLayout.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() { // from class: com.app.orsozoxi.Alarm.Services.FlyBitch.4
            @Override // com.app.orsozoxi.Alarm.Bubbles.BubbleLayout.OnBubbleRemoveListener
            public void onBubbleRemoved(BubbleLayout bubbleLayout2) {
            }
        });
        addBubble(bubbleLayout, 60, 20, i);
        TextView textView = (TextView) bubbleLayout.findViewById(R.id.tv_counter);
        textView.setText("" + i);
        textView.setVisibility(8);
    }

    @Override // android.app.Service
    public void onDestroy() throws IllegalStateException {
        super.onDestroy();
        try {
            if (this.vibrator != null) {
                this.vibrator.cancel();
            }
        } catch (Exception unused) {
        }
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (Exception unused2) {
            }
            try {
                this.mediaPlayer.release();
            } catch (Exception unused3) {
            }
        }
        Iterator<BubbleLayout> it2 = this.bubbles.iterator();
        while (it2.hasNext()) {
            recycleBubble(it2.next());
        }
        this.bubbles.clear();
        stopService(new Intent(this, (Class<?>) FlyBitch.class));
    }

    private void recycleBubble(final BubbleLayout bubbleLayout) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.app.orsozoxi.Alarm.Services.FlyBitch.5
            @Override // java.lang.Runnable
            public void run() throws IllegalStateException {
                FlyBitch.this.getWindowManager().removeView(bubbleLayout);
                for (BubbleLayout bubbleLayout2 : FlyBitch.this.bubbles) {
                    if (bubbleLayout2 == bubbleLayout && bubbleLayout2.getId() == bubbleLayout.getId()) {
                        bubbleLayout.notifyBubbleRemoved();
                        FlyBitch.this.bubbles.remove(bubbleLayout2);
                        try {
                            if (FlyBitch.this.vibrator != null) {
                                FlyBitch.this.vibrator.cancel();
                            }
                        } catch (Exception unused) {
                        }
                        if (FlyBitch.this.mediaPlayer != null) {
                            try {
                                FlyBitch.this.mediaPlayer.stop();
                            } catch (Exception unused2) {
                            }
                            try {
                                FlyBitch.this.mediaPlayer.release();
                                return;
                            } catch (Exception unused3) {
                                return;
                            }
                        }
                        return;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WindowManager getWindowManager() {
        if (this.windowManager == null) {
            this.windowManager = (WindowManager) getSystemService("window");
        }
        return this.windowManager;
    }

    public void addBubble(BubbleLayout bubbleLayout, int i, int i2, int i3) {
        WindowManager.LayoutParams layoutParamsBuildLayoutParamsForBubble = buildLayoutParamsForBubble(i, i2);
        bubbleLayout.setWindowManager(getWindowManager());
        bubbleLayout.setViewParams(layoutParamsBuildLayoutParamsForBubble);
        bubbleLayout.setLayoutCoordinator(this.layoutCoordinator);
        bubbleLayout.setId(i3);
        this.bubbles.add(bubbleLayout);
        addViewToWindow(bubbleLayout);
    }

    void addTrash(int i) {
        if (i != 0) {
            BubbleTrashLayout bubbleTrashLayout = new BubbleTrashLayout(this);
            this.bubblesTrash = bubbleTrashLayout;
            bubbleTrashLayout.setWindowManager(this.windowManager);
            this.bubblesTrash.setViewParams(buildLayoutParamsForTrash());
            this.bubblesTrash.setVisibility(8);
            LayoutInflater.from(this).inflate(i, (ViewGroup) this.bubblesTrash, true);
            addViewToWindow(this.bubblesTrash);
            initializeLayoutCoordinator();
        }
    }

    private void initializeLayoutCoordinator() {
        this.layoutCoordinator = new BubblesLayoutCoordinator.Builder(this).setWindowManager(getWindowManager()).setTrashView(this.bubblesTrash).build();
    }

    private void addViewToWindow(final BubbleBaseLayout bubbleBaseLayout) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.app.orsozoxi.Alarm.Services.FlyBitch.6
            @Override // java.lang.Runnable
            public void run() {
                WindowManager windowManager = FlyBitch.this.getWindowManager();
                BubbleBaseLayout bubbleBaseLayout2 = bubbleBaseLayout;
                windowManager.addView(bubbleBaseLayout2, bubbleBaseLayout2.getViewParams());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WindowManager.LayoutParams buildLayoutParamsForBubble(int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, R2.dimen.notification_media_narrow_margin, 8, -2);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.x = i;
        layoutParams.y = i2;
        return layoutParams;
    }

    private WindowManager.LayoutParams buildLayoutParamsForTrash() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, R2.dimen.notification_media_narrow_margin, 8, -2);
        layoutParams.x = 0;
        layoutParams.y = 0;
        return layoutParams;
    }

    public void removeBubble(BubbleLayout bubbleLayout) {
        recycleBubble(bubbleLayout);
    }

    public class BubblesServiceBinder extends Binder {
        public BubblesServiceBinder() {
        }

        public FlyBitch getService() {
            return FlyBitch.this;
        }
    }

    public static boolean isExternalStorageReadOnly() {
        return "mounted_ro".equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    private String readExcelFileAndReturnRandom() {
        if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            ArrayList arrayList = new ArrayList();
            try {
                int i = 0;
                Iterator<Row> itRowIterator = new HSSFWorkbook(new POIFSFileSystem(getAssets().open("random.xls"))).getSheetAt(0).rowIterator();
                new FileExcel();
                ArrayList<String> arrayList2 = new ArrayList<>();
                while (itRowIterator.hasNext()) {
                    FileExcel fileExcel = new FileExcel();
                    fileExcel.setRow(i);
                    ArrayList<Integer> arrayList3 = new ArrayList<>();
                    Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                    while (itCellIterator.hasNext()) {
                        HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                        arrayList2.add(hSSFCell.toString());
                        arrayList3.add(Integer.valueOf(hSSFCell.getColumnIndex()));
                    }
                    fileExcel.setCellPos(arrayList3);
                    fileExcel.setCell(arrayList2);
                    arrayList.add(fileExcel);
                    i++;
                }
                return arrayList2.get(new Random().nextInt(arrayList2.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
