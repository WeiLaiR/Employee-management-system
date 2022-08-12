<template>
  <div>
    <div class="wrapper">
      <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 370px; padding: 20px; border-radius: 10px">
        <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
        <el-form :model="loginEmp" :rules="rules" ref="empForm">


          <el-form-item prop="email">
            <el-input placeholder="请输入邮箱" size="medium" style="margin: 6px 0" prefix-icon="el-icon-user" v-model="loginEmp.email" type="email"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input placeholder="请输入密码" size="medium" style="margin: 6px 0" prefix-icon="el-icon-lock" show-password v-model="loginEmp.password"></el-input>
          </el-form-item>

          <el-form-item prop="confirmPassword">
            <el-input placeholder="请确认密码" size="medium" style="margin: 6px 0" prefix-icon="el-icon-lock" show-password v-model="loginEmp.confirmPassword"></el-input>
          </el-form-item>

          <el-form-item style="margin: 10px 0; text-align: right">
            <el-button type="primary" size="small"  autocomplete="off" @click="Register">注册用户</el-button>
            <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>


  </div>

</template>

<script>
import JSEncrypt from "jsencrypt";
import request from "@/utils/request";
import SHA256 from "js-sha256";

export default {
  name: "Register",
  data() {
    return {
      loginEmp: {
        email: "",
        password: "",
        confirmPassword: "",
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
          { min: 8, max: 30, message: '长度在 8 到 30 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 8, max: 20, message: '长度在 8 到 30 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    Register() {
      this.$refs['empForm'].validate(async (valid) => {
        if (valid) {  // 表单校验合法

          //请求后端发送公钥
          //注意！！！这里必须加await来等待异步任务执行完成，也就是将当前任务设置为同步，不然axios默认异步执行！！！
          await request.post("/RSA/getPublicKey").then(res => {
            this.publicKey = res.publicKey
          })

          if (this.loginEmp.confirmPassword !== this.loginEmp.password) {
            this.$message.error("两次输入的密码不一致")
            return false
          }

          // 进行简单的加盐
          let temp = ""
          for (let i = 0; i < this.loginEmp.password.length; i++) {
            temp += i * i + temp.length + this.loginEmp.password.length ^ 66
            temp += String.fromCharCode((i * temp.length * 3 + 6) % 24 + 97)
            temp += (this.loginEmp.password.charAt(i) + 16) ^ (this.loginEmp.password.length * temp.length % 150 + 8)
            if (i * temp.length % 2 === 0) {
              temp += (this.loginEmp.password.charAt(i) + 66) ^ (i * temp.length % 300 + 6) + 8
            }
            temp += String.fromCharCode( (temp.length - i) * temp.length % 24 + 65)
          }
          console.log(temp)

          //对数据进行SHA256加密
          let sha256PW = this.$SHA256(temp)
          console.log(sha256PW)

          //对已进行SHA256加密的密码进行不对称加密
          let encrypt = new JSEncrypt();
          encrypt.setPublicKey(this.publicKey)
          this.loginEnc.encryptPW = encrypt.encrypt(sha256PW)
          this.loginEnc.email = this.loginEmp.email
          console.log(this.loginEnc.encryptPW)

          request.post("/login/register", this.loginEnc).then(res => {
            console.log(res)
            if (res.state === "Success") {
              this.$message.success("注册成功，请登录！")
              this.$router.push("/login")
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
