package it.orsozoxi.android.orsonotes.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Toast;
import exceptions.ImportException;
import it.feio.android.springpadimporter.Importer;
import it.feio.android.springpadimporter.models.SpringpadAttachment;
import it.feio.android.springpadimporter.models.SpringpadComment;
import it.feio.android.springpadimporter.models.SpringpadElement;
import it.feio.android.springpadimporter.models.SpringpadItem;
import it.feio.android.springpadimporter.utils.Constants;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.ReminderHelper;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationChannels;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationsHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import listeners.ZipProgressesListener;

/* loaded from: classes3.dex */
public class SpringImportHelper {
    public static final String ACTION_DATA_IMPORT_SPRINGPAD = "action_data_import_springpad";
    public static final String EXTRA_SPRINGPAD_BACKUP = "extra_springpad_backup";
    private final Context context;
    private int importedSpringpadNotebooks;
    private int importedSpringpadNotes;

    public SpringImportHelper(Context context) {
        this.context = context;
    }

    public synchronized void importDataFromSpringpad(Intent intent, final NotificationsHelper notificationsHelper) {
        Attachment attachmentCreateAttachmentFromUri;
        Attachment attachmentCreateAttachmentFromUri2;
        String stringExtra = intent.getStringExtra(EXTRA_SPRINGPAD_BACKUP);
        Importer importer = new Importer();
        try {
            importer.setZipProgressesListener(new ZipProgressesListener() { // from class: it.orsozoxi.android.orsonotes.helpers.-$$Lambda$SpringImportHelper$F8MC1tlhPtFWT-hIAjTrPM1YfSk
                @Override // listeners.ZipProgressesListener
                public final void onZipProgress(int i) {
                    this.f$0.lambda$importDataFromSpringpad$0$SpringImportHelper(notificationsHelper, i);
                }
            });
            importer.doImport(stringExtra);
            updateImportNotification(importer, notificationsHelper);
            List<SpringpadElement> springpadNotes = importer.getSpringpadNotes();
            if (springpadNotes != null && springpadNotes.size() != 0) {
                HashMap map = new HashMap();
                for (SpringpadElement springpadElement : importer.getNotebooks()) {
                    Category category = new Category();
                    category.setName(springpadElement.getName());
                    category.setColor(String.valueOf(Color.parseColor("#F9EA1B")));
                    DbHelper.getInstance().updateCategory(category);
                    map.put(springpadElement.getUuid(), category);
                    this.importedSpringpadNotebooks++;
                    updateImportNotification(importer, notificationsHelper);
                }
                Category category2 = new Category();
                category2.setName("Springpad");
                category2.setColor(String.valueOf(Color.parseColor("#F9EA1B")));
                DbHelper.getInstance().updateCategory(category2);
                for (SpringpadElement springpadElement2 : importer.getNotes()) {
                    Note note = new Note();
                    note.setTitle(springpadElement2.getName());
                    StringBuilder sb = new StringBuilder();
                    sb.append(TextUtils.isEmpty(springpadElement2.getText()) ? "" : Html.fromHtml(springpadElement2.getText()));
                    sb.append(TextUtils.isEmpty(springpadElement2.getDescription()) ? "" : springpadElement2.getDescription());
                    if (springpadElement2.getType() == null) {
                        Toast.makeText(this.context, this.context.getString(R.string.error), 0).show();
                    } else {
                        if (springpadElement2.getType().equals(Constants.TYPE_VIDEO)) {
                            try {
                                sb.append(System.getProperty("line.separator"));
                                sb.append(springpadElement2.getVideos().get(0));
                            } catch (IndexOutOfBoundsException unused) {
                                sb.append(System.getProperty("line.separator"));
                                sb.append(springpadElement2.getUrl());
                            }
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_TVSHOW)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append(TextUtils.join(", ", springpadElement2.getCast()));
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_BOOK)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Author: ");
                            sb.append(springpadElement2.getAuthor());
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Publication date: ");
                            sb.append(springpadElement2.getPublicationDate());
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_RECIPE)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Ingredients: ");
                            sb.append(springpadElement2.getIngredients());
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Directions: ");
                            sb.append(springpadElement2.getDirections());
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_BOOKMARK)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append(springpadElement2.getUrl());
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_BUSINESS) && springpadElement2.getPhoneNumbers() != null) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Phone number: ");
                            sb.append(springpadElement2.getPhoneNumbers().getPhone());
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_PRODUCT)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Category: ");
                            sb.append(springpadElement2.getCategory());
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Manufacturer: ");
                            sb.append(springpadElement2.getManufacturer());
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Price: ");
                            sb.append(springpadElement2.getPrice());
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_WINE)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Wine type: ");
                            sb.append(springpadElement2.getWine_type());
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Varietal: ");
                            sb.append(springpadElement2.getVarietal());
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Price: ");
                            sb.append(springpadElement2.getPrice());
                        }
                        if (springpadElement2.getType().equals(Constants.TYPE_ALBUM)) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append("Artist: ");
                            sb.append(springpadElement2.getArtist());
                        }
                        for (SpringpadComment springpadComment : springpadElement2.getComments()) {
                            sb.append(System.getProperty("line.separator"));
                            sb.append(springpadComment.getCommenter());
                            sb.append(" commented at 0");
                            sb.append(springpadComment.getDate());
                            sb.append(": ");
                            sb.append(springpadElement2.getArtist());
                        }
                        note.setContent(sb.toString());
                        if (springpadElement2.getType().equals(Constants.TYPE_CHECKLIST)) {
                            StringBuilder sb2 = new StringBuilder();
                            for (SpringpadItem springpadItem : springpadElement2.getItems()) {
                                sb2.append(springpadItem.getComplete() ? it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM : it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM);
                                sb2.append(springpadItem.getName());
                                sb2.append(System.getProperty("line.separator"));
                            }
                            note.setContent(sb2.toString());
                            note.setChecklist((Boolean) true);
                        }
                        String str = springpadElement2.getTags().size() > 0 ? "#" + TextUtils.join(" #", springpadElement2.getTags()) : "";
                        if (note.isChecklist().booleanValue()) {
                            note.setTitle(note.getTitle() + str);
                        } else {
                            note.setContent(note.getContent() + System.getProperty("line.separator") + str);
                        }
                        if (springpadElement2.getDate() != null) {
                            note.setAlarm(springpadElement2.getDate().getTime());
                        }
                        note.setCreation(Long.valueOf(springpadElement2.getCreated().getTime()));
                        note.setLastModification(Long.valueOf(springpadElement2.getModified().getTime()));
                        String image = springpadElement2.getImage();
                        if (!TextUtils.isEmpty(image)) {
                            try {
                                Uri uriFromFile = Uri.fromFile(StorageHelper.createNewAttachmentFileFromHttp(this.context, image));
                                attachmentCreateAttachmentFromUri = new Attachment(uriFromFile, StorageHelper.getMimeType(uriFromFile.getPath()));
                            } catch (MalformedURLException unused2) {
                                attachmentCreateAttachmentFromUri = StorageHelper.createAttachmentFromUri(this.context, Uri.parse(importer.getWorkingPath() + image), true);
                            } catch (IOException unused3) {
                                LogDelegate.e("Error retrieving Springpad online image");
                                attachmentCreateAttachmentFromUri = null;
                            }
                            if (attachmentCreateAttachmentFromUri != null) {
                                note.addAttachment(attachmentCreateAttachmentFromUri);
                            }
                        }
                        for (SpringpadAttachment springpadAttachment : springpadElement2.getAttachments()) {
                            if (image == null || !image.equals(springpadAttachment.getUrl())) {
                                if (!TextUtils.isEmpty(springpadAttachment.getUrl())) {
                                    try {
                                        Uri uriFromFile2 = Uri.fromFile(StorageHelper.createNewAttachmentFileFromHttp(this.context, springpadAttachment.getUrl()));
                                        attachmentCreateAttachmentFromUri2 = new Attachment(uriFromFile2, StorageHelper.getMimeType(uriFromFile2.getPath()));
                                    } catch (MalformedURLException unused4) {
                                        attachmentCreateAttachmentFromUri2 = StorageHelper.createAttachmentFromUri(this.context, Uri.parse(importer.getWorkingPath() + springpadAttachment.getUrl()), true);
                                    } catch (IOException unused5) {
                                        LogDelegate.e("Error retrieving Springpad online image");
                                        attachmentCreateAttachmentFromUri2 = null;
                                    }
                                    if (attachmentCreateAttachmentFromUri2 != null) {
                                        note.addAttachment(attachmentCreateAttachmentFromUri2);
                                    }
                                }
                            }
                        }
                        if (springpadElement2.getNotebooks().size() > 0) {
                            note.setCategory((Category) map.get(springpadElement2.getNotebooks().get(0)));
                        } else {
                            note.setCategory(category2);
                        }
                        DbHelper.getInstance().updateNote(note, false);
                        ReminderHelper.addReminder(this.context, note);
                        this.importedSpringpadNotes++;
                        updateImportNotification(importer, notificationsHelper);
                    }
                }
                try {
                    importer.clean();
                } catch (IOException unused6) {
                    LogDelegate.w("Springpad import temp files not deleted");
                }
            }
        } catch (ImportException e) {
            new NotificationsHelper(this.context).createNotification(NotificationChannels.NotificationChannelNames.Backups, R.drawable.ic_emoticon_sad_white_24dp, this.context.getString(R.string.import_fail) + ": " + e.getMessage(), null).setLedActive().show();
        }
    }

    public /* synthetic */ void lambda$importDataFromSpringpad$0$SpringImportHelper(NotificationsHelper notificationsHelper, int i) {
        notificationsHelper.setMessage(this.context.getString(R.string.extracted) + " " + i + "%").show();
    }

    private void updateImportNotification(Importer importer, NotificationsHelper notificationsHelper) {
        notificationsHelper.setMessage(importer.getNotebooksCount() + " " + this.context.getString(R.string.categories) + " (" + this.importedSpringpadNotebooks + " " + this.context.getString(R.string.imported) + "), " + importer.getNotesCount() + " " + this.context.getString(R.string.notes) + " (" + this.importedSpringpadNotes + " " + this.context.getString(R.string.imported) + ")").show();
    }
}
