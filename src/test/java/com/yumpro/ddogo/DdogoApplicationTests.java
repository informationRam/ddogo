package com.yumpro.ddogo;

import com.yumpro.ddogo.mymap.dto.MymapMarkerDTO;
import com.yumpro.ddogo.mymap.entity.Hotplace;
import com.yumpro.ddogo.mymap.repository.MymapMarkerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DdogoApplicationTests {

	@Autowired
	private MymapMarkerRepository mymapMarkerRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		List<MymapMarkerDTO> mymapMarkerDTOS = mymapMarkerRepository.findLatLngNames();
		// 결과가 비어있지 않은지 확인
		Hotplace hotplace = Hotplace.get();
		assertEquals("사운드온",mymapMarkerDTOS.getLatLngNames());
	}

}
