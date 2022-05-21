<template>
  <div class="register">



    <div class="register_info">
          <div class="register_title">
         <!-- 插入图片 -->
         <img src="../assets/img/logo.png" />
          <div class="register_text">哇塞电影-注册</div>
      </div>



    

    <div class="register_form">
     <p>Username</p>
     <el-input v-model="reusername" placeholder="账号" @blur="isExistUserName"></el-input>
     <p>Password</p>
     <el-input v-model="repassword" placeholder="密码" show-password></el-input>
     <p>Check Password</p>
     <el-input v-model="repassword_recheck" placeholder="确认密码" show-password></el-input>
     <p>邮箱</p>
     <el-input v-model="reemail" placeholder="请输入邮箱" @blur="checkEmail"
     :style="isPassEmail ?'':'border:1px solid red;'"></el-input>
     <el-button type="info" icon="el-icon-message" @click="sendEmail" style="margin:4px 130px;position: relative;">发送验证码</el-button>
     <p>邮箱验证码</p>
        <el-input
          v-model="emailCode"
          placeholder="请输入验证码"
        ></el-input>
    <div class="agreement">
      <el-checkbox v-model="ifcheck" @change="checkbox_change"><span class="check">我已同意"哇塞电影"用户协议</span></el-checkbox>
     </div>

     
       <div class="btn"><el-button class="register_button" type="primary" @click="register()">Register</el-button></div>
    </div>

     

    <div class="register_footer">
     <el-link :underline="false" class="rg" href="#/login">我有账号，去登陆<i class="el-icon-right"></i></el-link>
     <br />
     <el-link :underline="false" class="rg" href="#/">返回首页<i class="el-icon-left"></i></el-link>

     </div>
    </div>
  </div>
  
</template>

<script>
import{Register,IsExistUserName,SendEmailCode} from "@/api/user";
export default {
   data(){
       return{
            reusername:"",
            repassword:"",
            repassword_recheck:"",
            reemail:'',
            emailCode:'',
            ifcheck:false,
            isPassEmail:true,
       }
   },
   methods:{
       checkbox_change(e){
           
       },
       //判断用户名是否存在
		isExistUserName(){
			const vo = {
				email:this.reusername,
			}
			console.log(vo);
			IsExistUserName(vo).then((res)=>{
				console.log(res);
				if(res.code==200){
					if(res.data.success==="success"){
						this.$message({
							message:res.data.msg,
							type:"success"
						});
					}else{
						this.$message.error(res.data.msg);
					}
				}
			});
			
		}		,
       //邮件输入框的失去焦点时间
		checkEmail(){
			const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ;
			if(mailReg.test(this.reemail)){
				this.isPassEmail=true;
			}else{
				this.isPassEmail=false;
			}
			console.log(this.isPassEmail);
		},
		
		//发送email验证码
		sendEmail(){
			//客户端对传入的邮箱内容，进行基本的邮箱格式验证。验证成功后发消息到服务端。
			//https://www.jb51.net/article/67628.htm
		
			if(this.isPassEmail){
				const emailVo={
					email:this.reemail,
				};
				console.log(emailVo);
				
				//如何发送
				SendEmailCode(emailVo).then((res)=>{
					console.log(res.data);
					if(res.code==200){
						
                        console.log("注册邮箱验证码为"+res.data.emailcode);
                        this.$message({
							message:"验证码为"+res.data.emailcode,
							type:"success"
						})
						//保存验证码
						localStorage.setItem("code",res.data.code);
					}
				});
			}else{
				this.$message({
					message:'邮箱格式不正确',
					type:"warning"
				})
				
			}
		},
       register(){
           if(this.reusername.length<6){
               this.$message({
                   message:"请输入不少于六位的用户名",
                   type:"warning"
               });
               return;
           }
           else if(this.ifcheck==false){
               this.$message({
                   message:"请勾选注册协议",
                   type:"warning"
               });
               return;
           }
           else if(this.repassword!=this.repassword_recheck){
               this.$message({
                   message:"请输入两次相同的密码",
                   type:"warning"
               });
               return;
           }
           else{
              const register_info={
                  username:this.reusername,
                  password:this.repassword,
                  email:this.reemail,
                  token:localStorage.getItem("code"),
                  emailCode:this.emailCode,
              }
              Register(register_info).then((res)=>
              {
                  if(res.code==200)
                  {
                      this.$message({message:res.data.msg,type:"success"});
                      this.$router.push("/login");

                  }
                  else{
                      this.$message({message:"登陆失败",type:"warning"});
                  }
              })
           }
       }
   }
}
</script>

<style scoped>
.agreement{
    margin-top: 20px;
    
}

.check{
    color:#91949c;
    font-size:18px;
}
.register_title{
    
    height: 75px;
}
.register{
    width: 100%;
    height: 100%;
}
.register_info{
    width:500px;
    margin:50px auto;
}
.register_title img{
    width:75px;
    height:75px;
    float:left;
    margin-left: 60px;
}
.register_text{
    float: left;
    line-height: 75px;
    font-size:30px;
    font-weight: bold;
    margin-left:60px ;
}
.register_form{
    padding: 50px;
    padding-top: 30px;
    margin-top: 100px;
    font-size:16px;
    color:#91949c;
    font-weight: bold;
    border-color: rgba(151, 151, 151, 0.3);
    border-style: solid;
    border-width: 2px;
    border-radius: 50px;
}

.register_button{
    margin-top: 20px;
    font-weight: bold;
    width: 100%;
    border-radius: 15px;
}
.register_footer{
    height:50px;
    line-height:50px;
    text-align: center;
    font-weight: bold;
}
.rg{
    font-weight: bold;
    font-size: 16px;
    margin-top: 35px;
}

.btn{
    clear: both;
}
</style>