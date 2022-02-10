<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-input v-model="listQuery.title" prefix-icon="el-icon-search" placeholder="标题" style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-button class="filter-item" style="margin-left: 10px;" icon="el-icon-search" @click="handleFilter" round>
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="plain" icon="el-icon-plus"
                 @click="handleCreate" round>
        新增
      </el-button>
    </div>
    <!--表格-->
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading">
      <el-table-column type="expand">
        <template slot-scope="{row}">
          <el-form label-position="left" inline class="word-table-expand">
            <el-form-item label="标题">
              <span>{{ row.title }}</span>
            </el-form-item>
            <el-form-item label="简介">
              <span>{{ row.summary }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="summary" label="简介" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="pageViews" label="浏览量" align="center"></el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
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
      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row,$index}">
          <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
          <el-button size="mini" @click="handleView(row)" type="text">详情</el-button>
          <el-popconfirm confirm-button-text='好的' cancel-button-text='不用了' icon="el-icon-info"
                         icon-color="red" title="确定删除吗？" @onConfirm="handleDelete(row)">
            <el-button slot="reference" size="mini" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                @pagination="getList"/>

  </div>
</template>

<script>
  import {getList, deleteArticle, editArticle, addArticle} from '@/api/web/blog/blog'
  import waves from '@/directive/waves' // waves directive
  import Pagination from '@/components/Pagination' // 分页
  import {validateEmail, validateAccount, validatePassword} from '@/utils/validate'
  import {deleteUser} from "@/api/blog/beautifulWords/beautifulWords";

  // 状态
  const statusOptions = [
    {key: '0', name: '已发布'},
    {key: '1', name: '未发布'}
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
          title: undefined
        },
        statusOptions, // 用户状态
        temp: {
          id: undefined,
          title: '',
          status: '0'
        },
        dialogFormVisible: false, //控制新增页关闭
        dialogFormVisibleView: false, //控制新增页关闭
        dialogStatus: '', // 判断当前操作是新增还是修改
        textMap: {
          add: '新增',
          edit: '编辑'
        }
      }
    },
    created() {
      this.getList()
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
      /*用户状态改变*/
      handleModifyStatus(row) {
        let text = row.status === "0" ? "发布" : "下架";
        this.$confirm('确认要"' + text + '"这个博客吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.temp.id = row.id;
          let param = {};
          param.id = row.id;
          param.status = row.status;
          editArticle(param).then(() => {
            this.$message({
              message: text + '成功',
              type: 'success'
            });
            this.getList();
          })
        }).catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      /*表单重置*/
      resetTemp() {
        this.temp = {
          id: undefined,
          title: '',
          status: '0'
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
            addArticle(this.temp).then(() => {
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
            editArticle(this.temp).then(() => {
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
        this.temp.id = row.id;
        deleteArticle(this.temp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
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
</style>
