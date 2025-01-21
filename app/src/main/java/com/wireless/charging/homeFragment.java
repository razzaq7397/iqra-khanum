package com.wireless.charging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class homeFragment extends Fragment {

    View view;
    TextView station1, station2;
    ValueEventListener valueEventListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        station1 =(TextView) view.findViewById(R.id.station1);
        station2 =(TextView) view.findViewById(R.id.station2);

        valueEventListener = FirebaseDatabase.getInstance().getReference("distances").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("distance1").getValue(Double.class)>10){
                        station1.setText("Station 1\n" + "Free");
                    }else{
                        station1.setText("Station 1\n" + "Charging Mode");
                    }

                    if (snapshot.child("distance2").getValue(Double.class)>10){
                        station2.setText("Station 2\n" + "Free");
                    }else{
                        station2.setText("Station 2\n" + "Charging Mode");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        valueEventListener = null;
        super.onDestroyView();
    }
}