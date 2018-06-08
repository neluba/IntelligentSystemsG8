package de.competition.thesis.loaderwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvWriter {
    PrintWriter pw;
    private String path;
    private String fileName;

    public CsvWriter(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public void writeStringToCSV(String content){
        pw = null;
        createFile(path, fileName);
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }

    private void createFile(String path, String fileName) {
        try {
            pw = new PrintWriter(new File(path+fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
