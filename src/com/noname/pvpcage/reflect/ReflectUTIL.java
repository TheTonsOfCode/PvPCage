package com.noname.pvpcage.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ReflectUTIL {

    public static void sendSoundPacket(Player p, String sound, Location loc, float volume, float pitch) {
        sendPacket(p, newInstance(ReflConstants.packet_PacketPlayOutNamedSoundEffect, new Object[]{sound, Double.valueOf(loc.getX()), Double.valueOf(loc.getY()), Double.valueOf(loc.getZ()), Float.valueOf(volume), Float.valueOf(pitch)}));
    }

    public static void sendPacket(Player p, Object packet) {
        if (packet == null) {
            return;
        }
        try {
            Object nmsPlayer = getMethodByName(p.getClass(), "getHandle").invoke(p, new Object[0]);
            Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            getMethodByName(con.getClass(), "sendPacket").invoke(con, new Object[]{packet});
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void recivePacket(Player p, Object packet) {
        if (packet == null) {
            return;
        }
        try {
            Object nmsPlayer = getMethodByName(p.getClass(), "getHandle").invoke(p, new Object[0]);
            Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            getMethod(con.getClass(), "a", new Class[]{packet.getClass()}).invoke(con, new Object[]{packet});
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static <T> T getField(Object cls, String name, Class<T> def) {
        Object o = null;
        try {
            Field f = cls.getClass().getDeclaredField(name);
            f.setAccessible(true);
            o = f.get(cls);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o == null ? null : def.cast(o);
    }

    private static final String NMSversion = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

    public static Class<?> getMCClass(String name) {
        try {
            String className = "net.minecraft.server." + NMSversion + name;
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Class<?> getCraftClass(String name) {
        try {
            String className = "org.bukkit.craftbukkit." + NMSversion + name;
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setField(Object cls, String name, Object value) {
        try {
            Field f = cls.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(cls, value);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Field getField(Class<?> cl, String field) {
        for (Field f : cl.getFields()) {
            if (f.getName().equals(field)) {
                return f;
            }
        }
        return null;
    }

    public static Class<?> getFieldType(Class c, String name) {
        try {
            Field f = getField(c, name);
            if (f == null) {
                return null;
            }
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            return f.getType();
        } catch (IllegalArgumentException | SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Object getFieldValue(Object obj, String name) {
        try {
            Field f = getField(obj.getClass(), name);
            if (f == null) {
                return null;
            }
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            return f.get(obj);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Method getMethodByName(Class c, String name) {
        try {
            for (Method m : c.getDeclaredMethods()) {
                if (m.getName().equals(name)) {
                    m.setAccessible(true);
                    return m;
                }
            }
        } catch (IllegalArgumentException | SecurityException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Method getMethod(Class c, String name, Class... params) {
        try {
            Method m = c.getDeclaredMethod(name, params);
            m.setAccessible(true);
            return m;
        } catch (IllegalArgumentException | SecurityException | NoSuchMethodException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Object invoke(Method m, Object obj, Object... params) {
        try {
            return m.invoke(obj, params);
        } catch (IllegalArgumentException | SecurityException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Constructor getConstructor(Class c, Class... params) {
        try {
            return c.getConstructor(params);
        } catch (SecurityException | IllegalArgumentException | NoSuchMethodException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Object newInstance(Constructor c, Object... params) {
        try {
            return c.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException | IllegalArgumentException ex) {
            Logger.getLogger(ReflectUTIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
