package it.feio.android.springpadimporter.models;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SpringpadAttachment {
    private String description;
    private float duration;
    private String image;
    private String type;
    private String url;
    private List<String> videos = new ArrayList();

    public String getImage() {
        return this.image;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public float getDuration() {
        return this.duration;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getVideos() {
        return this.videos;
    }
}
