package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HistoryOfMessages historyOfMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        historyOfMessages = new HistoryOfMessages();
        doHttpRequest();

        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){

        EditText edit =  (EditText) findViewById(R.id.editTextTextMultiLine);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        TextView oneMessageBlock = new TextView(this);
        oneMessageBlock.setGravity(Gravity.BOTTOM);
        //oneMessageBlock.setTextAlignment(Tex);
        oneMessageBlock.setText(edit.getText().toString());

        //oneMessageBlock.setLayoutParams(params);
        linearLayout.addView(oneMessageBlock,0);

        this.historyOfMessages.addOneSendedMessage
                (new OneSendedMessage(edit.getText().toString(), true, new Date()));

        edit.clearComposingText();
        //System.out.println(edit.getText().toString());

    }

    public void doHttpRequest(){
        //request and get information
        //fill this.historymessages
    }

    public void addMessagesToScreen(){
        //
    }
}