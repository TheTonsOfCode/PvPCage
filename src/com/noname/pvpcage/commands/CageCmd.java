package com.noname.pvpcage.commands;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.builder.CageBuilder;
import com.noname.pvpcage.builder.WESchematic;
import com.noname.pvpcage.listeners.CageItemListener;
import com.noname.pvpcage.objects.Command;
import com.noname.pvpcage.objects.SubCommand;
import java.io.File;

public class CageCmd extends Command {

    public CageCmd() {
        super("cage", "komenda glowna", "");
        setMins(1, 2, "<subcommand>");
        addSubCommand(new SubCommand("build", "buduje klatke", "b") {
                @Override
                protected void execute() {
//                    new WalledCage().onCreateBattle(player.getLocation());
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
        addSubCommand(new SubCommand("delWorld", "usuwa cage world'a", "dw") {
                @Override
                protected void execute() {
                    if(CageBuilder.deleteCageWorld()) {
                        CageBuilder.createCageWorld();
                        send("&eUsunieto &7Cage World!");
                        return;
                    }
                    send("&aNie odnaleziono Cage World'u!");
                }  
        });
        addSubCommand(new SubCommand("cc", "buduje 32 klatki", "") {
                @Override
                protected void execute() {
                    for (int j = 0; j < 32 ;j++) {
//                        CageBuilder.buildCage(new WalledCage());
                    }
                    send("&aStworzono 32 klatki!");
                }  
        });
        
        addSubCommand(new SubCommand("schem", "zapisuje teren", "sch") {
                @Override
                protected void execute() {
                    if(!CageItemListener.areSetted()) {
                        send("&ePozycje nie sa ustawione!");
                        return;
                    }
                    
                    new WESchematic(player).saveSchematic("backup1", CageItemListener.LEFT, CageItemListener.RIGHT);
                }  
        });
        
        addSubCommand(new SubCommand("z", "zapisuje", "") {
                @Override
                protected void execute() {
                    new WESchematic(player).loadSchematic("backup1", player.getLocation());
                }
        });
    }

    @Override
    protected void execute() {
        runSubCommand(this, true, mainArg);
    }
}
