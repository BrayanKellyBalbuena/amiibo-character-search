package edu.unapec.amiiboarecycleview.dataAccess.Repositories;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import edu.unapec.amiiboarecycleview.dataAccess.AmiiboDatabase;
import edu.unapec.amiiboarecycleview.dataAccess.daos.AmiiboDao;
import edu.unapec.amiiboarecycleview.dataAccess.daos.ReleaseDao;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.Release;

public class ReleaseRepository {
    private ReleaseDao releaseDao;
    private List<Release> releaseList;

    public ReleaseRepository(Context context){
        AmiiboDatabase db = AmiiboDatabase.getDatabase(context);
        releaseDao =  db.releaseDao();
    }

    public List<Amiibo> getAll() {
        return null;
    }

    public void insert(Release release) {
        releaseDao.insert(release);
       // new ReleaseRepository.insertAsyncTask(releaseDao).execute(release);
    }

    public void deleteAll(){
        new ReleaseRepository.deleteAllAsycnTask(releaseDao).execute();
    }

    public  static class insertAsyncTask extends AsyncTask<Release,Void , Void> {

        private ReleaseDao asyncReleaseDao;

        insertAsyncTask(ReleaseDao dao) {
            asyncReleaseDao = dao;
        }

        @Override
        protected Void doInBackground(Release... releases) {
            asyncReleaseDao.insert(releases[0]);
            return null;
        }
    }

    public static class deleteAllAsycnTask extends AsyncTask<Void, Void, Void>{

        private ReleaseDao asyncReleaseDao;

        deleteAllAsycnTask(ReleaseDao dao) {
            asyncReleaseDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncReleaseDao.deleteAll();
            return null;
        }
    }
}
