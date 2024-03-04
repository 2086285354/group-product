<template>
  <div>
    <el-table
      :data="recoverData"
      height="250"
      border
      style="width: 100%">
      <el-table-column
        prop="no"
        label="申请编号">
      </el-table-column>
      <el-table-column
        prop="taskId"
        label="任务ID">
      </el-table-column>
      <el-table-column
        prop="taskName"
        label="任务名称">
      </el-table-column>
      <el-table-column
        prop="username"
        label="申请账号">
      </el-table-column>
      <el-table-column
        prop="assignee"
        label="审批人">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="审批时间">
      </el-table-column>
      <el-table-column
        prop="reason"
        label="申请理由">
      </el-table-column>
      <el-table-column
        label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="switched" @click="complete(scope.row)">通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="!switched"
            @click="complete1(scope.row)">通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="!switched"
            @click="openRefuse(scope.row)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="拒绝"
      :visible.sync="dialog"
      width="30%">
      <el-input
        type="textarea"
        :rows="2"
        placeholder="拒绝理由"
        v-model="object.result">
      </el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialog = false">取 消</el-button>
    <el-button type="primary" @click="complete2">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>

import {completeRecover, getRecoverData} from "@/api/system/act";
import {getInfo} from "@/api/login";

export default {
  data() {
    return {
      switched:true,
      recoverData: [],
      object:{},
      dialog:false
    }
  },
  methods: {
    getList(){
      getRecoverData().then(r=>{
        this.recoverData=r.data
      })
    },
    complete(obj){
      completeRecover(obj).then(r=>{
        if (r.code == 200){
          this.$message.success("操作成功");
          this.getList()
        }
      })
    },
    openRefuse(obj){
      this.object=obj
      this.dialog=true
    },
    complete2(){
      this.object.flag = 1;
      completeRecover(this.object).then(r=>{
        if (r.code == 200){
          this.$message.success("操作成功");
          this.dialog=false
          this.getList()
        }
      })
    },
    complete1(obj){
      obj.flag = 0;
      completeRecover(obj).then(r=>{
        if (r.code == 200){
          this.$message.success("账号恢复成功，请联系管理员重新授权");
          this.getList()
        }
      })
    },
    getLoginUser(){
      getInfo().then(r=>{
        console.log(r)
        if (r.roles.includes("admin")){
          this.switched=false;
        }
      })
    },
  },
  created() {
    this.getList()
    this.getLoginUser()
  }
}
</script>

<style scoped>

</style>
