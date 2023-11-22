package de.zeppy5.rgbplugintest.util;

import java.util.List;

public class ServerPlayer {
    String uuid;
    boolean auth;
    List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean getAuth(){
        return auth;
    }
}
