<template>
  <div class="all">
    <el-container>
        <!-- 标签部分 -->
        <el-header>
            <div class="tagbox">
                 <el-row class="tagrow">
                   <el-col :span="1" >
                <span style="margin-left:10px">类型:</span>
                   </el-col>
                    <el-radio-group v-model="selectType" class="tag"  @change="tag_change()">
                <el-radio-button v-for="(item,index) in typeList" :key="index" :label="item" class="tagRadiobutton" border-size="mini" ></el-radio-button>
                    </el-radio-group>
                 </el-row>  
             <el-row class="tagrow">
                 <el-col :span="1" >
                <span style="margin-left:10px">地区:</span>
                   </el-col>
                   <el-radio-group v-model="selectRegion" class="tag"  @change="tag_change()">
                <el-radio-button v-for="(item,index) in regionList" :key="index" :label="item" class="tagRadiobutton" border-size="mini"></el-radio-button>
                    </el-radio-group>
             </el-row>
            </div>
        </el-header>
        <!-- 标签部分结束 -->
        <el-main>
        <!-- 电影区 -->
        <el-col :span="5" v-for="(item,index) in FliterMovieList" :key="index" :offset="1" style="margin-top:20px">
       
        <div @click="Movie_card_click(item)">
<el-link :underline="false">
      <el-card :body-style="{ padding: '13px 0px' }" style="margin-bottom:30px;height:330px" >
      <img :src="item.cover" style="width: 170px;height:250px" alt="ERROR" title=""/>
      <div style="padding: 5px;">
        <div class="movie_info">
           <div class="movie_info_inside">{{item.name}}</div>
        </div>
      </div>
    </el-card>
    </el-link>
        </div>
  </el-col>
        <!-- 电影区结束 -->
        </el-main>
    </el-container>
    





  </div>
</template>

<script>
import {QueryFilmList} from "@/api/film"
export default {
   data(){
       return{
           selectType:'全部',
           selectRegion: "全部",
            typeList: [
        "全部",
        "家庭",
        "惊悚",
        "科幻",
        "爱情",
        "动作",
        "喜剧",
        "恐怖",
        "悬疑",
        "犯罪",
        "冒险",
        "战争",
        "历史",
        "武侠",
        "传记",
        "古装",
        "其他",
      ],
      regionList: [
        "全部",
        "内地",
        "香港",
        "台湾",
        "美国",
        "韩国",
        "日本",
        "泰国",
        "印度",
        "法国",
        "英国",
        "德国",
        "其他",
      ],
      movielist:[],
      newmovielist:[],
       }
       
   },
   methods:{
    //    点击标签刷新
        tag_change(){
            this.newmovielist=undefined;
            console.log(this.newmovielist);
            this.newmovielist = new Array();
            console.log(this.newmovielist);
        },
        // 查询所有电影接口
        CreateMovieList(){
            QueryFilmList().then((res)=>{
                if(res.code==200){
                    for(var i=0;i<res.data.length;i++)
                    {
                        if(res.data[i].status==1){
                            this.movielist.push(res.data[i]);
                        }
                    }
                    
                }
            })
        },
        //点击电影卡片
        Movie_card_click(item){
            this.$router.push("/film_detail?fid="+item.id);
            this.$store.state.navnum=1;
            this.$store.state.color2.color="blue";
            this.$store.state.color1.color="#5b5d63";this.$store.state.color3.color="#5b5d63";this.$store.state.color4.color="#5b5d63";
        },
   },
   computed: {
    //    筛选电影表的方法
	FliterMovieList: function () {
        if(this.selectType=="全部"&this.selectRegion=="全部"){
            for(var i=0;i<this.movielist.length;i++)
            {
            this.newmovielist.push(this.movielist[i]);
            }
        }
        else if(this.selectType!="全部"&this.selectRegion!="全部"){
		for(var i=0;i<this.movielist.length;i++)
        {
        if(this.selectType==this.movielist[i].type&this.selectRegion==this.movielist[i].region)
        {
        this.newmovielist.push(this.movielist[i]);
        }
        }
        }
        else if(this.selectType=="全部"&this.selectRegion!="全部"){
		for(var i=0;i<this.movielist.length;i++)
        {
        if(this.selectRegion==this.movielist[i].region)
        {
        this.newmovielist.push(this.movielist[i]);
        }
        }
        }
        else if(this.selectType!="全部"&this.selectRegion=="全部"){
		for(var i=0;i<this.movielist.length;i++)
        {
        if(this.selectType==this.movielist[i].type)
        {
        this.newmovielist.push(this.movielist[i]);
        }
        }
        }
        return this.newmovielist;

	},

    },
   beforeCreate:function(){
			console.log("BeforeCreate");
            this.$store.state.navnum=1;
            this.$store.state.color2.color="blue";
            this.$store.state.color1.color="#5b5d63";this.$store.state.color3.color="#5b5d63";this.$store.state.color4.color="#5b5d63";
		},
        mounted:function(){
            // 查询电影添加到MovieList
            this.CreateMovieList();
            console.log(this.movielist);
             console.log(this.newmovielist);
        }
}
</script>

<style scoped>
.tagrow{
   height: 50px;
   line-height: 50px;
   color:black;
   font-size:14px;
}
.all{
    width:100%;
    height:100%;
    text-align: center;
}
.tagbox{
    position: relative;
    width:1200px;
    height:100px;
    margin: 0px auto;
    /* background-color: brown; */
    padding: 0px;
    
}
.tag{float:left;margin-left:10px;margin-top:5px}
.tagRadiobutton{
    width:65px;
    
}
.el-header{
    background-color: rgb(255, 255, 255);
    color: honeydew;
    min-height:100px;
}
.el-main{
    margin: 0 auto;
    /* background-color: blueviolet; */
    color: honeydew;
    width:1300px;
}
/* 电影区 */
.image {
    width: 100%;
    display: block;
  }
  .showTagName{
    display:none;width:170px;height:30px;position:relative;top:-39px;color:white;background-color:rgba(1, 1,1, 0.3);
  }
  .el-col:hover div span{
    display: inline-block;
  }
  .movie_info_inside{margin-top:0px;width:250px;color:black;font-size:19px;text-align:center;position:relative;}
</style>