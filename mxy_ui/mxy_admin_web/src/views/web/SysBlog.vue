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
      <el-table-column prop="title" label="标题" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="Introduction" label="简介" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="content" label="内容" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="url" label="图片url" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="urlList" label="图片url（多图片JSON）" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="time" label="发布时间" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="source" label="来源（0原创 1转载）" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="showType" label="博客展示类型（1 2 3）" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="type" label="博客类型（1技术博客 2日常博客）" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="status" label="状态（0上线 1下线）" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="isDelete" label="是否删除（0未删除 1已删除）" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="createUser" label="创建者" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="updateUser" label="更新者" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip align="center"></el-table-column>
      <el-table-column prop="remark" label="备注" show-overflow-tooltip align="center"></el-table-column>
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
        <el-form-item label="标题">
          <el-input v-model="temp.title"/>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="temp.Introduction"/>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="temp.content"/>
        </el-form-item>
        <el-form-item label="图片url">
          <el-input v-model="temp.url"/>
        </el-form-item>
        <el-form-item label="图片url（多图片JSON）">
          <el-input v-model="temp.urlList"/>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-input v-model="temp.time"/>
        </el-form-item>
        <el-form-item label="来源（0原创 1转载）">
          <el-input v-model="temp.source"/>
        </el-form-item>
        <el-form-item label="博客展示类型（1 2 3）">
          <el-input v-model="temp.showType"/>
        </el-form-item>
        <el-form-item label="博客类型（1技术博客 2日常博客）">
          <el-input v-model="temp.type"/>
        </el-form-item>
        <el-form-item label="状态（0上线 1下线）">
          <el-input v-model="temp.status"/>
        </el-form-item>
        <el-form-item label="是否删除（0未删除 1已删除）">
          <el-input v-model="temp.isDelete"/>
        </el-form-item>
        <el-form-item label="创建者">
          <el-input v-model="temp.createUser"/>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="temp.createTime"/>
        </el-form-item>
        <el-form-item label="更新者">
          <el-input v-model="temp.updateUser"/>
        </el-form-item>
        <el-form-item label="更新时间">
          <el-input v-model="temp.updateTime"/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="temp.remark"/>
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
            <el-form-item label="标题：">{{ temp.title }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="简介：">{{ temp.Introduction }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="内容：">{{ temp.content }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图片url：">{{ temp.url }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图片url（多图片JSON）：">{{ temp.urlList }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布时间：">{{ temp.time }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="来源（0原创 1转载）：">{{ temp.source }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="博客展示类型（1 2 3）：">{{ temp.showType }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="博客类型（1技术博客 2日常博客）：">{{ temp.type }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态（0上线 1下线）：">{{ temp.status }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否删除（0未删除 1已删除）：">{{ temp.isDelete }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建者：">{{ temp.createUser }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间：">{{ temp.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新者：">{{ temp.updateUser }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间：">{{ temp.updateTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注：">{{ temp.remark }}</el-form-item>
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
  name: 'SysBlogTable',
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
        title:'',
        Introduction:'',
        content:'',
        url:'',
        urlList:'',
        time:'',
        source:'',
        showType:'',
        type:'',
        status:'',
        isDelete:'',
        createUser:'',
        createTime:'',
        updateUser:'',
        updateTime:'',
        remark:'',
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
        title:'',
        Introduction:'',
        content:'',
        url:'',
        urlList:'',
        time:'',
        source:'',
        showType:'',
        type:'',
        status:'',
        isDelete:'',
        createUser:'',
        createTime:'',
        updateUser:'',
        updateTime:'',
        remark:'',
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
