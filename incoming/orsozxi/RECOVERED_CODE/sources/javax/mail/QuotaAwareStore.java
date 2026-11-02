package javax.mail;

/* loaded from: classes3.dex */
public interface QuotaAwareStore {
    Quota[] getQuota(String str) throws MessagingException;

    void setQuota(Quota quota) throws MessagingException;
}
