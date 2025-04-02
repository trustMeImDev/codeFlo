package com.codeflo.db;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoClient;

/**
 * Handles MongoDB database connection and operations.
 */
public class MongoDBHandler {
    private static final String DB_NAME = "codeflo";
    private static final String REPO_COLLECTION = "repository_structure";
    private static final String CONTENT_COLLECTION = "repository_contents";

    private final MongoDatabase database;
    private final MongoCollection<Document> repoStructureCollection;
    private final MongoCollection<Document> contentCollection;

    public MongoDBHandler() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase(DB_NAME);
        repoStructureCollection = database.getCollection(REPO_COLLECTION);
        contentCollection = database.getCollection(CONTENT_COLLECTION);
        System.out.println("Connected to MongoDB successfully.");
    }

    public void insertRepoStructure(Document repoDoc) {
        repoStructureCollection.insertOne(repoDoc);
    }

    public void insertFileContent(Document fileDoc) {
        contentCollection.insertOne(fileDoc);
    }
}
