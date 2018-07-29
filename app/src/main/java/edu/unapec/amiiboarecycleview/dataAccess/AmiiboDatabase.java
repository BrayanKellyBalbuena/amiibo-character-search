package edu.unapec.amiiboarecycleview.dataAccess;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import edu.unapec.amiiboarecycleview.dataAccess.daos.AmiiboDao;
import edu.unapec.amiiboarecycleview.dataAccess.daos.ReleaseDao;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.AmiiboWithRelease;
import edu.unapec.amiiboarecycleview.models.Release;

@Database(entities = {Amiibo.class, Release.class, AmiiboWithRelease.class}, version = 1)
public abstract class AmiiboDatabase extends RoomDatabase {
    public abstract AmiiboDao amiiboDao();
    public abstract ReleaseDao releaseDao();

    private static AmiiboDatabase INSTANCE;

    public static final String NAME = "amiibo_database";

   public static AmiiboDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AmiiboDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AmiiboDatabase.class, NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sAmiiboDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            new PopulateDbAsycn(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsycn extends AsyncTask<Void, Void,Void>{

       private final AmiiboDao adao;
       private final ReleaseDao rdao;


        PopulateDbAsycn(AmiiboDatabase data){
            adao = data.amiiboDao();
            rdao = data.releaseDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Amiibo a = new Amiibo();
            a.name = "mario";
            Long l = adao.insert(a);
            Release r = new Release();
            r.au = "au";
            r.amiibo_id = l;
            rdao.insert(r);
            return null;
        }
    }
}
