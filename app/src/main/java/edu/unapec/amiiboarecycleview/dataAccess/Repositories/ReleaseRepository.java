package edu.unapec.amiiboarecycleview.dataAccess.Repositories;

import android.os.AsyncTask;

import java.util.List;

import edu.unapec.amiiboarecycleview.dataAccess.daos.AmiiboDao;
import edu.unapec.amiiboarecycleview.dataAccess.daos.ReleaseDao;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.Release;

public class ReleaseRepository {
    private ReleaseDao releaseDao;
    private List<Release> releaseList;

    public List<Amiibo> getAll() {
        return null;
    }

    public void insert(Release release) {
        new ReleaseRepository.insertAsyncTask(releaseDao).execute(release);
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
}
