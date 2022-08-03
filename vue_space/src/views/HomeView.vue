<template>
  <el-container style="min-height: 100vh">

    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
      <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
               background-color="rgb(48, 65, 86)"
               text-color="#fff"
               active-text-color="#ffd04b"
               :collapse-transition="false"
               :collapse="isCollapse"
      >
        <div style="height: 60px; line-height: 60px; text-align: center">
          <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
          <b style="color: white" v-show="logoTextShow">后台管理系统</b>
        </div>
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-message"></i>
            <span slot="title">导航一</span>
          </template>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </template>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span slot="title">导航三</span>
          </template>
          <el-submenu index="3-4">
            <template slot="title">选项4</template>
            <el-menu-item index="3-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 20px">
          <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
        </div>
        <el-dropdown style="width: 70px; cursor: pointer">
          <span>王小虎<i class="el-icon-arrow-down" style="margin-left: 5px"></i></span>
          <el-dropdown-menu slot="dropdown" >
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-header>

      <el-main>
        <div style="margin-bottom: 30px">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div style="margin: 10px 0">
          <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
          <el-select v-model="depValue" clearable placeholder="请选择部门" class="ml-5" style="width: 200px">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.value"
                :value="item.value">
            </el-option>
          </el-select>
<!--          <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>-->
<!--          <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input>-->
          <el-button class="ml-5" type="primary" @click="loadData">搜索</el-button>
          <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
          <el-button type="primary" @click="openAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
          <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>
          <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
          <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
          <el-table-column prop="eid" label="员工id" width="180">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="130">
          </el-table-column>
          <el-table-column prop="department" label="部门" width="150">
          </el-table-column>
          <el-table-column prop="post" label="岗位">
          </el-table-column>
          <el-table-column prop="createTime" label="入职时间">
          </el-table-column>
          <el-table-column label="操作"  width="200" align="center">
            <template slot-scope="scope">
              <el-button type="success">编辑 <i class="el-icon-edit"></i></el-button>
              <el-button type="danger">删除 <i class="el-icon-remove-outline"></i></el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 15, 20, 30]"
              :page-size="pageSize"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              >
          </el-pagination>
        </div>

        <el-dialog title="新增员工信息" :visible.sync="dialogFormVisible" width="500px" >
          <el-form label-width="120px" size="small">
            <el-form-item label="姓名" style="width: 330px">
              <el-input v-model="newDep.name" autocomplete="off" ></el-input>
            </el-form-item>
            <el-form-item label="部门" style="width: 330px">
              <el-select v-model="newDep.department" clearable placeholder="请选择部门" style="width: 210px">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.value"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="岗位" style="width: 330px">
              <el-input v-model="newDep.post" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addDep">确 定</el-button>
          </div>
        </el-dialog>



      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'HomeView',
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      name: "",
      tableData: [],
      total: 0,
      msg: "hello world",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false, //默认侧边栏展开
      sideWidth: 200, //动态的通过变量控制侧边栏高度
      logoTextShow: true,
      headerBg: 'headerBg',
      options: [{
        value: '开发部'
      }, {
        value: '市场部'
      }, {
        value: '营销部'
      }, {
        value: '采购部'
      }, {
        value: '人事部'
      }, {
        value: '财务部'
      }],
      depValue: "",
      newDep: {},
      dialogFormVisible: false,
    }
  },
  created() {
    //  请求分页查询数据
    this.loadData()
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false //收缩，图标旁边的文字不展示
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true //展开，图标旁边的文字展示
      }
    },
    loadData() {
      request.get("http://localhost:8888/department/getPage/"+this.pageNum+"/"+this.pageSize+"/"+(this.name === "" ? "null": this.name)+"/"+(this.depValue === "" ? "null": this.depValue)).then(res => {
        console.log(res)
        this.tableData = res.values
        this.total = res.total
      })
    },
    reset() {
      this.name = ""
      this.depValue = ""
      this.loadData()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.loadData()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.loadData()
    },
    openAdd() {
      this.dialogFormVisible = true
      this.newDep = {}
    },
    addDep() {
      request.post("http://localhost:8888/department/addDep",this.newDep).then(res => {
        if (res) {
          this.$message.success("新增员工成功")
          this.dialogFormVisible = false
        }else {
          this.$message.error("新增员工失败")
        }
      })
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
