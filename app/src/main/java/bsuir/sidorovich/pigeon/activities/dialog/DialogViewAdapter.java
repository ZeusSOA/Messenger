package bsuir.sidorovich.pigeon.activities.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.model.Message;

public class DialogViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<Message> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;

    public void setParams(Context context, ArrayList<Message> list){
        this.context = context;
        this.list = list;
    }
    public DialogViewAdapter(Context context){
        this.context = context;
    }
    public DialogViewAdapter(Context context, ArrayList<Message> list) { // you can pass other parameters in constructor
        this.context = context;
        this.list = list;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }
        void bind(int position) {
            Message messageModel = list.get(position);
            messageTV.setText(messageModel.message);
            dateTV.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(messageModel.messageTime.getTime() + 2 * 3600*1000)));
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }
        void bind(int position) {
            Message messageModel = list.get(position);
            messageTV.setText(messageModel.message);
            dateTV.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.messageTime));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.message_in, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.message_out, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).messageType == MESSAGE_TYPE_IN) {
            ((MessageInViewHolder) holder).bind(position);
        } else {
            ((MessageOutViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).messageType;
    }
}