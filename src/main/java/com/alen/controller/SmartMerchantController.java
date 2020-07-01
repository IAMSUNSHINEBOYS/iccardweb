package com.alen.controller;


import com.alen.entity.SmartMerchant;
import com.alen.service.*;
import com.alen.utils.R;
import com.alen.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;

/**
 * 商户组织信息表
 * @author LK
 * @version 1.0
 * @date 2020/6/9 20:24
 */
@Controller
@RequestMapping("/admin/smartMerchant")
public class SmartMerchantController extends BaseController{


    private static final String PRIFIX = "merchant/smartMerchant/";

    @Autowired
    private SmartMerchantService smartMerchantService;

    /**
     * 系统用户列表
     */
    @RequestMapping("/smartMerchantList")
    @RequiresPermissions("sys:smartMerchant:list")
    public String smartMerchantList(ModelMap map, Params p, SmartMerchant smartMerchant) {
        if (p.getFlag() == 0) {
            return PRIFIX + "shsMerchantQuery";
        }
        map.put("list", smartMerchantService.findSmartMerchantList(p.getPager(), smartMerchant, p.getDateMap(), p.getField(), p.getOrder()));
        return PRIFIX + "smartMerchantList";
    }

    @RequestMapping("/ztree")
    public String ztree() {
        return PRIFIX + "smartMerchantZtree";
    }

    /**
     * 查询数据列表
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAll(){
        List<Map<String,Object>> listMapData = smartMerchantService.findAllByMap();
        JSONObject result = new JSONObject();
        result.put("code",0);
        result.put("msg","");
        result.put("count",listMapData!=null?listMapData.size():0);
        result.put("data",JSON.toJSON(listMapData));
        return result;
    }

    /**
     * 添加商户组织
     */
    @RequestMapping("/smartMerchantAdd")
    @RequiresPermissions("sys:smartMerchant:add")
    public String smartMerchantAdd(ModelMap map,String merchantId) {
        if(!StringUtils.isBlank(merchantId)){
            List<SmartMerchant> list = smartMerchantService.findSmartMerchantIdByParents(merchantId);
            map.put("smartMerchant",list.get(list.size()-1));
            map.put("parentInfo",findParent(list));
        }
        return PRIFIX + "smartMerchantAdd";
    }

    /**
     * 添加商户组织保存
     */
    @RequestMapping("/smartMerchantAddSave")
    @ResponseBody
    @RequiresPermissions("sys:smartMerchant:add")
    public R shsMerchantAddSave(SmartMerchant smartMerchant, String isChild) {
        smartMerchantService.saveSmartMerchant(smartMerchant,isChild);
        return R.ok("添加成功");

    }

    /**
     * 修改商户组织
     */
    @RequestMapping("/smartMerchantUpdate")
    @RequiresPermissions("sys:smartMerchant:update")
    public String smartMerchantUpdate(ModelMap map,String merchantId) {
        map.put("smartMerchant",smartMerchantService.findSmartMerchantByMerchantId(merchantId));
        map.put("parentInfo",findParent(smartMerchantService.findSmartMerchantIdByParents(merchantId)));
        return PRIFIX + "smartMerchantUpate";
    }

    /**
     * 修改商户组织保存
     */
    @RequestMapping("/smartMerchantUpdateSave")
    @ResponseBody
    @RequiresPermissions("sys:smartMerchant:update")
    public R shsMerchantUpdateSave(SmartMerchant smartMerchant) {
        SmartMerchant sm = smartMerchantService.findSmartMerchantByMerchantId(smartMerchant.getSmtMerchantId());
        if(sm == null){
            return R.err("当前组织不存在");
        }
        smartMerchantService.updateSmartMerchant(smartMerchant);
        return R.ok("修改成功");

    }


    /**
     * 删除商户组织
     */
    @RequestMapping("/smartMerchantDelete")
    @ResponseBody
    @RequiresPermissions("sys:smartMerchant:delete")
    public R SmartMerchantDelete(String merchantId) {
        if (!StringUtils.isBlank(merchantId)) {
            smartMerchantService.deleteByIds(merchantId);
            return R.ok("删除成功");
        } else
            return R.err("删除失败");
    }

    private String findParent(List<SmartMerchant> list){
        StringBuffer result = new StringBuffer();
        for (SmartMerchant sm :list) {
            result.append(sm.getSmtMerchantName()+"\\");
        }
        String data = result.toString();
        return data.length()<=0?"":data.substring(0,data.length()-1);
    }
}
