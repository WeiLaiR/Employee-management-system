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

      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
      <div style="display:inline; margin: 0 10px;float: right">

      </div>
    </div>

    <div style="margin: 10px 0">

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="selectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="eid" label="员工id" width="230">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="department" label="部门" width="200">
      </el-table-column>
      <el-table-column prop="post" label="岗位">
      </el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">

          <el-button type="success" @click="editAuthority(scope.row)">权限编辑 <i class="el-icon-s-order"></i></el-button>

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



    <el-dialog title=" 员工权限编辑 " :visible.sync="dialogFormVisible" width="500px" >

      <div style="margin-left: 50px">
        <el-tree
            :data="data"
            show-checkbox
            node-key="id"
            ref="tree"
            :default-expanded-keys="[990]"
            :default-checked-keys="checks"
            :props="defaultProps">
        </el-tree>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAu">确 定</el-button>
      </div>
    </el-dialog>





  </div>
</template>


<script>
import request from "@/utils/request";

export default {
  name: "Authority",
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      depName: "",
      tableData: [],
      total: 0,
      size: 0,
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

      data: [{
        id: 1,
        label: '主页'
      }, {
        id: 990,
        label: '公司管理',
        children: [{
          id: 2,
          label: '员工管理'
        }]
      }, {
        id: 3,
        label: '个人信息'
      }, {
        id: 4,
        label: '权限管理'
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      checks: [],
      returnAu: {
        eid: 0,
        authority: 0
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
    selectionChange(val) {
      console.log(val)
      this.Deps = val
    },
    editAuthority(row) {
      this.returnAu.eid = row.eid

      //从数据库中读取权限信息并展示
      request.post("/authority/getById", row.eid).then(res => {
        console.log("RES：")
        console.log(res)
        this.checks = []
        let temp = res.authority
        let value = 1
        let count = 1
        while (value <= temp) {
          if ((value & temp) > 0) {
            this.checks.push(count)
          }
          count ++
          value <<= 1
        }
      })

      this.dialogFormVisible = true
    },
    updateAu() {
      let temp = this.$refs.tree.getCheckedKeys()
      console.log(temp);
      for (let i = 0; i < temp.length; i++) {
        if (temp[i] < 900) {
          this.returnAu.authority += (1 << (temp[i] - 1))
        }
      }
      console.log(this.returnAu.authority)

      this.dialogFormVisible = false

      request.post("/authority/update", this.returnAu).then(res => {
        console.log(res)
        if (res) {
          this.$message.success("权限更改成功！")
        }else {
          this.$message.error("权限修改失败!")
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
p {
  display: inline-block;
  font-size: small;
  color: #666;
}
</style>
