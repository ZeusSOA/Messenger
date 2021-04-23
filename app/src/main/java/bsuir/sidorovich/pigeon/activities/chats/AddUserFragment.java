package bsuir.sidorovich.pigeon.activities.chats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.model.server_access.ServerApi;
import bsuir.sidorovich.pigeon.model.User;

public class AddUserFragment extends Fragment {
    private ArrayList<User> foundUsers = new ArrayList<>();
    private UserListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        ImageButton searchButton = view.findViewById(R.id.find_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foundUsers.clear();
                EditText searchEditText = view.findViewById(R.id.search_edit_text);
                String textToSearch = searchEditText.getText().toString();

                if (textToSearch.length() == 0) {
                    Toast.makeText(view.getContext(), "Заполните текстовое поле", Toast.LENGTH_LONG).show();
                    return;
                }

                foundUsers.addAll(ServerApi.getUsersByText(textToSearch));

                if (foundUsers.size() == 0)
                    Toast.makeText(view.getContext(), "Чаты не найдены", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView usersView;
        usersView = view.findViewById(R.id.found_user_list);
        usersView.setHasFixedSize(true);
        usersView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new UserListAdapter(foundUsers, usersView, this);
        usersView.setAdapter(adapter);

        return view;
    }
}