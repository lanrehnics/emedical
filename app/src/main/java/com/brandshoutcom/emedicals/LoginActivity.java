package com.brandshoutcom.emedicals;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.brandshoutcom.network.RestClientHelper;
import com.brandshoutcom.utils.SharedPrefs;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Random;

import xhome.uestcfei.com.loadingpoppoint.LoadingPopPoint;

import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_IS_LOGIN;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_LOGIN;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE_DOC;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE_USER;
import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    public static Activity loginActivity = null;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private Button btn_login;
    private Random r = new Random();
    private FormEditText et_email, et_pass;
    private RelativeLayout logPage;
    private int[] tipImages = {R.drawable.doc_f, R.drawable.doc_ff, R.drawable.doc_m, R.drawable.hrt};
    private LoadingPopPoint loading;
//    private CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        et_email = (FormEditText) findViewById(R.id.et_email);
        et_pass = (FormEditText) findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        loading = (LoadingPopPoint) findViewById(R.id.loading);
        logPage = (RelativeLayout) findViewById(R.id.activity_login);
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);

    }

    public void loginIn(View view) {
        FormEditText[] allFields = {et_email,
                et_pass};
        boolean allValid = true;

        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {
            holdOn();
            loading.setVisibility(View.VISIBLE);
            String user = et_email.getText().toString().trim().toLowerCase();
            String pd = et_pass.getText().toString();

            RestClientHelper.getInstance().post(MyApplication.SITE_URL + KEY_LOGIN + "?user=" + user + "&pd=" + pd, null, new RestClientHelper.RestClientListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d("#00#uu", response);
                    if (response.toLowerCase().contentEquals("success")) {
                        SharedPrefs sharedPrefs = SharedPrefs.getPreference(MyApplication.getAppContext());
                        sharedPrefs.putBoolean(KEY_IS_LOGIN, true);
                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        release();
                        startActivity(main);

                    } else {
                        Snackbar.make(logPage, response, Snackbar.LENGTH_LONG).show();
                        release();
                    }

                }

                @Override
                public void onError(String error) {
                    release();
                    Snackbar.make(logPage, error, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private void holdOn() {
        loading.setVisibility(View.VISIBLE);
        et_email.setEnabled(false);
        et_pass.setEnabled(false);

        btn_login.setClickable(false);

    }

    private void release() {
        loading.setVisibility(View.GONE);
        et_email.setEnabled(true);
        et_pass.setEnabled(true);

        btn_login.setClickable(true);

    }

    public void regUser(View view) {
        startActivity(new Intent(LoginActivity.this, UserReg.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
//        nextActivity(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    private void nextActivity(Profile profile) {
        if (profile != null) {
            SharedPrefs sharedPrefs = SharedPrefs.getPreference(MyApplication.getAppContext());
            sharedPrefs.putBoolean(KEY_IS_LOGIN, true);
            sharedPrefs.putString(KEY_USER_TYPE, KEY_USER_TYPE_USER);

            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200, 200).toString());
            startActivity(main);

        }
    }

    //Facebook login button
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            nextActivity(profile);
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }
    };

    public void loginDoc(View v) {
        startActivity(new Intent(LoginActivity.this, DoctorLogin.class));
    }

}
