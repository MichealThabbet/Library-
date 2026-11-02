package it.piapostolos.android.pinotes.models.listeners;

import android.location.Location;

/* loaded from: classes3.dex */
public interface OnGeoUtilResultListener {
    void onAddressResolved(String str);

    void onCoordinatesResolved(Location location, String str);

    void onLocationRetrieved(Location location);

    void onLocationUnavailable();
}
