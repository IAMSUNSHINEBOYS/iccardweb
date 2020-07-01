package com.alen.controller;


import com.alen.entity.MerchantZtree;
import com.alen.entity.SmartMerchant;
import com.alen.entity.SmartTerminal;
import com.alen.service.SmartTerminalServcie;
import com.alen.utils.MerchantUtils;
import com.alen.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author LK
 * @version 1.0
 * @date 2020/6/16 21:21
 */
@Controller
@RequestMapping("/admin/smartTerminal")
public class SmartTerminalController extends  BaseController{

    private static final String PRIFIX = "merchant/smartTerminal/";
    @Autowired
    private SmartTerminalServcie smartTerminalServcie;

    /**
     * 系统用户列表
     */
    @RequestMapping("/smartTerminalInput")
    @RequiresPermissions("sys:smartTerminal:list")
    public String shsTerminalInput(ModelMap map, BaseController.Params p, SmartTerminal smartTerminal) {
        List<MerchantZtree> list = MerchantUtils.getMerchantList();
        JSONArray jsonaArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (MerchantZtree m : list) {
            jsonObject = new JSONObject();
            jsonObject.put("id", m.getId());
            jsonObject.put("pId", m.getParentId());
            jsonObject.put("name", m.getName());
            jsonObject.put("open", false);
            jsonaArray.add(jsonObject);
        }
        map.put("mztree", jsonaArray.toString());
        return PRIFIX + "smartTerminalInput";

    }


    @RequestMapping("/smartTerminalList")
    @RequiresPermissions("sys:smartTerminal:list")
    public String smartTerminalList(ModelMap map, BaseController.Params p,SmartTerminal smartTerminal) {
        if (p.getFlag() == 0) {
            return PRIFIX + "smartTerminalQuery";
        }
        List<SmartTerminal> list = smartTerminalServcie.findSmartTerminalList(p.getPager(), smartTerminal, p.getDateMap(), p.getField(), p.getOrder());
        map.put("list",list);
        return PRIFIX + "smartTerminalList";

    }


    /**
     * 添加商户组织
     */
    @RequestMapping("/smartTerminalAdd")
    @RequiresPermissions("sys:smartTerminal:add")
    public String smartTerminalAdd(ModelMap map,String merchantId) {
        List<MerchantZtree> list = MerchantUtils.getMerchantList();
        JSONArray jsonaArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (MerchantZtree m : list) {
            jsonObject = new JSONObject();
            jsonObject.put("id", m.getId());
            jsonObject.put("pId", m.getParentId());
            jsonObject.put("name", m.getName());
            jsonObject.put("open", false);
            jsonaArray.add(jsonObject);
        }
        map.put("mztree", jsonaArray.toString());
        return PRIFIX + "smartTerminalAdd";
    }
}
