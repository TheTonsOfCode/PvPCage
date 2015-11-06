package com.noname.pvpcage.Test;

import com.noname.pvpcage.PvPCage;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] strings) {
        if (sender instanceof Player) {
            ItemManager.createInventory((Player) sender);
            Player p = (Player) sender;
            TextEffect text = new TextEffect(PvPCage.getInstance().getEffectManager());
            text.setLocation(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 3, p.getLocation().getZ()));
            text.text = "Wlasciciel";
            text.particle = ParticleEffect.CRIT;
            text.period = 10;
            text.start();
        }
        return false;
    }

}
