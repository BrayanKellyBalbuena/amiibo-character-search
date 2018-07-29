package edu.unapec.amiiboarecycleview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import edu.unapec.amiiboarecycleview.Api.AmiiboApi;
import edu.unapec.amiiboarecycleview.dataAccess.Repositories.AmiiboRepository;
import edu.unapec.amiiboarecycleview.dataAccess.Repositories.ReleaseRepository;
import edu.unapec.amiiboarecycleview.dtos.AmiiboDto;
import edu.unapec.amiiboarecycleview.dtos.AmiiboListDto;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.AmiiboWithAllRelease;
import edu.unapec.amiiboarecycleview.models.Release;
import edu.unapec.amiiboarecycleview.utils.ImageUtil;
import edu.unapec.amiiboarecycleview.utils.ModelMapping;
import edu.unapec.amiiboarecycleview.utils.ScreenshotUtils;
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
    private DrawerLayout mDrawLayer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigation;
    private AmiiboRepository amiiboRepository;
    private ReleaseRepository releaseRepository;
    private List<AmiiboWithAllRelease> amiiboWithAllRelease = null;
    private Amiibo currentAmiibo;

    AmiiboListDto amiiboListDto = new AmiiboListDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Amiibo List");
        loadRepository();
        mappingLayoutControl();
//        releaseRepository.deleteAll();
//        amiiboRepository.deleteAll();
        Amiibo a = new Amiibo();
//        a.character = "mario";
//        Release r = new Release();
//        a.image = "https://iloveradio.de/fileadmin/coverbilder/ivangough_inmymind.jpg";
//        r.na = "jo";
//        a.release = r;
//        amiiboRepository.insert(a);
//        amiiboWithAllRelease = amiiboRepository.getAllWithRelease();

        initImageBitmaps();


        new Runnable(){
            @Override
            public void run() {
                amiiboWithAllRelease = amiiboRepository.getAllWithRelease();
            }
        };
//        AmiiboDto a = new AmiiboDto();
//        a.setImage("https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000100-00190002.png");
//        a.setTail("00940102");
//
//        new RetrieveFeedTask().execute(a);

        mDrawLayer = (DrawerLayout) findViewById(R.id.draw_layer);
        mNavigation = (NavigationView) findViewById(R.id.nav_view);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawLayer,
                R.string.open,
                R.string.close){

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.amiibo_list);
            }

            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                getSupportActionBar().setTitle("Menu");
            }
        };

        mDrawLayer.addDrawerListener(mDrawerToggle);

        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.save_offline:
                        Toast.makeText(MainActivity.this, "Save offline",Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }
            }
        });
    }

    public void mappingLayoutControl(){
        mProgressBar =(ProgressBar) findViewById(R.id.progress_bar);

        mEditTextSearch = (EditText) findViewById(R.id.edit_text_search);
        mRecyclerView  = (RecyclerView) findViewById(R.id.recycler_view);
        mMario_404 = (AppCompatImageView) findViewById(R.id.mario_404);
    }

    public void loadRepository(){
        amiiboRepository = new AmiiboRepository(this);
        releaseRepository = new ReleaseRepository(this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean hasAmiibos(){
        return amiiboRepository.getAllWithRelease().size() > 0 ? true : false;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initImageBitmaps(){

        if(hasAmiibos() && !isNetworkAvailable()){
            amiiboWithAllRelease = amiiboRepository.getAllWithRelease();
            amiiboListDto = ModelMapping.amiiboWithAllReleaseToAmiiboListDto(amiiboWithAllRelease);

            initRecyclerView();
            mProgressBar.setVisibility(View.GONE);

        } else {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AmiiboApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AmiiboApi api = retrofit.create(AmiiboApi.class);
            Call<AmiiboListDto> call = api.getAmiibos();

            call.enqueue(new Callback<AmiiboListDto>() {
                @Override
                public void onResponse(Call<AmiiboListDto> call, Response<AmiiboListDto> response) {
                    amiiboListDto = response.body();

                    ImageUtil imageUtil = new ImageUtil();
                    imageUtil.saveImages(MainActivity.this ,amiiboListDto.getAmiibos());

//                    List<Amiibo> amiib = ModelMapping.AmiiboListDToAmiiboList(amiiboListDto);
////
//                    for (Amiibo  a : amiib
//                         ) {
//                        amiiboRepository.insert(a);
//                    }
                    List<Amiibo> amiibos = ModelMapping.AmiiboListDToAmiiboList(amiiboListDto);
                    amiiboRepository.InsertAll(amiibos);

                    initRecyclerView();
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AmiiboListDto> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT)
                            .show();

                    mProgressBar.setVisibility(View.GONE);
                    mMario_404.setVisibility(View.GONE);
                }
            });
        }
    }

    private void initRecyclerView(){

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, amiiboListDto);
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
                        amiiboListDto = response.body();

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

    class saveListImage extends AsyncTask<List<AmiiboDto>, Void, Void> {

        private Exception exception;

        protected Void doInBackground(List<AmiiboDto>... amiibosDto) {

            for (AmiiboDto dto: amiibosDto[0]) {
                dto.setImage(ScreenshotUtils.saveImageFromUrl(MainActivity.this, dto));
            }

            return null;
        }
    }

    class saveImage extends AsyncTask<AmiiboDto, Void, Void> {

        private Exception exception;

        protected Void doInBackground(AmiiboDto... amiibo) {
            ScreenshotUtils.saveImageFromUrl(MainActivity.this, amiibo[0]);

            return null;
        }
    }
}
