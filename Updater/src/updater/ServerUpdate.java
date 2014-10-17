/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package updater;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kaso
 */
public class ServerUpdate {

    private Listener listener;


    public void fetchUpdateFromServer(Listener l) {
        this.listener = l;
        ExecutorService s = Executors.newCachedThreadPool();

        s.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i += 20) {
                    if (listener != null)
                        listener.update(i);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServerUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }


    interface Listener {
        public void update(int progress);
    }
}
