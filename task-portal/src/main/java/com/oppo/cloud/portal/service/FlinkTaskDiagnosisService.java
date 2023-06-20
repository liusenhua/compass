package com.oppo.cloud.portal.service;

import com.oppo.cloud.common.api.CommonPage;
import com.oppo.cloud.common.api.CommonStatus;
import com.oppo.cloud.model.FlinkTaskDiagnosis;
import com.oppo.cloud.portal.domain.realtime.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface FlinkTaskDiagnosisService {
    String getResourceAdvice(FlinkTaskDiagnosis request);
    CommonPage<FlinkTaskDiagnosis> pageJobs(DiagnosisAdviceListReq req);
    DiagnosisGeneralViewNumberResp getGeneralViewNumber(@Validated @RequestBody DiagnosisGeneralViewReq request);
    DiagnosisGeneralViewTrendResp getGeneralViewTrend(@Validated @RequestBody DiagnosisGeneralViewReq request);
    DiagnosisGeneralVIewDistributeResp getGeneralViewDistribute(@Validated @RequestBody DiagnosisGeneralViewReq request);
    DiagnosisReportResp getReport(FlinkTaskDiagnosis request);
    CommonStatus<OneClickDiagnosisResponse> diagnosis(OneClickDiagnosisRequest req);
    CommonStatus<FlinkTaskDiagnosis> updateStatus(FlinkTaskDiagnosis flinkTaskDiagnosis);
}
