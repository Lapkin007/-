<template>
  <div class="home">
    <!-- 轮播图 -->
    <div class="post">
      <el-carousel height="500px" width="1500px">
        <el-carousel-item v-for="(item, index) in posterList" :key="index">
          <el-image
            :src="item.url"
            alt=""
            style="width: 1500px; height: 500px"
          ></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
    <br /><br />
    <!-- 轮播图结束 -->
    <el-container>
      <!-- 左侧电影区 -->
      <el-main>
        <h2 class="sidetitle">热门电影</h2>
        <br />
        <el-col
          :span="5"
          v-for="(item, index) in movie_area"
          :key="index"
          :offset="1"
          style="margin-top: 20px"
        >
          <div @click="Movie_card_click(item)">
            <el-card
              :body-style="{ padding: '13px 0px' }"
              style="margin-bottom: 30px; height: 330px"
            >
              <img
                :src="item.cover"
                style="width: 170px; height: 250px"
                alt="ERROR"
                title=""
              />
              <div style="padding: 5px">
                <span class="showTagName">{{ item.name }}</span>
                <div class="movie_info">
                  <div class="movie_info_wanttosee">{{ item.hot }}人想看</div>
                  <div class="movie_info_on">
                    {{ item.releaseTime }}&nbsp;上映
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-main>
      <!-- 左侧电影区结束 -->
      <!-- 右侧排行区 -->
      <el-aside
        ><span class="hot_title">热门榜单TOP10</span>
        <div class="top1" style="margin-top: 30px">
          <div @click="Movie_card_click(hot[0])">
            <div class="top_rank_num">1</div>
            <img
              width="120"
              :src="hot[0].cover"
              alt="ERROR"
              style="float: left"
            />
            <span class="top1_text1">{{ hot[0].name }}</span
            ><br />
            <span class="top1_text2">上映时间:{{ hot[0].releaseTime }}</span
            ><br />
            <span class="top1_text3">热度值:{{ hot[0].hot }}</span
            ><br />
          </div>
        </div>

        <div class="top2" style="margin-left: -50px">
          <div @click="Movie_card_click(hot[1])">
            <div class="top_rank_num">2</div>
            <img
              width="150"
              height="180"
              :src="hot[1].cover"
              alt="ERROR"
              style="float: left"
            />
            <span class="top2_text1">{{ hot[1].name }}</span
            ><br />
            <span class="top2_text2">热度值:{{ hot[1].hot }}</span
            ><br />
          </div>
        </div>
        <div class="top2" style="margin-left: 8px">
          <div @click="Movie_card_click(hot[2])">
            <div class="top_rank_num">3</div>
            <img
              width="150"
              height="180"
              :src="hot[2].cover"
              alt="ERROR"
              style="float: left"
            />
            <span class="top2_text1">{{ hot[2].name }}</span
            ><br />
            <span class="top2_text2">热度值:{{ hot[2].hot }}</span
            ><br />
          </div>
        </div>
        <br />
        <!-- 第四位开始 -->
        <div class="top_more">
          <el-row
            v-for="(item, index) in hot.slice(3)"
            :key="index"
            :offset="1"
            style="height: 50px; line-height: 50px"
          >
            <span class="top_more_span1"
              ><span class="top_more_span2">{{ index + 4 }}</span
              >&nbsp;{{ item.name }}</span
            >
            <span class="top_more_span3">热度值:{{ item.hot }}</span>
          </el-row>
        </div>
      </el-aside>
      <!-- 右侧排行区结束 -->
    </el-container>
  </div>
</template>

<script>
import { QueryFilmList, ListAllPost, QueryHotRank } from "@/api/film";
import { parseTime } from "@/plugins/DateTran"; //日期格式转化
export default {
  data() {
    return {
      //轮播图
      posterList: [{ url: "" }],
      //左侧热门电影区
      movie_area: [{ name: "XXXX", cover: "", hot: "xxx", releaseTime: "" }],
      //右侧榜单
      hot: [
        { name: "XXXX", cover: "", hot: "xxx" },
        { name: "XXXX", cover: "", hot: "xxx" },
        { name: "XXXX", cover: "", hot: "xxx" },
      ],
    };
  },
  methods: {
    //点击电影卡片的跳转
    Movie_card_click(item) {
      this.$store.state.navnum = 1;
      this.$store.state.color2.color = "blue";
      this.$store.state.color1.color = "#5b5d63";
      this.$store.state.color3.color = "#5b5d63";
      this.$store.state.color4.color = "#5b5d63";
      this.$router.push("/film_detail?fid=" + item.id);
    },
    //海报
    PosterQuery() {
      ListAllPost().then((res) => {
        if (res.code == 200) {
          this.posterList = res.data;
        }
      });
    },
    //排行查询
    RankQuery() {
      this.hot = undefined;
      this.hot = new Array();
      QueryHotRank().then((res) => {
        if (res.code == 200) {
          this.hot = res.data;
          //转化其中显示的日期
          for (var i = 0; i < this.hot.length; i++) {
            this.hot[i].releaseTime = parseTime(
              new Date(this.hot[i].releaseTime),
              "{y}-{m}-{d}"
            );
          }
          console.log(this.hot);
        }
        if (this.hot.length < 10) {
          for (var i = this.hot.length; i < 10; i++) {
            this.hot.push({
              name: "XXXX",
              cover: "https://s1.ax1x.com/2022/03/31/qRtZDg.gif",
              hot: "xxx",
            });
          }
        }
      });
    },
  },
  // 创建前方法，实现相应页面LINK变蓝
  beforeCreate: function () {
    this.$store.state.navnum = 0;
    this.$store.state.color1.color = "blue";
    this.$store.state.color4.color = "#5b5d63";
    this.$store.state.color2.color = "#5b5d63";
    this.$store.state.color3.color = "#5b5d63";
  },
  created() {
    console.log("created");
    // 热门接口
    QueryHotRank().then((res) => {
      if (res.code == 200) {
        this.movie_area = res.data;
        for (var i = 0; i < this.movie_area.length; i++) {
          this.movie_area[i].releaseTime = parseTime(
            new Date(this.movie_area[i].releaseTime),
            "{y}-{m}-{d}"
          );
        }
      }
    });
    //海报图POST
  },
  mounted() {
    //海报图POST
    this.PosterQuery();
    //测试用
    // this.$axios
    //    .get("http://localhost:8081/api/post")
    //    .then((res)=>
    //      {
    //        console.log(res);
    //          if(res.data.code==200){
    //              console.log("wtffff");
    //               this.posterList=res.data.data;
    //          }
    //      });
    //热门榜单
    this.RankQuery();
    console.log(this.posterList);
  },
  Updated() {
    //海报图POST
  },
};
</script>

<style scoped>
/* 排行CSS */
.top_rank_num {
  height: 20px;
  width: 20px;
  display: block;
  position: absolute;
  background-color: yellow;
  color: black;
}

.hot_title {
  display: inline-block;
  text-align: left;
  font-size: 28px;
  color: orange;
  width: 400px;
  padding-left: 30px;
}

.top1 {
  position: relative;

  height: 160px;
  width: 300px;
  margin-left: 23px;
  border: 1px solid rgb(231, 231, 231);
  overflow: hidden;
}

.top1_text1 {
  padding-top: 20px;
  display: block;
  width: 400px;
  text-align: left;
  text-indent: 10px;
  color: black;
}

.top1_text2 {
  display: block;
  width: 400px;
  text-align: left;
  text-indent: 10px;
  font-size: 16px;
  color: black;
}

.top1_text3 {
  display: block;
  width: 400px;
  text-align: left;
  text-indent: 10px;
  color: orange;
  font-size: 14px;
}

.top2 {
  width: 145px;
  height: 230px;
  margin-top: 20px;
  display: inline-block;
  border: 1px solid rgb(228, 228, 228);
  overflow: hidden;
}

.top2_text1 {
  color: black;
  text-align: left;
  text-indent: 10px;
  width: 140px;
  height: 22px;
  font-size: 18px;
  display: inline-block;
  overflow: hidden;
}

.top2_text2 {
  display: block;
  margin-top: 5px;
  width: 400px;
  text-align: left;
  text-indent: 10px;
  color: orange;
  font-size: 14px;
}

.top_more {
  margin-top: 30px;
  width: 303px;
  margin-left: 24px;
}

.top_more_span1 {
  width:200px;
  height:50px;
  float: left;
  color: black;
  font-size: 18px;
  overflow:hidden;
}

.top_more_span2 {
  width: 5px;
  color: gray;
  display: inline-block;
  text-align: left;
  margin-right: 30px;
}

.top_more_span3 {
  float: right;
  color: orange;
  font-size: 15px;
  display: inline-block;
}

/* 电影区CSS */
.sidetitle {
  color: orange;
  float: left;
  width: 800px;
  text-align: left;
  text-indent: 40px;
}

.movie_info {
  position: relative;
  top: -41px;
}

.movie_info_wanttosee {
  text-indent: 10px;
  padding: 5px 0;
  text-align: left;
  width: 170px;
  color: orange;
  font-size: 14px;
  margin-left: 9px;
  margin-top: 3px;
  position: relative;
  background-color: rgb(235, 228, 220);
}

.movie_info_on {
  margin-top: 14px;
  text-align: center;
  width: 170px;
  color: gray;
  font-size: 14px;
  margin-left: 9px;
  position: relative;
}

.image {
  width: 100%;
  display: block;
}

.showTagName {
  overflow: hidden;
  display: inline-block;
  width: 170px;
  height: 25px;
  position: relative;
  top: -35px;
  color: white;
  background-color: rgba(1, 1, 1, 0.3);
}

.el-col:hover div span {
  display: inline-block;
}

/* 轮播CSS */
.post {
  position: relative;
  margin: 0 auto;
  width: 1500px;
  overflow: hidden;
}

/* 主页面CSS */
.home {
  background-color: rgb(255, 255, 255);
  text-align: center;
}

.el-aside {
  /* background-color:darkslategray; */
  color: honeydew;
  margin-left: 100px;
  min-width: 400px;
  overflow: hidden;
}

.el-main {
  /* background-color: blueviolet; */
  color: honeydew;

  margin-left: 175px;
  max-width: 1000px;
}
</style>