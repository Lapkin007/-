<template>
<div class="all">
  <el-tabs type="border-card" tab-position="left" style="height:800px" v-model="activeTab">
      <!-- 购物车 -->
  <el-tab-pane name="first">
    <div slot="label" class="tabtag"><i class="el-icon-goods"></i> 购物车&nbsp;&nbsp;</div>
    <h2>购物车</h2>
    <div style="overflow:auto;height:730px">
    <div class="cart_box" v-for="(item,index) in cart_list" :key="index">
      <!-- 选中框 -->
      <el-checkbox v-model="selectItem" :label="index" style="position:relative;top:-80px" @change="handleCheckedCitiesChange">选中</el-checkbox>
      <!-- 电影海报 -->
      <div class="cart_box1" ><img :src="item.film.cover" /></div>
      <!-- 电影名字座位手机号等信息 -->
       <div class="cart_box2">
         <h3 style="padding:0px">{{item.film.name}}</h3>
         <h5 style="padding:0px;margin-top:-5px">座位：{{item.cart.seats}}</h5>
         <h5>手机：{{item.cart.phone}}</h5>
         <h5>开场时间{{item.arr.date}}&nbsp;{{item.arr.startTime}}</h5>
       </div>
       <!-- 电影价格 -->
<div class="cart_box3" >价格:¥{{item.cart.price}}</div>
<div class="cart_box4" ><el-button type="danger" style="width:130px" @click="ClickDelete(item,index)">删除</el-button></div>
    </div>
    <!-- 购物车页底部 -->
    <div class="cart_footer">
       <div class="cart_footer_1" ><el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox></div>
       <div class="cart_footer_2"  ><el-button type="primary" style="width:130px" @click="cilckconfirmPay()">全部购买</el-button></div>
       <div class="cart_footer_3" >总价格:¥{{totalprice}}</div>
    </div></div>
  </el-tab-pane>
  <!-- 购物车删除弹框 -->
<el-dialog
  title="提示"
  :visible.sync="confirmDelete"
  width="30%"
  :before-close="handleClose">
  <span>确认删除吗?此操作不可撤销</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="confirmDelete = false">取 消</el-button>
    <el-button type="primary" @click="HandleDeleteCartItem(item,index)">确定删除</el-button>
  </span>
</el-dialog>
<!-- 结算弹框1 -->
<el-dialog
  title="提示"
  :visible.sync="confirmPay"
  width="30%"
  :before-close="handleClose">
  <span>总价格:¥{{totalprice}}</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="confirmPay = false">取 消</el-button>
    <el-button type="primary" @click="confirmPay = false;confirmPay2 =true">确定支付</el-button>
  </span>
</el-dialog>

<!-- 结算弹框2 -->
<el-dialog
  title="支付界面"
  :visible.sync="confirmPay2"
  width="30%"
  :before-close="handleClose">
  <img style="width:300px;height:300px;margin:10px 100px" src="../assets/img/QRcode.jpg" />
  <span slot="footer" class="dialog-footer">
    <el-button @click="confirmPay2 = false">取 消</el-button>
    <el-button type="primary" @click="SuccessPay()">模拟支付成功</el-button>
    <el-button type="primary" @click="FailPay()">模拟支付失败</el-button>
  </span>
</el-dialog>

<!-- 购物车页结束 -->


<!-- 我的订单 -->
  <el-tab-pane name="second">
      <span slot="label" class="tabtag"><i class="el-icon-tickets"></i>订单详情</span>
      <h2>我的订单</h2>
      <div style="overflow:auto;height:730px">
      <div class="my_ticket" v-for="(item,index) in ticket_state" :key="index">
        <div class="cart_box1" ><img :src="item.film.cover" /></div>
      <!-- 我的订单电影名字座位手机号等信息 -->
       <div class="cart_box2">
         <h3 style="padding:0px">{{item.film.name}}</h3>
         <h5 style="padding:0px;margin-top:-5px">订购座位：{{item.order.seats}}</h5>
         <h5>开场时间:{{item.arr.date}}&nbsp;{{item.arr.startTime}}</h5>
         <h5>结束时间:{{item.arr.date}}&nbsp;{{item.arr.endTime}}</h5>
       </div>
       <!-- 我的订单电影价格 -->
<div class="cart_box3" >价格:¥{{item.order.price}}</div>
<div class="cart_box4" ><span class="ticket_state_css" :style="changeStateCSS(item)" >{{changeState(item)}}</span></div>
      </div>

      </div>



      </el-tab-pane>

<!-- 基本设置 -->
  <el-tab-pane name="third">
      <span slot="label" class="tabtag"><i class="el-icon-setting"></i>个人设置</span>
       <h2>个人设置</h2>
       <div class="my_setting">
           
          <el-form ref="form" :model="form" label-width="80px">
  <el-form-item label="昵称">
    <el-input v-model="form.name" style="width:300px"></el-input>
  </el-form-item>
  <el-form-item label="密码">
    <el-input v-model="form.password" style="width:300px" show-password></el-input>
  </el-form-item>
  <el-form-item label="出生年月">
    <el-col :span="9">
      <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width:300px"></el-date-picker>
    </el-col>
  </el-form-item>

  <el-form-item label="性别">
    <el-radio-group v-model="form.sex">
      <el-radio label="男"></el-radio>
      <el-radio label="女"></el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="邮箱">
    <el-input v-model="form.email" style="width:300px"></el-input>
  </el-form-item>
  <el-form-item label="个人简介">
    <el-input type="textarea" style="width:300px" v-model="form.desc" maxlength="60" show-word-limit :autosize="{ minRows: 5, maxRows: 5}"></el-input>
  </el-form-item>
  <!-- 头像 -->
  
</el-form>

       </div>
      

      </el-tab-pane>
</el-tabs>
</div>
</template>

<script>

import{QueryCart,DeleteCart} from "@/api/cart";
import{QueryOrderByUid,SimPaySuccess,SimPayFail} from "@/api/order";
import { parseTime } from "@/plugins/DateTran";//日期格式转化
export default {
     data(){
       return{
         //暂存信息
         item:{},
         index:99,
        //  Tab页的切换显示
        activeTab:this.$store.state.activeTab,
        //  选中框存放序列
         selectItem:[],
         //购物车删除弹框
         confirmDelete:false,
         //确认结算弹框开关
         confirmPay:false,
         confirmPay2:false,
         checkAll: false,
         isIndeterminate: true,
         //购物车
        cart_list:[],
        //订单
        ticket_state:[
          {img:require("../assets/filmImg/error.gif"),film_name:"狮子王",seat:30,start_time:"2022/3/27 12:00",end_time:"2022/3/27 12:00",state:0,price:30},
          {img:require("../assets/filmImg/error.gif"),film_name:"狮子王",seat:30,start_time:"2022/3/27 12:00",end_time:"2022/3/27 12:00",state:1,price:10},
          {img:require("../assets/filmImg/error.gif"),film_name:"狮子王",seat:30,start_time:"2022/3/27 12:00",end_time:"2022/3/27 12:00",state:2,price:50},
          {img:require("../assets/filmImg/error.gif"),film_name:"狮子王",seat:30,start_time:"2022/3/27 12:00",end_time:"2022/3/27 12:00",state:3,price:50}
        ],
        //支付状态
        state0:[{color:"orange"},{state_desc:"等待支付"}],
        state1:[{color:"red"},{state_desc:"支付失败"}],
        state2:[{color:"green"},{state_desc:"支付成功"}],
        state3:[{color:"blue"},{state_desc:"已被撤销"}],
        // 个人设置表单
        form: {
          name: '',
          password: '',
          date: '',
          sex: '男',
          desc: '',
          email:"",
        },

         //头像上传
         imageUrl: '', 

       }
     },
     computed:{
       totalprice:function(){
         var total_price=0;
         for(var i=0;i<this.selectItem.length;i++)
         {total_price=total_price+this.cart_list[this.selectItem[i]].cart.price}
         return total_price;
       }
     },
     methods:{
       //删除弹框方法
       ClickDelete(item,index)
       {
         this.item=item;
         this.index=index;
         this.confirmDelete=true;
       },
       onSubmit() {
        console.log('submit!')
     },
     //模拟支付成功
     SuccessPay(){
       for(var i=0;i<this.selectItem.length;i++)
       {
         const vo=this.cart_list[i];
        SimPaySuccess(vo).then((res)=>{
          if(res.code==200)
          {
            if(res.data.success=="success"){
                    this.$message({message:res.data.msg,type:"success"},)
                   }
                    else{
                 this.$message({message:res.data.msg,type:"warning"},)
                     }
          }
          else{
            this.$message.error("支付失败")
          }
          window.location.reload();
        })
        console.log(vo);
       }
       this.confirmPay2 = false;
     },
     //模拟支付失败
     FailPay(){
      for(var i=0;i<this.selectItem.length;i++)
       {
         const vo=this.cart_list[i];
        SimPayFail(vo).then((res)=>{
          if(res.code==200)
          {
            if(res.data.success=="success"){
                    this.$message({message:res.data.msg,type:"success"},)
                   }
                    else{
                 this.$message({message:res.data.msg,type:"warning"},)
                     }
          }
          else{
            this.$message.error("支付失败")
          }
          window.location.reload();
        })
        console.log(vo);
       }
       this.confirmPay2 = false;
     },
     //点击确认支付按钮
     cilckconfirmPay(){
       if(this.totalprice!=0)
       {
       this.confirmPay =true;
       }
       else{
         this.$message({message:"请先选中一个订单",type:"warning"});
       }

     },
     //删除按钮
     HandleDeleteCartItem(item,index){
        DeleteCart(item.cart).then((res)=>{
          if(res.code==200){
            this.cart_list.splice(index,1);
          }
          else{
            this.$message.error("删除失败");
          }
        })
        window.location.reload();
     },
     //确认结算弹框
     handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      //改变支付状态样式和判断支付样式的方法
      changeStateCSS(item){
        if(item.order.status==0){return this.state0[0]}
        if(item.order.status==1){return this.state1[0]}
        if(item.order.status==2){return this.state2[0]}
        if(item.order.status==3){return this.state3[0]}
      },
      changeState(item){
        if(item.order.status==0){return this.state0[1].state_desc}
        if(item.order.status==1){return this.state1[1].state_desc}
        if(item.order.status==2){return this.state2[1].state_desc}
        if(item.order.status==3){return this.state3[1].state_desc}
      },
      // 按下全选按钮的方法
      handleCheckAllChange(val) {
        if(this.checkAll==true)
        {this.selectItem=undefined;this.selectItem=new Array();
         for(var i=0;i<this.cart_list.length;i++){this.selectItem.push(i)}
        }
        if(this.checkAll==false)
        {this.selectItem=undefined;this.selectItem=new Array();}
        this.isIndeterminate = false;
      },
       // 按下选中按钮的方法
      handleCheckedCitiesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.selectItem.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.selectItem.length;
        if(this.selectItem.length==this.cart_list.length){this.checkAll=true}
      },
      //购物车接口,输入Cart_List
    Add_Cart_List(){
      QueryCart(   parseInt( localStorage.getItem("uid") )  ).then((res)=>{
        if(res.code==200)
        {
          console.log("购物车接口数据");
         console.log(res);
         this.cart_list=res.data;
         for(var i=0;i<this.cart_list.length;i++)
         {
         
           this.cart_list[i].arr.date=parseTime(new Date(this.cart_list[i].arr.date),"{y}-{m}-{d}");
        
         }
        }
      })
  },
      //订单接口，输入 ticket_state
     AddtoOrderList(){
       QueryOrderByUid(parseInt( localStorage.getItem("uid") )).then((res)=>{
         if(res.code==200)
         {
           console.log("用户订单接口数据");
           this.ticket_state=res.data;
          
           for(var i=0;i<this.ticket_state.length;i++)
         {
         
           this.ticket_state[i].arr.date=parseTime(new Date(this.ticket_state[i].arr.date),"{y}-{m}-{d}");
        
         }
         }
       })
     },
  
     },
     
     beforeCreate:function(){
			console.log("BeforeCreate");
            this.$store.state.navnum=0;
            this.$store.state.color3.color="#5b5d63";
            this.$store.state.color1.color="#5b5d63";this.$store.state.color2.color="#5b5d63";this.$store.state.color4.color="#5b5d63";
		},
     created(){
    console.log("查询购物车方法QueryCart,获取其中的购物车信息,添加到cart_list,并且清空Cart_list");
      this.cart_list=undefined;
      this.cart_list=new Array();
      this.ticket_state=undefined;
      this.ticket_state=new Array();
      this.Add_Cart_List();
      this.AddtoOrderList();
  },
     
     
     }

</script>

<style scoped>
.all{width:1400px;overflow:auto;margin:20px auto}
.tabtag{display:block;width:200px;text-align:center;font-size:20px}
/* 购物车页 */
.cart_box{width:1100px;height:180px;margin-top:30px;overflow:hidden;border: 1px solid rgb(187, 187, 187);padding-left: 5px;}
.cart_box1 img{width:140px;height:160px}
.cart_box1{display:inline-block;width:140px;height:160px;margin:10px 40px;overflow:hidden}
.cart_box2{display:inline-block;width:440px;height:160px;margin:10px 10px;overflow:hidden}
.cart_box3{display:inline-block;width:140px;height:160px;line-height:160px;margin:10px 30px;overflow:hidden}
.cart_box4{display:inline-block;width:140px;height:60px;line-height:60px;margin:60px 0px;overflow:hidden}
.cart_footer{width:1100px;height:60px;margin-top:30px;overflow:hidden}
.cart_footer_1{display:inline-block;height:60px;line-height:60px}
.cart_footer_2{display:inline-block;float:right;height:60px;line-height:60px;margin-right:30px}
.cart_footer_3{display:inline-block;height:60px;line-height:60px;float:right;width:180px}
/* 我的订单 */
.my_ticket{
  width:1100px;height:180px;margin-top:30px;overflow:hidden;border: 1px solid rgb(187, 187, 187);
}
.ticket_state_css{width:130px;text-align:center;display:inline-block}
/* 个人设置 */
.my_setting{
  width:1100px;height:700px;margin-top:30px;overflow:hidden;
}
/* 头像设置 */
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
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .all .el-tabs__item{height:200px !important}
</style>

