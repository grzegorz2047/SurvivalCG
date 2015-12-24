package pl.grzegorz2047.survivalcg.managers;

/**
 * Created by grzeg on 24.12.2015.
 */
public class PermissionsManager {

    private String vipPerms;

    public PermissionsManager(String vipPerms){
        this.vipPerms = vipPerms;
    }

    public String getVipPerms() {
        return vipPerms;
    }

    public void setVipPerms(String vipPerms) {
        this.vipPerms = vipPerms;
    }
}
