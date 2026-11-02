package org.apache.poi.poifs.storage;

import java.io.IOException;

/* loaded from: classes3.dex */
abstract class BlockListImpl implements BlockList {
    private ListManagedBlock[] _blocks = new ListManagedBlock[0];
    private BlockAllocationTableReader _bat = null;

    protected BlockListImpl() {
    }

    protected void setBlocks(ListManagedBlock[] listManagedBlockArr) {
        this._blocks = listManagedBlockArr;
    }

    @Override // org.apache.poi.poifs.storage.BlockList
    public void zap(int i) {
        if (i >= 0) {
            ListManagedBlock[] listManagedBlockArr = this._blocks;
            if (i < listManagedBlockArr.length) {
                listManagedBlockArr[i] = null;
            }
        }
    }

    protected ListManagedBlock get(int i) {
        return this._blocks[i];
    }

    @Override // org.apache.poi.poifs.storage.BlockList
    public ListManagedBlock remove(int i) throws IOException {
        try {
            ListManagedBlock listManagedBlock = this._blocks[i];
            if (listManagedBlock == null) {
                throw new IOException("block[ " + i + " ] already removed - does your POIFS have circular or duplicate block references?");
            }
            this._blocks[i] = null;
            return listManagedBlock;
        } catch (ArrayIndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot remove block[ ");
            sb.append(i);
            sb.append(" ]; out of range[ 0 - ");
            sb.append(this._blocks.length - 1);
            sb.append(" ]");
            throw new IOException(sb.toString());
        }
    }

    @Override // org.apache.poi.poifs.storage.BlockList
    public ListManagedBlock[] fetchBlocks(int i, int i2) throws IOException {
        BlockAllocationTableReader blockAllocationTableReader = this._bat;
        if (blockAllocationTableReader == null) {
            throw new IOException("Improperly initialized list: no block allocation table provided");
        }
        return blockAllocationTableReader.fetchBlocks(i, i2, this);
    }

    @Override // org.apache.poi.poifs.storage.BlockList
    public void setBAT(BlockAllocationTableReader blockAllocationTableReader) throws IOException {
        if (this._bat != null) {
            throw new IOException("Attempt to replace existing BlockAllocationTable");
        }
        this._bat = blockAllocationTableReader;
    }

    @Override // org.apache.poi.poifs.storage.BlockList
    public int blockCount() {
        return this._blocks.length;
    }

    protected int remainingBlocks() {
        int i = 0;
        int i2 = 0;
        while (true) {
            ListManagedBlock[] listManagedBlockArr = this._blocks;
            if (i >= listManagedBlockArr.length) {
                return i2;
            }
            if (listManagedBlockArr[i] != null) {
                i2++;
            }
            i++;
        }
    }
}
