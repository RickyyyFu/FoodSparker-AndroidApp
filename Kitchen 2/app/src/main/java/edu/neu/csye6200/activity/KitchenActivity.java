package edu.neu.csye6200.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.neu.csye6200.Methods.RequestItemStock;
import edu.neu.csye6200.entity.Customer;
import edu.neu.csye6200.entity.Item;
import edu.neu.csye6200.util.ApplicationUtil;
import edu.neu.csye6200.util.MsgTransfer;
import edu.neu.csye6200.util.OrderAdapter;

public class KitchenActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    TextView txtBurgerS, txtOnionS, txtFrenchS, txtChickenS;

    private Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private OrderAdapter orderAdapter;
    private Handler handler;

    static ArrayList<Customer> customerList = new ArrayList<Customer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        txtBurgerS = findViewById(R.id.burgerStock);
        txtOnionS = findViewById(R.id.onionStock);
        txtFrenchS = findViewById(R.id.friesStock);
        txtChickenS = findViewById(R.id.chickenStock);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //connect to server
        final ApplicationUtil appUtil = (ApplicationUtil) getApplication();
        socket = appUtil.getSocket();
//        try {
//            oos = new ObjectOutputStream(socket.getOutputStream());
//            ois = new ObjectInputStream(socket.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //  initialize recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //Perform a timed loop task to display stock
        handler = new Handler();
        Runnable task = new Runnable() {
            public void run() {

                try{
                    final MsgTransfer MSG = RequestItemStock.requestItemStock(socket);
                    //     ArrayList<Item> inventoryList = RequestItemStock.requestItemStock(socket);

                    String cmd = MSG.getCmd();
                    if(cmd.equals("updateStock")) {
                        ArrayList<Item> inventoryList = (ArrayList<Item>) MSG.getData();
                        displayStock(inventoryList);
                    }

                    else {
                        Customer customer = (Customer)MSG.getData();
                        customerList.add(customer);

                        orderAdapter = new OrderAdapter(KitchenActivity.this,customerList);
                        mRecyclerView.setAdapter(orderAdapter);
                        orderAdapter.buttonSetOnclick(new OrderAdapter.ButtonInterface() {
                            @Override
                            public void onclick(View view, int position) {
                                try{
                                    MSG.setCmd("Confirm");
                                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                    oos.writeObject(MSG);
                                    customerList.remove(position);
                                    orderAdapter.notifyItemRemoved(position);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                    handler.postDelayed(this,1000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        handler.post(task);


//       new Thread ( new KitchenThread(socket)).start();






        ArrayList<String> datas = initData();
//        orderAdapter = new OrderAdapter(KitchenActivity.this,customerList);
//        mRecyclerView.setAdapter(orderAdapter);


    }


    public void displayStock(ArrayList<Item> inventoryList) {

        for (Item item : inventoryList) {
            String itemname = item.getName();
            int item_inventory = item.getStock();

            if ("Burger".equals(itemname)) {
                txtBurgerS.setText(Integer.toString(item_inventory));
            } else if ("Chickens".equals(itemname)) {
                txtChickenS.setText(Integer.toString(item_inventory));
            } else if ("French Fries".equals(itemname)) {
                txtFrenchS.setText(Integer.toString(item_inventory));
            } else if ("Onion Rings".equals(itemname)) {
                txtOnionS.setText(Integer.toString(item_inventory));
            }
        }
    }

    protected ArrayList<String> initData() {
        ArrayList<String> mDatas = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("我是条目" + i);
        }
        return  mDatas;
    }


}

class KitchenThread implements Runnable{

    private Socket socket;

    public KitchenThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            ObjectInputStream oois = new ObjectInputStream(socket.getInputStream());


            MsgTransfer MSG;

            while ((MSG = (MsgTransfer) oois.readObject()) != null){
                String cmd = MSG.getCmd();

                if(cmd.equals("Submit")){
                    Customer customer = (Customer) MSG.getData();

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

