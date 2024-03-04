package com.ruoyi.util;


import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.ruoyi.common.EasyExcelConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    /**
     * @param response
     * @param list 数据
     * @param filenamePrefix 文件名前缀
     * @throws IOException
     * @throws InterruptedException
     */
    public static void excelDownload(HttpServletResponse response,List list,String filenamePrefix) throws IOException, InterruptedException {
        response.setContentType(EasyExcelConfig.RESPONSE_TYPE);//设置响应类型
        response.setCharacterEncoding(EasyExcelConfig.CHARSET);//设置编码
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename="+filenamePrefix+"_"+new Snowflake().nextIdStr() +".xls");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), list.get(0).getClass())
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
            List range =
                    list.subList(indexStart, indexEnd);

            // 创建Sheet对象
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet" + i).build();
            excelWriter.write(range, writeSheet);
        }
        // 关闭流
        excelWriter.finish();

        Thread.sleep(2000);
    }
}
