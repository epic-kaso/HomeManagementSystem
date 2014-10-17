package home.manager.system.hardware.modules;

/**
 * Created by kaso on 10/17/2014.
 */
public interface Module {
    public long getId();

    ;

    public void setId(long id);

    public boolean turnOn();

    public boolean turnOff();

    public STATE getCurrentState();

    public enum STATE {ON, OFF}
}
