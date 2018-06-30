
package edu.unapec.amiiboarecycleview.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleaseDto implements Parcelable
{

    @SerializedName("au")
    @Expose
    private String au;
    @SerializedName("eu")
    @Expose
    private String eu;
    @SerializedName("jp")
    @Expose
    private String jp;
    @SerializedName("na")
    @Expose
    private String na;
    public final static Creator<ReleaseDto> CREATOR = new Creator<ReleaseDto>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ReleaseDto createFromParcel(Parcel in) {
            return new ReleaseDto(in);
        }

        public ReleaseDto[] newArray(int size) {
            return (new ReleaseDto[size]);
        }

    }
    ;

    protected ReleaseDto(Parcel in) {
        this.au = ((String) in.readValue((String.class.getClassLoader())));
        this.eu = ((String) in.readValue((String.class.getClassLoader())));
        this.jp = ((String) in.readValue((String.class.getClassLoader())));
        this.na = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReleaseDto() {
    }

    public String getAu() {
        return au;
    }

    public void setAu(String au) {
        this.au = au;
    }

    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(au);
        dest.writeValue(eu);
        dest.writeValue(jp);
        dest.writeValue(na);
    }

    public int describeContents() {
        return  0;
    }

}
