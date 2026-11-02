package it.feio.android.springpadimporter.models;

import com.google.gson.annotations.SerializedName;
import it.feio.android.springpadimporter.utils.Constants;
import it.feio.android.springpadimporter.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes2.dex */
public class SpringpadElement implements Constants {
    private SpringpadAddresses addresses;
    private String artist;
    private String author;
    private String category;
    private boolean complete;
    private String created;
    private String date;
    private String description;
    private String directions;
    private String image;
    private String ingredients;
    private boolean isPublic;
    private String isbn;
    private boolean linked;
    private String manufacturer;
    private String modified;
    private String name;

    @SerializedName("phone numbers")
    SpringpadPhoneNumbers phoneNumbers;
    private String price;

    @SerializedName("publication date")
    private String publicationDate;
    private float rating;
    private String region;
    private String text;
    private String type;
    private String url;
    private String uuid;
    private String varietal;
    private String vintage;
    private String wine_type;
    private List<String> tags = new ArrayList();
    private List<String> notebooks = new ArrayList();
    private List<SpringpadAttachment> attachments = new ArrayList();
    private List<SpringpadComment> comments = new ArrayList();
    private List<String> videos = new ArrayList();
    private List<String> cast = new ArrayList();
    private List<SpringpadItem> items = new ArrayList();

    public String getName() {
        return this.name;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getImage() {
        return this.image;
    }

    public String getText() {
        return this.text;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public boolean isLinked() {
        return this.linked;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public float getRating() {
        return this.rating;
    }

    public List<String> getNotebooks() {
        return this.notebooks;
    }

    public SpringpadAddresses getAddresses() {
        return this.addresses;
    }

    public Date getCreated() {
        return Utils.getDate(this.created);
    }

    public Date getModified() {
        return Utils.getDate(this.modified);
    }

    public String getArtist() {
        return this.artist;
    }

    public Date getDate() {
        return Utils.getDate(this.date);
    }

    public String getVarietal() {
        return this.varietal;
    }

    public String getWine_type() {
        return this.wine_type;
    }

    public List<SpringpadAttachment> getAttachments() {
        return this.attachments;
    }

    public List<SpringpadComment> getComments() {
        return this.comments;
    }

    public List<SpringpadItem> getItems() {
        return this.items;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getPublicationDate() {
        return this.publicationDate;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public String getDirections() {
        return this.directions;
    }

    public String getPrice() {
        return this.price;
    }

    public String getRegion() {
        return this.region;
    }

    public String getVintage() {
        return this.vintage;
    }

    public List<String> getCast() {
        return this.cast;
    }

    public SpringpadPhoneNumbers getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public List<String> getVideos() {
        return this.videos;
    }
}
