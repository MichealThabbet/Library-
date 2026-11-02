package com.app.piapostolos.Alarm.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.app.piapostolos.Alarm.Alarm;
import com.app.piapostolos.Alarm.AlarmActivity;
import com.app.piapostolos.Alarm.Bubbles.BubbleBaseLayout;
import com.app.piapostolos.Alarm.Bubbles.BubbleLayout;
import com.app.piapostolos.Alarm.Bubbles.BubbleTrashLayout;
import com.app.piapostolos.Alarm.Bubbles.BubblesLayoutCoordinator;
import com.app.piapostolos.Others.FileExcel;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.gson.Gson;
import com.huawei.hms.push.constant.RemoteMessageConst;
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
public class FlyBitchJobService extends JobIntentService {
    static final int JOB_ID = 1005;
    private static Alarm alarm;
    private BubblesServiceBinder binder = new BubblesServiceBinder();
    private List<BubbleLayout> bubbles = new ArrayList();
    private BubbleTrashLayout bubblesTrash;
    private Context context;
    private BubblesLayoutCoordinator layoutCoordinator;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private WindowManager windowManager;

    public class BubblesServiceBinder extends Binder {
        public BubblesServiceBinder() {
        }

        public FlyBitchJobService getService() {
            return FlyBitchJobService.this;
        }
    }

    public static void enqueueWork(Context context, Intent intent) {
        alarm = new Alarm();
        alarm = (Alarm) new Gson().fromJson(intent.getExtras().getString("alarm"), Alarm.class);
        enqueueWork(context, (Class<?>) FlyBitchJobService.class, 1005, intent);
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

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Alarm alarm2;
        this.context = this;
        String excelFileAndReturnRandom = readExcelFileAndReturnRandom();
        if (canDrawOverlays(this) && (alarm2 = alarm) != null) {
            sendNotification(excelFileAndReturnRandom, alarm2.getId());
            addNewBubble(alarm.getId(), excelFileAndReturnRandom);
            startAlarm();
        }
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
        if (canDrawOverlays(this)) {
            getWindowManager();
            configureBubblesService();
        }
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    private void startAlarm() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Alarm alarm2 = alarm;
        if (alarm2 != null && alarm2.getVibrate().booleanValue()) {
            Vibrator vibrator = (Vibrator) getSystemService("vibrator");
            this.vibrator = vibrator;
            vibrator.vibrate(1000L);
        }
        Alarm alarm3 = alarm;
        if (alarm3 == null || alarm3.getAlarmTonePath().equals("") || alarm.getAlarmTonePath().equals(null)) {
            return;
        }
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mediaPlayer = mediaPlayer;
        try {
            mediaPlayer.setVolume(1.0f, 1.0f);
            this.mediaPlayer.setDataSource(this, Uri.parse(alarm.getAlarmTonePath()));
            this.mediaPlayer.setAudioStreamType(4);
            this.mediaPlayer.setLooping(false);
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
        } catch (Exception unused) {
            this.mediaPlayer.release();
        }
    }

    private void sendNotification(String str, int i) {
        PendingIntent activity;
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("orso_id_01", "My Notifications", 3);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, "orso_id_01");
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) AlarmActivity.class), 335544320);
        } else {
            activity = PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) AlarmActivity.class), 0);
        }
        builder.setCategory(NotificationCompat.CATEGORY_PROMO).setContentTitle("الرسالة اليومية").setContentText(str).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setVisibility(1).setContentIntent(activity).setPriority(1).setDefaults(-1).build();
        notificationManager.notify(1, builder.build());
    }

    private void configureBubblesService() {
        addTrash(R.layout.bubble_trash_layout);
    }

    private void addNewBubble(int i, final String str) {
        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.bubble_layout, (ViewGroup) null);
        final ScrollView scrollView = (ScrollView) bubbleLayout.findViewById(R.id.scrollView1);
        bubbleLayout.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Alarm.Services.FlyBitchJobService.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", str + "\n\nتم ارسال الرسالة من تطبيق PI APOSTOLOS\nhttps://www.orsozoxi.net/");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                scrollView.setVisibility(8);
                FlyBitchJobService.this.startActivity(Intent.createChooser(intent, "PI APOSTOLOS").setFlags(268468224));
            }
        });
        bubbleLayout.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.Alarm.Services.FlyBitchJobService.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (scrollView.getVisibility() == 0) {
                    scrollView.setVisibility(8);
                    return;
                }
                if (bubbleLayout.isShown()) {
                    FlyBitchJobService.this.getWindowManager().updateViewLayout(bubbleLayout, FlyBitchJobService.this.buildLayoutParamsForBubble(60, 20));
                    scrollView.setVisibility(0);
                    TextView textView = (TextView) bubbleLayout.findViewById(R.id.tv_message);
                    textView.setText(str);
                    checksim checksimVar = new checksim();
                    if (FlyBitchJobService.this.context != null) {
                        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(FlyBitchJobService.this.context)).floatValue());
                    }
                    ((TextView) bubbleLayout.findViewById(R.id.tv_counter)).setVisibility(8);
                    try {
                        if (FlyBitchJobService.this.vibrator != null) {
                            FlyBitchJobService.this.vibrator.cancel();
                        }
                    } catch (Exception unused) {
                    }
                    if (FlyBitchJobService.this.mediaPlayer != null) {
                        try {
                            FlyBitchJobService.this.mediaPlayer.stop();
                        } catch (Exception unused2) {
                        }
                        try {
                            FlyBitchJobService.this.mediaPlayer.release();
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
        bubbleLayout.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() { // from class: com.app.piapostolos.Alarm.Services.FlyBitchJobService.3
            @Override // com.app.piapostolos.Alarm.Bubbles.BubbleLayout.OnBubbleRemoveListener
            public void onBubbleRemoved(BubbleLayout bubbleLayout2) {
            }
        });
        addBubble(bubbleLayout, 60, 20, i);
        TextView textView = (TextView) bubbleLayout.findViewById(R.id.tv_counter);
        textView.setText("" + i);
        textView.setVisibility(8);
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    private void recycleBubble(final BubbleLayout bubbleLayout) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.app.piapostolos.Alarm.Services.FlyBitchJobService.4
            @Override // java.lang.Runnable
            public void run() throws IllegalStateException {
                FlyBitchJobService.this.getWindowManager().removeView(bubbleLayout);
                for (BubbleLayout bubbleLayout2 : FlyBitchJobService.this.bubbles) {
                    if (bubbleLayout2 == bubbleLayout && bubbleLayout2.getId() == bubbleLayout.getId()) {
                        bubbleLayout.notifyBubbleRemoved();
                        FlyBitchJobService.this.bubbles.remove(bubbleLayout2);
                        try {
                            if (FlyBitchJobService.this.vibrator != null) {
                                FlyBitchJobService.this.vibrator.cancel();
                            }
                        } catch (Exception unused) {
                        }
                        if (FlyBitchJobService.this.mediaPlayer != null) {
                            try {
                                FlyBitchJobService.this.mediaPlayer.stop();
                            } catch (Exception unused2) {
                            }
                            try {
                                FlyBitchJobService.this.mediaPlayer.release();
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
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.app.piapostolos.Alarm.Services.FlyBitchJobService.5
            @Override // java.lang.Runnable
            public void run() {
                WindowManager windowManager = FlyBitchJobService.this.getWindowManager();
                BubbleBaseLayout bubbleBaseLayout2 = bubbleBaseLayout;
                windowManager.addView(bubbleBaseLayout2, bubbleBaseLayout2.getViewParams());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WindowManager.LayoutParams buildLayoutParamsForBubble(int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, Build.VERSION.SDK_INT >= 26 ? R2.dimen.sp_datepicker_list_year_label_size : R2.dimen.notification_media_narrow_margin, 8, -2);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.x = i;
        layoutParams.y = i2;
        return layoutParams;
    }

    private WindowManager.LayoutParams buildLayoutParamsForTrash() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, Build.VERSION.SDK_INT >= 26 ? R2.dimen.sp_datepicker_list_year_label_size : R2.dimen.notification_media_narrow_margin, 8, -2);
        layoutParams.x = 0;
        layoutParams.y = 0;
        return layoutParams;
    }

    public void removeBubble(BubbleLayout bubbleLayout) {
        recycleBubble(bubbleLayout);
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
