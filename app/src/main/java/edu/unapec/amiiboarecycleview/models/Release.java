package edu.unapec.amiiboarecycleview.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "releases")
public class Release {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "release_id")
    public Long id ;

    @ColumnInfo(name = "amiibo_id")
    public Long amiibo_id ;

    @ColumnInfo
    public String au;

    @ColumnInfo
    public String eu;

    @ColumnInfo
    public String jp;

    @ColumnInfo
    public String na;
}
