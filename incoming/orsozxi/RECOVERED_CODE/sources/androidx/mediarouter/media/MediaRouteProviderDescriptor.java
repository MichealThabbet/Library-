package androidx.mediarouter.media;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class MediaRouteProviderDescriptor {
    private static final String KEY_ROUTES = "routes";
    final Bundle mBundle;
    List<MediaRouteDescriptor> mRoutes;

    MediaRouteProviderDescriptor(Bundle bundle, List<MediaRouteDescriptor> list) {
        this.mBundle = bundle;
        this.mRoutes = list;
    }

    public List<MediaRouteDescriptor> getRoutes() {
        ensureRoutes();
        return this.mRoutes;
    }

    void ensureRoutes() {
        if (this.mRoutes == null) {
            ArrayList parcelableArrayList = this.mBundle.getParcelableArrayList(KEY_ROUTES);
            if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
                this.mRoutes = Collections.emptyList();
                return;
            }
            int size = parcelableArrayList.size();
            this.mRoutes = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                this.mRoutes.add(MediaRouteDescriptor.fromBundle((Bundle) parcelableArrayList.get(i)));
            }
        }
    }

    public boolean isValid() {
        ensureRoutes();
        int size = this.mRoutes.size();
        for (int i = 0; i < size; i++) {
            MediaRouteDescriptor mediaRouteDescriptor = this.mRoutes.get(i);
            if (mediaRouteDescriptor == null || !mediaRouteDescriptor.isValid()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "MediaRouteProviderDescriptor{ routes=" + Arrays.toString(getRoutes().toArray()) + ", isValid=" + isValid() + " }";
    }

    public Bundle asBundle() {
        return this.mBundle;
    }

    public static MediaRouteProviderDescriptor fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new MediaRouteProviderDescriptor(bundle, null);
        }
        return null;
    }

    public static final class Builder {
        private final Bundle mBundle;
        private ArrayList<MediaRouteDescriptor> mRoutes;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
            if (mediaRouteProviderDescriptor == null) {
                throw new IllegalArgumentException("descriptor must not be null");
            }
            this.mBundle = new Bundle(mediaRouteProviderDescriptor.mBundle);
            mediaRouteProviderDescriptor.ensureRoutes();
            if (mediaRouteProviderDescriptor.mRoutes.isEmpty()) {
                return;
            }
            this.mRoutes = new ArrayList<>(mediaRouteProviderDescriptor.mRoutes);
        }

        public Builder addRoute(MediaRouteDescriptor mediaRouteDescriptor) {
            if (mediaRouteDescriptor == null) {
                throw new IllegalArgumentException("route must not be null");
            }
            ArrayList<MediaRouteDescriptor> arrayList = this.mRoutes;
            if (arrayList == null) {
                this.mRoutes = new ArrayList<>();
            } else if (arrayList.contains(mediaRouteDescriptor)) {
                throw new IllegalArgumentException("route descriptor already added");
            }
            this.mRoutes.add(mediaRouteDescriptor);
            return this;
        }

        public Builder addRoutes(Collection<MediaRouteDescriptor> collection) {
            if (collection == null) {
                throw new IllegalArgumentException("routes must not be null");
            }
            if (!collection.isEmpty()) {
                Iterator<MediaRouteDescriptor> it2 = collection.iterator();
                while (it2.hasNext()) {
                    addRoute(it2.next());
                }
            }
            return this;
        }

        Builder setRoutes(Collection<MediaRouteDescriptor> collection) {
            if (collection == null || collection.isEmpty()) {
                this.mRoutes = null;
                this.mBundle.remove(MediaRouteProviderDescriptor.KEY_ROUTES);
            } else {
                this.mRoutes = new ArrayList<>(collection);
            }
            return this;
        }

        public MediaRouteProviderDescriptor build() {
            ArrayList<MediaRouteDescriptor> arrayList = this.mRoutes;
            if (arrayList != null) {
                int size = arrayList.size();
                ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    arrayList2.add(this.mRoutes.get(i).asBundle());
                }
                this.mBundle.putParcelableArrayList(MediaRouteProviderDescriptor.KEY_ROUTES, arrayList2);
            }
            return new MediaRouteProviderDescriptor(this.mBundle, this.mRoutes);
        }
    }
}
