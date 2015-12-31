package pl.grzegorz2047.survivalcg.guild.relation;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class Relation {

    public Relation(String who, String withWho, Status state){
        this.who = who;
        this.withWho = withWho;
        this.startDate = System.currentTimeMillis();
        this.expireDate = (120 * 1000) + System.currentTimeMillis();
        this.state = state;
    }

    private String who;
    private String withWho;
    private long startDate;
    private long expireDate;
    private boolean expired;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public enum Status { ALLY, ENEMY }

    private Status state;

    public String getWithWho() {
        return withWho;
    }

    public void setWithWho(String withWho) {
        this.withWho = withWho;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

}
