package home.manager.system.servercomms;

import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

/**
 * **********************************************************************
 *
 * @author :  OKAFOR AKACHUKWU
 * @email :  kasoprecede47@gmail.com
 * @date :  10/16/2014
 * This file was created by the said author as written above
 * see http://www.kaso.co/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2014 OKAFOR AKACHUKWU
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
