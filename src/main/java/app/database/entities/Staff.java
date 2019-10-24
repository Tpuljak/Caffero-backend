package app.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import app.database.common.StaffType;

import javax.persistence.*;

@Entity(name = "staff")
@Table(name = "staff")
public class Staff {

    public Staff() {}

    public Staff(String fullName, String email, StaffType type) {
        this.fullName = fullName;
        this.email = email;
        this.staffType = type;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StaffType staffType;

    private String fullName;

    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Shop shop;

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }
}
