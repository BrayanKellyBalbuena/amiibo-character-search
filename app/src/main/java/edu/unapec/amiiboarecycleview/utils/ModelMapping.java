package edu.unapec.amiiboarecycleview.utils;

import java.util.ArrayList;
import java.util.List;

import edu.unapec.amiiboarecycleview.dtos.AmiiboDto;
import edu.unapec.amiiboarecycleview.dtos.AmiiboListDto;
import edu.unapec.amiiboarecycleview.dtos.ReleaseDto;
import edu.unapec.amiiboarecycleview.models.Amiibo;
import edu.unapec.amiiboarecycleview.models.AmiiboWithAllRelease;
import edu.unapec.amiiboarecycleview.models.AmiiboWithRelease;
import edu.unapec.amiiboarecycleview.models.Release;

public class ModelMapping {

    public static AmiiboListDto amiiboWithAllReleaseToAmiiboListDto(List<AmiiboWithAllRelease> withAllRelease){
        AmiiboListDto amiibosDto = new AmiiboListDto();
        List<AmiiboDto> amiibos = new ArrayList<>();

        for(AmiiboWithAllRelease amiiboWithRelease : withAllRelease){
            AmiiboDto amiibo = amiiboWithAllReleaseToListDto(amiiboWithRelease);
            amiibos.add(amiibo);
        }
        amiibosDto.setAmiibo(amiibos);
        return amiibosDto;
    }

    public static AmiiboDto amiiboWithAllReleaseToListDto(AmiiboWithAllRelease withAllRelease){
       Amiibo amiibo = withAllRelease.amiibo;
       amiibo.release = amiiboWithReleaseToRelease( withAllRelease.release.get(0));
       AmiiboDto amiiboDto = amiiboToAmiiboDto(amiibo);

        return amiiboDto;
    }

    public static Release amiiboWithReleaseToRelease(AmiiboWithRelease amiiboWithRelease){
        Release release = new Release();
        release.au = amiiboWithRelease.au;
        release.eu = amiiboWithRelease.eu;
        release.jp = amiiboWithRelease.jp;
        release.na = amiiboWithRelease.na;

        return release;
    }

    public static AmiiboDto amiiboToAmiiboDto(Amiibo amiibo){
       AmiiboDto amiiboDto = new AmiiboDto();
       amiiboDto.setName(amiibo.name);
       amiiboDto.setCharacter(amiibo.character);
       amiiboDto.setTail(amiibo.tail);
       amiiboDto.setAmiiboSeries(amiibo.amiiboSeries);
       amiiboDto.setImage(amiibo.image);
       amiiboDto.setGameSeries(amiibo.gameSeries);
       amiiboDto.setType(amiibo.type);
       amiiboDto.setReleaseDto(releaseToReleaseDto(amiibo.release));

        return amiiboDto;
    }

    public static ReleaseDto releaseToReleaseDto(Release release){
        ReleaseDto releaseDto = new ReleaseDto();
        releaseDto.setAu(release.au);
        releaseDto.setEu(release.eu);
        releaseDto.setJp(release.jp);
        releaseDto.setNa(release.na);

        return releaseDto;
    }

    public static List<Amiibo> AmiiboListDToAmiiboList(AmiiboListDto amiiboListDto){
        List<Amiibo> amiiboList = new ArrayList<>();

        for(AmiiboDto dto : amiiboListDto.getAmiibos()){
            Amiibo amiibo = amiiboDtoToAmiibo(dto);
            amiiboList.add(amiibo);
        }

        return amiiboList;
    }

    public static Amiibo amiiboDtoToAmiibo(AmiiboDto amiiboDto){
        Amiibo amiibo = new Amiibo();
        amiibo.name = amiiboDto.getName();
        amiibo.character = amiiboDto.getCharacter();
        amiibo.tail = amiiboDto.getTail();
        amiibo.amiiboSeries = amiiboDto.getAmiiboSeries();
        amiibo.image = amiiboDto.getImage();
        amiibo.gameSeries = amiiboDto.getGameSeries();
        amiibo.release = releaseDtoToRelease(amiiboDto.getReleaseDto());
        amiibo.type = amiiboDto.getType();

        return amiibo;
    }

    public static Release releaseDtoToRelease(ReleaseDto dto){
        Release release = new Release();
        release.au = dto.getAu();
        release.eu = dto.getEu();
        release.jp = dto.getJp();
        release.na = dto.getNa();
        return release;
    }
}
