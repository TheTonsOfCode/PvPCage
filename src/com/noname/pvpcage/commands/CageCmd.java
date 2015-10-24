package com.noname.pvpcage.commands;

import com.noname.pvpcage.builder.CageBuilder;
import com.noname.pvpcage.builder.cage.WalledCage;
import com.noname.pvpcage.objects.Command;
import com.noname.pvpcage.objects.SubCommand;

public class CageCmd extends Command {

    public CageCmd() {
        super("cage", "komenda glowna", "");
        setMins(1, 1, "<subcommand>");
        addSubCommand(new SubCommand("build", "buduje klatke", "b") {
                @Override
                protected void execute() {
                    new WalledCage().onCreateBattle(player.getLocation());
                    send("&aStworzono klatke!");
                }  
        });
        addSubCommand(new SubCommand("world", "tworzy cage world'a", "w") {
                @Override
                protected void execute() {
                    if(CageBuilder.getCageWorld() == null) {
                        CageBuilder.createCageWorld();
                        send("&6Stworzono &7Cage World!");
                        return;
                    }
                    CageBuilder.teleportToCageWorld(player);
                    send("&aPrzteleportowano do Cage World'u!");
                }  
        });
    }

    @Override
    protected void execute() {
        runSubCommand(this, true, mainArg);
    }
}
