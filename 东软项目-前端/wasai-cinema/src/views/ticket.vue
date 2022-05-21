<template>
  <div class="all">
      <el-container>
          <!-- 上半 -->
          <!-- 上方电影图片及按钮 -->
         <el-header>
             <div class="film_info">
              <img style="width: 240px;height:350px;float:left" :src="movie_info.cover" alt="error"/>
               <div class="film_detail_info_box">
              <h2 class="film_detail_name" style="overflow: hidden;">{{movie_info.name}}</h2>
              <!-- 下面的filmstar是根据热度值HOT除以3来算的，因为超过5不会显示 -->
                        <el-rate v-model="score" disabled  text-color="#ff9900"
                            score-template="{value}" class="rate"></el-rate>
                            <span class="star">{{movie_info.hot}}</span>
              <span class="info_text1">电影类型：&nbsp; {{movie_info.type}}</span>
              <span class="info_text1">地区:&nbsp;{{movie_info.region}}&nbsp;/&nbsp;时长:&nbsp;{{movie_info.duration}} </span>
              <span class="info_text1">上映日期：&nbsp;{{movie_info.releaseTime}} </span>
              <!-- 购票按钮 -->
              
              <el-button class="btn" disabled>特惠购票</el-button>
              <!-- 评分按钮 -->
              <!-- <el-button class="btn" @click="rate_visible = true;isLogin()">评分</el-button> -->
               <el-dialog title="电影评分" :visible.sync="rate_visible" width="50%"  >
               <el-form :model="form_comment">
                   <el-form-item label="" >
                   <el-rate v-model="form_comment.comment_rate" show-score text-color="#ff9900"></el-rate>
                   </el-form-item>
                <el-form-item label="" >
               <el-input v-model="form_comment.content" autocomplete="off" type="textarea" maxlength="20" show-word-limit :autosize="{ minRows: 10, maxRows: 10}"
                 placeholder="请输入内容"></el-input>
               </el-form-item>
               </el-form>
               <span slot="footer" class="dialog-footer">
               <el-button @click="rate_visible = false">取 消</el-button>
               <el-button type="primary" @click="ClickPostComment()">确认提交</el-button>
               </span>
               </el-dialog>
               </div>
             </div>
         </el-header>
         <!-- 上半结束 -->

         <!-- 间幕不要动 -->
         <el-link icon="el-icon-back"  @click="ClickBack()" :underline="false" class="middle_link" >返回</el-link>
         <!-- 间幕结束 -->
          <h1 style="text-align:left;color:black;margin-left:400px">电影购票</h1>
         <!-- 下半 -->
         <el-main>
              <!-- 剧情简介结束 -->

             <!-- 电影无排片 -->
             <div v-show=" nofilm_visible">
              <span style="color:red;font-size:20px">本片暂时没有场次</span>
             </div>
             <!-- 电影排片 -->
             <div v-show="film_visible">
               <ul class="movie_comment_box" v-infinite-scroll="load" style="overflow:auto" >
                   <li v-for="(item,index) in new_film_list" :key="index">
                       <span class="movie_comment_span1" >{{item.name}}</span>
                        <br/><span class="movie_comment_span2" >{{item.date}}&nbsp;{{item.startTime}}-{{item.endTime}}</span>
                        <br/><span class="movietag">{{item.type}}</span>
                        <el-button class="btn" style="float:right;margin-top:-20px" @click="buy_ticket_click(item)">选座购票</el-button>
                       <span class="movie_comment_span3" >{{item.price}}¥</span>
                      
                   </li>
               </ul>
             </div>
             <!-- 电影排片结束 -->

         </el-main>
         <!-- 下半结束 -->
      </el-container>
  </div>
</template>

<script>
import {QueryFilmList,QueryFilmArrangement,PostFilmComment} from "@/api/film";
import { parseTime } from "@/plugins/DateTran";
export default {
    data(){
        return{
            //初始化评分
            score:0,
            //控制评分框显示
            rate_visible:false,
            //控制排片表显示
            film_visible:true,
            //控制无排片的显示
            nofilm_visible:false,
            //提交评分表单
            form_comment:{comment:"",star:0,fid:"",uid:""},
            //路由传参FID
            Film_id:this.$route.query.fid,
            //电影详情，接口传入数据
            movie_info:[],
            //测试用数据
            testvalue: 3.7,
            //经过接口之后的表
            new_film_list:[]

        }
    },
    methods: {
        ClickBack(){
            this.$router.push("/film_detail?fid=" + this.Film_id);
        },
        //生成一个除以3的缩略星级热度
        filmstar(n){
            this.score=this.movie_info.hot/n;
        },
        //点击购票之后的跳转
        buy_ticket_click(item){
            if(this.$store.state.islogin==false){
        this.$confirm('未检测到登录', '提示', {
          confirmButtonText: '去登陆',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/login");
        });}else{
             this.$store.state.navnum=1;
      this.$store.state.color2.color="blue";
            this.$store.state.color1.color="#5b5d63";this.$store.state.color3.color="#5b5d63";this.$store.state.color4.color="#5b5d63";
            this.$router.push("/seat?id="+item.id);
        }
        },
      load () {
        this.count += 2
      },
         //电影场次信息(测试用)
        //  QueryMovieArrangement(){
        //      QueryFilmArrangement().then((res)=>{
        //         if(res.code==200){
        //            for(var i=0;i<res.data.arrangements.length;i++){
        //           if(this.Film_id==res.data.arrangements[i].fid)
        //           {this.new_film_list.push(res.data.arrangements[i])}
        //         };
        //         if(this.new_film_list.length>=1){this.film_visible=true;this.nofilm_visible=false}
        //         else if(this.new_film_list.length==0){this.film_visible=false;this.nofilm_visible=true};
        //         }
        //     })
        //  },
        // 电影场次信息接口
         QueryMovieArrangement(){
             QueryFilmArrangement(this.Film_id).then((res)=>{
                if(res.code==200){
                    console.log(res);
                   this.new_film_list=res.data;
                };
                if(this.new_film_list.length>=1)
                {this.film_visible=true;this.nofilm_visible=false}
                else if(this.new_film_list.length==0)
                {this.film_visible=false;this.nofilm_visible=true};
                for(var i=0;i<this.new_film_list.length;i++){
                    this.new_film_list[i].date=parseTime(new Date(this.new_film_list[i].date),'{y}-{m}-{d}')
                }
                }
                
            )},
        //电影详细信息
        QueryMovieinfo(){
            QueryFilmList().then((res)=>{
                if(res.code==200){
                   for(var i=0;i<res.data.length;i++){
                  if(this.Film_id==res.data[i].id) { 
                      this.movie_info = res.data[i];
                      this.movie_info.releaseTime = parseTime(
                      new Date(this.movie_info.releaseTime),"{y}-{m}-{d}");
                      }
                }
                }
            })
        },
       //点击评论
        // ClickPostComment(){
        //     this.form_comment.fid=this.Film_id;
        //     this.form_comment.uid=localStorage.getItem("uid");
        //     const PostComment=this.form_comment;
        //     PostFilmComment(PostComment).then((res)=>{
        //         if(res.code==200)
        //         {
        //             this.$message({message:"评论成功",type:"success"},);
        //             this.rate_visible = false;
        //             }
        //         else{
        //             this.$message({message:"评论失败",type:"warning"},);
        //         }
        //     })
        // },
        //查看是否登录
      isLogin(a) {
        if(this.$store.state.islogin==false){
        this.$confirm('未检测到登录', '提示', {
          confirmButtonText: '去登陆',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/login");
        });}
        },
        preload(){
            console.log(this.new_film_list);
            this.new_film_list=undefined;
            this.new_film_list = new Array();
            console.log(this.new_film_list);
        }
    },
    //生命周期函数。用来筛选新排片列表，生成new_film_list
            created:function(){
            this.new_film_list=undefined;
            this.new_film_list = new Array();
			console.log("BeforeTicketCreate");  
              
		},
        updated:function(){
        //热度星级除以3算法,直接写进computed有BUG
        this.filmstar(3);
    },
        //生命周期函数。用来调用接口
        mounted:function(){
        //查询电影列表并添加到movie_info列表
        this.QueryMovieinfo();
        //查询电影列表并添加到new_film_list列表
        this.QueryMovieArrangement();
        //查看电影有没有场次
        if(this.new_film_list.length>=1){this.film_visible=true;this.nofilm_visible=false}
        else if(this.new_film_list.length==0){this.film_visible=false;this.nofilm_visible=true};
    }
}
</script>

<style scoped>
/* 全局及header和main设置 */
.all{
    width:100%;
    height:100%;
    text-align: center;
}
.el-header{
    background-color: rgb(78, 89, 241);
    color: honeydew;
    min-height:100px;
    width:1400px;
    margin: 0 auto;
    max-height: 350px;
    min-height: 350px;
}
.el-main{
    margin: 0 auto;
    /* background-color: blueviolet; */
    color: honeydew;
    width:1100px;
    max-height:400px;
    min-height:400px;
    overflow:auto
}
/* 上方电影图片及按钮CSS */
.film_info{
    width:1100px;
    height:400px;
    /* background-color: rgb(90, 155, 117); */
    position: relative;
    margin: 50px auto;
}
.film_detail_name{
    display: inline-block;
    float:left;
    font-size:30px;
    color:white;
    width:300px;
    /* background-color: brown; */
    text-align: left;
    height: 143px;
    line-height: 50px;
    margin: -3px;

}
.film_detail_info_box{
    float:left;margin-left:60px;position:relative;
    /* background-color: blue; */
    height: 400px;
    width:500px;
    }
    .star{
    position:absolute;top:40px;
    color: rgb(255, 136, 0);
}
.rate{
    display: inline-block;
    /* background-color: aqua; */
    height: 100px;
    margin-top:40px ;
    width: 200px;
    text-align: left;
}
.info_text1{
    height: 30px;
    line-height: 30px;
    /* background-color: aqua; */
    display: block;
    text-align: left;
}
.btn{
    margin-top:15px;
    background-color: rgb(243, 77, 77);
    border: none;
    color: white;
    float: left;
    width: 100px;
    margin-right: 20px;
    font-size: 13px;
}
.el-rate__text{
    margin-top:-5px !important ;
}
/* 电影排片CSS */
 .movie_comment_box{
    list-style-type: none;
    margin: 0;
    padding:0;
     /* background-color: blue;  */
} 

.movie_comment_box li{
    text-align: left;
    height:80px;
     /* background-color: brown;  */
    margin-bottom: 20px;}
.movie_comment_box li img{
    width:60px;height:60px;border:1px solid gray;border-radius:60px;
}
.movietag{
    float:left;color:blue;border:1px solid lightblue;width:60px;text-align:center;border-radius: 20px;
}
.movie_comment_span1{display:inline-block;float:left;color:black;width:450px}
.movie_comment_span2{float:left;color:gray}
.movie_comment_span3{margin-top:-15px;font-size:20px;margin-right:50px;color:orange;float:right}
/* 间幕 */
.middle_link{margin-bottom:10px;display:block;font-size:16px;width:100px;margin-left:350px;margin-top:60px}
</style>