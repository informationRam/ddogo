package com.yumpro.ddogo.searchmap.service;

import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.repository.SearchMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchMapService {

    private SearchMapRepository searchMapRepository;
    public void insertDTO(SearchMapDTO searchMapDTO) {
        searchMapRepository.testInsert(searchMapDTO);
    }
//    private final SearchMapRepository searchMapRepository;
//    public void deleteBoardVO(SearchMapDTO searchMapDTO){
//        searchMapRepository.addMyMap(searchMapDTO);
//    }
}
