package io.github.erde.core.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * JarClassLoader.
 *
 * @author modified by parapata
 */
public class JarClassLoader extends URLClassLoader {

    public JarClassLoader(URL url) {
        super(new URL[] { url });
    }

    public JarClassLoader(URL[] urls) {
        super(urls);
    }

    public void getJDBCDriverClass(List<Class<?>> list, Class<?> cls, Class<?> org) {
        for (Class<?> item : cls.getInterfaces()) {
            item.getInterfaces();
            if (item.equals(Driver.class)) {
                list.add(org);
            }
        }
        Class<?> clazz = cls.getSuperclass();
        if (clazz != null) {
            getJDBCDriverClass(list, clazz, org);
        }
    }

    public List<Class<?>> getJDBCDriverClass(String jarName) throws IOException, ClassNotFoundException {
        if (StringUtils.isEmpty(jarName)) {
            return Collections.emptyList();
        }
        try (JarFile jarFile = new JarFile(jarName)) {
            Enumeration<JarEntry> jarEntry = jarFile.entries();
            ArrayList<Class<?>> list = new ArrayList<>();
            while (jarEntry.hasMoreElements()) {
                JarEntry entry = jarEntry.nextElement();
                String name = entry.getName();
                if (name.lastIndexOf(".class") != -1) {
                    String className = name.replaceFirst(".class", "").replaceAll("/", ".");
                    try {
                        Class<?> clazz = loadClass(className, true);
                        getJDBCDriverClass(list, clazz, clazz);
                    } catch (NoClassDefFoundError e) {
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
            return list;
        }
    }
}
