package net.fortuna.ical4j.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* loaded from: classes3.dex */
public class InetAddressHostInfo implements HostInfo {
    private final InetAddress hostAddress;

    public InetAddressHostInfo() throws SocketException {
        this(findNonLoopbackAddress());
    }

    public InetAddressHostInfo(InetAddress inetAddress) {
        this.hostAddress = inetAddress;
    }

    @Override // net.fortuna.ical4j.util.HostInfo
    public String getHostName() {
        return this.hostAddress.getHostName();
    }

    private static InetAddress findNonLoopbackAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                if (!inetAddressNextElement.isLoopbackAddress()) {
                    return inetAddressNextElement;
                }
            }
        }
        return null;
    }
}
