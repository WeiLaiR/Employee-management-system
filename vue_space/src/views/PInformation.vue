<template>

  <el-card style="width: 500px">
    <div >
      <el-form label-width="60px" style="padding: 8px 25px" size="small">
        <el-form-item label="姓 名">
          <el-input v-model="newDep.name" autocomplete="off" maxlength="8" ></el-input>
        </el-form-item>

        <el-form-item label="部 门">
          <el-input v-model="newDep.department" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="岗 位">
          <el-input v-model="newDep.post" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-left: 150px">
        <el-button type="primary" @click="save">保 存</el-button>
        <el-button style="margin-left: 30px" @click="$router.push('/')">取 消</el-button>
      </div>
    </div>
  </el-card>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "PInformation",
  data() {
    return {
      newDep: {
        eid: 0,
        name: "",
        department: "",
        post: "",
      }
    }
  },
  created() {
    request.post("/department/getById").then(res => {
      console.log(res)
      this.newDep.eid = res.eid
      this.newDep.name = res.name
      this.newDep.department = res.department
      this.newDep.post = res.post
    })
  },
  methods: {
    save() {
      request.post("/department/addDep",this.newDep).then(res => {
        if (res){
          this.$message.success("修改成功！")
          this.$router.push('/')
        }else {
          this.$message.error("出现了未知错误，请稍后再试")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
