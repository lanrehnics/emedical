package com.brandshoutcom.emedicals;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorActivity extends AppCompatActivity {

    private CircleImageView im_doc_sign;
    private CircleImageView im_doc;
    private Spinner sp_avail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        im_doc_sign = (CircleImageView) findViewById(R.id.im_doc_sign);
        im_doc = (CircleImageView) findViewById(R.id.im_doc);
        sp_avail = (Spinner) findViewById(R.id.sp_avail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("eMedicals");
        sp_avail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
//                ((TextView) adapterView.getChildAt(0)).setTextSize(5);
                if (sp_avail.getItemAtPosition(i).toString().trim().contentEquals("Available")) {
                    im_doc_sign.setImageResource(R.drawable.avail);
                } else if (sp_avail.getItemAtPosition(i).toString().trim().contentEquals("Busy")) {
                    im_doc_sign.setImageResource(R.drawable.busy);
                } else {
                    im_doc_sign.setImageResource(R.drawable.not_avail);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
