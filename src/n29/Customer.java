package n29;
public class Customer {
    private String name; // customer name
    private int l;
    private int r; // range of favorite integers [l,r]
    private long money; // money
    private int id;
    public Customer(String name,int l,int r,long money,int id){
        setName(name);
        setLR(l,r);
        setMoney(money);
        setId(id);
    }
    public boolean setId(int id){
        if(id<0){
            return false;
        }
        this.id=id;
        return true;
    }
    public int getId(){
        return this.id;
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
    public boolean setLR(int l,int r){
        if(l>r)return false;
        this.l=l;
        this.r=r;
        return true;
    }
    public boolean setName(String name){
        if(name==null || name.isBlank()){
            return false;
        }
        this.name=name;
        return true;
    }
    public boolean setMoney(long money){
        if(money<0)return false;
        this.money=money;
        return true;
    }
    public String getInfo(){
        return "Customer id:" + getId() + "\nCustomer name:" + getName() + "\nCustomer range:[" + getL() + "," + getR() + "]\n" + "Customer balance:" +getMoney() ;
    }
}