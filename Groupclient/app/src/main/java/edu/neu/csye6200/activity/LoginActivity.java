package edu.neu.csye6200.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import edu.neu.csye6200.R;
import edu.neu.csye6200.entity.Customer;
import edu.neu.csye6200.util.ApplicationUtil;
import edu.neu.csye6200.util.MsgTransfer;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginActivity extends Activity {

    ImageButton login_button, register_button;
    EditText usernameT, pwdT;
    TextView resultT;

    private Socket socket;
    private MsgTransfer MSG = new MsgTransfer();
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // connect to server
        final ApplicationUtil appUtil = (ApplicationUtil) getApplication();
        socket = appUtil.getSocket();
        MSG = appUtil.getMSG();
//        oos = appUtil.getOutputStream();
//        ois = appUtil.getInputStream();
        addListenerOnButton();

    }

    public void addListenerOnButton() {

        final Context context = this;

        login_button = (ImageButton) findViewById(R.id.loginbtn);
        register_button = (ImageButton) findViewById(R.id.registerbtn);

        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                usernameT = (EditText)findViewById(R.id.usertxt);
                pwdT = (EditText)findViewById(R.id.pwdtxt);
                String username = usernameT.getText().toString();
                String pwd = pwdT.getText().toString();

                resultT = (TextView) findViewById(R.id.resulttext);
                resultT.setVisibility(View.VISIBLE);

                if(username.isEmpty() || pwd.isEmpty()){
                    String loginresult = "username and pwd cannot be NULL";
                    resultT.setText(loginresult);
                }
                else{
                    // set MsgTransfer object
                    Customer customer = new Customer(username,pwd);
                    // set socket
                    //Socket client_socket = InetAddress.getLocalHost();

                    MSG.setCmd("Login");
                    MSG.setData(customer);
                    System.out.println("");
                    Log.i("message", MSG.getCmd());

                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(MSG);

                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        MSG = (MsgTransfer) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    boolean flag = MSG.getFlag();
                    String result = MSG.getResult();
                    resultT.setText(result);

                    if(flag){
                        Intent intent = new Intent(context, MenuActivity.class);

                        // transfer data to next activity
                        intent.putExtra("user",username);
                        intent.putExtra("pwd",pwd);
                        intent.putExtra("MSG",MSG);

                        startActivity(intent);
                    }

                }
            }

        });

        register_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                usernameT = (EditText)findViewById(R.id.usertxt);
                pwdT=(EditText)findViewById(R.id.pwdtxt);
                String username=usernameT.getText().toString().trim();
                String pwd=pwdT.getText().toString().trim();


                resultT = (TextView) findViewById(R.id.resulttext);
                resultT.setVisibility(View.VISIBLE);

                if(username.isEmpty() || pwd.isEmpty()) {
                    String failedresult = "username and pwd cannot be NULL";
                    resultT.setText(failedresult);
                } else{
                    // set MsgTransfer object
                    Customer customer = new Customer(username,pwd);
                    // set socket
                    //Socket client_socket = InetAddress.getLocalHost();

                    MSG.setCmd("Register");
                    MSG.setData(customer);

                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(MSG);

                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        MSG = (MsgTransfer) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    boolean flag = MSG.getFlag();
                    String result = MSG.getResult();
                    resultT.setText(result);
                }
            }

        });

    }
}
