/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dy.huibiao_f80.api;

import com.dy.huibiao_f80.api.back.AnalyseSubmit_Back;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.BeginTestForm_Back;
import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;
import com.dy.huibiao_f80.api.back.CheckExaminer_Back;
import com.dy.huibiao_f80.api.back.ExistExam_Back;
import com.dy.huibiao_f80.api.back.GetExamPage_Back;
import com.dy.huibiao_f80.api.back.GetTestForm_Back;
import com.dy.huibiao_f80.api.back.IsTeacherSubmit_Back;
import com.dy.huibiao_f80.api.back.OperationTestRecord_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.api.back.TheorySubmit_Back;
import com.dy.huibiao_f80.bean.UpdateMessage;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 *
 * @author wangzhenxiong
 */
public interface HuiBiaoService {

    /**
     * 法律法规
     * @param s
     * @return
     */
    @Headers({"Domain-Name: kjfw"})
    @GET("/pdf/法律法规/{id}")
    Observable<ResponseBody> getLaws(
            @Path("id") String s);

    /**
     * 食品标准
     * @param s
     * @return
     */
    @Headers({"Domain-Name: kjfw"})
    @GET("/pdf/食品标准/{id}")
    Observable<ResponseBody> getStandard(
            @Path("id") String s);

    @Headers({"Domain-Name: upgradeVersion"}) // 加上 Domain-Name header
    @POST("/dyfda2/os/java/pub/app/upgradeVersion.shtml")
    Observable<UpdateMessage> checkNewVersion(@Query("appname") String appName);
    /**
     * 点击考试
     *
     * @param identificationNumber
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/existExam")
    Observable<ExistExam_Back> existExam(@Query("identificationNumber") String identificationNumber);

    /**
     * 考生认证
     *
     * @param examinationId  考试主键id
     * @param name   真实姓名
     * @param idNumber  准考证号
     * @param personTestMethod  考生验证方式，默认1
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examiner/checkExaminer")
    Observable<CheckExaminer_Back> checkExaminer(
            @Query("examinationId") String examinationId ,
            @Query("name") String name ,
            @Query("idNumber") String idNumber  ,
            @Query("personTestMethod") int personTestMethod
    );

    /**
     * 考试须知-点击考试/考试完成后展示考试页
     *
     * @param examinationId 考试信息主键
     * @param examinerId    考生信息主键
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examiner/getExamPage")
    Observable<GetExamPage_Back> getExamPage(@Query("examinationId") String examinationId, @Query("examinerId") String examinerId);

    /**
     * 理论考试-进入考试
     *
     * @param examinationId 考试信息主键
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/beginTheoryExam")
    Observable<BeginTheoryExam_Back> beginTheoryExam(@Query("examinationId") String examinationId);

    /**
     * 分析题考试-进入考试
     * @param examinationId  考试信息主键
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/beginAnalyseExam")
    Observable<BeginAnalyseExam_Back> beginAnalyseExam(@Query("examinationId") String examinationId);

    /**
     * 实操考试-进入考试
     * @param examinationId  考试信息主键
     * @param examinerId  考生信息主键
     * @param identificationNumber  设备号
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("examPath/quickCheck/examination/getOperationExam")
    Observable<BeginOperationExam_Back> getOperationExam(@Query("examinationId") String examinationId, @Query("examinerId") String examinerId, @Query("identificationNumber") String identificationNumber);

    /**
     * 获取实践报告数据
     * @param examinationId  考试信息主键
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/beginTestForm")
    Observable<BeginTestForm_Back> beginTestForm(@Query("examinationId") String examinationId);


    /**
     * 获取实践报告数据
     * @param operationPaperId  实操题主键
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @GET("/examPath/quickCheck/examination/getTestForm")
    Observable<GetTestForm_Back> getTestForm(@Query("operationPaperId") String operationPaperId);

    /**
     * 理论试卷-交卷
     * @param requestBody 考生理论考试答案记录实体
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/theorySubmit")
    Observable<TheorySubmit_Back> theorySubmit(@Body RequestBody requestBody);

    /**
     * 分析题试卷-交卷
     * @param requestBody  考生分析题考试答案记录实体
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/analyseSubmit")
    Observable<AnalyseSubmit_Back> analyseSubmit(@Body RequestBody requestBody);

    /**
     * 实践报告-提交报告
     * @param requestBody 实践报告记录实体
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/testFormSubmit")
    Observable<TestFormSubmit_Back> testFormSubmit(@Body RequestBody requestBody);

    /**
     * 判断考评员是否提交分数
     * @param examinerId
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examination/isTeacherSubmit")
    Observable<IsTeacherSubmit_Back> isTeacherSubmit(@Query("examinerId") String examinerId);


    /**
     * 实操考试-提交实验结果
     * @param requestBody
     * @return
     */
    @Headers({"Domain-Name: xxx"})
    @POST("/examPath/quickCheck/examiner/operationTestRecord")
    Observable<OperationTestRecord_Back> operationTestRecord(@Body RequestBody requestBody);

}
