package com.tutorials.hp.firebaserecyclerview.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Oclemmy on 4/11/2016 for ProgrammingWizards Channel.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String name;
    private String description;

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
