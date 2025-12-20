package n29;
public class Main {
    public static void main(String[] args) {
        lines line1=new lines("increasing",10,5,25);
        lines line2=new lines("decreasing", -10,25,50);
        lines line3=new lines("constant", 0,100,200);
        Customer customer1=new Customer("Wansur",157,2441,99999);
        queries queries1=new queries(customer1);
        queries1.addLine(line1);
        queries1.addLine(line2);
        System.out.println(queries1.getTotalPrice());
        System.out.println(queries1.getTotalPriceWithPayment());
        System.out.println(queries1.getHappines());
        System.out.println(customer1.getInfo());
        System.out.println(line1.getInfo());
        System.out.println(queries1.getQueryInfo());
    }
}
//
