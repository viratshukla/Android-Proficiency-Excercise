package com.example.virat.shukla.androidproficiency;

public class Model {
    private String title;
    private InnerClass[] rows;

    public Model(String title, InnerClass[] rows) {
        this.title = title;
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InnerClass[] getRows() {
        return rows;
    }

    public void setRows(InnerClass[] rows) {
        this.rows = rows;
    }

}
