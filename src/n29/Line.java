package n29;
public class Line {
    private String name;
    private int k;
    private int b;
    private int cost;
    private int id;
    public Line(String name, int k, int b, int cost, int id){
        setName(name);
        setK(k);
        setB(b);
        setCost(cost);
        setId(id);
    }
    public boolean setName(String name){
        if(name==null || name.isBlank()){
            return false;
        }
        this.name=name;
        return true;
    }
    public boolean setCost(int cost){
        if(cost<0){
            return false;
        }
        this.cost=cost;
        return true;
    }
    public boolean setId(int id){
        if(id<0){
            return false;
        }
        this.id=id;
        return true;
    }
    public boolean setK(int k){
        this.k=k;
        return true;
    }
    public boolean setB(int b){
        this.b=b;
        return true;
    }
    public int getId() {
        return this.id;
    }
    public String getName(){
        return name;
    }
    public int getK(){
        return k;
    }
    public int getB(){
        return b;
    }
    public int getCost(){
        return cost;
    }
    public long f(int x){
        return (long) k *x+b;
    }
    public String getInfo(){
        return "Line Id:" + getId() + "\nLine name: " + getName() + "\nLine function: "+getK()+ "*x+" +getB() + "\nLine cost:" + getCost();
    }
}
