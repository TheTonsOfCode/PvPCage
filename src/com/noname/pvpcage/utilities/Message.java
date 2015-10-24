package com.noname.pvpcage.utilities;


import com.noname.pvpcage.PvPCage;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.CommandSender;

/**
 *
 * @author CTRL
 */
public enum Message {


    
    Message_prefix;

    private static final MessageManager msgManager = PvPCage.getMessageManager();
    private String path = null;
    private HashMap<String, String> vars = new HashMap<>();
    private boolean prefix = true;
    private boolean list = false;

    Message() {

    }

    Message(boolean prefix, boolean list) {
        this.prefix = prefix;
        this.list = list;

        if (list) {
            prefix = false;
        }
    }

    public boolean getPrefix() {
        return prefix;
    }

    public String getPath() {
        if (path == null) {
            path = name().replace("_", ".");
        }

        return path;
    }

    public void send(CommandSender sender) {
        if (list) {
            MessageManager.sendMessagesList(sender, getPath(), vars, getPrefix());
        } else {
            MessageManager.sendMessagesMsg(sender, getPath(), vars, getPrefix());
        }
    }

    public Message vars(HashMap<String, String> vars) {
        this.vars = vars;
        return this;
    }

    public Message list() {
        list = true;
        return this;
    }

    public Message prefix(boolean prefix) {
        this.prefix = prefix;
        return this;
    }

    public void broadcast() {
        if (list) {
            MessageManager.broadcastList(getPath(), vars);
        } else {
            MessageManager.broadcastMessage(getPath(), vars);
        }
    }

    public void broadcast(String permission) {
        MessageManager.broadcastMessageForPermitted(getPath(), permission);
    }

    public String get() {
        return MessageManager.replaceMap(MessageManager.getMessagesString(getPath()), vars); //TODO replace with Message
    }

    public List<String> getList() {
        return msgManager.getMessages().getStringList(getPath());
    }

}
