package com.yumpro.ddogo.qna.controller;

import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.common.entity.QnaSolve;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import com.yumpro.ddogo.qna.service.QnaService;
import com.yumpro.ddogo.admin.service.UserListService;
import com.yumpro.ddogo.qna.validation.QnaAddForm;
import com.yumpro.ddogo.qna.validation.QnaSolveAddForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;
    private final UserListService userListService;

    //메일용 html파일 문서화
    public String readHTMLFileAsString(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            content = "HTML 파일을 불러오지 못했습니다.";
        }
        return content;
    }

    //(all)문의글 리스트 보기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String qnaList(Model model, @RequestParam Map<String, Object> map,
                          @RequestParam(value = "page", defaultValue = "1") int currentPage,
                          @RequestParam(value="search", required=false) String search,
                          @RequestParam(value="searchCategory", required=false) String searchCategory,
                          @RequestParam(value="sortField", required=false) String sortField,
                          @RequestParam(value="sortOrder", required=false) String sortOrder,
                          Principal principal) {
        int limit = 15; // 페이지당 보여줄 아이템 개수
        int offset = (currentPage - 1) * limit;

        if (sortField == null) {
            sortField = "qna_no";
        }
        if (sortOrder == null) {
            sortOrder = "desc";
        }

        map.put("limit", limit);
        map.put("offset", offset);
        map.put("search", search);
        map.put("searchCategory", searchCategory);
        map.put("sortField", sortField);
        map.put("sortOrder", sortOrder);

        List<QnaListDTO> qnaList = qnaService.getQnaList(map);

        int totalCount = qnaService.getQnaListCount(map); // 전체 데이터 수를 가져오는 메서드를 추가해야 합니다.
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        model.addAttribute("totalCnt",totalCount);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("qnaList",qnaList);
        model.addAttribute("userId",principal.getName());
        model.addAttribute("search", search);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

        return "qna/qna_list";
    }

    //(유저)문의글 작성 폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String qnaAdd(QnaAddForm qnaAddForm){
        return "qna/qna_add";
    }

    //(유저)문의글 작성 처리
    @PreAuthorize("isAuthenticated()")//로그인인증=>로그인이 필요한 기능
    @PostMapping("/add")
    public String qnaAdd(@Valid QnaAddForm qnaAddForm, BindingResult bindingResult,
                         Principal principal) {
        //1.파라미터받기 qna_title,qna_content,qna_pwd
        //2.비즈니스로직
        if(bindingResult.hasErrors()) { //에러가 존재하면
            return "qna/qna_add";
        }

        Optional<User> user = userListService.findUserByUserId(principal.getName());//user정보가져오기

        qnaService.add(qnaAddForm.getQna_title(),qnaAddForm.getQna_content(),qnaAddForm.getQna_pwd(),user.get());

        //3.Model&4.View
        return "redirect:/qna/list"; //(질문목록조회요청을 통한)질문목록페이지로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String qnaDetailGet(@PathVariable int id,@RequestParam(value="inputPwd", required = false) String inputPwd,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "문의글 상세보기는 비밀번호가 일치해야 조회할 수 있습니다");
        return"redirect:/qna/list";
    }

    //문의글 상세 보기 (비밀번호 확인 / 관리자는 free)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/detail/{id}")
    public String qnaDetail(@PathVariable int id, @RequestParam(value="inputPwd", required = false) String inputPwd, Model model, Principal principal, QnaSolveAddForm qnaSolveAddForm,RedirectAttributes redirectAttributes){

        if(inputPwd==null){
            redirectAttributes.addFlashAttribute("error", "문의글 상세보기는 비밀번호가 일치해야 조회할 수 있습니다");
            return "redirect:/qna/list";
        }

        Qna qna = qnaService.getQnaById(id);

        if(!principal.getName().equals("admin")){
            if(!qna.getQnaPwd().equals(inputPwd)){
                redirectAttributes.addFlashAttribute("error", "문의글 상세보기는 비밀번호가 일치해야 조회할 수 있습니다");
                return "redirect:/qna/list";
            }
        }

        QnaSolve qnaSolve = qnaService.getQnaSolveByQna(qna);

        String userId=principal.getName();
        String userRole=null;
        if(userId.equals("admin")){
            userRole="admin";
        }else{
            userRole="user";
        }

        model.addAttribute("qnaSolve",qnaSolve);
        model.addAttribute("qna",qna);
        model.addAttribute("userRole",userRole);
        return "qna/qna_detail";
    }

    //답변 등록 처리
    @PreAuthorize("isAuthenticated()")//로그인인증
    @PostMapping("/solve/add/{id}")
    public String addAnswer(@PathVariable("id") Integer id,
                            @Valid QnaSolveAddForm qnaSolveAddForm, BindingResult bindingResult,
                            Model model, Principal principal){
        //1.파라미터 받기
        //2.비지니스로직
        //아이디로 질문 가져오기
        Qna qna = qnaService.getQnaById(id);
        QnaSolve qnaSolve = qnaService.getQnaSolveByQna(qna);

        if ( !principal.getName().equals("admin") ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"권한이 없습니다.");
        }
        String userId=principal.getName();
        String userRole=null;
        if(userId.equals("admin")){
            userRole="admin";
        }else{
            userRole="user";
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("qna",qna);
            model.addAttribute("qnaSolve",qnaSolve);
            model.addAttribute("user",principal.getName());
            model.addAttribute("userRole",userRole);
            return "qna/qna_detail";
        } else{

        String text = readHTMLFileAsString("src/main/resources/templates/qna/qna_mail.html");
        //답변 저장 로직+질문 상태변경
        qnaService.Solveadd(qnaSolveAddForm.getQnaSolveTitle(),qnaSolveAddForm.getQnaSolveContent(),qna);
        userListService.sendSimpleEmail(qna.getUser().getEmail(), "[또갈지도]답변이 등록되었습니다", text);
        }

        Qna qna1 = qnaService.getQnaById(id);
        QnaSolve qnaSolve1 = qnaService.getQnaSolveByQna(qna1);

        //3.Model
        model.addAttribute("qnaSolve",qnaSolve1);
        model.addAttribute("qna",qna1);
        model.addAttribute("user",principal.getName());
        model.addAttribute("userRole",userRole);

        //4.View
        return "qna/qna_detail";
    }

    //답변 수정 폼 보여줘
    @PreAuthorize("isAuthenticated()")//로그인인증
    @GetMapping("/solve/modify/{id}")
    public String qnaSolveMofiFrom(@PathVariable int id, Model model, Principal principal, QnaSolveAddForm qnaSolveAddForm, RedirectAttributes redirectAttributes){

        Qna qna = qnaService.getQnaById(id);
        QnaSolve qnaSolve = qnaService.getQnaSolveByQna(qna);

        if(!principal.getName().equals("admin")){
            redirectAttributes.addFlashAttribute("error", "문의 답글은 관리자만 수정할 수 있습니다");
            return "redirect:/qna/list";
        }

        qnaSolveAddForm.setQnaSolveTitle(qnaSolve.getQnaSolveTitle());
        qnaSolveAddForm.setQnaSolveContent(qnaSolve.getQnaSolveContent());

        model.addAttribute("qna",qna);

        return "qna/qna_modify";
    }

    //답변수정처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/solve/modify/{id}")
    public String qnaSolveMofiy(@PathVariable int id,@Valid QnaSolveAddForm qnaSolveAddForm,BindingResult bindingResult,Model model,Principal principal,RedirectAttributes redirectAttributes){
        if(!principal.getName().equals("admin")){
            redirectAttributes.addFlashAttribute("error", "답변 수정은 관리자만 가능합니다");
            return "redirect:/qna/list";
        }

        if(bindingResult.hasErrors()){  //유효성검사시 에러가 발생하면
            return "question_form"; //question_form.html문서로 이동
        }

        Qna qna = qnaService.getQnaById(id);
        QnaSolve qnaSolve = qnaService.getQnaSolveByQna(qna);
        String title=qnaSolveAddForm.getQnaSolveTitle();
        String content=qnaSolveAddForm.getQnaSolveContent();

        qnaService.moidfy(qnaSolve,title,content);

        QnaSolve qnaSolve1 = qnaService.getQnaSolveByQna(qna);

        //3.Model
        model.addAttribute("qnaSolve",qnaSolve1);
        model.addAttribute("qna",qna);
        model.addAttribute("user",principal.getName());

        //4.View
        return "qna/qna_detail";
    }

    //답변 삭제
    ///solve/delete/${qna.qnaNo}
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/solve/delete/{id}")
    public String qnaSolveDelete(@PathVariable("id") Integer id,Principal principal,RedirectAttributes redirectAttributes){
        if(!principal.getName().equals("admin")){
            redirectAttributes.addFlashAttribute("error", "답글 삭제는 관리자만 가능합니다");
            return "redirect:/qna/list";
        }

        Qna qna=qnaService.getQnaById(id);
        QnaSolve qnaSolve=qnaService.getQnaSolveByQna(qna);

        qnaService.delete(qnaSolve,qna);

        return "redirect:/qna/list";
    }

    //문의사항 수정폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("modify/{id}")
    public String qnaModiForm(QnaAddForm QnaAddForm,@PathVariable("id") Integer id,Principal principal,Model model, RedirectAttributes redirectAttributes){
        Qna qna = qnaService.getQnaById(id);
        if ( !qna.getUser().getUserId().equals(principal.getName()) ) {
            redirectAttributes.addFlashAttribute("error", "작성자 본인 이외의 사용자에게는 수정 권한이 없습니다");
            return "redirect:/qna/list";
        }
        QnaAddForm.setQna_title(qna.getQnaTitle());
        QnaAddForm.setQna_content(qna.getQnaContent());
        model.addAttribute("qna",qna);

        return "qna/qna_modi";
    }

    //(유저)문의글 작성 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("modify/{id}")
    public String qnaModi(@Valid QnaAddForm qnaAddForm, BindingResult bindingResult, @PathVariable int id, Principal principal, Model model){

        Qna qna = qnaService.getQnaById(id);

        if ( !qna.getUser().getUserId().equals(principal.getName()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        if(qna.getQnaSolved()=='Y'){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        if(bindingResult.hasErrors()){  //유효성검사시 에러가 발생하면
            model.addAttribute("qna",qna);
            return "qna/qna_modi";
        }

        if(!qnaAddForm.getQna_pwd().equals(qna.getQnaPwd())){
            bindingResult.rejectValue("qna_pwd","qnaPwdInCorrect","문의 비밀번호가 일치하지 않습니다");
            model.addAttribute("qna",qna);
            return "qna/qna_modi";
        }

        String title=qnaAddForm.getQna_title();
        String content=qnaAddForm.getQna_content();

        qnaService.modi(qna,title,content);

        Qna qna1 = qnaService.getQnaById(id);
        QnaSolve qnaSolve = qnaService.getQnaSolveByQna(qna1);
        String userRole=null;
        if(principal.getName().equals("admin")){
            userRole="admin";
        }else{
            userRole="user";
        }

        model.addAttribute("qnaSolve",qnaSolve);
        model.addAttribute("qna",qna1);
        model.addAttribute("userRole",userRole);
        return "qna/qna_detail";
    }


    //문의사항 삭제
    ///solve/delete/${qna.qnaNo}
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String qnaDelete(@PathVariable("id") Integer id,Principal principal, RedirectAttributes redirectAttributes){
        Qna qna = qnaService.getQnaById(id);

        if ( !qna.getUser().getUserId().equals(principal.getName()) ) {
            redirectAttributes.addFlashAttribute("error", "작성자 본인 이외의 사용자에게는 삭제 권한이 없습니다");
            return "redirect:/qna/list";
        }

        qnaService.qnaDelete(qna);

        return "redirect:/qna/list";
    }

    @GetMapping("/error")
    public String qnaError(){
        return "qna/error_forbidden";
    }
}