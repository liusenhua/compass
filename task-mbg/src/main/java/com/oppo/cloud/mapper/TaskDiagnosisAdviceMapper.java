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

package com.oppo.cloud.mapper;

import com.oppo.cloud.model.TaskDiagnosisAdvice;
import com.oppo.cloud.model.TaskDiagnosisAdviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskDiagnosisAdviceMapper {

    long countByExample(TaskDiagnosisAdviceExample example);

    int deleteByExample(TaskDiagnosisAdviceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TaskDiagnosisAdvice record);

    int insertSelective(TaskDiagnosisAdvice record);

    List<TaskDiagnosisAdvice> selectByExampleWithBLOBs(TaskDiagnosisAdviceExample example);

    List<TaskDiagnosisAdvice> selectByExample(TaskDiagnosisAdviceExample example);

    TaskDiagnosisAdvice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TaskDiagnosisAdvice record,
                                 @Param("example") TaskDiagnosisAdviceExample example);

    int updateByExampleWithBLOBs(@Param("record") TaskDiagnosisAdvice record,
                                 @Param("example") TaskDiagnosisAdviceExample example);

    int updateByExample(@Param("record") TaskDiagnosisAdvice record,
                        @Param("example") TaskDiagnosisAdviceExample example);

    int updateByPrimaryKeySelective(TaskDiagnosisAdvice record);

    int updateByPrimaryKeyWithBLOBs(TaskDiagnosisAdvice record);

    int updateByPrimaryKey(TaskDiagnosisAdvice record);
}
