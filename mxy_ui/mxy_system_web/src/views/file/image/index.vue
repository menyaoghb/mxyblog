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
        <div class="demo-image" :key="comKey">
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
    <el-dialog title="图片上传" :visible.sync="dialogFormVisible" style="text-align: center">

      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="100px"
      >
        <el-form-item label="图片标签" align="left">
          <el-autocomplete
            class="inline-input"
            v-model="fileData.fileType"
            :fetch-suggestions="querySearchIns"
            placeholder="请输入标签"
            @select="handleSelect"
            clearable
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="图片名称" prop="fileName">
          <el-input v-model="fileData.fileName"/>
        </el-form-item>
      </el-form>

      <el-upload
        class="upload-demo"
        ref="upload"
        action="http://10.0.4.4:8001/system/sysPicture/uploadPicture"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :data="fileData"
        :file-list="fileList"
        list-type="picture"
        :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </el-dialog>


  </div>
</template>

<script>
  import {getList, addUser, editUser, deleteUser, getFileTypeList} from '@/api/file/picture/picture'
  import waves from '@/directive/waves' // waves directive
  import Pagination from '@/components/Pagination' // 分页
  import {validateEmail, validateAccount, validatePassword} from '@/utils/validate'
  import {getAdviceList} from "@/api/mxy/beautifulWords/beautifulWords";

  export default {
    name: 'ComplexTable',
    components: {Pagination},
    directives: {waves},
    filters: {},
    data() {
      return {
        /*图片标签-新增*/
        imageType: [],
        /*图片上传-额外参数*/
        fileData: {
          fileType: '', // 图片类型
          fileName: ''  // 图片名称
        },
        /*待上传图片列表*/
        fileList: [],
        /*控制刷新左侧标签内容*/
        comKey: 0,
        /*标签*/
        types: [],
        /*预览图*/
        srcList: [],
        /*图片集合*/
        urlList: [],
        listLoading: true,
        /*查询参数*/
        listQuery: {
          currentPage: 1, // 当前页
          pageSize: 1000, // 页数
          pictureName: undefined // 图片名称
        },
        temp: {
          id: undefined
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
    created() {
      this.getList();
      this.getFileTypeList();
    },
    methods: {
      /*图片上传*/
      submitUpload() {
        this.$refs.upload.submit();
        /*清空图片列表*/
        this.fileList = [];
        this.$message({
          message: '上传成功',
          type: 'success'
        });
        this.dialogFormVisible = false;
        this.handleFilter();
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
        this.comKey += 1;
      },
      /*表单重置*/
      resetTemp() {
        this.temp = {userId: undefined};
        /*清空图片列表*/
        this.fileList = [];
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
      /*图片点击预览*/
      previewUrl(url) {
        const preUrl = [];
        preUrl.push(url);
        this.srcList = preUrl;
        console.log(preUrl);
      },
      handleSelect(item) {
        console.log(item);
      },
      /*输入建议查询*/
      getFileTypeList() {
        getFileTypeList(this.temp).then(response => {
          this.imageType = response.data.typeMap
          let arr = this.imageType;
          let type = [];
          for (let i = 0; i < arr.length; i++) {
            type.push(arr[i].value);
          }
          this.types = type;
        })
      },
      /*输入建议*/
      querySearchIns(queryString, cb) {
        const restaurants = this.imageType;
        const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        cb(results);
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
