<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="loginEmp" :rules="rules" ref="empForm">
        <el-form-item prop="email">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="loginEmp.email" type="email"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="loginEmp.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="small"  autocomplete="off">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginEmp: {},
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { pattern: /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/,required: true, message: "请输入正确的邮箱地址", trigger: "blur" }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['empForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if(!res) {
              this.$message.error("用户名或密码错误")
            } else {
              this.$router.push("/")
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
