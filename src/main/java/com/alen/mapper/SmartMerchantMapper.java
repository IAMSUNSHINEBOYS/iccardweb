package com.alen.mapper;


import com.alen.entity.MerchantZtree;
import com.alen.entity.SmartMerchant;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户组织信息表
 * @author LK
 * @version 1.0
 * @date 2020/6/9 18:58
 */
public interface SmartMerchantMapper {

    /**
     * 查询用户列表
     *smartMerchant
     *
     * @param smartMerchant
     *            商户组织
     * @param dateMap
     *            时间条件
     * @return
     */
    List<SmartMerchant> getSmartMerchantList(@Param("smartMerchant") SmartMerchant smartMerchant,
                                           @Param("dateMap") Map<String, Date> dateMap);

    /**
     * 查询所有的数据
     * @return
     */
    List<Map<String,Object>> getAllByMap();

    /**
     * 查询所有对象list
     * @return
     */
    List<MerchantZtree> getdAllByList();

    /**
     * 通过父id查询父消息
     * @param parentId
     * @return
     */
    SmartMerchant getSmartMerchantByParent(String parentId);

    /**
     * 查询所有父类信息
     * @param
     *          merchantId 组织id
     * @return
     */
    List<SmartMerchant> getSmartMerchantIdByParents(String merchantId);

    /**
     * 查询所有父类以及子
     * @param
     *          merchantId 组织id
     * @return
     */
    List<String> getSmartMerchantByChild(String merchantId);

    /**
     * 通过id查询
     * @param merchantId
     * @return
     */
    SmartMerchant getSmartMerchantByMerchantId(String merchantId);

    /**
     * 查询添加子集最大MerchantId
     * @return
     */
    String getMaxMerchantId(String merchantId);

    /**
     * 保存
     * @param smartMerchant
     * @return
     */
    int saveSmartMerchant(SmartMerchant smartMerchant);

    /**
     * 修改
     * @param smartMerchant
     * @return
     */
    int updateSmartMerchant(SmartMerchant smartMerchant);

    /**
     * 删除
     * @param merchantId
     * @return
     */
    int deleteSmartMerchant(String merchantId);

}
