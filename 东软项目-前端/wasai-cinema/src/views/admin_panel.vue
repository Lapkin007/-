<template>
  <div>
      <el-container class="out">
          <!-- 侧边栏-管理面板 -->
          <el-aside class="side">
            <el-menu
      default-active="2"
      class="el-menu"
      @open="handleOpen"
      @close="handleClose"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="lightblue">
      <img style="width:80px;height:80px;display:inline-block;position:relative;top:30px;margin-left:30px" src="../assets/img/logo.png">
      <span style="width:100px;height:80px;display:inline-block;font-size:20px;color:white;margin-left:5px">后台管理</span>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location" style="color:white"></i>
          <span>影视管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="1-1">电影列表</el-menu-item>
          <el-menu-item index="1-2">院线排片</el-menu-item>
          <el-menu-item index="1-3">新增电影</el-menu-item>
          <el-menu-item index="1-4">轮播海报</el-menu-item>
        </el-menu-item-group>
        </el-submenu>
      <el-submenu index="2">
          <template slot="title">
        <i class="el-icon-menu" style="color:white"></i>
        <span slot="title">用户管理</span>
          </template>
          <el-menu-item index="2-1">用户列表</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
          <template slot="title">
        <i class="el-icon-menu" style="color:white"></i>
        <span slot="title" >订单管理</span>
          </template>
          <el-menu-item index="3-1" @click="HandleClickOrderList()">订单列表</el-menu-item>
          <el-menu-item index="3-2" @click="HandleClickOrderErrorReport()">异常订单</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
          <template slot="title">
        <i class="el-icon-menu" style="color:white"></i>
        <span slot="title">员工管理</span>
          </template>
          <el-menu-item index="4-1">员工列表</el-menu-item>
          <el-menu-item index="4-2" @click="HandleClickStaffAdd()">新增员工</el-menu-item>
      </el-submenu>
    </el-menu>
          </el-aside>

          <!-- 显示面板 中间包含头部栏 -->
          <el-main class="main_1">
              <!-- 头部栏 -->
              <el-header class="header">
                  <div style="width:240px;height:80px;background-color:blue;margin:0 auto">
                    <el-avatar style="width:80px;height:80px;display:inline-block"></el-avatar>
                    <div style="width:130px;height:80px;float:right;line-height:80px">Admin</div>
                  </div>
              </el-header>


              <!-- RouterView部栏 -->
              <el-main class="main_2">
                   <router-view />
              </el-main>

          </el-main>
      </el-container>
  </div>
</template>

<script>
export default {
    data(){
        return{
            
        }
    },
    
     methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },

HandleClickOrderList(){
  window.location.href="#/admin_panel/order_manage";
  //this.$router.push("./admin_panel/order_manage");//会出现连续点击跳转错误的问题
},
HandleClickOrderErrorReport(){
  window.location.href="#/admin_panel/order_error_report";
  //this.$router.push("./admin_panel/order_error_report");//会出现连续点击跳转错误的问题
},
HandleClickStaffAdd(){
  window.location.href="#/admin_panel/staff_add";
  //this.$router.push("./admin_panel/order_error_report");//会出现连续点击跳转错误的问题
},

    },
}
</script>

<style scoped>
.out{background-color: blueviolet;width:1500px;height:870px;padding: 0px;margin: 0 auto;overflow: hidden !important;}
.side{width:250px !important;min-height:800px;background-color:#545c64;overflow: hidden !important;}
.main_1{min-width:1200px;min-height:800px;background-color:red;padding: 0px !important;margin: 0px;overflow: hidden !important;}
.main_2{min-height:800px;max-height:800px;background-color:green;min-width:1100px;overflow: hidden !important;}
.header{background-color: beige;min-width:1100px;overflow: hidden;height:80px !important}
.el-menu{
    border: none !important;
}
</style>