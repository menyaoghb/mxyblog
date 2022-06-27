<template>
  <el-row :gutter="20">
    <el-col :span="24">
      <div class="app-container">
        <!--查询-->
        <div class="filter-container">
          <el-row :gutter="20">
            <el-col :span="4">
              <el-input placeholder="请输入名称" v-model="listQuery.menuName" clearable @keyup.enter.native="handleFilter">
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
        <div id="privilege-manager">
          <!--表格-->
          <el-row>
            <el-table :data="tableData" style="width: 100%;" row-key="menuId"
                      :tree-props="{children: 'children', hasChildren: 'hasChildren'}" v-loading="listLoading"
                      element-loading-spinner="el-icon-loading">
              <el-table-column label="名称">
                <template slot-scope="{row}">
                  <a style="color: #007aff" @click="handleUpdate(row)">{{ row.menuName }}</a>
                </template>
              </el-table-column>
              <el-table-column prop="menuType" label="类别" :formatter="formatPrivilegeType"></el-table-column>
              <el-table-column prop="component" label="路径"></el-table-column>
              <el-table-column prop="orderNum" label="排序"></el-table-column>
              <el-table-column prop="perms" label="权限"></el-table-column>
              <el-table-column label="操作" min-width="80" align="center">
                <template slot-scope="{row,$index}">
                  <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
                  <el-button size="mini" @click="handleDelete(row)" type="text">删除</el-button>
                  <el-button size="mini" @click="handleCreate(row)" type="text" v-if="row.menuType!== '3'">新增子级
                  </el-button>
                  <el-button size="mini" @click="handleCreate(row)" type="text">新增同级</el-button>
                </template>
              </el-table-column>
            </el-table>
            <br>
          </el-row>
        </div>
        <!--新增/修改页-->
        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
          <el-row :gutter="20">
            <el-col :span="18">
              <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px"
                       style="width: 400px; margin-left:160px;">
                <el-form-item label="名称">
                  <el-input v-model="temp.menuName"/>
                </el-form-item>
                <el-form-item label="父级">
                  <el-input v-model="parentName"/>
                </el-form-item>
                <el-form-item label="路由地址">
                  <el-input v-model="temp.path"/>
                </el-form-item>
                <el-form-item label="组件路径">
                  <el-input v-model="temp.component"/>
                </el-form-item>
                <el-form-item label="权限标识">
                  <el-input v-model="temp.perms"/>
                </el-form-item>
                <el-form-item label="排序">
                  <el-input v-model="temp.orderNum"/>
                </el-form-item>
                <el-form-item label="菜单图标">
                  <el-input v-model="temp.icon"/>
                </el-form-item>
                <el-form-item label="菜单类型">
                  <el-select v-model="temp.menuType" class="filter-item" style="width: 100%;">
                    <el-option v-for="item in typeOptions" :key="item.key" :label="item.name" :value="item.key"/>
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
            </el-col>
            <el-col :span="6">
              <el-tree
                class="menu-tree"
                :data="data"
                node-key="id"
                ref="tree"
                empty-text=" "
                icon-class="el-icon-loading"
                :highlight-current="true"
                :props="defaultProps"
                :default-expanded-keys="defaulKeys"
                @node-click="handleNodeClick"
                v-loading="listLoading"
                element-loading-spinner="el-icon-loading">
              </el-tree>
            </el-col>
          </el-row>

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
import {getTreeTableData, addMenu, editMenu, deleteMenu, getTreeData} from '@/api/sys/menu/menu'

// 权限
const typeOptions = [
  {key: '1', name: '目录'},
  {key: '2', name: '菜单'},
  {key: '3', name: '按钮'}
]
// 状态
const statusOptions = [
  {key: '0', name: '启用'},
  {key: '1', name: '禁用'}
]

export default {
  name: 'ComplexTable',
  filters: {},
  data() {
    return {
      tableData: [],
      listLoading: true,
      listQuery: {
        menuName: undefined
      },
      typeOptions, // 权限
      statusOptions, // 状态
      temp: {
        menuId: undefined,
        menuName: '',
        parentId: '',
        orderNum: '',
        path: '',
        component: '',
        isFrame: '',
        isCache: '',
        menuType: '',
        visible: '',
        perms: '',
        icon: '',
        status: '0',
        remark: ''
      },
      dialogFormVisible: false, //控制新增页关闭
      dialogStatus: '', // 判断当前操作是新增还是修改
      textMap: {
        add: '新增',
        edit: '编辑'
      },
      rules: {},

      data: [],
      defaulKeys: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      parentName: ""
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.listLoading = true
      getTreeTableData({menuName: ""}).then(response => {
        this.tableData = response.data.tableData;
        this.listLoading = false
      })
      this.getTreeData();
    },
    getTreeData() {
      this.listLoading = true
      getTreeData({roleId: ''}).then(response => {
        this.data = response.data
        this.listLoading = false
      })
    },
    // 权限类别转换
    formatPrivilegeType: function (row, column) {
      if (row.menuType === '1') {
        return '目录'
      } else if (row.menuType === '2') {
        return '菜单'
      } else if (row.menuType === '3') {
        return '按钮'
      } else {
        return ''
      }
    },
    /*条件查询*/
    handleFilter() {
      this.init()
    },
    /*表单重置*/
    resetTemp() {
      this.temp = {
        menuId: undefined,
        menuName: '',
        parentId: '',
        orderNum: '',
        path: '',
        component: '',
        isFrame: '',
        isCache: '',
        menuType: '',
        visible: '',
        perms: '',
        icon: '',
        status: '0',
        remark: ''
      }
    },
    handleRest() {
      this.listQuery.menuName = "";
    },
    /*新增跳转*/
    handleCreate() {
      this.resetTemp();
      this.parentName = "";
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
          addMenu(this.temp).then(() => {
            this.$message({
              message: '新增成功',
              type: 'success',
              center: true
            });
            this.dialogFormVisible = false
            this.init();
          })
        }
      })
    },
    /*修改跳转*/
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      if (this.temp.parentId === "0") {
        this.parentName = "YFTest";
      } else {
        this.defaulKeys = [this.temp.parentId];
        this.$nextTick(() => {
          this.$refs["tree"].setCurrentKey(this.temp.parentId);
        });
        this.$nextTick(() => {
          let node = this.$refs.tree.getCurrentNode();
          this.parentName = node.label;
        });
      }
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
          editMenu(this.temp).then(() => {
            this.$message({
              message: '修改成功',
              type: 'success',
              center: true
            });
            this.dialogFormVisible = false
            this.init();
          })
        }
      })
    },
    /*数据删除*/
    handleDelete(row) {
      this.$confirm('是否确认删除"' + row.menuName + '"的数据?', "警告", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.temp.menuId = row.menuId;
        deleteMenu(this.temp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success',
            center: true
          });
          this.dialogFormVisible = false
          this.init();
        })
      }).catch(() => {
      });
    },
    /*树点击事件*/
    handleNodeClick(data) {
      this.parentName = data.label;
      this.temp.parentId = data.id;
    }
  }
}
</script>
<style>
/*新增页按钮居中--（写法暂定）*/
.dialog-footer {
  text-align: center;
}

.filter-container {
  margin-bottom: 18px;
}
</style>
