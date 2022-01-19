<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <div class="app-container">
        <el-divider content-position="center">角色结构</el-divider>
        <el-input
          placeholder="输入角色名称进行过滤"
          v-model="filterText" clearable>
        </el-input>
        <el-tree class="filter-tree role-tree"
                 :data="data"
                 :props="defaultProps"
                 default-expand-all
                 :filter-node-method="filterNode"
                 ref="tree"
                 @node-click="handleNodeClick"
                 empty-text=" "
                 icon-class="el-icon-loading"
                 v-loading="listLoading"
                 element-loading-spinner="el-icon-loading"></el-tree>
      </div>
    </el-col>
    <el-col :span="18">
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="包含人员" name="first">
        <!--查询-->
        <div class="filter-container">
          <el-input v-model="listQuery.nickName" prefix-icon="el-icon-search" placeholder="姓名" style="width: 200px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable/>
          <el-input v-model="listQuery.username" prefix-icon="el-icon-search" placeholder="账号"
                    style="width: 200px;margin-left: 5px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable/>
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleFilter" size="small">
            查询
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" @click="handleRest" size="small">
            重置
          </el-button>
        </div>
        <!--表格-->
        <el-table
          :data="userList"
          style="width: 100%" :row-style="{height:'40px'}"
          :cell-style="{padding:'0px'}" v-loading="listLoading"
          element-loading-spinner="el-icon-loading">
          <el-table-column type="index" width="50" align="center"/>
          <el-table-column label="头像" align="center">
            <template slot-scope="{row}">
              <el-image :src="row.avatar" style="border-radius: 50%;width: 30px;height: 30px;"></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="姓名" align="center"></el-table-column>
          <el-table-column prop="username" label="账号" align="center"></el-table-column>
          <el-table-column label="权限" align="center">
            <template slot-scope="{row}">
              <span v-for="item in userTypeOptions" v-if="row.userType===item.key">{{ item.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="230" class-name="small-padding">
            <template slot-scope="{row,$index}">
              <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                    @pagination="getList"/>
      </el-tab-pane>
      <el-tab-pane label="功能权限" name="second">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-tree
              :data="menuData"
              show-checkbox
              node-key="id"
              default-expand-all
              :props="menuDefaultProps"
              empty-text=" "
              v-loading="listLoading"
              @node-click="menuHandleNodeClick"
              element-loading-spinner="el-icon-loading">
            </el-tree>

          </el-col>
          <el-col :span="18">
            <div class="filter-container">
              <el-button v-show="isSetRoleMenu" class="filter-item" style="margin-left: 10px;float: right;" @click="setRoleMenu" size="small">
                设置
              </el-button>
              <el-button v-show="!isSetRoleMenu" class="filter-item" style="margin-left: 10px;float: right;" @click="isSetRoleMenu=true" size="small">
                取消
              </el-button>
              <el-button v-show="!isSetRoleMenu" class="filter-item" style="margin-left: 10px;float: right;" @click="saveRoleMenu" size="small">
                保存
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="数据权限" name="third">角色管理</el-tab-pane>
      <el-tab-pane label="角色首页" name="fourth">定时任务补偿</el-tab-pane>
    </el-tabs>

    <!--新增/修改页-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px"
               style="width: 400px; margin-left:160px;">
        <el-form-item label="名称">
          <el-input v-model="temp.roleName"/>
        </el-form-item>
        <el-form-item label="标识">
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
    </el-col>
  </el-row>
</template>

<script>
  import {getSysRoleList, addRole, editRole, deleteRole,editRoleStatus} from '@/api/role/role'
  import Pagination from '@/components/Pagination'
  import {addUser, editUserStatus, getRoles, getSysUserList} from "@/api/user/user";
  import {getTreeData} from "@/api/menu/menu"; // 分页

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
        data: [], // 角色集合
        menuData: [], // 角色集合
        activeName: 'first', // tab 默认首次加载位置
        filterText: '', // 树搜索
        defaultProps: {
          children: 'children',
          label: 'roleName'
        },
        menuDefaultProps: {
          children: 'children',
          label: 'label',
          disabled: this.disabledFn
        },
        isSetRoleMenu: true,
        userList: null, //人员数据
        total: 0, // 总条数
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 10,
          nickName: undefined,
          username: undefined,
          userType:undefined
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
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    created() {
      this.getList()
    },
    methods: {
      /*角色列表查询*/
      getList() {
        this.listLoading = true
        getSysRoleList({currentPage: 1,pageSize: 10}).then(response => {
          this.data = response.data.records
          if (response.data.total>0){
            this.listQuery.userType = this.data[0].roleId;
            this.getUserList();
          }
          this.listLoading = false
        })
      },
      /*人员列表查询*/
      getUserList() {
        this.listLoading = true
        getRoles().then(response => {
          let roles = response.data;
          this.userTypeOptions = [];
          for (const role of roles) {
            this.userTypeOptions.push({key: role.roleId, name: role.roleName})
          }
        })
        getSysUserList(this.listQuery).then(response => {
          this.userList = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
      },
      /*菜单树数据*/
      getTreeData() {
        this.listLoading = true
        getTreeData().then(response => {
          this.menuData = response.data
          this.listLoading = false
        })
      },
      /*条件查询*/
      handleFilter() {
        this.listQuery.currentPage = 1
        this.getUserList()
      },
      /*条件查询*/
      disabledFn() {
        return this.isSetRoleMenu;
      },
      /*条件重置*/
      handleRest() {
        this.listQuery.nickName = "";
        this.listQuery.username = "";
      },
      /*树点击事件*/
      handleNodeClick(data) {
        this.listQuery.userType = data.roleId;
        this.getUserList()
      },
      /*菜单树点击事件*/
      menuHandleNodeClick(data) {
        console.log(data)
      },
      /*功能权限设置*/
      setRoleMenu() {
        this.isSetRoleMenu = false
      },
      /*功能权限设置*/
      saveRoleMenu() {
        this.isSetRoleMenu = true
      },
      /*树查询过滤*/
      filterNode(value, data) {
        if (!value) return true;
        return data.roleName.indexOf(value) !== -1;
      },
      /*标签切换点击事件*/
      handleClick(tab, event) {
        if (tab.name==="first"){
          this.getUserList();
        }
        if (tab.name==="second"){
            this.getTreeData();
        }
        if (tab.name==="third"){

        }
        if (tab.name==="fourth"){

        }
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
  .role-tree{
    margin-top: 15px;
  }
  .el-input__inner{
    height: 32px;
    line-height: 32px;
  }
</style>
