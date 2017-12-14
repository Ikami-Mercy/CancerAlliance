package app.feed.mercyapp.connection;

/**
 * Created by toni on 9/20/17.
 */

public class APIError {

    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}