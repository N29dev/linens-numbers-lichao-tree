package n29;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    Scanner cin=new Scanner(System.in);

    public void main(String[] args){
        TerminalWork terminal=new TerminalWork();

        while(true){
            System.out.flush();
            terminal.StartInfo();
            int t;
            t=cin.nextInt();cin.nextLine();
            if(t==0){
                break;
            }
            if(t==1){
                String name;
                System.out.println("Customer name:");
                name=cin.nextLine();
                int l,r;
                System.out.println("Customer favorite number range (l,r):");
                l=cin.nextInt();
                r=cin.nextInt();cin.nextLine();
                long money;
                System.out.println("Customer money:");
                money=cin.nextLong();cin.nextLine();
                int id;
                System.out.println("Customer id:");
                id=cin.nextInt();cin.nextLine();
                Customer x=new Customer(name,l,r,money,id);
                System.out.println(terminal.AddCustomer(x));
            }
            if(t==2){
                String name;
                System.out.println("Line name:");
                name=cin.nextLine();
                int k,b;
                System.out.println("Line Slope and Height (k,b):");
                k=cin.nextInt();
                b=cin.nextInt();cin.nextLine();
                int cost;
                System.out.println("Line cost:");
                cost=cin.nextInt();cin.nextLine();
                int id;
                System.out.println("Customer id:");
                id=cin.nextInt();cin.nextLine();
                Line x=new Line(name,k,b,cost,id);
                System.out.println(terminal.AddLine(x));
            }
            if(t==3){
                int OrderId;
                System.out.println("Order id:");
                OrderId=cin.nextInt();cin.nextLine();
                int CustomerId;
                System.out.println("Customer id:");
                CustomerId=cin.nextInt();cin.nextLine();
                ArrayList<Integer>LineId=new ArrayList<>();
                while(true){
                    int lineId;
                    System.out.println("Line id or -1 for end:");
                    lineId=cin.nextInt();cin.nextLine();
                    if(lineId==-1){
                        break;
                    }
                    LineId.add(lineId);
                }
                System.out.println(terminal.AddOrder(OrderId,CustomerId,LineId));
            }
            if(t==4){
                terminal.GetCustomers();
            }
            if(t==5){
                terminal.GetLines();
            }
            if(t==6){
                terminal.GetOrders();
            }
            if(t==7){
                int id;
                System.out.println("Customer id:");
                id=cin.nextInt();cin.nextLine();
                System.out.println(terminal.GetCustomerInfoById(id));
            }
            if(t==8){
                int id;
                System.out.println("Line id:");
                id=cin.nextInt();cin.nextLine();
                System.out.println(terminal.GetLineInfoById(id));
            }
            if(t==9){
                int id;
                System.out.println("Orders id:");
                id=cin.nextInt();cin.nextLine();
                System.out.println(terminal.GetOrdersInfoById(id));
            }
        }
    }
}
