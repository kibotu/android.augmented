package net.kibotu.android.albert.stl;

import android.util.Log;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 11.02.13
 * Time: 07:17
 * To change this template use File | Settings | File Templates.
 */
public class STLFileLoader {

    public final String TAG = STLFileLoader.class.getSimpleName();

    public List<STLFileObject> load(final String path) {
        File file = new File(path);

        if (!file.exists()) {
            Log.d(TAG, "load - file " + file.getAbsolutePath() + " not existing");
            return new LinkedList<STLFileObject>();
        }

        if (file.isDirectory()) {
            return loadFilesFromDirectory(file);
        }

        List<STLFileObject> stlFileObjects = new LinkedList<STLFileObject>();
        stlFileObjects.add(loadSingleFile(file));
        return stlFileObjects;
    }

    private List<STLFileObject> loadFilesFromDirectory(final File stlDirectory) {
        List<STLFileObject> stlFileObjects = new LinkedList<STLFileObject>();

        if(!stlDirectory.exists()) {
            Log.d(TAG, "loadFilesFromDirectory - " + stlDirectory.getName() + " not existing");
            return stlFileObjects;
        }

        if(!stlDirectory.isDirectory()) {
            Log.d(TAG, "loadFilesFromDirectory - " + stlDirectory.getName() + " file is no directory");
            return stlFileObjects;
        }

        long before = System.currentTimeMillis();

        for(File currentFile : stlDirectory.listFiles(new STLFileFilter())) {
            STLFileObject currentSTLFileObject = loadSingleFile(currentFile);
            if(currentSTLFileObject.isValid()){
                stlFileObjects.add(currentSTLFileObject);
            }
        }

        long after = System.currentTimeMillis();
        double runningTime = (after - before) / 1000;

        Log.d(TAG, "loadFilesFromDirectory - Files loaded in " + runningTime + " s.");

        return stlFileObjects;
    }

    private STLFileObject loadSingleFile(final File stlFile) {

        return new STLFileParser().parseFast(stlFile);

    }

}
