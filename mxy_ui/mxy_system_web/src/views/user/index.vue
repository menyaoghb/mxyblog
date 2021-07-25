<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-input v-model="listQuery.nickName" prefix-icon="el-icon-search" placeholder="姓名" style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-input v-model="listQuery.userName" prefix-icon="el-icon-search" placeholder="账号" style="width: 200px;margin-left: 5px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-button class="filter-item" style="margin-left: 10px;" icon="el-icon-search" @click="handleFilter" round>
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="plain" icon="el-icon-plus"
                 @click="handleCreate" round>
        新增用户
      </el-button>
    </div>
    <!--表格-->
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading">
      <el-table-column type="index" width="50" align="center"/>
      <el-table-column prop="nickName" label="姓名" align="center"></el-table-column>
      <el-table-column prop="userName" label="账号" align="center"></el-table-column>
      <el-table-column label="权限" align="center">
        <template slot-scope="{row}">
          <span v-for="item in userTypeOptions" v-if="row.userType===item.key">{{ item.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
      <el-table-column label="最后登录时间" align="center">
        <template slot-scope="{row}">
          <span>{{ row.loginDate | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="90" class-name="status-col">
        <template slot-scope="{row}">
          <el-button v-if="row.status==='0'" size="mini" type="success" @click="handleModifyStatus(row,'0')" round>
            已启用
          </el-button>
          <el-button v-if="row.status==='1'" size="mini" type="info" @click="handleModifyStatus(row,'1')" round>
            已禁用
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row,$index}">
          <el-button size="mini" @click="handleUpdate(row)" type="primary" icon="el-icon-edit">
          </el-button>
          <el-button size="mini" @click="handleDelete(row)" type="danger" icon="el-icon-delete">
          </el-button>
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
        <el-form-item label="姓名">
          <el-input v-model="temp.nickName"/>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="temp.userName"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="temp.password"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="temp.email"/>
        </el-form-item>
        <el-form-item label="权限">
          <el-select v-model="temp.userType" class="filter-item" style="width: 100%;">
            <el-option v-for="item in userTypeOptions" :key="item.key" :label="item.name" :value="item.key"/>
          </el-select>
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
  import {getSysUserList, addUser, editUser, deleteUser} from '@/api/user/user'
  import waves from '@/directive/waves' // waves directive
  import Pagination from '@/components/Pagination' // 分页

  // 权限
  const userTypeOptions = [
    {key: '01', name: 'S级管理员'},
    {key: '02', name: 'A级管理员'},
    {key: '03', name: 'B级管理员'},
    {key: '04', name: 'C级管理员'},
    {key: '05', name: 'D级管理员'},
    {key: '06', name: 'E级管理员'},
    {key: '07', name: 'F级参观者'}
  ]
  // 状态
  const statusOptions = [
    {key: '0', name: '启用'},
    {key: '1', name: '禁用'}
  ]

  export default {
    name: 'ComplexTable',
    components: {Pagination},
    directives: {waves},
    filters: {},
    data() {
      return {
        list: null, //表格列表数据
        total: 0, // 总条数
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 10,
          nickName: undefined,
          userName: undefined
        },
        userTypeOptions, // 用户权限
        statusOptions, // 用户状态
        temp: {
          userId: undefined,
          nickName: '',
          userName: '',
          password: '',
          email: '',
          userType: '',
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
        getSysUserList(this.listQuery).then(response => {
          this.list = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
      },
      /*条件查询*/
      handleFilter() {
        this.listQuery.pages = 1
        this.getList()
      },
      /*用户状态改变*/
      handleModifyStatus(row) {
        let text = row.status === "1" ? "启用" : "禁用";
        this.$confirm('确认要"' + text + '""' + row.userName + '"这个用户吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.temp.userId = row.userId;
          let param = {};
          param.userId = row.userId;
          param.status = row.status === "1" ? "0" : "1";
          editUser(param).then(() => {
            this.$message({
              message: text + '成功',
              type: 'success'
            });
            this.getList();
          })
        }).catch(() => {
        });
      },
      /*表单重置*/
      resetTemp() {
        this.temp = {
          userId: undefined,
          userName: '',
          nickName: '',
          password: '',
          email: '',
          userType: '',
          status: '0',
          remark: '',
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
            addUser(this.temp).then(() => {
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
            editUser(this.temp).then(() => {
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
        this.$confirm('是否确认删除用户账号为"' + row.userName + '"的数据?', "警告", {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.temp.userId = row.userId;
          deleteUser(this.temp).then(() => {
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.dialogFormVisible = false
            this.getList();
          })
        }).catch(() => {
        });
      }
    }
  }
</script>
<style>
  /*新增页按钮居中--（写法暂定）*/
  .dialog-footer {
    text-align: center;
  }
</style>
