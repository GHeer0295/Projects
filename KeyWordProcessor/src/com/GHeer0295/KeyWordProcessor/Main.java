package com.GHeer0295.KeyWordProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main{
    public static void main(String[] args) throws IOException{
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".txt");
            }
        };

        File file = new File("data");
        File[] files = file.listFiles(filter);
        ArrayList<FileType> fileList = new ArrayList<>();

        assert files != null;
        for(File f : files){
            FileType fileType = new FileType(f.getName());
            Processor processor = new Processor();
            int numberOfKeyWords = processor.findKeyWords(f.getAbsolutePath());
            fileType.setNumOfKeyWords(numberOfKeyWords);
            fileType.setPriority();
            fileList.add(fileType);
        }
        showFilesAccordingToPriority(fileList);
    }

    public static void showFilesAccordingToPriority(ArrayList<FileType> files){
        Collections.sort(files);

        for(FileType f : files){
            System.out.println(f.getFileName());
        }
    }
}
