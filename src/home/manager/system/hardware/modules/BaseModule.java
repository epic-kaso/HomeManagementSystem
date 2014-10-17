package home.manager.system.hardware.modules;

/**
 * Created by kaso on 10/17/2014.
 */
abstract public class BaseModule implements Module {

    private static long classId = 0l;
    private long id;
    private STATE currentState;


    public BaseModule() {
        this(classId++);
    }

    public BaseModule(long id) {
        this.setId(id);
        this.turnOff();
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean turnOn() {
        this.currentState = STATE.ON;
        return true;
    }

    @Override
    public boolean turnOff() {
        this.currentState = STATE.OFF;
        return true;
    }

    @Override
    public STATE getCurrentState() {
        return this.currentState;
    }

    @Override
    public String toString() {
        return String.format("Class: %s, Id: %s", this.getClass().getSimpleName(), this.getId());
    }
}
