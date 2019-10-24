package app.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public abstract class Item_ {

	public static volatile SingularAttribute<Item, User> createdBy;
	public static volatile SingularAttribute<Item, Double> price;
	public static volatile SingularAttribute<Item, String> name;
	public static volatile SingularAttribute<Item, Integer> id;

	public static final String CREATED_BY = "createdBy";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String ID = "id";

}

