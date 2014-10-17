/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.manager.system.servercomms;

import home.manager.system.common.CommunicableListener;
import home.manager.system.common.Message;
import home.manager.system.utils.Settings;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
public class ServerCommunication extends CommunicableListener {

    static ServerCommunication serverCommunication;
    private final HomeManagerWebService homeManagerWebService;
    private String token;

    private ServerCommunication(String username, String password) throws Exception {

        Settings settings = Settings.getInstance();
        String endpoint =
                settings.getHostAddress() == null ? "http://169.254.123.43:8000" : settings.getHostAddress();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .setErrorHandler(new ServerErrorHandler())
                .build();

        this.homeManagerWebService = restAdapter.create(HomeManagerWebService.class);
        try {
            token = this.homeManagerWebService.login(username, password);
        } catch (RetrofitError e) {
            System.out.println(e.getMessage());
        }

        if (null != token) {

            //this.pollForNewMessage();
        } else {
            throw new Exception("Token must not be null, please ensure you have a valid username/password and you are connected to a network");
        }

    }

    /**
     * Initialize the server communications
     *
     * @throws Exception
     */
    public static void initialize(String username, String password) throws Exception {
        serverCommunication = new ServerCommunication(username, password);
    }

    /**
     * Get an Instance of the server communications.
     *
     * @return
     * @throws Exception
     */
    public static ServerCommunication getInstance() throws Exception {
        if (null == serverCommunication)
            throw new Exception("Must initialize Servercomms first call the static method 'initialize'");
        return serverCommunication;
    }

    /**
     * Creates a new thread that polls for new messages from the server    *
     */
    private void pollForNewMessage() {
        Timer pollNewMessagesTimer = new Timer("Poll For Messages");
        TimerTask pollNewMessagesTimerTask = new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Polling for New MESSAGES!!!");
                    homeManagerWebService.getNewMessage(getToken(), new Callback<List<ServerCommunicationMessage>>() {

                        @Override
                        public void success(List<ServerCommunicationMessage> messages, Response arg1) {
                            if (null != messages && messages.size() > 0) {
                                System.out.println("We've got some messages....");
                                for (ServerCommunicationMessage message : messages) {
                                    informObservers(message);
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };

        pollNewMessagesTimer.schedule(pollNewMessagesTimerTask, 100);

    }

    /**
     * Inform Observers of new state of the ServerComms.
     *
     * @param o
     */
    private synchronized void informObservers(Message o) {
        System.out.println("Informing observers....");
        this.notifyListeners(o);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean sendMessage(final Message message) {
        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("!!! Sending Message !!!");
                homeManagerWebService.sendNewMessage(getToken(), (ServerCommunicationMessage) message,
                        new Callback<ServerCommunicationMessage>() {

                            @Override
                            public void success(ServerCommunicationMessage messages, Response arg1) {
                                if (null != messages) {
                                    System.out.println("We've got a message response ....");
                                    informObservers(message);
                                }
                            }

                            @Override
                            public void failure(RetrofitError arg0) {
                                System.out.println("Error Occcured: " + arg0.getMessage());

                            }
                        });

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        return true;
    }
}
