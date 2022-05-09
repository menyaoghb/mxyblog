<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输入名称" v-model="listQuery.nickName" clearable @keyup.enter.native="handleFilter">
            <template slot="prepend">姓名</template>
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
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading"
      element-loading-spinner="el-icon-loading">
      <el-table-column type="index" width="50" align="center"/>
  <el-table-column prop="name" label="姓名" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="phone" label="手机号" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="salary" label="工资" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="company" label="公司" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="sex" label="性别" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="address" label="地址" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="status" label="状态（0正常 1停用）" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="createUser" label="创建者" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="updateUser" label="更新者" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="isDelete" label="是否删除" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldOne" label="备用字段一" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldTwo" label="备用字段二" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldThree" label="备用字段三" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldFour" label="备用字段四" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldFive" label="备用字段五" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldSix" label="备用字段六" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldSeven" label="备用字段七" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldEight" label="备用字段八" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldNine" label="备用字段九" show-overflow-tooltip align="center"></el-table-column>
  <el-table-column prop="fieldTen" label="备用字段十" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding">
        <template slot-scope="{row}">
          <el-button size="mini" @click="handleUpdate(row)" type="text">编辑</el-button>
          <el-button size="mini" @click="handleView(row)" type="text">详情</el-button>
          <el-popconfirm confirm-button-text='删除' cancel-button-text='取消' icon="el-icon-info"
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
               style="width: 100%; margin-left:0px;">
  <el-form-item label="姓名">
  <el-input v-model="temp.name"/>
  </el-form-item>
  <el-form-item label="手机号">
  <el-input v-model="temp.phone"/>
  </el-form-item>
  <el-form-item label="工资">
  <el-input v-model="temp.salary"/>
  </el-form-item>
  <el-form-item label="公司">
  <el-input v-model="temp.company"/>
  </el-form-item>
  <el-form-item label="性别">
  <el-input v-model="temp.sex"/>
  </el-form-item>
  <el-form-item label="地址">
  <el-input v-model="temp.address"/>
  </el-form-item>
  <el-form-item label="状态（0正常 1停用）">
  <el-input v-model="temp.status"/>
  </el-form-item>
  <el-form-item label="创建时间">
  <el-input v-model="temp.createTime"/>
  </el-form-item>
  <el-form-item label="创建者">
  <el-input v-model="temp.createUser"/>
  </el-form-item>
  <el-form-item label="更新时间">
  <el-input v-model="temp.updateTime"/>
  </el-form-item>
  <el-form-item label="更新者">
  <el-input v-model="temp.updateUser"/>
  </el-form-item>
  <el-form-item label="是否删除">
  <el-input v-model="temp.isDelete"/>
  </el-form-item>
  <el-form-item label="备用字段一">
  <el-input v-model="temp.fieldOne"/>
  </el-form-item>
  <el-form-item label="备用字段二">
  <el-input v-model="temp.fieldTwo"/>
  </el-form-item>
  <el-form-item label="备用字段三">
  <el-input v-model="temp.fieldThree"/>
  </el-form-item>
  <el-form-item label="备用字段四">
  <el-input v-model="temp.fieldFour"/>
  </el-form-item>
  <el-form-item label="备用字段五">
  <el-input v-model="temp.fieldFive"/>
  </el-form-item>
  <el-form-item label="备用字段六">
  <el-input v-model="temp.fieldSix"/>
  </el-form-item>
  <el-form-item label="备用字段七">
  <el-input v-model="temp.fieldSeven"/>
  </el-form-item>
  <el-form-item label="备用字段八">
  <el-input v-model="temp.fieldEight"/>
  </el-form-item>
  <el-form-item label="备用字段九">
  <el-input v-model="temp.fieldNine"/>
  </el-form-item>
  <el-form-item label="备用字段十">
  <el-input v-model="temp.fieldTen"/>
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
  <el-form-item label="姓名：">{{ temp.name }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="手机号：">{{ temp.phone }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="工资：">{{ temp.salary }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="公司：">{{ temp.company }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="性别：">{{ temp.sex }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="地址：">{{ temp.address }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="状态（0正常 1停用）：">{{ temp.status }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="创建时间：">{{ temp.createTime }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="创建者：">{{ temp.createUser }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="更新时间：">{{ temp.updateTime }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="更新者：">{{ temp.updateUser }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="是否删除：">{{ temp.isDelete }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段一：">{{ temp.fieldOne }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段二：">{{ temp.fieldTwo }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段三：">{{ temp.fieldThree }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段四：">{{ temp.fieldFour }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段五：">{{ temp.fieldFive }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段六：">{{ temp.fieldSix }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段七：">{{ temp.fieldSeven }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段八：">{{ temp.fieldEight }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段九：">{{ temp.fieldNine }}</el-form-item>
  </el-col>
  <el-col :span="12">
  <el-form-item label="备用字段十：">{{ temp.fieldTen }}</el-form-item>
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
import {getList,add,edit,deleteData} from '@/api/sys/user/user'
import {addMsg,editMsg,delMsg} from "@/api/common/common";
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'SysEsDataTable',
  components: {Pagination},
  filters: {},
  data() {
    return {
      list: null, //表格列表数据
      total: 0, // 总条数
      listLoading: true, // 列表加载圈
      listQuery: {
        currentPage: 1,
        pageSize: 10
      },
      temp: {
    id:'',
    name:'',
    phone:'',
    salary:'',
    company:'',
    sex:'',
    address:'',
    status:'',
    createTime:'',
    createUser:'',
    updateTime:'',
    updateUser:'',
    isDelete:'',
    fieldOne:'',
    fieldTwo:'',
    fieldThree:'',
    fieldFour:'',
    fieldFive:'',
    fieldSix:'',
    fieldSeven:'',
    fieldEight:'',
    fieldNine:'',
    fieldTen:'',
      },
      dialogFormVisible: false, //控制新增页关闭
      dialogFormVisibleView: false, //控制新增页关闭
      dialogStatus: '', // 判断当前操作是新增还是修改
      textMap: {
        add: '新增',
        edit: '编辑'
      },
      rules: {}
    }
  },
  created() {
    this.getList();
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
    /*条件重置*/
    handleRest() {},
    /*表单重置*/
    resetTemp() {
      this.temp = {
      id:'',
      name:'',
      phone:'',
      salary:'',
      company:'',
      sex:'',
      address:'',
      status:'',
      createTime:'',
      createUser:'',
      updateTime:'',
      updateUser:'',
      isDelete:'',
      fieldOne:'',
      fieldTwo:'',
      fieldThree:'',
      fieldFour:'',
      fieldFive:'',
      fieldSix:'',
      fieldSeven:'',
      fieldEight:'',
      fieldNine:'',
      fieldTen:'',
      }
    },
    /*新增跳转*/
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'add'
      this.dialogFormVisible = true
    },
    /*新增提交*/
    createData() {
      add(this.temp).then(() => {
        addMsg();
        this.dialogFormVisible = false
        this.getList();
      })
    },
    /*修改跳转*/
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'edit'
      this.dialogFormVisible = true
    },
    /*修改提交*/
    updateData() {
      edit(this.temp).then(() => {
        editMsg();
        this.dialogFormVisible = false
        this.getList();
      })
    },
    /*数据删除*/
    handleDelete(row) {
     this.temp.id = row.id;
      deleteData(this.temp).then(() => {
        delMsg();
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

.el-dialog__body {
  padding: 10px 10px;
}

.filter-container {
  margin-bottom: 18px;
}
</style>