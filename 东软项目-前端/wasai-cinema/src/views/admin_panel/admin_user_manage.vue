<template>
  <div class="outbox">
    <!-- 头部显示栏 -->
    <div class="header">
      <el-row>
        <el-col :span="5" class="header-item">用户ID</el-col>
        <el-col :span="4" class="header-item" style="padding-left:0px">账号</el-col>
        <el-col :span="5" class="header-item" style="padding-left:70px">邮箱</el-col>
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
            <el-col :span="5" class="order-box-item1">{{ item.email }}</el-col>
            <el-col :span="8" class="order-box-item3">
              <el-button class="wait1" @click="UpdateUserData(index)">修改用户信息</el-button>
              <el-button class="fail1" @click="DeleteUser(index)"> 删除用户</el-button>
              <el-button class="PadButton" @click="changePad(index)"> 展开收起</el-button>
            </el-col>
          </el-row>
          <!-- 折叠的面板内容,这里用来显示详细信息 -->
        </template>
        <div class="collapse-panel-item">用户ID:{{ item.id }}</div>
        <div class="collapse-panel-item">用户账号:{{ item.username }}</div>
        <div class="collapse-panel-item">用户昵称:{{ item.nickname }}</div>
        <div class="collapse-panel-item">用户头像URL:{{ item.avatar }}</div>
        <img :src="item.avatar" style="margin-left:30px;width:100px;height:100px">
        <div class="collapse-panel-item">生日:{{ item.birthday }}</div>
        <div class="collapse-panel-item">性别:{{ item.gender }}</div>
        <div class="collapse-panel-item">介绍:{{ item.info }}</div>
        <div class="collapse-panel-item">创建时间:{{ item.createTime }}</div>
        <div class="collapse-panel-item">状态(0可用1锁定):{{ item.status }}</div>
      </el-collapse-item>
    </el-collapse>
    <!-- 删除用户面板 -->
    <el-dialog title="确定删除吗?此操作不可撤销" :visible.sync="DeleteVisible">
       <el-button type="danger" @click="DeleteOneUser()">确定删除</el-button>
          <el-button @click="DeleteVisible=false">取消</el-button>
    </el-dialog>
    <!-- 修改用户信息通知面板 -->
    <el-dialog title="修改用户信息" :visible.sync="UpdateFormVisible">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item>
          <p style="display:block">昵称</p>
          <el-input v-model="form.name" style="display:inline-block;width:300px"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <p>密码</p>
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item>
          <p>性别</p>
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <p>出生年月</p>
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form :model="dynamicValidateForm" ref="dynamicValidateForm">
          <el-form-item prop="email" :rules="[
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            {
              type: 'email',
              message: '　　　　　　　　请输入正确的邮箱地址',
              trigger: ['blur', 'change'],
            },
          ]">
            <p style="display:block;position:relative;left:80px">邮箱</p>
            <el-input style="display:block;position:relative;left:80px;width:300px" v-model="dynamicValidateForm.email"></el-input>
          </el-form-item>
        </el-form>
        <el-form-item>
        <p>状态(1锁定0解锁)</p>
          <el-radio-group v-model="form.status">
            <el-radio label="1"></el-radio>
            <el-radio label="0"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <p>个人简介</p>
          <el-input type="textarea" v-model="form.desc"></el-input>
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
import { ModifySelf, SelectAllUser,DeleteUser } from "@/api/user"
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
      status:null,
      name: null, //昵称
      region: null,
      date1: null,//生日
      date2: null,
      delivery: false,
      type: [],
      sex: null,// 性别
      desc: null, //个人介绍
    },
    fileName: '',
      //折叠面板的展开序列
      activeNames: [],
      //正式的用户数组
      userlist: [],
      //点击修改用户后保存索引
      UpdateIndex:-1,
      //修改用户信息的面板可见开关 
      UpdateFormVisible:false,
      //删除用户的具体开关
      DeleteVisible:false,
    }
  },
  methods: {
    //个人信息
       beforeAvatarUpload(file) {
			const isJPG = file.type === 'image/jpeg';
			const isLt2M = file.size / 1024 / 1024 < 2;

			if (!isJPG) {
				this.$message.error('上传头像图片只能是 JPG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传头像图片大小不能超过 2MB!');
			}
			return isJPG && isLt2M;
		},
    //头像处理
		handleAvatarSuccess(res, file) {
			console.log(res.data.fileName);
			this.fileName=res.data.fileName;
			this.imageUrl = URL.createObjectURL(file.raw);
		
		},
    //更改基本信息
    onSubmit2() {
      if(this.form.status=="1"){var status=1}
      if(this.form.status=="0"){var status=0}
			let data={
        status:status,
				password:this.ruleForm.pass,
				avatar:null,
				nickname:this.form.name,
				email:this.dynamicValidateForm.email,
				birthday:this.form.date1,
				gender:this.form.sex,
				info:this.form.desc,
				id:this.userlist[this.UpdateIndex].id,
			};
			console.log(data);
			  if(this.ruleForm.pass!=null)
        { if(data.password.length<6)
        {this.$message.error("请输入6位以上的密码")}
        else{
          ModifySelf(data).then((res)=>{
				if(res.code==200)
        {
          if(res.data.success=="success")
          {
            this.$message({message:res.data.msg,type:res.data.success})
            this.UpdateFormVisible=false;
            window.location.href="#/admin_panel/order_manage";
            window.location.href="#/admin_panel/user_manage";
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
          ModifySelf(data).then((res)=>{
				if(res.code==200)
        {
          if(res.data.success=="success")
          {
            this.$message({message:res.data.msg,type:res.data.success})
            this.UpdateFormVisible=false;
            window.location.href="#/admin_panel/order_manage";
            window.location.href="#/admin_panel/user_manage";
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
    //删除用户接口
    DeleteOneUser(){
       DeleteUser(this.userlist[this.UpdateIndex].id).then((res)=>{
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
    //弹出更改用户信息按钮
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
    //删除某一个用户显示弹窗
    DeleteUser(index){
      this.DeleteVisible=true;
      this.UpdateIndex=index;
    },
    //查询所有的用户信息并加入userlist
    QueryAlluser() {
      SelectAllUser().then((res) => {
        if (res.code == 200) {
          this.userlist = res.data;
          for (var i = 0; i < this.userlist.length; i++) {
             this.userlist[i].birthday = parseTime(new Date(this.userlist[i].birthday),"{y}-{m}-{d}");
            this.userlist[i].createTime = parseTime(new Date(this.userlist[i].createTime));
          }
        }
      })

    },


  },
  created: function () {
    //查询所有用户并且放入订单表
    this.QueryAlluser();
  }
}

</script>

<style scoped>
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
</style>