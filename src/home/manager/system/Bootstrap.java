package home.manager.system;

import home.manager.system.hardware.SystemHardWare;
import home.manager.system.servercomms.ServerCommunication;
import home.manager.system.utils.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by kaso on 10/16/2014.
 */
public class Bootstrap {

    private static Settings settings;
    private static Scanner scanner;
    private static HomeManagerSystem homeManagerSystem;

    /**
     * @param args the command line arguments
     *             usage java HomeManagerSystem username password
     */
    public static void main(String[] args) {

        //checkForUpdate();

        settings = Settings.getInstance();
        scanner = new Scanner(getInputStream());
        homeManagerSystem = new HomeManagerSystem();

        String username,
                password;

        if (args.length > 2) {
            username = args[0];
            password = args[1];
        } else {
            username = getUsername();
            password = getPassword();
        }

        try {

            // ServerCommunication.initialize(username, password);
            SystemHardWare.initialize();

            homeManagerSystem.manage(ServerCommunication.getInstance(),
                    SystemHardWare.getInstance());

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static InputStream getInputStream() {
        return System.in;
    }

    private static String getPassword() {
        String password = settings.getPassword();
        if (null == password) {
            password = getInputFromConsole("Password: ");
        }
        return password;
    }

    private static String getUsername() {
        String username = settings.getUsername();
        if (null == username) {
            username = getInputFromConsole("Username: ");
        }
        return username;
    }

    private static String getInputFromConsole(String string) {
        System.out.print(string);
        return scanner.next();
    }

    private static void checkForUpdate() {
        String updateStr = "java -jar Updater.jar";
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec("update.bat");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
