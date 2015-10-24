package com.noname.pvpcage.objects;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command extends SubCommand {

    public Command(String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    public void setPermissionPrefix(String permPrefix) {
        if (permPrefix == null) {
            return;
        }

        this.setPermission(permPrefix + "." + getName());
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] args) {
        if (!hasPerms(sender)) {
            return false;
        }

        if (!(sender instanceof Player)) {
            send(sender, true, "&cYou must be a player!");
            return false;
        }

        setValues((Player) sender, args);

        if (len == 1) {
            if (checkHelp(this, mainArg)) {
                return false;
            }
        }

        if ((minA != null && len < minA) || (maxA != null && len > maxA)) {
            send(player, false, this.usageMessage);
            return false;
        }

        execute();
        setValues(null, null);
        return true;
    }

    private Integer minA, maxA;

    protected void setMins(Integer minA, Integer maxA, String correct) {
        this.minA = minA;
        this.maxA = maxA;
        this.usageMessage = "&c/" + getName() + " " + (correct != null ? correct : "");
    }

    public Integer getMinA() {
        return minA;
    }

    public Integer getMaxA() {
        return maxA;
    }
    
    protected boolean hasPerms() {
        return hasPerms(player, null);
    }
    
    protected boolean hasPerms(String extension) {
        return hasPerms(player, extension);
    }
    
    protected boolean hasPerms(CommandSender sender) {
        return hasPerms(sender, null);
    }
    
    protected boolean hasPerms(CommandSender sender, String extension) {
        String perm = this.getPermission();
        perm += extension == null ? "" : extension.length() == 0 ? "" : "." + extension;
        if (perm.length() > 1 && !sender.hasPermission(perm)) {
            send(sender, true, "&cYou dont have permission! &7(&f" + perm + "&7)");
            return false;
        }
        return true;
    }
}
