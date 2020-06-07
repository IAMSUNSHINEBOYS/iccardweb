package com.alen.service;


import com.alen.mapper.OnlineMapper;
import com.alen.entity.Online;
import com.alen.utils.Pager;
import com.alen.utils.SqlDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 16:45
 */
@Service
public class OnlineService {
    @Autowired
    private OnlineMapper onlineMapper;

    /**
     * 通过sessionID查找在线信息
     *
     * @param sessionId
     *            sessionId
     */
    public Online findOnlineBySessionId(String sessionId) {
        return onlineMapper.getOnlineBySessionId(sessionId);
    }

    /**
     * 通过Id删除在线信息
     *
     * @param id
     *            ids
     */
    public int deleteOnlineById(Integer id) {
        return onlineMapper.deleteOnlineById(id);
    }

    /**
     * 保存在线信息
     *
     * @param online
     *            在线信息
     */
    public int saveOnline(Online online) {
        return onlineMapper.saveOnline(online);
    }

    /**
     * 删除在线信息
     *
     * @param ids
     *            id集合
     */
    public int deleteByIds(Integer[] ids) {
        return onlineMapper.deleteByIds(ids);
    }

    /**
     * 通过条件查询在线信息列表
     *
     * @param pager
     *            分页对象
     * @param online
     *            在线信息对象
     * @param dateMap
     *            时间条件
     * @param field
     *            排序属性
     * @param order
     *            0降序、1升序
     */
    public List<Online> findOnlineList(Pager pager, Online online,
                                       Map<String, String> dateMap, String field, int order) {
        pager.addSortable("loginTime").addOrder(field, order).startPage();
        return onlineMapper.getOnlineList(online,
                SqlDateUtils.getDateMap(dateMap));
    }
}
