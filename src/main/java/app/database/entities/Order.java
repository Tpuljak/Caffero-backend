package app.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "order")
@Table(name = "orders")
public class Order {
    public Order() {}

    public Order(Item itemOrdered, User orderedBy) {
        this.itemOrdered = itemOrdered;
        this.orderedBy = orderedBy;
        this.placedOn = new Date();
    }

    public Order(Item itemOrdered, User orderedBy, Session session) {
        this.itemOrdered = itemOrdered;
        this.orderedBy = orderedBy;
        this.session = session;
        this.placedOn = new Date();
    }

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User orderedBy;

    @OneToOne
    private Item itemOrdered;

    @Temporal(TemporalType.TIMESTAMP)
    private Date placedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }
}
