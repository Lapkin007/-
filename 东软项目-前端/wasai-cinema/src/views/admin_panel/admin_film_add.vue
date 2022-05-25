<template>
  <div class="outbox">
      <el-form v-model="FilmForm">
      <div><div class="inputLabel"><span style="color:red">*</span>电影名</div><el-input placeholder="请输入电影名" class="inputBox" v-model=" FilmForm.name"></el-input></div>
      <div class="inputLabel" style="top:-5px;width:500px;position: relative;left:-60px"><span style="color:red">*</span>发布日期<el-date-picker
              type="date"
              class="inputBox"
              placeholder="选择日期"
              v-model="FilmForm.releaseTime"
              
            ></el-date-picker></div>
      <div><div class="inputLabel"><span style="color:red">*</span>电影时长</div><el-input placeholder="请输入电影时长" class="inputBox" v-model=" FilmForm.duration"></el-input></div>
      <!-- 类型选框 -->
      <div>
        <div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>类型</div>
       <el-radio v-model="FilmForm.type" label="家庭">家庭</el-radio>
       <el-radio v-model="FilmForm.type" label="惊悚">惊悚</el-radio>
       <el-radio v-model="FilmForm.type" label="科幻">科幻</el-radio>
       <el-radio v-model="FilmForm.type" label="爱情">爱情</el-radio>
       <el-radio v-model="FilmForm.type" label="动作">动作</el-radio>
       <el-radio v-model="FilmForm.type" label="喜剧">喜剧</el-radio>
       <el-radio v-model="FilmForm.type" label="恐怖">恐怖</el-radio>
       <el-radio v-model="FilmForm.type" label="悬疑">悬疑</el-radio>
       <el-radio v-model="FilmForm.type" label="犯罪">犯罪</el-radio>
       <el-radio v-model="FilmForm.type" label="冒险">冒险</el-radio>
       <el-radio v-model="FilmForm.type" label="战争">战争</el-radio>
       <el-radio v-model="FilmForm.type" label="历史">历史</el-radio>
       <el-radio v-model="FilmForm.type" label="武侠">武侠</el-radio>
       <el-radio v-model="FilmForm.type" label="古装" style="position:relative;left:90px">古装</el-radio>
       <el-radio v-model="FilmForm.type" label="其他" style="position:relative;left:90px">其他</el-radio>
      </div>
      <!-- 地区选框 -->
      <div>
        <div class="inputLabel" style="margin-right:10px"><span style="color:red">*</span>地区</div>
       <el-radio v-model="FilmForm.region" label="内地">内地</el-radio>
       <el-radio v-model="FilmForm.region" label="香港">香港</el-radio>
       <el-radio v-model="FilmForm.region" label="台湾">台湾</el-radio>
       <el-radio v-model="FilmForm.region" label="美国">美国</el-radio>
       <el-radio v-model="FilmForm.region" label="韩国">韩国</el-radio>
       <el-radio v-model="FilmForm.region" label="日本">日本</el-radio>
       <el-radio v-model="FilmForm.region" label="泰国">泰国</el-radio>
       <el-radio v-model="FilmForm.region" label="印度">印度</el-radio>
       <el-radio v-model="FilmForm.region" label="法国">法国</el-radio>
       <el-radio v-model="FilmForm.region" label="英国">英国</el-radio>
       <el-radio v-model="FilmForm.region" label="德国">德国</el-radio>
       <el-radio v-model="FilmForm.region" label="其他">其他</el-radio>
      </div>
      <!-- 电影简介 -->
      <el-form-item>
          <div><div class="inputLabel" style="font-size:16px"><span style="color:red">*</span>电影简介</div></div>
          <el-input type="textarea" v-model="FilmForm.info" style="width:700px;position: relative;left:90px"></el-input>
        </el-form-item>
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
AddOneFilm
import { AddOneFilm } from "@/api/film";
export default {
  data(){
    return{
      FilmForm:{
          name:null,
          releaseTime:null,
          type:"其他",
          region:"其他",
          info:null,
          duration:null,
      }
        
    }
  },
  methods:{
    //确认添加员工
    onSubmit() {
        if(this.FilmForm.info==null||this.FilmForm.name==null||this.FilmForm.releaseTime==null||this.FilmForm.type==null||this.FilmForm.region==null||this.FilmForm.duration==null)
        {
          this.$message.error("请填写用户名,密码,昵称,和电话")
        }
        else{
          if(this.FilmForm.info==""||this.FilmForm.name==""||this.FilmForm.releaseTime==""||this.FilmForm.type==""||this.FilmForm.region==""||this.FilmForm.duration=="")
        {
          this.$message.error("检测到空白数据")
        }
        else{
          const vo={
            name:this.FilmForm.name,
            releaseTime:this.FilmForm.releaseTime,
            type:this.FilmForm.type,
            region:this.FilmForm.region,
            entry:this.FilmForm.entry,
            introduction:this.FilmForm.info,
            duration:this.FilmForm.duration,
          }
          AddOneFilm(vo).then((res)=>{
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