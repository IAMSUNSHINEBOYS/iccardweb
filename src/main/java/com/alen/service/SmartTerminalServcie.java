package com.alen.service;

import com.alen.entity.SmartTerminal;
import com.alen.mapper.SmartTerminalMapper;
import com.alen.utils.Pager;
import com.alen.utils.SqlDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 终端信息
 * @author LK
 * @version 1.0
 * @date 2020/6/19 17:37
 */
@Service
public class SmartTerminalServcie {

    @Autowired
    private SmartTerminalMapper smartTerminalMapper;

    /**
     * 通过条件查询终端列表
     *
     * @param pager
     *            分页对象
     * @param smartTerminal
     *            终端对象
     * @param dateMap
     *            时间条件
     * @param field
     *            排序属性
     * @param order
     *            0降序、1升序
     */
    public List<SmartTerminal> findSmartTerminalList(Pager pager, SmartTerminal smartTerminal, Map<String, String> dateMap, String field, int order) {
        pager.addSortable("fd_shs_datetime").addOrder(field, order).startPage();
        return smartTerminalMapper.getSmartTerminalList(smartTerminal, SqlDateUtils.getDateMap(dateMap));
    }
}
