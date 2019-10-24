package app.database.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "shop")
@Table(name = "shops")
public class Shop {

    public Shop() {}

    public Shop(String name, int numberOfTables) {
        this.name = name;
        this.numberOfTables = numberOfTables;
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private int numberOfTables;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<QRCode> qrCodes = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Staff> staff = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public void addStaffMember(Staff staffMember) {
        this.staff.add(staffMember);
    }

    public void addQrCode(QRCode qrCode) {
        this.qrCodes.add(qrCode);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item itemToRemove) {
        this.items.remove(itemToRemove);
    }

    public Set<Staff> getStaffMembers() {
        return this.staff;
    }
}
