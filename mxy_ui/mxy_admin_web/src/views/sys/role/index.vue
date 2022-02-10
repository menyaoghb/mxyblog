<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <div class="app-container">
        <el-divider content-position="center">角色结构</el-divider>
        <el-input
          placeholder="输入角色名称进行过滤"
          style="width: 80%;"
          v-model="filterText" clearable>
        </el-input>
        <div class="add-role">
          <el-button type="primary" icon="el-icon-plus" title="新增角色" @click="handleCreate()" circle></el-button>
        </div>
        <div style="height: 585px;overflow: auto;">
          <el-tree class="filter-tree role-tree"
                   :data="data"
                   :props="defaultProps"
                   default-expand-all
                   :filter-node-method="filterNode"
                   ref="tree"
                   @node-click="handleNodeClick"
                   @node-drop="handleDrop"
                   @node-contextmenu="rightClick"
                   draggable
                   :allow-drop="allowDrop"
                   :allow-drag="allowDrag"
                   empty-text=" "
                   icon-class="el-icon-loading"
                   v-loading="rolesLoading"
                   element-loading-spinner="el-icon-loading"></el-tree>
        </div>
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
            <!--角色关联用户表格-->
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
            <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage"
                        :limit.sync="listQuery.pageSize"
                        @pagination="getList"/>
          </el-tab-pane>
          <el-tab-pane label="功能权限" name="second">
            <el-row :gutter="20">
              <!--菜单树-->
              <el-col :span="6">
                <el-tree
                  :data="menuData"
                  show-checkbox
                  node-key="id"
                  default-expand-all
                  :props="menuDefaultProps"
                  :default-checked-keys="checkMenuData"
                  empty-text=" "
                  ref="menuTree"
                  v-loading="menuTreeLoading"
                  @node-click="menuHandleNodeClick"
                  element-loading-spinner="el-icon-loading">
                </el-tree>
              </el-col>
              <!--菜单关联的角色列表-->
              <el-col :span="18">
                <div class="filter-container">
                  <el-button v-show="isSetRoleMenu" class="filter-item" style="margin-left: 10px;float: right;"
                             @click="setRoleMenu" size="small">
                    设置
                  </el-button>
                  <el-button v-show="!isSetRoleMenu" class="filter-item" style="margin-left: 10px;float: right;"
                             @click="isSetRoleMenu=true" size="small">
                    取消
                  </el-button>
                  <el-button v-show="!isSetRoleMenu" class="filter-item" style="margin-left: 10px;float: right;"
                             @click="saveRoleMenu" size="small">
                    保存
                  </el-button>
                </div>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="数据权限" name="third">数据权限</el-tab-pane>
          <el-tab-pane label="角色更新" name="fourth">
            <div style="width: 100%;height: 35px">
              <el-alert title="请点击角色进行操作" type="info" v-show="showTitle" center close-text="知道了">
              </el-alert>
            </div>
            <div>
              <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px"
                       style="width: 400px; margin-left:160px;">
                <el-form-item label="名称">
                  <el-input v-model="temp.roleName"/>
                </el-form-item>
                <el-form-item label="标识">
                  <el-input v-model="temp.roleKey" disabled/>
                </el-form-item>
                <el-form-item label="状态">
                  <el-select v-model="temp.status" class="filter-item" style="width: 100%;">
                    <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="updateData()">更新</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
        </el-tabs>
        <!--新增页-->
        <el-drawer title="新增角色" :visible.sync="dialog" direction="ttb" custom-class="role-drawer" ref="drawer"
                   size="50%">
          <div class="role-drawer__content">
            <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px"
                     style="width: 400px;display: inline-block;">
              <el-form-item label="名称" prop="roleName">
                <el-input v-model="temp.roleName"/>
              </el-form-item>
              <el-form-item label="标识" prop="roleKey">
                <el-input v-model="temp.roleKey"/>
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
            <div class="demo-drawer__footer">
              <el-button @click="cancelForm">取 消</el-button>
              <el-button type="primary" @click="createData()" :loading="loading">{{
                  loading ? '提交中 ...' : '确 定'
                }}
              </el-button>
            </div>
          </div>
        </el-drawer>
        <!--右键菜单-->
        <div id="perTreeMenu" class="tree_menu" v-show="menuDisplay" :style="{...rmStyle}">
          <el-menu class="el-menu-vertical-demo"
                   text-color="#000" active-text-color="#409EFF">
            <el-menu-item index="1">新年</el-menu-item>
            <el-menu-item index="2">快乐</el-menu-item>
          </el-menu>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import {getSysRoleList, addRole, editRole, deleteRole, editRoleStatus, saveRoleMenu} from '@/api/role/role'
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
      menuData: [], // 菜单集合
      checkMenuData: [], // 选中菜单集合
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
      roleId: '', // 当前选择角色id
      userList: null, //人员数据
      total: 0, // 总条数
      listLoading: true,
      rolesLoading: true,
      menuTreeLoading: true,
      menuDisplay: false,
      rmStyle: {},
      listQuery: {
        currentPage: 1,
        pageSize: 10,
        nickName: undefined,
        username: undefined,
        userType: undefined
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
      rules: {
        roleName: [
          {required: true, message: '请填写角色名称', trigger: 'blur'},
          {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
        ],
        roleKey: [
          {required: true, message: '请填写角色标识', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ]
      },
      dialog: false,
      loading: false,
      showTitle: true
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
      this.rolesLoading = true
      getSysRoleList({currentPage: 1, pageSize: 100}).then(response => {
        this.data = response.data.records
        this.rolesLoading = false
        if (response.data.total > 0) {
          this.roleId = this.data[0].roleId;
          this.listQuery.userType = this.data[0].roleId;
          this.temp = Object.assign({}, this.data)
          this.getUserList();
        }

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
      this.menuTreeLoading = true
      getTreeData({roleId: this.roleId}).then(response => {
        this.menuData = response.data.treeData;
        this.checkMenuData = response.data.checkTreeData;
        this.menuTreeLoading = false
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
    /*角色结构-角色树点击事件*/
    handleNodeClick(data) {
      this.showTitle = false;
      this.listQuery.userType = data.roleId;
      this.roleId = data.roleId;
      this.temp = Object.assign({}, data)
      this.getUserList();
      this.getTreeData();
    },
    /*功能权限-菜单树点击事件*/
    menuHandleNodeClick(data) {
      console.log(data)
    },
    /*功能权限设置*/
    setRoleMenu() {
      this.isSetRoleMenu = false
    },
    /*功能权限保存*/
    saveRoleMenu() {
      this.isSetRoleMenu = true;
      saveRoleMenu({roleId: this.roleId, menuIds: this.$refs.menuTree.getCheckedKeys()}).then(response => {
        this.$message({
          message: '更新成功',
          type: 'success'
        });
      })
    },
    /*树查询过滤*/
    filterNode(value, data) {
      if (!value) return true;
      return data.roleName.indexOf(value) !== -1;
    },
    /*标签切换点击事件*/
    handleClick(tab, event) {
      if (tab.name === "first") {
        this.getUserList();
      }
      if (tab.name === "second") {
        this.getTreeData();
      }
      if (tab.name === "third") {

      }
      if (tab.name === "fourth") {

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
      this.dialog = true
    },
    /*新增提交*/
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.loading = true;
          addRole(this.temp).then(() => {
            this.dialog = false
            this.loading = false;
            this.$message({
              message: '新增成功',
              type: 'success'
            });
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
    },
    /*新增取消*/
    cancelForm() {
      this.loading = false;
      this.dialog = false;
    },
    /*节点是否拖拽*/
    allowDrag(draggingNode) {
      return true;
    },
    /*节点放置方式*/
    allowDrop(draggingNode, dropNode, type) {
      return type !== 'inner';
    },
    /*拖拽成功完成时触发的事件*/
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log('tree drop: ', draggingNode, dropNode, dropType);
    },
    // 右击树节点
    rightClick(e, obj, node, self) {
      this.rmStyle = {
        top: e.pageY + 'px',
        left: e.pageX + 'px'
      }
      this.menuDisplay = true
      const sel = this
      document.onclick = function (ev) {
        if (ev.target !== document.getElementById('perTreeMenu')) {
          sel.menuDisplay = false
        }
      }
    }
  }
}
</script>
<style>
/*新增页按钮居中--（写法暂定）*/
.dialog-footer {
  text-align: center;
}

.role-tree {
  margin-top: 15px;
}

.el-input__inner {
  height: 32px;
  line-height: 32px;
}

.el-button.is-circle {
  padding: 8px;
  margin-left: 20px;
}

.role-drawer {
  text-align: center;
}

.tree_menu {
  position: fixed;
  display: block;
  z-index: 20000;
  background-color: #fff;
  padding: 5px 0;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
}

.tree_menu .menuItem span {
  color: #000;
}

.tree_menu .el-menu-item,
.tree_menu /deep/ .el-submenu__title {
  height: 30px;
  line-height: 30px;
  padding: 0 10px !important;
}

.tree_menu .el-submenu /deep/ .el-submenu__icon-arrow {
  display: block
}

.tree_menu .el-menu--collapse {
  width: 74px;
  background-color: #fff;
}

.add-role {
  display: inline-block;
}

.add-role .el-button--primary {
  background-color: #89ef87;
  border-color: #89ef87;
}
</style>
