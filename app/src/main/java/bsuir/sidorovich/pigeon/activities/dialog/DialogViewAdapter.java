package bsuir.sidorovich.pigeon.activities.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.Message;

public class DialogViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Message> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;

    private RadioGroup parentView;

    private int selectedPosition = -1;

    public void setParams(Context context, ArrayList<Message> list) {
        this.context = context;
        this.list = list;
    }

    public DialogViewAdapter(Context context) {
        this.context = context;
    }

    public DialogViewAdapter(Context context, ArrayList<Message> list) { // you can pass other parameters in constructor
        this.context = context;
        this.list = list;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV, dateTV, authorTV;

        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
            authorTV = itemView.findViewById(R.id.author_text);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(int position) {
            Message messageModel = list.get(position);
            messageTV.setText(messageModel.message);

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            dateTV.setText(date);
            authorTV.setText("anton");
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV, dateTV, authorTV;

        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
            authorTV = itemView.findViewById(R.id.author_text);
        }

        void bind(int position) {
            Message messageModel = list.get(position);
            messageTV.setText(messageModel.message);

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            dateTV.setText(date);
            authorTV.setText("anton");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.message_in, parent, false));
        }
        this.parentView = parent.findViewById(R.id.radioGroup);
        // parent.findViewById(R.id.radioGroup);
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.message_out, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (selectedPosition == position) {
            ((ChatActivity) this.context).findViewById(R.id.radioGroup).setVisibility(View.VISIBLE);
            ((ChatActivity) this.context).findViewById(R.id.radioGroup).bringToFront();
            //holder.itemView.gette
            //holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            //holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                ///  v.findViewById(R.id.radioGroup).setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

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