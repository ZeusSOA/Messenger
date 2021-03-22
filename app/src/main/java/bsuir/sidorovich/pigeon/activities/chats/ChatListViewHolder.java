package bsuir.sidorovich.pigeon.activities.chats;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

//package bsuir.sidorovich.pigeon.activities.chats;
//
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import bsuir.sidorovich.pigeon.R;
//
//public class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//    private TextView chatname;
//    public ChatListViewHolder(@NonNull View itemView) {
//        super(itemView);
//        itemView.setOnClickListener(this);
//        chatname = itemView.findViewById(R.id.chatname);
//    }
//
//    @Override
//    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
//    }
//
//        public TextView getChatname(){
//        return chatname;
//    }
//}

