package model.ui;

import model.sql.Shop;
import model.ui.products.Car;

/**
 * Created by Ivan on 14.12.2015.
 */
public class SetupForDemo {

    private Shop shop;

    public SetupForDemo(Shop shop) {
        this.shop = shop;
    }

    public void fillDataBase() {
        Car c = new Car("X1");
        c.setBrand("BMW");
        c.setPrice(26000);
        c.setEngineVollume(1.8);
        c.setColor("white");
        c.setQuantity(15);
        shop.addProduct(c);

        c = new Car("Panamera");
        c.setBrand("Porsche");
        c.setPrice(98000);
        c.setEngineVollume(2.8);
        c.setColor("brown");
        shop.addProduct(c);

        c = new Car("X6");
        c.setBrand("BMW");
        c.setPrice(35000);
        c.setEngineVollume(3.2);
        c.setColor("silver");
        shop.addProduct(c);

        c = new Car("X3");
        c.setBrand("BMW");
        c.setPrice(28500);
        c.setEngineVollume(2.0);
        c.setColor("brown");
        shop.addProduct(c);

        c = new Car("X5");
        c.setBrand("BMW");
        c.setPrice(31000);
        c.setEngineVollume(2.5);
        c.setColor("black");
        shop.addProduct(c);


        c = new Car("X6");
        c.setBrand("BMW");
        c.setPrice(35000);
        c.setEngineVollume(3.2);
        c.setColor("silver");
        shop.addProduct(c);

        c = new Car("Civic");
        c.setBrand("Honda");
        c.setPrice(18000);
        c.setEngineVollume(1.8);
        c.setColor("blue");
        shop.addProduct(c);

        c = new Car("Civic");
        c.setBrand("Honda");
        c.setPrice(18000);
        c.setEngineVollume(1.8);
        c.setColor("yellow");
        shop.addProduct(c);

        c = new Car("Accord");
        c.setBrand("Honda");
        c.setPrice(26500);
        c.setEngineVollume(2.0);
        c.setColor("black");
        shop.addProduct(c);

        c = new Car("Accord");
        c.setBrand("Honda");
        c.setPrice(26500);
        c.setEngineVollume(2.0);
        c.setColor("red");
        shop.addProduct(c);

        c = new Car("CRV");
        c.setBrand("Honda");
        c.setPrice(35500);
        c.setEngineVollume(2.2);
        c.setColor("white");
        shop.addProduct(c);

        c = new Car("CRV");
        c.setBrand("Honda");
        c.setPrice(35500);
        c.setEngineVollume(2.2);
        c.setColor("blue");
        shop.addProduct(c);

        c = new Car("CRV");
        c.setBrand("Honda");
        c.setPrice(35500);
        c.setEngineVollume(2.2);
        c.setColor("cherry");
        shop.addProduct(c);

        c = new Car("CRV");
        c.setBrand("Honda");
        c.setPrice(35500);
        c.setEngineVollume(2.2);
        c.setColor("black");
        shop.addProduct(c);

        c = new Car("Astra");
        c.setBrand("Opel");
        c.setPrice(15000);
        c.setEngineVollume(1.3);
        c.setColor("blue");
        shop.addProduct(c);
    }
}
