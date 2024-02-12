package com.example.ciervo_ilustre_leafy_investments;

public class Clients {
    String fullName, userName, age, birthDay, emailAddress, password;
    private String documentId;

    public Clients() {
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Clients(String fullName, String userName, String age, String birthDay, String emailAddress, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.age = age;
        this.birthDay = birthDay;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
