package com.noname.pvpcage.commands;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.builder.CageBuilder;
import com.noname.pvpcage.builder.WESchematic;
import com.noname.pvpcage.listeners.CageItemListener;
import com.noname.pvpcage.objects.Command;
import com.noname.pvpcage.objects.SubCommand;

public class CageCmd extends Command {

    public CageCmd() {
        super("cage", "komenda glowna", "");
        setMins(1, 2, "<subcommand>");
        
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
        addSubCommand(new SubCommand("cc", "buduje 12 klatek", "") {
                @Override
                protected void execute() {
                    for (int j = 0; j < 32 ;j++) {
                        CageBuilder.buildRandomSchematicCage();
                    }
                    send("&aStworzono 12 klatek!");
                }  
        });
        
        addSubCommand(new SubCommand("schem", "tworzy schematic klatki", "sch") {
                @Override
                protected void execute() {
                    if(len != 2) {
                        correct();
                        return;
                    }
                    
                    if(CageBuilder.schemExist(args[1])) {
                        send("&eIstnieje juz taki schemat!");
                        return;
                    }
                    
                    if(!CageItemListener.areSetted()) {
                        send("&ePozycje nie sa ustawione!");
                        return;
                    }
                    
                    new WESchematic(player).saveSchematic(args[1], CageItemListener.LEFT, CageItemListener.RIGHT);
                    CageBuilder.refreshSchematicsNames();
                    send("&aUtworzono schemat klatki o nazwie &f" + args[1] + "&a! &ePamietaj o skonfigurowaniu go!");
                }  
        });
        
        addSubCommand(new SubCommand("borderTest", "buduje testowy scheatic klatki", "bt") {
                @Override
                protected void execute() {
                    CageBuilder.bb(player.getLocation());
                    send("&aStworzono klatke!");
                }  
        });
    }

    @Override
    protected void execute() {
        runSubCommand(this, true, mainArg);
    }
}
