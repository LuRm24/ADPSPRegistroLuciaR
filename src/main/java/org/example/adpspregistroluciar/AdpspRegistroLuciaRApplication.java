package org.example.adpspregistroluciar;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static org.example.adpspregistroluciar.interfaces.MainMenu.mainMenu;

public class AdpspRegistroLuciaRApplication {

    public static void main(String[] args) {
        mainMenu();
        //Conexion a la BD:
        String url = "mongodb://root:Sandia4you@localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(url);) {
            System.out.println("Conectado a la BD :-)");
            MongoDatabase mongoDatabase = mongoClient.getDatabase("appregistro");
            MongoCollection<Document> users = mongoDatabase.getCollection("users");

        }

    }
}
