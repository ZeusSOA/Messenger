package bsuir.sidorovich.pigeon.activities.dialog;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.Message;

public class HistoryOfMessages {

    private ArrayList<Message> historyOfMessagesList;

    public HistoryOfMessages(){
        this.historyOfMessagesList = new ArrayList<>();
    }

    public void addOneSendedMessage(Message message){
        this.historyOfMessagesList.add(message);
    }

    public ArrayList<Message> getHistoryOfMessagesList(){
        return this.historyOfMessagesList;
    }
}
