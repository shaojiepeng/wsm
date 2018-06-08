package com.wsm.sso.exception;

/**
 *  2018-04-02 21:01:41
 */
public class SSOException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public SSOException(String msg) {
        super(msg);
    }

    public SSOException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SSOException(Throwable cause) {
        super(cause);
    }

}
