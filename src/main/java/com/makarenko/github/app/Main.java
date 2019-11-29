package com.makarenko.githubapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.kohsuke.github.GHMyself.RepositoryListFilter;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class Main {

  public static void main(String[] args) {
    try (InputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {
      Properties prop = new Properties();
      prop.load(input);

      GitHub gitHub = GitHub.connectUsingOAuth(prop.getProperty("token"));

      System.out.println("My repositories: ");
      PagedIterable<GHRepository> ghRepositories = gitHub.getMyself()
          .listRepositories(1, RepositoryListFilter.OWNER);

      for (GHRepository repo : ghRepositories) {
        System.out.println(repo.getFullName());
      }

      System.out.println();

      System.out.println("My star repositories: ");
      PagedIterable<GHRepository> gh = gitHub.getMyself().listStarredRepositories();

      for (GHRepository repo : gh) {
        System.out.println(repo.getFullName());
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
