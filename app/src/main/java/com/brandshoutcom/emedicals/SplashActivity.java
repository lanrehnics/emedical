package com.brandshoutcom.emedicals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandshoutcom.utils.SharedPrefs;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_IS_LOGIN;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE_DOC;
import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {
    ImageView splash_icon;
    TextView tv_help, tv_emed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash_icon = (ImageView) findViewById(R.id.splash_icon);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_emed = (TextView) findViewById(R.id.tv_emed);
        YoYo.with(Techniques.RollIn).duration(2000).playOn(splash_icon);
        YoYo.with(Techniques.StandUp).duration(2000).playOn(tv_emed);
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(tv_help);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                } finally {
                    SplashActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // we launch log-in page heree..
//                            SharedPrefs sharedPrefs = SharedPrefs.getPreference(MyApplication.getAppContext());
//                            boolean logged_in = sharedPrefs.getPreferenceBoolean(KEY_IS_LOGIN);
//                            if (logged_in) {
//                                startActivity(new Intent(Splash.this, MainActivity.class));
//                                finish();
//                            } else {
//                                final Intent intent = new Intent(Splash.this, Login.class);
//                                ActivityTransitionLauncher.with(Splash.this).from(splash_icon).launch(intent);
//                                finish();


                            SharedPrefs sharedPrefs = SharedPrefs.getPreference(MyApplication.getAppContext());
                            if (sharedPrefs.getPreferenceBoolean(KEY_IS_LOGIN)) {
                                if (sharedPrefs.getPreferenceString(KEY_USER_TYPE).contentEquals(sharedPrefs.getPreferenceString(KEY_USER_TYPE_DOC)))
                                {
                                    startActivity(new Intent(SplashActivity.this, DoctorActivity.class));
                                    finish();
                                }else{
                                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                    finish();
                                }

                            } else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        }

//                        }
                    });
                }
            }
        }).start();
    }
}
