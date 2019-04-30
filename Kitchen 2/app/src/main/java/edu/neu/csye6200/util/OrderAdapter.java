package edu.neu.csye6200.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import edu.neu.csye6200.activity.KitchenActivity;
import edu.neu.csye6200.activity.R;
import edu.neu.csye6200.entity.Customer;
import edu.neu.csye6200.entity.Item;
import edu.neu.csye6200.entity.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    private Context context;
    private Customer customer;
    private ArrayList<Customer>  list;
    private ButtonInterface buttonInterface;

    public OrderAdapter(KitchenActivity context, ArrayList<Customer> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemCustomerName;
        TextView tv_itemBurgerQ;
        TextView tv_itemChickenQ;
        TextView tv_itemFrenchFriesQ;
        TextView tv_itemOnionRingQ;

        ImageButton buttonConfirm;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_itemCustomerName = itemView.findViewById(R.id.itemCustomerName);
            tv_itemBurgerQ =  itemView.findViewById(R.id.itemBurgerQ);
            tv_itemChickenQ = itemView.findViewById(R.id.itemChickenQ);
            tv_itemFrenchFriesQ = itemView.findViewById(R.id.itemFrenchFriesQ);
            tv_itemOnionRingQ = itemView.findViewById(R.id.itemOnionRingQ);

            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);

        }
    }


    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    public interface ButtonInterface{
        void onclick(View view, int position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, @NonNull final int i) {
       try{
           int j =getItemCount();
           int qb = 0;
           int cq = 0;
           int fq = 0;
           int oq = 0;

           holder.tv_itemCustomerName.setText(list.get(i).getUsername());

//       ArrayList<Order> orderList = list.get(i).getOrderList();
//        holder.tv_itemBurgerQ.setText("1");
//        holder.tv_itemChickenQ.setText("2");
//        holder.tv_itemFrenchFriesQ.setText("4");
//        holder.tv_itemOnionRingQ.setText("2");
           for(int m = 0; m < j; m++){
               ArrayList<Order> orderlisti = list.get(m).getOrderList();
               Order order = orderlisti.get(0);
               Map<Item,Integer> map = order.getItem_QuantityMap();



               Iterator<Item> it = map.keySet().iterator();
               while (it.hasNext()){
                   Item item = it.next();
                   if("Burger".equals(item.getName())){
                       qb = map.get(item);
                   }
                   else if("Chickens".equals(item.getName())){
                       cq =  map.get(item);
                   }
                   else if ("French Fries".equals(item.getName())){
                       fq =  map.get(item);
                   }else if("Onion Rings".equals(item.getName())){
                       oq =  map.get(item);
                   }
               }
               holder.tv_itemBurgerQ.setText(String.valueOf(qb));
               holder.tv_itemChickenQ.setText(String.valueOf(cq));
               holder.tv_itemFrenchFriesQ.setText(String.valueOf(fq));
               holder.tv_itemOnionRingQ.setText(String.valueOf(oq));
           }


//        holder.tv_itemBurgerQ.setText(list.get(i).getItem_QuantityMap().get("Burger"));
//        holder.tv_itemChickenQ.setText(list.get(i).getItem_QuantityMap().get("Chickens"));
//        holder.tv_itemFrenchFriesQ.setText(list.get(i).getItem_QuantityMap().get("French Fries"));
//        holder.tv_itemOnionRingQ.setText(list.get(i).getItem_QuantityMap().get("Onion Rings"));
//
//         holder.buttonConfirm.setText(list.get(i));
           holder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(buttonInterface!=null) {

                       buttonInterface.onclick(v, i);
                   }

               }
           });
       }
        catch (Exception e){
           e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}