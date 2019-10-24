package app.database.entities;

import app.database.common.StaffType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Staff.class)
public abstract class Staff_ {

	public static volatile SingularAttribute<Staff, Shop> shop;
	public static volatile SingularAttribute<Staff, String> fullName;
	public static volatile SingularAttribute<Staff, Integer> id;
	public static volatile SingularAttribute<Staff, StaffType> staffType;
	public static volatile SingularAttribute<Staff, String> email;

	public static final String SHOP = "shop";
	public static final String FULL_NAME = "fullName";
	public static final String ID = "id";
	public static final String STAFF_TYPE = "staffType";
	public static final String EMAIL = "email";

}

