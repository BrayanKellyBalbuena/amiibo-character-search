package edu.unapec.amiiboarecycleview.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import edu.unapec.amiiboarecycleview.dtos.ReleaseDto;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "amiibos")
public class Amiibo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;

    @ColumnInfo
    public String amiiboSeries;

    @ColumnInfo
    public String character;

    @ColumnInfo
    public String gameSeries;

    @ColumnInfo
    public String head;

    @ColumnInfo
    public String image;

    @ColumnInfo
    public String name;

    @Ignore
    public ReleaseDto releaseDto;

    public String tail;

    @ColumnInfo
    public String type;

    public Amiibo() {

    }

    public Amiibo(Long id, String amiiboSeries, String character,
                  String gameSeries, String head,
                  String image, String name,
                  ReleaseDto releaseDto, String tail, String type) {

        this.id = id;
        this.amiiboSeries = amiiboSeries;
        this.character = character;
        this.gameSeries = gameSeries;
        this.head = head;
        this.image = image;
        this.name = name;
        this.releaseDto = releaseDto;
        this.tail = tail;
        this.type = type;
    }

}
