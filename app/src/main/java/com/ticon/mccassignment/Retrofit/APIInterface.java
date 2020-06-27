package com.ticon.mccassignment.Retrofit;

import com.ticon.mccassignment.Model.PopularMovieListModel;
import com.ticon.mccassignment.Model.TrailerdataSourceModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



public interface APIInterface {
    @GET("/3/movie/popular")
    Call<PopularMovieListModel> getPopularMoiveList(@Query("api_key") String api_key);

    @GET("/3/movie/{id}/videos")
    Call<TrailerdataSourceModel> getMovieTrailer(@Path("id") String id, @Query("api_key") String api_key);

}
