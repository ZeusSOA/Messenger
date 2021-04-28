package bsuir.sidorovich.pigeon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.dialog.DialogViewAdapter;
import bsuir.sidorovich.pigeon.activities.dialog.HistoryOfMessages;
import bsuir.sidorovich.pigeon.model.Message;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatType;
import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import bsuir.sidorovich.pigeon.model.server_access.server_api.ChatServiceApi;
import bsuir.sidorovich.pigeon.model.server_access.server_api.MessageServiceApi;
import bsuir.sidorovich.pigeon.model.server_access.server_api.UserServiceApi;

// activity с возможностью ввода и отображения сообщений
// реализация - Антон Дурко

// в текущем состоянии обеспечивается связь данной ChatActivity со списком чатов из ChatsFragment
// при входе в ChatActivity     - передача chatId, по которому в будущем будет производится запрос на сервер (при необходимости возможна передача целого класса)
// при выходе из ChatActivity   - нажатие кнопки -> вызов обработчика -> возврат на ChatsFragment, который внутри MainActivity

public class ChatActivity extends AppCompatActivity {

    public HistoryOfMessages historyOfMessages;
    private RecyclerView recyclerViewMessages;
    private DialogViewAdapter adapter;

    public void setTitle(String title){
        ((TextView)findViewById(R.id.textView)).setText(title);
    }
    public void fillHistoryOfMessages(ArrayList<MessageEntity> list) throws CloneNotSupportedException {

        Message message;

        for (MessageEntity messageEntity : list) {
            message = new Message(messageEntity.text, messageEntity.user.id == UserServiceApi.userId ? 1 : 2, messageEntity.user.username, new Date(), messageEntity.id);
            System.out.println(messageEntity.user.username);
            this.historyOfMessages.addOneSendedMessage(message);
        }

        sortMessagesById();

        this.adapter.setParams(this, this.historyOfMessages.getHistoryOfMessagesList());

        this.recyclerViewMessages = findViewById(R.id.recyclerView);
        this.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerViewMessages.setAdapter(adapter);
        updateActivity(historyOfMessages.getHistoryOfMessagesList());
    }

    public void sortMessagesById() throws CloneNotSupportedException {
        for (int i = 0; i < historyOfMessages.getHistoryOfMessagesList().size(); i++) {
            for (int j = i+1; j < historyOfMessages.getHistoryOfMessagesList().size(); j++) {
                if (historyOfMessages.getHistoryOfMessagesList().get(j).id < historyOfMessages.getHistoryOfMessagesList().get(i).id) {
                    Message temp;
                    temp = historyOfMessages.getHistoryOfMessagesList().get(i).clone();
                    historyOfMessages.getHistoryOfMessagesList().get(i).set(historyOfMessages.getHistoryOfMessagesList().get(j).clone());
                    historyOfMessages.getHistoryOfMessagesList().get(j).set(temp);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        UserServiceApi.userId = 1L;

        this.adapter = new DialogViewAdapter(this);
        this.historyOfMessages = new HistoryOfMessages();

        ImageButton sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOneMessage(v);
            }
        });

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.recyclerViewMessages = findViewById(R.id.recyclerView);

        MessageServiceApi.getMessagesFromChatById(1L, this);
    }

    public MessageEntity fillEntity() {

        MessageEntity messageEntity = new MessageEntity();

        messageEntity.text = ((EditText) findViewById(R.id.example_edit_text)).getText().toString();
        messageEntity.user = new UserEntity();
        messageEntity.chat = new ChatEntity();
        messageEntity.chat.id = 1L;
        messageEntity.chat.chatName = "single";
        messageEntity.chat.chatType = ChatType.SINGLE;
        messageEntity.user.id = UserServiceApi.userId;
        messageEntity.id = historyOfMessages.getHistoryOfMessagesList().size() + 1;
        //messageEntity.sendTime=Calendar.getInstance().getTime();
        //System.out.println("user"+ messageEntity.user.username);

        return messageEntity;
    }

    public void sendOneMessage(View View) {

        EditText edit = findViewById(R.id.example_edit_text);

        if (edit.getText().toString().length() > 0) {

            MessageEntity messageEntity = fillEntity();

            MessageServiceApi.sendMessage(messageEntity);

            this.historyOfMessages.addOneSendedMessage(new Message(edit.getText().toString(), 1, messageEntity.user.username, new Date(), historyOfMessages.getHistoryOfMessagesList().size() + 1));
            edit.getText().clear();

            this.adapter.setParams(this, this.historyOfMessages.getHistoryOfMessagesList());

            this.recyclerViewMessages = findViewById(R.id.recyclerView);
            this.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerViewMessages.setAdapter(adapter);
            updateActivity(historyOfMessages.getHistoryOfMessagesList());
        }
    }

    public void updateActivity(ArrayList<Message> messages) {
        this.adapter.setParams(this, messages);
        this.recyclerViewMessages = findViewById(R.id.recyclerView);
        this.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerViewMessages.scrollToPosition(historyOfMessages.getHistoryOfMessagesList().size() - 1);
        this.recyclerViewMessages.setAdapter(adapter);
    }
}