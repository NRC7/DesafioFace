package com.desafiolatam.desafioface.views.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.data.CurrentUserQueries;
import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.views.login.LoginActivity;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {


    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CurrentUser currentUser = new CurrentUserQueries().get();
        TextView textView = view.findViewById(R.id.nickTv);
        textView.setText(currentUser.getEmail());

        CircularImageView circularImageView = view.findViewById(R.id.avatarCiv);
        Picasso.get().load(currentUser.getPhoto_url()).centerCrop().fit().into(circularImageView);

        TextView logout = view.findViewById(R.id.logoutTv);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "LogOut", Toast.LENGTH_SHORT).show();
                CurrentUser.deleteAll(CurrentUser.class);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();

            }
        });
    }
}
