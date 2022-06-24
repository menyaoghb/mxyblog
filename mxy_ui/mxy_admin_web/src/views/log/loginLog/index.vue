<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input placeholder="登录账号 | 登录姓名" v-model="listQuery.nickName" clearable @keyup.enter.native="handleFilter">
            <template slot="prepend">登录账号</template>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleFilter" size="small">
            查询
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleRest" size="small">
            重置
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" size="small">
            新增
          </el-button>
        </el-col>
      </el-row>
    </div>
    <!--表格-->
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading"
      element-loading-spinner="el-icon-loading">
      <el-table-column type="index" width="50" align="center"/>
      <el-table-column prop="userId" label="登录账号" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="userName" label="登录姓名" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="ip" label="登录IP" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="address" label="登录地点" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="description" label="登录描述" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="loginType" label="登录方式" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="登录时间" align="center" width="150">
        <template slot-scope="{row}">
          <span>{{ row.loginTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row}">
          <el-button size="mini" @click="handleView(row)" type="text">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                @pagination="getList"/>

    <!-- 详情 -->
    <el-dialog title="详情" :visible.sync="dialogFormVisibleView" width="700px" append-to-body>
      <el-form ref="dataForm" :model="temp" label-width="120px" size="mini">
        <el-row>

          <el-col :span="12">
            <el-form-item label="登录账号：">{{ temp.userId }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录姓名：">{{ temp.userName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录地点：">{{ temp.address }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录IP：">{{ temp.ip }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录描述：">{{ temp.description }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录方式：">{{ temp.loginType }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录时间：">{{ temp.loginTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleView = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getList, add, edit, deleteData} from '@/api/log/SysLoginLog'
import {addMsg, editMsg, delMsg} from "@/api/common/common";
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'SysLoginLogTable',
  components: {Pagination},
  filters: {},
  data() {
    return {
      list: null, //表格列表数据
      total: 0, // 总条数
      listLoading: true, // 列表加载圈
      listQuery: {
        currentPage: 1,
        pageSize: 10
      },
      temp: {
        loginId: '',
        userId: '',
        userName: '',
        ip: '',
        address: '',
        description: '',
        loginType: '',
        loginTime: '',
      },
      dialogFormVisibleView: false, //控制新增页关闭
      rules: {}
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /*列表查询*/
    getList() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    /*条件查询*/
    handleFilter() {
      this.listQuery.currentPage = 1
      this.getList()
    },
    /*条件重置*/
    handleRest() {
    },
    /*表单重置*/
    resetTemp() {
      this.temp = {
        loginId: '',
        userId: '',
        userName: '',
        ip: '',
        address: '',
        description: '',
        loginType: '',
        loginTime: '',
      }
    },
    /*数据详情*/
    handleView(row) {
      this.dialogFormVisibleView = true;
      this.temp = row;
    }
  }
}
</script>
<style>
/*新增页按钮居中--（写法暂定）*/
.dialog-footer {
  text-align: center;
}

.el-dialog__body {
  padding: 10px 10px;
}

.filter-container {
  margin-bottom: 18px;
}
</style>
