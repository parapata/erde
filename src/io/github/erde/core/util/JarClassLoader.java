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
        Class<?>[] interfaces = cls.getInterfaces();
        for (Class<?> interface1 : interfaces) {
            interface1.getInterfaces();
            if (interface1.equals(Driver.class)) {
                list.add(org);
            }
        }
        Class<?> s = cls.getSuperclass();
        if (s != null) {
            getJDBCDriverClass(list, s, org);
        }
    }

    public List<Class<?>> getJDBCDriverClass(String jarName) throws IOException, ClassNotFoundException {
        if (jarName.equals("")) {
            return Collections.emptyList();
        }
        try (JarFile jarFile = new JarFile(jarName)) {
            Enumeration<JarEntry> jarEntry = jarFile.entries();
            ArrayList<Class<?>> list = new ArrayList<>();
            while (jarEntry.hasMoreElements()) {
                JarEntry entry = jarEntry.nextElement();
                String name = entry.getName();
                if (name.lastIndexOf(".class") != -1) {
                    String ccls = name.replaceFirst(".class", "").replaceAll("/", ".");
                    try {
                        Class<?> cls = loadClass(ccls, true);
                        getJDBCDriverClass(list, cls, cls);
                    } catch (NoClassDefFoundError e) {
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
            return list;
        }
    }
}
