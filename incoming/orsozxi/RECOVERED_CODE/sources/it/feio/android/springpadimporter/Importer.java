package it.feio.android.springpadimporter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ibm.icu.impl.locale.BaseLocale;
import exceptions.ImportException;
import it.feio.android.springpadimporter.models.SpringpadComment;
import it.feio.android.springpadimporter.models.SpringpadElement;
import it.feio.android.springpadimporter.utils.Constants;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import listeners.ZipProgressesListener;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.progress.ProgressMonitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/* loaded from: classes2.dex */
public class Importer {
    private static final String JSON = "export.json";
    private List<SpringpadElement> list;
    private ZipProgressesListener mZipProgressesListener;
    private Integer notebooksCount;
    private Integer notesCount;
    private String outputTemporaryFolder;
    private ProgressMonitor pm;
    private String workingPath;

    public static void main(String[] strArr) {
        Importer importer = new Importer();
        importer.setZipProgressesListener(new ZipProgressesListener() { // from class: it.feio.android.springpadimporter.Importer.1
            @Override // listeners.ZipProgressesListener
            public void onZipProgress(int i) {
                System.out.println(i);
            }
        });
        try {
            importer.doImport("/home/fede/Scaricati/My_Springpad_Export.zip");
        } catch (ImportException e) {
            e.printStackTrace();
        }
        for (SpringpadElement springpadElement : importer.getNotes()) {
            StringBuilder sb = new StringBuilder();
            if (springpadElement.getType() != null) {
                if (springpadElement.getType().equals(Constants.TYPE_VIDEO)) {
                    try {
                        sb.append(System.getProperty("line.separator"));
                        sb.append(springpadElement.getVideos().get(0));
                    } catch (IndexOutOfBoundsException unused) {
                        sb.append(System.getProperty("line.separator"));
                        sb.append(springpadElement.getUrl());
                    }
                }
                if (springpadElement.getType().equals(Constants.TYPE_TVSHOW)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append(springpadElement.getCast());
                }
                if (springpadElement.getType().equals(Constants.TYPE_BOOK)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Author: ");
                    sb.append(springpadElement.getAuthor());
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Publication date: ");
                    sb.append(springpadElement.getPublicationDate());
                }
                if (springpadElement.getType().equals(Constants.TYPE_RECIPE)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Ingredients: ");
                    sb.append(springpadElement.getIngredients());
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Directions: ");
                    sb.append(springpadElement.getDirections());
                }
                if (springpadElement.getType().equals(Constants.TYPE_BOOKMARK)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append(springpadElement.getUrl());
                }
                if (springpadElement.getType().equals(Constants.TYPE_BUSINESS) && springpadElement.getPhoneNumbers() != null) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Phone number: ");
                    sb.append(springpadElement.getPhoneNumbers().getPhone());
                }
                if (springpadElement.getType().equals(Constants.TYPE_PRODUCT)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Category: ");
                    sb.append(springpadElement.getCategory());
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Manufacturer: ");
                    sb.append(springpadElement.getManufacturer());
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Price: ");
                    sb.append(springpadElement.getPrice());
                }
                if (springpadElement.getType().equals(Constants.TYPE_WINE)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Wine type: ");
                    sb.append(springpadElement.getWine_type());
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Varietal: ");
                    sb.append(springpadElement.getVarietal());
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Price: ");
                    sb.append(springpadElement.getPrice());
                }
                if (springpadElement.getType().equals(Constants.TYPE_ALBUM)) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Artist: ");
                    sb.append(springpadElement.getArtist());
                }
                for (SpringpadComment springpadComment : springpadElement.getComments()) {
                    sb.append(System.getProperty("line.separator"));
                    sb.append(springpadComment.getCommenter());
                    sb.append(" commented at 0");
                    sb.append(springpadComment.getDate());
                    sb.append(": ");
                    sb.append(springpadElement.getArtist());
                }
                System.out.println(sb);
            }
        }
        try {
            importer.clean();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void setZipProgressesListener(ZipProgressesListener zipProgressesListener) {
        this.mZipProgressesListener = zipProgressesListener;
    }

    public void doImport(String str) throws ImportException {
        try {
            this.list = parseJson(getJson(str));
        } catch (ImportException e) {
            throw e;
        } catch (Exception e2) {
            throw new ImportException(e2.getMessage(), e2);
        }
    }

    private File getJson(String str) throws Exception {
        if (str == null) {
            throw new NullPointerException("zip is null");
        }
        File next = null;
        this.outputTemporaryFolder = str.substring(0, str.lastIndexOf(".")) + BaseLocale.SEP + Calendar.getInstance().getTimeInMillis();
        try {
            ZipFile zipFile = new ZipFile(str);
            if (this.mZipProgressesListener != null) {
                keepProgressUpdated(zipFile);
            }
            zipFile.extractAll(this.outputTemporaryFolder);
            Iterator<File> it2 = FileUtils.listFiles(new File(this.outputTemporaryFolder), FileFilterUtils.nameFileFilter(JSON), TrueFileFilter.INSTANCE).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                next = it2.next();
                if (next != null) {
                    setWorkingPath(next.getParent() + File.separator);
                    break;
                }
            }
            if (next != null) {
                return next;
            }
            throw new ImportException("The file is not a Springpad export archive");
        } catch (Exception e) {
            throw new ImportException("Error decompressing archive", e);
        }
    }

    private void keepProgressUpdated(ZipFile zipFile) {
        this.pm = zipFile.getProgressMonitor();
        new Thread() { // from class: it.feio.android.springpadimporter.Importer.2
            private int percentDone;
            private int percentPrevious;

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (this.percentDone != 100) {
                    int percentDone = Importer.this.pm.getPercentDone();
                    this.percentDone = percentDone;
                    if (percentDone != this.percentPrevious) {
                        Importer.this.mZipProgressesListener.onZipProgress(this.percentDone);
                        this.percentPrevious = this.percentDone;
                    }
                    try {
                        sleep(1000L);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }.start();
    }

    private List<SpringpadElement> parseJson(File file) throws IOException {
        ArrayList arrayList = new ArrayList();
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader(file));
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add((SpringpadElement) gson.fromJson(jsonReader, SpringpadElement.class));
        }
        jsonReader.endArray();
        jsonReader.close();
        return arrayList;
    }

    public List<SpringpadElement> getSpringpadNotes() {
        return this.list;
    }

    public void clean() throws IOException {
        FileUtils.deleteDirectory(new File(this.outputTemporaryFolder));
    }

    public String getWorkingPath() {
        return this.workingPath;
    }

    public void setWorkingPath(String str) {
        this.workingPath = str;
    }

    public List<SpringpadElement> getNotes() {
        ArrayList arrayList = new ArrayList();
        for (SpringpadElement springpadElement : this.list) {
            if (!Constants.TYPE_NOTEBOOK.equals(springpadElement.getType())) {
                arrayList.add(springpadElement);
            }
        }
        return arrayList;
    }

    public List<SpringpadElement> getNotebooks() {
        ArrayList arrayList = new ArrayList();
        for (SpringpadElement springpadElement : this.list) {
            if (Constants.TYPE_NOTEBOOK.equals(springpadElement.getType())) {
                arrayList.add(springpadElement);
            }
        }
        return arrayList;
    }

    public int getNotesCount() {
        if (this.notesCount == null) {
            this.notesCount = Integer.valueOf(getNotes().size());
        }
        return this.notesCount.intValue();
    }

    public int getNotebooksCount() {
        if (this.notebooksCount == null) {
            this.notebooksCount = Integer.valueOf(getNotebooks().size());
        }
        return this.notebooksCount.intValue();
    }

    public int getUnzipPercentage() {
        return this.pm.getPercentDone();
    }
}
