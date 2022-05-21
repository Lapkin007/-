<template>
    <div class="all">
        <el-container>
            <!-- 上半 -->
            <!-- 上方电影图片及按钮 -->
            <el-header>
                <div class="film_info">
                    <img style="width: 240px; height: 350px; float: left" :src="movie_info.cover" alt="error" />
                    <div class="film_detail_info_box">
                        <h2 class="film_detail_name" style="overflow: hidden">
                            {{ movie_info.name }}
                        </h2>
                        <!-- 下面的filmstar是根据热度值HOT除以3来算的，因为超过5不会显示 -->
                        <el-rate v-model="score" disabled  text-color="#ff9900"
                            score-template="{value}" class="rate"></el-rate>
                            <span class="star">{{movie_info.hot}}</span>
                        <span class="info_text1">电影类型：&nbsp; {{ movie_info.type }}</span>
                        <span class="info_text1">地区:&nbsp;{{ movie_info.region }}&nbsp;/&nbsp;时长:&nbsp;{{movie_info.duration}}
                        </span>
                        <span class="info_text1">上映日期：&nbsp;{{ movie_info.releaseTime }}
                        </span>
                        <!-- 购票按钮 -->

                        <el-button class="btn" @click="ClickTicket()">特惠购票</el-button>
                        <!-- 评分按钮 -->
                        <el-button class="btn" @click="isLogin()">评分</el-button>
                        <el-dialog title="电影评分" :visible.sync="rate_visible" width="50%">
                            <el-form :model="form_comment">
                                <el-form-item label="">
                                    <el-rate v-model="form_comment.star" show-score text-color="#ff9900"></el-rate>
                                </el-form-item>
                                <el-form-item label="">
                                    <el-input v-model="form_comment.comment" autocomplete="off" type="textarea"
                                        maxlength="20" show-word-limit :autosize="{ minRows: 10, maxRows: 10 }"
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
            <el-link icon="el-icon-back" href="/" :underline="false" class="middle_ellink">返回</el-link>
            <span class="middle_span">详情页面</span>
            <!-- 间幕结束 -->

            <!-- 下半 -->
            <el-main>
                <h1 class="title">剧情简介</h1>

                <!-- 剧情简介 -->
                <p class="shortdesc">{{ movie_info.introduction }}</p>
                <!-- 剧情简介结束 -->

                <h1 style="text-align: left; color: black">电影评论</h1>

                <!-- 电影评论 -->
                <ul class="movie_comment_box" v-infinite-scroll="load" style="overflow: auto">
                    <li v-for="(item, index) in comment" :key="index">
                        <img :src="item.user.avatar" class="movie_comment_img" />
                        <span class="movie_comment_span1">{{ item.user.nickname }}</span>
                        <el-rate v-model="item.star" disabled show-score text-color="#ff9900" score-template="{value}"
                            class="movie_comment_rate"></el-rate><br />
                        <span class="movie_comment_span2">{{
                                item.comment
                        }}</span>
                        <span class="movie_comment_span3">{{
                                item.createAt
                        }}</span>
                    </li>
                </ul>
                <!-- 电影评论结束 -->
            </el-main>
            <!-- 下半结束 -->
        </el-container>
    </div>
</template>

<script>
import { QueryFilmList, QueryFilmComment, PostFilmComment } from "@/api/film";
import { parseTime } from "@/plugins/DateTran";
export default {
    data() {
        return {
            //电影信息
            movie_info: [],
            //电影热度（除以n之后的缩率星级）
            score:0,
            //评论信息
            comment: [],
            //评分表是否可见
            rate_visible: false,
            //评分表单
            form_comment: { comment: "", star: 0, fid: "", uid: "" },
            value: 3,
            //通过网址fid传参数到电影主键
            Film_id: this.$route.query.fid,
        };
    },
    methods: {
        //上传评论接口
      UploadMyEm(vo){
         PostFilmComment(vo).then((res)=>{
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
      //电影评论提交表单
      UploadFilmEva(){
          this.rate_visible = false;
          const vo={
              create_time:new Date(),
              update_time:new Date(),
              comment:this.form_comment.comment,
              uid:parseInt(localStorage.getItem("uid")),
              fid:parseInt(this.Film_id),
              star:this.form_comment.star,
          }
          console.log(vo);
          this.UploadMyEm(vo);
      },
        //电影详细信息
        QueryMovieinfo() {
            QueryFilmList().then((res) => {
                if (res.code == 200) {
                    for (var i = 0; i < res.data.length; i++) {
                        if (this.Film_id == res.data[i].id) {
                            this.movie_info = res.data[i];
                            //日期格式化显示，数据库里不变
                            this.movie_info.releaseTime = parseTime(
                                new Date(this.movie_info.releaseTime),"{y}-{m}-{d}"
                            );
                        }
                    }
                }
            });
        },
        //点击购票
        ClickTicket() {
            this.$router.push("/ticket?fid=" + this.Film_id);
        },
        //点击评论
        ClickPostComment() {
           this.UploadFilmEva();
        },
        //电影评论信息
        QueryMovieFE() {
            QueryFilmComment(this.Film_id).then((res) => {
                if (res.code == 200) {
                    this.comment = res.data;
                    console.log("以下是电影评论表");
                    console.log(this.comment);
                }
            });
        },

        //小功能
        load() {
            this.count += 2;
        },
        //检查是否登录
        isLogin(a) {
            if (localStorage.getItem("uid") == null) {
                this.$confirm("未检测到登录", "提示", {
                    confirmButtonText: "去登陆",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(() => {
                    this.$router.push("/login");
                });
            } else {
                this.rate_visible = true
            }
        },
        //生成一个除以3的缩略星级热度
        filmstar(n){
            this.score=this.movie_info.hot/n;
        }
    },
    computed:{
        
    },
    mounted: function () {
        //查询电影列表并添加到movie_info列表
        this.QueryMovieinfo();
        //查询电影评论并添加到movie_comment列表
        this.QueryMovieFE();
        //测试日期格式化
        //console.log(new Date());
        //console.log(parseTime(new Date()));
    },
    updated:function(){
        //热度星级除以3算法,直接写进computed有BUG
        this.filmstar(3);
    }
};
</script>

<style scoped>
/* 全局及header和main设置 */
.all {
    width: 100%;
    height: 100%;
    text-align: center;
}

.el-header {
    background-color: rgb(78, 89, 241);
    color: honeydew;
    min-height: 100px;
    width: 1400px;
    margin: 0 auto;
    max-height: 350px;
    min-height: 350px;
}

.el-main {
    margin: 0 auto;
    /* background-color: blueviolet; */
    color: honeydew;
    width: 1100px;
    max-height: 400px;
    min-height: 400px;
    overflow: auto;
}

/* 上方电影图片及按钮CSS */
.film_info {
    width: 1100px;
    height: 400px;
    /* background-color: rgb(90, 155, 117); */
    position: relative;
    margin: 50px auto;
}

.film_detail_name {
    display: inline-block;
    float: left;
    font-size: 30px;
    color: white;
    width: 300px;
    /* background-color: brown; */
    text-align: left;
    height: 143px;
    line-height: 50px;
    margin: -3px;
}

.film_detail_info_box {
    float: left;
    margin-left: 60px;
    position: relative;
    /* background-color: blue; */
    height: 400px;
    width: 500px;
}
.star{
    position:absolute;top:40px;
    color: rgb(255, 136, 0);
}
.rate {
    display: inline-block;
    /* background-color: aqua; */
    height: 100px;
    margin-top: 40px;
    width: 200px;
    text-align: left;
}

.info_text1 {
    height: 30px;
    line-height: 30px;
    /* background-color: aqua; */
    display: block;
    text-align: left;
}

.btn {
    margin-top: 15px;
    background-color: rgb(243, 77, 77);
    border: none;
    color: white;
    float: left;
    width: 100px;
    margin-right: 20px;
    font-size: 13px;
}

.el-rate__text {
    margin-top: -5px !important;
}

/* 间幕 */
.middle_span {
    display: inline-block;
    text-align: left;
    width: 90px;
    margin-left: 380px;
    margin-top: 10px;
    font-size: 20px;
    color: black;
}

.middle_ellink {
    margin-bottom: 10px;
    display: block;
    font-size: 16px;
    width: 100px;
    margin-left: 350px;
    margin-top: 60px;
}

/* 剧情简介及标题 */
.shortdesc {
    text-indent: 30px;
    text-align: left;
    color: black;
}

.title {
    text-align: left;
    color: black;
}

/* 电影评论CSS */
.movie_comment_box {
    list-style-type: none;
    margin: 0;
    padding: 0;
}

.movie_comment_box li {
    text-align: left;
    height: 80px;
    margin-bottom: 20px;
}

.movie_comment_box li img {
    width: 60px;
    height: 60px;
    border: 1px solid gray;
    border-radius: 60px;
}

.movie_comment_img {
    display: inline-block;
    float: left;
}

.movie_comment_span1 {
    display: inline-block;
    float: left;
    margin-left: 20px;
    color: black;
    width: 100px;
}

.movie_comment_rate {
    display: inline-block;
    height: 100px;
    margin-top: 40px;
    width: 200px;
    text-align: left;
    height: 30px;
    margin: 0;
    margin-left: 50px;
}

.movie_comment_span2 {
    font-size: 20px;
    margin-left: 50px;
    color: gray;
}

.movie_comment_span3 {
    float: right;
    color: gray;
}
</style>