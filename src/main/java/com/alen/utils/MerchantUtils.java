package com.alen.utils;

import com.alen.entity.MerchantZtree;
import com.alen.service.SmartMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户组织管理
 * @author LK
 * @version 1.0
 * @date 2020/6/17 0:00
 */
@Component
public class MerchantUtils {

    private static SmartMerchantService shsMerchantService;

    @Autowired
    public void setSHSMerchantService(SmartMerchantService shsMerchantService) {
        MerchantUtils.shsMerchantService = shsMerchantService;
    }

    /**
     * 获取商户组织列表
     * @return
     */
    public static List<MerchantZtree> getMerchantList(){
        List<MerchantZtree> list = shsMerchantService.findAllByList();
        List<MerchantZtree> topList = new ArrayList<MerchantZtree>();
        List<MerchantZtree> childList = new ArrayList<MerchantZtree>();
        List<MerchantZtree> listdest = new ArrayList<MerchantZtree>();
        for (MerchantZtree m : list) {
            if ("0".equals(m.getParentId()))
                topList.add(m);
            else
                childList.add(m);
        }
        for (MerchantZtree m : topList) {
            listdest.add(m);
            m.setMlevel(1);
            recur(childList, 1, listdest, m.getId());
        }
        return listdest;
    }

    /**
     * 商户组织递归
     */
    private static void recur(List<MerchantZtree> listsrc, int level,
                              List<MerchantZtree> listdest, String parentId) {
        level++;
        for (MerchantZtree m : listsrc) {
            if (parentId.equals(m.getParentId())) {
                listdest.add(m);
                m.setMlevel(level);
                recur(listsrc, level, listdest, m.getId());
            }
        }
    }
}
