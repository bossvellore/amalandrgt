package com.dsa.gt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dsa.authfirebase.AuthFireBase;
import com.dsa.authfirebase.AuthFireBaseListener;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements AuthFireBaseListener {

    AuthFireBaseListener authFireBaseListener;
    AuthFireBase authFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signupWithGoogleBtn = (Button)findViewById(R.id.googleSignUpBtn);
        signupWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupWithGoogle();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = AuthFireBase.getCurrentUser();
        if(currentUser != null) {
            this.updateUI("LOGGED-IN");
        }
    }

    private void signupWithGoogle()
    {
        authFireBase=new AuthFireBase(this, this);
        authFireBase.googleSignup();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AuthFireBase.GOOGLE_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data);
            authFireBase.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onAuthFireBaseSuccess() {
        this.updateUI("LOGGED-IN");
    }

    @Override
    public void onAuthFireBaseFailure() {
        this.updateUI("LOGGED-IN-FAILED");
    }

    private void updateUI(String status)
    {
        switch (status){
            case "LOGGED-IN":
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                finish();
                break;
            case "LOGGED-IN-FAILED":
                Toast.makeText(SignupActivity.this, " Google Signup failed.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
