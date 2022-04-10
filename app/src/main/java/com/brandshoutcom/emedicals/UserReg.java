package com.brandshoutcom.emedicals;

import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.brandshoutcom.network.RestClientHelper;

import xhome.uestcfei.com.loadingpoppoint.LoadingPopPoint;

import static com.brandshoutcom.extras.KEYS.EndPointQuestions.KEY_REG;

public class UserReg extends AppCompatActivity {
    private FormEditText et_firstname,
            et_lastname,
            et_phoneno,
            et_email,
            et_username,
            et_pass,
            et_cofirmpass,
            et_town,
            et_city;
    private Button btn_reg;
    private Spinner spin_gender, spin_state;
    private LoadingPopPoint loading;
    private RelativeLayout regPage;
    private String gender, state;
    private CheckBox cb_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        init();

    }

    private void init() {
        regPage = (RelativeLayout) findViewById(R.id.activity_registration);
        et_firstname = (FormEditText) findViewById(R.id.et_firstname);
        et_lastname = (FormEditText) findViewById(R.id.et_lastname);
        et_phoneno = (FormEditText) findViewById(R.id.et_phoneno);
        et_email = (FormEditText) findViewById(R.id.et_email);
        et_username = (FormEditText) findViewById(R.id.et_username);
        et_pass = (FormEditText) findViewById(R.id.et_pass);
        et_cofirmpass = (FormEditText) findViewById(R.id.et_cofirmpass);
        et_town = (FormEditText) findViewById(R.id.et_town);
        et_city = (FormEditText) findViewById(R.id.et_city);
        spin_gender = (Spinner) findViewById(R.id.spin_gender);
        spin_state = (Spinner) findViewById(R.id.spin_state);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        cb_terms = (CheckBox) findViewById(R.id.cb_terms);

        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = spin_gender.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = spin_state.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loading = (LoadingPopPoint) findViewById(R.id.loading);


    }

    public void regUser(View view) {
        FormEditText[] allFields = {et_firstname,
                et_lastname,
                et_phoneno,
                et_email,
                et_username,
                et_pass,
                et_cofirmpass,
                et_town,
                et_city};
        boolean allValid = true;

        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid && comparePass() && isTermChecked() && checkStatetAndGender();
        }

        if (allValid) {
            holdOn();
            loading.setVisibility(View.VISIBLE);
            ArrayMap<String, Object> postParams = new ArrayMap<>();
            String email = et_email.getText().toString().trim().toLowerCase();
            String firstname = et_firstname.getText().toString().trim().toLowerCase();
            String lastname = et_lastname.getText().toString().trim().toLowerCase();
            String username = et_username.getText().toString().trim().toLowerCase();
            String password = et_pass.getText().toString().trim();
            String phonenumber = et_phoneno.getText().toString().trim().toLowerCase();
            String town = et_town.getText().toString().trim().toLowerCase();
            String city = et_city.getText().toString().trim().toLowerCase();

            String url = String.format("?email=%s&firstname=%s&lastname=%s&username=%s&password=%s&phonenumber=%s&gender=%s&state=%s&town=%s&city=%s" +
                    "", email, firstname, lastname, username, password, phonenumber, gender, state, town, city);
            RestClientHelper.getInstance().post(MyApplication.SITE_URL + KEY_REG+url, postParams, new RestClientHelper.RestClientListener() {
                @Override
                public void onSuccess(String response) {
                    if (response.toLowerCase().contentEquals("success")) {
                        Toast.makeText(MyApplication.getAppContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Snackbar.make(regPage, response, Snackbar.LENGTH_LONG).show();
                        release();
                    }

                }

                @Override
                public void onError(String error) {
                    release();
                    Snackbar.make(regPage, error, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private boolean checkStatetAndGender() {
        if (state.contentEquals("Select State")) {
            Snackbar.make(regPage, "Select a state", Snackbar.LENGTH_LONG).show();
        } else {
            if (gender.contentEquals("Select Gender")) {
                Snackbar.make(regPage, "Select your gender", Snackbar.LENGTH_LONG).show();
            } else {
                return true;
            }
        }
        Toast.makeText(MyApplication.getAppContext(), "Gender and State not selected", Toast.LENGTH_LONG).show();
        return false;
    }

    private void holdOn() {
        loading.setVisibility(View.VISIBLE);
        et_firstname.setEnabled(false);
        et_lastname.setEnabled(false);
        et_phoneno.setEnabled(false);
        et_email.setEnabled(false);
        et_username.setEnabled(false);
        et_pass.setEnabled(false);
        et_cofirmpass.setEnabled(false);
        et_town.setEnabled(false);
        et_city.setEnabled(false);

        btn_reg.setClickable(false);

    }

    private void release() {
        loading.setVisibility(View.GONE);
        et_firstname.setEnabled(true);
        et_lastname.setEnabled(true);
        et_phoneno.setEnabled(true);
        et_email.setEnabled(true);
        et_username.setEnabled(true);
        et_pass.setEnabled(true);
        et_cofirmpass.setEnabled(true);
        et_town.setEnabled(true);
        et_city.setEnabled(true);
        btn_reg.setClickable(true);

        btn_reg.setClickable(true);

    }

    private boolean isTermChecked() {
        if (cb_terms.isChecked()) {
            return true;
        }
        Snackbar.make(regPage, "Accept Terms and Conditions", Snackbar.LENGTH_LONG).show();
        return false;
    }

    private boolean comparePass() {
        if (et_pass.getText().toString().trim().contentEquals(et_cofirmpass.getText().toString().trim())) {
            return true;
        }
        Snackbar.make(regPage, "Password not equal", Snackbar.LENGTH_LONG).show();
        return false;
    }
}
