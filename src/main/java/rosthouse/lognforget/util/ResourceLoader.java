/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author Rosthouse <rosthouse@gmail.com>
 * @see http://www.uofr.net/~greg/java/get-resource-listing.html
 */
public class ResourceLoader {

    /**
     * List directory contents for a resource folder. Not recursive. This is
     * basically a brute-force implementation. Works for regular files and also
     * JARs.
     *
     * @author Greg Briggs, Refactored by Rosthouse
     * @param clazz Any java class that lives in the same place as the resources
     * you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            return listDirectoryEntries(dirURL);
        }

        if (dirURL == null) {
            dirURL = convertDirectoryUrlToJarUrl(clazz);
        }

        if (dirURL.getProtocol().equals("jar")) {
            return listJarEntries(dirURL, path);
        }

        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }

    private static URL convertDirectoryUrlToJarUrl(Class clazz) {
        URL dirURL;
        /*
        * In case of a jar file, we can't actually find a directory.
        * Have to assume the same jar as clazz.
        */
        String me = clazz.getName().replace(".", "/") + ".class";
        dirURL = clazz.getClassLoader().getResource(me);
        return dirURL;
    }

    private static String[] listDirectoryEntries(URL dirURL) throws URISyntaxException {
        /* A file path: easy enough */
        return new File(dirURL.toURI()).list();
    }

    private static String[] listJarEntries(URL dirURL, String path) throws IOException {
        Enumeration<JarEntry> entries = getJarEntriesEnumeration(dirURL); 
        Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
        while (entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if (name.startsWith(path)) {                 
                String entry = getResourcename(name, path);
                result.add(entry);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    private static String getResourcename(String name, String path) {
        //filter according to the path
        String entry = name.substring(path.length());
        int checkSubdir = entry.indexOf("/");
        if (checkSubdir >= 0) {
            // if it is a subdirectory, we just return the directory name
            entry = entry.substring(0, checkSubdir);
        }
        return entry;
    }

    private static Enumeration<JarEntry> getJarEntriesEnumeration(URL dirURL) throws IOException {
        /* A JAR path */
        String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
        JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
        Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
        return entries;
    }
}
