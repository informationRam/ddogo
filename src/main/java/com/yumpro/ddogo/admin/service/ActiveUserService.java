package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.repository.ActiveUserRepository;
import com.yumpro.ddogo.common.entity.ActiveUser;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActiveUserService {
    public final ActiveUserRepository activeUserRepository;
    public List<ActiveUser> findByYear(int year) throws NotFoundException {
        System.out.println("진입");
        Optional<List<ActiveUser>> oa = activeUserRepository.findByYear(year);
        if(oa.isPresent()){
            return oa.get();
        } else{
            throw new NotFoundException("정보가 없습니다");
        }
    }
}
