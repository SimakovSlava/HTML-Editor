package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {
        boolean fileDir = file.isDirectory();
        String fileName = file.getName();
        return (fileName.toLowerCase().endsWith(".html") || fileName.toLowerCase().endsWith(".htm") || fileDir);
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
