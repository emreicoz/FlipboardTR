package com.example.flipboardtr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class LoginActivity extends Fragment {
    private FirebaseAuth mAuth;
    TextView email;
    TextView password;
    TextView username;
    TextView et;
    Button butongiris;
    Button butonkayit;
    Button butoncikis;
    TextView profilename;
    ImageView imageview;
    FirebaseUser user;
    View RootView;
    private static final String TAG = "MainActivity";



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.activity_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        username = RootView.findViewById(R.id.username_editText);
        et = RootView.findViewById(R.id.textView3);
        email = RootView.findViewById(R.id.email_text);
        password = RootView.findViewById(R.id.password_text);
        butongiris = RootView.findViewById(R.id.button_giris);
        profilename = RootView.findViewById(R.id.username_textView);
        butonkayit = RootView.findViewById(R.id.button_kayit);
        butoncikis = RootView.findViewById(R.id.button_cikis);
        imageview = RootView.findViewById(R.id.profile_imageView);


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getEmail();
            profilename.setText(name);
            username.setVisibility(View.INVISIBLE);
            et.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            butongiris.setVisibility(View.INVISIBLE);
            butonkayit.setVisibility(View.INVISIBLE);
            butoncikis.setVisibility(View.VISIBLE);

            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ProfilSecActivity.class);
                    startActivity(intent);
                }
            });

            getData();

        }


        username.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);



            butongiris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signInWithEmailAndPassword((username.getText().toString()+et.getText().toString()+email.getText().toString()),password.getText().toString())
                            .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "signInWithEmail:success");
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(LoginActivity.this).attach(LoginActivity.this).commit();

                                    }else{
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "Giriş başarısız"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });


        butonkayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword((username.getText().toString()+et.getText().toString()+email.getText().toString()),password.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "signIpWithEmail:success");
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(LoginActivity.this).attach(LoginActivity.this).commit();

                                }else{
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Kayıt başarısız",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        butoncikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(LoginActivity.this).attach(LoginActivity.this).commit();
            }
        });

        return RootView;
    }

    private void getData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newReference = database.getReference("Profiles");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    String username = ds.getValue(String.class);
                    String us = user.toString();
                    if(username.matches(mAuth.getCurrentUser().getEmail().toString())){
                        String userImageUrl = dataSnapshot.child("userimageurl").getValue(String.class);
                        if(userImageUrl != null){
                            Picasso.get().load(userImageUrl).fit().centerCrop().into(imageview);
                        }
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = username.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            Boolean bool = !usernameInput.isEmpty() && !emailInput.isEmpty() && !passwordInput.isEmpty();
            butongiris.setEnabled(bool);
            butonkayit.setEnabled(bool);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



}

