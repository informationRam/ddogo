package com.yumpro.ddogo.myMap;

import com.yumpro.ddogo.mymap.controller.MymapController;
import com.yumpro.ddogo.mymap.service.MymapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MymapController.class) // MymapController를 테스트하기 위한 환경 설정
public class myMapControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc를 주입

    @Autowired
    private MymapController mymapController; // 테스트 대상 컨트롤러 주입
    @MockBean
    private MymapService mymapService;
//    @Test
//        // 컨트롤러 호출 시, 로그인한 사용자로 시뮬레이션하기 위해 @WithMockUser 어노테이션이 사용된다.
//    @WithMockUser(username = "testuser", roles = {"USER"})
//    public void testControllerWithLoggedInUser() {
//        when(mymapService.getHotplacesByUserNo(100)).thenReturn(new ArrayList<>());
//        // 아래와 같이 mockMvc를 사용하여 컨트롤러를 호출할 수 있습니다.
//        // 예를 들어, GET 요청을 보내는 경우:
//
//    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testShowUserMyMap() throws Exception {
        mockMvc.perform(get("/mymap/user/100")) // GET 요청을 보냄
                .andExpect(status().isOk()); // HTTP 상태코드가 200 OK인지 확인
    }
}