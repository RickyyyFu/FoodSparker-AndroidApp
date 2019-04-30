package edu.neu.csye6200.util;

import android.app.Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ApplicationUtil extends Application {
    private Socket socket;
    private MsgTransfer MSG;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setSocket(new Socket("10.41.142.189", 8888));
                    setMSG(new MsgTransfer());
//                    setOutputStream(new ObjectOutputStream(socket.getOutputStream()));
//                    setInputStream(new ObjectInputStream(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    public void setMSG(MsgTransfer MSG){
        this.MSG = MSG;
    }

    public MsgTransfer getMSG(){
        return MSG;
    }

    public void setOutputStream(ObjectOutputStream oos){
        this.oos = oos;
    }

    public ObjectOutputStream getOutputStream(){
        return oos;
    }

    public void setInputStream(ObjectInputStream ois){
        this.ois = ois;
    }

    public ObjectInputStream getInputStream(){
        return ois;
    }
}
