<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input v-model="listQuery.title" prefix-icon="el-icon-search" placeholder="模块标题" style="width: 200px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable/>
        </el-col>
        <el-col :span="4">
          <el-select v-model="listQuery.businessType" class="filter-item" style="width: 100%;">
            <el-option v-for="item in TypeOptions" :key="item.key" :label="item.name" :value="item.key"/>
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-date-picker
            v-model="createTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%"
            type="datetimerange"
            range-separator="~"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          >
          </el-date-picker>
        </el-col>
        <el-col :span="8">
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleFilter" size="small">
            查询
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleRest" size="small">
            重置
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" @click="exportDownload" size="small">
            导出
          </el-button>
        </el-col>
      </el-row>
    </div>
    <!--表格-->
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading">
      <el-table-column type="index" width="50" align="center"/>
      <el-table-column prop="title" label="模块标题" align="center" show-overflow-tooltip width="150"></el-table-column>
      <el-table-column label="操作类型" align="center">
        <template slot-scope="{row}">
          <span v-for="item in TypeOptions" v-if="row.businessType===item.key">{{ item.name }}</span>
        </template>
      </el-table-column>
      <!--<el-table-column prop="method" label="方法名称" align="center"></el-table-column>-->
      <el-table-column prop="operUrl" show-overflow-tooltip label="请求URL" align="center"></el-table-column>
      <el-table-column label="请求时间" align="center">

        <template slot-scope="{row}">
          <div slot="reference" class="name-wrapper" v-if="row.status=== 0">
            <el-tag size="medium">{{ row.resTime }}ms</el-tag>
          </div>
          <div slot="reference" class="name-wrapper" v-if="row.status=== 1">
            <el-tag size="medium" style="color: red">{{ row.resTime }}ms</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="operIp" label="主机地址" align="center"></el-table-column>
      <el-table-column prop="operLocation" show-overflow-tooltip label="操作地点" align="center"></el-table-column>
      <el-table-column prop="operName" label="操作人员" align="center"></el-table-column>
      <el-table-column label="操作时间" align="center" width="150">
        <template slot-scope="{row}">
          <span>{{ row.operTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" class-name="small-padding">
        <template slot-scope="{row,$index}">
          <el-button size="mini" @click="handleView(row)" type="text">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                @pagination="getList"/>

    <!-- 操作日志详细 -->
    <el-dialog title="日志详情" :visible.sync="dialogFormVisible" width="700px" append-to-body>
      <el-form ref="dataForm" :model="temp" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作模块：">{{ temp.title }}</el-form-item>
            <el-form-item
              label="登录信息："
            >{{ temp.operName }} / {{ temp.operIp }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求地址：">{{ temp.operUrl }}</el-form-item>
            <el-form-item label="请求方式：">{{ temp.requestMethod }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作方法：">{{ temp.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">{{ temp.operParam }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="返回参数：">{{ temp.jsonResult }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作人员：">{{ temp.operName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：" v-for="item in statusOptions" v-if="temp.status===item.key">
              {{ item.name }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作时间：">{{ temp.operTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {getSysLogList, detailLog} from '@/api/log/log'
import Pagination from '@/components/Pagination'
import {parseTime} from "../../../../../mxy_system_web/src/utils"; // 分页

// 操作类型
const TypeOptions = [
  {key: 1, name: '新增'},
  {key: 2, name: '删除'},
  {key: 3, name: '修改'},
  {key: 4, name: '查询'},
  {key: 5, name: '导入'},
  {key: 6, name: '导出'},
  {key: 7, name: '上传'},
  {key: 8, name: '下载'},
  {key: 9, name: '登入'},
  {key: 10, name: '登出'},
  {key: 11, name: '失败'},
  {key: 99, name: '失败原因'},
  {key: 0, name: '其他'}
]
// 操作状态
const statusOptions = [
  {key: 0, name: '成功'},
  {key: 1, name: '失败'}
]

export default {
  name: 'ComplexTable',
  components: {Pagination},
  filters: {},
  data() {
    return {
      list: null, //表格列表数据
      total: 0, // 总条数
      listLoading: true,
      listQuery: {
        currentPage: 1,
        pageSize: 10,
        title: undefined,
        businessType: undefined,
        startTime: undefined,
        endTime: undefined
      },
      createTime: undefined,
      TypeOptions, // 操作类型
      statusOptions, // 操作状态
      temp: {
        userId: undefined,
        nickName: '',
        userName: '',
        password: '',
        email: '',
        userType: '',
        status: '',
        remark: ''
      },
      dialogFormVisible: false //控制新增页关闭
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /*列表查询*/
    getList() {
      this.listLoading = true
      getSysLogList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    /*条件查询*/
    handleFilter() {
      this.listQuery.currentPage = 1
      const createTime = this.createTime;
      if (createTime !== null && createTime !== ''){
        this.listQuery.startTime = createTime[0];
        this.listQuery.endTime = createTime[1]
      }
      this.getList()
    },
    /*条件重置*/
    handleRest() {
      this.listQuery.title = "";
      this.listQuery.businessType = "";
      this.listQuery.endTime = "";
      this.listQuery.startTime = "";
      this.createTime = [];
    },
    /*数据详情*/
    handleView(row) {
      this.dialogFormVisible = true;
      this.temp = row;
    },
    /*数据导出*/
    exportDownload() {
      const headers = {
        '模块标题': 'title',
        '操作类型': 'businessType',
        '请求URL': 'operUrl',
        '请求时间': 'resTime',
        '主机地址': 'operIp',
        '操作地点': 'operLocation',
        '操作人员': 'operName',
        '操作时间': 'operTime'
      }
      import('@/vendor/Export2Excel').then(async excel => {
        const list = this.list
        const data = this.formatJson(headers, list)
        excel.export_json_to_excel({
          header: Object.keys(headers),
          data,
          filename: '数据导出',
          autoWidth: true,
          bookType: 'xlsx'
        })
      })
    },
    formatJson(headers, rows) {
      return rows.map(item => {
        return Object.keys(headers).map(key => {
          if (headers[key] === 'operTime') {
            return parseTime(item[headers[key]])
          }
          return item[headers[key]]
        })
      })
    }
  }
}
</script>
<style>
/*新增页按钮居中--（写法暂定）*/
.dialog-footer {
  text-align: center;
}
.filter-container{
  margin-bottom: 18px;
}
</style>
