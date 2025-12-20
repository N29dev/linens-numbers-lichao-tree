package n29;
public class Customer {
    private String name; // customer name
    private int l;
    private int r; // range of favorite integers [l,r]
    private long money; // money
    public Customer(String name,int l,int r,long money){
        this.name=name;
        this.l=l;
        this.r=r;
        this.money=money;
    }
    public String getName(){
        return this.name;
    }
    public int getL(){
        return this.l;
    }
    public int getR(){
        return this.r;
    }
    public long getMoney(){
        return this.money;
    }
    public void setMoney(long money){
        this.money=money;
    }

}