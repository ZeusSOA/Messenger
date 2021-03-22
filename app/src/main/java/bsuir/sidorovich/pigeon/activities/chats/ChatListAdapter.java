package bsuir.sidorovich.pigeon.activities.chats;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.Chat;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {
    private ArrayList<Chat> chats;
    private RecyclerView chatsView;
    private Fragment fragment;

    public ChatListAdapter(
            ArrayList<Chat> chats,
            RecyclerView chatsView,
            Fragment fragment
    ) {
        this.chats = chats;
        this.chatsView = chatsView;
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.layout_chat_list_item;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // обработка нажатия https://stackoverflow.com/questions/24471109/recyclerview-onclick

                Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
                intent.putExtra("id", chats.get(chatsView.getChildLayoutPosition(view)).getId());
                fragment.startActivity(intent);
            }
        });

        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.getChatname().setText(chats.get(position).getChatname());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}