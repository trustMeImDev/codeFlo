package com.codeflo.auth;

/**
 * Handles GitHub authentication using a personal access token (PAT).
 */
public class GitHubAuth {
    private final String token;

    public GitHubAuth() {
        // Hardcoded for now, but should be stored securely in the future
        this.token = "<Personal Access Token here>"; // No recommended to put tokens hardcoded, but used here for testing purposes.
    }

    public String getToken() {
        return token;
    }
}
