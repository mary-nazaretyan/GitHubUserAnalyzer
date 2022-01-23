package com.disqo.mary.gitanalyzer.model.dto;

import java.util.Date;

import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsDTO {
    private String login;
    private Long id;
    private String nodeId;
    private String avatarUrl;
    private String gravatarId;
    private String url;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private String siteAdmin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String twitterUsername;
    private int publicRepos;
    private int publicGists;
    private int followers;
    private int following;
    private Date createdAt;
    private Date updatedAtString;

    public UserDetailsDTO(UserDetails userDetails) {
        login = userDetails.getLogin();
        id = userDetails.getId();
        nodeId = userDetails.getNodeId();
        avatarUrl = userDetails.getAvatarUrl();
        gravatarId = userDetails.getGravatarId();
        url = userDetails.getUrl();
        htmlUrl = userDetails.getHtmlUrl();
        followersUrl = userDetails.getFollowersUrl();
        followingUrl = userDetails.getFollowingUrl();
        gistsUrl = userDetails.getGistsUrl();
        starredUrl = userDetails.getStarredUrl();
        subscriptionsUrl = userDetails.getSubscriptionsUrl();
        organizationsUrl = userDetails.getOrganizationsUrl();
        reposUrl = userDetails.getReposUrl();
        eventsUrl = userDetails.getEventsUrl();
        receivedEventsUrl = userDetails.getReceivedEventsUrl();
        type = userDetails.getType();
        siteAdmin = userDetails.getSiteAdmin();
        name = userDetails.getName();
        company = userDetails.getCompany();
        blog = userDetails.getBlog();
        location = userDetails.getLocation();
        email = userDetails.getEmail();
        hireable = userDetails.getHireable();
        bio = userDetails.getBio();
        twitterUsername = userDetails.getTwitterUsername();
        publicRepos = userDetails.getPublicRepos();
        publicGists = userDetails.getPublicGists();
        followers = userDetails.getFollowers();
        following = userDetails.getFollowing();
        createdAt = userDetails.getCreatedAt();
        updatedAtString = userDetails.getUpdatedAtString();
    }
}
