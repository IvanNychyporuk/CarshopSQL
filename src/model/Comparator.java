package model;


import model.ui.products.Customer;
import model.ui.products.Product;

public class Comparator implements java.util.Comparator {
//    String alphabet = "-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    @Override
    public int compare(Object o1, Object o2) {

        if (o1 instanceof Product && o2 instanceof Product) {

//            if (!((Product) o1).getBrand().equals(((Product) o2).getBrand())) {
//                return ((Product) o1).getBrand().compareTo(((Product) o2).getBrand());
//            } else {
//                return ((Product) o1).getName().compareTo(((Product) o2).getName());
//            }

            return compareProducts((Product) o1, (Product) o2);

        } else if (o1 instanceof Customer && o2 instanceof Customer) {
            return compareCustomers((Customer) o1, (Customer) o2);
        }

        return 0;
    }

    private int compareProducts(Product p1, Product p2) {

        if (p1.getBrand().equals(p2.getBrand())) {
            return p1.getName().compareTo(p2.getName());
        }

        return p1.getBrand().compareTo(p2.getBrand());

    }

    private int compareCustomers(Customer c1, Customer c2) {

        if (c1.getName().equals(c2.getName())) {
            return c1.getSecondName().compareTo(c2.getSecondName());
        }

        return c1.getName().compareTo(c2.getName());
    }
}
