package bsuir.sidorovich.pigeon.activities.chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.model.Chat;

public class ChatsFragment extends Fragment {
    //при необходимости при запуске приложения метод getChats() будет загружать список не в ОЗУ, как сейчас, а в БД
    private ArrayList<Chat> chats = getChats();

    private ImageButton searchButton;
    private ImageButton addButton;

    private RecyclerView chatList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chats, container, false);

//        searchButton = view.findViewById(R.id.search_button);
//        addButton = view.findViewById(R.id.add_button);
//
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("tag", "search");
//
////                Intent intent = new Intent(getActivity(), TestActivity.class);
////                startActivity(intent);
//            }
//        });
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("tag", "add");
//            }
//        });

        chatList = view.findViewById(R.id.chat_list);
        chatList.setHasFixedSize(true);
        chatList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        chatList.setAdapter(new ChatListAdapter(chats));

        return view;
    }

    //  клиентский метод взаимодействия с сервером (в будущем лучше перенести в отдельный класс)
    public ArrayList<Chat> getChats() {
        //отправить свой userID серверу
        //сервер ищет, в каких чатах состоит userID
        //сервер возвращает список чатов с chatID

        //ChatView:
        //название, id чата
        //фото, последнее сообщение + время, уведомление

        ArrayList<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Chat chat = new Chat();
            chat.setChatname("chat_" + (i + 1));
            chats.add(chat);
        }
        return chats;
    }
}

////код фрагмента с выводом имени пользователя через firebase
//
//package bsuir.sidorovich.pigeon.activities.chats;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import bsuir.sidorovich.pigeon.R;
//import bsuir.sidorovich.pigeon.model.User;
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class ChatsFragment extends Fragment {
//    private CircleImageView profileImage;
//    private TextView username;
//    private FirebaseUser firebaseUser;
//    private DatabaseReference reference;
//
//    private ImageButton searchButton;
//
//
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.fragment_chats, container, false);
//
//        profileImage = view.findViewById(R.id.profile_image);
//        username = view.findViewById(R.id.username);
//        searchButton = view.findViewById(R.id.search_button);
//
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("tag", "search");
//            }
//        });
//
//
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                assert user != null;
//                username.setText(user.getUsername());
//
//                if (user.getImageUrl().equals("default"))
//                    profileImage.setImageResource(R.mipmap.ic_launcher);
//                else
//                    Glide.with(view).load(user.getImageUrl()).into(profileImage);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return view;
//    }
//}