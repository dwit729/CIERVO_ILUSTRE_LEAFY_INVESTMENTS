package com.example.ciervo_ilustre_leafy_investments;

public class DueDate {
    String name, amount, category, date, id;


    public  DueDate()
    {

    }

    public DueDate(String name, String amount, String category, String date, String id) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
