package edu.unapec.amiiboarecycleview.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import edu.unapec.amiiboarecycleview.R;
import edu.unapec.amiiboarecycleview.dtos.AmiiboDto;
import edu.unapec.amiiboarecycleview.models.Amiibo;

import static android.provider.Settings.Global.getString;
import static android.support.v4.content.ContextCompat.startActivity;


public class ScreenshotUtils {

    private static final String SHARE = "Share";

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static File getMainDirectoryName(Context context){
        File mainDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "AmiiboScreenShots");

        if(!mainDir.exists()){
            if(mainDir.mkdir())
                Log.e("Create Directory", "Main Directory Created: " + mainDir);
        }
        return mainDir;
    }

    public static File store(Bitmap bm, String fileName, File saveFilePath){
        File dir = new File(saveFilePath.getAbsolutePath());
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try{
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

    public static String saveImageFromUrl(Context c, AmiiboDto amiiboDto){
        try{
            String localpath;
            URL url = new URL(amiiboDto.getImage());
            InputStream input = url.openStream();

            try {
                //The sdcard directory e.g. '/sdcard' can be used directly, or
                //more safely abstracted with getExternalStorageDirectory()
                File storagePath = new File( c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "AmiiboScreenShots");
                localpath = storagePath +"/"+ amiiboDto.getTail() + ".png";
                OutputStream output = new FileOutputStream ( localpath);
                int b = 1;

                try {
                    int aReasonableSize = 1000;
                    byte[] buffer = new byte[aReasonableSize];
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                        output.write(buffer, 0, bytesRead);
                    }
                } finally {
                    output.close();
                }
            } finally {
                input.close();
            }
            return localpath;
        }catch (MalformedURLException ex){

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
