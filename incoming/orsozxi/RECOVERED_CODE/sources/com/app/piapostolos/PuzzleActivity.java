package com.app.piapostolos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.app.piapostolos.GamesBeans.ButtonView;
import com.app.piapostolos.GamesBeans.Puzzle;
import com.app.piapostolos.GamesBeans.PuzzleStats;
import com.app.piapostolos.GamesBeans.PuzzleView;
import com.app.piapostolos.Others.CommonUtilities;
import com.app.piapostolos.R;
import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/* loaded from: classes.dex */
public class PuzzleActivity extends BaseActivity {
    protected static final int DEFAULT_SIZE = 3;
    protected static final String FILENAME_DIR = "name.schedenig.android.ntilepuzzle";
    protected static final String FILENAME_DISABLE_INTERNAL_IMAGES = ".disable_internal_images";
    protected static final String FILENAME_IMAGE_PREFIX = "image_";
    protected static final String FILENAME_PHOTO = "photo.jpg";
    protected static final String FILENAME_PHOTO_DIR = "name.schedenig.android.ntilepuzzle/photo";
    protected static final String KEY_IMAGE_URI = "imageUri";
    protected static final String KEY_MOVE_AVERAGE_PREFIX = "moveAvg_";
    protected static final String KEY_MOVE_BEST_PREFIX = "moveBest_";
    protected static final String KEY_MOVE_COUNT = "moveCount";
    protected static final String KEY_PLAYS_PREFIX = "plays_";
    protected static final String KEY_PUZZLE = "puzzle";
    protected static final String KEY_PUZZLE_SIZE = "puzzleSize";
    protected static final String KEY_SHOW_NUMBERS = "showNumbers";
    protected static final int MAX_WIDTH = 6;
    protected static final int MENU_ABOUT = 7;
    protected static final int MENU_CHANGE_TILING = 5;
    protected static final int MENU_NEW_IMAGE = 1;
    protected static final int MENU_SCRAMBLE = 0;
    protected static final int MENU_SELECT_IMAGE = 2;
    protected static final int MENU_SHOW_NUMBERS = 4;
    protected static final int MENU_STATS = 6;
    protected static final int MENU_TAKE_PHOTO = 3;
    protected static final int MIN_WIDTH = 2;
    protected static final int RESULT_SELECT_IMAGE = 0;
    protected static final int RESULT_TAKE_PHOTO = 1;
    protected static final String SUFFIX_JPEG = ".jpeg";
    protected static final String SUFFIX_JPG = ".jpg";
    protected static final String SUFFIX_PNG = ".png";
    protected String[] LABELS_SHOW_NUMBERS_ITEMS;
    private BitmapFactory.Options bitmapOptions;
    private boolean expert;
    private Uri imageUri;
    private MenuItem menuShowNumbers;
    private boolean portrait;
    private Puzzle puzzle;
    private PuzzleView view;
    private int puzzleWidth = 1;
    private int puzzleHeight = 1;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws SecurityException {
        super.onCreate(bundle);
        this.LABELS_SHOW_NUMBERS_ITEMS = new String[]{getString(R.string.label_show_numbers_none), getString(R.string.label_show_numbers_some), getString(R.string.label_show_numbers_all)};
        requestWindowFeature(1);
        setVolumeControlStream(3);
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.bitmapOptions = options;
        options.inScaled = false;
        this.puzzle = new Puzzle();
        this.view = new PuzzleView(this, this.puzzle);
        setContentView(new ButtonView(this, this.view));
        System.out.println("besho0");
        scramble();
        System.out.println("besho01");
        if (!loadPreferences()) {
            System.out.println("besho02");
            setPuzzleSize(3, true);
        }
        System.out.println("besho03");
        if (this.imageUri == null) {
            System.out.println("besho04");
            newImage();
            System.out.println("besho05");
        }
    }

    private void scramble() {
        this.puzzle.init(this.puzzleWidth, this.puzzleHeight);
        this.puzzle.scramble();
        this.view.invalidate();
        this.expert = this.view.getShowNumbers() == PuzzleView.ShowNumbers.NONE;
    }

    protected void newImage() throws SecurityException {
        LinkedList linkedList = new LinkedList();
        if (linkedList.isEmpty() || isUseInternalImages()) {
            findInternalImages(linkedList);
        }
        if (linkedList.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_could_not_load_image), 1).show();
        } else {
            loadBitmap((Uri) linkedList.get(new Random().nextInt(linkedList.size())));
        }
    }

    protected void findInternalImages(List<Uri> list) throws SecurityException {
        String resourceBaseUri = getResourceBaseUri();
        R.drawable.class.getFields();
        list.add(Uri.parse(resourceBaseUri + R.drawable.a1));
        list.add(Uri.parse(resourceBaseUri + R.drawable.a2));
        list.add(Uri.parse(resourceBaseUri + R.drawable.a3));
        list.add(Uri.parse(resourceBaseUri + R.drawable.a4));
        list.add(Uri.parse(resourceBaseUri + R.drawable.a5));
        list.add(Uri.parse(resourceBaseUri + R.drawable.a6));
        list.add(Uri.parse(resourceBaseUri + R.drawable.a7));
    }

    protected boolean isUseInternalImages() {
        if (new File(new File(CommonUtilities.getStoragePath(this)), FILENAME_DIR).exists()) {
            return !new File(r1, FILENAME_DISABLE_INTERNAL_IMAGES).exists();
        }
        return false;
    }

    protected void findExternalImages(List<Uri> list) {
        File file = new File(new File(CommonUtilities.getStoragePath(this)), FILENAME_DIR);
        if (file.exists()) {
            for (File file2 : file.listFiles(new FilenameFilter() { // from class: com.app.piapostolos.PuzzleActivity.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file3, String str) {
                    String lowerCase = str.toLowerCase(Locale.getDefault());
                    return lowerCase.endsWith(PuzzleActivity.SUFFIX_JPG) || lowerCase.endsWith(".jpeg") || lowerCase.endsWith(".png");
                }
            })) {
                list.add(Uri.fromFile(file2));
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void loadBitmap(android.net.Uri r14) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.PuzzleActivity.loadBitmap(android.net.Uri):void");
    }

    private void setBitmap(Bitmap bitmap) {
        this.portrait = bitmap.getWidth() < bitmap.getHeight();
        this.view.setBitmap(bitmap);
        setPuzzleSize(Math.min(this.puzzleWidth, this.puzzleHeight), true);
        setRequestedOrientation(this.portrait ? 1 : 0);
    }

    private void selectImage() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        Uri uriFromFile;
        super.onActivityResult(i, i2, intent);
        if (i == 0) {
            if (i2 == -1) {
                loadBitmap(intent.getData());
            }
        } else if (i == 1 && i2 == -1) {
            File file = new File(getSaveDirectory(), FILENAME_PHOTO);
            if (!file.exists() || (uriFromFile = Uri.fromFile(file)) == null) {
                return;
            }
            loadBitmap(uriFromFile);
        }
    }

    private File getSaveDirectory() {
        File file = new File(CommonUtilities.getStoragePath(this));
        File file2 = new File(file, FILENAME_PHOTO_DIR);
        if (file2.exists() || (file.exists() && file2.mkdirs())) {
            return file2;
        }
        return null;
    }

    private void changeTiling() {
        int i;
        int i2;
        float imageAspectRatio = getImageAspectRatio();
        if (imageAspectRatio < 1.0f) {
            imageAspectRatio = 1.0f / imageAspectRatio;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_select_tiling);
        String[] strArr = new String[5];
        int i3 = 0;
        for (int i4 = 0; i4 < 5; i4++) {
            if (this.portrait) {
                i2 = i4 + 2;
                i = (int) (i2 * imageAspectRatio);
            } else {
                i = i4 + 2;
                i2 = (int) (i * imageAspectRatio);
            }
            strArr[i4] = sizeToString(i2, i);
            if (i4 + 2 == Math.min(this.puzzleWidth, this.puzzleHeight)) {
                i3 = i4;
            }
        }
        builder.setSingleChoiceItems(strArr, i3, new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.PuzzleActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i5) {
                PuzzleActivity.this.setPuzzleSize(i5 + 2, false);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private float getImageAspectRatio() {
        if (this.view.getBitmap() == null) {
            return 1.0f;
        }
        return r0.getWidth() / r0.getHeight();
    }

    protected void setPuzzleSize(int i, boolean z) {
        int i2;
        float imageAspectRatio = getImageAspectRatio();
        if (imageAspectRatio < 1.0f) {
            imageAspectRatio = 1.0f / imageAspectRatio;
        }
        if (this.portrait) {
            i2 = (int) (i * imageAspectRatio);
        } else {
            int i3 = (int) (i * imageAspectRatio);
            i2 = i;
            i = i3;
        }
        if (!z && i == this.puzzleWidth && i2 == this.puzzleHeight) {
            return;
        }
        this.puzzleWidth = i;
        this.puzzleHeight = i2;
        scramble();
    }

    protected String sizeToString(int i, int i2) {
        return MessageFormat.format(getString(R.string.puzzle_size_x_y), Integer.valueOf(i), Integer.valueOf(i2));
    }

    private void toggleShowNumbers() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_show_numbers);
        builder.setSingleChoiceItems(this.LABELS_SHOW_NUMBERS_ITEMS, showNumbersToInt(this.view.getShowNumbers()), new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.PuzzleActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PuzzleView.ShowNumbers showNumbers = PuzzleActivity.this.view.getShowNumbers();
                PuzzleView.ShowNumbers showNumbersIntToShowNumbers = PuzzleActivity.this.intToShowNumbers(i);
                if (showNumbersIntToShowNumbers != showNumbers) {
                    PuzzleActivity.this.view.setShowNumbers(showNumbersIntToShowNumbers);
                    PuzzleActivity.this.updateMenuShowNumbers();
                    PuzzleActivity.this.view.invalidate();
                    if (showNumbers == PuzzleView.ShowNumbers.NONE && PuzzleActivity.this.expert) {
                        Toast.makeText(PuzzleActivity.this, R.string.toast_disabled_expert_mode, 1).show();
                        PuzzleActivity.this.expert = false;
                    } else if (showNumbersIntToShowNumbers == PuzzleView.ShowNumbers.NONE) {
                        Toast.makeText(PuzzleActivity.this, R.string.toast_not_enabled_expert_mode, 1).show();
                    }
                }
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    protected void updateMenuShowNumbers() {
        this.menuShowNumbers.setTitle(MessageFormat.format(getString(R.string.menu_show_numbers), this.LABELS_SHOW_NUMBERS_ITEMS[showNumbersToInt(this.view.getShowNumbers())]));
    }

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        onCreateOptionsMenu(contextMenu);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getPackageManager().hasSystemFeature("android.hardware.camera");
        menu.add(0, 1, 0, R.string.menu_new_image);
        menu.add(0, 0, 0, R.string.menu_scramble);
        menu.add(0, 5, 0, R.string.menu_change_tiling);
        this.menuShowNumbers = menu.add(0, 4, 0, "");
        menu.add(0, 6, 0, R.string.menu_stats);
        updateMenuShowNumbers();
        return true;
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem menuItem) {
        return onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) throws SecurityException {
        int itemId = menuItem.getItemId();
        if (itemId == 0) {
            scramble();
            return true;
        }
        if (itemId == 1) {
            newImage();
            return true;
        }
        if (itemId == 4) {
            toggleShowNumbers();
            return true;
        }
        if (itemId == 5) {
            changeTiling();
            return true;
        }
        if (itemId == 6) {
            showStats();
            return true;
        }
        if (itemId == 7) {
            showAbout();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected SharedPreferences getPreferences() {
        return getSharedPreferences(PuzzleActivity.class.getName(), 0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    public PuzzleStats updateStats() {
        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editorEdit = preferences.edit();
        String strValueOf = String.valueOf((this.expert ? 10000 : 0) + (Math.min(this.puzzle.getWidth(), this.puzzle.getHeight()) * 100) + Math.max(this.puzzle.getWidth(), this.puzzle.getHeight()));
        int i = preferences.getInt(KEY_PLAYS_PREFIX + strValueOf, 0);
        int moveCount = preferences.getInt(KEY_MOVE_BEST_PREFIX + strValueOf, 0);
        float f = preferences.getFloat(KEY_MOVE_AVERAGE_PREFIX + strValueOf, 0.0f);
        int i2 = i + 1;
        boolean z = moveCount == 0 || moveCount > this.puzzle.getMoveCount();
        if (z) {
            moveCount = this.puzzle.getMoveCount();
        }
        float moveCount2 = ((f * (i2 - 1)) + this.puzzle.getMoveCount()) / i2;
        editorEdit.putInt(KEY_PLAYS_PREFIX + strValueOf, i2);
        editorEdit.putInt(KEY_MOVE_BEST_PREFIX + strValueOf, moveCount);
        editorEdit.putFloat(KEY_MOVE_AVERAGE_PREFIX + strValueOf, moveCount2);
        editorEdit.commit();
        return new PuzzleStats(i2, moveCount, moveCount2, z);
    }

    protected PuzzleView.ShowNumbers intToShowNumbers(int i) {
        if (i == 1) {
            return PuzzleView.ShowNumbers.SOME;
        }
        if (i == 2) {
            return PuzzleView.ShowNumbers.ALL;
        }
        return PuzzleView.ShowNumbers.NONE;
    }

    /* renamed from: com.app.piapostolos.PuzzleActivity$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$app$piapostolos$GamesBeans$PuzzleView$ShowNumbers;

        static {
            int[] iArr = new int[PuzzleView.ShowNumbers.values().length];
            $SwitchMap$com$app$piapostolos$GamesBeans$PuzzleView$ShowNumbers = iArr;
            try {
                iArr[PuzzleView.ShowNumbers.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$app$piapostolos$GamesBeans$PuzzleView$ShowNumbers[PuzzleView.ShowNumbers.SOME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    protected int showNumbersToInt(PuzzleView.ShowNumbers showNumbers) {
        int i = AnonymousClass4.$SwitchMap$com$app$piapostolos$GamesBeans$PuzzleView$ShowNumbers[showNumbers.ordinal()];
        if (i != 1) {
            return i != 2 ? 0 : 1;
        }
        return 2;
    }

    protected boolean loadPreferences() {
        SharedPreferences preferences = getPreferences();
        try {
            this.view.setShowNumbers(intToShowNumbers(preferences.getInt(KEY_SHOW_NUMBERS, 1)));
            String string = preferences.getString(KEY_IMAGE_URI, null);
            if (string == null) {
                this.imageUri = null;
            } else {
                loadBitmap(Uri.parse(string));
            }
            int i = preferences.getInt(KEY_PUZZLE_SIZE, 0);
            String string2 = preferences.getString(KEY_PUZZLE, null);
            if (i > 0 && string2 != null) {
                String[] strArrSplit = string2.split("\\;");
                if (strArrSplit.length / i > 1) {
                    setPuzzleSize(i, false);
                    this.puzzle.init(this.puzzleWidth, this.puzzleHeight);
                    int length = strArrSplit.length;
                    int[] iArr = new int[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        try {
                            iArr[i2] = Integer.parseInt(strArrSplit[i2]);
                        } catch (NumberFormatException unused) {
                        }
                    }
                    this.puzzle.setTiles(iArr);
                }
            }
            this.puzzle.setMoveCount(preferences.getInt(KEY_MOVE_COUNT, 0));
            return preferences.contains(KEY_SHOW_NUMBERS);
        } catch (ClassCastException unused2) {
            return false;
        }
    }

    protected String getResourceBaseUri() {
        return "android.resource://" + PuzzleActivity.class.getPackage().getName() + "/";
    }

    protected void savePreferences() {
        SharedPreferences.Editor editorEdit = getPreferences().edit();
        editorEdit.putInt(KEY_SHOW_NUMBERS, showNumbersToInt(this.view.getShowNumbers()));
        Uri uri = this.imageUri;
        if (uri == null) {
            editorEdit.remove(KEY_IMAGE_URI);
        } else {
            editorEdit.putString(KEY_IMAGE_URI, uri.toString());
        }
        StringBuilder sb = null;
        for (int i : this.puzzle.getTiles()) {
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.append(';');
            }
            sb.append(i);
        }
        editorEdit.putInt(KEY_PUZZLE_SIZE, Math.min(this.puzzleWidth, this.puzzleHeight));
        editorEdit.putString(KEY_PUZZLE, sb.toString());
        editorEdit.putInt(KEY_MOVE_COUNT, this.puzzle.getMoveCount());
        editorEdit.commit();
    }

    private void showStats() {
        SharedPreferences preferences = getPreferences();
        String strValueOf = String.valueOf((this.expert ? 10000 : 0) + (Math.min(this.puzzle.getWidth(), this.puzzle.getHeight()) * 100) + Math.max(this.puzzle.getWidth(), this.puzzle.getHeight()));
        showStats(new PuzzleStats(preferences.getInt(KEY_PLAYS_PREFIX + strValueOf, 0), preferences.getInt(KEY_MOVE_BEST_PREFIX + strValueOf, 0), preferences.getFloat(KEY_MOVE_AVERAGE_PREFIX + strValueOf, 0.0f), false));
    }

    public void showStats(PuzzleStats puzzleStats) {
        String str;
        String strSizeToString = sizeToString(this.puzzleWidth, this.puzzleHeight);
        if (this.puzzle.isSolved()) {
            str = MessageFormat.format(getString(R.string.finished_type_expert_puzzle_in_n_moves), strSizeToString, Integer.valueOf(this.expert ? 1 : 0), Integer.valueOf(this.puzzle.getMoveCount()));
        } else {
            str = MessageFormat.format(getString(R.string.type_expert_puzzle_n_moves_so_far), strSizeToString, Integer.valueOf(this.expert ? 1 : 0), Integer.valueOf(this.puzzle.getMoveCount()));
        }
        String str2 = MessageFormat.format(getString(R.string.message_stats_best_avg_plays), str, Integer.valueOf(puzzleStats.getBest()), Float.valueOf(puzzleStats.getAvg()), Integer.valueOf(puzzleStats.getPlays()));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(!this.puzzle.isSolved() ? R.string.title_stats : puzzleStats.isNewBest() ? R.string.title_new_record : R.string.title_solved);
        builder.setMessage(str2);
        builder.setPositiveButton(R.string.label_ok, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    public void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_about);
        builder.setPositiveButton(R.string.label_ok, (DialogInterface.OnClickListener) null);
        builder.setIcon(R.mipmap.ic_launcher);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.show();
        ((TextView) alertDialogCreate.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void playSound(int i) throws IllegalStateException {
        MediaPlayer.create(this, i).start();
    }

    public void onFinish() {
        PuzzleStats puzzleStatsUpdateStats = updateStats();
        playSound(puzzleStatsUpdateStats.isNewBest() ? R.raw.record : R.raw.solved);
        showStats(puzzleStatsUpdateStats);
    }

    public PuzzleView getView() {
        return this.view;
    }

    public Puzzle getPuzzle() {
        return this.puzzle;
    }
}
