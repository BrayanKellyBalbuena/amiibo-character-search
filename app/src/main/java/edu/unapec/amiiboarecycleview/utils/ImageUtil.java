package edu.unapec.amiiboarecycleview.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import edu.unapec.amiiboarecycleview.MainActivity;
import edu.unapec.amiiboarecycleview.dtos.AmiiboDto;

public  class ImageUtil {

   public  void saveImages(Context context,List<AmiiboDto> amiiboDtos){
       new saveListImage(context).execute(amiiboDtos);
   }

   public static class saveListImage extends AsyncTask<List<AmiiboDto>, Void, Void> {

        private Exception exception;
        private Context context;

       saveListImage(Context cont){context = cont;}

        protected Void doInBackground(List<AmiiboDto>... amiibosDto) {

            for (AmiiboDto dto: amiibosDto[0]) {
                dto.setImage(ScreenshotUtils.saveImageFromUrl(context, dto));
            }

            return null;
        }
    }

//    private static saveImage extends AsyncTask<AmiiboDto, Void, Void> {
//
//        private Exception exception;
//
//        protected Void doInBackground(AmiiboDto... amiibo) {
//            ScreenshotUtils.saveImageFromUrl(MainActivity.this, amiibo[0]);
//
//            return null;
//        }
//    }
}
