<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">主页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 70px; cursor: pointer">
      <span>{{ emp.name }}<i class="el-icon-arrow-down" style="margin-left: 5px"></i></span>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span @click="$router.push('/pInformation')">个人信息</span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0" >
          <span @click="logout">注销登录</span>
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
      eid: localStorage.getItem("eid"),
      emp: {
        name: ""
      }
    }
  },
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
  },
  computed: {
    currentPathName () {
      return store.state.currentPathName;　　//需要监听的数据
    }
  },
  watch: {
    currentPathName (newVal, oldVal) {
      console.log(newVal)
    }
  },
  created() {
    if (this.eid !== null){
      this.request.post("/department/getById", this.eid).then(res => {
        console.log(res)
        this.emp.name = res.name == null ? "新用户" : res.name
      })
    }
  },
  methods: {
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("eid")
      this.$message.success("Logout Success!")
    }
  }
}
</script>

<style scoped>

</style>
