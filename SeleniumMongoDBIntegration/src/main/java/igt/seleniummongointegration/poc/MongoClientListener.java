package igt.seleniummongointegration.poc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoClientListener implements ITestListener {
	
	MongoCollection<Document> datacollection;
	MongoClient mongoclient;


	public void onStart(ISuite suite) {		
		/*
		 * We are not using this as of now.
		 */
	}



	public void onFinish(ISuite arg0) {	
		/*
		 * We are not using this as of now.
		 */
	}


	public void onTestStart(ITestResult result) {
		/*
		 * We are not using this as of now.
		 */
	}



	public void onTestSuccess(ITestResult result) {
		String methodname = result.getMethod().getMethodName();
		String classname = result.getMethod().getRealClass().getName();
		Integer priority = result.getMethod().getPriority();
		String desc = result.getMethod().getDescription();
		
		Document dl = new Document();
		dl.append("ClassName", classname);
		dl.append("MethodName", methodname);
		dl.append("Description", desc);
		dl.append("Priority", priority);
		dl.append("Status", "PASS");
		
		List<Document> list = new ArrayList<Document>();
		list.add(dl);
		datacollection.insertMany(list);

	}


	public void onTestFailure(ITestResult result) {		
		String methodname = result.getMethod().getMethodName();
		String classname = result.getMethod().getRealClass().getName();
		Integer priority = result.getMethod().getPriority();
		String desc = result.getMethod().getDescription();
		
		Document dl = new Document();
		dl.append("ClassName", classname);
		dl.append("MethodName", methodname);
		dl.append("Description", desc);
		dl.append("Priority", priority);
		dl.append("Status", "FAIL");
		
		List<Document> list = new ArrayList<Document>();
		list.add(dl);
		datacollection.insertMany(list);
	}



	public void onTestSkipped(ITestResult result) {
		String methodname = result.getMethod().getMethodName();
		String classname = result.getMethod().getRealClass().getName();
		Integer priority = result.getMethod().getPriority();
		String desc = result.getMethod().getDescription();
		
		Document dl = new Document();
		dl.append("ClassName", classname);
		dl.append("MethodName", methodname);
		dl.append("Description", desc);
		dl.append("Priority", priority);
		dl.append("Status", "SKIP");
		
		List<Document> list = new ArrayList<Document>();
		list.add(dl);
		datacollection.insertMany(list);

	}


	public void onFinish(ITestContext arg0) {
		mongoclient.close();

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		/*
		 * We are not using this as of now.
		 */
	}



	public void onStart(ITestContext arg0) {
		Logger mongologger = Logger.getLogger("org.mongodb.driver"); //Creating logger for MongoDB
		 mongoclient = MongoClients.create("mongodb://localhost:27017"); //Connecting with MongoDB on local host
		mongologger.info("Connection created succesfully");
		//Get mongo db
		MongoDatabase database = mongoclient.getDatabase("evisadb"); //Getting database from mongo DB
		//Create collection
		datacollection = database.getCollection("SelniumMongoDB"); //If their is no such collection then it will create it
		System.out.println("Collection is created");
	}

}
