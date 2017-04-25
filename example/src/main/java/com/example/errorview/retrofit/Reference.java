package com.example.errorview.retrofit;

import com.example.errorview.model.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface Reference {

    String BASE_URL = "https://jsonplaceholder.typicode.com";

    @GET("posts")
    Observable<List<Post>> posts();
}