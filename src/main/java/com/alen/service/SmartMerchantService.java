package com.alen.service;


import com.alen.entity.Admin;
import com.alen.entity.MerchantZtree;
import com.alen.entity.SmartMerchant;
import com.alen.mapper.SmartMerchantMapper;
import com.alen.shiro.ShiroUtils;
import com.alen.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户组织信息表
 * @author LK
 * @version 1.0
 * @date 2020/6/9 20:19
 */
@Service
public class SmartMerchantService {

    @Autowired
    private SmartMerchantMapper smartMerchantMapper;

    /**
     * 通过条件查询商户组织列表
     *
     * @param pager
     *            分页对象
     * @param smartMerchant
     *            商户组织对象
     * @param dateMap
     *            时间条件
     * @param field
     *            排序属性
     * @param order
     *            0降序、1升序
     */
    public List<SmartMerchant> findSmartMerchantList(Pager pager, SmartMerchant smartMerchant, Map<String, String> dateMap, String field, int order) {
        pager.addSortable("fd_shs_datetime").addOrder(field, order).startPage();
        return smartMerchantMapper.getSmartMerchantList(smartMerchant, SqlDateUtils.getDateMap(dateMap));
    }

    /*
     * 查询所有的数据map格式
     * @return
     */
    public List<Map<String,Object>> findAllByMap(){
        return smartMerchantMapper.getAllByMap();
    }

    /*
     * 查询所有的数据对象格式
     * @return
     */
    public List<MerchantZtree> findAllByList(){
        return smartMerchantMapper.getdAllByList();
    }

    /**
     * 查询父类信息
     * @param parentId
     * @return
     */
    public SmartMerchant findSmartMerchantByParent(String parentId){
        return smartMerchantMapper.getSmartMerchantByParent(parentId);
    }

    /**
     * 通过ID查询
     * @param merchantId
     * @return
     */
    public SmartMerchant findSmartMerchantByMerchantId(String merchantId){
        return smartMerchantMapper.getSmartMerchantByMerchantId(merchantId);
    }

    /**
     * 查询添加子集最大MerchantId
     * @return
     */
    public String findMaxMerchantId(String merchantId){
        return smartMerchantMapper.getMaxMerchantId(merchantId);
    }


    /**
     * 添加商户管理
     * @param
     *          smartMerchant
     * @param
     *          isChild 是否是否在下一级添加
     */
    public R saveSmartMerchant(SmartMerchant smartMerchant, String isChild){
        Admin operator = ShiroUtils.getAdmin();
        //如果shsMerchant为空，或者shs_MerchantId为空，说明是在第一级别添加
        if(smartMerchant == null){
            return R.err("添加失败");
        }
        SmartMerchant newSM = new SmartMerchant();
        newSM.setSmtUnitId("00007"); //商户号
        String maxMerchantId = "001";
        //如果是为空，那就是第一级
        if(StringUtils.isBlank(smartMerchant.getSmtMerchantId())){
            maxMerchantId = findMaxMerchantId("0");
            maxMerchantId = StringUtils.isBlank(maxMerchantId)?"000":maxMerchantId;
            newSM.setSmtParentId("0");
            newSM.setSmtLevel(1);
            newSM.setSmtLeast(0);
        }else{
            //
            SmartMerchant peerSm = findSmartMerchantByMerchantId(smartMerchant.getSmtMerchantId());
            if(!StringUtils.isBlank(isChild)){ //如果isChild 不为空，那么就在当前选中下添加子集

                maxMerchantId = findMaxMerchantId(peerSm.getSmtMerchantId());
                maxMerchantId = StringUtils.isBlank(maxMerchantId)?peerSm.getSmtMerchantId()+"000":maxMerchantId;
                newSM.setSmtParentId(smartMerchant.getSmtMerchantId());
                newSM.setSmtLevel(peerSm.getSmtLevel()+1);
                newSM.setSmtLeast(0);
            }else{ //添加同级
                maxMerchantId = findMaxMerchantId(peerSm.getSmtParentId());
                maxMerchantId = StringUtils.isBlank(maxMerchantId)?peerSm.getSmtMerchantId()+"000":maxMerchantId;
                newSM.setSmtParentId(peerSm.getSmtParentId());
                newSM.setSmtLevel(peerSm.getSmtLevel());
                newSM.setSmtLeast(0);
            }
        }
        newSM.setSmtMerchantId(getMerchantId(maxMerchantId));
        newSM.setSmtUserId(String.valueOf(operator.getId()));
        newSM.setSmtDateTime(new Date());
        newSM.setSmtMerchantName(smartMerchant.getSmtMerchantName());
        smartMerchantMapper.saveSmartMerchant(newSM);
        return R.ok("添加成功");
    }

    /**
     * 修改
     * @param smartMerchant
     */
    public void updateSmartMerchant(SmartMerchant smartMerchant){
        SmartMerchant updateSM = new SmartMerchant();
        updateSM.setSmtMerchantId(smartMerchant.getSmtMerchantId());
        updateSM.setSmtMerchantName(smartMerchant.getSmtMerchantName());
        smartMerchantMapper.updateSmartMerchant(updateSM);
    }
    /**
     * 删除（可以删除子集）
     *
     * @param merchantId
     *            用户ID集合
     */
    public void deleteByIds(String merchantId) {
        smartMerchantMapper.deleteSmartMerchant(merchantId);
    }

    /**
     * 查询所有父类信息
     * @param
     *          merchantId 组织id
     * @return
     */
    public List<SmartMerchant> findSmartMerchantIdByParents(String merchantId){
        return smartMerchantMapper.getSmartMerchantIdByParents(merchantId);
    }

    /**
     *  计算MaxMerchantId
     *  @param
     *          maxMerchantId
     * @return
     */
    public synchronized String getMerchantId(String maxMerchantId){
        String merchantId = maxMerchantId.substring(0,maxMerchantId.length()-3);
        if(StringUtils.isBlank(maxMerchantId)){
            merchantId = "001";
        }else{
            //截取后三位进行计算
            int id = Integer.valueOf(maxMerchantId.substring(maxMerchantId.length()-3,maxMerchantId.length()));
            //计算出id转string的长度，计算需要补齐多少位
            String id_len = String.valueOf(id);
            //计算
            for (int i = 0; i < 3-id_len.length(); i++) {
                merchantId += "0";
            }
            merchantId += (id+1);
        }
        return merchantId;
    }

    public static void main(String[] arg){
        //System.err.println("----"+getMerchantId("001"));
    }

    public List<MerchantZtree> getList(){
        return MerchantUtils.getMerchantList();
    }
}
