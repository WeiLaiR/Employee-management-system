<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
      <b style="color: white" v-show="logoTextShow">后台管理系统</b>
    </div>
    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">公司管理</span>
      </template>
      <el-menu-item index="/employee">
        <i class="el-icon-s-custom"></i>
        <span slot="title">员工管理</span>
      </el-menu-item>
    </el-submenu>

    <el-menu-item index="/pInformation">
      <template slot="title">
        <i class="el-icon-user"></i>
        <span slot="title">个人信息</span>
      </template>
    </el-menu-item>

    <el-menu-item index="/authority">
      <template slot="title">
        <i class="el-icon-set-up"></i>
        <span slot="title">权限管理</span>
      </template>
    </el-menu-item>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  }
}
</script>

<style scoped>

</style>
