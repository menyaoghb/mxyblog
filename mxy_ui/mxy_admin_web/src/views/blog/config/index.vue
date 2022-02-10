<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input placeholder="请输入名称" v-model="listQuery.name" clearable @keyup.enter.native="handleFilter">
            <template slot="prepend">名称</template>
          </el-input>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">
            <el-input placeholder="请输入值" v-model="listQuery.value" clearable @keyup.enter.native="handleFilter">
              <template slot="prepend">值</template>
            </el-input>
          </div>
        </el-col>
        <el-col :span="8">
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
      :cell-style="{padding:'0px'}" v-loading="listLoading">
      <el-table-column prop="name" label="名称" align="center"></el-table-column>
      <el-table-column prop="value" label="值" align="center"></el-table-column>
      <el-table-column prop="sign" label="标识" align="center"></el-table-column>
      <el-table-column prop="type" label="类型" align="center"></el-table-column>
      <el-table-column label="状态" width="90" class-name="status-col">
        <template slot-scope="{row}">
          <el-tooltip v-for="item in statusOptions" v-if="row.status===item.key" :content="item.name" placement="right">
            <el-switch
              v-model="row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
              @change="handleModifyStatus(row)">
            </el-switch>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row,$index}">
          <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
          <el-button size="mini" @click="handleDelete(row)" type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                @pagination="getList"/>

    <!--新增/修改页-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px"
               style="width: 400px; margin-left:160px;">
        <el-form-item label="名称">
          <el-input v-model="temp.name"/>
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="temp.value"/>
        </el-form-item>
        <el-form-item label="标识">
          <el-input v-model="temp.sign"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="temp.status" class="filter-item" style="width: 100%;">
            <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"/>
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
  </div>
</template>

<script>
import {getSysWebConfigList, addSysWebConfig, editSysWebConfig, deleteSysWebConfig} from '@/api/blog/config/config'
import Pagination from '@/components/Pagination'

// 权限
const userTypeOptions = []
// 状态
const statusOptions = [
  {key: '0', name: '启用'},
  {key: '1', name: '禁用'}
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
        name: undefined,
        value: undefined
      },
      userTypeOptions, // 用户权限
      statusOptions, // 用户状态
      temp: {
        id: undefined,
        name: '',
        value: '',
        sign: '',
        type: '',
        status: '0',
        remark: ''
      },
      dialogFormVisible: false, //控制新增页关闭
      dialogStatus: '', // 判断当前操作是新增还是修改
      textMap: {
        add: '新增',
        edit: '编辑'
      },
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /*列表查询*/
    getList() {
      this.listLoading = true
      getSysWebConfigList(this.listQuery).then(response => {
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
    /*用户状态改变*/
    handleModifyStatus(row) {
      this.temp.id = row.id;
      let param = {};
      param.id = row.id;
      param.status = row.status;
      editSysWebConfig(param).then(() => {
        this.$message({
          message: '更新成功',
          type: 'success'
        });
        this.getList();
      })
    },
    /*表单重置*/
    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        value: '',
        sign: '',
        type: '',
        status: '0',
        remark: ''
      }
    },
    /*新增跳转*/
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'add'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /*新增提交*/
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addSysWebConfig(this.temp).then(() => {
            this.$message({
              message: '新增成功',
              type: 'success'
            });
            this.dialogFormVisible = false
            this.getList();
          })
        }
      })
    },
    /*修改跳转*/
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'edit'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /*修改提交*/
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          editSysWebConfig(this.temp).then(() => {
            this.$message({
              message: '修改成功',
              type: 'success'
            });
            this.dialogFormVisible = false
            this.getList();
          })
        }
      })
    },
    /*数据删除*/
    handleDelete(row) {
      this.$confirm('是否确认删除？', "警告", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.temp.id = row.id;
        deleteSysWebConfig(this.temp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.dialogFormVisible = false
          this.getList();
        })
      }).catch(() => {
      });
    },
    /*条件重置*/
    handleRest() {
      this.listQuery.name = "";
      this.listQuery.value = "";
    },
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
