package dabase.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Session.class)
public abstract class Session_ {

	public static volatile SingularAttribute<Session, QRCode> qrCode;
	public static volatile SingularAttribute<Session, Integer> id;

	public static final String QR_CODE = "qrCode";
	public static final String ID = "id";

}

