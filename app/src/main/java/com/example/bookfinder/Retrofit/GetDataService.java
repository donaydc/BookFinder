package com.example.bookfinder.Retrofit;

import com.example.bookfinder.Objects.InitDownload;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("search/{word}")
    Call<InitDownload> getSelectedBooks(@Path("word") String searchWord);

}
