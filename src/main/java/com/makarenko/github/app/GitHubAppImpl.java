package com.makarenko.github.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHLabel;
import org.kohsuke.github.GHMyself.RepositoryListFilter;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class GitHubAppImpl {

  public static void getUserPullRequest() {
    try {
      GitHub gitHub = getGitHubConnection();
      String nameRepository = readProperty().getProperty("repository");
      GHRepository repository = gitHub.getRepository(nameRepository);
      List<GHPullRequest> pullRequests = repository.getPullRequests(GHIssueState.OPEN);

      System.out.println("\nRepository '" + repository.getFullName() + "' have open pullRequest: ");
      for (GHPullRequest repo : pullRequests) {
        GHUser user = repo.getUser();
        Collection<GHLabel> labels = repo.getLabels();

        labels.forEach(label -> System.out.println(user.getLogin() + ": " + label.getName()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void getMyRepositories() {
    try {
      GitHub gitHub = getGitHubConnection();

      System.out.println("My repositories: ");

      PagedIterable<GHRepository> ghRepositories = gitHub.getMyself()
          .listRepositories(1, RepositoryListFilter.OWNER);
      ghRepositories.forEach(repo -> System.out.println(repo.getFullName()));

      System.out.println("\nMy star repositories: ");

      PagedIterable<GHRepository> ghRepositoriesStar = gitHub.getMyself().listStarredRepositories();
      ghRepositoriesStar.forEach(repo -> System.out.println(repo.getFullName()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static GitHub getGitHubConnection() {
    GitHub gitHub = null;
    try {
      String token = readProperty().getProperty("token");
      gitHub = GitHub.connectUsingOAuth(token);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return gitHub;
  }

  public static Properties readProperty() {
    Properties prop = null;
    try (InputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {
      prop = new Properties();
      prop.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return prop;
  }
}
