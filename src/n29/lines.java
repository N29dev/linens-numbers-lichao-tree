package n29;
public class lines {
    private String name;
    private int k;
    private int b;
    private int cost;
    public lines(String name,int k,int b,int cost){
        this.name=name;
        this.k=k;
        this.b=b;
        this.cost=cost;
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
    public void setK(int k){
        this.k=k;
    }
    public void setB(int b){
        this.b=b;
    }
    public void setCost(int cost){
        this.cost=cost;
    }
    public long f(int x){
        return (long) k *x+b;
    }

}
