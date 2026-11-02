package com.github.barteksc.pdfviewer;

import android.graphics.RectF;
import com.github.barteksc.pdfviewer.model.PagePart;
import com.github.barteksc.pdfviewer.util.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/* loaded from: classes.dex */
class CacheManager {
    private final Object passiveActiveLock = new Object();
    private final PagePartComparator orderComparator = new PagePartComparator();
    private final PriorityQueue<PagePart> activeCache = new PriorityQueue<>(Constants.Cache.CACHE_SIZE, this.orderComparator);
    private final PriorityQueue<PagePart> passiveCache = new PriorityQueue<>(Constants.Cache.CACHE_SIZE, this.orderComparator);
    private final List<PagePart> thumbnails = new ArrayList();

    public void cachePart(PagePart pagePart) {
        synchronized (this.passiveActiveLock) {
            makeAFreeSpace();
            this.activeCache.offer(pagePart);
        }
    }

    public void makeANewSet() {
        synchronized (this.passiveActiveLock) {
            this.passiveCache.addAll(this.activeCache);
            this.activeCache.clear();
        }
    }

    private void makeAFreeSpace() {
        synchronized (this.passiveActiveLock) {
            while (this.activeCache.size() + this.passiveCache.size() >= Constants.Cache.CACHE_SIZE && !this.passiveCache.isEmpty()) {
                this.passiveCache.poll().getRenderedBitmap().recycle();
            }
            while (this.activeCache.size() + this.passiveCache.size() >= Constants.Cache.CACHE_SIZE && !this.activeCache.isEmpty()) {
                this.activeCache.poll().getRenderedBitmap().recycle();
            }
        }
    }

    public void cacheThumbnail(PagePart pagePart) {
        synchronized (this.thumbnails) {
            while (this.thumbnails.size() >= Constants.Cache.THUMBNAILS_CACHE_SIZE) {
                this.thumbnails.remove(0).getRenderedBitmap().recycle();
            }
            addWithoutDuplicates(this.thumbnails, pagePart);
        }
    }

    public boolean upPartIfContained(int i, RectF rectF, int i2) {
        PagePart pagePart = new PagePart(i, null, rectF, false, 0);
        synchronized (this.passiveActiveLock) {
            PagePart pagePartFind = find(this.passiveCache, pagePart);
            boolean z = true;
            if (pagePartFind != null) {
                this.passiveCache.remove(pagePartFind);
                pagePartFind.setCacheOrder(i2);
                this.activeCache.offer(pagePartFind);
                return true;
            }
            if (find(this.activeCache, pagePart) == null) {
                z = false;
            }
            return z;
        }
    }

    public boolean containsThumbnail(int i, RectF rectF) {
        PagePart pagePart = new PagePart(i, null, rectF, true, 0);
        synchronized (this.thumbnails) {
            Iterator<PagePart> it2 = this.thumbnails.iterator();
            while (it2.hasNext()) {
                if (it2.next().equals(pagePart)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void addWithoutDuplicates(Collection<PagePart> collection, PagePart pagePart) {
        Iterator<PagePart> it2 = collection.iterator();
        while (it2.hasNext()) {
            if (it2.next().equals(pagePart)) {
                pagePart.getRenderedBitmap().recycle();
                return;
            }
        }
        collection.add(pagePart);
    }

    private static PagePart find(PriorityQueue<PagePart> priorityQueue, PagePart pagePart) {
        Iterator<PagePart> it2 = priorityQueue.iterator();
        while (it2.hasNext()) {
            PagePart next = it2.next();
            if (next.equals(pagePart)) {
                return next;
            }
        }
        return null;
    }

    public List<PagePart> getPageParts() {
        ArrayList arrayList;
        synchronized (this.passiveActiveLock) {
            arrayList = new ArrayList(this.passiveCache);
            arrayList.addAll(this.activeCache);
        }
        return arrayList;
    }

    public List<PagePart> getThumbnails() {
        List<PagePart> list;
        synchronized (this.thumbnails) {
            list = this.thumbnails;
        }
        return list;
    }

    public void recycle() {
        synchronized (this.passiveActiveLock) {
            Iterator<PagePart> it2 = this.passiveCache.iterator();
            while (it2.hasNext()) {
                it2.next().getRenderedBitmap().recycle();
            }
            this.passiveCache.clear();
            Iterator<PagePart> it3 = this.activeCache.iterator();
            while (it3.hasNext()) {
                it3.next().getRenderedBitmap().recycle();
            }
            this.activeCache.clear();
        }
        synchronized (this.thumbnails) {
            Iterator<PagePart> it4 = this.thumbnails.iterator();
            while (it4.hasNext()) {
                it4.next().getRenderedBitmap().recycle();
            }
            this.thumbnails.clear();
        }
    }

    class PagePartComparator implements Comparator<PagePart> {
        PagePartComparator() {
        }

        @Override // java.util.Comparator
        public int compare(PagePart pagePart, PagePart pagePart2) {
            if (pagePart.getCacheOrder() == pagePart2.getCacheOrder()) {
                return 0;
            }
            return pagePart.getCacheOrder() > pagePart2.getCacheOrder() ? 1 : -1;
        }
    }
}
