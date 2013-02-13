package net.kibotu.android.albert.stl;

import java.io.File;
import java.io.FileFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 11.02.13
 * Time: 09:43
 * To change this template use File | Settings | File Templates.
 */
public class STLFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        if(!file.exists()) {
            return false;
        }
        if(file.isDirectory()) {
            return false;
        }
        if(file.getName().toLowerCase().endsWith(".stl")) {
            return true;
        };
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
