//定义一个验证管理员登陆方法，如果说没有进行管理员登录，自动回到首页
export function Admin_Login_JS() {
      if(localStorage.getItem("admin")!="true"){
        window.location.href="#/";
        localStorage.removeItem('admin_id');
      }
      else{
          console.log("验证登录成功")
      }
}