package com.codeflo.ingestion;

import com.codeflo.auth.GitHubAuth;
import com.codeflo.db.MongoDBHandler;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Fetches repository structure and file contents from GitHub.
 */
public class GitHubIngestion {
    private final String token;
    private final MongoDBHandler mongoDB;

    public GitHubIngestion(GitHubAuth auth, MongoDBHandler mongoDB) {
        this.token = auth.getToken();
        this.mongoDB = mongoDB;
    }


    public void fetchRepositoryContents(String owner, String repo, String path) {
        try {
            String url = "https://api.github.com/repos/" + owner + "/" + repo + "/contents/" + path;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Authorization", "token " + token);
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Error: Received HTTP response " + responseCode);
                return;
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject fileObj = jsonArray.getJSONObject(i);
                String fileName = fileObj.getString("name");
                String fileType = fileObj.getString("type");

                System.out.println("Processing: " + fileName + " (" + fileType + ")");

                if (fileType.equals("file")) {
                    String fileUrl = fileObj.getString("download_url");
                    storeFileContent(repo, fileName, path, fileUrl);
                } else if (fileType.equals("dir")) {
                    fetchRepositoryContents(owner, repo, path + "/" + fileName);
                }

                mongoDB.insertRepoStructure(new Document("repo", repo)
                        .append("file", fileName)
                        .append("path", path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void storeFileContent(String repo, String fileName, String path, String fileUrl) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(fileUrl).openConnection();
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder content = new StringBuilder();
            while (scanner.hasNext()) {
                content.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            mongoDB.insertFileContent(new Document("repo", repo).append("file", fileName).append("path", path).append("content", content.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
