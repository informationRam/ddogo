package com.yumpro.ddogo.searchmap.service;

import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.repository.SearchMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchMapService {

    /*private final SearchMapRepository searchMapRepository;
    public void insertDTO(SearchMapDTO searchMapDTO) {
        searchMapRepository.testInsert(searchMapDTO);
    }

    // 1) hotplace 테이블에 insert
    //  리턴: 방금 hotplace에 입력된 pk인 hotplace_no의 값
    public int insertHotpalce(SearchMapDTO searchMapDTO) {
        //searchMapRepository.insertHotplace();
        return 0;
    }

    // 2) mymap 테이블에 insert
    //  리턴: 방금 mymap에 입력된 pk인 map_no의 값
    public int insertMyMap(SearchMapDTO searchMapDTO) {
        //searchMapRepository.insertMyMap();
        return 0;
    }

    // 4) 분설결과와 함께 emoreview 테이블에 insert
    public void insertEmoReview(double emoResult, SearchMapDTO searchMapDTO) {
    }*/
}
