package app.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "qrcode")
@Table(name = "qrcodes")
public class QRCode {

    public QRCode() {}

    public QRCode(int table) {
        this.cafeTable = table;
    }

    public QRCode(Shop shop, int table) {
        this.cafeTable = table;
        this.shop = shop;
    }

    @Id
    @GeneratedValue
    private Integer id;

    private int cafeTable;

    @OneToOne
    @JsonIgnore
    private Session session;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Shop shop;

    public int getCafeTable() {
        return cafeTable;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
