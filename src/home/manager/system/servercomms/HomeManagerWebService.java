package home.manager.system.servercomms;

import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

public interface HomeManagerWebService {

    /**
     * Used to fetch new set of messages from the server
     *
     * @param token
     * @param callback
     */
    @GET("/units/messages/new")
    void getNewMessage(
            @Header("Authorization") String token,
            Callback<List<ServerCommunicationMessage>> callback
    );

    /**
     * Used to send new message to server.
     *
     * @param token
     * @param message
     * @param callback
     */
    @POST("/units/messages/new")
    void sendNewMessage(
            @Header("Authorization") String token,
            @Body ServerCommunicationMessage message,
            Callback<ServerCommunicationMessage> callback
    );

    /**
     * @param username
     * @param password
     * @return token that will be used for further communications.
     */
    @FormUrlEncoded
    @POST("/units/login")
    String login(@Field("username") String username, @Field("password") String password);

}
