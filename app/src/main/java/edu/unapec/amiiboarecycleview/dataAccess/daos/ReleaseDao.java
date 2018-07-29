package edu.unapec.amiiboarecycleview.dataAccess.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import edu.unapec.amiiboarecycleview.models.Release;

@Dao
public interface ReleaseDao{

    @Query("SELECT * FROM  releases WHERE release_id = :id")
    public Release getById(Integer id);

    @Insert
    public Long insert(Release release);

    @Delete
    public void delete (Release release);

    @Query("DELETE  FROM releases")
    public void deleteAll();
}
