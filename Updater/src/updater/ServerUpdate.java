/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package updater;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kaso
 */
public class ServerUpdate {

    private Listener listener;
    private int size;
    private int downloaded;
    private URL url;
    private STATUS currentStatus;
    private String tempDownloadFileName = "temp_update.jar";

    public ServerUpdate(URL url) {
        this.url = url;
        size = -1;
        downloaded = 0;
        this.currentStatus = STATUS.DOWNLOADING;
        this.download();
    }

    public void download() {
        ExecutorService s = Executors.newCachedThreadPool();

        s.execute(this::performDownload);
    }

    private void error() {
        this.currentStatus = STATUS.ERROR;
        notifyListener();
    }

    private void performDownload() {
        if (this.url == null) {
            return;
        }

        RandomAccessFile randomAccessFile;
        InputStream inputStream;


        try {

            HttpURLConnection urlConnection =
                    (HttpURLConnection) this.url.openConnection();
            urlConnection.setRequestProperty("Range", "bytes=" + downloaded + "-");

            urlConnection.connect();

            if (urlConnection.getResponseCode() / 100 != 2) {
                error();
            }

            int contentLength = urlConnection.getContentLength();

            if (contentLength < 1) {
                error();
            }


            if (this.size == -1) {
                System.out.println("size: " + contentLength);
                this.size = contentLength;
                notifyListener();
            }

            randomAccessFile = new RandomAccessFile(this.tempDownloadFileName, "rw");
            randomAccessFile.seek(this.downloaded);

            inputStream = urlConnection.getInputStream();

            while (this.currentStatus == STATUS.DOWNLOADING) {
                byte[] buffer;

                int MAX_BUFFER_SIZE = 1024;
                if (size - downloaded > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[size - downloaded];
                }

                int read = inputStream.read(buffer);
                if (read == -1)
                    break;

                randomAccessFile.write(buffer, 0, read);
                System.out.println("reading: " + read);

                this.downloaded += read;
                notifyListener();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            randomAccessFile.close();

            if (this.currentStatus == STATUS.DOWNLOADING) {
                this.currentStatus = STATUS.UPDATING;
                notifyListener();
                updateSystem();
            }

        } catch (IOException e) {
            error();
            Logger.getLogger(ServerUpdate.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void updateSystem() {
        Path downloaded = Paths.get(this.tempDownloadFileName);

        String mainSystemFileName = "SmartHome.jar";
        Path path = Paths.get(mainSystemFileName);
        Path backUp = Paths.get(mainSystemFileName + ".bk");
        if (Files.exists(path)) {
            System.out.println("GOOD! Path found:" + path.toString());
            try {
                Files.copy(path, backUp, StandardCopyOption.REPLACE_EXISTING);

                Files.copy(downloaded, path, StandardCopyOption.REPLACE_EXISTING);
                this.currentStatus = STATUS.COMPLETED;

                notifyListener();
            } catch (IOException e) {
                e.printStackTrace();
                error();
                Logger.getLogger(ServerUpdate.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            System.out.println("BAD! Path doesnt exist:" + path.toString());
            error();
        }

        try {
            Files.deleteIfExists(downloaded);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener l) {
        this.listener = l;
    }

    private void notifyListener() {
        if (listener != null) {
            System.out.println("downloaded: " + this.downloaded + ", Size: " + this.size);
            int i = (int) (((float) this.downloaded / this.size) * 100);
            System.out.println("Progress: " + i);
            listener.update(i, this.currentStatus);
        }
    }

    public enum STATUS {DOWNLOADING, ERROR, UPDATING, COMPLETED}


    interface Listener {
        public void update(int progress, STATUS status);
    }
}
