package edu.unapec.amiiboarecycleview.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Relation;

import java.util.List;



public class AmiiboWithAllRelease {
     @Embedded
    public Amiibo amiibo;

    @Relation(parentColumn =  "id", entityColumn = "amiibo_id", entity = Release.class)
    public List<AmiiboWithRelease> release;
}

