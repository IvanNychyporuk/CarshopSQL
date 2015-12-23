package model.ui.products;

public class Car implements Product{

    private double price;
    private String brand;
    private String name;
    private String carKey;

    private double engineVollume;
    private String color;

    private int id;
    private int quantity;


    public Car() {
        setQuantity(1);
        updateProductKey();
    }


    public Car(String name) {
        this.name = name;
        setQuantity(1);
        updateProductKey();
    }

    public Car(String name, int price, double engineVollume, String color) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.engineVollume = engineVollume;
        setQuantity(1);
        updateProductKey();

    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void updateProductKey (){
        carKey = this.getBrand() + " " + this.getName() + " " + this.getEngineVollume() +
                " " + this.getColor();
    }

    public String getProductKey() {
        return carKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAdditionalInfo() {
        return "Engine: " + this.getEngineVollume();
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdditionalInfo(String info){
        if (info.indexOf("Engine: ") != -1) {
            engineVollume = Double.parseDouble(info.substring(info.indexOf(":") + 2));
        }
    }

    public void setEngineVollume(double engineVollume) {
        this.engineVollume = engineVollume;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public double getEngineVollume() {
        return engineVollume;
    }

    public String getColor() {
        return color;
    }

    public int hashCode() {
        int result = 25;
        result = 37 * result + name.hashCode();
        result = 37 * result + new Double(price).hashCode();
        result = 37 * result + new Double(engineVollume).hashCode();
        result = 37 * result + color.hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Car) {
            Car c = (Car) obj;
            if (this.getBrand().equals(c.getBrand()) && this.name.equals(c.getName())
                    && this.engineVollume == c.getEngineVollume()
                    && this.color.equals(c.getColor())) {
                return true;
            }
        }
        return false;
    }
}
