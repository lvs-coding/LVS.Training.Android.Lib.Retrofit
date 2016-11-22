package lvsandroid.simpleretrofitlibproject.interfaces;

import lvsandroid.simpleretrofitlibproject.models.StackOverflowQuestions;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Call;

public interface StackOverflowAPI {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);
}