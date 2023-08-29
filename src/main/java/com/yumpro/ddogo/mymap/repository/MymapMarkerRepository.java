package com.yumpro.ddogo.mymap.repository;

import com.yumpro.ddogo.mymap.dto.MymapMarkerDTO;
import com.yumpro.ddogo.mymap.entity.Hotplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository인터페이스를 상속하고 있다
// <Repository의 대상이 되는 Entity타입,  Entity타입의 PK타입>
//여기에서는 <Hotplace,Integer> Repository의 대상이 되는 Answer의 PK는  Integer
public interface MymapMarkerRepository extends JpaRepository<Hotplace, Integer> {

    @Query("SELECT NEW com.yumpro.ddogo.mymap.dto.MymapMarkerDTO(h.hotplaceName, h.lat, h.lng) FROM Hotplace h")
    List<MymapMarkerDTO> findLatLngNames();

}
