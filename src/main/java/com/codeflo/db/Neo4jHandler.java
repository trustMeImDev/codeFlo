package com.codeflo.db;

import org.neo4j.driver.*;

public class Neo4jHandler implements AutoCloseable {
    private final Driver driver;

    public Neo4jHandler() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }

    public void storeGraphData(String repo, String file, String parentFolder) {
        try (Session session = driver.session()) {
            String query = "MERGE (f:File {name: $file, repo: $repo, folder: $parentFolder})";
            session.run(query, Values.parameters("repo", repo, "file", file, "parentFolder", parentFolder));
        }
    }

    @Override
    public void close() {
        driver.close();
    }
}
