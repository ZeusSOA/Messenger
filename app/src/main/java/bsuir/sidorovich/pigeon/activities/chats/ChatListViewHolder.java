package bsuir.sidorovich.pigeon.activities.chats;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bsuir.sidorovich.pigeon.R;

public class ChatListViewHolder extends RecyclerView.ViewHolder {

    private TextView chatname;
    public ChatListViewHolder(@NonNull View itemView) {
        super(itemView);
        chatname = itemView.findViewById(R.id.chatname);
    }

    public TextView getChatname(){
        return chatname;
    }
}