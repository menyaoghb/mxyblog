<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输入接收号码" v-model="listQuery.phone" clearable @keyup.enter.native="handleFilter">
            <template slot="prepend">号码</template>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleFilter" size="small">
            查询
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleRest" size="small">
            重置
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
      <el-table-column prop="phone" label="接收号码" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="request" label="入参报文" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="response" label="出参报文" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="发送时间" show-overflow-tooltip align="center" width="150">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
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

          <el-col :span="24">
            <el-form-item label="接收号码：">{{ temp.phone }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="入参报文：">{{ temp.request }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="出参报文：">{{ temp.response }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发送时间：">{{ temp.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</el-form-item>
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
import {getList, add, edit, deleteData} from '@/api/log/SysSmsSendLog'
import {addMsg, editMsg, delMsg} from "@/api/common/common";
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'SysSmsSendLogTable',
  components: {Pagination},
  filters: {},
  data() {
    return {
      list: null, //表格列表数据
      total: 0, // 总条数
      listLoading: true, // 列表加载圈
      listQuery: {
        phone: undefined,
        currentPage: 1,
        pageSize: 10
      },
      temp: {
        id: '',
        phone: '',
        request: '',
        response: '',
        createTime: '',
      },
      dialogFormVisible: false, //控制新增页关闭
      dialogFormVisibleView: false, //控制新增页关闭
      dialogStatus: '', // 判断当前操作是新增还是修改
      textMap: {
        add: '新增',
        edit: '编辑'
      },
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
      this.listQuery.phone = ''
    },
    /*表单重置*/
    resetTemp() {
      this.temp = {
        id: '',
        phone: '',
        request: '',
        response: '',
        createTime: '',
      }
    },
    /*新增跳转*/
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'add'
      this.dialogFormVisible = true
    },
    /*新增提交*/
    createData() {
      add(this.temp).then(() => {
        addMsg();
        this.dialogFormVisible = false
        this.getList();
      })
    },
    /*修改跳转*/
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'edit'
      this.dialogFormVisible = true
    },
    /*修改提交*/
    updateData() {
      edit(this.temp).then(() => {
        editMsg();
        this.dialogFormVisible = false
        this.getList();
      })
    },
    /*数据删除*/
    handleDelete(row) {
      this.temp.id = row.id;
      deleteData(this.temp).then(() => {
        delMsg();
        this.dialogFormVisible = false
        this.getList();
      })
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
