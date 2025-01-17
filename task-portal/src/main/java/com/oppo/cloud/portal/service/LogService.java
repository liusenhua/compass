/*
 * Copyright 2023 OPPO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oppo.cloud.portal.service;

import com.oppo.cloud.common.domain.opensearch.TaskApp;
import com.oppo.cloud.portal.domain.log.LogInfo;
import com.oppo.cloud.portal.domain.task.JobDetailRequest;

import java.util.List;

/**
 * 日志内容查询Service
 */
public interface LogService {

    /**
     * 获取Yarn diagnostic的诊断结果
     */
    List<LogInfo> getDiagnosticDetect(List<TaskApp> taskApps);

    /**
     * 获取某种日志类型的诊断结果
     */
    List<LogInfo> getLogDetect(JobDetailRequest jobDetailRequest, String logType);
}
