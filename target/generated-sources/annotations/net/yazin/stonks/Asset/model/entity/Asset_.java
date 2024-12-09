package net.yazin.stonks.Asset.model.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Asset.class)
public abstract class Asset_ {

	public static volatile SingularAttribute<Asset, Double> size;
	public static volatile SingularAttribute<Asset, Integer> assetId;
	public static volatile SingularAttribute<Asset, String> customerId;
	public static volatile SingularAttribute<Asset, String> assetName;
	public static volatile SingularAttribute<Asset, Double> usableSize;

	public static final String SIZE = "size";
	public static final String ASSET_ID = "assetId";
	public static final String CUSTOMER_ID = "customerId";
	public static final String ASSET_NAME = "assetName";
	public static final String USABLE_SIZE = "usableSize";

}

