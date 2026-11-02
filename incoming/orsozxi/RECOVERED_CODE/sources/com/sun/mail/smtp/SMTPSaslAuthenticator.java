package com.sun.mail.smtp;

import com.huawei.hms.framework.common.ContainerUtils;
import com.sun.mail.auth.OAuth2SaslClientFactory;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import com.sun.mail.util.MailLogger;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

/* loaded from: classes2.dex */
public class SMTPSaslAuthenticator implements SaslAuthenticator {
    private String host;
    private MailLogger logger;
    private String name;
    private SMTPTransport pr;
    private Properties props;

    static {
        try {
            OAuth2SaslClientFactory.init();
        } catch (Throwable unused) {
        }
    }

    public SMTPSaslAuthenticator(SMTPTransport sMTPTransport, String str, Properties properties, MailLogger mailLogger, String str2) {
        this.pr = sMTPTransport;
        this.name = str;
        this.props = properties;
        this.logger = mailLogger;
        this.host = str2;
    }

    @Override // com.sun.mail.smtp.SaslAuthenticator
    public boolean authenticate(String[] strArr, final String str, String str2, final String str3, final String str4) throws MessagingException {
        String string;
        int iSimpleCommand;
        String str5;
        byte[] bArrEvaluateChallenge;
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("SASL Mechanisms:");
            for (String str6 : strArr) {
                this.logger.fine(" " + str6);
            }
            this.logger.fine("");
        }
        try {
            SaslClient saslClientCreateSaslClient = Sasl.createSaslClient(strArr, str2, this.name, this.host, this.props, new CallbackHandler() { // from class: com.sun.mail.smtp.SMTPSaslAuthenticator.1
                @Override // javax.security.auth.callback.CallbackHandler
                public void handle(Callback[] callbackArr) {
                    if (SMTPSaslAuthenticator.this.logger.isLoggable(Level.FINE)) {
                        SMTPSaslAuthenticator.this.logger.fine("SASL callback length: " + callbackArr.length);
                    }
                    for (int i = 0; i < callbackArr.length; i++) {
                        if (SMTPSaslAuthenticator.this.logger.isLoggable(Level.FINE)) {
                            SMTPSaslAuthenticator.this.logger.fine("SASL callback " + i + ": " + callbackArr[i]);
                        }
                        if (callbackArr[i] instanceof NameCallback) {
                            ((NameCallback) callbackArr[i]).setName(str3);
                        } else if (callbackArr[i] instanceof PasswordCallback) {
                            ((PasswordCallback) callbackArr[i]).setPassword(str4.toCharArray());
                        } else if (callbackArr[i] instanceof RealmCallback) {
                            RealmCallback realmCallback = (RealmCallback) callbackArr[i];
                            String defaultText = str;
                            if (defaultText == null) {
                                defaultText = realmCallback.getDefaultText();
                            }
                            realmCallback.setText(defaultText);
                        } else if (callbackArr[i] instanceof RealmChoiceCallback) {
                            RealmChoiceCallback realmChoiceCallback = (RealmChoiceCallback) callbackArr[i];
                            if (str == null) {
                                realmChoiceCallback.setSelectedIndex(realmChoiceCallback.getDefaultChoice());
                            } else {
                                String[] choices = realmChoiceCallback.getChoices();
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= choices.length) {
                                        break;
                                    }
                                    if (choices[i2].equals(str)) {
                                        realmChoiceCallback.setSelectedIndex(i2);
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                    }
                }
            });
            if (saslClientCreateSaslClient == null) {
                this.logger.fine("No SASL support");
                throw new UnsupportedOperationException("No SASL support");
            }
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("SASL client " + saslClientCreateSaslClient.getMechanismName());
            }
            try {
                String mechanismName = saslClientCreateSaslClient.getMechanismName();
                if (saslClientCreateSaslClient.hasInitialResponse()) {
                    byte[] bArrEvaluateChallenge2 = saslClientCreateSaslClient.evaluateChallenge(new byte[0]);
                    if (bArrEvaluateChallenge2.length > 0) {
                        byte[] bArrEncode = BASE64EncoderStream.encode(bArrEvaluateChallenge2);
                        string = ASCIIUtility.toString(bArrEncode, 0, bArrEncode.length);
                    } else {
                        string = ContainerUtils.KEY_VALUE_DELIMITER;
                    }
                } else {
                    string = null;
                }
                if (string != null) {
                    iSimpleCommand = this.pr.simpleCommand("AUTH " + mechanismName + " " + string);
                } else {
                    iSimpleCommand = this.pr.simpleCommand("AUTH " + mechanismName);
                }
                if (iSimpleCommand == 530) {
                    this.pr.startTLS();
                    if (string != null) {
                        iSimpleCommand = this.pr.simpleCommand("AUTH " + mechanismName + " " + string);
                    } else {
                        iSimpleCommand = this.pr.simpleCommand("AUTH " + mechanismName);
                    }
                }
                if (iSimpleCommand == 235) {
                    return true;
                }
                if (iSimpleCommand != 334) {
                    return false;
                }
                boolean z = false;
                while (!z) {
                    if (iSimpleCommand == 334) {
                        try {
                            if (saslClientCreateSaslClient.isComplete()) {
                                bArrEvaluateChallenge = null;
                            } else {
                                byte[] bytes = ASCIIUtility.getBytes(responseText(this.pr));
                                if (bytes.length > 0) {
                                    bytes = BASE64DecoderStream.decode(bytes);
                                }
                                if (this.logger.isLoggable(Level.FINE)) {
                                    this.logger.fine("SASL challenge: " + ASCIIUtility.toString(bytes, 0, bytes.length) + " :");
                                }
                                bArrEvaluateChallenge = saslClientCreateSaslClient.evaluateChallenge(bytes);
                            }
                            if (bArrEvaluateChallenge == null) {
                                this.logger.fine("SASL: no response");
                                iSimpleCommand = this.pr.simpleCommand("");
                            } else {
                                if (this.logger.isLoggable(Level.FINE)) {
                                    this.logger.fine("SASL response: " + ASCIIUtility.toString(bArrEvaluateChallenge, 0, bArrEvaluateChallenge.length) + " :");
                                }
                                iSimpleCommand = this.pr.simpleCommand(BASE64EncoderStream.encode(bArrEvaluateChallenge));
                            }
                        } catch (Exception e) {
                            this.logger.log(Level.FINE, "SASL Exception", (Throwable) e);
                        }
                    }
                    z = true;
                }
                if (iSimpleCommand != 235) {
                    return false;
                }
                if (!saslClientCreateSaslClient.isComplete() || (str5 = (String) saslClientCreateSaslClient.getNegotiatedProperty("javax.security.sasl.qop")) == null || (!str5.equalsIgnoreCase("auth-int") && !str5.equalsIgnoreCase("auth-conf"))) {
                    return true;
                }
                this.logger.fine("SASL Mechanism requires integrity or confidentiality");
                return false;
            } catch (Exception e2) {
                this.logger.log(Level.FINE, "SASL AUTHENTICATE Exception", (Throwable) e2);
                return false;
            }
        } catch (SaslException e3) {
            this.logger.log(Level.FINE, "Failed to create SASL client", e3);
            throw new UnsupportedOperationException(e3.getMessage(), e3);
        }
    }

    private static final String responseText(SMTPTransport sMTPTransport) {
        String strTrim = sMTPTransport.getLastServerResponse().trim();
        return strTrim.length() > 4 ? strTrim.substring(4) : "";
    }
}
