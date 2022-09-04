<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-input v-model="listQuery.beautifulWords" prefix-icon="el-icon-search" placeholder="佳句" style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-autocomplete
        class="filter-item"
        v-model="listQuery.inspiration"
        :fetch-suggestions="querySearchIns"
        placeholder="请输入灵感"
        style="margin-left: 10px;" prefix-icon="el-icon-search"
        @select="handleSelect"
        @keyup.enter.native="handleFilter" clearable
      ></el-autocomplete>
      <el-autocomplete
        class="filter-item"
        v-model="listQuery.label"
        :fetch-suggestions="querySearchLab"
        placeholder="请输入标签"
        style="margin-left: 10px;" prefix-icon="el-icon-search"
        @select="handleSelect"
        @keyup.enter.native="handleFilter" clearable
      ></el-autocomplete>
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
            <el-form-item label="标签">
              <span>{{ row.label }}</span>
            </el-form-item>
            <el-form-item label="灵感">
              <span>{{ row.inspiration }}</span>
            </el-form-item>
            <el-form-item label="佳句">
              <span>{{ row.beautifulWords }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="beautifulWords" label="佳句" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="灵感" align="center" width="100">
        <template slot-scope="{row}">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium" class="ins_tag">{{ row.inspiration }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" width="100">
        <template slot-scope="{row}">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium" class="lab_tag">{{ row.label }}</el-tag>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row,$index}">
          <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
          <el-popconfirm confirm-button-text='好的' cancel-button-text='不用了' icon="el-icon-info"
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
      >
        <el-form-item label="灵感">
          <el-autocomplete
            class="inline-input"
            v-model="temp.inspiration"
            :fetch-suggestions="querySearchIns"
            placeholder="请输入灵感"
            @select="handleSelect"
            clearable
          ></el-autocomplete>
          <div class="tag-group">
            <el-tag
              v-for="item in itemsIns"
              :key="item.value"
              type=""
              effect="plain">
              {{ item.value }}
            </el-tag>
          </div>
        </el-form-item>
        <el-form-item label="标签">
          <el-autocomplete
            class="inline-input"
            v-model="temp.label"
            :fetch-suggestions="querySearchLab"
            placeholder="请输入标签"
            @select="handleSelect"
            clearable
          ></el-autocomplete>
          <div class="tag-group">
            <el-tag
              v-for="item in itemsLab"
              :key="item.value"
              type="success"
              effect="plain">
              {{ item.value }}
            </el-tag>
          </div>
        </el-form-item>
        <el-form-item label="佳句" prop="beautifulWords">
          <el-input class="inline-input" v-model="temp.beautifulWords" autosize type="textarea"/>
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
  import {getList, getAdviceList, addUser, editUser, deleteUser} from '@/api/blog/beautifulWords/beautifulWords'
  import Pagination from '@/components/Pagination' // 分页
  import {validateEmail, validateAccount, validatePassword} from '@/utils/validate'

  export default {
    name: 'ComplexTable',
    components: {Pagination},
    data() {
      return {
        restaurantsIns: [],// 输入建议-灵感
        restaurantsLab: [],// 输入建议-标签
        itemsIns: [
          {type: '', label: '标签一'},
          {type: 'success', label: '标签二'},
          {type: 'info', label: '标签三'},
          {type: 'danger', label: '标签四'},
          {type: 'warning', label: '标签五'}
        ],
        itemsLab: [
          {type: '', label: '标签一'},
          {type: 'success', label: '标签二'},
          {type: 'info', label: '标签三'},
          {type: 'danger', label: '标签四'},
          {type: 'warning', label: '标签五'}
        ],
        list: null, //表格列表数据
        total: 0, // 总条数
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 10,
          beautifulWords: undefined,
          label: undefined,
          inspiration: undefined
        },
        temp: {
          id: undefined,
          inspiration: '',
          label: '',
          beautifulWords: '',
          type: '',
          status: '',
          createTime: '',
          updateTime: '',
          updateUser: '',
          createUser: '',
          remark: ''
        },
        dialogFormVisible: false, //控制新增页关闭
        dialogFormVisibleView: false, //控制详情页关闭
        dialogStatus: '', // 判断当前操作是新增还是修改
        textMap: {
          add: '新增',
          edit: '编辑'
        },
        rules: {
          type: [
            {type: 'array', required: true, message: '请至少选择一个类型', trigger: 'change'}
          ],
          beautifulWords: [
            {required: true, message: '请输入优美的句子', trigger: 'blur'}
          ]
        }
      }
    },
    created() {
      this.getList();
      this.getAdviceList();
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
      /*输入建议查询*/
      getAdviceList() {
        getAdviceList(this.temp).then(response => {
          this.restaurantsIns = response.data.insMap
          this.restaurantsLab = response.data.labMap

          this.itemsIns = response.data.insMap
          this.itemsLab = response.data.labMap

        })
      },
      /*条件查询*/
      handleFilter() {
        this.listQuery.currentPage = 1
        this.getList();
        this.getAdviceList();
      },
      /*用户状态改变*/
      handleModifyStatus(row) {

        let text = row.status === "0" ? "启用" : "禁用";
        this.$confirm('确认要"' + text + '""' + row.userName + '"这个用户吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.temp.userId = row.userId;
          let param = {};
          param.userId = row.userId;
          param.status = row.status;
          editUser(param).then(() => {
            this.$message({
              message: text + '成功',
              type: 'success'
            });
            this.getList();
            this.getAdviceList();
          })
        }).catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      /*表单重置*/
      resetTemp() {
        this.temp = {
          id: undefined,
          inspiration: '',
          label: '',
          beautifulWords: ''
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
              this.getAdviceList();
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
              this.getAdviceList();
            })
          }
        })
      },
      /*数据删除*/
      handleDelete(row) {
        this.temp.id = row.id;
        deleteUser(this.temp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getList();
          this.getAdviceList();
        })
      },
      /*数据详情*/
      handleView(row) {
        this.dialogFormVisibleView = true;
        this.temp = row;
      },
      /*输入建议*/
      querySearchIns(queryString, cb) {
        const restaurants = this.restaurantsIns;
        const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      querySearchLab(queryString, cb) {
        const restaurants = this.restaurantsLab;
        const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item);
      }
    }
  }
</script>
<style>
  /*新增页按钮居中--（写法暂定）*/
  .dialog-footer {
    text-align: center;
  }

  .word-table-expand {
    font-size: 0;
  }

  .word-table-expand label {
    width: 40px;
    color: #99a9bf;
  }

  .word-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }

  .tag-group {
    display: inline;
    margin-left: 10px;
  }

  .tag-group span {
    margin-right: 10px;
  }

  .lab_tag{
    border-color: #a1ebc2;
    color: #13ce66;
  }

  .ins_tag{
    border-color: #a3d3ff;
    color: #1890ff;
  }
</style>
