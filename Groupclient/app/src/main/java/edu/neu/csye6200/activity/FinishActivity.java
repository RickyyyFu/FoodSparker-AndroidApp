package edu.neu.csye6200.activity;

import android.app.Activity;

import android.os.Bundle;

import android.widget.TextView;


import java.text.DecimalFormat;

import edu.neu.csye6200.R;


public class FinishActivity extends Activity {

    int q1;
    int q2;
    int q3;
    int q4;
    TextView qqchick;
    TextView qqfries;
    TextView qqburger;
    TextView qqor;
    TextView subt;
    TextView taxt;
    TextView tot;
    TextView cp;
    TextView fp;
    TextView bp;
    TextView orp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_screen);


        Bundle b = getIntent().getExtras();

        q1 = b.getInt("cq");
        q2= b.getInt("ffq");

        q3 = b.getInt("bbq");

        q4 = b.getInt("orq");


        DecimalFormat df = new DecimalFormat("#.00");
        qqchick = (TextView)findViewById(R.id.cq2);
        qqchick.setText((String.valueOf(q1)));

        qqfries = (TextView)findViewById(R.id.ffq2);
        qqfries.setText((String.valueOf(q2)));

        qqburger = (TextView)findViewById(R.id.bq2);
        qqburger.setText((String.valueOf(q3)));

        qqor = (TextView)findViewById(R.id.orq2);
        qqor.setText((String.valueOf(q4)));

        cp = (TextView)findViewById(R.id.ctp);
        cp.setText(String.valueOf(df.format(q1*4*1.3)));

        fp = (TextView)findViewById(R.id.ftp);
        fp.setText(String.valueOf(df.format(q2*1.3*1.3)));

        bp = (TextView)findViewById(R.id.btp);
        bp.setText(String.valueOf(df.format(q3*3.5*1.3)));

        orp = (TextView)findViewById(R.id.ortp);
        orp.setText(String.valueOf(df.format(q4*2*1.3)));



        subt = (TextView)findViewById(R.id.sub2);
        subt.setText(String.valueOf(df.format(q1*4.00*1.3+q2*1.50*1.3+q3*3.50*1.3+q4*2.00*1.3)));

        taxt = (TextView)findViewById(R.id.tax2);
        taxt.setText(String.valueOf(df.format((q1*4.00*1.3+q2*1.50*1.3+q3*3.50*1.3+q4*2.00*1.3)*0.06)));

        tot = (TextView)findViewById(R.id.total2);
        tot.setText(String.valueOf(df.format((q1*4.00*1.3+q2*1.50*1.3+q3*3.50*1.3+q4*2.00*1.3)*1.06)));



    }}





