package com.alen.mapper;

import com.alen.entity.Online;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 14:02
 */
public interface OnlineMapper {
    /**
     * 通过sessionID查找在线信息
     *
     * @param sessionId
     *            sessionId
     */
    Online getOnlineBySessionId(String sessionId);

    /**
     * 通过Id删除在线信息
     *
     * @param sessionId
     *            sessionId
     */
    int deleteOnlineById(Integer id);

    /**
     * 保存在线信息
     *
     * @param online
     *            在线信息
     */
    int saveOnline(Online online);

    /**
     * 删除在线信息
     *
     * @param ids
     *            id集合
     */
    int deleteByIds(@Param("ids") Integer[] ids);

    /**
     * 在线信息列表
     *
     * @param online
     *            在线信息对象
     * @param dateMap
     *            时间条件
     */
    List<Online> getOnlineList(@Param("online") Online online,
                               @Param("dateMap") Map<String, Date> dateMap);
}
