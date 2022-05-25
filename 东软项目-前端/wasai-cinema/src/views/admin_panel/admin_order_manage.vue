<template>
  <div class="outbox">
    <!-- 头部显示栏 -->
      <div class="header">
        <el-row>
          <el-col :span="6" class="header-item" >订单ID</el-col>
          <el-col :span="4" class="header-item" style="padding-left:15px" >订单金额</el-col>
          <el-col :span="3" class="header-item" style="padding-left:40px">订单状态</el-col>
          <el-col :span="11" class="header-item" style="padding-left:170px">操作</el-col>
        </el-row>
      </div>
    <!-- 循环显示订单信息栏位-测试用-->
      <!-- <div class="order-box" :key="index" v-for="(item,index) in testlist">
        <el-row>
          <el-col span="8" class="order-box-item1" >{{item.id}}</el-col>
          <el-col span="4" class="order-box-item1" >{{item.money}}</el-col>
          <el-col span="6" class="order-box-item2" ><div :class="changeOrderCss(item)">{{item.statue}}</div></el-col>
          <el-col span="6" class="order-box-item3" ><el-button class="wait1" @click="Operation_CancleOrder(index)">撤销订单</el-button> &nbsp; <el-button class="fail1" @click="Operation_FailOrder(index)">上报异常</el-button></el-col>
        </el-row>
      </div> -->
  <!-- 折叠面板 -->
  <el-collapse v-model="activeNames">
  <el-collapse-item  :name="index" disabled :key="index" v-for="(item,index) in order_list" style="border-bottom:1px solid black;">
    <template slot="title">
    <el-row style="background-color:white;width: 1800px;height: 48px;">
         <el-col :span="7" class="order-box-item1">{{item.order.id}}</el-col>
         <el-col :span="4" class="order-box-item1">{{item.order.price}}</el-col>
         <el-col :span="4" class="order-box-item2"><div :class="changeOrderCss(item)">{{item.order.status}}</div></el-col>
         <el-col :span="6" class="order-box-item3">
           <el-button class="wait1" @click="Operation_CancleOrder(index)">撤销订单</el-button>
           <el-button class="fail1" @click="Operation_FailOrder(index)">上报异常</el-button>
           <el-button class="PadButton" @click="changePad(index)">展开收起</el-button>
         </el-col>
        </el-row>
        <!-- 折叠的面板内容,这里用来显示详细信息 -->
    </template>
    <div class="collapse-panel-item">订单ID:{{item.order.id}}</div>
    <div class="collapse-panel-item">用户ID:{{item.order.uid}}</div>
    <div class="collapse-panel-item">电影ID:{{item.arr.fid}}</div>
    <div class="collapse-panel-item">场次ID:{{item.order.aid}}</div>
    <div class="collapse-panel-item">电影名:{{item.arr.name}}</div>
    <div class="collapse-panel-item">座位号:{{item.order.seats}}</div>
    <div class="collapse-panel-item">订单金额:{{item.order.price}}</div>
    <div class="collapse-panel-item">下单时间:{{item.order.createTime}}</div>
    <div class="collapse-panel-item">支付时间:{{item.order.payAt}}</div>
  </el-collapse-item>
</el-collapse>
<!-- 异常上报通知面板 -->
<el-dialog title="异常上报" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="上报人" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="上报原因" :label-width="formLabelWidth">
       <el-input v-model="form.reason" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="Operation_FailOrder_confirm(form_index)">确 定</el-button>
  </div>
</el-dialog>
<!-- 撤销确认通知面板 -->
<el-dialog title="撤销确认" :visible.sync="dialogCancleVisible">
  确认撤销此订单吗
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogCancleVisible = false">取 消</el-button>
    <el-button type="primary" @click="Operation_CancleOrder_confirm(form_index)">确 定</el-button>
  </div>
</el-dialog>
<!-- 底部 -->
  </div>
</template>

<script>
import { QueryAdminAccount,QueryAdminInfo } from "@/api/admin";
import { QueryAllorder ,CancleOneOrder,PostOeOrder} from "@/api/order";//引入订单接口
import { parseTime } from "@/plugins/DateTran";//日期格式转化
export default {
  data(){
    return{
      //撤销通知
      dialogCancleVisible: false,
      // 异常上报通知
       form_index:-1,
       formLabelWidth: '90px',
       dialogFormVisible: false,
        form: {
          name: '',
          reason:'',
        },
        //测试用展开后电影数据
        // order_detail:[
        //   {
        //     order_id:"123",
        //     user_id:"12321321",
        //     movie_id:"ji33232",
        //     arrn_id:"ucbbr1",
        //     movie_name:"刺杀小说家",
        //     seat_no:"14",
        //     order_cost:232,
        //     order_time:"12:00",
        //     pay_time:"13:00",
        //   },
        //   {
        //     order_id:"123",
        //     user_id:"12321321",
        //     movie_id:"ji33232",
        //     arrn_id:"ucbbr1",
        //     movie_name:"狮子王",
        //     seat_no:"14",
        //     order_cost:232,
        //     order_time:"12:00",
        //     pay_time:"13:00",
        //   },
        //   {
        //     order_id:"123",
        //     user_id:"12321321",
        //     movie_id:"ji33232",
        //     arrn_id:"ucbbr1",
        //     movie_name:"天气之子",
        //     seat_no:"14",
        //     order_cost:232,
        //     order_time:"12:00",
        //     pay_time:"13:00",
        //   },
        // ],
      // 测试用
        activeNames:[],
        //测试用列表
        testlist:[
          {id:"1343-2341-2323-9212",
           money:40,
           statue:"已被撤销",
           order_id:"123",
            user_id:"12321321",
            movie_id:"ji33232",
            arrn_id:"ucbbr1",
            movie_name:"刺杀小说家",
            seat_no:"14",
            order_cost:232,
            order_time:"12:00",
            pay_time:"13:00",
           },
          {id:"2332-2321-2122-8743",
           money:60,
           statue:"支付成功",
           order_id:"123",
            user_id:"12321321",
            movie_id:"ji33232",
            arrn_id:"ucbbr1",
            movie_name:"狮子王",
            seat_no:"14",
            order_cost:232,
            order_time:"12:00",
            pay_time:"13:00",
           },
           {id:"7632-2181-9522-8000",
           money:70,
           statue:"等待支付",
           order_id:"123",
            user_id:"12321321",
            movie_id:"ji33232",
            arrn_id:"ucbbr1",
            movie_name:"天气之子",
            seat_no:"14",
            order_cost:232,
            order_time:"12:00",
            pay_time:"13:00",
           },
        ],
         //*********正式订单列表(原始数据，不会放入显示)************
        order_list_origin:{},
        //*********正式订单列表************
        order_list:{},
        //订单状态样式
           cancled:{cancle:true,pay:false,wait:false,fail:false},
           paid:{cancle:false,pay:true,wait:false,fail:false},
           waited:{cancle:false,pay:false,wait:true,fail:false},
           failed:{cancle:false,pay:false,wait:false,fail:true},
    }
  },
  methods:{
    //上报一个异常订单接口
    PostOneOeOrder(index){
       QueryAllorder().then((res)=>{
         if(res.code==200)
         {
           const vo={
             oid:res.data[index].order.id,
             reason:this.form.reason,
             reviewer:this.form.name,
           };
           PostOeOrder(vo).then((res2)=>{
             if(res2.code==200)
             {
               this.dialogFormVisible = false;
               this.$message({message:"上报异常成功",type:"success"});
             }
         })
         }
       })
        
    },
     //撤销一个订单接口
    CancleOnesOrder(index){
       QueryAllorder().then((res)=>{
         if(res.code==200)
         {
           CancleOneOrder(res.data[index]).then((res2)=>{
             if(res2.code==200)
             {
               this.order_list[index].order.status="已被撤销";
               this.dialogCancleVisible = false;
               this.$message({message:"撤销成功",type:"success"});
             }
         })
         }
       })
        
    },
    //查询所有的订单
    QueryAllOrderAdmin(){
      QueryAllorder().then((res)=>{
        if(res.code==200)
        {
          console.log(res.data);
          this.order_list=res.data;//添加进显示表
          for(var i=0;i<this.order_list.length;i++)
          {
            //传入的时候改变其状态为文字表示
          if(this.order_list[i].order.status==3){this.order_list[i].order.status="已被撤销"}
          if(this.order_list[i].order.status==2){this.order_list[i].order.status="支付成功"}
          if(this.order_list[i].order.status==0){this.order_list[i].order.status="等待支付"}
          if(this.order_list[i].order.status==1){this.order_list[i].order.status="支付失败"}
            this.order_list[i].order.createTime=parseTime(new Date( this.order_list[i].order.createTime));
            this.order_list[i].order.payAt=parseTime(new Date( this.order_list[i].order.payAt));
            }
        }
       }
      )
    },
    //更改状态显示样式
    changeOrderCss:function(item){
          if(item.order.status=="已被撤销"){return this.cancled}
          if(item.order.status=="支付成功"){return this.paid}
          if(item.order.status=="等待支付"){return this.waited}
          if(item.order.status=="支付失败"){return this.failed}
      },
      //撤销按钮和确认
    Operation_CancleOrder:function(index){
      this.dialogCancleVisible=true;
      this.form_index=index;
    },
    Operation_CancleOrder_confirm:function(index){
      this.CancleOnesOrder(index);
      // this.testlist[index].status="已被撤销";
      // this.dialogCancleVisible = false;
    },
    //上报按钮和通知
    Operation_FailOrder:function(index){
      this.dialogFormVisible=true;
      this.form_index=index;
    },
    Operation_FailOrder_confirm:function(index){
      this.PostOneOeOrder(index);
      //this.testlist[index].statue="异常订单";
      //this.dialogFormVisible = false;
    },
    //样式改变，不要动
    changePad:function(n){
      if(this.activeNames.indexOf(n)==-1){
        this.activeNames.push(n);
      }
       else{
         this.activeNames.splice(this.activeNames.indexOf(n),1);
       }
    }
  },
  created:function(){
    this.QueryAllOrderAdmin();
  }
}
</script>

<style scoped>
.outbox{
  overflow: auto;
  background-color: rgb(187, 189, 189);
  height:760px;
}
/* 隐藏滚动条 */
.outbox::-webkit-scrollbar{
				display: none;
			}
/* 头部栏 */
.header-item{
  background-color:rgb(179, 179, 179);text-align:left;height: 50px;line-height: 50px;font-size: 20px;
  font-weight: 600 !important;
}
.order-box{
  border-bottom-color:rgb(233, 233, 233);height: 50px;text-align: center;font-size: 16px;line-height: 50px;
  padding-bottom:5px;
  border-bottom:1px solid black;
}
/* 按钮样式 */
.pay{
    margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid green;
    color: green;
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    border-radius: 5px;
    background-color: rgb(182, 238, 199);
}
.cancle{
    margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(189, 124, 2);
    color: rgb(196, 130, 7);
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    background-color: rgb(240, 206, 144);
    border-radius: 5px;
}
.wait{
    margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid blue;
    color: blue;
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    border-radius: 5px;
    background-color: rgb(172, 214, 248);
}
.fail{margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(182, 0, 0);
    color: rgb(218, 15, 0);
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    border-radius: 5px;
    background-color: rgb(241, 195, 195);}
  .wait1{
    margin-top:7px;
    text-align: center;
    display:inline-block;
    border: 1px solid blue;
    color: blue;
    border-radius: 5px;
    background-color: rgb(114, 187, 247);
    height:35px;
    
    
}
.fail1{
    height:35px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(182, 0, 0);
    color: rgb(218, 15, 0);
    border-radius: 5px;
    background-color: rgb(245, 122, 122);}
    /* 折叠面板 */
    .order-box-item1{
      color:black;
      font-size:18px;
    }
    .PadButton{
    height:35px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(68, 68, 68);
    color: rgb(0, 0, 0);
    border-radius: 5px;
    background-color: rgb(238, 238, 238);
    margin-left:60px !important;
    }
    /* 折叠板展开后的 */
    .collapse-panel-item{
        text-indent:2em;font-size:14px
    }
    /* 定义订单状态和操作栏的 */
    .order-box-item3{
      width:390px
    }
</style>