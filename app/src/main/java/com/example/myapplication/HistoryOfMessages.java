package com.example.myapplication;

import java.util.ArrayList;

public class HistoryOfMessages {

    private ArrayList<OneSendedMessage> historyOfMessagesList;

    public HistoryOfMessages(){
        this.historyOfMessagesList = new ArrayList<>();
    }

    public void addOneSendedMessage(OneSendedMessage message){
        this.historyOfMessagesList.add(message);
    }

    public ArrayList<OneSendedMessage> getHistoryOfMessagesList(){
        return this.historyOfMessagesList;
    }
}
