package com.makarenko.github.app;

import static com.makarenko.github.app.GitHubAppImpl.getMyRepositories;
import static com.makarenko.github.app.GitHubAppImpl.getUserPullRequest;

public class Main {

  public static void main(String[] args) {
    getMyRepositories();
    getUserPullRequest();
  }
}
