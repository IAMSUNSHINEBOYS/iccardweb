package com.alen.controller;

import com.alen.entity.LoginRecord;
import com.alen.entity.Online;
import com.alen.service.LoginRecordService;
import com.alen.service.OnlineService;
import com.alen.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录信息
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/19 19:59
 */
@Controller
@RequestMapping("/admin/loginInfo")
public class LoginInfoController extends BaseController {

	private static final String PRIFIX = "query/loginInfo/";
	@Autowired
	private OnlineService onlineService;
	@Autowired
	private LoginRecordService loginRecordService;
	/**
	 * 登录信息页面
	 */
	@RequestMapping("/loginInfo")
	@RequiresPermissions("sys:loginInfo:loginInfo")
	public String loginInfo() {
		return PRIFIX + "loginInfo";
	}

	/**
	 * 在线系统用户列表
	 * 
	 */
	@RequestMapping("/onlineList")
	@RequiresPermissions("sys:online:list")
	public String onlineList(HttpServletRequest request, ModelMap map,
                             Params p, Online online) {
		if (p.getFlag() == 0) {
			return PRIFIX + "online/onlineQuery";
		}
		initPageSize(request, p.getPager(), 16);
		map.put("list",
				onlineService.findOnlineList(p.getPager(), online,
						p.getDateMap(), p.getField(), p.getOrder()));
		return PRIFIX + "online/onlineList";
	}

	/**
	 * 在线系统用户删除
	 * 
	 */
	@RequestMapping("/onlineDelete")
	@ResponseBody
	@RequiresPermissions("sys:online:delete")
	public R onlineDelete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			onlineService.deleteByIds(ids);
			return R.ok("删除成功");
		} else
			return R.err("删除失败");
	}

	/**
	 * 登录记录列表
	 * 
	 */
	@RequestMapping("/loginRecordList")
	@RequiresPermissions("sys:loginRecord:list")
	public String loginRecordList(HttpServletRequest request,ModelMap map, Params p,
			LoginRecord loginRecord) {
		if (p.getFlag() == 0) {
			return PRIFIX + "loginRecord/loginRecordQuery";
		}
		initPageSize(request, p.getPager(), 16);
		map.put("list", loginRecordService.findLoginRecordList(p.getPager(),
				loginRecord, p.getDateMap(), p.getField(), p.getOrder()));
		return PRIFIX + "loginRecord/loginRecordList";
	}

	/**
	 * 登录记录删除
	 * 
	 */
	@RequestMapping("/loginRecordDelete")
	@ResponseBody
	@RequiresPermissions("sys:loginRecord:delete")
	public R loginRecordDelete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			loginRecordService.deleteByIds(ids);
			return R.ok("删除成功");
		} else
			return R.err("删除失败");
	}
}
