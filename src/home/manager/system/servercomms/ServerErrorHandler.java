package home.manager.system.servercomms;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ServerErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError arg0) {
        Response r = arg0.getResponse();
        System.out.print(r);
        return arg0;
    }

}
