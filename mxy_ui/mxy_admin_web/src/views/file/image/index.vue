<template>
  <div class="app-container">
    <div class="filter-container" style="margin-left: 112px;margin-bottom: 30px;">
      <el-row :gutter="20">
        <el-col :span="6">
      <el-input placeholder="请输入图片名称" v-model="listQuery.pictureName" clearable @keyup.enter.native="handleFilter">
        <template slot="prepend">图片名称</template>
      </el-input>
        </el-col>
          <el-col :span="8">
      <el-button class="filter-item" style="margin-left: 10px;height: 40px;" icon="el-icon-search" @click="handleFilter"  size="small">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;height: 40px;" icon="el-icon-refresh-right" @click="handleFilter"  size="small">
        刷新
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;height: 40px;" type="plain" icon="el-icon-plus"
                 @click="handleCreate"  size="small">
        新增
      </el-button>
        </el-col>
      </el-row>
    </div>
    <el-tabs tab-position="left" @tab-click="handleClick" v-loading="listLoading"
             element-loading-spinner="el-icon-loading">
      <el-tab-pane v-for="type in types" :key="type">
        <span slot="label"><i class="el-icon-date"></i> {{ type }}</span>
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
            <span class="img-title">{{ url.pictureName }}</span>
            <div>
          <span class="el-upload-list__item-actions">
        <span class="image-icon image-center" @click="indexPreview(url)">
          <i class="el-icon-zoom-in" title="预览"></i>
        </span>
        <span class="image-icon image-center" @click="indexDownload(url)">
          <i class="el-icon-download" title="下载"></i>
        </span>
        <span class="image-icon image-center" @click="indexCopy(url)">
          <i class="el-icon-copy-document" title="复制链接"></i>
        </span>
        <span class="image-icon image-center" @click="indexRemove(url)">
          <i class="el-icon-delete" title="删除"></i>
        </span>
      </span>
            </div>


          </div>
        </div>
      </el-tab-pane>
    </el-tabs>


    <!--新增/修改页-->
    <el-dialog title="图片上传" :visible.sync="dialogFormVisible" style="text-align: center">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
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
        :multiple="true"
        ref="fileUpload"
        action="#"
        list-type="picture-card"
        :http-request="uploadImg">
        <i slot="default" class="el-icon-plus"></i>
        <div style="height: 100%" slot="file" slot-scope="{file}">
          <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
          <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <i class="el-icon-zoom-in"></i>
          </span>
      </span>
        </div>
      </el-upload>
      <div v-show="progressFlag" class="head-img">
        <el-progress :percentage="progressPercent" :format="format"></el-progress>
      </div>
      <div>
        <el-button style="margin-top: 25px;" size="small" type="success" @click="submitUpload">关闭</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import {getList, addUser, editUser, deletePicture, getFileTypeList,downloadPicture} from '@/api/file/img/img'
import Pagination from '@/components/Pagination' // 分页
import axios from 'axios'

export default {
  name: 'ComplexTable',
  components: {Pagination},
  filters: {},
  data() {
    return {
      progressFlag: false,
      progressPercent: 0,
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
      rules: {},
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false
    }
  },
  created() {
    this.getList();
    this.getFileTypeList();
  },
  methods: {
    uploadImg(f) {
      this.progressFlag = true;
      let formData = new FormData()
      formData.append('file', f.file)
      formData.append('fileName', this.fileData.fileName)
      formData.append('fileType', this.fileData.fileType)
      axios({
        url: window.SITE_CONFIG['systemUrl'] +'/api/foreign/uploadPicture',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
        onUploadProgress: progressEvent => {
          // progressEvent.loaded:已上传文件大小
          // progressEvent.total:被上传文件的总大小
          this.progressPercent = (progressEvent.loaded / progressEvent.total * 100)
        }
      }).then(res => {
        if (this.progressPercent === 100) {
          this.progressFlag = false
          this.progressPercent = 0
          this.$message({
            message: '上传成功',
            type: 'success'
          });
        }
      }).then(error => {
        console.log(error)
      })
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    indexPreview(url) {
      this.dialogImageUrl = url.pictureId;
      this.dialogVisible = true;
    },
    indexDownload(url) {
      window.location.href = window.SITE_CONFIG['systemUrl'] + '/api/foreign/downloadPicture?id=' + url.id
      this.$message({
        message: '下载中，请稍后...',
        type: 'success'
      });
    },
    indexCopy(url) {
      var domUrl = document.createElement("input");
      domUrl.value = url.pictureId;
      domUrl.id = "creatDom";
      document.body.appendChild(domUrl);
      domUrl.select(); // 选择对象
      document.execCommand("Copy"); // 执行浏览器复制命令
      let creatDom = document.getElementById("creatDom");
      creatDom.parentNode.removeChild(creatDom);
      this.$message({
        message: '链接复制成功',
        type: 'success'
      });
    },
    indexRemove(url) {
      this.$confirm('是否删除此图片？', "警告", {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning'
      }).then(() => {
        this.temp.id = url.id;
        deletePicture(this.temp).then(() => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getList();
        })
      }).catch(() => {
      });
    },
    format(percentage) {
      return percentage === 100 ? '100%' : `${percentage}%`;
    },
    /*图片上传*/
    submitUpload(f) {
      // 清空图片缓存
      this.$refs.fileUpload.clearFiles();
      /*清空图片列表*/
      this.fileList = [];
      this.dialogFormVisible = false;
      this.handleFilter();
    },
    handlePreview(file) {
      console.log(file);
    },
    /*标签切换点击事件*/
    handleClick(tab, event) {
      if (this.listQuery.pictureName){
        this.handleFilter();
      }
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
        deletePicture(this.temp).then(() => {
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
.head-img{
  display: inline-block;
  width: 50%;
}

.image-center {
  padding: 15px;
}

.image-center :hover {
  color: #20a0ff;
}

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
