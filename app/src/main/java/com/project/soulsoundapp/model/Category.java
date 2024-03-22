package com.project.soulsoundapp.model;

public class Category {
    private int image;
    private String name;
    private int bgColor;

    public Category(int image, String name, int bgColor) {
        this.image = image;
        this.name = name;
        this.bgColor = bgColor;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
