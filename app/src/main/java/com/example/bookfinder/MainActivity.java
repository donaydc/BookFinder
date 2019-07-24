package com.example.bookfinder;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bookfinder.Adapters.BookAdapter;
import com.example.bookfinder.Adapters.SearchAdapter;
import com.example.bookfinder.Objects.BookObject;
import com.example.bookfinder.Objects.InitDownload;
import com.example.bookfinder.Objects.SearchObject;
import com.example.bookfinder.Retrofit.GetDataService;
import com.example.bookfinder.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private LinearLayout SplashImage, emptyLayout;
    private BookAdapter adapterB;
    private SearchAdapter adapterS;
    private RecyclerView rvBooks, rvSearch;
    private ViewGroup transitionsContainer;
    private android.support.v7.widget.SearchView etSearch;
    public List<SearchObject> searchList = new ArrayList<>();
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transitionsContainer = findViewById(R.id.allContainer);
        etSearch = findViewById(R.id.etSearch);
        rvSearch = findViewById(R.id.rvSearch);
        rvBooks = findViewById(R.id.rvBooks);
        emptyLayout = findViewById(R.id.noResult);
        SplashImage = findViewById(R.id.SplashImage);

        adapterS = new SearchAdapter(this, searchList, etSearch);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(adapterS);

        etSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                SplashImage.setVisibility(View.GONE);
                rvSearch.setVisibility(View.VISIBLE);
            }
        });

        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                rvSearch.setVisibility(View.GONE);
                if(searchList.size()>=5){
                    searchList.remove(searchList.size()-1);
                    searchList.add(0, new SearchObject(etSearch.getQuery().toString()));
                    adapterS = new SearchAdapter(MainActivity.this, searchList, etSearch);
                    rvSearch.setAdapter(adapterS);
                } else {
                    searchList.add(0, new SearchObject(etSearch.getQuery().toString()));
                    adapterS = new SearchAdapter(MainActivity.this, searchList, etSearch);
                    rvSearch.setAdapter(adapterS);
                }

                progressDoalog = new ProgressDialog(MainActivity.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();

                final GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<InitDownload> call = service.getSelectedBooks(etSearch.getQuery().toString());
                call.enqueue(new Callback<InitDownload>() {
                    @Override
                    public void onResponse(Call<InitDownload> call, Response<InitDownload> response) {
                        progressDoalog.dismiss();
                        generateDataList(response.body().getBooks());
                    }

                    @Override
                    public void onFailure(Call<InitDownload> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(MainActivity.this, "Something went wrong...Please try later!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                rvSearch.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }

    private void generateDataList(List<BookObject> bookList) {
        adapterB = new BookAdapter(this, bookList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvBooks.setLayoutManager(layoutManager);
        rvBooks.setAdapter(adapterB);
        if(bookList.size()>0){
            emptyLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }

    }

}
