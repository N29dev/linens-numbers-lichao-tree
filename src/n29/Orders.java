package n29;
import java.util.ArrayList;

import static java.lang.Long.min;

public class Orders {
    private Customer customer;
    private ArrayList<Line> orders;
    private long happines;
    private long price;
    private int id;
    public Orders(Customer customer,int id){
        this.customer=customer;
        orders = new ArrayList<>();
        happines=0;
        price=0;
        setId(id);
    }
    public boolean setId(int id){
        if(id<0)return false;
        this.id=id;
        return true;
    }
    public int getId(){
        return this.id;
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
    public ArrayList<Line> getOrders(){
        return this.orders;
    }
    public void addLine(Line line){
        this.orders.add(line);
        this.price+=line.getCost();
        this.happines+=min(line.f(this.customer.getL()),line.f(this.customer.getR()));
    }
    public void delLine(Line line){
        this.orders.remove(line);
        this.price-=line.getCost();
        this.happines-=min(line.f(this.customer.getL()),line.f(this.customer.getR()));
    }
    public String getInfo(){
        String res="Customer info <<<<<<<<\n" + customer.getInfo() + "\n";
        int tin=0;
        for(Line line:orders){
            tin+=1;
            res+="Order #" + tin + '\n';
            res+=line.getInfo() +'\n';
        }
        return res;
    }

}
