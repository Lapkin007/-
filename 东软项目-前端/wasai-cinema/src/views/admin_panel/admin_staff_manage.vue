<template>
  <div class="outbox">
    <!-- 头部显示栏 -->
    <div class="header">
      <el-row>
        <el-col :span="5" class="header-item">客服ID</el-col>
        <el-col :span="4" class="header-item" style="padding-left:0px">账号</el-col>
        <el-col :span="5" class="header-item" style="padding-left:70px">电话</el-col>
        <el-col :span="10" class="header-item" style="padding-left:170px">操作</el-col>
      </el-row>
    </div>
    <el-collapse v-model="activeNames">
      <el-collapse-item :name="index" disabled :key="index" v-for="(item, index) in userlist"
        style="border-bottom:1px solid black;">
        <template slot="title">
          <el-row style="background-color:white;width: 1800px;height: 48px;">
            <el-col :span="5" class="order-box-item1">{{ item.id }}</el-col>
            <el-col :span="5" class="order-box-item1">{{ item.username }}</el-col>
            <el-col :span="5" class="order-box-item1">{{ item.phone }}</el-col>
            <el-col :span="8" class="order-box-item3">
              <el-button class="wait1" @click="UpdateUserData(index)">修改客服信息</el-button>
              <el-button class="fail1" @click="DeleteUser(index)"> 删除客服</el-button>
              <el-button class="PadButton" @click="changePad(index)"> 展开收起</el-button>
            </el-col>
          </el-row>
          <!-- 折叠的面板内容,这里用来显示详细信息 -->
        </template>
        <div class="collapse-panel-item">客服ID:{{ item.id }}</div>
        <div class="collapse-panel-item">客服账号:{{ item.username }}</div>
        <div class="collapse-panel-item">客服昵称:{{ item.nickname }}</div>
        <div class="collapse-panel-item">客服头像URL:{{ item.avatar }}</div>
        <img :src="item.avatar" style="margin-left:30px;width:100px;height:100px">
        <div class="collapse-panel-item">性别:{{ item.gender }}</div>
        <div class="collapse-panel-item">所属:{{ item.department }}</div>
        <div class="collapse-panel-item">创建时间:{{ item.createTime }}</div>
        <div class="collapse-panel-item">是否可用:{{ item.entry }}</div>
      </el-collapse-item>
    </el-collapse>
    <!-- 删除客服面板 -->
    <el-dialog title="确定删除吗?此操作不可撤销" :visible.sync="DeleteVisible">
       <el-button type="danger" @click="DeleteOneUser()">确定删除</el-button>
          <el-button @click="DeleteVisible=false">取消</el-button>
    </el-dialog>
    <!-- 修改客服信息通知面板 -->
    <el-dialog title="修改客服信息" :visible.sync="UpdateFormVisible">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item>
          <p style="display:block">昵称</p>
          <el-input v-model="form.nickname" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <p>密码</p>
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-form-item>
          <p style="display:block">电话</p>
          <el-input v-model="form.phone" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
          <p>性别</p>
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <p>部门</p>
          <el-radio-group v-model="form.dept">
            <el-radio  label="客服部">客服部</el-radio><el-radio  label="宣传部">宣传部</el-radio>
            <el-radio  label="运营部">运营部</el-radio><el-radio  label="策划部">策划部</el-radio>
            <el-radio  label="人事部">人事部</el-radio><el-radio  label="监督部">监督部</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
        <p>状态(1锁定0解锁)</p>
          <el-radio-group v-model="form.entry">
            <el-radio label="true">启用</el-radio>
            <el-radio label="false">禁用</el-radio>
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
import { workerAll,DeleteOneWorker,ModifyStaff } from "@/api/worker"
import { ModifySelf } from "@/api/user"
import { parseTime } from "@/plugins/DateTran";//日期格式转化
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
    form: {
      phone:null,
      dept:null,
      entry:null,
      nickname: null, //昵称
      sex: null,// 性别
      desc: null, //个人介绍
    },
    fileName:null,
      //折叠面板的展开序列
      activeNames: [],
      //正式的客服数组
      userlist: [],
      //点击修改客服后保存索引
      UpdateIndex:-1,
      //修改客服信息的面板可见开关 
      UpdateFormVisible:false,
      //删除客服的具体开关
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
				this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传头像图片大小不能超过 8MB!');
			}
			return isPNG&isJPG && isLt2M;
		},
		handleAvatarSuccess(res, file) {
			console.log(res.data.fileName);
			this.fileName=res.data.fileName;
			this.imageUrl = URL.createObjectURL(file.raw);
		
		},
    //更改客服基本信息
    onSubmit2() {
			let data={
        entry:this.form.entry,
				password:this.ruleForm.pass,
				avatar:this.fileName,
				nickname:this.form.nickname,
				phone:this.form.phone,
	      department:this.form.dept,
				gender:this.form.sex,
				id:this.userlist[this.UpdateIndex].id,
			};
			console.log(data);
			  if(this.ruleForm.pass!=null)
        { if(data.password.length<6)
        {this.$message.error("请输入6位以上的密码")}
        else{
          ModifyStaff(data).then((res)=>{
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
          ModifyStaff(data).then((res)=>{
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
    //客服信息头像更新规则
       beforeAvatarUpload(file) {
			const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
			const isLt2M = file.size / 2048 / 2048 < 2;

			if (!isJPG&!isPNG) {
				this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传头像图片大小不能超过 8MB!');
			}
			return isPNG&isJPG && isLt2M;
		},
    //头像处理
		handleAvatarSuccess(res, file) {
			console.log(res.data.fileName);
			this.fileName=res.data.fileName;
			this.imageUrl = URL.createObjectURL(file.raw);
		
		},
    
    //删除客服接口
    DeleteOneUser(){
       DeleteOneWorker(this.userlist[this.UpdateIndex].id).then((res)=>{
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
    //弹出更改客服信息按钮
    UpdateUserData(index) {
        this.UpdateFormVisible=true;
        this.UpdateIndex=index;
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
    //删除某一个客服显示弹窗
    DeleteUser(index){
      this.DeleteVisible=true;
      this.UpdateIndex=index;
    },
    //查询所有的客服信息并加入userlist
    QueryAlluser() {
      workerAll().then((res) => {
        if (res.code == 200) {
          this.userlist = res.data;
          for (var i = 0; i < this.userlist.length; i++) {
            this.userlist[i].createTime = parseTime(new Date(this.userlist[i].createTime));
          }
        }
      })

    },


  },
  created: function () {
    //查询所有客服并且放入客服表
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