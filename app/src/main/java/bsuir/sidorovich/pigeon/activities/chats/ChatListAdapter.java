package bsuir.sidorovich.pigeon.activities.chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.model.Chat;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {
    private ArrayList<Chat> chats;

    public ChatListAdapter(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.layout_chat_list_item;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
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