package edu.unapec.amiiboarecycleview.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class AmiiboWithRelease {

    @PrimaryKey
    @NonNull
    public Long amiibo_id ;

    public String au;

    public String eu;

    public String jp;

    public String na;
}
