package de.competition.thesis.loaderwriter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import de.competition.thesis.models.ProblemDefinition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class JsonLoader {
    private ProblemDefinition problemDefinition;
    private String path;
    private String data;

    public JsonLoader(String path, String data) {
        this.path = path;
        this.data = data;
        try {
            loadJSONData();
        } catch (FileNotFoundException e) {
            System.out.println("fail");
        }
    }


    private void loadJSONData() throws FileNotFoundException {
        final Type REVIEW_TYPE = new TypeToken<ProblemDefinition>() {  }.getType();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path+data));
        problemDefinition = gson.fromJson(reader, REVIEW_TYPE);
    }

    public ProblemDefinition getProblemDefinition() {
        return problemDefinition;
    }



}
