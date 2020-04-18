package com.GHeer0295.KeyWordProcessor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Processor{
    private ArrayList<String> keyWords = new ArrayList<>();
    private HashMap<String, Integer> map = new HashMap<>();

    public Processor() {
        addKeyWords();
    }

    private void addKeyWords() {
        keyWords.add("TEST1");
        keyWords.add("TEST2");
        keyWords.add("TEST3");
        keyWords.add("TEST4");
    }
    public void printAll(){
        for(String s : map.keySet()){
            System.out.println(map);
        }
    }


    public int findKeyWords(String path) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");

        for(String s : keyWords){
            int i;
            while((i = file.read()) != -1){

                String line = file.readLine().toLowerCase();
                if(line.contains(s.toLowerCase())){

                    if(map.containsKey(s.toLowerCase())){
                        map.put(s.toLowerCase(), map.get(s) + 1);
                    }
                    else{
                        map.put(s, 0);
                    }
                }
            }
            //back to the top of file
            file.seek(0);
        }
        return map.size();
    }
}

