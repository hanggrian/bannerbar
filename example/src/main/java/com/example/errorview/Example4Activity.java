package com.example.errorview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.errorview.model.Post;
import com.example.errorview.retrofit.Reference;
import com.hendraanggrian.widget.ErrorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class Example4Activity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swiperefreshlayout_example4) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview_example4) RecyclerView recyclerView;
    @BindView(R.id.errorview_example4) ErrorView errorView;
    private List<Post> list;

    @Override
    public int getContentView() {
        return R.layout.activity_example4;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostsAdapter());
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        int size = list.size();
        list.clear();
        recyclerView.getAdapter().notifyItemRangeRemoved(0, size);
        new Retrofit.Builder()
                .baseUrl(Reference.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Reference.class)
                .posts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Post>>() {
                    @Override
                    public void accept(List<Post> posts) throws Exception {
                        list.addAll(posts);
                        recyclerView.getAdapter().notifyItemRangeInserted(0, list.size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

        @Override
        public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(Example4Activity.this).inflate(R.layout.item_post, parent, false));
        }

        @Override
        public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {
            holder.textView.setText(list.get(position).title);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @NonNull private final TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }
    }
}