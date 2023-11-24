package de.zeppy5.rgbplugintest.util;

import java.util.List;

public class Role {

    String name;
    String prefix;
    List<String> permissions;

    String priority;

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public String getPriority() {
        return priority;
    }
}
