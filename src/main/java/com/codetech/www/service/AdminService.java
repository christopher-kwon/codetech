package com.codetech.www.service;

import java.util.List;

import com.codetech.www.domain.Menu;
import com.codetech.www.domain.Notice;
import com.codetech.www.domain.ReportStoreList;
import com.codetech.www.domain.ReportUserList;
import com.codetech.www.domain.StoreInfoList;
import com.codetech.www.domain.UserPlusInfo;

public interface AdminService {
	public List<UserPlusInfo> getUsersSearchList(int index, int state, String search_word, int page, int limit);
	public int getSearchListCount(int index, int state, String search_word);

	public int user_susp(String user_id);		// 유저 정지
	public int user_reac(String user_id);		// 유저 재활동
	public int user_banned(String user_id);		// 유저 추방
	public int user_inac(String user_id);		// 본인 탈퇴

	
	public int getPartnerSearchListCount(int index, int state, String search_word);
	public List<StoreInfoList> getPartnerSearchList(int index, int state, String search_word, int page, int limit);

	public int store_act(String store_id, String owner_id); 		// 가게 재활동
	public int store_susp(String store_id); 	// 가게 정지
	public int store_termi(String store_id, String owner_id);	// 가게 재계약 (보류)
	
	public List<Menu> getStoreMenuList(String store_id);
	
	public List<Notice> getNoticeList(String search_text, int index, int page, int limit);
	public int getNoticeListCount(String search_text, int index);
	
	public void insertNotice(Notice notice);
	
	public Notice noticeView(int notice_id);
	
	int setReadCountUpdate(int num);
	
	public int modifyAction(Notice noticeModify);
	
	public int noticeDelete(int notice_id);
	
	public int getRULcount(int index, String search_text);
	public List<ReportUserList> getRUL(int index, String search_text, int page, int limit);
	public int RULstatusChange(int user_report_id);
	public int RULstatusCompleted(int user_report_id);
	
	public int getRSLcount(int index, String search_text);
	public List<ReportUserList> getRSL(int index, String search_text, int page, int limit);
	public int RSLstatusChange(int user_report_id);
	public int RSLstatusCompleted(int user_report_id);
	
	public int getRUScount(int index, String search_text);
	public List<ReportStoreList> getRUS(int index, String search_text, int page, int limit);
	public int RUSstatusChange(int store_report_id);
	public int RUSstatusCompleted(int store_report_id);
}
