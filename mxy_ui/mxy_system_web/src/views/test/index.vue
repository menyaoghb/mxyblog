<template>
  <div class="app-container">
    <div class="title-report-name">
      <span>
        报表用例一
      </span>
    </div>
    <el-table
      :data="tableData"
      :span-method="objectSpanMethod"
      border
      height="590"
      align="center"
      style="width: 100%">
      <el-table-column align="center" fixed prop="id" label="日期"></el-table-column>
      <el-table-column align="center" fixed prop="type" label="类型"></el-table-column>
      <el-table-column align="center"  v-for="(item,index) in days" :key="item" :label="item">
        <template slot-scope="scope">
          <span>{{scope.row.count[index].sum}}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        days: ["1日", "2日", "3日", "4日", "5日", "6日", "7日", "8日", "9日", "10日", "11日", "12日", "13日", "14日", "15日", "16日", "17日", "18日", "19日", "20日", "21日", "22日", "23日", "24日", "25日", "26日", "27日", "28日", "29日", "30日", "31日"],
        tableData: [{
          "id": '1月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '1月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '2月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '2月',
          "type": "实际产能",
          "count": [{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "22"
          }, {
            "sum": "33"
          },{
            "sum": "66"
          }, {
            "sum": "58"
          },{
            "sum": "46"
          }, {
            "sum": "85"
          },{
            "sum": "77"
          }, {
            "sum": "69"
          },{
            "sum": "25"
          }, {
            "sum": "38"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '3月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '3月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '4月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '4月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '5月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '5月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '6月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '6月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '7月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '7月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '8月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '8月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '9月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '9月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '10月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '10月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '11月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '11月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        },{
          "id": '12月',
          "type": "计划产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "88"
          }, {
            "sum": "77"
          },{
            "sum": "66"
          }, {
            "sum": "55"
          },{
            "sum": "66"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }]
        }, {
          "id": '12月',
          "type": "实际产能",
          "count": [{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          },{
            "sum": "100"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "99"
          }, {
            "sum": "88"
          }]
        }]
      };
    },
    methods: {
      /*合并行*/
      arraySpanMethod({row, column, rowIndex, columnIndex}) {
        if (rowIndex % 2 === 0) {
          if (columnIndex === 0) {
            return [1, 2];
          } else if (columnIndex === 1) {
            return [0, 0];
          }
        }
      },
      /*合并列*/
      objectSpanMethod({row, column, rowIndex, columnIndex}) {
        if (columnIndex === 0) {
          if (rowIndex % 2 === 0) {
            return {
              rowspan: 2,
              colspan: 1
            };
          } else {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }
      }
    }
  };
</script>
<style>
  .title-report-name{
    text-align: center;
    padding: 20px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }
</style>
