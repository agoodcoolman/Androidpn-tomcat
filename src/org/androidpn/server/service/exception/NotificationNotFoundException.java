package org.androidpn.server.service.exception;
/**
 * 
  * @ClassName: NotificationNotFoundException
  * @Description: Thrown it if the notification not found by userName;
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-19 下午3:00:57
  *
 */
public class NotificationNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public NotificationNotFoundException() {
        super();
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }

    public NotificationNotFoundException(Throwable cause) {
        super(cause);
    }

    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
