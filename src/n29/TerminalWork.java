package n29;

import java.util.ArrayList;

public class TerminalWork {



    private String[] options={
            "WRITE CORRESPONDING NUMBER",
            "0.EXIT TERMINAL",
            "1.ADD NEW CUSTOMER",
            "2.ADD NEW LINE",
            "3.ADD NEW ORDER",
            "4.LIST OF CUSTOMERS",
            "5.LIST OF LINES",
            "6.LIST OF ORDERS",
            "7.GET CUSTOMER INFO",
            "8.GET LINE INFO",
            "9.GET ORDER INFO",
    };
    private ArrayList<Customer> CustomerList;
    private ArrayList<Line> LineList;
    private ArrayList<Orders> OrdersList;
    public TerminalWork(){
        CustomerList=new ArrayList<>();
        LineList=new ArrayList<>();
        OrdersList=new ArrayList<>();
    }
    public void StartInfo(){
        for(String option:options){
            System.out.println(option);
        }
    }

    public String AddCustomer(Customer x){
        Customer c=new Customer("x",1,1,1,1);
        if(!c.setName(x.getName())){
            return "Failed to find name";
        }
        if(!c.setLR(x.getL(),x.getR())){
            return "Failed, invalid range (L>R)";
        }
        if(!c.setMoney(x.getMoney())){
            return "Failed, money can't be negative";
        }
        if(!c.setId(x.getId())){
            return "Failed, id can't be negative";
        }
        if(checkIdCustomer(x.getId())){
            return "Failed, duplicate Customer id";
        }
        return "Customer successfully added";
    }
    public boolean checkIdCustomer(int id){
        for(Customer customer:CustomerList){
            if(customer.getId()==id){
                return true;
            }
        }
        return false;
    }
    public String AddLine(Line x){
        Line c=new Line("1",1,1,1,1);
        if(!c.setName(x.getName())){
            return "Failed to find name";
        }
        if(!c.setK(x.getK())){
            return "Failed, getK() function corrupted";
        }
        if(!c.setB(x.getB())){
            return "Failed, getB() function corrupted";
        }
        if(!c.setCost(x.getCost())){
            return "Failed, cost can't be negative";
        }
        if(!c.setId(x.getId())){
            return "Failed, id can't be negative";
        }
        if(checkIdLine(x.getId())){
            return "Failed, duplicate Line id";
        }
        return "Line successfully added";
    }
    public boolean checkIdLine(int id){
        for(Line line:LineList){
            if(line.getId()==id){
                return true;
            }
        }
        return false;
    }
    public Customer GetCustomerById(int id){
        for(Customer customer:CustomerList){
            if(customer.getId()==id){
                return customer;
            }
        }
        return null;
    }
    public Line GetLineById(int id){
        for(Line line:LineList){
            if(line.getId()==id){
                return line;
            }
        }
        return null;
    }
    public String AddOrder(int OrderID,int CustomerId,ArrayList<Integer> LineId){
        if(checkIdOrders(OrderID)){
            return "Failed, duplicate Order id";
        }
        if(!checkIdCustomer(CustomerId)){
            return "Failed to find Customer with id " + CustomerId;
        }
        for(int lineid:LineId){
            if(!checkIdLine(lineid)){
                return "Failed to find Line with id " + lineid;
            }
        }
        Customer cus=GetCustomerById(CustomerId);
        Orders x=new Orders(cus,OrderID);
        for(int lineid:LineId) {
            x.addLine(GetLineById(lineid));
        }
        OrdersList.add(x);
        return "Orders successfully added";
    }
    public boolean checkIdOrders(int id){
        for(Orders orders:OrdersList){
            if(orders.getId()==id){
                return true;
            }
        }
        return false;
    }
    public Orders GetOrdersById(int id){
        for(Orders orders:OrdersList){
            if(orders.getId()==id){
                return orders;
            }
        }
        return null;
    }
    public void GetCustomers(){
        System.out.println("Number of Customers:" + CustomerList.size());
        for(Customer customer:CustomerList){
            System.out.println(customer.getInfo());
        }
    }
    public void GetLines(){
        System.out.println("Number of Lines:" + LineList.size());
        for(Line line:LineList){
            System.out.println(line.getInfo());
        }
    }
    public void GetOrders(){
        System.out.println("Number of Orders:" + OrdersList.size());
        for(Orders orders:OrdersList){
            System.out.println(orders.getInfo());
        }
    }
    public String GetCustomerInfoById(int id){
        if(!checkIdCustomer(id)){
            return "Failed to find Customer with id " + id;
        }
        return GetCustomerById(id).getInfo();
    }
    public String GetLineInfoById(int id){
        if(!checkIdLine(id)){
            return "Failed to find Line with id " + id;
        }
        return GetLineById(id).getInfo();
    }
    public String GetOrdersInfoById(int id){
        if(!checkIdOrders(id)){
            return "Failed to find Orders with id " + id;
        }
        return GetOrdersById(id).getInfo();
    }
}
