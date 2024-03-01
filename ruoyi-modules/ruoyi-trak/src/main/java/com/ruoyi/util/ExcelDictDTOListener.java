package com.ruoyi.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.ruoyi.domain.PlanInfo;
import com.ruoyi.service.PlanInfoService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * todo 文件读取
 * @author 马震
 * @version 1.0
 * @date 2024/3/1 9:28
 */
@Component
@Data
@Slf4j
public class ExcelDictDTOListener   extends AnalysisEventListener<PlanInfo> {
    @Autowired
    PlanInfoService planInfoService;

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
            planInfoService.savaBach(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        planInfoService.savaBach(list);
        log.info("所有数据解析完成！");
    }







    /**
     * todo 注意导出前端必须发get请求  但是不能用axios请求
     *  window.location="http://localhost:8902/planInfo/downExcel";
     * @param fileName 文件名
     * @param sheetName sheet名字
     * @param list 所需要的数据
     * @param response response响应对象
     * @param <T>
     * @param mergeRowIndex 从第几行开始合并 0为第一行
     * @param mergeColumIndex 合并那些列 {下标从0开始 上同}
     * @param pageSize   每页多少条
     */
    public   <T> void downExcel(String fileName, String sheetName, List<T> list, HttpServletResponse response,int mergeRowIndex,int [] mergeColumIndex,int pageSize) {
        Set<String> excludeField = new HashSet<>();
        excludeField.add("searchValue");
        excludeField.add("createBy");
        excludeField.add("updateBy");
        excludeField.add("updateTime");
        excludeField.add("params");

        try {
            //        告诉浏览器返回的是 Excel
            //设置响应格式
            response.setContentType("application/vnd.ms-excel;chartset=utf-8"); //文件扩展名为excel格式
            //告诉浏览器编码规则
            response.setCharacterEncoding("utf-8");
            //文件名进行编码
            fileName = URLEncoder.encode(fileName+".xlsx", StandardCharsets.UTF_8.toString());
            //响应头加文件名信息
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName); //触发文件名为filename的“另存为”对话框
//            EasyExcel.write(response.getOutputStream(), list.get(0).getClass()).sheet("sheetName").doWrite(list);



            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PlanInfo.class)
                    .registerWriteHandler(new ExcelFillCellMergeStrategy(mergeRowIndex,mergeColumIndex))
                    .build();


            //多少页
            int page = list.size()/pageSize + (list.size()%pageSize!=0?1:0);

            // 向Excel的不同Sheet分页写入数据
            for (int i = 0; i < page; i++) {
                //下表开始位置
                int indexStart = i * pageSize;
                //下标结束位置
                int indexEnd = i * pageSize + pageSize > list.size() ? list.size() : i * pageSize + pageSize;
                List<PlanInfo> range =
                        (List<PlanInfo>) list.subList(indexStart, indexEnd);

                // 创建Sheet对象
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetName+ i).build();
                excelWriter.write(range, writeSheet);
            }
            // 关闭流
            excelWriter.finish();

            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
