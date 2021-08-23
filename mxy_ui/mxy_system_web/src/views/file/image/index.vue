<template>
  <div class="app-container">
    <div class="filter-container" style="margin-left: 85px;">
      <el-input v-model="listQuery.pictureName" prefix-icon="el-icon-search" placeholder="图片名称" style="width: 200px;"
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
    <el-tabs tab-position="left" @tab-click="handleClick">
      <el-tab-pane v-for="type in types" :key="type">
        <span slot="label"><i class="el-icon-date"></i> {{type}}</span>
        <div class="demo-image":key="comKey">
          <div class="block" v-for="(url,index) in urlList" :key="index" v-if="type===url.type">
            <el-image
              style="width: 100px; height: 100px"
              :src="url.pictureId"
              fit="fill"
              :preview-src-list="srcList"
              @click="previewUrl(url.pictureId)"
              lazy>
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
            <span class="img-title">{{url.pictureName}}</span>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>


    <!--新增/修改页-->
    <el-dialog title="图片上传" :visible.sync="dialogFormVisible">
      <el-upload
        class="upload-demo"
        ref="upload"
        action="https://jsonplaceholder.typicode.com/posts/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </el-dialog>


  </div>
</template>

<script>
  import {getList, addUser, editUser, deleteUser} from '@/api/file/picture/picture'
  import waves from '@/directive/waves' // waves directive
  import Pagination from '@/components/Pagination' // 分页
  import {validateEmail, validateAccount, validatePassword} from '@/utils/validate'

  // 权限
  const userTypeOptions = [
    {key: '01', name: 'S级管理员'},
    {key: '02', name: 'A级管理员'},
    {key: '03', name: 'B级管理员'},
    {key: '04', name: 'C级管理员'},
    {key: '05', name: 'D级管理员'},
    {key: '06', name: 'E级管理员'},
    {key: '07', name: 'F级参观者'}
  ]
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
    directives: {waves},
    filters: {},
    data() {
      return {
        fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],

        comKey:0,
        /*标签*/
        types:['缘分','情感','学习','励志'],
        /*预览图*/
        srcList: [],
        /*图片集合*/
        urlList: [],
        list: null, //表格列表数据
        total: 0, // 总条数
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 1000,
          pictureName: undefined,
          userName: undefined
        },
        userTypeOptions, // 用户权限
        statusOptions, // 用户状态
        sexOptions, // 用户性别
        temp: {
          userId: undefined,
          nickName: '',
          userName: '',
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
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      submitUpload() {
        this.$refs.upload.submit();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      /*标签切换点击事件*/
      handleClick(tab, event) {
        this.handleFilter();
      },
      /*列表查询*/
      getList() {
        this.listLoading = true
        getList(this.listQuery).then(response => {
          this.urlList = response.data.records
          this.listLoading = false
        })
      },
      /*条件查询*/
      handleFilter() {
        this.listQuery.currentPage = 1
        this.getList()
        this.comKey+=1;
      },
      /*用户状态改变*/
      handleModifyStatus(row) {
        debugger
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
          })
        }).catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      /*表单重置*/
      resetTemp() {
        this.temp = {
          userId: undefined,
          userName: '',
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
            })
          }
        })
      },
      /*数据删除*/
      handleDelete(row) {
        this.$confirm('是否确认删除用户账号为"' + row.userName + '"的数据?', "警告", {
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
      },
      previewUrl(url){
        const preUrl = [];
        preUrl.push(url);
        this.srcList=preUrl;
        console.log(preUrl);
      }
    }
  }
</script>
<style>
  /*新增页按钮居中--（写法暂定）*/
  .dialog-footer {
    text-align: center;
  }

  .block {
    padding: 30px 0;
    text-align: center;
    border-right: 1px solid #eff2f6;
    display: inline-block;
    width: 20%;
    box-sizing: border-box;
    vertical-align: top;
  }

  .img-title {
    display: block;
    color: #8492a6;
    font-size: 14px;
    margin-bottom: 20px;
    margin-top: 10px;
  }

</style>
