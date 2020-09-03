/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escuelaing.Servidor;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.text.Document;




/**
 *
 * @author diego
 */
public class DataBase {
    
    private MongoClientURI uri = new MongoClientURI(
    "mongodb+srv://mongodb:MongoDB29@cluster0.cgjvc.mongodb.net/Temperatures?retryWrites=true&w=majority");
    private MongoClient mongoCl ;
    private DB database;
    private ArrayList<Table> tables = new ArrayList<Table>();;
    
    public  void DataBase() {
        mongoCl = new MongoClient(uri);
        database = mongoCl.getDB("Cluster0");
        dropColletion("Temperatures");
        createTable("Temperatures");
        insert("Temperatures", "08/08/2020", "2.7");
        insert("Temperatures", "09/08/2020", "2.8");
        insert("Temperatures", "10/08/2020", "2.3");
        insert("Temperatures", "11/08/2020", "3.0");
        insert("Temperatures", "12/08/2020", "3.1");
        insert("Temperatures", "13/08/2020", "3.2");
        if (mongoCl!=null){
            System.out.println("OK");
        }
        else{
            System.out.println("FUCK");
        }
    }
    
    public void createTable(String name){
        database.createCollection("name", null);
        System.out.println("collectio created");
	database.getCollectionNames().forEach(System.out::println);
    }
    
    
    public void insert(String name,String date,String temp){
        DBCollection collection = database.getCollection(name);
	BasicDBObject document = new BasicDBObject();
	document.put("Date", date);
	document.put("Temperature", temp);
	collection.insert(document);
        System.out.println(name+" "+date+" "+temp);
    }
    public ArrayList<Table> getData(String name){
        String[][] Data;
        DBCollection collection = database.getCollection(name);
	Iterator<DBObject> cursor = collection.find().iterator();
        DBObject it ;
        while (cursor.hasNext()){
            it = cursor.next();
            tables.add(new Table(it.toString().split(",")[1].split(":")[1],it.toString().split(",")[2].split(":")[1].replace("}","")));
        }
        return tables;
    }
    
    public void  dropColletion(String name){
        DBCollection collection = database.getCollection(name);
        collection.drop();
    }
    
    public void Close(){
        mongoCl.close();
    }
}
