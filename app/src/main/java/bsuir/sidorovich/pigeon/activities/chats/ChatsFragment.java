package bsuir.sidorovich.pigeon.activities.chats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.server_access.ServerApi;
import bsuir.sidorovich.pigeon.model.server_access.server_api.ChatServiceApi;
import bsuir.sidorovich.pigeon.model.server_access.server_api.UserServiceApi;

public class ChatsFragment extends Fragment {
    //при необходимости при запуске приложения метод getChats() будет загружать список не в ОЗУ, как сейчас, а в БД
    private ArrayList<Chat> chats = ServerApi.getChats();
    private ChatListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chats, container, false);

        ///
        //кнопка для проверки какого-либо запроса
        //находится во вкладке Чаты (левее всех)
        Button serverButton = view.findViewById(R.id.server_button);
        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), UserServiceApi.test(), Toast.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), ChatServiceApi.test(), Toast.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), ChatServiceApi.testObject(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), ChatServiceApi.test_getChatById(), Toast.LENGTH_LONG).show();
            }
        });
        ///

        ImageButton searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment searchChatFragment = new SearchChatFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, searchChatFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton addUserButton = view.findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addUserFragment = new AddUserFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, addUserFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton addGroupButton = view.findViewById(R.id.add_group_button);
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), view.findViewById(R.id.add_group_button));
                popup.inflate(R.menu.add_group_chat_menu);
                Menu menu = popup.getMenu();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        FragmentManager fragmentManager;
                        FragmentTransaction fragmentTransaction;
                        switch (item.getItemId()) {
                            case R.id.create_menu_item:
                                Fragment createGroupChatFragment = new CreateGroupChatFragment();
                                fragmentManager = getFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, createGroupChatFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;
                            case R.id.join_menu_item:
                                Fragment joinGroupChatFragment = new JoinGroupChatFragment();
                                fragmentManager = getFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, joinGroupChatFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        // возможно стоит перенести в глобальную область
        RecyclerView chatsView;
        chatsView = view.findViewById(R.id.chat_list);
        chatsView.setHasFixedSize(true);
        chatsView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ChatListAdapter(chats, chatsView, this);
        chatsView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            adapter.removeSelectedItem();
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