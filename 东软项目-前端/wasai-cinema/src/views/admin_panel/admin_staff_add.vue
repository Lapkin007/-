<template>
  <div class="outbox">
      <el-form v-model="StaffForm">
      <div><div class="inputLabel"><span style="color:red">*</span>用户名</div><el-input placeholder="请输入用户名" class="inputBox" v-model=" StaffForm.staff_name"></el-input></div>
      <div><div class="inputLabel"><span style="color:red">*</span>密码</div><el-input placeholder="请输入密码" class="inputBox" v-model=" StaffForm.staff_pwd"></el-input></div>
      <div><div class="inputLabel"><span style="color:red">*</span>昵称</div><el-input placeholder="请输入昵称" class="inputBox" v-model=" StaffForm.staff_nickname"></el-input></div>
      <!-- 性别选框 -->
      <div><div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>性别</div>
       <el-radio v-model="StaffForm.staff_sex" label="男">男</el-radio>
       <el-radio v-model="StaffForm.staff_sex" label="女">女</el-radio>
      </div>
      <div><div class="inputLabel"><span style="color:red">*</span>电话号码</div><el-input placeholder="请输入电话" class="inputBox" v-model=" StaffForm.staff_phone"></el-input></div>
      <!-- 部门选框 -->
      <div><div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>入职部门</div>
       <el-radio v-model="StaffForm.staff_dept" label="客服部">客服部</el-radio><el-radio v-model="StaffForm.staff_dept" label="宣传部">宣传部</el-radio>
       <el-radio v-model="StaffForm.staff_dept" label="运营部">运营部</el-radio><el-radio v-model="StaffForm.staff_dept" label="策划部">策划部</el-radio>
       <el-radio v-model="StaffForm.staff_dept" label="人事部">人事部</el-radio><el-radio v-model="StaffForm.staff_dept" label="监督部">监督部</el-radio>
      </div>
      <div><div class="inputLabel"><span style="color:red">*</span>头像</div>
      <el-upload
       class="upload-demo" drag action="https://jsonplaceholder.typicode.com/posts/" multiple style="margin-left:90px">
      <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件,且不超过500kb</div>
</el-upload></div>
      <!-- 按钮 -->
      <el-form-item>
    <el-button type="primary" @click="onSubmit" class="SubmitBTN">立即创建</el-button>
    <el-button>取消</el-button>
  </el-form-item>
      </el-form>
<!-- 底部 -->
  </div>
</template>

<script>
export default {
  data(){
    return{
      StaffForm:{
          staff_name:'',
          staff_pwd:'',
          staff_nickname:'',
          staff_sex:'男',
          staff_phone:'',
          staff_dept:'',
          staff_img:'',
      }
        
    }
  },
  methods:{
    onSubmit() {
        console.log('submit!');
      }
    }
}
</script>

<style scoped>
.outbox{
  overflow: auto;
  background-color: aqua;
  height:760px;
  padding-left:50px;
  padding-top:50px;
}
/* 表单部分 */
.inputBox{
    width:350px;margin-left:10px;
    margin-top:10px;
}
.inputLabel{
    display:inline-block;width:80px;text-align: right;
    margin-top:10px;
}
.SubmitBTN{
    margin-top:20px;
    margin-left:90px
}
</style>