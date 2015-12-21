package pl.grzegorz2047.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.grzegorz2047.api.runnable.Counter;

/**
 * Created by Grzegorz2047. 31.08.2015.
 */
public class CountdownSecondEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Counter counter;


    public CountdownSecondEvent(int currentTime, Counter counter) {
        this.counter = counter;
        this.currentTime = currentTime;
    }

    private int currentTime;

    public int getCurrentTime() {
        return currentTime;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public Counter getCounter() {
        return counter;
    }
}
