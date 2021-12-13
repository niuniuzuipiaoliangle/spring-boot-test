package com.servyou.test.demo.test.kafkaProConfig;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class FpszjkFpkjZbdf {

    private List<String> mzzbList = new ArrayList<>();

    private String mzzbzh;

    /**
     * 预警触发原因
     * 1-指标计算，2-风险等级影响，3-白名单，4-人工调整
     */
    private String yjcfyy;

    private String nsrFxdj;

    private Date fxdjBgsj;

    private FpkjNsrxx nsrxx;

    /**
     * 人工调整前预警得分
     */
    private String rgtzqYjjb;

    public String getMzzbzh() {
        return mzzbList.toString().substring(1, mzzbList.toString().length() - 1);
    }

    /**
     * 计算批次UUID
     */
    private String jspcuuid;

    /**
     * 纳税人名称
     */
    private String nsrmc;

    /**
     * 提交日期
     */
    private Date tjrq;

    /**
     * 有销项无进项（主指标一）
     */
    private double zb1;

    /**
     * 风险MAC地址
     */
    private double zb2;

    /**
     * 人员交叉任职
     */
    private double zb3;

    /**
     * 法财办领同人
     */
    private double zb4;

    /**
     * 风险时段开票
     */
    private double zb5;

    /**
     * 凌晨开具发票
     */
    private double zb6;

    /**
     * 短期开完发票
     */
    private double zb7;

    /**
     * 顶额开具发票
     */
    private double zb8;

    /**
     * 开向省外畸高
     */
    private double zb9;

    /**
     * 作废发票畸高
     */
    private double zb10;

    /**
     * 风险企业开票
     */
    private double zb11;

    /**
     * 下游风险畸高
     */
    private double zb12;

    /**
     * 红黄蓝未申报
     */
    private double zb13;

    /**
     * 风险复合指标
     */
    private double zb14;

    /**
     * 缴税抵减风险
     */
    private double zb15;

    /**
     * 灰白名单标志
     */
    private String hbmdbz;

    /**
     * 发票领用风险等级
     */

    private Double rgdzqdf;

    private String yfdm;


    //判断命中的指标代码真包含传入的指标代码
    public boolean zContainsAll(String... args) {
        List<String> zbs = Arrays.asList(args);
        if (mzzbList.size() > zbs.size() && mzzbList.containsAll(zbs)) {
            return true;
        }
        return false;
    }

    //判断命中的指标代码包含传入的指标代码中的部分
    public boolean zContainsOne(String... args) {
        List<String> zbs = Arrays.asList(args);
        for (String zbdm : zbs) {
            if (mzzbList.contains(zbdm)) {
                return true;
            }
        }
        return false;
    }
}