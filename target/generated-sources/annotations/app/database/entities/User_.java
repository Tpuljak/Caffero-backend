package app.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Session> activeSession;
	public static volatile SingularAttribute<User, String> fullName;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> email;

	public static final String ACTIVE_SESSION = "activeSession";
	public static final String FULL_NAME = "fullName";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

