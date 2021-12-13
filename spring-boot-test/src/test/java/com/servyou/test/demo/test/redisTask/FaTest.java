package com.servyou.test.demo.test.redisTask;

import cn.com.servyou.analysisengine.api.dto.FxCs;
import cn.com.servyou.analysisengine.api.dto.FxJg;
import cn.com.servyou.analysisengine.api.service.AnalysisEngineRunService;
import gov.chinatax.its.common.standard.api.HsfResultDTO;
import org.junit.Test;

import javax.annotation.Resource;

public class FaTest extends BaseTest {

    @Resource
    private AnalysisEngineRunService analysisEngineRunService;

    @Test
    public void analysisEngine() {
        FxCs cs = new FxCs();
        HsfResultDTO<FxJg> r = analysisEngineRunService.callEngine(cs);
        System.out.println(r);
    }
}
