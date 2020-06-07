package com.alen.service;


import com.alen.mapper.LoginRecordMapper;
import com.alen.entity.LoginRecord;
import com.alen.entity.Online;
import com.alen.utils.Pager;
import com.alen.utils.SqlDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 16:46
 */
@Service
public class LoginRecordService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;
    @Autowired
    private OnlineService onlineService;

    /**
     * 保存退出记录
     *
     * @param sessionId
     *            sessionId
     * @param exitTime
     *            退出时间
     */
    public void saveLoginRecord(String sessionId, Date exitTime) {
        Online online = onlineService.findOnlineBySessionId(sessionId);
        if (online != null) {
            LoginRecord loginRecord = new LoginRecord(online);
            loginRecord.setExitTime(exitTime);
            loginRecord.setRecordType("2");
            saveLoginRecord(loginRecord);
            onlineService.deleteOnlineById(online.getId());
        }
    }

    /**
     * 保存登录记录
     *
     * @param loginRecord
     *            登录记录
     */
    public void saveLoginRecord(LoginRecord loginRecord) {
        loginRecordMapper.saveLoginRecord(loginRecord);
    }

    /**
     * 删除登录记录
     *
     * @param ids
     *            id集合
     */
    public int deleteByIds(Integer[] ids) {
        return loginRecordMapper.deleteByIds(ids);
    }

    /**
     * 通过条件查询登录记录列表
     *
     * @param pager
     *            分页对象
     * @param loginRecord
     *            登录记录对象
     * @param dateMap
     *            时间条件
     * @param field
     *            排序属性
     * @param order
     *            0降序、1升序
     */
    public List<LoginRecord> findLoginRecordList(Pager pager,
                                                 LoginRecord loginRecord, Map<String, String> dateMap, String field,
                                                 int order) {
        pager.addSortable("id", "loginTime", "exitTime").addOrder(field, order)
                .addOrder("id", 0).startPage();
        return loginRecordMapper.getLoginRecordList(loginRecord,
                SqlDateUtils.getDateMap(dateMap));
    }
}
