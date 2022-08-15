<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="depName"></el-input>
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
<!--      <el-button class="ml-5" type="primary" @click="loadData">搜索</el-button>-->
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
      <div style="display:inline; margin: 0 10px;float: right">
        <el-button type="success" @click="openAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        <el-button type="danger" @click="deleteDeps">批量删除 <i class="el-icon-remove-outline"></i></el-button>
<!--        <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--        <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
      </div>
    </div>

    <div style="margin: 10px 0">

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="selectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
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
          <el-button type="primary" @click="showInformation(scope.row)">详情 <i class="el-icon-s-order"></i></el-button>
<!--          <el-button type="success" @click="openEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>-->
          <el-button type="danger" @click="deleteDep(scope.row)">删除 <i class="el-icon-remove-outline"></i></el-button>
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
          <el-input v-model="newDep.name" autocomplete="off" :disabled="true" ></el-input>
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




    <el-dialog title="员工信息" :visible.sync="dialogInformation" width="750px" >

      <el-form label-width="60px"  style="padding: 8px 25px;">

        <div style="margin: 23px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p>姓 名：</p>
            <el-input
                size="small"
                v-model="newDep.name"
                autocomplete="off"
                maxlength="8"
                :disabled="true"
                style="width: 150px;display: inline-block">
            </el-input>
          </div>

          <div style="display: inline-block;padding-right: 15px">
            <p>性 别：</p>
            <el-input
                size="small"
                v-model="newInformation.sex"
                autocomplete="off"
                maxlength="8"
                :disabled="true"
                style="width: 150px;display: inline-block">
            </el-input>
          </div>

          <div style="display: inline-block">
            <p>年 龄：</p>
            <el-input size="small" min="0" max="200" :disabled="true" v-model="newInformation.age" autocomplete="off" style="width: 150px;display: inline-block">
            </el-input>
          </div>
        </div>


        <div style="margin: 23px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p>昵 称：</p>
            <el-input
                size="small"
                v-model="newInformation.nickName"
                autocomplete="off"
                maxlength="8"
                :disabled="true"
                style="width: 150px;display: inline-block">
            </el-input>
          </div>

          <div style="display: inline-block;padding-right: 15px">
            <p>学 历：</p>
            <el-input
                size="small"
                v-model="newInformation.education"
                autocomplete="off"
                maxlength="8"
                :disabled="true"
                style="width: 150px;display: inline-block">
            </el-input>
          </div>

          <div style="display: inline-block">
            <p>电 话：</p>
            <el-input size="small"
                      v-model="newInformation.phoneNumber"
                      autocomplete="off"
                      maxlength="15"
                      :disabled="true"
                      style="width: 150px;display: inline-block">
            </el-input>
          </div>
        </div>

        <div style="margin: 23px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p>部 门：</p>

            <el-select size="small" v-model="newDep.department" clearable placeholder="请选择部门" style="width: 257px">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.value"
                  :value="item.value">
              </el-option>
            </el-select>

          </div>

          <div style="display: inline-block">
            <p>岗 位：</p>
            <el-input
                size="small"
                v-model="newDep.post"
                autocomplete="off"
                style="width: 257px">
            </el-input>
          </div>
        </div>

        <div style="margin: 13px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p style="display: block;color: #555;margin-bottom: 5px">住 址：</p>
            <el-input
                size="small"
                v-model="newInformation.address"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                maxlength="70"
                :disabled="true"
                style="width: 572px;margin-left: 43px">
            </el-input>
          </div>
        </div>

        <div style="margin: 13px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p style="display: block;color: #555;margin-bottom: 5px">简 介：</p>
            <el-input
                size="small"
                v-model="newInformation.briefIntroduction"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                maxlength="70"
                :disabled="true"
                style="width: 572px;margin-left: 43px">
            </el-input>
          </div>
        </div>


        <div style="margin: 13px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p style="display: block;color: #555;margin-bottom: 5px">爱 好：</p>
            <el-input
                size="small"
                v-model="newInformation.hobby"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                maxlength="70"
                :disabled="true"
                style="width: 572px;margin-left: 43px">
            </el-input>
          </div>
        </div>


        <div style="margin: 13px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p style="display: block;color: #555;margin-bottom: 5px">备 注：</p>
            <el-input
                size="small"
                v-model="newInformation.remarks"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                maxlength="70"
                :disabled="true"
                style="width: 572px;margin-left: 43px">
            </el-input>
          </div>
        </div>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogInformation = false">取 消</el-button>
        <el-button type="primary" @click="clickUpdate">修 改</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>
import request from "@/utils/request";

export default {
  name: "Employee",
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      depName: "",
      tableData: [],
      total: 0,
      size: 0,
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
      newDep: {
        eid: 0,
        name: "",
        department: "",
        post: ""
      },
      Deps: {},
      dialogFormVisible: false,
      dialogInformation: false,
      newInformation: {
        eid: 0,
        sex: "",
        age: 0,
        education: "",
        phoneNumber: "",
        nickName: "",
        address: "",
        hobby: "",
        briefIntroduction: "",
        remarks: ""
      }
    }
  },
  watch: {
    depName(newName,oldName) {
      if (newName !== oldName) {
        this.loadData()
      }
    },
    depValue(newValue,oldValue) {
      if (newValue !== oldValue){
        this.loadData()
      }
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
      request.get("/department/getPage/"+this.pageNum+"/"+this.pageSize+"/"+(this.depName === "" ? "null": this.depName)+"/"+(this.depValue === "" ? "null": this.depValue)).then(res => {
        console.log("num：" + this.pageNum)
        console.log("size：" + this.pageSize)
        console.log(res)
        this.tableData = res.values
        this.total = res.total
        this.size = res.size
      })
    },
    reset() {
      this.depName = ""
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
      request.post("/department/addDep",this.newDep).then(res => {
        if (res) {
          if (this.newDep.eid == null){
            this.$message.success("新增员工成功")
          }else {
            this.$message.success("更新员工信息成功")
          }
          this.dialogFormVisible = false
          this.loadData()
        }else {
          this.$message.error("新增员工失败")
        }
      })
    },
    openEdit(row) {
      this.newDep = row
      this.dialogFormVisible = true
    },
    deleteDep(row) {
      this.newDep = row
      this.$confirm('确认删除该员工信息？')
          .then(_ => {
            this.isDelete();
          })
          .catch(_ => {});
    },
    isDelete() {
      request.post("/department/deleteDep",this.newDep.eid).then(res => {
        if (res) {
          this.$message.success("已删除该员工信息！")
          if (this.total !== 1 && this.size === 1 && this.pageNum > 1) {
            this.pageNum --
          }
          this.loadData()
        }else {
          this.$message.error("员工信息删除失败")
        }
      })
    },
    selectionChange(val) {
      console.log(val)
      this.Deps = val
    },
    deleteDeps() {
      this.$confirm('确认批量删除员工信息？？？')
          .then(_ => {
            this.isDeleteDeps();
          })
          .catch(_ => {});
    },
    isDeleteDeps() {
      request.post("/department/deleteDeps",this.Deps).then(res => {
        if (res) {
          this.$message.success("已批量删除员工信息！")
          if (this.total !== 1 && this.size === this.Deps.length && this.pageNum > 1) {
            this.pageNum --
          }
          this.loadData()
        }else {
          this.$message.error("员工信息批量删除失败")
        }
      })
    },
    showInformation(row) {

      request.post("/department/getById",row.eid).then(res => {
        console.log(res)
        this.newDep.eid = res.eid
        this.newDep.department = res.department
        this.newDep.name = res.name
        this.newDep.post = res.post
      })

      request.post("/information/getInformationById",row.eid).then(res => {
        console.log(res)
        this.newInformation.eid = res.eid
        this.newInformation.sex = res.sex === true ? "男" : "女"
        this.newInformation.age = res.age
        this.newInformation.education = res.education
        this.newInformation.phoneNumber = res.phoneNumber
        this.newInformation.nickName = res.nickName
        this.newInformation.address = res.address
        this.newInformation.hobby = res.hobby
        this.newInformation.briefIntroduction = res.briefIntroduction
        this.newInformation.remarks = res.remarks
      })

      this.dialogInformation = true
    },
    clickUpdate() {
      this.addDep()
      this.dialogInformation = false
    }

  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
p {
  display: inline-block;
  font-size: small;
  color: #666;
}
</style>
