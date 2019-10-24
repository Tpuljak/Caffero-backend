package app.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "item")
@Table(name = "items")
public class Item {
    public Item() {}

    public Item(String name, Double price, User createdBy, Shop shop) {
        this.name = name;
        this.createdBy = createdBy;
        this.price = price;
        this.shop = shop;
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Shop shop;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Shop getShop() {
        return this.shop;
    }

    public Double getPrice() {
        return this.price;
    }
}
