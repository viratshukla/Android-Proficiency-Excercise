package com.example.virat.shukla.androidproficiency.model;

public class Model {
    private String title;
    private InnerRowsClass[] rows;

    public Model(String title, InnerRowsClass[] rows) {
        this.title = title;
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InnerRowsClass[] getRows() {
        return rows;
    }

    public void setRows(InnerRowsClass[] rows) {
        this.rows = rows;
    }

}
