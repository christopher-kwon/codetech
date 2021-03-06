package com.codetech.www.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetech.www.dao.MenuDAO;
import com.codetech.www.dao.OrderDAO;
import com.codetech.www.dao.StoreDAO;
import com.codetech.www.dao.UsersDAO;
import com.codetech.www.domain.Point;
import com.codetech.www.domain.Store;
import com.codetech.www.domain.User;
import com.codetech.www.domain.UserInfo;
import com.codetech.www.domain.UserPlusInfo;

@Service
public class OwnerServiceImpl implements OwnerService {
	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceImpl.class);

	@Autowired
	private StoreDAO dao;

	@Autowired
	private UsersDAO udao;

	@Autowired
	private OrderDAO odao;

	@Autowired
	private MenuDAO mdao;

	@Override
	public List<Store> getStoreForMain() {
		return dao.getStoreForMain();
	}

	@Override
	public List<Store> getStoreForReviewList(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getStoreForReviewList(map);
	}

	@Override
	public List<Store> getStoreForSearchList(int page, int limit, String searchWord) {
		String searchWordLike = "%" + searchWord + "%";
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("searchWord", searchWordLike);
		return dao.getStoreForSearchList(map);

	}

	@Override
	public List<UserInfo> getAdminList() {
		return udao.getAdminList();
	}

	@Override
	public List<Store> getStoreForMainList(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getStoreForMainList(map);
	}

	@Override
	public int getListCount() {
		return dao.getListCount();

	}

	@Override
	public int getListCountforMainList() {
		return dao.getListCountforMainList();

	}

	@Override
	public int getListCountforSearchList(String searchWord) {
		String searchWordLike = "%" + searchWord + "%";
		return dao.getListCountforSearchList(searchWordLike);
	}

	@Override
	public Store getMap(String searchWord) {
		String searchWordLike = "%" + searchWord + "%";
		return dao.getMap(searchWordLike);
	}

	@Override
	public User getAdmin(int user_id) {
		return udao.getAdmin(user_id);

	}

	@Override
	public UserInfo getAdminInfo(int user_id) {
		return udao.getAdminInfo(user_id);
	}

	@Override
	public Store getStore(int store_id) {
		return dao.getStore(store_id);
	}

	@Override
	public UserPlusInfo getOwnerInfo(int user_id) {
		return udao.user_total_info(user_id);
	}

	@Override
	public List<Store> getStoreName() {
		return dao.getStoreName();

	}

	@Override
	public List<UserInfo> getUserName() {
		return udao.getUserName();

	}

	@Override
	public int plusOrderCount(int menu_id) {
		return mdao.plusOrderCount(menu_id);
	}

	@Override
	public int getStoreId(int menu_id) {
		return mdao.getStoreId(menu_id);
	}

	@Override
	public int order(int price, int user_id, int store_id, int[] m_num, int[] p_price, int[] p_num, int point_value) {
		int rtn = -1;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("price", price);
		map.put("store_id", store_id);
		map.put("user_id", user_id);

		int result = odao.order(map);

		if (result != 0) {
			int key = map.get("order_id");
			int detailInsert = 0;
			for (int i = 0; i < m_num.length; i++) {
				map.put("order_id", key);
				map.put("m_num", m_num[i]);
				logger.info("m_num = " + m_num[i]);
				map.put("p_price", p_price[i] * p_num[i]);
				logger.info("p_price = " + p_price[i] * p_num[i]);

				map.put("p_num", p_num[i]);
				logger.info("p_num = " + p_num[i]);

				detailInsert = odao.orderDetail(map);
				if (detailInsert == 1) {
				}
				
				HashMap<String, Object> umap = new HashMap<String, Object>();
				UserPlusInfo ui = udao.user_total_info(user_id);
				int prev_point = ui.getPoint();
				int using_point = point_value;
				int total_point = prev_point - using_point;
				if(total_point >= 0) {
					Point point = new Point();
		        	point.setPoint_value(point_value);
		        	point.setOrder_id(key);
		        	point.setStore_id(store_id);
		        	point.setUser_id(user_id);
		        	point.setPoint_type("use");
		        	int insertPointResult = odao.insertPoint(point);
		        	
		        	if(insertPointResult  == 1) {
		                umap.put("user_id", user_id);
		                umap.put("point", total_point);
		        		int pointUpdateResult = udao.updatePoint(umap);
						if (pointUpdateResult != 1) {
			            } else {
			            }
		        	}else {
		        	}
				}else {
					
				}
				/*HJE used points update end*/
			}
			return detailInsert;
		}
		logger.info("order : " + user_id);
		return rtn;
	}

	@Override
	public int delCartList(int menu_id) {
		return mdao.delCartList(menu_id);
	}

	@Override
	public int revoke(int user_id) {
		return udao.revoke(user_id);
	}

	@Override
	public int getOrderStore(int store_id) {
		return udao.getOrderStore(store_id);

	}

	@Override
	public int getStore_owner(int ordered_store) {
		return odao.getStore_owner(ordered_store);
	}

	@Override
	public List<UserPlusInfo> getAdminSearchList(String search_val) {
		String searchWordLike = "%" + search_val + "%";
		return udao.getAdminSearchList(searchWordLike);
	}

	@Override
	public int grantAdminConfirm(String user_email) {
		return udao.grantAdminConfirm(user_email);
	}

}
