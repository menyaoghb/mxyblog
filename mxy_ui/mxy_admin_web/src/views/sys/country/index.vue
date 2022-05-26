<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输入名称" v-model="listQuery.nickName" clearable @keyup.enter.native="handleFilter">
            <template slot="prepend">名称</template>
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
      <el-table-column prop="name" label="名称" sortable show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="value" label="值" sortable show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="path" label="全路径" sortable show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row}">
          <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
          <el-button size="mini" @click="handleView(row)" type="text">详情</el-button>
          <el-popconfirm confirm-button-text='删除' cancel-button-text='取消' icon="el-icon-info"
                         icon-color="red" title="确定删除吗？" @confirm="handleDelete(row)">
            <el-button slot="reference" size="mini" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                @pagination="getList"/>

    <!--新增/修改页-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="100px"
               style="width: 100%; margin-left:0px;">
        <el-form-item label="名称">
          <el-input v-model="temp.name"/>
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="temp.value"/>
        </el-form-item>
        <el-form-item label="父ID">
          <el-input v-model="temp.parentId"/>
        </el-form-item>
        <el-form-item label="全路径">
          <el-input v-model="temp.path"/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="temp.remark"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='add'?createData():updateData()">
          提交
        </el-button>
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>

    <!-- 详情 -->
    <el-dialog title="详情" :visible.sync="dialogFormVisibleView" width="700px" append-to-body>
      <el-form ref="dataForm" :model="temp" label-width="120px" size="mini">
        <el-row>

          <el-col :span="12">
            <el-form-item label="名称：">{{ temp.name }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="值：">{{ temp.value }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父ID：">{{ temp.parentId }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="全路径：">{{ temp.path }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建者：">{{ temp.createUser }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间：">{{ temp.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新者：">{{ temp.updateUser }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间：">{{ temp.updateTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注：">{{ temp.remark }}</el-form-item>
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
import {getList, add, edit, deleteData} from '@/api/sys/country/SysCountry.js'
import {addMsg, editMsg, delMsg} from "@/api/common/common";
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'SysCountryTable',
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
        id: '',
        name: '',
        value: '',
        parentId: '',
        path: '',
        isDelete: '',
        createUser: '',
        createTime: '',
        updateUser: '',
        updateTime: '',
        remark: '',
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
    },
    /*表单重置*/
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        value: '',
        parentId: '',
        path: '',
        isDelete: '',
        createUser: '',
        createTime: '',
        updateUser: '',
        updateTime: '',
        remark: '',
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
        this.dialogFormVisible = false
        this.getList();
      })
    },
    /*数据删除*/
    handleDelete(row) {
      this.temp.id = row.id;
      deleteData(this.temp).then(() => {
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
