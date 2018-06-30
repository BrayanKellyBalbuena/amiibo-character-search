package edu.unapec.amiiboarecycleview.Api;

import edu.unapec.amiiboarecycleview.dtos.AmiiboListDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AmiiboApi {

    String BASE_URL = "http://www.amiiboapi.com/";

    @GET("api/amiibo")
    Call <AmiiboListDto> getAmiibos();

    @GET("api/amiibo")
    Call <AmiiboListDto> getAmiibosByCharacter(@Query("character") String name);

}
