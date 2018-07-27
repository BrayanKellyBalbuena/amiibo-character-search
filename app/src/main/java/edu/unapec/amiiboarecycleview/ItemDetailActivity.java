package edu.unapec.amiiboarecycleview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import edu.unapec.amiiboarecycleview.dtos.AmiiboDto;
import edu.unapec.amiiboarecycleview.utils.ScreenshotUtils;

public class ItemDetailActivity extends AppCompatActivity {

    private static final String TAG = "Item Details";
    private TextView mAmiiboSeries;
    private TextView amiiboName;
    private TextView mReleaseEu;
    private TextView mReleaseAu;
    private TextView mReleaseJp;
    private TextView mType;
    private ProgressBar mProgressBar;
    private LinearLayout layoutDetails;
    private AppCompatButton mBtnShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        setTitle("Character Details");
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        amiiboName = (TextView)findViewById(R.id.amiibo_name);
        mAmiiboSeries = (TextView) findViewById(R.id.amiibo_series);
        mReleaseEu = (TextView) findViewById(R.id.release_eu);
        mReleaseAu = (TextView) findViewById(R.id.release_au);
        mReleaseJp = (TextView) findViewById(R.id.release_jp);
        mType = (TextView) findViewById(R.id.type);
        layoutDetails = (LinearLayout) findViewById(R.id.layout_details);

        mBtnShare = (AppCompatButton) findViewById(R.id.btnShare);

        implementClickEvents();
    }

    private void implementClickEvents() {
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getAmiiboIntent();
    }

    public void getAmiiboIntent(){
        if(getIntent().hasExtra("currentAmiibo")){
            AmiiboDto amiiboDto = getIntent().getParcelableExtra("currentAmiibo");

           fillLayoutValues(amiiboDto);
        }
    }

    public void fillLayoutValues(AmiiboDto amiiboDto){

        ImageView imageView = (ImageView) findViewById(R.id.amiibo_image);

        Glide.with(this)
                .asBitmap()
                .load(amiiboDto.getImage())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imageView);

        amiiboName.setText(amiiboDto.getName());
        mAmiiboSeries.setText(amiiboDto.getAmiiboSeries());
        mReleaseEu.setText(amiiboDto.getReleaseDto().getEu());
        mReleaseAu.setText(amiiboDto.getReleaseDto().getAu());
        mReleaseJp.setText(amiiboDto.getReleaseDto().getJp());
        mType.setText(amiiboDto.getType());

        mProgressBar.setVisibility(View.GONE);
        layoutDetails.setVisibility(View.VISIBLE);

    }

    private void takeScreenshot() {
        Bitmap bitmap = ScreenshotUtils.getScreenShot(layoutDetails);


            if(bitmap != null){
                File saveFile = ScreenshotUtils.getMainDirectoryName(this);
                File file = ScreenshotUtils.store(bitmap,  amiiboName.getText()
                        + ".jpg", saveFile);

                shareScreenshot(file);
            } else
            //If bitmap is null show toast message
            Toast.makeText(this, R.string.screenshot_take_failed, Toast.LENGTH_SHORT).show();


        }

    private void shareScreenshot(File file) {
        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.sharing_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }
}
