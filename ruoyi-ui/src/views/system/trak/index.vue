<template>
  <div>
    <el-form align="center" style="margin-top: 10px;" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作物名称" prop="cropName">
        <el-input
          v-model="queryParams.cropName"
          placeholder="农作物名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="query"
        />
      </el-form-item>
      <el-form-item label="记录时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="query">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <center style="margin-top: 10px;">
      <el-button
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="handAdd"
      >新增</el-button>

      <el-button
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="openUpdate"
      >修改</el-button>

      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="deletePlans"
      >删除</el-button>

      <el-button
        type="info"
        plain
        icon="el-icon-upload2"
        size="mini"
        @click="handleImport"
      >导入</el-button>

      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="excelDownload"
      >导出</el-button>
    </center>
    <right-toolbar :showSearch.sync="showSearch" @queryTable="query" :columns="columns"></right-toolbar>
    <el-table
      ref="multipleTable"
      :data="planInfoData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        align="center"
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        v-if="columns[0].visible"
        align="center"
        label="追踪编号"
        prop="planId">
      </el-table-column>
      <el-table-column
        v-if="columns[1].visible"
        align="center"
        prop="cropInfo.cropName"
        label="农产品名称">
      </el-table-column>
      <el-table-column
        v-if="columns[2].visible"
        align="center"
        prop="recordTime"
        label="记录时间"
        width="170px">
      </el-table-column>
      <el-table-column
        v-if="columns[3].visible"
        align="center"
        prop="temperature"
        label="温度">
      </el-table-column>
      <el-table-column
        v-if="columns[4].visible"
        align="center"
        prop="arowths"
        label="生长状况">
      </el-table-column>
      <el-table-column
        v-if="columns[5].visible"
        align="center"
        prop="humidness"
        label="湿度">
      </el-table-column>
      <el-table-column
        v-if="columns[6].visible"
        align="center"
        prop="illumination"
        label="光照">
      </el-table-column>
      <el-table-column
        v-if="columns[7].visible"
        align="center"
        label="农作物图片">
        <template v-slot="scope">
          <el-image v-if="scope.row.pic != null" style="width: 50px;height: 50px;" :src="scope.row.pic"></el-image>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns[8].visible"
        align="center"
        prop="remark"
        label="备注信息">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      align="center"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <!--添加修改对话框-->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="100px"
                 label-position="top">
          <el-col :span="12">
            <el-form-item label="农产品" prop="cropInfoId">
              <el-select v-model="form.cropInfoId" placeholder="请选择农产品" clearable
                         :style="{width: '100%'}">
                <el-option v-for="t in cropInfoIdOptions" :label="t.cropName" :value="t.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="温度" prop="temperature">
              <el-input v-model="form.temperature" placeholder="请输入温度" clearable :style="{width: '100%'}">
                <template slot="append">℃</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生长状况" prop="arowths">
              <el-input v-model="form.arowths" placeholder="请输入生长状况" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="湿度" prop="humidness">
              <el-input v-model="form.humidness" placeholder="请输入湿度" clearable :style="{width: '100%'}">
                <template slot="append">RH</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="光照" prop="illumination">
              <el-input v-model="form.illumination" placeholder="请输入光照" clearable :style="{width: '100%'}">
                <template slot="append">Lux</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上传" prop="pic" required>
              <el-upload
                class="avatar-uploader"
                :action="picAction"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="form.pic" :src="form.pic" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注信息" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息"
                        :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--导入数据框-->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="30%">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addPlanCropInfo,
  deletePlanCropInfo,
  getCropInfo,
  getPlanInfo,
  updatePlanCropInfo
} from "@/api/system/trak";
import {getUserProfile} from "@/api/system/user";
import {getToken} from "@/utils/auth";

export default {
  data() {
    return {
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/trakapi/trak/importPlanCropInfoData"
      },
      //日期范围
      dateRange:[],
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cropName: undefined,
      },
      //列表数据
      planInfoData: [],
      //总条数
      total:0,
      //查询条件可见
      showSearch:true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //列
      columns:[
        { key: 0, label: `追踪编号`, visible: true },
        { key: 1, label: `农产品名称`, visible: true },
        { key: 2, label: `记录时间`, visible: true },
        { key: 3, label: `温度`, visible: true },
        { key: 4, label: `生长状况`, visible: true },
        { key: 5, label: `湿度`, visible: true },
        { key: 6, label: `光照`, visible: true },
        { key: 7, label: `农作物图片`, visible: true },
        { key: 8, label: `备注信息`, visible: true },
      ],
      //表单参数
      form:{},
      // 是否显示对话框
      open:false,
      //对话框标题
      title:"",
      //校验规则
      rules:{
        cropInfoId: [{
          required: true,
          message: '请选择农产品',
          trigger: 'change'
        }],
        temperature: [{
          required: true,
          message: '请输入温度',
          trigger: 'blur'
        }],
        arowths: [{
          required: true,
          message: '请输入生长状况',
          trigger: 'blur'
        }],
        humidness: [{
          required: true,
          message: '请输入湿度',
          trigger: 'blur'
        }],
        illumination: [{
          required: true,
          message: '请输入光照',
          trigger: 'blur'
        }],
        remark: [{
          required: true,
          message: '请输入备注信息',
          trigger: 'blur'
        }],
      },
      //农作物信息
      cropInfoIdOptions:[],
      //上传地址
      picAction:"http://localhost:9205/trak/upload",
      //选中的id
      planIds:[],
      delPlanIds:"",
    }
  },
  methods: {
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.query();
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    //导出excel数据
    excelDownload(){
      let params = this.addDateRange(this.queryParams, this.dateRange);
      location.href="http://localhost:8080/trakapi/trak/excelDownload?pageNum="+params.pageNum+
        "&pageSize="+params.pageSize+"&beginTime="+params.params.beginTime+"&endTime="+params.params.endTime
    },
    //单删
    handleDelete(row){
      this.delPlanIds = row.id;
      this.deletePlan()
    },
    //批量删除
    deletePlans(){
      this.deletePlan(this.delPlanIds);
    },
    deletePlan(ids){
      deletePlanCropInfo(ids).then(r=>{
        if (r.code == 200){
          this.$modal.msgSuccess("删除成功");
          this.delPlanIds = "";
          this.query();
        }
      })
    },
    //打开添加对话框
    handAdd(){
      this.reflush();
      getUserProfile().then(r=>{
        this.form.createBy=r.data.userName
      })
      this.open = true;
      this.title = "添加";
    },
    //打开修改对话框
    handleUpdate(obj){
      this.reflush();
      this.form=obj;
      getUserProfile().then(r=>{
        this.form.updateBy=r.data.userName
      })
      this.getTitle();
    },
    openUpdate(){
      this.getTitle();
    },
    //获取标题
    getTitle(){
      this.open=true;
      this.title="修改"
    },
    //提交表单：添加/修改
    submitForm(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.planId != undefined) {
            updatePlanCropInfo(this.form).then(r => {
              if (r.code == 200){
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.query();
              }
            });
          } else {
            addPlanCropInfo(this.form).then(r => {
              if (r.code == 200){
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.query();
              }
            });
          }
        }
      });
    },
    //图片上传成功之后
    handleAvatarSuccess(res) {
      this.form.pic = res.data
    },
    //图片上传之前
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },
    //取消对话框
    cancel(){
      this.open=false;
      this.reflush();
    },
    //重置表单
    reflush(){
      this.form={
        planId:undefined,
        cropInfoId:undefined,
        recordTime:undefined,
        temperature:undefined,
        arowths:undefined,
        humidness:undefined,
        illumination:undefined,
        pic:"",
        remark:undefined,
        createBy:"",
        updateBy:""
      }
    },
    //每页显示条数改变时
    handleSizeChange(val) {
      this.queryParams.pageSize=val;
      this.getPlanInfo();
    },
    //当前页改变时
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getPlanInfo();
    },
    //复选框改变时
    handleSelectionChange(selected) {
      this.planIds = selected
      if (this.planIds.length==1){
        this.form = this.planIds[0];
      }else {
        this.reflush();
      }
      this.delPlanIds = selected.map(r=>{return r.planId}).join();
      this.single=selected.length!=1;
      this.multiple = ! selected.length
    },
    query(){
      this.queryParams.pageNum = 1;
      this.getPlanInfo()
    },
    /** 获取种植列表数据 */
    getPlanInfo(){
      getPlanInfo(this.addDateRange(this.queryParams, this.dateRange)).then(r=>{
        if (r.code == 200){
          this.planInfoData = r.data.list;
          this.total = r.data.total;
        }
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.queryParams = {pageNum: 1, pageSize: 10, cropName: undefined,}
      this.query();
    },
    getCropInfo(){
      getCropInfo().then(r=>{
        if (r.code==200){
          this.cropInfoIdOptions = r.data
          console.log(this.cropInfoIdOptions)
        }
      })
    },
  },
  created() {
    this.query();
    this.getCropInfo();
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
