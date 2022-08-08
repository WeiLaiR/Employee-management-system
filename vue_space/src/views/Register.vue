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
import store from "@/store";

export default {
  name: "Register",
  data() {
    return {
      loginEmp: {
        email: "",
        password: "",
        confirmPassword: "",
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
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    //请求后端发送公钥
    this.request.post("/RSA/getPublicKey").then(res => {
      this.publicKey = res.publicKey
    })
  },
  methods: {
    Register() {
      this.$refs['empForm'].validate((valid) => {
        if (valid) {  // 表单校验合法

          if (this.loginEmp.confirmPassword !== this.loginEmp.password){
            this.$message.error("两次输入的密码不一致")
            return false
          }

          //对数据进行MD5加密
          this.loginEmp.md5PW = this.$md5(this.loginEmp.password)

          console.log(this.publicKey)
          //对已进行MD5加密的密码进行不对称加密
          let encrypt = new JSEncrypt();
          encrypt.setPublicKey(this.publicKey)
          this.loginEnc.encryptPW = encrypt.encrypt(this.loginEmp.md5PW)
          this.loginEnc.encryptPW = encrypt.encrypt(this.loginEmp.md5PW)
          console.log(this.loginEnc.encryptPW)
          this.loginEnc.email = this.loginEmp.email

          this.request.post("/login/register/", this.loginEnc).then(res => {
            console.log(res)
            if (res.state === "Success") {
              this.$message.success("注册成功，请登录！")
              this.$router.push("/login")
            }else if (res.state() === "ErrorDuplicate"){
              this.$message.error("该邮箱已被注册！")
            }else {
              this.$message.error("出现了未知错误，请稍后再试")
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
