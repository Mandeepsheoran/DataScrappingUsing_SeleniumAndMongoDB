package igt.selenium4.poc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebScrngSeleniumAndMongoDB {
	WebDriver driver;
	MongoCollection<Document> datacollection;
	
	
	@BeforeSuite
	public void connectmongodb() {
		Logger mongologger = Logger.getLogger("org.mongodb.driver"); //Creating logger for MongoDB
		MongoClient mongoclient = MongoClients.create("mongodb://localhost:27017"); //Connecting with MongoDB on local host
		mongologger.info("Connection created succesfully");
		//Get mongo db
		MongoDatabase database = mongoclient.getDatabase("evisadb"); //Getting database from mongo DB
		//Create collection
		datacollection = database.getCollection("SelniumMongoDB"); //If their is no such collection then it will create it
		System.out.println("Collection is created");
	}
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--headless");
		driver = (WebDriver) new ChromeDriver(co);
		System.out.println("Driver is initilized and will run in headless mode");
	}
	
	@DataProvider
	public Object[][] getData(){
		return new Object[][] {
			{"https://evisa.rop.gov.om"},
			//{"https://www.flipkart.com"}
		};
	}
	
	@Test(dataProvider="getData")
	public void seleniumDataScrappingSsingMongo(String appurl) {
		driver.get(appurl);
		//Storing URL and Web page title in string
		String url = driver.getCurrentUrl();
		String title = driver.getTitle();
		
		int totalmenuicons = driver.findElements(By.className("dropbtn")).size();
		String menuname = driver.findElement(By.className("imgclass")).getText();
		System.out.println("menu name is:"+ menuname);
		System.out.println("URL is : "+ url + "and" + "Title is :"+ title);
	    //Creating a document and adding these two string value in it
		Document dl = new Document();
		dl.append("ApplicationURL", url);
		dl.append("Application Page Title", title);
		dl.append("Total menu Options", totalmenuicons);
		dl.append("Menu Name", menuname);
		System.out.println("Document is created and string value are stored in document");
		
		//Coverting document in list as mongodb insert method accept list as input
		List<Document> doclist = new ArrayList<Document>();
		doclist.add(dl);
		System.out.println("Document is passed in list");
		
		//Inserting data in collection created in @BeforeSuite
		datacollection.insertMany(doclist);
		System.out.println("Scrapped data is pushed now in Mongo DB in concerned database and collection");
		
	}
	 public  void tearDown() {
		 driver.quit();
	 }

}
