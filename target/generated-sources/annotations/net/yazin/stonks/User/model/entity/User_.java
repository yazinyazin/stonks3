package net.yazin.stonks.User.model.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;
import net.yazin.stonks.Common.model.enums.UserRole;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> userIdOnAuthServer;
	public static volatile SingularAttribute<User, Long> dateJoined;
	public static volatile SingularAttribute<User, String> fullName;
	public static volatile SingularAttribute<User, UserRole> userRole;
	public static volatile SingularAttribute<User, Integer> userId;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String USER_ID_ON_AUTH_SERVER = "userIdOnAuthServer";
	public static final String DATE_JOINED = "dateJoined";
	public static final String FULL_NAME = "fullName";
	public static final String USER_ROLE = "userRole";
	public static final String USER_ID = "userId";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

