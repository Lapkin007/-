<template>
  <div name="header">
       <!-- 头部分 -->
     <el-header>
      <el-row>
          
          <el-col :offset="4" :span="3">
              <div class="logobox">
              <img src="../../assets/img/logo.png" />
              <div class="logotext">哇塞影院</div></div>
          </el-col>
          <el-col :span="5">
              <el-link class="link_btn" href="#/" :underline="false" active-class="active" :style="$store.state.color1" @click="changecolor(1)" >首页</el-link>
              <el-link class="link_btn" href="#/movie_page" :underline="false" active-class="active" :style="$store.state.color2" @click="changecolor(2)">电影</el-link>
              <el-link class="link_btn" href="#/activities" :underline="false" active-class="active" :style="$store.state.color3" @click="changecolor(3)">活动</el-link>
              <el-link class="link_btn" href="#/comment" :underline="false" active-class="active" :style="$store.state.color4" @click="changecolor(4)"> 留言</el-link>
              <el-link class="link_btn" href="#/worker_login" :underline="false" active-class="active"  > 客服登录</el-link>
          </el-col>
          <el-col :span="5">
              <!-- <el-input placeholder="请输入电影名" v-model="search" class="search_movie" clearable prefix-icon="el-icon-search"></el-input> 
              -->
              <div class="header-search">
          <el-autocomplete
            style="width: 350px;margin-top:20px"
            v-model="search"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入电影名"
            @select="handleSelect"
            prefix-icon="el-icon-search"
          />
          </div>
          </el-col>
          
          <el-col :span="4" :offset="2" class="head" >
              <div class="headimg"><img
             style="width: 50px; height: 50px"
              :src="avatar"
              />
              </div>
              <button  class="headimg_name" @mouseenter="showlist" @mouseleave="closelist">{{nickname}}</button>
           </el-col>
          
      </el-row>
     </el-header>
     <!-- 头部分结束 -->
    <!-- 用户名选中列表 -->
     <ul class="choosebox" v-show="ishow" @mouseenter="showlist" @mouseleave="closelist">
         <a href="#" @click.prevent="changeActiveTab(1)"> <li>购物车</li></a>
         <a href="#" @click.prevent="changeActiveTab(2)"> <li>我的订单</li></a>
         <a href="#" @click.prevent="changeActiveTab(3)"> <li>个人设置</li></a>
         <a href="#" @click="quit_login()"> <li ><i class="el-icon-switch-button"></i>{{Login_text}}</li></a>
     </ul>
    <!-- 用户名选中列表结束 -->
  </div>
</template>

<script>

import{UserInfo} from "@/api/user";
import{SearchFilm} from "@/api/film";
export default {
data(){
        return{
            //定义
            Login_text:"登录",
            //初始化设置,导航栏
            name:"header",
            //定义导航数
            navnum:0,
            //定义导航选中样式，首页，电影，活动，留言
            color1:{color:"#5b5d63"},color2:{color:"#5b5d63"},color3:{color:"#5b5d63"},color4:{color:"#5b5d63"},
            linkcolor:["#5b5d63","#5b5d63","#5b5d63","#5b5d63"],
            ishow:false,
            //搜索
            search:"",
            searchlist:[],
            //用户名头像
            nickname:"未登录",  
            avatar:require("../../assets/img/avatar.png"),
        }
    },
    methods:{
        showlist:function(){
            this.ishow=true;
        },
        closelist:function(){
            this.ishow=false;
        },
        changecolor(n){
            this.$store.state.navnum=n;

        },
        // 检测登录
        isLogin() {
        if(localStorage.getItem("uid")==null){
        this.$confirm('未检测到登录', '提示', {
          confirmButtonText: '去登陆',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/login");
        });}
        if(localStorage.getItem("uid")!=null)
        {this.$router.push("/personal_center");}
        },
        //搜索栏-模糊查询
        querySearchAsync(queryString,cb){
            
                SearchFilm().then((res)=>{
                    setTimeout(() => {
                    this.searchlist=res.data; 
                    cb(this.loadSearchList())
                }, 100);
                
                });

            
        },
        handleSelect(item){
           this.$router.push("/film_detail?fid="+item.id);
           window.setTimeout(function () {
             window.location.reload();
      },1000)
        },
        //加载搜索列表
        loadSearchList(){
            let arr=new Array();
            for(let i=0;i<this.searchlist.length;i++){
                if(this.searchlist[i].name.indexOf(this.search) > -1){
                  arr.push({
                    id:this.searchlist[i].id,
                    value:this.searchlist[i].name,
                });        }
            };
            return arr;
        },

        //导航栏定位TAB
        changeActiveTab(n){
           if(n==1){this.$store.state.activeTab="first";this.isLogin();}
           if(n==2){this.$store.state.activeTab="second";this.isLogin();}
           if(n==3){this.$store.state.activeTab="third";this.isLogin();}
        },
        // 退出登录
        quit_login(){
             localStorage.removeItem("uid");
             localStorage.removeItem("token");
             this.nickname="未登录";
             this.$router.push("/login");
        },
        //查询个人信息
        QueryUserInfo(uid){
            
            if(uid!=null){
                console.log(uid);
                const vo={id:localStorage.getItem("uid")};
                 console.log(vo);
            UserInfo(vo).then((res)=>
            {
                if(res.code==200){   
                this.nickname=res.data.username; this.avatar=res.data.avatar;
                console.log("查询个人信息header中的QueryUserInfo");
                console.log(res);
                this.$store.state.islogin=true;
                }
            })
        }
        else{
            console.log("ID未找到,请登录")
        }
        },
    },
    created:function(){
         console.log(this.$store.state.navnum);
        console.log("上面为显示页面代码");
            if(this.$store.state.navnum==0)
			{this.color1.color="blue"};
            if(this.$store.state.navnum==1)
			{this.color2.color="blue"};
            if(this.$store.state.navnum==2)
			{this.color3.color="blue"};
            if(this.$store.state.navnum==3)
			{this.color4.color="blue"};
		},
        mounted:function(){
        console.log("取UID");
        console.log(localStorage.getItem("uid"));
        console.log("取token");
        console.log(localStorage.getItem("token"));
        this.QueryUserInfo(localStorage.getItem("uid"));
        if(localStorage.getItem("uid")!=null){
            this.Login_text="退出登录";
        }
        else{this.Login_text="登录"}
    }
		
}


</script>

<style scoped>
.active{
    background-color: blue !important;
}
.el-row{
    /* background-color: blue; */
   overflow: hidden;
}
.el-col{
    /* background-color: rgb(25, 97, 46); */
    overflow: hidden;
    height: 70px;
}
.mainpage{
    width: 100%;
    height: 100%;
    overflow: hidden;
}
.el-container{
    background-color: #fff;
    margin-top: 20px;
}
.el-header{
    background-color: rgb(255, 255, 255);
    color: honeydew;
    min-height:100px;
}

.el-aside{
    background-color:darkslategray;
    color: honeydew;
    height: 160px;
    max-width: 90px;
}
.el-main{
    background-color: blueviolet;
    color: honeydew;
    height: 1800px;
}
.link_btn{
    font-size:18px;
    margin-top: 28px;
    margin-right: 27px;
    color:#5b5d63;
    
}


.search_movie{
    margin-top: 18px;
}
.headimg{
    margin-top: 10px;
    border-radius: 30px;
    width: 50px;
    height: 50px;
    overflow: hidden;
    background-color: #91949c;
    float: left;
    border: 1px solid rgb(136, 135, 135);
    background-image:src("../assets/img/nim.png");
}
.headimg_name{
    height:70px;
    line-height: 70px;
    margin: auto 10px;
    width:100px;
    background-color: transparent;
    border-style: none;
}
.headimg_name:hover{
    color: blue;
}

.choosebox{
    position:absolute;
    width:200px ;
    height:200px;
    left: 77.5%;
    top:8.2%;
    list-style-type: none;
    margin: 0;
    padding: 0;
    background-color:rgb(238, 238, 238);
    border-radius: 10px;
    z-index: 8;
}
.head:hover .choosebox{
    display: none;
}
.choosebox a{
    text-decoration: none;
    color: black;
}
.choosebox li{
    
    position: relative;
    display:block;
    width:200px;
    height:50px;
    line-height: 50px;
    text-align: center;
}
.choosebox li:hover{
    background-color: royalblue;
}
.el-icon-switch-button{
    font-size:23px;
    margin-right: 10px;
    
}
.logobox img{
    width:60px;
    height:60px;
    float:left;
    margin-top: 5px;
    
}
.logotext{
    float: left;
    line-height: 75px;
    font-size:28px;
    font-weight: 600;
    margin-left:10px ;
    color: black;
}
/* 头部和底部结束 */
</style>