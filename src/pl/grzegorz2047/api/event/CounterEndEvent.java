package pl.grzegorz2047.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Grzegorz2047. 28.08.2015.
 */
public class CounterEndEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public CounterEndEvent() {

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
}
