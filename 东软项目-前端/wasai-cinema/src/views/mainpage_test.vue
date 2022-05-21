<template>
  <div class="mainpage">
   <el-container>
       <!-- 头部分 -->
     <el-header>
      <el-row>
          
          <el-col :offset="4" :span="3">
              <div class="logobox">
              <img src="../assets/img/logo.png" />
              <div class="logotext">哇塞影院</div></div>
          </el-col>
          <el-col :span="4">
              <el-link class="link_btn" :underline="false">首页</el-link>
              <el-link class="link_btn" :underline="false">电影</el-link>
              <el-link class="link_btn" :underline="false">活动</el-link>
              <el-link class="link_btn" :underline="false">留言</el-link>
          </el-col>
          <el-col :span="4">
              <el-input placeholder="请输入电影名" v-model="search" class="search_movie" clearable prefix-icon="el-icon-search"></el-input>
          </el-col>
          
          <el-col :span="4" :offset="4" class="head" >
              <div class="headimg"></div>
              <button  class="headimg_name" @mouseenter="showlist" @mouseleave="closelist">{{name}}</button>
              
          </el-col>
          
      </el-row>
     </el-header>
     <!-- 头部分结束 -->
    <!-- 用户名选中列表 -->
     <ul class="choosebox" v-show="ishow" @mouseenter="showlist" @mouseleave="closelist">
         <a href="#"> <li>购物车</li></a>
         <a href="#"> <li>我的订单</li></a>
         <a href="#"> <li>个人设置</li></a>
         <a href="#"> <li ><i class="el-icon-switch-button"></i>退出登录</li></a>
     </ul>
    <!-- 用户名选中列表结束 -->

     <!-- 主部分 -->
          <el-main>main</el-main>
     <!-- 主部分结束 -->
     <!-- 底部分 -->
          <el-footer class="footer"><p class="footer_text">Copyright　2021　哇塞影院<p><p class="footer_text">2019014867-Linjun</p></el-footer>
     <!-- 底部分结束 -->
      </el-container>
  </div>
</template>

<script>
export default {
    data(){
        return{
            search:"",
            name:"ice",
            ishow:false,
        }
    },
    methods:{
        showlist:function(){
            this.ishow=true;
        },
        closelist:function(){
            this.ishow=false;
        }
    }
}
</script>

<style scoped>
/* 头部和底部 */
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
    background-color: lightcoral;
    color: honeydew;
    min-height:100px;
}
.footer{
    background-color: orange;
    color: honeydew;
    min-height: 100px;
    text-align: center;
    
}
.footer_text{
      font-size:13px;
      color:#91949c;
      margin-bottom:-10px;
      margin-top:10px;
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
    margin-top: 25px;
    margin-right: 27px;
    color: #91949c;
    
}
.search_movie{
    margin-top: 18px;
}
.headimg{
    margin-top: 10px;
    border-radius: 30px;
    width: 50px;
    height: 50px;
    
    background-color: #91949c;
    float: left;
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
    top:9.5%;
    list-style-type: none;
    margin: 0;
    padding: 0;
    background-color:rgb(199, 193, 193);
    border-radius: 10px;
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