<template>
  <div class="app-container">
    <div class="title-report-name">
      <span>
        报表用例二
      </span>
    </div>
  <el-table :data="tableData" border>
    <el-table-column
      label="公司"
      prop="departmentName">
    </el-table-column>
    <el-table-column
      key="年"
      label="每月达标率（%）">
      <el-table-column v-for="(item,index) in months" :key="item" :label="item">
        <template slot-scope="scope">
          <span>{{scope.row.monthData[index].complianceRate}}</span>
        </template>
      </el-table-column>
    </el-table-column>
  </el-table>
  </div>
</template>

<script>
  export default {
    data(){
      const staticData= [{
        "departmentName": "张三科技",
        monthData: [{
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "1"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "2"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "3"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "4"
        }, {
          "complianceRate": 82.71,
          "patrolLength": "62151.00",
          "actualPatrol": "51403.77",
          "monthOrWeekOrDay": "5"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "6"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "7"
        }, {
          "complianceRate": 33.0,
          "monthOrWeekOrDay": "8"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "9"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "10"
        }, {
          "complianceRate": 13.0,
          "monthOrWeekOrDay": "11"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "12"
        }],
      }, {
        "departmentName": "李四集团",
        "monthData": [{
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "1"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "2"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "3"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "4"
        }, {
          "complianceRate": 69.68,
          "patrolLength": "55661.00",
          "actualPatrol": "38784.54",
          "monthOrWeekOrDay": "5"
        }, {
          "complianceRate": 88.0,
          "monthOrWeekOrDay": "6"
        }, {
          "complianceRate": 77.0,
          "monthOrWeekOrDay": "7"
        }, {
          "complianceRate": 66.0,
          "monthOrWeekOrDay": "8"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "9"
        }, {
          "complianceRate": 44.0,
          "monthOrWeekOrDay": "10"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "11"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "12"
        }],
      }, {
        "departmentName": "王五投资",
        "monthData": [{
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "1"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "2"
        }, {
          "complianceRate": 43.0,
          "monthOrWeekOrDay": "3"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "4"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "5"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "6"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "7"
        }, {
          "complianceRate": 0.0,
          "monthOrWeekOrDay": "8"
        }, {
          "complianceRate": 4.0,
          "monthOrWeekOrDay": "9"
        }, {
          "complianceRate": 88.0,
          "monthOrWeekOrDay": "10"
        }, {
          "complianceRate": 6.0,
          "monthOrWeekOrDay": "11"
        }, {
          "complianceRate": 77.0,
          "monthOrWeekOrDay": "12"
        }],
      }]
      return{
        // 有两层数据，渲染表格
        months:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
        tableData:staticData,
      }
    }

  }
</script>
<style>
  .title-report-name{
    text-align: center;
    padding: 20px;
  }
</style>
