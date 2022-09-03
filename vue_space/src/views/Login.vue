<template>
  <div>
    <div class="wrapper">
      <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
        <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
        <el-form :model="loginEmp" :rules="rules" ref="empForm">

          <el-form-item prop="email">
            <el-input placeholder="请输入邮箱" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="loginEmp.email" type="email"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="loginEmp.password"></el-input>
          </el-form-item>

          <el-form-item style="margin: 10px 0; text-align: right">
            <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
            <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/register')">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>


  </div>

</template>

<script>
import JSEncrypt from "jsencrypt";
import store from "@/store";
import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      loginEmp: {
        email: "",
        password: "",
        md5PW: "",
      },
      loginEnc: {
        email: "",
        encryptPW: ""
      },
      publicKey: "",
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { pattern: /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/,required: true, message: "请输入正确的邮箱地址", trigger: "blur" }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
     login() {
       this.$refs['empForm'].validate(async (valid) => {
         if (valid) {  // 表单校验合法

           //请求后端发送公钥
           //注意！！！这里必须加await来等待异步任务执行完成，也就是将当前任务设置为同步，不然axios默认异步执行！！！
           await request.post("/server/RSA/getPublicKey").then(res => {
             this.publicKey = res.publicKey
           })

           // 进行简单的加盐
           let temp = ""
           for (let i = 0; i < this.loginEmp.password.length; i++) {
             temp += (i + 7) * (this.loginEmp.password.charCodeAt(this.loginEmp.password.length - i - 1) + temp.length) % 103
             temp += String.fromCharCode((((i + 3) * this.loginEmp.password.charCodeAt(i) + temp.length) % this.loginEmp.password.length) % 24 + 97)
             temp += ((this.loginEmp.password.charCodeAt(i) + 16) * temp.length % 107)
             if (i * temp.length % 2 === 0) {
               temp += String.fromCharCode(((this.loginEmp.password.charCodeAt(this.loginEmp.password.length - i - 1) + temp.length) * (i * temp.length % 300 + 6)) % 24 + 65)
             }
             temp += String.fromCharCode( ((this.loginEmp.password.charCodeAt(i) + i) * temp.length) % 24 + 65)
           }

           console.log(temp)

           //对数据进行SHA256加密
           let sha256PW = this.$SHA256(temp)

           //对已进行SHA256加密的密码进行不对称加密
           let encrypt = new JSEncrypt();
           encrypt.setPublicKey(this.publicKey)
           this.loginEnc.encryptPW = encrypt.encrypt(sha256PW)
           this.loginEnc.email = this.loginEmp.email

           request.post("/server/login/login", this.loginEnc).then(res => {
             console.log(res)
             if (res.state === "Success") {
               localStorage.setItem("token", res.token)
               localStorage.setItem("authority", res.authority)
               this.$router.push("/")
               this.$message.success("登陆成功!")
             } else {
               this.$message.error(res.message)
             }
           })
         } else {
           return false;
         }
       });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: url('https://source.unsplash.com/1920x1080/?HD');
  overflow: hidden;
}
</style>
