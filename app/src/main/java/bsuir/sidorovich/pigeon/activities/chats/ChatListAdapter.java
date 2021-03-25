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
import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    private ArrayList<Chat> chats;
    private RecyclerView chatsView;
    private Fragment fragment;
    private int selectedChatIndex;

    public static class ChatListViewHolder extends RecyclerView.ViewHolder {

        private TextView chatname;
        private TextView chatId;
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            chatname = itemView.findViewById(R.id.chatname_text);
            chatId = itemView.findViewById(R.id.chat_id_text);
        }

        public TextView getChatname(){
            return chatname;
        }
        public TextView getChatId(){
            return chatId;
        }
    }

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
                selectedChatIndex = chatsView.getChildLayoutPosition(view);
                Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
                intent.putExtra("id", chats.get(selectedChatIndex).getId());
                fragment.startActivity(intent);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectedChatIndex = chatsView.getChildLayoutPosition(view);

                String message = "";
                if (chats.get(selectedChatIndex) instanceof SingleChat) {
                    message = "Удалить пользователя из списка чатов?";
                } else if (chats.get(selectedChatIndex) instanceof GroupChat) {
                    message = "Выйти из беседы и удалить её из списка чатов?";
                }

                DeleteDialogFragment dialog = new DeleteDialogFragment();
                Bundle args = new Bundle();
                args.putString("chatname", chats.get(selectedChatIndex).getChatname());
                args.putString("message", message);
                dialog.setArguments(args);
                dialog.setTargetFragment(fragment, 1);
                dialog.show(fragment.getFragmentManager(), "dialog");

                return true;
            }
        });

        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.getChatname().setText(chats.get(position).getChatname());
        holder.getChatId().setText(chats.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void removeSelectedItem() {
        ServerApi.deleteChat(chats.remove(selectedChatIndex).getId());
        notifyItemRemoved(selectedChatIndex);
        notifyItemRangeChanged(selectedChatIndex, chats.size());

        //ServerApi.removeChat

        //лучше - удалить только на сервере и обновить весь список
    }

}
