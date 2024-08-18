package com.example.bunker.service;

import com.example.bunker.model.GithubContent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GithubService {
    @PUT("repos/{owner}/{repo}/contents/{path}")
    Call<Void> updateFile(
            @Header("Authorization") String auth,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("path") String path,
            @Body GithubContent body
    );

    @DELETE("repos/{owner}/{repo}/contents/{path}")
    Call<Void> deleteFile(
            @Header("Authorization") String auth,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("path") String path,
            @Body GithubContent body
    );
}
