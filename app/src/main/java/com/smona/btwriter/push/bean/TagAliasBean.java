package com.smona.btwriter.push.bean;

import java.util.Set;

public class TagAliasBean {
    public int action;
    public Set<String> tags;
    public String alias;
    public boolean isAliasAction;

    @Override
    public String toString() {
        return "TagAliasBean{" +
                "action=" + action +
                ", tags=" + tags +
                ", alias='" + alias + '\'' +
                ", isAliasAction=" + isAliasAction +
                '}';
    }
}
