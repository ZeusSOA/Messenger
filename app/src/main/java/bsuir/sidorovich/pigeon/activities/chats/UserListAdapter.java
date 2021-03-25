package bsuir.sidorovich.pigeon.activities.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.ServerApi;
import bsuir.sidorovich.pigeon.model.User;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    private ArrayList<User> users;
    private RecyclerView usersView;
    private Fragment fragment;
    private int selectedUserIndex;

    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView id;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_text);
            id = itemView.findViewById(R.id.id_text);
        }

        public TextView getUsername() {
            return username;
        }

        public TextView getId() {
            return id;
        }
    }

    public UserListAdapter(
            ArrayList<User> users,
            RecyclerView usersView,
            Fragment fragment
    ) {
        this.users = users;
        this.usersView = usersView;
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.layout_user_list_item;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUserIndex = usersView.getChildLayoutPosition(view);
                String chatId = ServerApi.addSingleChatWithUser(users.get(selectedUserIndex).getId());
                Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
                intent.putExtra("id", chatId);
                fragment.startActivity(intent);
            }
        });

        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        holder.getUsername().setText(users.get(position).getUsername());
        holder.getId().setText("@" + users.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
