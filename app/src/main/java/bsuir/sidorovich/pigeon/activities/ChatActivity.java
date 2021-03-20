package bsuir.sidorovich.pigeon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import bsuir.sidorovich.pigeon.R;

// activity с возможностью ввода и отображения сообщений
// реализация - Антон Дурко

// в текущем состоянии обеспечивается связь данной ChatActivity со списком чатов из ChatsFragment
// при входе в ChatActivity     - передача chatId, по которому в будущем будет производится запрос на сервер (при необходимости возможна передача целого класса)
// при выходе из ChatActivity   - нажатие кнопки -> вызов обработчика -> возврат на ChatsFragment, который внутри MainActivity

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // in
        Bundle arguments = getIntent().getExtras();
        String chatId = arguments.getString("id");
        TextView chatIdView = findViewById(R.id.chat_id);
        chatIdView.setText("@" + chatId);

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
}