package com.alen.controller;

import com.alen.service.AdminService;
import com.alen.utils.MenuUtills;
import com.alen.utils.PostRequest;
import com.alen.utils.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 首页
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {
	@Autowired
	private AdminService adminService;

	/**
	 * 首页
	 */
	@RequestMapping("/index")
	@RequiresPermissions("sys:index:index")
	public String index(ModelMap map) {
		map.put("operator", adminService.findAdminById(getOperator().getId()));
		map.put("list", MenuUtills.getMainMenuList(getOperator().getId()));
		return "index";
	}

	/**
	 * 获取天气
	 */
	@RequestMapping("/getWeather")
	@ResponseBody
	@RequiresPermissions("sys:index:index")
	public R getWeather(ModelMap map, String city) throws Exception {
		String url = "http://restapi.amap.com/v3/weather/weatherInfo";
		String ret = PostRequest.httpPost(url,
				"key=0911f3ab572e092c97c17e1a83f6b60f&city=" + city);
		JSONObject json = JSONObject.parseObject(ret);
		String status = json.getString("status");
		if ("1".equals(status)) {
			JSONArray results = json.getJSONArray("lives");
			JSONObject data = results.getJSONObject(0);
			return R.ok().put("city", data.getString("city"))
					.put("weather", data.getString("weather"))
					.put("temperature", data.getString("temperature"));
		} else {
			return R.err("获取天气失败");
		}
	}

	/**
	 * 主页
	 */
	@RequestMapping("/home")
	@RequiresPermissions("sys:index:home")
	public String home() {
		return "/home/home";
	}
}
