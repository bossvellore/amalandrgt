package com.dsa.authfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.dsa.firebasedl.UserRDB;
import com.dsa.gt.R;
import com.dsa.model.AppUser;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by amalroshand on 29/05/17.
 */

public class AuthFireBase implements GoogleApiClient.OnConnectionFailedListener{
    AppCompatActivity context;
    public static int GOOGLE_SIGN_IN=8912;
    String TAG="SignupActivity";
    GoogleApiClient googleApiClient;
    private static FirebaseAuth fbAuth;
    GoogleSignInOptions gso;
    public static FirebaseUser currentUser;
    private AuthFireBaseListener authFireBaseListener;
    public static FirebaseUser getCurrentUser()
    {
        fbAuth = FirebaseAuth.getInstance();
        return fbAuth.getCurrentUser();
    }

    public AuthFireBase(AppCompatActivity context, AuthFireBaseListener authFireBaseListener)
    {
        this.context = context;
        this.authFireBaseListener=authFireBaseListener;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(context /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        fbAuth = FirebaseAuth.getInstance();
        currentUser = fbAuth.getCurrentUser();
    }

    public AuthFireBase(AppCompatActivity context, boolean populateUserInDB)
    {
        //this.AuthFireBase(context);
    }
    public void googleSignup()
    {
        if(currentUser == null) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            context.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        }else{
            authFireBaseListener.onAuthFireBaseSuccess();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                authFireBaseListener.onAuthFireBaseFailure();
                //Log.d(TAG, result.getMyStatus().toString());
                //signupWithGoogleBtn.setVisibility(View.VISIBLE);
                //mProgress.setVisibility(View.INVISIBLE);
                //Toast.makeText(SignupActivity.this, " Google Signup failed.",
                //        Toast.LENGTH_SHORT).show();
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            currentUser = fbAuth.getCurrentUser();
                            AppUser appUser=new AppUser();
                            appUser.setUid(currentUser.getUid());
                            appUser.setDisplayName(currentUser.getDisplayName());
                            appUser.setEmail(currentUser.getEmail());
                            appUser.setPhotoUrl(currentUser.getPhotoUrl().toString());
                            UserRDB userRDB= UserRDB.getInstance();
                            userRDB.save(appUser);
                            authFireBaseListener.onAuthFireBaseSuccess();
                            //updateUI(user);
                        } else {
                            authFireBaseListener.onAuthFireBaseFailure();
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //signupWithGoogleBtn.setVisibility(View.VISIBLE);
                            //mProgress.setVisibility(View.INVISIBLE);
                            //Toast.makeText(SignupActivity.this, "Authentication failed.",
                            //        Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        authFireBaseListener.onAuthFireBaseFailure();
    }

    private void signOut() {
        // Firebase sign out
        fbAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        fbAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
    }
}
