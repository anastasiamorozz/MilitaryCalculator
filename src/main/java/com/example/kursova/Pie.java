package com.example.kursova;

import java.util.List;

public class Pie {
    private String name;
    private String description;
    private List<Product> ingredients;
    private double weight;
    private MRE mre;
    private String createdBy;

    public Pie(String name, String description, List<Product> ingredients, String createdBy) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.createdBy = createdBy;
        calculateWeight();
        calculateMRE();
    }

    private void calculateWeight() {
        weight = ingredients.stream().mapToDouble(Product::getWeight).sum();
    }

    private void calculateMRE() {
        mre = new MRE();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Product> getIngredients() {
        return ingredients;
    }

    public double getWeight() {
        return weight;
    }

    public MRE getMre() {
        return mre;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
