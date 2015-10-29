package com.noname.pvpcage.managers;

import com.noname.pvpcage.objects.Team;
import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.utilities.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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

    public static void loadTeamsFromFile() {
        int load = 0;
        for (File f : FileManager.getPartyData().listFiles()) {
            FileConfiguration yml = YamlConfiguration.loadConfiguration(f);
            Team t = new Team();
            t.setTag(yml.getString("Tag"));
            t.setLeader(UserManager.getUser(UUID.fromString(yml.getString("Leader.Uuid"))));
            t.setMod(UserManager.getUser(UUID.fromString(yml.getString("Mod.Uuid"))));
            t.setName(yml.getString("Name"));
            load++;
        }
        Log.INFO.print("&2Zaladowano: &6&l" + load + "&r&2 Teamow");
    }
    
    public static void saveTeamtoFile(){
        int saved = 0;
        for(Team t : teams){
            File f = new File(FileManager.getPartyData(),t.getTag()+".yml");
            if(!f.exists()){
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            FileConfiguration yml = YamlConfiguration.loadConfiguration(f);
            yml.set("Name", t.getMod());
            yml.set("Tag",t.getTag());
            yml.set("Leader",t.getLeader());
            yml.set("Mod",t.getMod());
            yml.set("Members",t.getMembers());
            saved++;
        }
        Log.INFO.print("&2Zapisano: &6&l" + saved + "&r&2 Teamow");
    }
    

}
