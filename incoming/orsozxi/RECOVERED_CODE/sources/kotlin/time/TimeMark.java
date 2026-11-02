package kotlin.time;

import kotlin.Metadata;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* compiled from: TimeSource.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u0004H&ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0005\u0010\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u001b\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0004H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ\u001b\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0004H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\r\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0010"}, d2 = {"Lkotlin/time/TimeMark;", "", "()V", "elapsedNow", "Lkotlin/time/Duration;", "elapsedNow-UwyO8pc", "()D", "hasNotPassedNow", "", "hasPassedNow", "minus", FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION, "minus-LRDsOJo", "(D)Lkotlin/time/TimeMark;", "plus", "plus-LRDsOJo", "kotlin-stdlib"}, k = 1, mv = {1, 4, 0})
/* loaded from: classes3.dex */
public abstract class TimeMark {
    /* renamed from: elapsedNow-UwyO8pc */
    public abstract double mo1290elapsedNowUwyO8pc();

    /* renamed from: plus-LRDsOJo */
    public TimeMark mo1291plusLRDsOJo(double duration) {
        return new AdjustedTimeMark(this, duration, null);
    }

    /* renamed from: minus-LRDsOJo, reason: not valid java name */
    public TimeMark m1347minusLRDsOJo(double duration) {
        return mo1291plusLRDsOJo(Duration.m1338unaryMinusUwyO8pc(duration));
    }

    public final boolean hasPassedNow() {
        return !Duration.m1318isNegativeimpl(mo1290elapsedNowUwyO8pc());
    }

    public final boolean hasNotPassedNow() {
        return Duration.m1318isNegativeimpl(mo1290elapsedNowUwyO8pc());
    }
}
