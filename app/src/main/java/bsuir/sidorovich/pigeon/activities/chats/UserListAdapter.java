package bsuir.sidorovich.pigeon.activities.chats;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.server_access.ServerApi;
import bsuir.sidorovich.pigeon.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    private ArrayList<User> users;
    private RecyclerView usersView;
    private Fragment fragment;
    private int selectedUserIndex;

    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView id;
        private ImageView chatCircle;
        private ImageView chatIcon;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_text);
            id = itemView.findViewById(R.id.id_text);
            chatCircle = itemView.findViewById(R.id.imageView);
            chatIcon = itemView.findViewById(R.id.imageView2);
        }

        public TextView getUsername() {
            return username;
        }
        public TextView getId() {
            return id;
        }
        public ImageView getChatCircle() {
            return chatCircle;
        }
        public ImageView getChatIcon() {
            return chatIcon;
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
        holder.getChatCircle().setImageResource(R.drawable.circle_user);
        holder.getChatIcon().setImageResource(R.drawable.ic_user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
