package org.apache.poi.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* loaded from: classes3.dex */
public class CommonsLogger extends POILogger {
    private static LogFactory _creator = LogFactory.getFactory();
    private Log log = null;

    @Override // org.apache.poi.util.POILogger
    public void initialize(String str) {
        this.log = _creator.getInstance(str);
    }

    @Override // org.apache.poi.util.POILogger
    public void log(int i, Object obj) {
        if (i == FATAL) {
            if (this.log.isFatalEnabled()) {
                this.log.fatal(obj);
                return;
            }
            return;
        }
        if (i == ERROR) {
            if (this.log.isErrorEnabled()) {
                this.log.error(obj);
                return;
            }
            return;
        }
        if (i == WARN) {
            if (this.log.isWarnEnabled()) {
                this.log.warn(obj);
            }
        } else if (i == INFO) {
            if (this.log.isInfoEnabled()) {
                this.log.info(obj);
            }
        } else if (i == DEBUG) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(obj);
            }
        } else if (this.log.isTraceEnabled()) {
            this.log.trace(obj);
        }
    }

    @Override // org.apache.poi.util.POILogger
    public void log(int i, Object obj, Throwable th) {
        if (i == FATAL) {
            if (this.log.isFatalEnabled()) {
                if (obj != null) {
                    this.log.fatal(obj, th);
                    return;
                } else {
                    this.log.fatal(th);
                    return;
                }
            }
            return;
        }
        if (i == ERROR) {
            if (this.log.isErrorEnabled()) {
                if (obj != null) {
                    this.log.error(obj, th);
                    return;
                } else {
                    this.log.error(th);
                    return;
                }
            }
            return;
        }
        if (i == WARN) {
            if (this.log.isWarnEnabled()) {
                if (obj != null) {
                    this.log.warn(obj, th);
                    return;
                } else {
                    this.log.warn(th);
                    return;
                }
            }
            return;
        }
        if (i == INFO) {
            if (this.log.isInfoEnabled()) {
                if (obj != null) {
                    this.log.info(obj, th);
                    return;
                } else {
                    this.log.info(th);
                    return;
                }
            }
            return;
        }
        if (i == DEBUG) {
            if (this.log.isDebugEnabled()) {
                if (obj != null) {
                    this.log.debug(obj, th);
                    return;
                } else {
                    this.log.debug(th);
                    return;
                }
            }
            return;
        }
        if (this.log.isTraceEnabled()) {
            if (obj != null) {
                this.log.trace(obj, th);
            } else {
                this.log.trace(th);
            }
        }
    }

    @Override // org.apache.poi.util.POILogger
    public boolean check(int i) {
        return i == FATAL ? this.log.isFatalEnabled() : i == ERROR ? this.log.isErrorEnabled() : i == WARN ? this.log.isWarnEnabled() : i == INFO ? this.log.isInfoEnabled() : i == DEBUG && this.log.isDebugEnabled();
    }
}
