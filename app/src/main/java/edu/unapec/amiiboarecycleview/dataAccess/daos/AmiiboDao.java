package edu.unapec.amiiboarecycleview.dataAccess.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.AmiiboWithAllRelease;
import edu.unapec.amiiboarecycleview.models.AmiiboWithRelease;

@Dao
public interface  AmiiboDao {
    @Query("SELECT * FROM amiibos WHERE id = :id")
    public Amiibo getById(Long id);

    @Query("SELECT * FROM Amiibos")
    public List<Amiibo> getAll();

    @Query("SELECT * FROM Amiibos")
    public List<AmiiboWithAllRelease> getAmiibosWithRelease();

    @Insert
    public Long insert(Amiibo amiibo);

    @Delete
    public void delete(Amiibo amiibo);
}
