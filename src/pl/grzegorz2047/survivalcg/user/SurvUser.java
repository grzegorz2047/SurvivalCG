package pl.grzegorz2047.survivalcg.user;

import pl.grzegorz2047.api.user.User;

/**
 * Created by grzeg on 19.12.2015.
 */
public class SurvUser extends User {


    private int points = 1000;//Start points
    private String group = "";

    public SurvUser(String username, boolean spectator) {
        super(username, spectator);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
}
