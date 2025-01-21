package com.wireless.charging;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class profileFragment extends Fragment {

    View view;
    TextView name, email, phone;
    Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(requireActivity(), login.class));
                            requireActivity().finish();
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            name.setText(snapshot.child("name").getValue(String.class));
                            email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            phone.setText(snapshot.child("phone").getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }
}