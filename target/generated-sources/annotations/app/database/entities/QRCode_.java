package app.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(QRCode.class)
public abstract class QRCode_ {

	public static volatile SingularAttribute<QRCode, Session> session;
	public static volatile SingularAttribute<QRCode, Integer> cafeTable;
	public static volatile SingularAttribute<QRCode, Integer> id;

	public static final String SESSION = "session";
	public static final String CAFE_TABLE = "cafeTable";
	public static final String ID = "id";

}

