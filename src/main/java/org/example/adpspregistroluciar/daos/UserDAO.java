package org.example.adpspregistroluciar.daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDAO {

        private final MongoCollection<Document> usersCollection;

        public UserDAO(MongoDatabase database) {
            this.usersCollection = database.getCollection("users");
        }

        public void createUser(String username, String password) {
            if (usersCollection.find(new Document("username", username)).first() != null) {
                System.out.println("El usuario ya existe.");
                return;
            }
            byte[] passwordHash = hashPassword(password);
            Document user = new Document("username", username)
                    .append("password", new Binary(passwordHash));
            usersCollection.insertOne(user);
            System.out.println("Registro exitoso.");
        }

        public boolean validateUser(String username, String password) {
            Document user = usersCollection.find(new Document("username", username)).first();
            return user != null && MessageDigest.isEqual(hashPassword(password), ((Binary) user.get("password")).getData());
        }

        public void getAllUsers() {
            for (Document user : usersCollection.find()) {
                System.out.println("Username: " + user.getString("username"));
            }
        }

        private byte[] hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                return md.digest(password.getBytes());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al generar hash de la contraseña", e);
            }
        }
    }

