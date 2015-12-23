package pl.grzegorz2047.survivalcg.teleport;

import org.bukkit.Location;

/**
 * Created by grzeg on 23.12.2015.
 */
public class TeleportRequest {

    private Location destination;
    private Location source;
    private String requester;
    private Long teleportTime;

    public TeleportRequest(String username,Location source, Location destination , Long currentTime, int delay) {
        this.requester = username;
        this.teleportTime = currentTime + 1000 * delay;
        this.destination = destination;
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public String getRequester() {
        return requester;
    }

    public Long getTeleportTime() {
        return teleportTime;
    }

    public Location getSource() {
        return source;
    }
}
