package com.iot.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.iot.business.IotConstants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * creates mongo client for creating and accessing data store
 * @author abhishekashwathnarayanvenkat
 *
 */
public class DbConfig {
	private static DbConfig instance = null;
	private final Datastore datastore;

	private DbConfig() {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().socketTimeout(60000).connectTimeout(15000)
				.maxConnectionIdleTime(600000).build();
		MongoClient mongoClient = null;

		try {
			// Creates a single connection to the database
			mongoClient = new MongoClient(new ServerAddress(IotConstants.DBHOST, IotConstants.DBPORT),
					mongoClientOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// creates datastore with the provided database name
		datastore = new Morphia().createDatastore(mongoClient, IotConstants.DBNAME);
		datastore.ensureIndexes();
		datastore.ensureCaps();
		System.out.println("Datastore created!!");

	}

	/**
	 * @return an instance of this class
	 */
	public static DbConfig getInstance() {
		if(instance ==null)
			instance = new DbConfig();
		return instance;
	}

	/**
	 * @return a single connection object to the database
	 */
	public synchronized Datastore getDatastore() {
		// System.out.println("Datastore created!!");
		return datastore;
	}
}
