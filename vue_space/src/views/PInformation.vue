<template>

  <el-card style="width: 750px">
    <div >

      <el-form label-width="60px"  style="padding: 8px 25px;">

        <div style="margin: 23px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p>姓 名：</p>
            <el-input
                size="small"
                v-model="newDep.name"
                autocomplete="off"
                maxlength="8"
                style="width: 150px;display: inline-block">
            </el-input>
          </div>

          <div style="display: inline-block;padding-right: 15px">
            <p>性 别：</p>
            <el-select size="small" v-model="newInformation.sex" clearable placeholder="性 别" class="ml-5" style="width: 150px;display: inline-block">
              <el-option
                  v-for="item in sexOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>

          <div style="display: inline-block">
            <p>年 龄：</p>
            <el-input size="small" min="0" max="200" v-model="newInformation.age" type="number" autocomplete="off" style="width: 150px;display: inline-block">
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
                style="width: 150px;display: inline-block">
            </el-input>
          </div>

          <div style="display: inline-block;padding-right: 15px">
            <p>学 历：</p>
            <el-select size="small" v-model="newInformation.education" clearable placeholder="学 历" class="ml-5" style="width: 150px;display: inline-block">
              <el-option
                  v-for="item in eduOptions"
                  :key="item.value"
                  :label="item.value"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>

          <div style="display: inline-block">
            <p>电 话：</p>
            <el-input size="small"
                      v-model="newInformation.phoneNumber"
                      autocomplete="off"
                      maxlength="15"
                      style="width: 150px;display: inline-block">
            </el-input>
          </div>
        </div>

        <div style="margin: 23px 8px">
          <div style="display: inline-block;padding-right: 15px">
            <p>部 门：</p>
            <el-input
                size="small"
                v-model="newDep.department"
                autocomplete="off"
                :disabled="true"
                style="width: 257px">
            </el-input>
          </div>

          <div style="display: inline-block">
            <p>岗 位：</p>
            <el-input
                size="small"
                v-model="newDep.post"
                autocomplete="off"
                :disabled="true"
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
                style="width: 572px;margin-left: 43px">
            </el-input>
          </div>
        </div>


      </el-form>

      <div slot="footer" class="dialog-footer" style="margin-left: 235px">
        <el-button size="small" type="primary" @click="save">保 存</el-button>
        <el-button size="small" style="margin-left: 60px" @click="$router.push('/')">取 消</el-button>
      </div>
    </div>
  </el-card>

</template>

<script>
import request from "@/utils/request";
import store from "@/store";

export default {
  name: "PInformation",
  data() {
    return {
      newDep: {
        eid: 0,
        name: "",
        department: "",
        post: "",
      },
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
      },
      sexOptions: [{
        value: true,
        label: "男"
      },{
        value: false,
        label: "女"
      }],
      eduOptions: [{
        value: "小学"
      },{
        value: "中学"
      },{
        value: "中专"
      },{
        value: "专科"
      },{
        value: "本科"
      },{
        value: "研究生"
      },{
        value: "博士"
      }]

    }
  },
  created() {
    request.post("/department/get").then(res => {
      console.log(res)
      this.newDep.eid = res.eid
      this.newDep.name = res.name
      this.newDep.department = res.department
      this.newDep.post = res.post
    })

    request.post("/information/getInformation").then(res => {
      console.log(res)
      this.newInformation.eid = res.eid
      this.newInformation.sex = res.sex
      this.newInformation.age = res.age
      this.newInformation.education = res.education
      this.newInformation.phoneNumber = res.phoneNumber
      this.newInformation.nickName = res.nickName
      this.newInformation.address = res.address
      this.newInformation.hobby = res.hobby
      this.newInformation.briefIntroduction = res.briefIntroduction
      this.newInformation.remarks = res.remarks
    })

  },
  methods: {
    save() {
      let sign = true
      request.post("/department/addDep",this.newDep).then(res => {
        if (!res){
          sign = false
        }
      })

      request.post("/information/setInformation",this.newInformation).then(res => {
        if (!res){
          sign = false
        }
      })

      if (sign) {
        this.$message.success("修改成功！")
        localStorage.setItem("empName", this.newDep.name)
        store.commit("setPath")
      }else {
        this.$message.error("出现了未知错误，请稍后再试！")
      }
    }
  }
}
</script>

<style scoped>
  p {
    display: inline-block;
    font-size: small;
    color: #666;
  }
</style>
