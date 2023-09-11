package com.yumpro.ddogo.notice.service;

import com.yumpro.ddogo.common.entity.Notice;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.notice.reprository.NoticeRepository;
import com.yumpro.ddogo.notice.validation.Noticeform;
import com.yumpro.ddogo.user.exception.DataNotFoundException;
import com.yumpro.ddogo.user.reprository.UserRepository;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    //공지사항목록조회 (페이징처리)
    public Page<Notice> getList(int page){

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("notiDate"));     //등록일순
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return noticeRepository.findAll(pageable);
    }

    // 공지사항 등록
    public void add(String notiTitle, String notiContent) {
        System.out.println("등록하기 서비스");
        Notice notice = new Notice();
        notice.setNotiTitle(notiTitle);
        notice.setNotiContent(notiContent);
        notice.setNotiDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    // notiNo값을 주면 notice 값을
    public Notice getNotice(Integer notiNo){
        System.out.println("getNotice진입");
        Optional<Notice> notice = noticeRepository.findById(notiNo);
        if(notice.isPresent()){
            return notice.get();
        }else {
            throw new DataNotFoundException("notice not Found");
        }
    }

    // 공지사항 수정 폼 Notice -> Noticeform
    public Noticeform toNoticeform(Notice notice) {
        Noticeform noticeform = new Noticeform();
        noticeform.setNotiTitle(notice.getNotiTitle());
        noticeform.setNotiContent(notice.getNotiContent());
       return noticeform;
    }


    //수정하기
    public void modify(Notice notice, Noticeform noticeform) {
        notice.setNotiContent(noticeform.getNotiContent());
        notice.setNotiTitle(noticeform.getNotiTitle());
        noticeRepository.save(notice);
    }

    //공지사항 삭제하
    public void noticeDelete(Notice notice) {
        noticeRepository.delete(notice);
    }
}
