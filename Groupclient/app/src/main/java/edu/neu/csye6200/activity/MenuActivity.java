package edu.neu.csye6200.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.neu.csye6200.R;
import edu.neu.csye6200.util.MsgTransfer;


public class MenuActivity extends Activity {
    String user;
    String pwd;
    MsgTransfer MSG = new MsgTransfer();
    ImageButton chicka;
    ImageButton chickm;
    ImageButton ffa;
    ImageButton ffm;
    ImageButton burgera;
    ImageButton burgerm;
    ImageButton ora;
    ImageButton orm;
    ImageButton sub;
    TextView chickq;
    TextView firesq;
    TextView burgerq;
    TextView orq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");
        pwd=b.getString("pwd");
        MSG = (MsgTransfer) getIntent().getSerializableExtra("MSG");

        addListenerOnButton();

        chickq=(TextView) findViewById(R.id.chickenquant);
        chickq.setText("0");

        firesq=(TextView) findViewById(R.id.ffquant);
        firesq.setText("0");

        burgerq=(TextView) findViewById(R.id.burgerquant);
        burgerq.setText("0");

        orq=(TextView) findViewById(R.id.onionquant);
        orq.setText("0");
    }

    public void addListenerOnButton() {

        final Context context = this;

        chicka =(ImageButton) findViewById(R.id.chickenadd);
        chickm =(ImageButton) findViewById(R.id.chickenminus);
        ffa =(ImageButton) findViewById(R.id.ffadd);
        ffm =(ImageButton) findViewById(R.id.ffminus);
        burgera =(ImageButton) findViewById(R.id.burgeradd);
        burgerm =(ImageButton) findViewById(R.id.burgerminus);
        ora =(ImageButton) findViewById(R.id.onionadd);
        orm =(ImageButton) findViewById(R.id.onionminus);
        sub=(ImageButton) findViewById(R.id.submitbtn);


        sub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(chickq.getText().toString());
                int q2=Integer.valueOf(firesq.getText().toString());
                int q3=Integer.valueOf(burgerq.getText().toString());
                int q4=Integer.valueOf(orq.getText().toString());

                if(q1 == 0 && q2 == 0 && q3 == 0 && q4 ==0){
                    AlertDialog.Builder submitResult = new AlertDialog.Builder(context);
                    String message= "Please add items!";
                    submitResult.setTitle("Submission Failed");
                    submitResult.setMessage(message).setCancelable(false).setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog=submitResult.create();
                    alertDialog.show();
                }
                else {
                    Intent intent = new Intent(context, CartActivity.class);
                    intent.putExtra("cq",q1);
                    intent.putExtra("ffq",q2);
                    intent.putExtra("bbq",q3);
                    intent.putExtra("orq",q4);
                    intent.putExtra("user",user);
                    intent.putExtra("pwd",pwd);
                    intent.putExtra("MSG", MSG);

                    startActivity(intent);
                }

            }
        });



        chicka.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(chickq.getText().toString());
                q1++;
                chickq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });



        ffa.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(firesq.getText().toString());
                q1++;
                firesq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });




        burgera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(burgerq.getText().toString());
                q1++;
                burgerq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });




        ora.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(orq.getText().toString());
                q1++;
                orq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });



        chickm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(chickq.getText().toString());
                if(q1>0){
                    q1=q1-1;
                }
                chickq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });



        ffm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(firesq.getText().toString());
                if(q1>0){
                    q1=q1-1;
                }
                firesq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });




        burgerm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(burgerq.getText().toString());
                if(q1>0){
                    q1=q1-1;
                }
                burgerq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });




        orm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int q1=Integer.valueOf(orq.getText().toString());
                if(q1>0){
                    q1=q1-1;
                }
                orq.setText(String.valueOf(q1));

                /*Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("bq",q1);
                intent.putExtra("cq",q2);
                intent.putExtra("ffq",q3);
                intent.putExtra("orq",q4);
                startActivity(intent);*/

            }
        });

    }
}
