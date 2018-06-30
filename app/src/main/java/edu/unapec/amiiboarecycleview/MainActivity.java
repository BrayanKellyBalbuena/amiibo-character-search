package edu.unapec.amiiboarecycleview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import edu.unapec.amiiboarecycleview.Api.AmiiboApi;
import edu.unapec.amiiboarecycleview.dtos.AmiiboListDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mEditTextSearch;
    private ProgressBar mProgressBar;
    private AppCompatButton mButtonSeacrh;
    private RecyclerView mRecyclerView;
    private AppCompatImageView mMario_404;

    AmiiboListDto ami = new AmiiboListDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Character List");
        mProgressBar =(ProgressBar) findViewById(R.id.progress_bar);
        mButtonSeacrh = (AppCompatButton) findViewById(R.id.button_search);
        mEditTextSearch = (EditText) findViewById(R.id.edit_text_search);
        mRecyclerView  = (RecyclerView) findViewById(R.id.recycler_view);
        mMario_404 = (AppCompatImageView) findViewById(R.id.mario_404);
        mButtonSeacrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByCharacter();
            }
        });
        initImageBitmaps();
    }

    private void initImageBitmaps(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AmiiboApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AmiiboApi api = retrofit.create(AmiiboApi.class);
        Call<AmiiboListDto> call = api.getAmiibos();

        call.enqueue(new Callback<AmiiboListDto>() {
            @Override
            public void onResponse(Call<AmiiboListDto> call, Response<AmiiboListDto> response) {
              ami =  response.body();

              initRecyclerView();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AmiiboListDto> call, Throwable t) {
                Toast.makeText(MainActivity.this    , "Network Error", Toast.LENGTH_SHORT)
                        .show();

                mProgressBar.setVisibility(View.GONE);
                mMario_404.setVisibility(View.GONE);
            }
        });
    }

    private void initRecyclerView(){
        ami.getAmiibos().forEach(x ->  Log.d(TAG,x.getName()));
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, ami);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void searchByCharacter(){

        String seachText = mEditTextSearch.getText().toString();

        if (seachText != null){

            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AmiiboApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AmiiboApi api = retrofit.create(AmiiboApi.class);
            Call<AmiiboListDto> call = api.getAmiibosByCharacter(seachText);

            call.enqueue(new Callback<AmiiboListDto>() {
                @Override
                public void onResponse(Call<AmiiboListDto> call, Response<AmiiboListDto> response) {

                    if(response.body() != null) {
                        ami = response.body();

                        initRecyclerView();
                        mProgressBar.setVisibility(View.GONE);
                        mMario_404.setVisibility(View.GONE);
                        Log.d(TAG, "Response");
                    }else {
                        Toast.makeText(MainActivity.this    , "Not found", Toast.LENGTH_SHORT)
                                .show();
                        mProgressBar.setVisibility(View.GONE);
                        mMario_404.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<AmiiboListDto> call, Throwable t) {
                    Toast.makeText(MainActivity.this    , "Network Error", Toast.LENGTH_SHORT)
                            .show();

                    mProgressBar.setVisibility(View.GONE);
                    mMario_404.setVisibility(View.GONE);

                }
            });
        }else {
            Toast.makeText(this, "Please enter a character", Toast.LENGTH_SHORT)
            .show();
        }


    }
}