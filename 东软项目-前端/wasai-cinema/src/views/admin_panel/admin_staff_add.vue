<template>
  <div class="outbox">
      <el-form v-model="StaffForm">
      <div><div class="inputLabel"><span style="color:red">*</span>用户名</div><el-input placeholder="请输入用户名" class="inputBox" v-model=" StaffForm.staff_name"></el-input></div>
      <div><div class="inputLabel"><span style="color:red">*</span>密码</div><el-input placeholder="请输入密码" class="inputBox" v-model=" StaffForm.staff_pwd"></el-input></div>
      <div><div class="inputLabel"><span style="color:red">*</span>昵称</div><el-input placeholder="请输入昵称" class="inputBox" v-model=" StaffForm.staff_nickname"></el-input></div>
      <!-- 性别选框 -->
      <div>
        <div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>性别</div>
       <el-radio v-model="StaffForm.staff_sex" label="男">男</el-radio>
       <el-radio v-model="StaffForm.staff_sex" label="女">女</el-radio>
      </div>
      <!-- 启用选框 -->
      <div>
        <div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>是否启用</div>
       <el-radio v-model="StaffForm.entry" label="false">禁用</el-radio>
       <el-radio v-model="StaffForm.entry" label="true">启用</el-radio>
      </div>
      <div><div class="inputLabel"><span style="color:red">*</span>电话号码</div><el-input placeholder="请输入电话" class="inputBox" v-model=" StaffForm.staff_phone"></el-input></div>
      <!-- 部门选框 -->
      <div><div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>入职部门</div>
       <el-radio v-model="StaffForm.staff_dept" label="客服部">客服部</el-radio><el-radio v-model="StaffForm.staff_dept" label="宣传部">宣传部</el-radio>
       <el-radio v-model="StaffForm.staff_dept" label="运营部">运营部</el-radio><el-radio v-model="StaffForm.staff_dept" label="策划部">策划部</el-radio>
       <el-radio v-model="StaffForm.staff_dept" label="人事部">人事部</el-radio><el-radio v-model="StaffForm.staff_dept" label="监督部">监督部</el-radio>
      </div>
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
import { workerAdd } from "@/api/worker";
export default {
  data(){
    return{
      StaffForm:{
          staff_name:null,
          staff_pwd:null,
          staff_nickname:null,
          staff_sex:'男',
          staff_phone:null,
          staff_dept:"客服部",
          entry:"false",
      }
        
    }
  },
  methods:{
    //确认添加员工
    onSubmit() {
        if(this.StaffForm.staff_name==null||this.StaffForm.staff_pwd==null||this.StaffForm.staff_nickname==null||this.StaffForm.staff_phone==null)
        {
          this.$message.error("请填写用户名,密码,昵称,和电话")
        }
        else{
          if(this.StaffForm.staff_name==""||this.StaffForm.staff_pwd==""||this.StaffForm.staff_nickname==""||this.StaffForm.staff_phone=="")
        {
          this.$message.error("检测到空白数据")
        }
        else{
          const vo={
            username:this.StaffForm.staff_name,
            password:this.StaffForm.staff_pwd,
            phone:this.StaffForm.staff_phone,
            department:this.StaffForm.staff_dept,
            entry:this.StaffForm.entry,
            gender:this.StaffForm.staff_sex,
            nickname:this.StaffForm.staff_nickname,
          }
          workerAdd(vo).then((res)=>{
            if(res.code==200)
        {
          if(res.data.success=="success")
          {
            this.$message({message:"添加成功",type:res.data.success})
          }
          else{
            this.$message.error("添加失败,请重试并查看字符")
          }
        }
        else{
          this.$message.error("请求失败")
        }
          })

        }

        }
      }
    }
}
</script>

<style scoped>
.outbox{
  overflow: auto;
  background-color: rgb(204, 207, 207);
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