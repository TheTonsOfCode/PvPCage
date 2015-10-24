package com.noname.pvpcage.objects;

import java.util.List;

/**
 *
 * @author dekros987
 */
public class Team {

    private int MAX_SIZE;
    private List<User> members;
    private String tag;
    private String name;
    private User leader;

    public Team() {
        MAX_SIZE = 5;
        tag = "";
        name = "";

    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }

    public void setMAX_SIZE(int MAX_SIZE) {
        this.MAX_SIZE = MAX_SIZE;
    }

    public List<User> getMembers() {
        return members;
    }

    public boolean containsMember(User user) {
        return members.contains(user);
    }

    public void removeMember(User user) {
        if (containsMember(user)) {
            members.remove(user);
        }
    }

    public void addMember(User user) {
        if (!containsMember(user)) {
            members.add(user);
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

}
