package de.competition.thesis.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class Product  implements Serializable {


    private String name;
    private List<Variant> variants;

    @Expose(serialize = false)
    private Variant chosenVariant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public Variant getChosenVariant() {
        return chosenVariant;
    }

    public void setChosenVariant(Variant chosenVariant) {
        this.chosenVariant = chosenVariant;
    }

    public Variant getVariantByName(String name){
        for(Variant variant: variants){
            if(variant.getName().equals(name)){
                return variant;
            }
        }
        return null;
    }
}
