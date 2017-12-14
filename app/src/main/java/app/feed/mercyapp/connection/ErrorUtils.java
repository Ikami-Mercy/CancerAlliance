package app.feed.mercyapp.connection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


/**
 * Created by toni on 9/20/17.
 */

public class ErrorUtils {

    public ErrorUtils() {
    }

    public static APIError parseError(Response<?> response) {

        Converter<ResponseBody, APIError> converter =
                ApiClient.getClient()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }

    public String parseOnFailure(Throwable t) {

        if (t instanceof IOException) {
            //Add your code for displaying no network connection error
            return "Check your internet connectivity.";
        } else if (t instanceof SocketTimeoutException) {
            return "Connection timeout.";
        }

        return "Something went wrong.";
    }
}

