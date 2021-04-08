package com.codetech.www.controller;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.codetech.www.domain.Notice;
import com.codetech.www.domain.User;
import com.codetech.www.domain.UserInfo;
import com.codetech.www.service.AdminService;
import com.codetech.www.service.UsersService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    // C:/Spring/Notice
    @Value("${saveFolderName}")
    private String saveFolder;
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
    	return "admin/index";
    }
    
    @RequestMapping(value = "/userList")
	public ModelAndView userList(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page, ModelAndView mv,
			@RequestParam(value = "search_field", defaultValue = "-1") int index,
			@RequestParam(value = "search_word", defaultValue = "") String search_word,
			@RequestParam(value = "check_state", defaultValue = "0") int state) {
    	
		List<User> Userslist = null;
		List<UserInfo> Infolist = null;
		
		int limit = 10;
		int listcount = adminService.getSearchListCount(index, state, search_word);
		
		Userslist = adminService.getUsersSearchList(index, state, search_word, page, limit);
		Infolist = adminService.getInfoSearchList(index, state, search_word, page, limit);
		
		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;
		
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		mv.setViewName("admin/user-list");

		mv.addObject("page", page); // 현재 페이지 수
		mv.addObject("maxpage", maxpage); // 최대 페이지 수

		// 현재 페이지에 표시할 첫 페이지 수
		mv.addObject("startpage", startpage);

		// 현재 페이지에 표시할 끝 페이지 수
		mv.addObject("endpage", endpage);

		mv.addObject("listcount", listcount); // 총 글의 수

		mv.addObject("Userslist", Userslist);
		mv.addObject("Infolist", Infolist);

		// 해당 페이지의 글 목록을 갖고 있는 리스트
		mv.addObject("search_field", index);
		mv.addObject("search_word", search_word);
		mv.addObject("check_state", state);
		
		return mv;
	}
    

    @RequestMapping(value = "/noticeList", method = RequestMethod.GET)
    public ModelAndView noticeList
            (@RequestParam(value = "page", defaultValue = "1", required = false) int page, ModelAndView mv) {
        int limit = 10; // 한 화면에 출력할 레코드 갯수
        int listcount = adminService.getNoticeListCount(); // 샐랙트 구문을 통해 리스트 값을 가져옴

        // 총 페이지 수
        int maxpage = (listcount + limit - 1) / limit;

        // 현재 페이지에 보여줄 시작 페이지수(1, 11, 21 등...)
        int startpage = ((page - 1) / 10) * 10 + 1;

        // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
        int endpage = startpage + 10 - 1;

        if (endpage > maxpage)
            endpage = maxpage;

        // List<Notice> noticelist = userService.getNoticeList(page, limit); // 리스트를 받아온다.

        mv.setViewName("notice/noticeList");
        mv.addObject("page", page);
        mv.addObject("maxpage", maxpage);
        mv.addObject("startpage", startpage);
        mv.addObject("endpage", endpage);
        mv.addObject("listcount", listcount);
        // mv.addObject("noticelist", noticelist);
        mv.addObject("limit", limit);

        return mv;
    }

    @RequestMapping(value = "/noticeWrite", method = RequestMethod.GET)
    public String noticeWrite() {
        return "admin/noticeWrite";
    }

    // 원래 파일명, 파일 저장 위치
    private String fileDBName(String fileName, String saveFolder) {
        // 새로운 폴더 이름: 오늘 년 + 월 + 일
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);        // 오늘의 "년도"를 구한다.
        int month = c.get(Calendar.MONTH) + 1;    // 오늘의 "월"을 구한다.
        int date = c.get(Calendar.DATE);        // 오늘의 "일"을 구한다.

        String homedir = saveFolder + year + "-" + month + "-" + date;
        logger.info(homedir);

        File path1 = new File(homedir);

        // 경로에 폴더가 존재하지 않으면 새로운 폴더를 생성
        if (!(path1.exists())) {
            path1.mkdir(); // 새로운 폴더를 생성
        }

        Random r = new Random();
        int random = r.nextInt(100000000);

        /* 확장자 구하기 시작 */
        int index = fileName.lastIndexOf("."); // 예) [.exe | .jpg | .gif] 등등...
        /* 문자열에서 특정 문자열의 위치 값(index)를 반환한다.
         * indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
         * lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환한다.
         * (파일명에 점이 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴한다.) */
        logger.info("index: " + index);

        // index는 a/b/c.png 중 a/b/c. 까지만 구했기 때문에 + 1을 해줘서 확장자 명 위치로 옮긴다.
        String fileExtension = fileName.substring(index + 1);
        logger.info("fileException: " + fileExtension);
        /* 확장자 구하기 끝 */

        // 새로운 파일명
        String refileName = "bbs" + year + month + date + random + "." + fileExtension;
        logger.info("refileName: " + refileName);

        // 오라클 디비에 저장될 파일 명
        String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
        logger.info("fileDBName: " + fileDBName);

        return fileDBName;
    }
}
