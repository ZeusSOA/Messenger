package bsuir.sidorovich.pigeon.activities.chats;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.ServerApi;

public class CreateGroupChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_group_chat, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        Button createButton = view.findViewById(R.id.create_group_chat_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText chatnameEditText = view.findViewById(R.id.group_chat_name_edit_text);
                String chatname = chatnameEditText.getText().toString();

                if (chatname.length() == 0) {
                    Toast.makeText(view.getContext(), "Заполните текстовое поле", Toast.LENGTH_LONG).show();
                    return;
                }

                String chatId = ServerApi.createGroupChatWithChatname(chatname);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("id", chatId);
                startActivity(intent);
            }
        });

        return view;
    }
}