package n29;
import java.util.ArrayList;

import static java.lang.Long.max;
import static java.lang.Long.min;

public class queries {
    private Customer customer;
    private ArrayList<lines> orders;
    private long happines;
    private long price;
    public queries(Customer customer){
        this.customer=customer;
        orders = new ArrayList<>();
        happines=0;
        price=0;
    }
    public Customer getCustomer(){
        return this.customer;
    }
    public long getTotalPrice(){
        return this.price;
    }
    public long getTotalPriceWithPayment(){
        long res=getTotalPrice();
        if(res-customer.getMoney()<=0){
            return 0;
        }
        else {
            return res - customer.getMoney();
        }
    }
    public long getHappines(){
        return this.happines;
    }
    public ArrayList<lines> getOrders(){
        return this.orders;
    }
    public void addLine(lines line){
        this.orders.add(line);
        this.price+=line.getCost();
        this.happines+=min(line.f(this.customer.getL()),line.f(this.customer.getR()));
    }
    public void delLine(lines line){
        this.orders.remove(line);
        this.price-=line.getCost();
        this.happines-=min(line.f(this.customer.getL()),line.f(this.customer.getR()));
    }
    public String getQueryInfo(){
        String res="Customer info <<<<<<<<\n" + customer.getInfo() + "\n";
        int tin=0;
        for(lines line:orders){
            tin+=1;
            res+="Order #" + tin + '\n';
            res+=line.getInfo() +'\n';
        }
        return res;
    }

}
