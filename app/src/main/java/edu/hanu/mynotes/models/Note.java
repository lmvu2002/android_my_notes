package edu.hanu.mynotes.models;


import java.io.Serializable;

public class Note implements Serializable {
    private long id;
    private String name;

    public Note(String name) {
        this.name = name;
    }
    
    public Note(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: "+getName()+
                "\n Id: "+getId();
    }
}
