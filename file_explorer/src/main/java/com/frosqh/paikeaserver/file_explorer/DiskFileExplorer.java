package com.frosqh.paikeaserver.file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiskFileExplorer {

    private final String initialPath;
    private final boolean recursivePath;
    private static final List<String> authExt = Arrays.asList("mp3","wav");

    public DiskFileExplorer(String path, boolean subFolder){
        initialPath = path;
        recursivePath = subFolder;
    }

    public List<String> list(){
        return listDirectory(initialPath);
    }

    private List<String> listDirectory(String dir){
        List<String> s = new ArrayList<>();
        String ext;
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files==null) return new ArrayList<>();
        for (File f : files){
            ext = "";
            String fileName = f.getName();
            int extIndex = fileName.lastIndexOf(".");
            if (extIndex>0)
                ext = fileName.substring(extIndex+1);
            if (authExt.contains(ext))
                s.add(fileName);
            if (f.isDirectory() && recursivePath)
                s.addAll(listDirectory(f.getAbsolutePath()));
        }
        return s;
    }
}
