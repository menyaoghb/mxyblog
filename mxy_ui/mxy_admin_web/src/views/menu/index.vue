<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <div class="app-container">
        <el-divider content-position="center">菜单结构</el-divider>
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText" clearable>
        </el-input>
        <el-tree
          class="menu-tree"
          :data="data"
          node-key="id"
          ref="tree"
          empty-text=" "
          icon-class="el-icon-loading"
          highlight-current
          :filter-node-method="filterNode"
          :props="defaultProps"
          v-loading="listLoading"
          element-loading-spinner="el-icon-loading">
        </el-tree>
      </div>
    </el-col>
    <el-col :span="18">
      <div class="app-container">
        <!--查询-->
        <div class="filter-container">
          <el-input v-model="listQuery.menuName" prefix-icon="el-icon-search" placeholder="菜单名称" style="width: 200px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable/>
          <el-input v-model="listQuery.menuType" prefix-icon="el-icon-search" placeholder="菜单类型"
                    style="width: 200px;margin-left: 5px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable/>
          <el-button class="filter-item" style="margin-left: 10px;" icon="el-icon-search" @click="handleFilter" round>
            查询
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="plain" icon="el-icon-plus"
                     @click="handleCreate" round>
            新增菜单
          </el-button>
        </div>
        <!--表格-->
        <el-table
          :data="list"
          style="width: 100%" :row-style="{height:'40px'}"
          :show-overflow-tooltip="true"
          :cell-style="{padding:'0px'}" v-loading="listLoading" element-loading-spinner="el-icon-loading">
          <el-table-column type="index" width="50" align="center"/>
          <el-table-column prop="menuName" label="菜单名称" align="center"></el-table-column>
          <!--<el-table-column prop="parentId" label="父菜单ID" align="center"></el-table-column>-->
          <el-table-column prop="path" show-overflow-tooltip label="路由地址" align="center"></el-table-column>
          <el-table-column prop="component" show-overflow-tooltip label="组件路径" align="center"></el-table-column>
          <el-table-column prop="perms" show-overflow-tooltip label="权限标识" align="center"></el-table-column>
          <el-table-column label="菜单类型" align="center">
            <template slot-scope="{row}">
              <span v-for="item in userTypeOptions" v-if="row.menuType===item.key">{{ item.name }}</span>
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
            <el-form-item label="菜单名称">
              <el-input v-model="temp.menuName"/>
            </el-form-item>
            <el-form-item label="父菜单ID">
              <el-input v-model="temp.parentId"/>
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
            <el-form-item label="菜单图标">
              <el-input v-model="temp.icon"/>
            </el-form-item>
            <el-form-item label="菜单类型">
              <el-select v-model="temp.menuType" class="filter-item" style="width: 100%;">
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
  import {getSysMenuList, addMenu, editMenu, deleteMenu, getTreeData} from '@/api/menu/menu'
  import Pagination from '@/components/Pagination' // 分页

  // 权限
  const userTypeOptions = [
    {key: '01', name: '目录'},
    {key: '02', name: '菜单'},
    {key: '03', name: '按钮'}
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
        data: [],
        filterText: '',
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        list: null, //表格列表数据
        total: 0, // 总条数
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 10,
          menuName: undefined,
          menuType: undefined
        },
        userTypeOptions, // 用户权限
        statusOptions, // 用户状态
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
        rules: {}
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    created() {
      this.getList();
      this.getTreeData();
    },
    methods: {
      /*列表查询*/
      getList() {
        this.listLoading = true
        getSysMenuList(this.listQuery).then(response => {
          this.list = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
      },
      /*菜单树数据*/
      getTreeData() {
        this.listLoading = true
        getTreeData({roleId: ''}).then(response => {
          this.data = response.data
          this.listLoading = false
        })
      },
      loadNode(node, resolve) {
        if (node.level === 0) {
          return resolve([{ name: 'region' }]);
        }
        if (node.level > 1) return resolve([]);

        setTimeout(() => {
          const data = [{
            name: 'leaf',
            leaf: true
          }, {
            name: 'zone'
          }];

          resolve(data);
        }, 500);
      },
      filterNode(value, data) {
        debugger
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      /*条件查询*/
      handleFilter() {
        this.listQuery.currentPage = 1
        this.getList()
      },
      /*用户状态改变*/
      handleModifyStatus(row) {
        let text = row.status === "1" ? "启用" : "禁用";
        this.$confirm('确认要"' + text + '""' + row.menuName + '"这个菜单吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let param = {};
          param.menuId = row.menuId;
          param.status = row.status === "1" ? "0" : "1";
          editMenu(param).then(() => {
            this.$message({
              message: text + '成功',
              type: 'success',
              center: true
            });
            this.getList();
          })
        }).catch(() => {
        });
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
            addMenu(this.temp).then(() => {
              this.$message({
                message: '新增成功',
                type: 'success',
                center: true
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
            editMenu(this.temp).then(() => {
              this.$message({
                message: '修改成功',
                type: 'success',
                center: true
              });
              this.dialogFormVisible = false
              this.getList();
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
  .menu-tree{
    margin-top: 15px;
  }
</style>
