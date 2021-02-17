package bsuir.sidorovich.pigeon.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.StartActivity;

public class PersonFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_person, container, false);

        Button signOut = view.findViewById(R.id.buttonExit);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(view.getContext(), StartActivity.class));
            }
        });

        return view;
    }
}
