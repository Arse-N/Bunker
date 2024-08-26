package com.example.bunker.service;

import com.example.bunker.dto.GithubFile;
import com.example.bunker.model.GithubContent;
import retrofit2.Call;
import retrofit2.http.*;

public interface GithubService {
    @PUT("repos/{owner}/{repo}/contents/{path}")
    Call<Void> updateFile(
            @Header("Authorization") String auth,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("path") String path,
            @Body GithubContent body
    );

    @GET("repos/{owner}/{repo}/contents/{path}")
    Call<GithubFile> getFileInfo(
            @Header("Authorization") String auth,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("path") String path
    );


    @DELETE("repos/{owner}/{repo}/contents/{path}")
    Call<Void> deleteFile(
            @Header("Authorization") String auth,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("path") String path,
            @Query("message") String message,
            @Query("sha") String sha
    );

}
