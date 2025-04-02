package com.codeflo.main;

import com.codeflo.auth.GitHubAuth;
import com.codeflo.db.MongoDBHandler;
import com.codeflo.ingestion.GitHubIngestion;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GitHubAuth auth = new GitHubAuth();
        MongoDBHandler mongoDB = new MongoDBHandler();
        GitHubIngestion ingestion = new GitHubIngestion(auth, mongoDB);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Repository Owner: ");
        String owner = scanner.nextLine();
        System.out.print("Enter Repository Name: ");
        String repo = scanner.nextLine();

        ingestion.fetchRepositoryContents(owner, repo, "");
    }
}
