package com.noname.pvpcage.commands;

import com.noname.pvpcage.builder.CageBuilder;
import com.noname.pvpcage.builder.cage.WalledCage;
import com.noname.pvpcage.objects.Command;
import com.noname.pvpcage.objects.SubCommand;

public class CageCmd extends Command {

    public CageCmd() {
        super("cage", "komenda glowna", "");
        setMins(1, 1, "<subcommand>");
        addSubCommand(new SubBuild());
        addSubCommand(new SubWorld());
    }

    @Override
    protected void execute() {
        runSubCommand(this, true, mainArg);
    }
}

class SubBuild extends SubCommand {

    public SubBuild() {
        super("build", "buduje klatke", "b");
    }

    @Override
    protected void execute() {
        new WalledCage().onCreateBattle(player.getLocation());
    }    
}

class SubWorld extends SubCommand {

    public SubWorld() {
        super("world", "tworzy cage world'a", "w");
    }

    @Override
    protected void execute() {
        CageBuilder.createCageWorld();
        CageBuilder.teleportToCageWorld(player);
    }    
}
