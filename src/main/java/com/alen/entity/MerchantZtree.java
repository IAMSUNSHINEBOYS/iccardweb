package com.alen.entity;

import lombok.Data;

/**
 * 商户组织
 * @author LK
 * @version 1.0
 * @date 2020/6/17 19:20
 */
@Data
public class MerchantZtree {

    private String id; //组织代码
    private String name;	//组织名称
    private String parentId;//父组织代码
    private Integer mlevel; //级别
}
