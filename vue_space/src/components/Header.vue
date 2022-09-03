<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">主页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 150px; cursor: pointer">
      <span style="float: right">{{ empName }}<i class="el-icon-arrow-down" style="margin-left: 5px"></i></span>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <div style="width: 100%;height: 100%" @click="$router.push('/pInformation')">
            <span >个人信息</span>
          </div>
        </el-dropdown-item>

        <el-dropdown-item style="font-size: 14px; padding: 5px 0" >
          <div style="width: 100%;height: 100%" @click="logout">
            <span >注销登录</span>
          </div>
        </el-dropdown-item>

      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import store from "@/store";

export default {
  name: "Header",
  data() {
    return {
      emp: {
        name: ""
      },
    }
  },
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
  },
  computed: {
    currentPathName () {
      return store.state.currentPathName;　　//需要监听的数据
    },
    empName () {
      return store.state.empNameValue;
    }
  },
  watch: {
    currentPathName (newVal, oldVal) {
      console.log(newVal)
    },
    empName (newName,oldName) {
      console.log(newName)
    }
  },
  created() {
    if (this.eid !== null){
      this.request.post("/server/department/get").then(res => {
        console.log(res)
        this.emp.name = res.name == null ? "新用户" : res.name
        localStorage.setItem("empName", this.emp.name)
        store.commit("setPath")
      })
    }
  },
  methods: {
    async logout() {
      //需同步执行
      await this.request.post("/server/login/logout").then(res => {
        if (res) {
          this.$message.success("Logout Success!")
        }
      })
      localStorage.removeItem("token")
      localStorage.removeItem("authority")
      await this.$router.push("/login")
    }
  }
}
</script>

<style scoped>

</style>
