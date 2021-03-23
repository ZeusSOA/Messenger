package bsuir.sidorovich.pigeon.activities.chats;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.ServerApi;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;


public class JoinGroupChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_join_group_chat, container, false);
        final ConstraintLayout foundGroupChatLayout = view.findViewById(R.id.found_group_chat_layout);
        foundGroupChatLayout.setVisibility(View.INVISIBLE);

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
                EditText searchEditText = view.findViewById(R.id.search_edit_text);
                String textToSearch = searchEditText.getText().toString();

                if (textToSearch.length() == 0) {
                    Toast.makeText(view.getContext(), "Заполните текстовое поле", Toast.LENGTH_LONG).show();
                    return;
                }
                if (textToSearch.charAt(0) != '@') {
                    Toast.makeText(view.getContext(), "Id начинается с символа \"@\"", Toast.LENGTH_LONG).show();
                    return;
                }
                textToSearch = textToSearch.substring(1);

                final GroupChat groupChat = ServerApi.getGroupChatWithId(textToSearch);
                if (groupChat != null) {
                    TextView chatnameText = view.findViewById(R.id.chatname_text);
                    TextView chatIdText = view.findViewById(R.id.chat_id_text);
                    TextView membersCountText = view.findViewById(R.id.members_count_text);

                    chatnameText.setText(groupChat.getChatname());
                    chatIdText.setText("@" + groupChat.getId());
                    membersCountText.setText(String.valueOf(groupChat.getMembersCount()) + " участников");

                    Button joinButton = view.findViewById(R.id.join_button);
                    joinButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ServerApi.joinGroupChatWithId(groupChat.getId())) {
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.putExtra("id", groupChat.getId());
                                startActivity(intent);
                            } else {
                                Toast.makeText(view.getContext(), "Вы уже вступили в эту беседу", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    if (foundGroupChatLayout.getVisibility() == View.INVISIBLE)
                        foundGroupChatLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(view.getContext(), "Беседа не найдена", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}