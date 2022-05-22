<template>
    <div class="login">
        <div class="login_info">
            <div class="login_title">
                <!-- 插入图片 -->
                <img src="../assets/img/logo.png" />
                <div class="login_text">哇塞电影-客服登录</div>
            </div>

            <div class="login_form">
                <p>Username</p>
                <el-input v-model="userName" placeholder="客服账号"></el-input>
                <p>Password</p>
                <el-input v-model="passWord" placeholder="客服密码" show-password></el-input>

                <div class="forget">
                    <el-tooltip content="请联系管理员" placement="bottom" effect="dark">
                        <el-link class="login_link" :underline="false">忘记密码</el-link>
                    </el-tooltip>
                </div>

                <div class="btn">
                    <el-button class="login_button" type="primary" @click="getLogin()">SIGN IN</el-button>
                </div>
            </div>

            <div class="login_footer">
                <el-link :underline="false" class="rg" href="#/">返回首页<i class="el-icon-left"></i></el-link>
            </div>
        </div>
    </div>
</template>

<script >
import { Login } from "@/api/user";
import { workerLogin } from "@/api/worker";
import toast_test1 from "@/vueToast/toast_test1";
export default {
    data() {
        return {
            userName: "",
            passWord: "",
        };
    },
    methods: {
        getLogin() {
            const LoginData = {
                username: this.userName,
                password: this.passWord,
            };
            workerLogin(LoginData).then((res) => {
                console.log(res); //控制台输出传来的数据
                if (res.code == 200) {
                    if (res.data.success == "success") {
                        //自定义的通知插件
                        this.$toast.success(toast_test1, {
                            position: "top-right",
                            timeout: 2500,
                            closeOnClick: true,
                            pauseOnFocusLoss: true,
                            pauseOnHover: false,
                            draggable: true,
                            draggablePercent: 0.6,
                            showCloseButtonOnHover: false,
                            hideProgressBar: false,
                            closeButton: "button",
                            icon: true,
                            rtl: false,
                        });
                        localStorage.setItem("WID", res.data.Worker.id);
                        console.log("WID:"+localStorage.getItem("WID"));
                        this.$router.push("/comment_worker");
                    } else {
                        this.$message({ message: res.data.msg, type: "warning" });
                        localStorage.removeItem("WID");
                    }
                } else {
                    this.$message({ message: res.data.msg, type: "warning" });
                    localStorage.removeItem("WID");
                }
                console.log(res);
            });
            //    this.$axios
            //    .post(this.$store.state.api_url+"/api/user/login",LoginData)
            //    .then((res)=>
            //      {
            //          if(res.data.code==200){
            //              this.$message({message:"登录成功",type:"success"},);
            //              localStorage.setItem("token",res.data.data.token);
            //      localStorage.setItem("uid",res.data.data.user.id);
            //      this.$router.push("/");
            //          }
            //      });
        },
    },
};
</script>

<style scoped>
.login_title {
    height: 75px;
}

.login {
    width: 100%;
    height: 100%;
}

.login_info {
    width: 500px;
    margin: 50px auto;
}

.login_title img {
    width: 75px;
    height: 75px;
    float: left;
    margin-left: 30px;
}

.login_text {
    float: left;
    line-height: 75px;
    font-size: 30px;
    font-weight: bold;
    margin-left: 60px;
}

.login_form {
    padding: 50px;
    padding-top: 30px;
    margin-top: 100px;
    font-size: 16px;
    color: #91949c;
    font-weight: bold;
    border-color: rgba(151, 151, 151, 0.3);
    border-style: solid;
    border-width: 2px;
    border-radius: 50px;
}

.forget .login_link {
    font-size: 16px;
    color: #91949c;
    margin-top: 10px;
}

.login_button {
    margin-top: 20px;
    font-weight: bold;
    width: 100%;
    border-radius: 15px;
}

.login_footer {
    height: 50px;
    line-height: 50px;
    text-align: center;
    font-weight: bold;
}

.rg {
    font-weight: bold;
    font-size: 16px;
    margin-top: 35px;
}

.forget {
    float: right;
}

.btn {
    clear: both;
}
</style>