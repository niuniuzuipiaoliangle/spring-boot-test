package com.servyou.test.demo.test.kafkaProConfig;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * DZDZ_FPXX_ZZSFP
 *
 * @author
 */
@Data
public class FpkjNsrxx implements Serializable {
    private String nsrwym;

    private String djxh;

    private String nsrmc;

    private String swjgdm;

    private Date kysj;
    /**
     * 专票：最高开票金额
     */
    private double zgkpjeZp;
    /**
     * 专票：最高开票份数
     */
    private long zdkpfsZp;
    /**
     * 机动车：最高开票金额
     */
    private double zgkpjeJdc;
    /**
     * 机动车：最高开票份数
     */
    private long zdkpfsJdc;

    /**
     * 增值税一般纳税人标志
     */
    private String zzsybnsrbz;
    /**
     * 课征主体登记类型
     */
    private String kzztdjlx;

    private String djzclxid;
    private String zfjglxid;
    private String frdbzjhm;
    private String cwfzrzjhm;
    private String bsryzjhm;
    private String gpryzjhm;
    private String frdbdhhm;
    private String cwfzrdhhm;
    private String bsrdhhm;
    private String gprdhhm;

    private String bz18;

    private String bz20;

    private String ybnsrbz;


    /**
     * 指标中用到
     * 票種核定數量
     *
     * @return
     */
    public long getPzhdsl() {
        return zdkpfsZp + zdkpfsJdc;
    }

    /**
     * 指标中用到
     * 票種核定金額
     *
     * @return
     */
    public double getPzhdje() {
        return zgkpjeZp > zgkpjeJdc ? zgkpjeZp : zgkpjeJdc;
    }

    /**
     * 指标中用到
     * 购票人员证件号码s
     *
     * @return
     */
    public List<String> gpryzjhms() {
        return (gpryzjhm == null || "".equals(gpryzjhm)) ? new ArrayList<String>() : Arrays.asList(gpryzjhm.split(","));
    }
}