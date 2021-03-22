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
import bsuir.sidorovich.pigeon.model.ServerApi;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;

public class SearchChatFragment extends Fragment {
    private ArrayList<Chat> chats = ServerApi.getChats();
    private ArrayList<Chat> foundChats = new ArrayList<>();
    private ChatListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_search, container, false);

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
                foundChats.clear();
                EditText searchEditText = view.findViewById(R.id.search_edit_text);
                String textToSearch = searchEditText.getText().toString();

                boolean isId = false;
                if (textToSearch.charAt(0) == '@') {
                    textToSearch = textToSearch.substring(1);
                    isId = true;
                }
                for (Chat chat : chats) {
                    String string = isId ? chat.getId() : chat.getChatname();
                    if (string.contains(textToSearch)) {
                        foundChats.add(chat);
                    }
                }
                if (foundChats.size() == 0)
                    Toast.makeText(view.getContext(), "Чаты не найдены", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView chatsView;
        chatsView = view.findViewById(R.id.found_chat_list);
        chatsView.setHasFixedSize(true);
        chatsView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ChatListAdapter(foundChats, chatsView, this);
        chatsView.setAdapter(adapter);

        return view;
    }
}