package model;

import java.util.Objects;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String model;

    @Override
    public String toString() {
        return "№"+id.toString()+" "+name+" "+price.toString()+"грн. "+model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, model);
    }

    public Product(){}

    public Product(Integer id, String name, Double price, String model) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
