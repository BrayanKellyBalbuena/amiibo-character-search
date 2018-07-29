
package edu.unapec.amiiboarecycleview.dtos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmiiboDto implements Parcelable
{

    @SerializedName("amiiboSeries")
    @Expose
    private String amiiboSeries;

    @SerializedName("character")
    @Expose
    private String character;

    @SerializedName("gameSeries")
    @Expose
    private String gameSeries;

    @SerializedName("head")
    @Expose
    private String head;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("release")
    @Expose
    private ReleaseDto releaseDto;

    @PrimaryKey(autoGenerate = false)
    @SerializedName("tail")
    @Expose
    private String tail;

    @SerializedName("type")
    @Expose
    private String type;
    public final static Creator<AmiiboDto> CREATOR = new Creator<AmiiboDto>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AmiiboDto createFromParcel(Parcel in) {
            return new AmiiboDto(in);
        }

        public AmiiboDto[] newArray(int size) {
            return (new AmiiboDto[size]);
        }

    }
    ;

    protected AmiiboDto(Parcel in) {
        this.amiiboSeries = ((String) in.readValue((String.class.getClassLoader())));
        this.character = ((String) in.readValue((String.class.getClassLoader())));
        this.gameSeries = ((String) in.readValue((String.class.getClassLoader())));
        this.head = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.releaseDto = ((ReleaseDto) in.readValue((ReleaseDto.class.getClassLoader())));
        this.tail = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }



    public AmiiboDto() {
    }

    public String getAmiiboSeries() {
        return amiiboSeries;
    }

    public void setAmiiboSeries(String amiiboSeries) {
        this.amiiboSeries = amiiboSeries;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getGameSeries() {
        return gameSeries;
    }

    public void setGameSeries(String gameSeries) {
        this.gameSeries = gameSeries;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReleaseDto getReleaseDto() {
        return releaseDto;
    }

    public void setReleaseDto(ReleaseDto releaseDto) {
        this.releaseDto = releaseDto;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(amiiboSeries);
        dest.writeValue(character);
        dest.writeValue(gameSeries);
        dest.writeValue(head);
        dest.writeValue(image);
        dest.writeValue(name);
        dest.writeValue(releaseDto);
        dest.writeValue(tail);
        dest.writeValue(type);
    }

    public int describeContents() {
        return  0;
    }

}
