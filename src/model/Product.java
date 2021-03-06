package model;

import java.util.Objects;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String model;
    private String buyer;

    @Override
    public String toString() {
        return "№"+id.toString()+" "+name+" "+price.toString()+"грн. "+model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(model, product.model) &&
                Objects.equals(buyer, product.buyer);
    }

    public Product(){}

    public Product(Integer id, String name, Double price, String model, String buyer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.model = model;
        this.buyer = buyer;
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

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
