package edu.neu.csye6200.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import android.os.AsyncTask;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.neu.csye6200.R;
import edu.neu.csye6200.entity.Customer;
import edu.neu.csye6200.entity.Item;
import edu.neu.csye6200.entity.Order;
import edu.neu.csye6200.util.ApplicationUtil;
import edu.neu.csye6200.util.MsgTransfer;


public class CartActivity extends Activity {

    Socket socket;

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
    String user;
    String pwd;
    MsgTransfer MSG = new MsgTransfer();
    ImageButton buttont;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_screen);
        addListenerOnButton();

        // connect to server
        final ApplicationUtil appUtil = (ApplicationUtil) getApplication();
        socket = appUtil.getSocket();

        Bundle b = getIntent().getExtras();
        user = b.getString("user");
        pwd=b.getString("pwd");
        q1 = b.getInt("cq");
        q2= b.getInt("ffq");
        q3 = b.getInt("bbq");
        q4 = b.getInt("orq");
        MSG = (MsgTransfer) getIntent().getSerializableExtra("MSG");

        DecimalFormat df = new DecimalFormat("#.00");
        qqchick = (TextView)findViewById(R.id.chickenq);
        qqchick.setText(String.valueOf(q1));

        qqfries = (TextView)findViewById(R.id.ffq);
        qqfries.setText(String.valueOf(q2));

        qqburger = (TextView)findViewById(R.id.burgerq);
        qqburger.setText(String.valueOf(q3));

        qqor = (TextView)findViewById(R.id.onionq);
        qqor.setText(String.valueOf(q4));

        subt = (TextView)findViewById(R.id.subtotal);
        subt.setText(String.valueOf(df.format(q1*4.0*1.3+q2*1.5*1.3+q3*3.5*1.3+q4*2.0*1.3)));

        taxt = (TextView)findViewById(R.id.tax);
        taxt.setText(String.valueOf(df.format((q1*4*1.3+q2*1.5*1.3+q3*3.5*1.3+q4*2*1.3)*0.06)));

        tot = (TextView)findViewById(R.id.total);
        tot.setText(String.valueOf(df.format((q1*4*1.3+q2*1.5*1.3+q3*3.5*1.3+q4*2*1.3)*1.06)));



    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.confirmbtn);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                MyClientTask myClientTask = new MyClientTask(q1,q2,q3,q4,user,pwd,MSG);
                myClientTask.generateOrderMSG();

                try {
                    ObjectOutputStream oos= new ObjectOutputStream(socket.getOutputStream());
                    MSG.setResult("Server is closed");
                    oos.writeObject(MSG);

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    MSG = (MsgTransfer) ois.readObject();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // display response from server by using AlertDialog
                AlertDialog.Builder submitResult = new AlertDialog.Builder(context);
                String message= MSG.getResult();
                submitResult.setTitle("Submit Results");
                submitResult.setMessage(message).setCancelable(false).setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean accept=true;
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog=submitResult.create();
                alertDialog.show();

            }

        });

        buttont=(ImageButton)findViewById(R.id.testbut);
       buttont.setBackgroundColor(Color.parseColor("#FFFFFF"));
        buttont.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String message= "Order has been accepted";
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Notification");
                builder.setMessage(message)
                        .setCancelable(false)
                        .setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();


                                Intent intent = new Intent(context, FinishActivity.class);

                                intent.putExtra("cq",q1);
                                intent.putExtra("ffq",q2);
                                intent.putExtra("bbq",q3);
                                intent.putExtra("orq",q4);
                                intent.putExtra("user",user);
                                intent.putExtra("pwd",pwd);
                                intent.putExtra("MSG", MSG);

                                    startActivity(intent);


                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });


//        buttont=(Button)findViewById(R.id.testbut);
//        buttont.setOnClickListener(new OnClickListener() {

 //           @Override
//            public void onClick(View arg0) {
            if("Order can be partially completed".equals(MSG.getResult())){
                String message="order changed";
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Order Update");
                builder.setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean accept=true;

                                dialog.cancel();


                            }
                        })
                        .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean accept=false;
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }

 //           }

 //      });


    }
}



class MyClientTask {

    Integer qt1;
    Integer qt2;
    Integer qt3;
    Integer qt4;
    String usern;
    String passwd;
    MsgTransfer MSG;

    MyClientTask(int q1,int q2,int q3,int q4,String user,String pwd,MsgTransfer MSG){
        qt1=q1;
        qt2=q2;
        qt3=q3;
        qt4=q4;
        usern=user;
        passwd=pwd;
        this.MSG=MSG;
    }

    protected void generateOrderMSG() {

//        try {
//            socket = new Socket("192.168.1.169", 8888);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//      MsgTransfer msg=new MsgTransfer();
        Customer customer= new Customer();
        customer = (Customer) MSG.getData();
        ArrayList<Order> orderList = new ArrayList<>();
        orderList = customer.getOrderList();
        Order order= new Order();
        Item chicken = new Item("Chickens",4.00);
        Item fries = new Item("French Fries",1.50);
        Item burger = new Item("Burger",3.50);
        Item onion = new Item("Onion Rings",2.00);
        Map<Item, Integer> foodmap = new HashMap<Item, Integer>();
        foodmap.put(chicken,qt1);
        foodmap.put(fries,qt2);
        foodmap.put(burger,qt3);
        foodmap.put(onion,qt4);

        order.setItem_QuantityMap(foodmap);
        orderList.add(order);
        customer.setArrayList(orderList);
        MSG.setData(customer);
        MSG.setCmd("Submit");
    }

}
