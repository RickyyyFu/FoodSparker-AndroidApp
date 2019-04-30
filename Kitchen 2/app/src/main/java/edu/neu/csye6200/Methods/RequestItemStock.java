package edu.neu.csye6200.Methods;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.neu.csye6200.util.MsgTransfer;

public class RequestItemStock {
    public static MsgTransfer requestItemStock(Socket socket) {
        try {


            MsgTransfer MSG_REQUESTSTOCK = new MsgTransfer();
            MSG_REQUESTSTOCK.setCmd("updateStock");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(MSG_REQUESTSTOCK);


            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            MsgTransfer MSG = (MsgTransfer) ois.readObject();

 //           String cmd = MSG.getCmd();

//            if(cmd.equals("updateStock")){
//                obj = (ArrayList<Item>) MSG.getData();
//            }
//
//            else{
//                obj = (Customer)MSG.getData();
//            }


//            oos.flush();
//            ois.close();

            return MSG;



//            MsgTransfer MSG_REQUESTSTOCK = new MsgTransfer();
//            MSG_REQUESTSTOCK.setCmd("updateStock");
//
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(MSG_REQUESTSTOCK);
//
//
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            MsgTransfer MSG = (MsgTransfer) ois.readObject();
//
//            String cmd = MSG.getCmd();
//
//
//
//            ArrayList<Item> inventoryList = (ArrayList<Item>) MSG.getData();
////            oos.flush();
////            ois.close();
//
//            return inventoryList;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
       }

        return null;
    }

}
