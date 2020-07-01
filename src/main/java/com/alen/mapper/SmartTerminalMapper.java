package com.alen.mapper;

import com.alen.entity.SmartTerminal;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户信息
 * @author LK
 * @version 1.0
 * @date 2020/6/19 16:47
 */
public interface SmartTerminalMapper {

    /**
     * 终端信息列表
     *
     *
     * @param smartMachine
     *            终端信息对象
     * @param dateMap
     *            时间条件
     * @return
     */
    List<SmartTerminal> getSmartTerminalList(@Param("smartTerminal") SmartTerminal smartTerminal,
                                            @Param("dateMap") Map<String, Date> dateMap);
}
