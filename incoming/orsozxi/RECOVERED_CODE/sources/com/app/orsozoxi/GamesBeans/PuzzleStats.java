package com.app.orsozoxi.GamesBeans;

/* loaded from: classes.dex */
public class PuzzleStats {
    private float avg;
    private int best;
    private boolean newBest;
    private int plays;

    public PuzzleStats(int i, int i2, float f, boolean z) {
        this.plays = i;
        this.best = i2;
        this.avg = f;
        this.newBest = z;
    }

    public int getPlays() {
        return this.plays;
    }

    public int getBest() {
        return this.best;
    }

    public float getAvg() {
        return this.avg;
    }

    public boolean isNewBest() {
        return this.newBest;
    }
}
