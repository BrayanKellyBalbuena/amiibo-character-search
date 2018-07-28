package edu.unapec.amiiboarecycleview.dataAccess.Repositories;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import java.util.List;

import edu.unapec.amiiboarecycleview.dataAccess.AmiiboDatabase;
import edu.unapec.amiiboarecycleview.dataAccess.daos.AmiiboDao;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.AmiiboWithAllRelease;

public class AmiiboRepository {

    private AmiiboDao amiiboDao;
    private List<Amiibo> amiiboList;

    public AmiiboRepository(Context context){
        AmiiboDatabase db = AmiiboDatabase.getDatabase(context);
        amiiboDao =  db.amiiboDao();
    }

    public List<Amiibo> getAll() {
       return amiiboDao.getAll();
    }

    public List<AmiiboWithAllRelease> getAllWithRelease(){
        return amiiboDao.getAmiibosWithRelease();
    }

    public void insert(Amiibo amiibo) {
        new insertAsyncTask(amiiboDao).execute(amiibo);
    }

    public  static class insertAsyncTask extends AsyncTask< Amiibo,Void , Void> {

        private AmiiboDao asyncAmiiboDao;

        insertAsyncTask(AmiiboDao dao) {
            asyncAmiiboDao = dao;
        }

        @Override
        protected Void doInBackground(Amiibo... amiibos) {
            asyncAmiiboDao.insert(amiibos[0]);
            return null;
        }
    }
}
