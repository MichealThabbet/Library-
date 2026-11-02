package com.app.orsozoxi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.app.orsozoxi.Beans.Card;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class Manager extends BaseActivity {
    private static int COL_COUNT = -1;
    private static int ROW_COUNT = -1;
    private static Object lock = new Object();
    private Drawable backImage;
    private ButtonListener buttonListener;
    private int[][] cards;
    private Context context;
    private Card firstCard;
    private UpdateCardsHandler handler;
    private List<Drawable> images;
    private TableLayout mainTable;
    private Card seconedCard;
    int turns;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.main);
        this.backImage = getResources().getDrawable(R.drawable.logo);
        this.buttonListener = new ButtonListener();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout03);
        this.mainTable = tableLayout;
        this.context = tableLayout.getContext();
        Spinner spinner = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.Manager.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ((Spinner) Manager.this.findViewById(R.id.Spinner01)).setSelection(0);
                int i2 = 5;
                int i3 = 6;
                if (i == 1) {
                    i2 = 4;
                    i3 = 4;
                } else if (i == 2) {
                    i2 = 4;
                    i3 = 5;
                } else if (i == 3) {
                    i2 = 4;
                } else if (i != 4) {
                    if (i != 5) {
                        return;
                    } else {
                        i2 = 6;
                    }
                }
                Manager.this.newGame(i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void newGame(int i, int i2) {
        ROW_COUNT = i2;
        COL_COUNT = i;
        this.cards = (int[][]) Array.newInstance((Class<?>) int.class, i, i2);
        this.mainTable.removeView(findViewById(R.id.TableRow01));
        this.mainTable.removeView(findViewById(R.id.TableRow02));
        TableRow tableRow = (TableRow) findViewById(R.id.TableRow03);
        tableRow.removeAllViews();
        TableLayout tableLayout = new TableLayout(this.context);
        this.mainTable = tableLayout;
        tableRow.addView(tableLayout);
        for (int i3 = 0; i3 < ROW_COUNT; i3++) {
            this.mainTable.addView(createRow(i3));
        }
        this.firstCard = null;
        loadCards();
        this.turns = 0;
        ((TextView) findViewById(R.id.tv1)).setText("Tries: " + this.turns);
    }

    private void loadImages() {
        ArrayList arrayList = new ArrayList();
        this.images = arrayList;
        arrayList.add(getResources().getDrawable(R.drawable.card1));
        this.images.add(getResources().getDrawable(R.drawable.card2));
        this.images.add(getResources().getDrawable(R.drawable.card3));
        this.images.add(getResources().getDrawable(R.drawable.card4));
        this.images.add(getResources().getDrawable(R.drawable.card5));
        this.images.add(getResources().getDrawable(R.drawable.card6));
        this.images.add(getResources().getDrawable(R.drawable.card7));
        this.images.add(getResources().getDrawable(R.drawable.card8));
        this.images.add(getResources().getDrawable(R.drawable.card9));
        this.images.add(getResources().getDrawable(R.drawable.card10));
        this.images.add(getResources().getDrawable(R.drawable.card11));
        this.images.add(getResources().getDrawable(R.drawable.card12));
        this.images.add(getResources().getDrawable(R.drawable.card13));
        this.images.add(getResources().getDrawable(R.drawable.card14));
        this.images.add(getResources().getDrawable(R.drawable.card15));
        this.images.add(getResources().getDrawable(R.drawable.card16));
        this.images.add(getResources().getDrawable(R.drawable.card17));
        this.images.add(getResources().getDrawable(R.drawable.card18));
        this.images.add(getResources().getDrawable(R.drawable.card19));
        this.images.add(getResources().getDrawable(R.drawable.card20));
    }

    private void loadCards() {
        try {
            int i = ROW_COUNT * COL_COUNT;
            Log.i("loadCards()", "size=" + i);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(new Integer(i2));
            }
            Random random = new Random();
            int i3 = i - 1;
            while (i3 >= 0) {
                this.cards[i3 % COL_COUNT][i3 / COL_COUNT] = ((Integer) arrayList.remove(i3 > 0 ? random.nextInt(i3) : 0)).intValue() % (i / 2);
                Log.i("loadCards()", "card[" + (i3 % COL_COUNT) + "][" + (i3 / COL_COUNT) + "]=" + this.cards[i3 % COL_COUNT][i3 / COL_COUNT]);
                i3--;
            }
        } catch (Exception e) {
            Log.e("loadCards()", e + "");
        }
    }

    private TableRow createRow(int i) {
        TableRow tableRow = new TableRow(this.context);
        tableRow.setHorizontalGravity(17);
        for (int i2 = 0; i2 < COL_COUNT; i2++) {
            tableRow.addView(createImageButton(i2, i));
        }
        return tableRow;
    }

    private View createImageButton(int i, int i2) {
        Button button = new Button(this.context);
        button.setBackgroundDrawable(this.backImage);
        button.setHeight(60);
        button.setWidth(50);
        button.setId((i * 100) + i2);
        button.setOnClickListener(this.buttonListener);
        return button;
    }

    class ButtonListener implements View.OnClickListener {
        ButtonListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            synchronized (Manager.lock) {
                if (Manager.this.firstCard == null || Manager.this.seconedCard == null) {
                    int id = view.getId();
                    turnCard((Button) view, id / 100, id % 100);
                }
            }
        }

        private void turnCard(Button button, int i, int i2) {
            button.setBackgroundDrawable((Drawable) Manager.this.images.get(Manager.this.cards[i][i2]));
            if (Manager.this.firstCard != null) {
                if (Manager.this.firstCard.x == i && Manager.this.firstCard.y == i2) {
                    return;
                }
                Manager.this.seconedCard = new Card(button, i, i2);
                Manager.this.turns++;
                ((TextView) Manager.this.findViewById(R.id.tv1)).setText("Tries: " + Manager.this.turns);
                new Timer(false).schedule(new TimerTask() { // from class: com.app.orsozoxi.Manager.ButtonListener.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        try {
                            synchronized (Manager.lock) {
                                Manager.this.handler.sendEmptyMessage(0);
                            }
                        } catch (Exception e) {
                            Log.e("E1", e.getMessage());
                        }
                    }
                }, 1300L);
                return;
            }
            Manager.this.firstCard = new Card(button, i, i2);
        }
    }

    class UpdateCardsHandler extends Handler {
        UpdateCardsHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            synchronized (Manager.lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (Manager.this.cards[Manager.this.seconedCard.x][Manager.this.seconedCard.y] == Manager.this.cards[Manager.this.firstCard.x][Manager.this.firstCard.y]) {
                Manager.this.firstCard.button.setVisibility(4);
                Manager.this.seconedCard.button.setVisibility(4);
            } else {
                Manager.this.seconedCard.button.setBackgroundDrawable(Manager.this.backImage);
                Manager.this.firstCard.button.setBackgroundDrawable(Manager.this.backImage);
            }
            Manager.this.firstCard = null;
            Manager.this.seconedCard = null;
        }
    }
}
