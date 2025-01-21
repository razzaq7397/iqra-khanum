package com.wireless.charging;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mainpage extends AppCompatActivity {

    private int selectedtab = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        final LinearLayout homelayout=findViewById(R.id.homelayout);
        final LinearLayout profilelayout=findViewById(R.id.profilelayout);
        final ImageView homeimage=findViewById(R.id.homeimage);
        final ImageView profileimage=findViewById(R.id.profileimage);
        final TextView hometxt=findViewById(R.id.hometxt);
        final TextView profiletxt=findViewById(R.id.profiletxt);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragments, homeFragment.class, null)
                .commit();

        homelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedtab != 1)
                {

                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragments, homeFragment.class, null)
                            .commit();

                    profiletxt.setVisibility(View.GONE);
                    profileimage.setImageResource(R.drawable.profile);
                    profilelayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    hometxt.setVisibility(View.VISIBLE);
                    homeimage.setImageResource(R.drawable.homeselected);
                    homelayout.setBackgroundResource(R.drawable.round_back_home);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f );
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homelayout.startAnimation(scaleAnimation);

                    selectedtab=1;
                }

            }
        });

        profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedtab != 3)
                {

                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragments, profileFragment.class, null)
                            .commit();

                    hometxt.setVisibility(View.GONE);

                    homeimage.setImageResource(R.drawable.home);

                    homelayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profiletxt.setVisibility(View.VISIBLE);
                    profileimage.setImageResource(R.drawable.profileselected);
                    profilelayout.setBackgroundResource(R.drawable.round_back_profile);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f );
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profilelayout.startAnimation(scaleAnimation);

                    selectedtab=3;
                }
            }
        });






    }
}