package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.scanner.ScannerException;

/**
 *
 * @author CTRL
 */
public class MessageManager {

    private static FileConfiguration messages;
    public static String prefix;
    public static ChatColor prefixColor = ChatColor.WHITE;
    public static MessageManager instance;

    public static void loadMessages() {
        File file = new File(PvPCage.getInstance().getDataFolder(), "Message.yml");
        if (!file.exists()) {
            if (PvPCage.getInstance().getResource("Message.yml") != null) {
                PvPCage.copy(PvPCage.getInstance().getResource("Message.yml"), file);
            }
        }
        try {
            messages = YamlConfiguration.loadConfiguration(file);
        } catch (ScannerException e) {
            e.printStackTrace();
        }

        prefix = Utils.fixColor(messages.getString("Message.prefix"));
    }

    //set string from file
    public static String getMessagesString(String path) {
        String msg = getMessages().getString(path);
        if (msg == null) {
            return path;
        }
        return msg;
    }

    //get messages
    public static FileConfiguration getMessages() {
        return messages;
    }

    //send string with prefix to a player
    public static void sendPrefixMessage(Player p, String msg) {
        p.sendMessage(Utils.fixColor(prefix + msg));
    }

    public static void sendPrefixMessage(CommandSender sender, String msg) {
        sender.sendMessage(Utils.fixColor(prefix + msg));
    }

    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(Utils.fixColor(msg));
    }

    public static void sendMessagesList(CommandSender sender, String path, HashMap<String, String> vars, boolean prefix) {
        List<String> list = messages.getStringList(path);

        if (list != null) {
            for (String msg : list) {
                if (vars != null) {
                    msg = replaceMap(msg, vars);
                }
                if (prefix) {
                    sendPrefixMessage(sender, msg);
                } else {
                    sendMessage(sender, msg);
                }
            }
        }
    }

    public static void broadcastList(String path, HashMap<String, String> vars) {
        List<String> list = messages.getStringList(path);
        if (list != null) {
            for (String msg : list) {
                if (vars != null) {
                    msg = replaceMap(msg, vars);
                }
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    sendMessage(p, msg);
                }
            }
        }
    }

    public static void sendMessagesMsg(CommandSender sender, String path) {
        sendMessagesMsg(sender, path, false);
    }

    public static void sendMessagesMsg(CommandSender sender, Message message, HashMap<String, String> vars) {
        sendMessagesMsg(sender, message.getPath(), vars, message.getPrefix());
    }

    public static void sendMessagesMsg(CommandSender sender, String path, boolean prefix) {
        String msg = getMessagesString(path);
        if (prefix && sender instanceof Player) {
            sendPrefixMessage((Player) sender, msg);
        } else {
            sendMessage(sender, msg);
        }
    }

    public static void sendMessagesMsg(CommandSender sender, String path, HashMap<String, String> vars) {
        sendMessagesMsg(sender, path, vars, true);
    }

    public static void sendMessagesMsg(CommandSender sender, String path, HashMap<String, String> vars, boolean prefix) {
        String msg = getMessagesString(path);
        msg = replaceMap(msg, vars);
        //sendPrefixMessage(sender,msg);
        if (prefix && sender instanceof Player) {
            sendPrefixMessage((Player) sender, msg);
        } else {
            sendMessage(sender, msg);
        }
    }

    //broadcast string to all players
    public static void broadcast(String msg) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            sendPrefixMessage(p, msg);
        }
    }

    //broadcast message from file to all players with permission
    public static void broadcastMessageForPermitted(String path, String permission) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission(permission)) {
                sendMessagesMsg(p, path);
            }
        }
    }

    public static void broadcastMessage(Message message, HashMap<String, String> vars) {
        broadcastMessage(message.getPath(), vars);
    }

    public static void broadcastMessage(String path, HashMap<String, String> vars) {
        String msg = getMessagesString(path);
        msg = replaceMap(msg, vars);

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            sendPrefixMessage(p, msg);
        }
    }

    public static void sendUsageMessage(CommandSender sender, String path) {
        sender.sendMessage(Utils.fixColor(getMessagesString("chat.usage." + path)));
    }

    public static String replaceMap(String msg, HashMap<String, String> vars) {
        if (vars != null) {
            for (Map.Entry<String, String> entry : vars.entrySet()) {
                try {
                    msg = replace(msg, "{" + entry.getKey() + "}", entry.getValue());
                } catch (Throwable ex) {
                }
            }
        }

        return msg;
    }

    public static String replace(String text, String searchString, String replacement) {
        if ((text == null) || (text.isEmpty()) || (searchString.isEmpty()) || (replacement == null)) {
            return text;
        }

        int start = 0;
        int max = -1;
        int end = text.indexOf(searchString, start);

        if (end == -1) {
            return text;
        }

        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= (max > 64 ? 64 : max < 0 ? 16 : max);
        StringBuilder sb = new StringBuilder(text.length() + increase);

        while (end != -1) {
            sb.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            max--;

            if (max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        sb.append(text.substring(start));
        return sb.toString();
    }
}
