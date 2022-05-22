<template>
  <div class="outbox">
    <!-- 头部显示栏 -->
      <div class="header">
        <el-row>
          <el-col :span="5" class="header-item" >订单ID</el-col>
          <el-col :span="4" class="header-item" style="padding-left:0px" >上报人</el-col>
          <el-col :span="5" class="header-item" style="padding-left:70px">异常处理状态</el-col>
          <el-col :span="10" class="header-item" style="padding-left:170px">操作</el-col>
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
  <el-collapse-item  :name="index" disabled :key="index" v-for="(item,index) in Oe_list" style="border-bottom:1px solid black;">
    <template slot="title">
    <el-row style="background-color:white;width: 1800px;height: 48px;">
         <el-col :span="5" class="order-box-item1">{{item.id}}</el-col>
         <el-col :span="6" class="order-box-item1">{{item.reviewer}}</el-col>
         <el-col :span="5" class="order-box-item2"><div :class="changeOrderCss(item)">{{item.status}}</div></el-col>
         <el-col :span="8" class="order-box-item3">
           <el-button class="wait1" @click="Operation_SolveOrder(index)">处理异常</el-button>
           <el-button class="PadButton" @click="changePad(index)">展开收起</el-button>
         </el-col>
        </el-row>
        <!-- 折叠的面板内容,这里用来显示详细信息 -->
    </template>
    <div class="collapse-panel-item">ID:{{item.id}}</div>
    <div class="collapse-panel-item">订单ID:{{item.oid}}</div>
    <div class="collapse-panel-item">异常原因:{{item.reason}}</div>
    <div class="collapse-panel-item">上报人:{{item.reviewer}}</div>
    <div class="collapse-panel-item">是否已处理完成:{{item.status}}</div>
    <div class="collapse-panel-item">处理结果:{{item.result}}</div>
    <div class="collapse-panel-item">创建时间:{{item.createTime}}</div>
    
  </el-collapse-item>
</el-collapse>
<!-- 异常处理通知面板 -->
<el-dialog title="异常上报" :visible.sync="dialogSolveVisible">
  <el-form :model="form">
    <el-form-item label="处理结果" :label-width="formLabelWidth">
      <el-input v-model="form.result" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="处理状态" :label-width="formLabelWidth">
       <el-select v-model="form.statue" placeholder="请选择处理状态">
        <el-option label="处理成功" value="处理成功"></el-option>
        <el-option label="等待处理" value="等待处理"></el-option>
      </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogSolveVisible = false">取 消</el-button>
    <el-button type="primary" @click="Operation_SolveOrder_confirm(form_index)">确 定</el-button>
  </div>
</el-dialog>

<!-- 底部 -->
  </div>
</template>

<script>
import { QueryAdminAccount,QueryAdminInfo } from "@/api/admin";
import { QueryAllorder ,CancleOneOrder,PostOeOrder,GetAlloe} from "@/api/order";//引入订单接口
import { parseTime } from "@/plugins/DateTran";//日期格式转化
import { Admin_Login_JS } from "@/plugins/AdminLogin";
export default {
  data(){
    return{
      // 异常处理通知
       form_index:-1,
       formLabelWidth: '90px',
       dialogSolveVisible: false,
        form: {
          name: '',
          reason:'',
          statue:'',
          result:'',
        },
        //测试用展开后电影数据
        //  order_detail:[
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
          
        // ],
         // 测试用
         activeNames: [],
         //正式用
         Oe_list:{},
         //订单状态样式
           cancled:{cancle:true,pay:false,wait:false,fail:false},
           paid:{cancle:false,pay:true,wait:false,fail:false},
           waited:{cancle:false,pay:false,wait:true,fail:false},
           failed:{cancle:false,pay:false,wait:false,fail:true},
    }
  },
  methods:{
    //加载所有异常订单
    LoadAllOe(){
         GetAlloe().then((res)=>{
           if(res.code==200)
           {
             console.log(res);
                  this.Oe_list=res.data;
             for(var i=0;i<this.Oe_list.length;i++)
          {
            //传入的时候改变其状态为文字表示
          if(this.Oe_list[i].status==0){this.Oe_list[i].status="等待处理"}
          if(this.Oe_list[i].status==1){this.Oe_list[i].status="处理成功"}
            this.Oe_list[i].createTime=parseTime(new Date( this.Oe_list[i].createTime));

            }
           }
         })
    },
    //更改状态显示样式
    changeOrderCss:function(item){
          if(item.status=="已被撤销"){return this.cancled}//黄色
          if(item.status=="处理成功"){return this.paid}   //绿色
          if(item.status=="处理异常"){return this.waited} //蓝色
          if(item.status=="等待处理"){return this.failed} //红色
      },
    //处理按钮和通知
    Operation_SolveOrder:function(index){
      this.dialogSolveVisible=true;
      this.form_index=index;
    },
    Operation_SolveOrder_confirm:function(index){
        if(this.form.statue=="处理成功")
      {this.testlist[index].statue="处理成功";
      this.testlist[index].solve_statue=true;}
        else{
            this.testlist[index].statue="等待处理";
        }
        this.testlist[index].solve_result=this.form.result;
        this.dialogSolveVisible = false;
    },
    changePad:function(n){
      if(this.activeNames.indexOf(n)==-1){
        this.activeNames.push(n);
      }
       else{
         this.activeNames.pop(n);
       }
    }
  },
  created:function(){
    this.LoadAllOe()
  },
}
</script>

<style scoped>
.outbox{
  overflow: auto;
  background-color: rgb(143, 143, 143);
  height:760px;
}
/* 头部栏 */
.header-item{
  background-color:rgb(177, 177, 177);text-align:left;height: 50px;line-height: 50px;font-size: 20px;
  font-weight: 600 !important;
}
.order-box{
  border-bottom-color:rgb(187, 187, 187);height: 50px;text-align: center;font-size: 16px;line-height: 50px;
  padding-bottom:5px;
  border-bottom:1px solid black;
}
/* 按钮样式 */
.pay{
    margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(150 214 164);
    color: rgb(150 214 164);
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    border-radius: 5px;
    background-color: rgb(231 254 180);
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
    background-color: rgb(252, 188, 69);
    border-radius: 5px;
}
.wait{
    margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(95 164 251);
    color: rgb(95 164 251);
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    border-radius: 5px;
    background-color: rgb(240 246 253);
}
.fail{margin-top:10px;
    height:30px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(251 95 114);
    color: rgb(251 95 114);
    line-height: 30px;
    width:80px;
    margin: 0,auto;
    border-radius: 5px;
    background-color: rgb(255 215 220);}
    /* 操作区域按钮样式，与前面的分割 */
  .wait1{

    margin-top:7px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(95 164 251);
    color: rgb(95 164 251);
    border-radius: 5px;
    background-color: rgb(240 246 253);
    height:35px;
    margin-left: 60px;
    
}
.fail1{
    height:35px;
    text-align: center;
    display:inline-block;
    border: 1px solid rgb(251 95 114);
    color: rgb(251 95 114);
    border-radius: 5px;
    background-color: rgb(255 215 220);}
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
    margin-left:90px !important;
    }
    /* 折叠板展开后的 */
    .collapse-panel-item{
        text-indent:2em;font-size:14px
    }
    /* 异常显示栏 */
    .order-box-item2{
      right:50px;
    }
</style>