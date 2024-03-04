package com.ruoyi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.domain.PlanInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyTest {

    @Test
    public void test2(){
        System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @Test
    public void test() throws Exception {
        String filePath = "C:/Users/86132/OneDrive/桌面/test.xls";
        EasyExcel.read(filePath, PlanInfo.class, new AnalysisEventListener<PlanInfo>(){
            @Override
            public void invoke(PlanInfo info, AnalysisContext analysisContext) {
                System.err.println(info.toString());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().doRead();
    }
}
