<template>
<div class="comment_box">
  <el-container>
      <!-- 左侧客服栏位 -->
      <el-aside>
          <p class="p0">我的信息</p>
            <div class="state_member_card" >
              <img :src="state_member.avatar" class="state_member_img">
              <p class="p1" >名字:{{state_member.nickname}}</p>
              <p class="p2" >性别:{{state_member.gender}}</p>
              <p class="p3" >ID:{{state_member.id}}</p>
              <p class="p3" >部门:{{state_member.department}}</p>
             <el-link class="btnMycomment" style="text-align: center;display:block;width:100px;margin-top:50px;position: relative;left:-100px" :underline="false" @click="ShowMyComment()"> 我的评价</el-link>
             <el-link class="btnMycomment" style="text-align: center;display:block;width:100px;margin-top:50px;position: relative;left:-100px" :underline="false" @click="ShowMyUpdate()"> 修改个人信息</el-link>
          </div>
      </el-aside>
      
       <!-- 我的评价弹窗 -->
      <el-dialog title="我的评价" :visible.sync="My_comment_visible" width="40%"  >
      <div class="mycomment" style="border-top:none"><span class="my_comment_span3" >评论时间</span><span class="my_comment_span4">评论</span></div>
      <div class="mycomment" v-for="(item,index) in Mycomment_new" :key="index"><span class="my_comment_span1" >{{item.createTime}}</span><span class="my_comment_span2">{{item.content}}</span><span :class="changeSatisfyCss(item)">{{item.type}}</span></div>
      
      <br />
     <span slot="footer" class="dialog-footer">
     <el-button  @click="My_comment_visible = false">关闭</el-button>
  </span>
</el-dialog>
      <!-- 我的评价结束 -->
    <!-- 修改客服信息弹框 -->
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
       <!-- 左侧客服栏位结束 -->

        <!-- 右侧影院留言栏位 -->
      <el-main >
          <p style="margin-left:50px;font-size:20px;font-weight:600;color:white">影院留言</p>
          <div class="user_card" v-for="(item,index) in user_comment" :key="index">
              <img :src="item.user.avatar" class="user_img">
              <p class="LM1" >客人:&nbsp;{{item.user.nickname}}</p>
              <p class="LM2" >{{item.content}}</p>
              <p class="LM3" >Time:{{item.createTime}}</p> 
              <p class="LM4" >回复:{{item.reply}}</p>
              <el-button style="display:inline-block;position: relative;left: 599px;
               top: -90px;" type="primary" @click=" add_leavingMessage(index)">添加回复</el-button>
          </div>
          
      </el-main>
      <!-- 评论弹窗 -->
      <el-dialog title="添加回复" :visible.sync="comment_visible" width="50%"  >
      <el-form :model="form_comment">
       <el-form-item label="" >
      <el-input v-model="form_comment.content" autocomplete="off" type="textarea" maxlength="60" show-word-limit :autosize="{ minRows: 10, maxRows: 10}"
        placeholder="请输入内容"></el-input>
    </el-form-item>
    </el-form>
     <span slot="footer" class="dialog-footer">
     <el-button @click="comment_visible = false">取 消</el-button>
     <el-button type="primary" @click="UploadLM()">添加回复</el-button>
  </span>
</el-dialog>
      <!-- 评论弹窗结束 -->
       <!-- 右侧影院留言栏位结束 -->
  </el-container>
</div>
</template>

<script>
import { workerAll,DeleteOneWorker,ModifyStaff } from "@/api/worker"
import {QueryWorker,QueryCinemeComment,QueryWorkerMycomment,UploadWorkerMycomment,UploadLeaveMessage,UploadLeaveMessageReply} from "@/api/comment";
import { parseTime } from "@/plugins/DateTran";//日期格式转化
export default {
  data(){
      return{
          //修改客服信息的面板可见开关 
      UpdateFormVisible:false,
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
      nickname: null, //昵称
      sex: null,// 性别
      desc: null, //个人介绍
    },
    fileName:null,
          //影院留言表单
          form_comment:{content:""},
          //对员工评价表单
          form_comment_state_member:{content:"",satisfy:"一般"},
          //添加评论显示
          comment_visible:false,
            //员工评价显示
          comment_state_member_visible:false,
           //我对员工评价
          My_comment_visible:false,
          //满意度样式
           exe:{excellent:true,justso:false,bad:false},
           just:{excellent:false,justso:true,bad:false},
           awful:{excellent:false,justso:false,bad:true},
          //   存放员工顺序，让其显示不同按钮下的各个员工评论
          state_index:0,
          state_member:[
          ],
          //用户评论
          user_comment:[],
          //我的评论,接口输入
          Mycomment:[],
          //我的评论,筛选后
          Mycomment_new_list:{},
      }
  },
  methods:{
      ShowMyUpdate(){
          this.UpdateFormVisible=true;
      },
        //头像处理
		handleAvatarSuccess(res, file) {
			console.log(res.data.fileName);
			this.fileName=res.data.fileName;
			this.imageUrl = URL.createObjectURL(file.raw);
		
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
      //点击更新信息

       onSubmit2() {
			let data={
				password:this.ruleForm.pass,
				avatar:this.fileName,
				nickname:this.form.nickname,
				phone:this.form.phone,
				gender:this.form.sex,
				id:parseInt(localStorage.getItem("WID")),
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
      //上传影院留言接口
      UploadMyLM(vo){
        UploadLeaveMessageReply(vo).then((res)=>{
              if(res.code==200){
                   this.$message({message:"回复成功",type:"success"})
                   window.location.reload();
             }
             else{
                 this.$message.error("fail")
             }
                  })}
      ,
      //无用表单
      UploadWorkerEva(){},
      //评论回复表单
      UploadLM(){
          this.comment_visible = false;
          const vo={
              reply:this.form_comment.content,
              id:this.user_comment[this.state_index].id,
          }
          console.log(vo);
          this.UploadMyLM(vo);
      },
      //  查看是否登录
    isLogin() {
      if (localStorage.getItem("WID") == null) {
        this.$confirm('未检测到登录', '提示', {
          confirmButtonText: '去登陆',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/worker_login");
        });
      }
      else {
          return true;
      }

    },
    //显示我的评论界面
      ShowMyComment(){
          if(this.isLogin())
          {
          this.My_comment_visible=true;
          }
      },
      //显示添加回复
      add_leavingMessage(index){
          if(this.isLogin())
          {
              this.state_index=index;
           this.comment_visible=true;
      }},
      //更改样式设定
      changeSatisfyCss:function(item){
          if(item.type=="非常满意"){return this.exe}
          if(item.type=="一般"){return this.just}
          if(item.type=="不满意"){return this.awful}
      },
      //查询员工接口和我的评价并且放入state_member
      QueryWorkerApi(){
          QueryWorker().then((res)=>{
              if(res.code==200){
                  for(var i=0;i<res.data.length;i++)
                  {
                      if(parseInt(localStorage.getItem("WID"))==res.data[i].id){
                          this.state_member=res.data[i];
                      }
                  }
                  console.log(this.state_member);
                  QueryWorkerMycomment().then((res)=>{
                      if(res.code==200){
                          for(var i=0;i<res.data.length;i++)
                          {
                            //   用来验证与本地存储的数据是否一致
                              if(true)
                              {
                                  this.Mycomment.push(res.data[i]);
                              }
                          }
                          
                      }
                  })
              }
          })
      },
      //查询影院评论接口并且放入user_comment
      QueryCineme(){
          QueryCinemeComment().then((res)=>{
              if(res.code==200){
                  for(var i=0;i<res.data.length;i++)
                  {
                      if(res.data[i].user!=null){
                          this.user_comment.push(res.data[i]);
                      }
                  }
                  //转化其中显示的日期
                  for (var i = 0; i < this.user_comment.length; i++) {
                  this.user_comment[i].createTime = parseTime(new Date(this.user_comment[i].createTime));
                   };
                  }
              
          })
      }

  },
  computed:{
      //评论全部放入新数组的算法
      Mycomment_new:function(){
           this.Mycomment_new_list=undefined;
           this.Mycomment_new_list=new Array();
              for(var j=0;j<this.Mycomment.length;j++){
                  if(this.state_member.id==this.Mycomment[j].wid)
                  {
                      this.Mycomment_new_list.push(this.Mycomment[j]);
                  }
              }
              //转化其中显示的日期
                  for (var i = 0; i < this.Mycomment_new_list.length; i++) {
                  this.Mycomment_new_list[i].createTime = parseTime(new Date(this.Mycomment_new_list[i].createTime),"{y}-{m}-{d}");
                   };
              return this.Mycomment_new_list;
         
      }
  },
  beforeCreate:function(){
			console.log("BeforeCreate");
            this.$store.state.navnum=3;
            this.$store.state.color4.color="blue";
            this.$store.state.color1.color="#5b5d63";this.$store.state.color2.color="#5b5d63";this.$store.state.color3.color="#5b5d63";
		},
        created:function(){
            if(localStorage.getItem("WID")==null)
            {
                this.$message.error("没有登录");
               window.location.href="#/";
            }
        },
        mounted:function(){
            //查询评论接口
            this.QueryCineme();
            //查询员工列表和我的评论接口
            this.QueryWorkerApi();
        }
}
</script>

<style scoped lang="scss">
/* 全局盒子 */

.comment_box{
    position: relative;
    margin: 30px auto;
    width:1400px;
    height:700px;
    overflow:auto
}
/* 客服栏 CSS */
.p0{margin-left:50px;font-size:20px;font-weight:600;color:white}
.p1{font-size:12px;display:inline-block;position:relative;top:-20px;left:30px}
.p2{font-size:12px;display:block;position:relative;top:-30px;left:110px}
.p3{    font-size: 6px;
    display: inline-block;
    position: relative;
    top: -39px;
    left: 110px;
    height: 46px;
    overflow: hidden;
    width: 112px;}
    /* 客服头像CSS */
.state_member_img{
    width:80px;
    height:80px;
    border-radius: 80px;
    display: inline-block;
    position: relative;
    top:41px;
    
}
/* 我的评价按钮样式 */
.btnMycomment{margin-left:250px !important;font-size:16px;color:lightblue !important;position:relative;left:-140px !important}
/* 评价客服按钮样式 */
.btnCommentKefu{font-size:16px;color:lightblue !important;position:relative;left:-50px}
.state_member_card{
    width:320px;
    height:600px;
    /* background-color: blue; */
    margin-left: 50px;
    overflow: hidden;
    
}

.mycomment{
    width:100%;
    border-top:1px black solid;
    /* background-color: blueviolet; */
    height: 60px;
    line-height: 60px;
    float: left;
    overflow: hidden;
}
.excellent{
    margin-top:10px;
    height:30px;
    width:60px;
    float:right;
    text-align: center;
    display:inline-block;
    border: 1px solid green;
    color: green;
    line-height: 30px;
}
.justso{
    margin-top:10px;
    height:30px;
    width:60px;
    float:right;
    text-align: center;
    display:inline-block;
    border: 1px solid orange;
    color: orange;
    line-height: 30px;
}
.bad{
    margin-top:10px;
    height:30px;
    width:60px;
    float:right;
    text-align: center;
    display:inline-block;
    border: 1px solid red;
    color: red;
    line-height: 30px;
}
/* 右侧留言栏位 */
.LM1{font-size:13px;display:inline-block;position:relative;top:-10px;left:50px}
.LM2{font-size:18px;display:block;position:relative;top:-30px;left:110px}
.LM3{font-size:14px;display:inline-block;position:relative;top:-50px;left:110px;width:220px}
.LM4{font-size:10px !important;color:gray !important;position: relative;top:-52px}
.my_comment_span1{display:inline-block;width:139px}
.my_comment_span2{display:inline-block;margin-left:95px}
.my_comment_span3{display:inline-block;color:gray;font-size:20px}
.my_comment_span4{display:inline-block;margin-left:152px;color:gray;font-size:20px}
/* 用户栏 */
.user_card{
    width:700px;
    height:200px;
    /* background-color: blue; */
    margin-left: 50px;
    overflow: hidden;
    
}
.user_img{
    width:60px;
    height:60px;
    border-radius: 80px;
    display: inline-block;
    position: relative;
    top:50px;
    
}
/* CONTAINER CSS */
.el-aside{
    background-color:rgb(26, 26, 26);
    color: honeydew;
    min-width: 400px;
    max-width: 400px;
    min-height: 700px;
    overflow: hidden;
}
.el-main{
    background-color: rgb(38, 42, 46);
    color: honeydew;
    max-width: 1000px;
    min-height: 700px;
    padding: 0px;
}
.el-main p{margin-left:50px;font-weight:600;color:white}


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