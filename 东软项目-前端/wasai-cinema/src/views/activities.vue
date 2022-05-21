<template>
  <div class="activities_box">
    <el-timeline>
      <el-timeline-item v-for="(item, index) in activities" v-bind:key="index" placement="top">
        <p style="color:gray;margin-top:-10px">{{ item.startTime }}</p>
        <el-card>
          <h3><strong>活动简述:{{ item.content }}</strong></h3>
          <p style="color:gray">活动结束时间{{ item.endTime }}</p>
          <el-link :underline="false" style="color:rgb(0, 132, 255)" @click="isLoginAndTakeIn(item)">报名参加</el-link>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import { QueryActivityList,UploadActivityTakeIn } from "@/api/activity"
import { parseTime } from "@/plugins/DateTran";//日期格式转化
export default {
  data() {
    return {
      activities: [],
    }
  },
  methods: {
    //  查看是否登录
    isLoginAndTakeIn(item) {
      if (localStorage.getItem("uid") == null) {
        this.$confirm('未检测到登录', '提示', {
          confirmButtonText: '去登陆',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/login");
        });
      }
      else {
        var aidNo=item.id;
        const vo={
          create_time:new Date(),
          update_time:new Date(),
          aid:parseInt(aidNo),
          uid:parseInt(localStorage.getItem("uid"))
        };
        console.log(vo);
        UploadActivityTakeIn(vo).then((res)=>{
          if(res.code==200){
            if(res.data.success=="success"){
                    this.$message({message:res.data.msg,type:"success"},)
                   }
                    else{
                 this.$message({message:res.data.msg,type:"warning"},)
                     }
          }
          else{
             this.$message({message:res.data.msg,type:"warning"},)
          }
        })

      }

    },

    // 查看活动列表并输入数组

    QueryActivity() {
      QueryActivityList().then((res) => {
        if (res.code == 200) {
          this.activities = res.data;
          //转化其中显示的日期
          for (var i = 0; i < this.activities.length; i++) {
            this.activities[i].startTime = parseTime(new Date(this.activities[i].startTime), "{y}-{m}-{d}");
            this.activities[i].endTime = parseTime(new Date(this.activities[i].endTime), "{y}-{m}-{d}");
          };
        }
      })
    }



  },
  //改变导航栏样式
  beforeCreate: function () {
    console.log("BeforeCreate");
    this.$store.state.navnum = 2;
    this.$store.state.color3.color = "blue";
    this.$store.state.color1.color = "#5b5d63"; this.$store.state.color2.color = "#5b5d63"; this.$store.state.color4.color = "#5b5d63";
  },
  mounted: function () {
    //查询活动列表并生成到this.activities
    this.QueryActivity();
  }
}
</script>

<style>
.activities_box {
  width: 1300px;
  position: relative;
  margin: 30px auto;

}
</style>