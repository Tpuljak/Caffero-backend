package app.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shop.class)
public abstract class Shop_ {

	public static volatile SetAttribute<Shop, Session> sessions;
	public static volatile SingularAttribute<Shop, Integer> numberOfTables;
	public static volatile SingularAttribute<Shop, String> name;
	public static volatile SetAttribute<Shop, Staff> staff;
	public static volatile SingularAttribute<Shop, Integer> id;

	public static final String SESSIONS = "sessions";
	public static final String NUMBER_OF_TABLES = "numberOfTables";
	public static final String NAME = "name";
	public static final String STAFF = "staff";
	public static final String ID = "id";

}

