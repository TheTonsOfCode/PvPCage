package com.noname.pvpcage.utilities.instance;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/*
    Super Instance Maker
    @author = Merbio
*/

public abstract class PackageInstancer {
    
    protected Plugin plugin;
    protected String typeName;
    protected int ma, mb;
    
    public PackageInstancer(Plugin plugin, String typeName) {
        this.plugin = plugin;
        this.typeName = typeName;
    }
    
    protected abstract void onPositiveCheck(Class c);

    public void instanceAllAt(String pkg) {
        ma = mb = 0;
        
        final long start = System.nanoTime();
        int classesNumber = pkg.split("\\.").length;

        ArrayList<String> classes = new ArrayList();

        String path = pkg.replaceAll("\\.", "/");

        String jarName = plugin.getDataFolder().getAbsolutePath() + ".jar";

        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(path)) && (jarEntry.getName().endsWith(".class"))) {
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String name : classes) {
            try {
                Class c = PackageInstancer.class.getClassLoader().loadClass(name.substring(0, name.length() - 6));
                ma++;
                onPositiveCheck(c);
            } catch (Exception ex) {
                Logger.getLogger(ex.getMessage());
            }
        }
        final long end = System.nanoTime();
        System.out.println("{Merbio PackageInstancer[" + typeName + "]}: making insances took " + ((end - start) / 1000000) + "ms, founded matched clases: " + mb + "(from " + ma + " files in choosed package).");
    }
}
