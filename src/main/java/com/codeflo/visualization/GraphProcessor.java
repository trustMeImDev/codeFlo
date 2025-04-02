package com.codeflo.visualization;

import com.codeflo.db.Neo4jHandler;

public class GraphProcessor {
    private final Neo4jHandler neo4j;

    public GraphProcessor() {
        this.neo4j = new Neo4jHandler();
    }

    public void generateGraph(String repoOwner, String repoName) {
        // Fetch data from MongoDB and generate graph visualization
    }
}
