package com.example.google_sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    Button googleSignOut, revokeAccess;
    private GoogleSignInClient mGoogleSignInClient;
    TextView userName;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        googleSignOut = findViewById(R.id.GoogleSignOut);
        userName = findViewById(R.id.username);
        revokeAccess = findViewById(R.id.RevokeAccess);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);


        if (account != null) {
            userName.setText(account.getDisplayName());
        }

        googleSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mGoogleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomePage.this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomePage.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });
            }
        });

        revokeAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mGoogleSignInClient.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomePage.this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomePage.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                    }
                });
            }
        });

    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_LONG).show();
        } else Toast.makeText(this, "No user", Toast.LENGTH_LONG).show();
    }

}