<template>
  <div class="app-container">
    <!--查询-->
    <div class="filter-container">
      <el-form ref="form" label-position="left" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="入库时间">
              <el-date-picker
                size="small"
                v-model="createTime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
                type="datetimerange"
                range-separator="~"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">

            <el-form-item label="地区">
              <el-cascader
                size="small"
                v-model="cascaderValue"
                style="width: 100%;"
                placeholder="多选任意"
                :options="cascaderOptions"
                :props="{ multiple: true, checkStrictly: true }"
                filterable
                clearable></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="6" class="col-salary">
            <el-form-item label="工资">
              <el-input
                size="small"
                placeholder="最小工资"
                suffix-icon="el-icon-minus"
                clearable
                v-model="listQuery.minSalary">
              </el-input>
              <el-input
                size="small"
                placeholder="最大工资"
                suffix-icon="el-icon-plus"
                clearable
                v-model="listQuery.maxSalary">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button class="filter-item" @click="handleFilter" size="small">
                查询
              </el-button>
              <el-button class="filter-item" style="margin-left: 10px;" @click="handleRest" size="small">
                重置
              </el-button>
              <el-link v-show="!filterShow" style="margin-left: 10px;font-size: small;" type="primary"
                       @click="filterShow=true" :underline="false">高级搜索
              </el-link>
              <el-link v-show="filterShow" style="margin-left: 10px;font-size: small;" type="primary"
                       @click="filterShow=false" :underline="false">高级搜索
              </el-link>
            </el-form-item>
          </el-col>
        </el-row>
        <div v-show="filterShow">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="姓名">
                <el-input size="small" placeholder="姓名/模糊查询" v-model="listQuery.name" clearable
                          @keyup.enter.native="handleFilter"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="手机号">
                <el-input size="small" placeholder="手机号/精确查询" v-model="listQuery.phone" clearable
                          @keyup.enter.native="handleFilter"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="公司">
                <el-input size="small" placeholder="公司名称/模糊查询" v-model="listQuery.company" clearable
                          @keyup.enter.native="handleFilter"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="地址">
                <el-input size="small" placeholder="地址名称/模糊查询" v-model="listQuery.address" clearable
                          @keyup.enter.native="handleFilter"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="字段三">
                <el-date-picker
                  size="small"
                  v-model="weekDate"
                  type="week"
                  format="yyyy 第 WW 周"
                  style="width: 100%"
                  placeholder="选择周">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="数据ID">
                <el-input size="small" placeholder="请输入数据ID" v-model="listQuery.fieldOne" clearable
                          @keyup.enter.native="handleFilter">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="月份">
                <el-date-picker
                  size="small"
                  v-model="monthDate"
                  type="monthrange"
                  align="right"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始月份"
                  end-placeholder="结束月份"
                  style="width: 100%"
                  :picker-options="monthDateOptions">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="数据状态">
                <el-select size="small" v-model="listQuery.status" filterable clearable placeholder="数据状态"
                           class="filter-item"
                           style="width: 100%;">
                  <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>
    <!--表格-->
    <el-table
      :data="list"
      style="width: 100%" :row-style="{height:'40px'}"
      :cell-style="{padding:'0px'}" v-loading="listLoading"
      element-loading-spinner="el-icon-loading">
      <el-table-column type="index" width="50" align="center"/>
      <el-table-column prop="fieldOne" label="生产ID" show-overflow-tooltip align="center" width="300"></el-table-column>
      <el-table-column prop="name" label="姓名" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="phone" label="手机号" show-overflow-tooltip align="center" width="150"></el-table-column>
      <el-table-column prop="salary" label="工资" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="company" label="公司" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="性别" align="center">
        <template slot-scope="{row}">
          <span v-for="item in sexOptions" v-if="row.sex===item.key">
            <el-tag v-if="item.key===1" size="medium" type="warning" style="color: #ff9c0a">{{ item.name }}</el-tag>
            <el-tag v-if="item.key===0" size="medium" style="color: #019dff">{{ item.name }}</el-tag>
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="地址" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="{row}">
          <span v-for="item in statusOptions" v-if="row.status===item.key"><el-tag size="medium" type="success">{{
              item.name
            }}</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column prop="fieldTwo" label="备用字段二" show-overflow-tooltip align="center" width="150"></el-table-column>
      <el-table-column prop="fieldThree" label="备用字段三" show-overflow-tooltip align="center"
                       width="150"></el-table-column>
      <el-table-column prop="fieldFour" label="备用字段四" show-overflow-tooltip align="center"
                       width="150"></el-table-column>
      <el-table-column prop="fieldFive" label="备用字段五" show-overflow-tooltip align="center"
                       width="150"></el-table-column>
      <el-table-column prop="fieldSix" label="备用字段六" show-overflow-tooltip align="center" width="150"></el-table-column>
      <el-table-column prop="fieldSeven" label="备用字段七" show-overflow-tooltip align="center"
                       width="150"></el-table-column>
      <el-table-column prop="fieldEight" label="备用字段八" show-overflow-tooltip align="center"
                       width="150"></el-table-column>
      <el-table-column prop="fieldNine" label="备用字段九" show-overflow-tooltip align="center"
                       width="150"></el-table-column>
      <el-table-column label="备用字段十" align="center" width="150"> show-overflow-tooltip>
        <template slot-scope="{row}">
          <span>{{ row.fieldTen | parseTime('{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="dataId" label="数据ID" show-overflow-tooltip align="center" width="300"></el-table-column>
      <el-table-column label="创建时间" align="center" width="150"> show-overflow-tooltip>
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createUser" label="创建者" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column label="更新时间" align="center" width="150"> show-overflow-tooltip>
        <template slot-scope="{row}">
          <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="updateUser" label="更新者" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="150" class-name="small-padding">
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
        <el-form-item label="状态">
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
            <el-form-item label="状态：">{{ temp.status }}</el-form-item>
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
import {getList, add, edit, deleteData} from '@/api/es/SysEsData'
import {addMsg, editMsg, delMsg} from "@/api/common/common";
import Pagination from '@/components/Pagination'
import {parseTime} from "@/utils"; // 分页

// 数据状态
const statusOptions = [
  {key: "0", name: '正常'},
  {key: "1", name: '失败'}
]
// 数据状态
const sexOptions = [
  {key: 0, name: '男'},
  {key: 1, name: '女'}
]

export default {
  name: 'SysEsDataTable',
  components: {Pagination},
  filters: {},
  data() {
    return {
      filterShow: false,
      list: null, //表格列表数据
      total: 0, // 总条数
      listLoading: true, // 列表加载圈
      listQuery: {
        currentPage: 1,
        pageSize: 10,
        name: undefined,
        phone: undefined,
        minSalary: undefined,
        maxSalary: undefined,
        company: undefined,
        sex: undefined,
        address: undefined,
        status: undefined,
        startTime: undefined,
        endTime: undefined,
        fieldOne: undefined,
        fieldThree: undefined
      },
      temp: {
        id: '',
        name: '',
        phone: '',
        salary: '',
        company: '',
        sex: '',
        address: '',
        status: '',
        createTime: '',
        createUser: '',
        updateTime: '',
        updateUser: '',
        isDelete: '',
        fieldOne: '',
        fieldTwo: '',
        fieldThree: '',
        fieldFour: '',
        fieldFive: '',
        fieldSix: '',
        fieldSeven: '',
        fieldEight: '',
        fieldNine: '',
        fieldTen: '',
      },
      dialogFormVisible: false, //控制新增页关闭
      dialogFormVisibleView: false, //控制新增页关闭
      dialogStatus: '', // 判断当前操作是新增还是修改
      textMap: {
        add: '新增',
        edit: '编辑'
      },
      createTime: undefined,
      createDate: undefined,
      monthDate: undefined,
      weekDate: undefined,
      startTimeDate: undefined,
      endTimeDate: undefined,
      statusOptions,
      sexOptions,
      rules: {},
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      monthDateOptions: {
        shortcuts: [{
          text: '本月',
          onClick(picker) {
            picker.$emit('pick', [new Date(), new Date()]);
          }
        }, {
          text: '今年至今',
          onClick(picker) {
            const end = new Date();
            const start = new Date(new Date().getFullYear(), 0);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近六个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setMonth(start.getMonth() - 6);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      cascaderValue: [],
      cascaderOptions: [{
        value: 1,
        label: '东南',
        children: [{
          value: 2,
          label: '上海',
          children: [
            {value: 3, label: '普陀'},
            {value: 4, label: '黄埔'},
            {value: 5, label: '徐汇'}
          ]
        }, {
          value: 7,
          label: '江苏',
          children: [
            {value: 8, label: '南京'},
            {value: 9, label: '苏州'},
            {value: 10, label: '无锡'}
          ]
        }, {
          value: 12,
          label: '浙江',
          children: [
            {value: 13, label: '杭州'},
            {value: 14, label: '宁波'},
            {value: 15, label: '嘉兴'}
          ]
        }]
      }, {
        value: 17,
        label: '西北',
        children: [{
          value: 18,
          label: '陕西',
          children: [
            {value: 19, label: '西安'},
            {value: 20, label: '延安'}
          ]
        }, {
          value: 21,
          label: '新疆维吾尔族自治区',
          children: [
            {value: 22, label: '乌鲁木齐'},
            {value: 23, label: '克拉玛依'}
          ]
        }]
      }]
    }
  },
  created() {
    this.defaultTime();
    this.getList();
  },
  methods: {
    handleChange(value) {
      console.log(value);
    },
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
      const createTime = this.createTime;
      if (createTime != null && createTime != '') {
        this.listQuery.startTime = createTime[0];
        this.listQuery.endTime = createTime[1]
      } else {
        this.$message({
          message: '入库时间不许为空哦',
          type: 'warning'
        });
        return;
      }
      this.getList()
    },
    /*条件重置*/
    handleRest() {
      this.listQuery = {
        currentPage: 1,
        pageSize: 10,
        name: undefined,
        phone: undefined,
        minSalary: undefined,
        maxSalary: undefined,
        company: undefined,
        sex: undefined,
        address: undefined,
        status: undefined,
        startTime: undefined,
        endTime: undefined
      }
      this.defaultTime();
    },
    /*表单重置*/
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        phone: '',
        salary: '',
        company: '',
        sex: '',
        address: '',
        status: '',
        createTime: '',
        createUser: '',
        updateTime: '',
        updateUser: '',
        isDelete: '',
        fieldOne: '',
        fieldTwo: '',
        fieldThree: '',
        fieldFour: '',
        fieldFive: '',
        fieldSix: '',
        fieldSeven: '',
        fieldEight: '',
        fieldNine: '',
        fieldTen: '',
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
    },
    defaultTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.createTime = [parseTime(start, '{y}-{m}-{d} {h}:{i}:{s}'), parseTime(end, '{y}-{m}-{d} {h}:{i}:{s}')];
      this.listQuery.startTime = this.createTime[0];
      this.listQuery.endTime = this.createTime[1];
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

.filter-container .el-form-item {
  margin-bottom: 10px;
}

.col-salary .el-form-item__content {
  display: flex;
}
</style>
