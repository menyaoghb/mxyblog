<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12">
        <el-card class="box-card">
          <div class="dic-type-div">
            <!--查询-->
            <div class="filter-container">
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-input placeholder="请输入字典名称" v-model="typeListQuery.dictName" clearable
                            @keyup.enter.native="typeFilter">
                    <template slot="prepend">字典名称</template>
                  </el-input>
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    <el-input placeholder="请输入字典编码" v-model="typeListQuery.dictType" clearable
                              @keyup.enter.native="typeFilter">
                      <template slot="prepend">字典编码</template>
                    </el-input>
                  </div>
                </el-col>
                <el-col :span="6">
                  <el-button class="filter-item" style="margin-left: 10px;" @click="typeFilter" size="small">
                    查询
                  </el-button>
                  <el-button class="filter-item" style="margin-left: 10px;" @click="typeCreateJump" size="small">
                    新增
                  </el-button>
                </el-col>
              </el-row>
            </div>
            <!--表格-->
            <el-table
              :data="typeList"
              ref="singleTable"
              highlight-current-row
              @current-change="handleCurrentChange"
              style="width: 100%" :row-style="{height:'40px'}"
              height="550"
              :cell-style="{padding:'0px'}" v-loading="typeListLoading"
              element-loading-spinner="el-icon-loading">
              <el-table-column type="index" width="50" align="center"/>
              <el-table-column prop="dictName" label="名称" align="center"></el-table-column>
              <el-table-column prop="dictType" label="编码" align="center"></el-table-column>
              <el-table-column prop="remark" label="备注" align="center"></el-table-column>
              <el-table-column label="状态" width="90" class-name="status-col">
                <template slot-scope="{row}">
                  <el-tooltip v-for="item in statusOptions" v-if="row.status===item.key" :content="item.name"
                              placement="right">
                    <el-switch
                      v-model="row.status"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      active-value="0"
                      inactive-value="1"
                      @change="typeStatus(row)">
                    </el-switch>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="230" class-name="small-padding">
                <template slot-scope="{row,$index}">
                  <el-button size="mini" @click="typeUpdateJump(row)" type="text">编辑</el-button>
                  <el-button size="mini" @click="typeDelete(row)" type="text">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--分页-->
            <pagination v-show="typeTotal>0" :total="typeTotal" :page.sync="typeListQuery.currentPage"
                        :limit.sync="typeListQuery.pageSize"
                        @pagination="getDictTypeList"/>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div class="dic-type-div">
            <!--查询-->
            <div class="filter-container">
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-input placeholder="请输入名称" v-model="dataListQuery.dictName" clearable
                            @keyup.enter.native="dataFilter">
                    <template slot="prepend">名称</template>
                  </el-input>
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    <el-input placeholder="请输入值" v-model="dataListQuery.dictValue" clearable
                              @keyup.enter.native="dataFilter">
                      <template slot="prepend">值</template>
                    </el-input>
                  </div>
                </el-col>
                <el-col :span="6">
                  <el-button class="filter-item" style="margin-left: 10px;" @click="dataFilter" size="small">
                    查询
                  </el-button>
                  <el-button class="filter-item" style="margin-left: 10px;" @click="dataCreateJump" size="small">
                    新增
                  </el-button>
                </el-col>
              </el-row>
            </div>
            <!--表格-->
            <el-table
              :data="dataList"
              style="width: 100%" :row-style="{height:'40px'}"
              height="550"
              :cell-style="{padding:'0px'}" v-loading="dataListLoading" element-loading-spinner="el-icon-loading">
              <el-table-column type="index" width="50" align="center"/>
              <el-table-column prop="dictName" label="名称" align="center"></el-table-column>
              <el-table-column prop="dictValue" label="值" align="center"></el-table-column>
              <el-table-column prop="dictSort" label="排序" align="center"></el-table-column>
              <el-table-column label="状态" width="90" class-name="status-col">
                <template slot-scope="{row}">
                  <el-tooltip v-for="item in statusOptions" v-if="row.status===item.key" :content="item.name"
                              placement="right">
                    <el-switch
                      v-model="row.status"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      active-value="0"
                      inactive-value="1"
                      @change="dataStatus(row)">
                    </el-switch>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column prop="remark" label="备注" align="center"></el-table-column>
              <el-table-column label="操作" align="center" width="230" class-name="small-padding">
                <template slot-scope="{row,$index}">
                  <el-button size="mini" @click="dataUpdateJump(row)" type="text">编辑</el-button>
                  <el-button size="mini" @click="dataDelete(row)" type="text">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--分页-->
            <pagination v-show="typeTotal>0" :total="dataTotal" :page.sync="dataListQuery.currentPage"
                        :limit.sync="dataListQuery.pageSize"
                        @pagination="getDictDataList"/>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!--新增/修改页（类型）-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="typeDialogFormVisible">
      <el-form ref="typeForm" :rules="rules" :model="typeTemp" label-position="left" label-width="100px"
               style="width: 400px; margin-left:160px;">
        <el-form-item label="字典名称">
          <el-input v-model="typeTemp.dictName"/>
        </el-form-item>
        <el-form-item label="字典类型">
          <el-input v-model="typeTemp.dictType"/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="typeTemp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='add'?typeCreate():typeUpdate()">
          提交
        </el-button>
        <el-button @click="typeDialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>

    <!--新增/修改页（数据）-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dataDialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="dataTemp" label-position="left" label-width="100px"
               style="width: 400px; margin-left:160px;">
        <el-form-item label="字典编码">
          <el-input v-model="dataTemp.dictType" disabled/>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="dataTemp.dictName"/>
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="dataTemp.dictValue"/>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="dataTemp.dictSort"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="dataTemp.status" class="filter-item" style="width: 100%;">
            <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="dataTemp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='add'?dataCreate():dataUpdate()">
          提交
        </el-button>
        <el-button @click="dataDialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSysDictTypeList, addDictType, editDictType, deleteDictType} from '@/api/sys/dictType/type'
import {getSysDictDataList, addDictData, editDictData, deleteDictData,getDictData} from '@/api/sys/dictData/data'

import Pagination from '@/components/Pagination'
import {editUserStatus} from "@/api/sys/user/user"; // 分页

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
      typeList: null, //类型列表数据
      dataList: null, //数据列表数据
      typeTotal: 0, // 类型总条数
      dataTotal: 0, // 数据总条数
      typeListLoading: true,
      dataListLoading: true,
      typeListQuery: {
        currentPage: 1,
        pageSize: 10,
        dictName: undefined,
        dictType: undefined
      },
      dataListQuery: {
        currentPage: 1,
        pageSize: 10,
        dictName: undefined,
        dictValue: undefined,
        dictType: undefined
      },
      statusOptions, // 用户状态
      typeTemp: {
        dictId: undefined,
        dictName: '',
        dictType: '',
        parentId: '',
        status: '0',
        remark: ''
      },
      dataTemp: {
        dictCode: undefined,
        dictName: '',
        dictValue: '',
        dictType: '',
        parentId: '',
        dictSort: '',
        status: '0',
        remark: ''
      },
      typeDialogFormVisible: false, //控制新增页关闭（类型）
      dataDialogFormVisible: false, //控制新增页关闭（数据）
      dialogStatus: '', // 判断当前操作是新增还是修改
      textMap: {
        add: '新增',
        edit: '编辑'
      },
      rules: {},
      currentRow: null,
      dictType: null,
      dictBlogType:null
    }
  },
  created() {
    this.getDictTypeList();
    this.getDictData();
    this.handleCurrentChange();
  },
  methods: {
    handleCurrentChange(val) {
      this.currentRow = val;
      this.dataListQuery.dictType = val.dictType;
      this.dictType = val.dictType;
      this.dataFilter();
    },
    /*字典查询*/
    getDictData() {
      getDictData({dictType:"BLOG-TYPE"}).then(response => {
        this.dictBlogType = response.data
      })
    },
    /*类型列表查询*/
    getDictTypeList() {
      this.typeListLoading = true
      getSysDictTypeList(this.typeListQuery).then(response => {
        this.typeList = response.data.records
        this.typeTotal = response.data.total
        this.$refs.singleTable.setCurrentRow(this.typeList[0]);
        this.typeListLoading = false
      })
    },
    /*数据列表查询*/
    getDictDataList() {
      this.dataListLoading = true
      getSysDictDataList(this.dataListQuery).then(response => {
        this.dataList = response.data.records
        this.dataTotal = response.data.total
        this.dataListLoading = false
      })
    },
    /*条件查询（类型）*/
    typeFilter() {
      this.typeListQuery.currentPage = 1
      this.getDictTypeList()
    },
    /*条件查询（数据）*/
    dataFilter() {
      this.dataListQuery.currentPage = 1
      this.getDictDataList()
    },
    /*状态改变（类型）*/
    typeStatus(row) {
      this.typeTemp.dictId = row.dictId;
      let param = {};
      param.dictId = row.dictId;
      param.status = row.status;
      editDictType(param).then(() => {
        this.$message({
          message: '更新成功',
          type: 'success'
        });
        this.getDictTypeList();
      })
    },
    /*状态改变（数据）*/
    dataStatus(row) {
      this.dataTemp.dictCode = row.dictCode;
      let param = {};
      param.dictCode = row.dictCode;
      param.status = row.status;
      editDictData(param).then(() => {
        this.$message({
          message: '更新成功',
          type: 'success'
        });
        this.getDictDataList();
      })
    },
    /*表单重置（类型）*/
    resetTypeTemp() {
      this.typeTemp = {
        dictId: undefined,
        dictName: '',
        dictType: '',
        parentId: '',
        status: '0',
        remark: ''
      }
    },
    /*表单重置（数据）*/
    resetDataTemp() {
      this.dataTemp = {
        dictCode: undefined,
        dictName: '',
        dictValue: '',
        dictType: this.dictType,
        parentId: '',
        dictSort: '',
        status: '0',
        remark: ''
      }
    },
    /*新增跳转（类型）*/
    typeCreateJump() {
      this.resetTypeTemp()
      this.dialogStatus = 'add'
      this.typeDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['typeForm'].clearValidate()
      })
    },
    /*新增提交（类型）*/
    typeCreate() {
      this.$refs['typeForm'].validate((valid) => {
        if (valid) {
          addDictType(this.typeTemp).then(() => {
            this.$message({
              message: '新增成功',
              type: 'success'
            });
            this.typeDialogFormVisible = false
            this.getDictTypeList();
          })
        }
      })
    },
    /*修改跳转（类型）*/
    typeUpdateJump(row) {
      this.typeTemp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'edit'
      this.typeDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['typeForm'].clearValidate()
      })
    },
    /*修改提交（类型）*/
    typeUpdate() {
      this.$refs['typeForm'].validate((valid) => {
        if (valid) {
          editDictType(this.typeTemp).then(() => {
            this.$message({
              message: '修改成功',
              type: 'success'
            });
            this.typeDialogFormVisible = false
            this.getDictTypeList();
          })
        }
      })
    },
    /*数据删除（类型）*/
    typeDelete(row) {
      this.$confirm('确定进行删除操作? 删除该字典后，关联的字典项也会被删除!', "警告", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.typeTemp.dictId = row.dictId;
        deleteDictType(this.typeTemp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.typeDialogFormVisible = false
          this.getDictTypeList();
        })
      }).catch(() => {
      });
    },
    /*新增跳转（数据）*/
    dataCreateJump() {
      this.resetDataTemp()
      this.dialogStatus = 'add'
      this.dataDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /*新增提交（数据）*/
    dataCreate() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addDictData(this.dataTemp).then(() => {
            this.$message({
              message: '新增成功',
              type: 'success'
            });
            this.dataDialogFormVisible = false
            this.getDictDataList();
          })
        }
      })
    },
    /*修改跳转（数据）*/
    dataUpdateJump(row) {
      this.dataTemp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'edit'
      this.dataDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['typeForm'].clearValidate()
      })
    },
    /*修改提交（数据）*/
    dataUpdate() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          editDictData(this.dataTemp).then(() => {
            this.$message({
              message: '修改成功',
              type: 'success'
            });
            this.dataDialogFormVisible = false
            this.getDictDataList();
          })
        }
      })
    },
    /*数据删除（数据）*/
    dataDelete(row) {
      this.$confirm('确定进行删除操作?', "警告", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dataTemp.dictCode = row.dictCode;
        deleteDictData(this.dataTemp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.dataDialogFormVisible = false
          this.getDictDataList();
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

.box-card {
  width: 98%;
}
</style>
