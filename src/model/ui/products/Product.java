package model.ui.products;


public interface Product {

    double getPrice();

    void setPrice(double price);

    String getBrand();

    void setBrand(String brand);

    String getName();

    void setName(String name);

    int getId();

    void setId(int id);

    String getAdditionalInfo();

    int getQuantity();

    void setQuantity(int quantity);

    String getProductKey();

    void updateProductKey();

    String getColor();

    void setColor(String color);

    void setAdditionalInfo(String info);
}
