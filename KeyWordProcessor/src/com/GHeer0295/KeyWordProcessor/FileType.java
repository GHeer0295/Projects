package com.GHeer0295.KeyWordProcessor;

public class FileType implements Comparable<FileType>{
    private String fileName;
    private int numOfKeyWords;
    private Priority priority;

    public FileType(String fileName){
        this.fileName = fileName;
    }

    public void setNumOfKeyWords(int numOfKeyWords){
        this.numOfKeyWords = numOfKeyWords;
    }

    public String getFileName(){return fileName;}

    public void setPriority() {
        if(this.numOfKeyWords == 0){
            this.priority = Priority.LOW;
        }
        if(this.numOfKeyWords >= 2 && this.numOfKeyWords <= 4){
            this.priority = Priority.MEDIUM;
        }
        if(this.numOfKeyWords > 4 && this.numOfKeyWords <= 6){
            this.priority = Priority.HIGH;
        }
    }
    public Priority getPriority(){
        return this.priority;
    }
    public int getNumOfKeyWords(){
        return this.numOfKeyWords;
    }
    @Override
    public int compareTo(FileType file){
        return file.numOfKeyWords - this.numOfKeyWords;
    }

}
