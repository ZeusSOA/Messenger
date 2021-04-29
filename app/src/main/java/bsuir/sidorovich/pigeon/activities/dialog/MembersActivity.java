package bsuir.sidorovich.pigeon.activities.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.chats.UserListAdapter;
import bsuir.sidorovich.pigeon.model.User;

public class MembersActivity extends AppCompatActivity {
    private ArrayList<User> foundUsers = new ArrayList<>();
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        foundUsers.add(new User( "8", "Steven Waters",""));
        foundUsers.add(new User( "38", "Simon Morris",""));
        foundUsers.add(new User( "1", "Joseph Hensley",""));

        RecyclerView usersView;
        usersView = findViewById(R.id.member_list);
        usersView.setHasFixedSize(true);
        usersView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new UserListAdapter(foundUsers, usersView, null);
        usersView.setAdapter(adapter);
    }
}