package bsuir.sidorovich.pigeon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.dialog.DialogViewAdapter;
import bsuir.sidorovich.pigeon.activities.dialog.HistoryOfMessages;
import bsuir.sidorovich.pigeon.model.Message;

// activity с возможностью ввода и отображения сообщений
// реализация - Антон Дурко

// в текущем состоянии обеспечивается связь данной ChatActivity со списком чатов из ChatsFragment
// при входе в ChatActivity     - передача chatId, по которому в будущем будет производится запрос на сервер (при необходимости возможна передача целого класса)
// при выходе из ChatActivity   - нажатие кнопки -> вызов обработчика -> возврат на ChatsFragment, который внутри MainActivity

public class ChatActivity extends AppCompatActivity {

    private HistoryOfMessages historyOfMessages;
    private RecyclerView recyclerViewMessages;
    private DialogViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.adapter = new DialogViewAdapter(this);
        this.historyOfMessages = new HistoryOfMessages();

        Button b = findViewById(R.id.button5);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("da");
                System.out.println("da");
                System.out.println("da");
                sendOneMessage(v);
            }
        });

        setTitle("Dialog");

        // in
       /* Bundle arguments = getIntent().getExtras();
        String chatId = arguments.getString("id");
        TextView chatIdView = findViewById(R.id.chat_id);
        chatIdView.setText("@" + chatId);*/

        // out
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //запрос на сервер для получения списка сообщений
    }


    public void sendOneMessage(View View){

        System.out.println("qqqqq");
        System.out.println("qqqqq");
        System.out.println("qqqqq");
        EditText edit =  (EditText) findViewById(R.id.example_edit_text);
        historyOfMessages.addOneSendedMessage(new Message(edit.getText().toString(),2,new Date()));
        edit.clearComposingText();
        this.adapter.setParams(this,historyOfMessages.getHistoryOfMessagesList());
        recyclerViewMessages = findViewById(R.id.recyclerView);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);
        updateActivity(historyOfMessages.getHistoryOfMessagesList());
    }
    public void updateActivity(ArrayList<Message> messages){
        this.adapter.setParams(this,messages);
        recyclerViewMessages = findViewById(R.id.recyclerView);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);
    }
}