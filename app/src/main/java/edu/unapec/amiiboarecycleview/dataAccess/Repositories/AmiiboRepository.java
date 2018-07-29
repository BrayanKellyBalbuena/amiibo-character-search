package edu.unapec.amiiboarecycleview.dataAccess.Repositories;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import edu.unapec.amiiboarecycleview.dataAccess.AmiiboDatabase;
import edu.unapec.amiiboarecycleview.dataAccess.daos.AmiiboDao;
import edu.unapec.amiiboarecycleview.dataAccess.daos.ReleaseDao;
import edu.unapec.amiiboarecycleview.dtos.ReleaseDto;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.AmiiboWithAllRelease;
import edu.unapec.amiiboarecycleview.models.Release;

public class AmiiboRepository {

    private AmiiboDao amiiboDao;
    private ReleaseDao releaseDao;
    private List<Amiibo> amiiboList;
    private final String imagePath = "/storage/emulated/0/Android/data" +
            "/edu.unapec.amiiboarecycleview/files/Pictures/AmiiboScreenShots/";

    public AmiiboRepository(Context context){
        AmiiboDatabase db = AmiiboDatabase.getDatabase(context);
        amiiboDao =  db.amiiboDao();
        releaseDao = db.releaseDao();
    }

    public List<Amiibo> getAll() {
       return amiiboDao.getAll();
    }

    public List<AmiiboWithAllRelease> getAllWithRelease(){
        return amiiboDao.getAmiibosWithRelease();
    }

    public void insert(Amiibo amiibo) {
       Long id = amiiboDao.insert(amiibo) ;
       Release r = amiibo.release;
       r.amiibo_id = id;
      Long s = releaseDao.insert(r);
//        new insertAsyncTask(amiiboDao, releaseDao).execute(amiibo);
    }

    public void InsertAll(List<Amiibo> amiibos){
        for (Amiibo amiibo : amiibos) {

            amiibo.image = imagePath + amiibo.tail + ".png";
            Long id = amiiboDao.insert(amiibo);
            Log.d("TAG",  amiibo.character);
            Release release = amiibo.release;
            release.amiibo_id = id;
            releaseDao.insert(release);

        }
//        new insertAllAsyncTask(amiiboDao, releaseDao).execute(amiibos);
    }

    public void deleteAll(){ new deleteAllAsycnTask(amiiboDao).execute();}

    public  static class insertAsyncTask extends AsyncTask<Amiibo,Void , Void> {

        private AmiiboDao asyncAmiiboDao;
        private ReleaseDao asyncReleaseDao;
        private Object lock = new Object();

        insertAsyncTask(AmiiboDao amiiboDao, ReleaseDao releaseDao) {
            asyncAmiiboDao = amiiboDao;
            asyncReleaseDao = releaseDao;
        }

        @Override
        protected Void doInBackground(Amiibo... amiibos) {
           Long id = asyncAmiiboDao.insert(amiibos[0]);
                Release release =  amiibos[0].release;
                Log.d("TAG",  id.toString());
                release.amiibo_id = id;
                asyncReleaseDao.insert(release);

            return null;
        }
    }

    public static class insertAllAsyncTask extends AsyncTask<List<Amiibo>,Void , Void> {

        private AmiiboDao asyncAmiiboDao;
        private ReleaseDao asyncReleaseDao;

        insertAllAsyncTask(AmiiboDao amiiboDao, ReleaseDao releaseDao) {
            asyncAmiiboDao = amiiboDao;
            asyncReleaseDao = releaseDao;
        }

        @Override
        protected Void doInBackground(List<Amiibo>... lists) {
            for (Amiibo amiibo : lists[0]) {


                    Long id = asyncAmiiboDao.insert(amiibo);
                    Log.d("TAG",  amiibo.character);
                    Release release = amiibo.release;
                    release.amiibo_id = id;
                    asyncReleaseDao.insert(release);

            }
            return null;
        }
    }

    public static class deleteAllAsycnTask extends AsyncTask<Void, Void, Void>{

        private AmiiboDao asyncReleaseDao;

        deleteAllAsycnTask(AmiiboDao dao) {
            asyncReleaseDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncReleaseDao.deleteAll();
            return null;
        }
    }
}
