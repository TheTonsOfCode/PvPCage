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
                }  
        });
        addSubCommand(new SubCommand("world", "tworzy cage world'a", "w") {
                @Override
                protected void execute() {
                    CageBuilder.createCageWorld();
                    CageBuilder.teleportToCageWorld(player);
                }  
        });
    }

    @Override
    protected void execute() {
        runSubCommand(this, true, mainArg);
    }
}
