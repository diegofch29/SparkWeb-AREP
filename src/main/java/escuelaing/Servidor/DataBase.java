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
    
    private final MongoClientURI uri;
    private final MongoClient mongoCl ;
    private final DB database;
    private final ArrayList<Table> tables = new ArrayList<>();;
    
    public DataBase() {
        this.uri = new MongoClientURI("mongodb+srv://mongodb:MongoDB29@cluster0.cgjvc.mongodb.net/Temperatures?retryWrites=true&w=majority");
        mongoCl = new MongoClient(uri);
        database = mongoCl.getDB("Cluster0");
        if (mongoCl!=null){
            System.out.println("OK");
        }
        else{
            System.out.println("FUCK");
        }
    }
    
    public void createTable(String name){
        database.createCollection("name", null);
        System.out.println("collection created");
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
