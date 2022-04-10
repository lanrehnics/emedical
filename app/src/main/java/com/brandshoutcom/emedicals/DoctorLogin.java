package com.brandshoutcom.emedicals;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andreabaccega.widget.FormEditText;
import com.brandshoutcom.network.RestClientHelper;
import com.brandshoutcom.utils.SharedPrefs;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import xhome.uestcfei.com.loadingpoppoint.LoadingPopPoint;

import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_IS_LOGIN;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_LOGIN;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE;
import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_USER_TYPE_DOC;

public class DoctorLogin extends AppCompatActivity {

    private ImageView im_l;
    private FormEditText et_doc_id, et_doc_pass;
    private RelativeLayout docpage;
    private LoadingPopPoint loading;
    private CardView btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        im_l = (ImageView) findViewById(R.id.im_l);
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(im_l);
        et_doc_id = (FormEditText) findViewById(R.id.et_doc_id);
        et_doc_pass = (FormEditText) findViewById(R.id.et_doc_pass);
        docpage = (RelativeLayout) findViewById(R.id.docpage);
        loading = (LoadingPopPoint) findViewById(R.id.loading);
        btn_login = (CardView) findViewById(R.id.card_login);
    }

    public void doclogin(View v) {
        FormEditText[] allFields = {et_doc_id,
                et_doc_pass};
        boolean allValid = true;

        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {
            holdOn();
            loading.setVisibility(View.VISIBLE);
            String user = et_doc_id.getText().toString().trim().toLowerCase();
            String pd = et_doc_pass.getText().toString();

            RestClientHelper.getInstance().post(MyApplication.SITE_URL + KEY_LOGIN + "?user=" + user + "&pd=" + pd, null, new RestClientHelper.RestClientListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d("#00#uu", response);
                    if (response.toLowerCase().contentEquals("success")) {
                        SharedPrefs sharedPrefs = SharedPrefs.getPreference(MyApplication.getAppContext());
                        sharedPrefs.putBoolean(KEY_IS_LOGIN, true);
                        sharedPrefs.putString(KEY_USER_TYPE, KEY_USER_TYPE_DOC);
                        Intent main = new Intent(DoctorLogin.this, DoctorActivity.class);
                        release();
                        startActivity(main);
                        LoginActivity.loginActivity.finish();

                    } else {
                        Snackbar.make(docpage, response, Snackbar.LENGTH_LONG).show();
                        release();
                    }

                }

                @Override
                public void onError(String error) {
                    release();
                    Snackbar.make(docpage, error, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private void holdOn() {
        loading.setVisibility(View.VISIBLE);
        et_doc_id.setEnabled(false);
        et_doc_pass.setEnabled(false);

        btn_login.setClickable(false);

    }

    private void release() {
        loading.setVisibility(View.GONE);
        et_doc_id.setEnabled(true);
        et_doc_pass.setEnabled(true);

        btn_login.setClickable(true);

    }
}
