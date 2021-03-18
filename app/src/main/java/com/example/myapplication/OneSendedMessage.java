package com.example.myapplication;

import java.util.Date;

public class OneSendedMessage {

    private String message;
    private boolean isMeSender;
    private Date date;

    public OneSendedMessage(){

    }

    public OneSendedMessage(String message, boolean isMeSender, Date date){
        this.date = date;
        System.out.println(date);
        this.isMeSender=isMeSender;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public Date getDate() {
        return date;
    }

    public boolean isMeSender() {
        return isMeSender;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMeSender(boolean meSender) {
        this.isMeSender = meSender;
    }



}
