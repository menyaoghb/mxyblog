<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-input v-model="listQuery.nickName" prefix-icon="el-icon-search" placeholder="姓名" style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-input v-model="listQuery.username" prefix-icon="el-icon-search" placeholder="账号"
                style="width: 200px;margin-left: 5px;"
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
      <el-table-column type="index" width="50" align="center"/>
      <el-table-column prop="nickName" label="姓名" align="center"></el-table-column>
      <el-table-column prop="username" label="账号" align="center"></el-table-column>
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
          <el-button size="mini" @click="handleDelete(row)" type="text">删除</el-button>
          <el-button size="mini" @click="handleUpdate(row)" type="text">重置密码</el-button>
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
        <el-form-item label="姓名" prop="nickName">
          <el-input v-model="temp.nickName"/>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="temp.username"/>
        </el-form-item>
        <el-form-item label="密码" style="margin-bottom: 30px;" v-show="dialogStatus === 'add'">
          <el-input v-model="temp.password"/>
        </el-form-item>
        <el-form-item label="角色">
          <el-radio-group v-model="temp.userType">
            <el-radio v-for="item in userTypeOptions" :key="item.key" :label="item.key" class="el-radio-role">{{ item.name }}</el-radio>
          </el-radio-group>
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

    <!-- 详情 -->
    <el-dialog title="详情" :visible.sync="dialogFormVisibleView" width="700px" append-to-body>
      <el-form ref="dataForm" :model="temp" label-width="120px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名：">{{ temp.nickName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号：">{{ temp.username }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="权限：" v-for="item in userTypeOptions" v-if="temp.userType===item.key">{{ item.name }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码：">{{ temp.phoneNumber }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱：">{{ temp.email }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别：" v-for="item in sexOptions" v-if="temp.sex===item.key">{{ item.name }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号状态：" v-for="item in statusOptions" v-if="temp.status===item.key">{{ item.name }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最后登录IP：">{{ temp.loginIp }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最后登录时间：">{{ temp.loginDate | parseTime('{y}-{m}-{d} {h}:{i}')}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间：">{{ temp.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间：">{{ temp.updateTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注：">
              <div>{{ temp.remark }}</div>
            </el-form-item>
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
  import {getSysUserList, addUser, editUser, deleteUser,getRoles} from '@/api/user/user'
  import Pagination from '@/components/Pagination' // 分页
  import {validateEmail, validateAccount, validatePassword} from '@/utils/validate'

  // 权限
  const userTypeOptions = []
  // 状态
  const statusOptions = [
    {key: '0', name: '启用'},
    {key: '1', name: '禁用'}
  ]
  // 性别
  const sexOptions = [
    {key: '0', name: '男'},
    {key: '1', name: '女'},
    {key: '2', name: '未知'}
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
          nickName: undefined,
          username: undefined
        },
        userTypeOptions, // 用户权限
        statusOptions, // 用户状态
        sexOptions, // 用户性别
        temp: {
          userId: undefined,
          nickName: '',
          username: '',
          password: '',
          email: '',
          phoneNumber: '',
          userType: '',
          sex: '',
          loginIp: '',
          loginDate: '',
          createTime: '',
          updateTime: '',
          status: '0',
          remark: ''
        },
        dialogFormVisible: false, //控制新增页关闭
        dialogFormVisibleView: false, //控制新增页关闭
        dialogStatus: '', // 判断当前操作是新增还是修改
        textMap: {
          add: '新增',
          edit: '编辑'
        },
        rules: {
          nickName: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 2, max: 6, message: '长度在 2 到 6 个字符', trigger: 'blur'}
          ],
          username: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            {validator: validateAccount, trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {validator: validatePassword, trigger: 'blur'}
          ],
          email: [
            {required: true, message: '请输入邮箱', trigger: 'blur'},
            {validator: validateEmail, trigger: 'blur'}
          ],
          date1: [
            {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
          ],
          date2: [
            {type: 'date', required: true, message: '请选择时间', trigger: 'change'}
          ],
          type: [
            {type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change'}
          ],
          userType: [
            {required: true, message: '请选择用户权限', trigger: 'change'}
          ],
          desc: [
            {required: true, message: '请填写活动形式', trigger: 'blur'}
          ]
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
        getSysUserList(this.listQuery).then(response => {
          this.list = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
      },
      /*角色查询*/
      getRoleList() {
        getRoles().then(response => {
          let roles = response.data;
          this.userTypeOptions = [];
          for (const role of roles) {
            this.userTypeOptions.push({key: role.roleId, name: role.roleName})
          }
        })
      },
      /*条件查询*/
      handleFilter() {
        this.listQuery.currentPage = 1
        this.getList()
      },
      /*用户状态改变*/
      handleModifyStatus(row) {
        debugger
        let text = row.status === "0" ? "启用" : "禁用";
        this.$confirm('确认要"' + text + '""' + row.username + '"这个用户吗?', "警告", {
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
          })
        }).catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      /*表单重置*/
      resetTemp() {
        this.temp = {
          userId: undefined,
          username: '',
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
        this.getRoleList()
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
        this.getRoleList()
        this.temp = Object.assign({}, row) // copy obj
        this.temp.password = ''
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
        this.$confirm('是否确认删除用户账号为"' + row.username + '"的数据?', "警告", {
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
  .el-radio-role{
    margin-bottom: 10px;
  }
</style>
