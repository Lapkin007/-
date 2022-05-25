<template>
  <div class="outbox">
    <!-- 头部显示栏 -->
    <div class="header">
      <el-row>
        <el-col :span="3" class="header-item">电影ID</el-col>
        <el-col :span="7" class="header-item" style="padding-left:0px">电影名</el-col>
        <el-col :span="1" class="header-item" style="padding-left:0px">状态</el-col>
        <el-col :span="4" class="header-item" style="padding-left:70px">地区/类型</el-col>
        <el-col :span="9" class="header-item" style="padding-left:170px">操作</el-col>
      </el-row>
    </div>
    <el-collapse v-model="activeNames">
      <el-collapse-item :name="index" disabled :key="index" v-for="(item, index) in userlist"
        style="border-bottom:1px solid black;">
        <template slot="title">
          <el-row style="background-color:white;width: 1800px;height: 48px;">
            <el-col :span="3" class="order-box-item1">{{ item.id }}</el-col>
            <el-col :span="7" class="order-box-item1" style="overflow:hidden;width:370px;height:50px">{{ item.name }}</el-col>
            <el-col :span="2" class="order-box-item1">{{ item.status }}</el-col>
            <el-col :span="3" class="order-box-item1">{{ item.region }}/{{ item.type }}</el-col>
            <el-col :span="9" class="order-box-item3">
              <el-button class="wait1" @click="UpdateUserData(index)">修改电影信息</el-button>
              <el-button class="fail1" @click="DeleteUser(index)"> 删除电影</el-button>
              <el-button class="PadButton" @click="changePad(index)"> 展开收起</el-button>
            </el-col>
          </el-row>
          <!-- 折叠的面板内容,这里用来显示详细信息 -->
        </template>
        <div class="collapse-panel-item">电影ID:{{ item.id }}</div>
        <div class="collapse-panel-item">电影名字:{{ item.name }}</div>
        <div class="collapse-panel-item">电影热度:{{ item.hot }}</div>
        <div class="collapse-panel-item">电影发布日期:{{ item.releaseTime }}</div>
        <div class="collapse-panel-item">电影头像URL:{{ item.cover }}</div>
        <img :src="item.cover" style="margin-left:30px;width:100px;height:150px">
        <div class="collapse-panel-item">类型:{{ item.type }}</div>
        <div class="collapse-panel-item">地区:{{ item.region }}</div>
        <div class="collapse-panel-item">时长:{{ item.duration }}</div>
        <div class="collapse-panel-item">简介:{{ item.introduction }}</div>
        <div class="collapse-panel-item">创建时间:{{ item.createTime }}</div>
        <div class="collapse-panel-item">是否删除(1:被删除,0:没被删除):{{ item.isDelete}}</div>
        <div class="collapse-panel-item">是否上架(1:上架,0:下架):{{ item.status}}</div>
      </el-collapse-item>
    </el-collapse>
    <!-- 删除电影面板 -->
    <el-dialog title="确定删除吗?此操作不可撤销" :visible.sync="DeleteVisible">
       <el-button type="danger" @click="DeleteOneUser()">确定删除</el-button>
          <el-button @click="DeleteVisible=false">取消</el-button>
    </el-dialog>
    <!-- 修改电影信息通知面板 -->
    <el-dialog title="修改电影信息" :visible.sync="UpdateFormVisible">
      <el-form ref="form" :model="FilmForm" label-width="80px">
        <div><div class="inputLabel"><span style="color:red">*</span>电影名</div><el-input placeholder="请输入电影名" class="inputBox" v-model=" FilmForm.name"></el-input></div>
      <div class="inputLabel" style="width:500px;position: relative;"><span style="color:red">*</span>发布日期<br/><el-date-picker
              type="date"
              class="inputBox"
              placeholder="选择日期"
              v-model="FilmForm.releaseTime"
              
            ></el-date-picker></div>
      <div><div class="inputLabel"><span style="color:red">*</span>电影时长</div><el-input placeholder="请输入电影时长" class="inputBox" v-model=" FilmForm.duration"></el-input></div>
      <div><div class="inputLabel"><span style="color:red">*</span>热度值</div><el-input placeholder="请输入电影热度值" class="inputBox" v-model=" FilmForm.hot"></el-input></div>
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
       <el-radio v-model="FilmForm.type" label="古装">古装</el-radio>
       <el-radio v-model="FilmForm.type" label="其他">其他</el-radio>
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
          <div><div class="inputLabel" style="left:-90px;position: relative;"><span style="color:red">*</span>电影简介</div></div>
          <el-input type="textarea" v-model="FilmForm.info" style="width:500px;position: relative;"></el-input>
        </el-form-item>
        <el-form-item>
        <p>状态</p>
          <el-radio-group v-model="FilmForm.status">
            <el-radio label="1">上架</el-radio>
            <el-radio label="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>
         <el-form-item>
        <p>是否删除</p>
          <el-radio-group v-model="FilmForm.Isdelete">
            <el-radio label="1">删除</el-radio>
            <el-radio label="0">不删除</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <div class="picture">
          <div><el-upload
						class="avatar-uploader"
            action="http://47.115.220.25:8081/api/user/upload"
						:show-file-list="false"
						:on-success="handleAvatarSuccess"
						:before-upload="beforeAvatarUpload">
						<img v-if="imageUrl" :src="imageUrl" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload></div>
      <!-- <el-button type="primary" class="btn">点击上传</el-button> -->
      <p>只能上传jpg/png图片</p>
      <p>不能带有特殊符号</p>
       </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit2()">更新基本信息</el-button>
          <el-button @click="UpdateFormVisible=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>

</template>

<script>
import { parseTime } from "@/plugins/DateTran";//日期格式转化
import { QueryFilmList,DeleteOneFilm,ModifyFilm } from "@/api/film";
export default {
  data() {
    return {
      //个人设置信息单
    imageUrl: null,
    dynamicValidateForm: {
      domains: [
        {
          value: null,
        },
      ],
      email:null, //邮箱
    },
    ruleForm: { pass:null }, //密码
    FilmForm: {
          status:null,
          name:null,
          releaseTime:null,
          type:"其他",
          region:"其他",
          info:null,
          duration:null,
          Isdelete:null,
          hot:null,
    },
    fileName:null,
      //折叠面板的展开序列
      activeNames: [],
      //正式的电影数组
      userlist: [],
      //点击修改电影后保存索引
      UpdateIndex:-1,
      //修改电影信息的面板可见开关 
      UpdateFormVisible:false,
      //删除电影的具体开关
      DeleteVisible:false,
    }
  },
  methods: {
     //个人信息
       beforeAvatarUpload(file) {
			const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
			const isLt2M = file.size / 2048 / 2048 < 2;

			if (!isJPG&!isPNG) {
				this.$message.error('上传海报图片只能是 JPG 或者 PNG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传海报图片大小不能超过 8MB!');
			}
			return isPNG&isJPG && isLt2M;
		},
		handleAvatarSuccess(res, file) {
			console.log(res.data.fileName);
			this.fileName=res.data.fileName;
			this.imageUrl = URL.createObjectURL(file.raw);
		
		},
    //更改电影基本信息
    onSubmit2() {
      if(this.FilmForm.status=="1"){var sta=1}
      if(this.FilmForm.status=="0"){var sta=0}
			let data={
        hot:this.FilmForm.hot,
        isDelete:this.FilmForm.Isdelete,
        cover:this.fileName,
				name:this.FilmForm.name,
        status:sta,
        releaseTime:this.FilmForm.releaseTime,
        type:this.FilmForm.type,
        region:this.FilmForm.region,
        introduction:this.FilmForm.info,
        duration:this.FilmForm.duration,
        cover:this.fileName,
        id:this.userlist[this.UpdateIndex].id,
        updateTime:new Date(),
        createTime:null,
			};
			console.log(data);
			  if(this.FilmForm.name!=null)
        { if(data.name.length<2)
        {this.$message.error("请输入2位以上的电影名称")}
        else{
          ModifyFilm(data).then((res)=>{
				if(res.code==200)
        {
          if(res.data.success=="success")
          {
            this.$message({message:res.data.msg,type:res.data.success})
            this.UpdateFormVisible=false;
          }
          else{
            this.$message.error("更新失败")
          }
        }
        else{
          this.$message.error("请求失败")
        }
			});
        }
          }
        else{
          ModifyFilm(data).then((res)=>{
				if(res.code==200)
        {
          if(res.data.success=="success")
          {
            this.$message({message:res.data.msg,type:res.data.success})
            this.UpdateFormVisible=false;
          }
          else{
            this.$message.error("更新失败")
          }
        }
        else{
          this.$message.error("请求失败")
        }
			});
        }
    },
    //电影信息海报更新规则
       beforeAvatarUpload(file) {
			const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
			const isLt2M = file.size / 4096 / 4096 < 2;

			if (!isJPG&!isPNG) {
				this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传头像图片大小不能超过 16MB!');
			}
			return isPNG&isJPG && isLt2M;
		},
    //头像处理
		handleAvatarSuccess(res, file) {
			console.log(res.data.fileName);
			this.fileName=res.data.fileName;
			this.imageUrl = URL.createObjectURL(file.raw);
		
		},
    
    //删除电影接口
    DeleteOneUser(){
       DeleteOneFilm(this.userlist[this.UpdateIndex].id).then((res)=>{
         if(res.code==200)
        {
          if(res.data.success=="success")
          {
            this.$message({message:"删除成功",type:res.data.success})
            this.DeleteVisible=false;
            this.userlist.splice(this.UpdateIndex,1)
          }
          else{
            this.$message.error("删除失败")
          }
        }
        else{
          this.$message.error("请求失败")
        }
       })
    },
    //弹出更改电影信息按钮
    UpdateUserData(index) {
        this.UpdateFormVisible=true;
        this.UpdateIndex=index;
        this.FilmForm.type=this.userlist[this.UpdateIndex].type;
         this.FilmForm.region=this.userlist[this.UpdateIndex].region;
    },
    //样式改变，不要动
    changePad: function (n) {
      if (this.activeNames.indexOf(n) == -1) {
        this.activeNames.push(n);
      }
      else {
        this.activeNames.splice(this.activeNames.indexOf(n), 1);
      }
    },
    //删除某一个电影显示弹窗
    DeleteUser(index){
      this.DeleteVisible=true;
      this.UpdateIndex=index;
    },
    //查询所有的电影信息并加入userlist
    QueryAlluser() {
      QueryFilmList().then((res) => {
        if (res.code == 200) {
          this.userlist = res.data;
          for (var i = 0; i < this.userlist.length; i++) {
            this.userlist[i].createTime = parseTime(new Date(this.userlist[i].createTime));
            this.userlist[i].releaseTime = parseTime(new Date(this.userlist[i].releaseTime));
          }
        }
      })

    },


  },
  created: function () {
    //查询所有电影并且放入电影表
    this.QueryAlluser();
  }
}

</script>

<style scoped lang="scss">
.outbox {
  overflow: auto;
  background-color: rgb(187, 189, 189);
  height: 760px;
}

/* 隐藏滚动条 */
.outbox::-webkit-scrollbar {
  display: none;
}

/* 头部栏 */
.header-item {
  background-color: rgb(179, 179, 179);
  text-align: left;
  height: 50px;
  line-height: 50px;
  font-size: 20px;
  font-weight: 600 !important;
}

.order-box {
  border-bottom-color: rgb(233, 233, 233);
  height: 50px;
  text-align: center;
  font-size: 16px;
  line-height: 50px;
  padding-bottom: 5px;
  border-bottom: 1px solid black;
}

/* 按钮样式 */
.pay {
  margin-top: 10px;
  height: 30px;
  text-align: center;
  display: inline-block;
  border: 1px solid green;
  color: green;
  line-height: 30px;
  width: 80px;
  margin: 0, auto;
  border-radius: 5px;
  background-color: rgb(182, 238, 199);
}

.cancle {
  margin-top: 10px;
  height: 30px;
  text-align: center;
  display: inline-block;
  border: 1px solid rgb(189, 124, 2);
  color: rgb(196, 130, 7);
  line-height: 30px;
  width: 80px;
  margin: 0, auto;
  background-color: rgb(240, 206, 144);
  border-radius: 5px;
}

.wait {
  margin-top: 10px;
  height: 30px;
  text-align: center;
  display: inline-block;
  border: 1px solid blue;
  color: blue;
  line-height: 30px;
  width: 80px;
  margin: 0, auto;
  border-radius: 5px;
  background-color: rgb(172, 214, 248);
}

.fail {
  margin-top: 10px;
  height: 30px;
  text-align: center;
  display: inline-block;
  border: 1px solid rgb(182, 0, 0);
  color: rgb(218, 15, 0);
  line-height: 30px;
  width: 80px;
  margin: 0, auto;
  border-radius: 5px;
  background-color: rgb(241, 195, 195);
}

.wait1 {
  margin-top: 7px;
  text-align: center;
  display: inline-block;
  border: 1px solid blue;
  color: blue;
  border-radius: 5px;
  background-color: rgb(114, 187, 247);
  height: 35px;


}

.fail1 {
  height: 35px;
  text-align: center;
  display: inline-block;
  border: 1px solid rgb(182, 0, 0);
  color: rgb(218, 15, 0);
  border-radius: 5px;
  background-color: rgb(245, 122, 122);
}

/* 折叠面板 */
.order-box-item1 {
  color: black;
  font-size: 18px;
}

.PadButton {
  height: 35px;
  text-align: center;
  display: inline-block;
  border: 1px solid rgb(68, 68, 68);
  color: rgb(0, 0, 0);
  border-radius: 5px;
  background-color: rgb(238, 238, 238);
  margin-left: 60px !important;
}

/* 折叠板展开后的 */
.collapse-panel-item {
  text-indent: 2em;
  font-size: 14px
}

/* 定义订单状态和操作栏的 */
.order-box-item3 {
  width: 420px
}
//图像上传
.set {
  float: left;
  width: 830px;
  height: 600px;
  overflow: auto;
  //   margin: 0 auto;
//   border: 1px solid rgb(158, 156, 156);
textarea{
     resize:none;
}
  .basic_aside {
    width: 400px;
    float: left;
    margin: 20px;
    .text {
      font-size: 28px;
      margin-left: 80px;
      height: 80px;
      line-height: 80px;
    }
  }}
  .picture {
    float: left;
    margin: 70px;
    div {
      width: 120px;
      height: 120px;
      background: rgb(197, 197, 197);
      //   margin-left: 20px;
    }
    .btn {
      margin-top: 40px;
      width: 120px;
    }
    p {
      height: 50px;
      line-height: 50px;
      text-align: center;
      font-size: 14px;
      color: gray;
    }
  }

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
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .avatar {
    width: 120px;
    height: 120px;
    display: block;
  }
</style>