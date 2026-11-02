package com.app.piapostolos.GamesBeans;

import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes.dex */
public class Puzzle {
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int[] DIRECTION_X = {-1, 0, 1, 0};
    public static final int[] DIRECTION_Y = {0, -1, 0, 1};
    private int handleLocation;
    private int height;
    private int moveCount;
    private Random random = new Random();
    private int[] tiles;
    private int width;

    private int invertMove(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 4;
        }
        if (i == 2) {
            return 8;
        }
        if (i == 4) {
            return 1;
        }
        return i == 8 ? 2 : 0;
    }

    public void init(int i, int i2) {
        this.width = i;
        this.height = i2;
        this.tiles = new int[i * i2];
        int i3 = 0;
        while (true) {
            int[] iArr = this.tiles;
            if (i3 < iArr.length) {
                iArr[i3] = i3;
                i3++;
            } else {
                this.handleLocation = iArr.length - 1;
                this.moveCount = 0;
                return;
            }
        }
    }

    public boolean isSolved() {
        int i = 0;
        while (true) {
            int[] iArr = this.tiles;
            if (i >= iArr.length) {
                return true;
            }
            if (iArr[i] != i) {
                return false;
            }
            i++;
        }
    }

    public void setTiles(int[] iArr) {
        this.tiles = iArr;
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] == iArr.length - 1) {
                this.handleLocation = i;
                return;
            }
        }
    }

    public int[] getTiles() {
        return this.tiles;
    }

    public int getColumnAt(int i) {
        return i % this.width;
    }

    public int getRowAt(int i) {
        return i / this.width;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int hammingDistance() {
        int i = 0;
        int iAbs = 0;
        while (true) {
            int[] iArr = this.tiles;
            if (i >= iArr.length) {
                return iAbs;
            }
            iAbs += Math.abs(i - iArr[i]);
            i++;
        }
    }

    public void scramble() {
        int i;
        int i2 = this.width;
        if (i2 < 2 || (i = this.height) < 2) {
            return;
        }
        int iMax = i2 * i * Math.max(i2, i);
        int iPickRandomMove = 0;
        while (hammingDistance() < iMax) {
            iPickRandomMove = pickRandomMove(invertMove(iPickRandomMove));
            move(iPickRandomMove, 1);
        }
        this.moveCount = 0;
    }

    private int pickRandomMove(int i) {
        ArrayList arrayList = new ArrayList(4);
        int possibleMoves = (~i) & getPossibleMoves();
        if ((possibleMoves & 1) > 0) {
            arrayList.add(0);
        }
        if ((possibleMoves & 2) > 0) {
            arrayList.add(1);
        }
        if ((possibleMoves & 4) > 0) {
            arrayList.add(2);
        }
        if ((possibleMoves & 8) > 0) {
            arrayList.add(3);
        }
        return ((Integer) arrayList.get(this.random.nextInt(arrayList.size()))).intValue();
    }

    public boolean move(int i, int i2) {
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.handleLocation;
            int i5 = DIRECTION_X[i] + i4 + (DIRECTION_Y[i] * this.width);
            int[] iArr = this.tiles;
            iArr[i4] = iArr[i5];
            z |= iArr[i4] == i4;
            int[] iArr2 = this.tiles;
            iArr2[i5] = iArr2.length - 1;
            this.handleLocation = i5;
        }
        this.moveCount++;
        return z;
    }

    public int getPossibleMoves() {
        int columnAt = getColumnAt(this.handleLocation);
        int rowAt = getRowAt(this.handleLocation);
        return (columnAt < this.width - 1 ? 4 : 0) | (columnAt > 0 ? 1 : 0) | (rowAt > 0 ? 2 : 0) | (rowAt < this.height - 1 ? 8 : 0);
    }

    public int getDirection(int i) {
        int i2 = this.handleLocation;
        int i3 = i - i2;
        int i4 = this.width;
        if (i3 % i4 == 0) {
            return i3 < 0 ? 1 : 3;
        }
        if (i2 / i4 == (i2 + i3) / i4) {
            return i3 < 0 ? 0 : 2;
        }
        return -1;
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public void setMoveCount(int i) {
        this.moveCount = i;
    }

    public int getHandleLocation() {
        return this.handleLocation;
    }
}
