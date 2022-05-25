<template>
  <div class="outbox">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item>
          <p style="display:block">场次显示的电影名称</p>
          <el-input v-model="form.nickname" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
        <el-form-item>
          <p style="display:block">电影ID(不知道请去查询电影列表)</p>
          <el-input v-model="form.fid" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <p>总座位</p>
          <el-input type="text" v-model="form.seatNum" autocomplete="off" style="width:300px"></el-input>
        </el-form-item>
          <el-form-item>
          <p style="display:block">类型</p>
          <el-input v-model="form.type" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
          <el-form-item>
          <p>电影日期</p>
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 100%"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item>
          <p style="display:block">价格</p>
          <el-input v-model="form.price" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
        <el-form-item>
          <p style="display:block">开始时间</p>
          <el-input v-model="form.startTime" style="display:inline-block;width:300px" placeholder="XX:XX:XX"></el-input>
        </el-form-item>
        <el-form-item>
          <p style="display:block">结束时间</p>
          <el-input v-model="form.endTime" style="display:inline-block;width:300px" placeholder="XX:XX:XX"></el-input>
        </el-form-item>
        <el-form-item>
          <p style="display:block">创建人</p>
          <el-input v-model="form.founder" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()">添加排片</el-button>
          <el-button @click="UpdateFormVisible=false">取消</el-button>
        </el-form-item>
      </el-form>
<!-- 底部 -->
  </div>
</template>

<script>

import { AddArr } from "@/api/film";
export default {
  data(){
    return{
      form: {
      type:null,
      nickname:"请输入电影名称", //显示的电影昵称
      price:null,
      date:null,
      startTime:null,
      endTime:null,
      seatNum:null,
      fid:1,
      founder:null,
    },  
    }
  },
  methods:{
    //确认添加员工
     onSubmit() {
      if(this.form.endTime==null||this.form.startTime==null||this.form.founder==null||this.form.nickname==null||this.form.price==null||this.form.fid==null||this.form.seatNum==null||this.form.type==null)
        {
          this.$message.error("请填写各项信息")
        }
        else{
          if(this.form.endTime==""||this.form.startTime==""||this.form.founder==""||this.form.nickname==""||this.form.price==""||this.form.fid==""||this.form.seatNum==""||this.form.type=="")
        {
          this.$message.error("检测到空白数据")
        }
        else{
          const vo={
            id:null,
         boxOffice:0,
         seatNumber:this.form.seatNum,
			 	 name:this.form.nickname,
         price:this.form.price,
	       type:this.form.type,
         date:this.form.date,
         startTime:this.form.startTime,
         endTime:this.form.endTime,
         founder:this.form.founder,
         fid:this.form.fid,
         createTime:new Date(),
         updateTime:new Date(),
          }
          AddArr(vo).then((res)=>{
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
  height:720px;
  padding-left:50px;
  
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