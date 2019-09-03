package top.ok.fine.pomegranate.system.exception;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/09/02
 */
public class AuthException extends RuntimeException {

    private int errorCode = 401;

    private String errorMsg;

    public AuthException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AuthException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}
