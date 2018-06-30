
package edu.unapec.amiiboarecycleview.dtos;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmiiboListDto implements Parcelable
{

    @SerializedName("amiibo")
    @Expose
    private List<AmiiboDto> amiibosDto = null;
    public final static Creator<AmiiboListDto> CREATOR = new Creator<AmiiboListDto>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AmiiboListDto createFromParcel(Parcel in) {
            return new AmiiboListDto(in);
        }

        public AmiiboListDto[] newArray(int size) {
            return (new AmiiboListDto[size]);
        }

    }
    ;

    protected AmiiboListDto(Parcel in) {
        in.readList(this.amiibosDto, (edu.unapec.amiiboarecycleview.dtos.AmiiboDto.class.getClassLoader()));
    }

    public AmiiboListDto() {
    }

    public List<AmiiboDto> getAmiibos() {
        return amiibosDto;
    }

    public void setAmiibo(List<AmiiboDto> amiibo) {
        this.amiibosDto = amiibo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(amiibosDto);
    }

    public int describeContents() {
        return  0;
    }

}
