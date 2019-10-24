package app.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Item> itemOrdered;
	public static volatile SingularAttribute<Order, Date> placedOn;
	public static volatile SingularAttribute<Order, User> orderedBy;
	public static volatile SingularAttribute<Order, Integer> id;

	public static final String ITEM_ORDERED = "itemOrdered";
	public static final String PLACED_ON = "placedOn";
	public static final String ORDERED_BY = "orderedBy";
	public static final String ID = "id";

}

