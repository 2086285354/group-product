package com.ruoyi.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.EasyExcelConfig;
import com.ruoyi.common.MinioConfig;
import com.ruoyi.common.QueryParams;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.domain.PlanInfo;
import com.ruoyi.emum.MinioUploadEnum;
import com.ruoyi.service.PlanInfoService;
import com.ruoyi.mapper.PlanInfoMapper;
import com.ruoyi.util.ExcelDictDTOListener;
import com.ruoyi.util.ExcelFillCellMergeStrategy;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* @author 86132
* @description 针对表【plan_info(农作物种植记录跟踪信息表)】的数据库操作Service实现
* @createDate 2024-02-28 11:53:11
*/
@Service
@Slf4j
public class PlanInfoServiceImpl extends ServiceImpl<PlanInfoMapper, PlanInfo>
    implements PlanInfoService{
    @Autowired
    MinioConfig minioConfig;

    @Override
    public PageInfo<PlanInfo> selectPlanInfoList(QueryParams params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<PlanInfo> list =  baseMapper.selectPlanInfoList(params);
        return new PageInfo<>(list);
    }

    @Override
    public String uploadPicture(MultipartFile file) {
        String filename = file.getOriginalFilename();
        filename = UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));

        try {
            MinioClient.builder()
                    .endpoint(minioConfig.getUrl())
                    .build()
                    .putObject(
                            PutObjectArgs.builder()
                                    .bucket(minioConfig.getBucket())
                                    .stream(file.getInputStream(), file.getSize(), -1)
                                    .object(filename)
                                    .contentType(MinioUploadEnum.getContentType(filename))//设置上传类型 点击链接预览而不是下载
                                    .build()
                    );
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
        return minioConfig.getUrl()+minioConfig.getBucket()+"/"+filename;
    }

    @Override
    @Transactional
    public void addPlanInfo(PlanInfo info) {
        if (info.getCropInfoId() == null) {
            throw new ServiceException("请选择农作物");
        }
        if (info.getTemperature() == null || info.getTemperature() == ""){
            throw new ServiceException("请输入温度");
        }
        if (info.getArowths() == null || info.getArowths()==""){
            throw new ServiceException("请输入生长状况");
        }
        if (info.getHumidness() == null || info.getHumidness()==""){
            throw new ServiceException("请输入湿度");
        }
        if (info.getIllumination() == null || info.getIllumination()==""){
            throw new ServiceException("请输入光照");
        }
        if (info.getPic() == null || info.getPic()==""){
            throw new ServiceException("请选择作物照片");
        }
        if (info.getCreateBy() == null || info.getCreateBy()==""){
            throw new ServiceException("请登陆后重试");
        }
        baseMapper.addPlanInfo(info);
    }

    @Override
    public void updatePlanCropInfo(PlanInfo info) {
        if (info.getCropInfoId() == null) {
            throw new ServiceException("农作物不能为空");
        }
        if (info.getTemperature() == null || info.getTemperature() == ""){
            throw new ServiceException("温度不能为空");
        }
        if (info.getArowths() == null || info.getArowths()==""){
            throw new ServiceException("请输入生长状况");
        }
        if (info.getHumidness() == null || info.getHumidness()==""){
            throw new ServiceException("湿度不能为空");
        }
        if (info.getIllumination() == null || info.getIllumination()==""){
            throw new ServiceException("请输入光照");
        }
        if (info.getPic() == null || info.getPic()==""){
            throw new ServiceException("作物照片不能为空");
        }
        if (info.getUpdateBy() == null || info.getUpdateBy()==""){
            throw new ServiceException("请登陆后重试");
        }
        baseMapper.updatePlanCropInfo(info);
    }

    @Override
    public void deletePlanCropInfoByIds(String ids) {
        if (ids == null || ids == "") {
            throw new ServiceException("请选择要删除的记录");
        }
        baseMapper.deletePlanCropInfoByIds(ids);
    }

    @Override
    public void excelDownload(HttpServletResponse response,QueryParams params) throws IOException, URISyntaxException, InterruptedException {
        response.setContentType(EasyExcelConfig.RESPONSE_TYPE);//设置响应类型
        response.setCharacterEncoding(EasyExcelConfig.CHARSET);//设置编码
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename=plant_"+new Snowflake().nextIdStr() +".xls");

        List<PlanInfo> list = baseMapper.selectPlanInfoToExcel(params);

        for (PlanInfo planInfo : list) {//遍历循环
            if (planInfo.getDelFlag() == 0){//将状态字段替换
                planInfo.setDelStr("未删除");
            }else {
                planInfo.setDelStr("已删除");
            }
//            if (planInfo.getPic()!=null && planInfo.getPic()!=""){//如果图片不为空 则下载到本地
//                URI url = new URI(planInfo.getPic());//图片网络地址
//                URLConnection conn = url.toURL().openConnection();
//                InputStream inputStream = conn.getInputStream();
//                //替换图片名称
//                String fileName = UUID.randomUUID().toString()+ planInfo.getPic().substring(planInfo.getPic().lastIndexOf("."));
//                //图片在本地的存储位置
//                String path = EasyExcelConfig.SYSPATH + fileName;
//                FileOutputStream outputStream = new FileOutputStream(path);
//                //写入到本地
//                int bytesRead;
//                byte[] buffer = new byte[4096];
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//                outputStream.close();//关流
//                inputStream.close();//关流
//                planInfo.setPic(path);
//            }
        }

//        EasyExcel.write(response.getOutputStream(),PlanInfo.class).sheet("种植数据").doWrite(list);
        //从第几行开始合并
        int mergeRowIndex = 1;
        //合并那些列 {下标从0开始 上同}
        int[] mergeColumIndex={1};

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PlanInfo.class)
                //合并列
//                .registerWriteHandler(new ExcelFillCellMergeStrategy(mergeRowIndex,mergeColumIndex))
                .build();

        //每页多少条
        int pageSize = 5;
        //多少页
        int page = list.size()/pageSize + (list.size()%pageSize!=0?1:0);

        // 向Excel的不同Sheet分页写入数据
        for (int i = 0; i < page; i++) {
            //下表开始位置
            int indexStart = i * pageSize;
            //下标结束位置
            int indexEnd = i * pageSize + pageSize > list.size() ? list.size() : i * pageSize + pageSize;
            List<PlanInfo> range =
                    list.subList(indexStart, indexEnd);

            // 创建Sheet对象
            WriteSheet writeSheet = EasyExcel.writerSheet("种植信息" + i).build();
            excelWriter.write(range, writeSheet);
        }
        // 关闭流
        excelWriter.finish();

        Thread.sleep(2000);
    }

    @Override
    public boolean importPlanCropInfoData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), PlanInfo.class,new AnalysisEventListener<PlanInfo>(){
                        /**
                         * 每隔3条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
                         */
                        private static final int BATCH_COUNT = 3;
                        List<PlanInfo> list = new ArrayList<>();

                        @Override
                        public void invoke(PlanInfo data, AnalysisContext analysisContext) {
                            log.info("解析到一条记录: {}", data);
                            list.add(data);
                            // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
                            if (list.size() >= BATCH_COUNT) {
                                //todo 在这里执行存入数据库
                                savaBach(list);
                                list.clear();
                            }
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                            // 这里也要保存数据，确保最后遗留的数据也存储到数据库
                            savaBach(list);
                            log.info("所有数据解析完成！");
                        }
                    })
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void savaBach(List<PlanInfo> list) {
        baseMapper.saveBach(list);
    }
}




