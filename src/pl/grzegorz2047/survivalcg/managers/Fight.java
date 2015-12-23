package pl.grzegorz2047.survivalcg.managers;

/**
 * Created by grzeg on 23.12.2015.
 */
public class Fight {
    private String victim;
    private String attacker;
    private Long lastHitTime;
    private int cooldown = 10;//ile trzeba poczekac przed logoutem w sek

    public Fight(String attacker, String victim, Long lastHitTime) {
        this.victim = victim;
        this.attacker = attacker;
        this.lastHitTime = lastHitTime;
    }

    public Long getEndCooldown(){
        return lastHitTime + cooldown * 1000;
    }

    public String getVictim() {
        return victim;
    }

    public String getAttacker() {
        return attacker;
    }

    public Long getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(Long lastHitTime) {
        this.lastHitTime = lastHitTime;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }
}
