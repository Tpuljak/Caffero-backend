package app.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "session")
@Table(name = "sessions")
public class Session {

    public Session() {}

    public Session(QRCode qrCode) {
        this.qrCode = qrCode;
        this.shop = qrCode.getShop();
        this.orderSum = 0.0;
        this.isActive = true;
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToOne
    private QRCode qrCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Shop shop;

    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> connectedUsers = new HashSet<>();

    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Order> ordersPlaced = new HashSet<>();

    private Double orderSum;

    public int getId() { return this.id; }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setQrCode(QRCode qrCode) {
        this.qrCode = qrCode;
    }

    public QRCode getQrCode() {
        return qrCode;
    }

    public void addUser(User user) { this.connectedUsers.add(user); }

    public void removeUser(User user) {
        this.connectedUsers.remove(user);
    }

    public Set<User> getUsers() { return this.connectedUsers; }

    public void addOrder(Order order) { this.ordersPlaced.add(order); }

    public Set<Order> getOrdersPlaced() { return this.ordersPlaced; }

    public Double getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Double orderSum) {
        this.orderSum = orderSum;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
