package com.gmail.desk1123;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 *
 * @author dekros987
 */
public class Test implements CommandExecutor {

    private static int t;

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (!(cs instanceof Player)) {
            return true;
        }
        final Player p = (Player) cs;

        Utils.createParticleText("", p.getLocation());
        t = Bukkit.getScheduler().scheduleAsyncDelayedTask(PvPCage.getInstance(), new Runnable() {
            public void run() {
                try {

                    Utils.createParticleText("3", p.getLocation());
                    Thread.sleep(1000L);
                    Utils.createParticleText("2", p.getLocation());
                    Thread.sleep(1000L);
                    Utils.createParticleText("1", p.getLocation());
                    Thread.sleep(1000L);
                    Utils.createParticleText("START", p.getLocation());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }, 10L);

        Cage c = new Cage();
        c.build((Player) cs, Integer.valueOf(args[0]),Integer.valueOf(args[1]));
        return true;
    }

}
