<template>
<el-container>
   <el-header>
  <Header></Header>
   </el-header>
   <br/>

   <el-main>
       <router-view />
   </el-main>

   <br/>
  <Footer></Footer>
</el-container>
</template>

<script>

import Header from "@/components/layout/Header.vue";
import Footer from "@/components/layout/Footer.vue";

export default {
       name:"Layout",
       components:{
           Footer,Header,
       }
}
</script>

<style>
.el-main{
    overflow: auto;
}
</style>