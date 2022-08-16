<template>
  <div>


    <el-row style="height: 180px;">
      <el-card style="height: 170px;" id="building" >
        <div  style="font-weight: bolder;font-size: 35px;color: antiquewhite;margin: 60px 90px">{{greet}},{{name}},来一把紧张刺激的派派吧！</div>
      </el-card>
    </el-row>




    <el-row :gutter="10" style="margin-bottom: 35px;">

      <el-col :span="6">
        <el-card style="padding: 30px">
          <div style="color: #409EFF; font-weight: bold"><i class="el-icon-user-solid"></i> 员工人数</div>
          <div style="padding: 10px; text-align: center; font-weight: bolder; font-size: 30px">
            100
          </div>

        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="padding: 30px">
          <div style="color: #409EFF; font-weight: bold">员工人数</div>
          <div style="padding: 10px; text-align: center; font-weight: bolder; font-size: 30px">
            100
          </div>

        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="padding: 30px">
          <div style="color: #409EFF; font-weight: bold">员工人数</div>
          <div style="padding: 10px; text-align: center; font-weight: bolder; font-size: 30px">
            100
          </div>

        </el-card>
      </el-col>




      <el-col :span="6">
        <el-card style="padding: 30px">
          <div style="color: #409EFF; font-weight: bold">员工人数</div>
          <div style="padding: 10px; text-align: center; font-weight: bolder; font-size: 30px">
            100
          </div>

        </el-card>
      </el-col>

    </el-row>






    <el-row>
      <el-col :span="12">
        <el-card style=" width: 88%; height: 450px; margin: 0 auto;">
          <div id="main" style="width: 100%; height: 450px;" ></div>
        </el-card>
      </el-col>


      <el-col :span="12">
        <el-card style=" width: 88%; height: 450px; margin: 0 auto;">
          <div id="main1" style="width: 100%; height: 450px;" ></div>
        </el-card>
      </el-col>

    </el-row>

  </div>
</template>

<script>

import * as echarts from 'echarts'


export default {
  name: "Home",
  data() {
    return {
      greet: "",
      name: "",

    }
  },
  created() {
    this.name = localStorage.getItem("empName")
    let time = new Date()
    let hours = time.getHours();
    if (hours >= 5 && hours < 8) {
      this.greet = "早上好"
    }else if (hours >= 8 && hours < 12) {
      this.greet = "上午好"
    }else if (hours >= 12 && hours < 14) {
      this.greet = "中午好"
    }else if (hours >= 14 && hours < 20) {
      this.greet = "下午好"
    }else {
      this.greet = "晚上好"
    }
  },
  mounted() {
    var option = {
      title: {
        text: '各部门人数',
        left: 'center',
        top: '3%',
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '11%',
        left: 'center'
      },
      series: [
        {
          top: '12%',
          name: 'Access From',
          type: 'pie',
          radius: ['43%', '82%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '40',
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ]
        }
      ]
    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    myChart.setOption(option)


    var option1 = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          // Use axis to trigger tooltip
          type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
        }
      },
      legend: {},
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value'
      },
      yAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      series: [
        {
          name: 'Direct',
          type: 'bar',
          stack: 'total',
          label: {
            show: true
          },
          emphasis: {
            focus: 'series'
          },
          data: [320, 302, 301, 334, 390, 330, 320]
        },
        {
          name: 'Mail Ad',
          type: 'bar',
          stack: 'total',
          label: {
            show: true
          },
          emphasis: {
            focus: 'series'
          },
          data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
          name: 'Affiliate Ad',
          type: 'bar',
          stack: 'total',
          label: {
            show: true
          },
          emphasis: {
            focus: 'series'
          },
          data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
          name: 'Video Ad',
          type: 'bar',
          stack: 'total',
          label: {
            show: true
          },
          emphasis: {
            focus: 'series'
          },
          data: [150, 212, 201, 154, 190, 330, 410]
        },
        {
          name: 'Search Engine',
          type: 'bar',
          stack: 'total',
          label: {
            show: true
          },
          emphasis: {
            focus: 'series'
          },
          data: [820, 832, 901, 934, 1290, 1330, 1320]
        }
      ]
    };
    var chartDom1 = document.getElementById('main1');
    var myChart1 = echarts.init(chartDom1);
    myChart1.setOption(option1)

  }
}
</script>

<style scoped>

/deep/.el-card__body {
  padding: 0;
}

#building{
  background:url("../assets/bg.png");
  background-size: 100% auto ;
}

</style>
