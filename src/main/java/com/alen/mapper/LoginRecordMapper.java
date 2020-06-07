package com.alen.mapper;

import com.alen.entity.LoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 14:01
 */
public interface LoginRecordMapper {
    /**
     * 保存登录记录
     *
     * @param loginRecord 登录记录
     */
    int saveLoginRecord(LoginRecord loginRecord);

    /**
     * 删除登录记录
     *
     * @param ids id集合
     */
    int deleteByIds(@Param("ids") Integer[] ids);

    /**
     * 登录记录列表
     *
     * @param loginRecord 登录记录对象
     * @param dateMap     时间条件
     */
    List<LoginRecord> getLoginRecordList(
            @Param("loginRecord") LoginRecord loginRecord,
            @Param("dateMap") Map<String, Date> dateMap);
}
