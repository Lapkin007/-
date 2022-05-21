<template>
<div class="outbox" >
  <el-container>
      
  <el-header>
      <el-steps :active="2" align-center style="width:1200px;margin:0 auto" >
  <el-step title="步骤1" description="选择电影场"></el-step>
  <el-step title="步骤2" description="选择座位号"></el-step>
  <el-step title="步骤3" description="加入购物车"></el-step>
  <el-step title="步骤4" description="结尾付款"></el-step>
</el-steps>
  </el-header>
  <el-container >
      <!-- 侧边电影信息 -->
      <el-aside>
          <!-- 电影海报地区等 -->
          <h3 style="margin:20px auto;width:300px;text-align:center">{{movie_info.arrangement.name}}</h3>
          <div class="sidebox1">
           <img :src="movie_info.film.cover"/>
           <span >类型:{{movie_info.film.type}}</span><br/><br/>
           <span>地区:{{movie_info.film.region}}</span><br/><br/>
           <span>时长:{{movie_info.film.duration}}</span>
          </div>
          <!-- 时间票价放映类型 -->
          <div class="boxside2" >
            <p>放映:{{movie_info.arrangement.type}}</p>
            <p>开场:{{movie_info.arrangement.startTime}}</p>
            <p>散场:{{movie_info.arrangement.endTime}}</p>
            <p>票价:${{movie_info.arrangement.price}}</p>
          </div>
          <!-- 已选座位和价格 -->
           <div class="boxside3" >
               <p>已选座位:{{selectdSeat}}</p>
               <p>总计:<span>${{totalprice}}</span></p>
           </div>
           <!-- 填入手机号以及购物车 -->
            <div class="boxside4" >
               <el-input placeholder="请输入手机号码" v-model="phoneNumber" @blur="checkTel()" :style="isPassTel ?'':'border:0.5px solid red;width:297px'"></el-input><br/>
               <el-button class="addtocart" @click="AddtoCart()">加入购物车</el-button>
           </div>
      </el-aside>
      <!-- 侧边电影信息结束 -->

      <!-- 选座位界面 方法是生成一个新数组返回，点击座位的时候更换IMG的图像-->
      <el-main>
          <div class="mainbox1">
              <div ><img src="../assets/img/seat3.png"/></div><span >可选座位</span>
              <div ><img src="../assets/img/seat1.png"/></div><span>已售座位</span>
              <div ><img src="../assets/img/seat2.png"/></div><span>已选座位</span>
          </div>
          <!-- 屏幕 -->
          <div class="displayscreen" >荧幕中央</div>
          <!-- 选座位 -->
          <div class="mainbox2">
             <div v-for="(item,index) in SelectSeat" :key="index"><el-button @click="clickseat(index)" class="seatBTN" ><img class="seatimg" :src="item.seatimg" /></el-button></div>
             

          </div>

      </el-main>
  </el-container>
  </el-container>
</div>
</template>

<script>
import{QueryFilmDetailArrangement,QueryFilmSeat,PostToCart}from "@/api/film"

export default {
    data(){
        return{
            //座位样式
            op_seat:require("../assets/img/seat3.png"),
            //验证手机号用
            isPassTel:true,
            // 包含座位的数量和已经选择的座位ID
            film_anpai_info:{
            select_seat:[],
                //总座位
            seatnum:0,
                // 售出电影票信息保存
            saledseat:[],
            },
            //手机号
                phoneNumber:"",
            //由座位数量生成包含 座位ID 座位的图片 的数组
            film_anpai:[],
            //座位样式
            empty_seat:require("../assets/img/seat3.png"),
              buy_seat:require("../assets/img/seat2.png"),
             sale_seat:require("../assets/img/seat1.png"),
            //  用户选中的多个座位
            selectdSeat:[], 
            //路由传参ID
            Arrangement_id:this.$route.query.id,
            //查询排片的电影详情传入的数组
            movie_info:{
                arrangement: {
id: "",
fid: "",
name: "",
seatNumber: 0,
price: 0,
type: "",
date: "",
startTime: "",
boxOffice: 0,
endTime: "",
founder: "",
createAt: ""
},
film: {
id: "",
name: "",
releaseTime: "",
type: "",
status: true,
region: "",
introduction: "",
hot: 13,
cover: "",
duration: 0,
isDelete: "",
}},
        }

    },
    methods:{
        //手机号输入框的失去焦点时间
		checkTel(){
			const TelReg = /^1[3|4|5|7|8][0-9]\d{8}$/ ;
			if(TelReg.test(this.phoneNumber)){
				this.isPassTel=true;
			}else{
				this.isPassTel=false;
                this.$message.error("请输入正确格式的手机号码");
			}
			console.log(this.isPassTel);
		},
        //调用POST接口加入购物车和订单,加入购物车的按钮
        AddtoCart(){
            if(this.phoneNumber==null||this.selectdSeat.length==0||this.phoneNumber==""||this.isPassTel==false)
            {this.$message.error("请正确填写电话或者选择至少一个座位")}
           else{
                const vo={
                uid:parseInt(localStorage.getItem("uid")),
                aid:parseInt(this.Arrangement_id),
                phone:this.phoneNumber,
                seats:this.selectdSeat,
                price:this.movie_info.arrangement.price,
                     }
            console.log(vo);
            PostToCart(vo).then((res)=>{
              if(res.code==200){
                   if(res.data.success=="success"){
                    this.$message({message:res.data.msg,type:"success"}
                    );
                    window.location.href="#/personal_center";
                   }
                    else{
                 this.$message({message:res.data.msg,type:"warning"},)
                     }
             }
             else{ this.$message.error("fail")}
                                        })
            
                }
        },
        //调用查询排片的电影详情接口方法
        QueryFilmInfo(){
             QueryFilmDetailArrangement(this.Arrangement_id).then((res)=>{
                 if(res.code==200){
                 if(res.data.arrangement.id==this.Arrangement_id)
                 {
                     this.movie_info=res.data;
                 };
                 this.film_anpai_info.seatnum=this.movie_info.arrangement.seatNumber;
                
                 }
             })
        },

        //调用查询排片的电影座位接口方法
        QueryFilmSeat(){
             QueryFilmSeat(this.Arrangement_id).then((res)=>{
                 if(res.code==200){
                    
                     this.film_anpai_info.saledseat=res.data;
                     console.log(this.film_anpai_info.saledseat);
                 }
             })
        },

        //点击座位触发的方法 包含显示已选座位的方法
        clickseat(seatnum){
              if(this.film_anpai[seatnum].seatimg==this.sale_seat){
                  alert("座位已售出,请选择别的座位");
              }
              else if(this.film_anpai[seatnum].seatimg==this.empty_seat)
              {
                  this.film_anpai[seatnum].seatimg=this.buy_seat;
                  this.selectdSeat.push(seatnum+1);
              }
              else if(this.film_anpai[seatnum].seatimg==this.buy_seat)
              {
                  this.film_anpai[seatnum].seatimg=this.empty_seat;
                  for(var i=0;i<this.selectdSeat.length;i++)
                  {
                      if(this.selectdSeat[i]==seatnum+1){this.selectdSeat.splice(i,1)}
                  }
              }
        },
    },
    computed:{
        //生成总价格价格
        totalprice:function(){return(this.movie_info.arrangement.price*this.selectdSeat.length)},
        //用来生成一个新的座位表,由座位数量生成包含 座位ID 座位的图片 的数组
        SelectSeat:function(){
            this.film_anpai=undefined;
            this.film_anpai=new Array();
            for(var i=0;i<this.film_anpai_info.seatnum;i++)
            {
                
                this.film_anpai.push({seat_id:i,seatimg:this.empty_seat});
            
            }
            for(var j=0;j<this.film_anpai_info.saledseat.length;j++)
                {
                     
                     this.film_anpai[this.film_anpai_info.saledseat[j]-1].seatimg=this.sale_seat;
                }
            return this.film_anpai;
        }
    },
    mounted:function(){
        //调用查询电影详情接口方法
       this.QueryFilmInfo();
       //调用查询电影已选座位接口方法
        this.QueryFilmSeat();
    }

}
</script>

<style scoped>
.outbox{position: relative;
    margin: 30px auto;
    width:1600px;
    height:1000px;
    overflow:hidden}
.el-header{
    /* background-color: aquamarine; */
    min-height: 100px;
}
.el-aside{
    background-color:rgb(224, 224, 224);
    color: honeydew;
    min-width: 400px;
    max-width: 600px;
    min-height: 700px;
    overflow:hidden;
}
.el-main{
    background-color: rgb(235, 235, 235);
    color: honeydew;
    max-width: 1200px;
    min-height: 700px;
    padding: 0px;
    
}   

/* 侧边 */
.boxside2{width:300px;height:120px;margin:10px auto;overflow:hidden;padding:0px}
.boxside2 p{
    margin: 7px auto;
    color:black;
}
.boxside3{border-top:1px solid black;border-bottom:1px solid black;width:300px;height:70px;margin:10px auto;overflow:hidden;padding:0px}
.boxside3 p{
    margin: 7px auto;
    color:black;
}
.boxside4{width:300px;height:110px;margin:10px auto;overflow:hidden;padding:0px}
.boxside4 p{
    margin: 7px auto;
    color:black;
}
.addtocart{color:white;margin:20px auto;width:180px;display:block;border: 1px solid rgb(255, 65, 65);border-radius:20px;background-color: rgb(255, 73, 73);}
.sidebox1{
    width:300px;height:250px;margin:30px auto;overflow:hidden
}
.sidebox1 img{height:250px;display:inline-block;float:left}
.sidebox1 span{display:inline-block;float:left;color:black;margin-left:20px}
/* 选座位 */
.displayscreen{width:800px;height:30px;margin:0px auto;text-align:center;background-color:gray}
.mainbox1{width:800px;height:50px;margin:30px auto;text-align:center}
.mainbox1 div{width:50px;height:50px;overflow:hidden;display:inline-block;margin:0px 40px;padding:0}
.mainbox1 img{height:40px}
.mainbox1 span{display:inline-block;height:50px;line-height:50px;position:relative;top:-20px;left:-20px;color:black}
.mainbox2{width:760px;height:700px;margin:20px auto;text-align:center;padding-top:50px}
.mainbox2 div{display:inline-block;width:40px;height:40px;margin:10px}
.seatBTN{background-color:transparent;border:none;width:40px;height:40px;}
.seatBTN:hover{background-color: transparent;}
.seatimg{position:relative;width:40px;left:-20px;top:-10px}
</style>