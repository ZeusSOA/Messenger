package bsuir.sidorovich.pigeon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    private final int COUNT_SYMBOLS_IN_ROW = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.adapter = new DialogViewAdapter(this);
        this.historyOfMessages = new HistoryOfMessages();

        Button sendButton = findViewById(R.id.button5);

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendOneMessage(v);
            }
        });
        setChangedEditText((RecyclerView) findViewById(R.id.recyclerView));

        // in
       /* Bundle arguments = getIntent().getExtras();
        String chatId = arguments.getString("id");
        TextView chatIdView = findViewById(R.id.chat_id_text);
        chatIdView.setText("@" + chatId);*/

        // out
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.recyclerViewMessages = findViewById(R.id.recyclerView);
        this.recyclerViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-");

            }
        });

        //запрос на сервер для получения списка сообщений
    }

    public void setChangedEditText(final RecyclerView recyclerView) {
        @SuppressLint("WrongViewCast") final EditText editText = findViewById(R.id.example_edit_text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().length() % COUNT_SYMBOLS_IN_ROW >= COUNT_SYMBOLS_IN_ROW) {

                    String oldString = editText.getText().toString();
                    editText.getText().clear();

                    editText.setHeight(editText.getHeight() + 10);
                    editText.setText("\n" + oldString);
                    ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
                    params.height -= 10;
                    recyclerView.requestLayout();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public void sendOneMessage(View View) {

        EditText edit = findViewById(R.id.example_edit_text);

        if (edit.getText().toString().length() > 0) {

            this.historyOfMessages.addOneSendedMessage(new Message(edit.getText().toString(), 1, "Anton Durko", new Date()));
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
        this.recyclerViewMessages.setAdapter(adapter);
    }
}