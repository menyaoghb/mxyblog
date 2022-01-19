<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-input v-model="listQuery.roleName" prefix-icon="el-icon-search" placeholder="角色名称" style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-button class="filter-item" style="margin-left: 10px;" icon="el-icon-search" @click="handleFilter" round>
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="plain" icon="el-icon-plus"
                 @click="handleCreate" round>
        新增角色
      </el-button>
    </div>
    <!--表格-->
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading" element-loading-spinner="el-icon-loading">
      <el-table-column type="index" width="50" align="center"/>
      <el-table-column prop="roleName" label="角色名称" align="center"></el-table-column>
      <el-table-column prop="roleKey" label="角色权限" align="center"></el-table-column>
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
      <el-table-column label="创建时间" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center">
        <template slot-scope="{row}">
          <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
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
        <el-form-item label="角色名称">
          <el-input v-model="temp.roleName"/>
        </el-form-item>
        <el-form-item label="角色权限">
          <el-input v-model="temp.roleKey"/>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-select v-model="temp.dataScope" class="filter-item" style="width: 100%;">
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
  import {getSysRoleList, addRole, editRole, deleteRole,editRoleStatus} from '@/api/role/role'
  import Pagination from '@/components/Pagination'
  import {addUser, editUserStatus} from "@/api/user/user"; // 分页

  // 权限
  const userTypeOptions = [
    {key: '01', name: '全部数据权限'},
    {key: '02', name: '自定数据权限'}
  ]
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
          roleName: undefined
        },
        userTypeOptions, // 用户权限
        statusOptions, // 用户状态
        temp: {
          roleId: undefined,
          roleName: '',
          roleKey: '',
          roleSort: '',
          dataScope: '',
          menuCheckStrictly: '',
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
        getSysRoleList(this.listQuery).then(response => {
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
      /*角色状态改变*/
      handleModifyStatus(row) {
        this.temp.roleId = row.roleId;
        let param = {};
        param.roleId = row.roleId;
        param.status = row.status;
        editRoleStatus(param).then(() => {
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
          roleId: undefined,
          roleName: '',
          roleKey: '',
          roleSort: '',
          dataScope: '',
          menuCheckStrictly: '',
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
            addRole(this.temp).then(() => {
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
            editRole(this.temp).then(() => {
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
        this.$confirm('是否确认删除"' + row.roleName + '"的角色数据?', "警告", {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.temp.roleId = row.roleId;
          deleteRole(this.temp).then(() => {
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
