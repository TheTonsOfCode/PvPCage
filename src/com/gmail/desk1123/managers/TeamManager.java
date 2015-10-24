package com.gmail.desk1123.managers;

import com.gmail.desk1123.objets.Team;
import com.gmail.desk1123.objets.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dekros987
 */
public class TeamManager {

    public static List<Team> teams = new ArrayList<>();

    public static void addTeam(Team t) {
        teams.add(t);
    }

    public static void removeTeam(Team t) {
        if (isTeam(t)) {
            teams.remove(t);
        }
    }

    public static boolean isTeam(Team t) {
        return teams.contains(t);
    }

    public static List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    public static Team getTeam(User u) {
        for (Team t : getTeams()) {
            if (t.getLeader().equals(u)) {
                return t;
            }
        }
        return null;
    }

    public static Team getTeam(String tag) {
        for (Team t : getTeams()) {
            if (t.getTag().equals(tag)) {
                return t;
            }
        }
        return null;
    }

}
