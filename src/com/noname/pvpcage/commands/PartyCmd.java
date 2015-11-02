package com.noname.pvpcage.commands;

import com.noname.pvpcage.managers.TeamManager;
import com.noname.pvpcage.managers.UserManager;
import com.noname.pvpcage.objects.Command;
import com.noname.pvpcage.objects.SubCommand;
import com.noname.pvpcage.objects.Team;
import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.utilities.Configuration;

public class PartyCmd extends Command {

    public PartyCmd() {
        super("party", "Komenda glowna do party", "");
        setMins(1, 4, "<subcommand>");
        addSubCommand(new SubCommand("create", "tworzy party", "c") {
            @Override
            protected void execute() {
                User user = UserManager.getUser(player.getUniqueId());
                if (len != 2) {// /party create {tag} {name}
                    send(usageMessage);
                    return;
                }
                if (user.getTeam() != null) {
                    send("&6Posiadasz juz team o tagu: &7[&4" + user.getTeam().getTag() + "&7]");
                    return;
                }
                if (args[0].length() > Configuration.PARTY_TAG_LENTH) {
                    send("&4Maksymalna dlugosc tagu to: &2" + Configuration.PARTY_TAG_LENTH);
                    return;
                }
                if (args[1].length() > Configuration.PARTY_NAME_LENTH) {
                    send("&4Maksymalna dlugosc tagu to: &2" + Configuration.PARTY_NAME_LENTH);
                    return;
                }
                if (TeamManager.getTeam(args[0]) != null) {
                    send("&6Team o tagu: &4" + args[0] + " &6Juz jest zajety!");
                    return;
                }
                Team team = new Team();
                team.setLeader(user);
                user.setTeam(team);
                team.setTag(args[0]);
                team.setName(args[1]);
                
                send("&6Zalozono Party o nazwie &2" + args[1] + " &6oraz tagu: &7[&2" + args[0] + "&7]");
                //team.save()''
            }
        });
    }

    @Override
    protected void execute() {
        runSubCommand(this, true, mainArg);
    }
}
