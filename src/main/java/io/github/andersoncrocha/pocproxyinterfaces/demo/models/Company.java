package io.github.andersoncrocha.pocproxyinterfaces.demo.models;

public class Company {

    private String name;
    private String document;

    public Company(String name, String document) {
        this.name = name;
        this.document = document;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }

}
