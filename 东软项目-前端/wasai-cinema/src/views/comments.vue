<template>
<div class="comment_box">
  <el-container>
      <!-- 左侧客服栏位 -->
      <el-aside>
          <p class="p0">客服人员</p>

          <div class="state_member_card" v-for="(item,index) in state_member" :key="index">
              <img :src="item.avatar" class="state_member_img">
              <p class="p1" >名字:{{item.nickname}}</p>
              <p class="p2" >性别:{{item.gender}}</p>
              <p class="p3" >ID:{{item.id}}</p>
             <el-link class="btnMycomment" :underline="false" @click="ShowMyComment(index)"> 我的评价</el-link>
              <el-link class="btnCommentKefu" :underline="false" @click="ShowStateComment(index)">评价客服</el-link>
          </div>
      </el-aside>
      <!-- 客服评论弹窗 -->
      <el-dialog title="评论客服" :visible.sync="comment_state_member_visible" width="50%"  >
      <el-form :model="form_comment_state_member">
          <el-form-item label="满意度">
          <el-select v-model="form_comment_state_member.satisfy" placeholder="请选择满意度">
      <el-option label="非常满意" value="非常满意"></el-option>
      <el-option label="一般" value="一般"></el-option>
      <el-option label="不满意" value="不满意"></el-option>
      
     
     </el-select>
          </el-form-item>
       <el-form-item label="评价内容" >
      <el-input v-model="form_comment_state_member.content" autocomplete="off" type="textarea" maxlength="15" show-word-limit :autosize="{ minRows: 5, maxRows: 5}"
  placeholder="请输入内容"></el-input>
    </el-form-item>
    </el-form>
     <span slot="footer" class="dialog-footer">
     <el-button @click="comment_state_member_visible = false">取 消</el-button>
     <el-button type="primary" @click="UploadWorkerEva()">确定评论</el-button>
  </span>
</el-dialog>
      <!-- 客服评论弹窗结束 -->

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
          </div>
          <div style="width:900px"><el-button type="primary" style="float:right" @click="add_leavingMessage()">添加留言</el-button></div>
      </el-main>
      <!-- 评论弹窗 -->
      <el-dialog title="添加一段留言" :visible.sync="comment_visible" width="50%"  >
      <el-form :model="form_comment">
       <el-form-item label="" >
      <el-input v-model="form_comment.content" autocomplete="off" type="textarea" maxlength="60" show-word-limit :autosize="{ minRows: 10, maxRows: 10}"
        placeholder="请输入内容"></el-input>
    </el-form-item>
    </el-form>
     <span slot="footer" class="dialog-footer">
     <el-button @click="comment_visible = false">取 消</el-button>
     <el-button type="primary" @click="UploadLM()">添加留言</el-button>
  </span>
</el-dialog>
      <!-- 评论弹窗结束 -->
       <!-- 右侧影院留言栏位结束 -->
  </el-container>
</div>
</template>

<script>
import {QueryWorker,QueryCinemeComment,QueryWorkerMycomment,UploadWorkerMycomment,UploadLeaveMessage} from "@/api/comment";
import { parseTime } from "@/plugins/DateTran";//日期格式转化
export default {
  data(){
      return{
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
            //   {
            //       pic:require("../assets/img/nim.png"),
            //       name:"客服1",
            //       sex:"男",
            //       id:"114514abxy",
            //       mycomment:[
            //           {
            //       time:"2021/2/1",
            //       content:"XXXX",
            //       satisfy:"满意",
            //       },
            //       {
            //       time:"2020/9/15",
            //       content:"XXxXX",
            //       satisfy:"不满意"
            //       },
            //       ]
            //   },
            //   {
            //       pic:require("../assets/img/nim.png"),
            //       name:"客服2",
            //       sex:"女",
            //       id:"245134abxy",
            //       mycomment:[
            //           {
            //       time:"2021/9/7",
            //       content:"XXXXXXX",
            //       satisfy:"一般",
            //       },
            //       {
            //       time:"2020/9/15",
            //       content:"XXxXX",
            //       satisfy:"满意"
            //       },
            //       ]
            //   },
            //   {
            //       pic:require("../assets/img/nim.png"),
            //       name:"客服3",
            //       sex:"男",
            //       id:"384311abxy",
            //       mycomment:[
            //           {
            //       time:"2021/11/17",
            //       content:"XXXX",
            //       satisfy:"一般",
            //       },
            //       {
            //       time:"2020/5/12",
            //       content:"XXxxXX",
            //       satisfy:"一般"
            //       },
            //       ]
            //   },
            //   {
            //       pic:require("../assets/img/nim.png"),
            //       name:"客服3",
            //       sex:"男",
            //       id:"384311abxy",
            //       mycomment:[
            //            {
            //       time:"2021/2/1",
            //       content:"XXXXXXX",
            //       satisfy:"满意",
            //       },
            //       {
            //       time:"2020/5/19",
            //       content:"XXxXX",
            //       satisfy:"满意"
            //       },
            //       ]
            //   }
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
      //上传我的评论接口
      UploadMycomment(vo){
        UploadWorkerMycomment(vo).then((res)=>{
              if(res.code==200){
                   if(res.data.success=="success"){
                    this.$message({message:res.data.msg,type:"success"},)
                   }
                    else{
                 this.$message({message:res.data.msg,type:"warning"},)
                     }
             }
             else{
                 this.$message.error("fail")
             }
                  })}
      ,
      //上传影院留言接口
      UploadMyLM(vo){
        UploadLeaveMessage(vo).then((res)=>{
              if(res.code==200){
                   if(res.data.success=="success"){
                    this.$message({message:res.data.msg,type:"success"},)
                   }
                    else{
                 this.$message({message:res.data.msg,type:"warning"},)
                     }
             }
             else{
                 this.$message.error("fail")
             }
                  })}
      ,
      //评论提交表单
      UploadWorkerEva(){
          this.comment_state_member_visible = false;
          const vo={
              createTime:new Date(),
              updateTime:new Date(),
              content:this.form_comment_state_member.content,
              type:this.form_comment_state_member.satisfy,
              uid:parseInt(localStorage.getItem("uid")),
              wid:this.state_member[this.state_index].id,
          }
          console.log(vo);
          this.UploadMycomment(vo);
      },
      //留言提交表单
      UploadLM(){
          this.comment_visible = false;
          const vo={
              createTime:new Date(),
              updateTime:new Date(),
              content:this.form_comment.content,
              uid:parseInt(localStorage.getItem("uid")),
             reply:"等待回复"
          }
          console.log(vo);
          this.UploadMyLM(vo);
      },
      //  查看是否登录
    isLogin() {
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
          return true;
      }

    },
    //显示我的评论界面
      ShowMyComment(n){
          if(this.isLogin())
          {
          this.state_index=n;
          this.My_comment_visible=true;
          }
      },
       //显示员工评论界面
      ShowStateComment(n){
          if(this.isLogin())
          {
          this.state_index=n;
           this.comment_state_member_visible=true;}
      },
      //显示影院留言
      add_leavingMessage(){
          if(this.isLogin())
          {
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
                  this.state_member=res.data;
                  QueryWorkerMycomment().then((res)=>{
                      if(res.code==200){
                          for(var i=0;i<res.data.length;i++)
                          {
                            //   用来验证与本地存储的数据是否一致
                              if(localStorage.getItem("uid")==res.data[i].uid)
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
                  if(this.state_member[this.state_index].id==this.Mycomment[j].wid)
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
        mounted:function(){
            //查询评论接口
            this.QueryCineme();
            //查询员工列表和我的评论接口
            this.QueryWorkerApi();
        }
}
</script>

<style scoped>
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
.btnMycomment{margin-left:10px !important;font-size:16px;color:lightblue !important;position:relative;left:-140px !important}
/* 评价客服按钮样式 */
.btnCommentKefu{font-size:16px;color:lightblue !important;position:relative;left:-50px}
.state_member_card{
    width:320px;
    height:220px;
    /* background-color: blue; */
    margin-left: 50px;
    overflow: hidden;
    border-bottom:2px dashed white;
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
    height:160px;
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
</style>