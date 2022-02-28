<template>
  <div style="margin: 20px;">
    <el-form ref="postForm" :model="postForm" v-loading="loading" element-loading-spinner="el-icon-loading">
      <div style="text-align: right;">
        <sticky>
          <el-button style="margin-left: 10px;" type="success" @click="submitForm">
            更新
          </el-button>
          <router-link to="blogList">
          <el-button style="margin-left: 10px;" type="success">
            返回
          </el-button>
            </router-link>
        </sticky>
      </div>
      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
                标题
              </MDinput>
            </el-form-item>
            <el-form-item style="margin-bottom: 40px;" label-width="70px" label="简介:">
              <el-input v-model="postForm.summary" :rows="1" type="textarea" class="article-textarea" autosize
                        placeholder=""/>
              <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="postInfo-container">
          <el-row>
            <el-col :span="6">
              <el-form-item label-width="70px" label="笔名:" class="postInfo-container-item">
                <el-input v-model="postForm.author" :rows="1" type="textarea" class="article-textarea" autosize
                          placeholder=""/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label-width="70px" label="来源:" class="postInfo-container-item">
                <el-input v-model="postForm.source" :rows="1" type="textarea" class="article-textarea" autosize
                          placeholder=""/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label-width="70px" label="标题图:" class="postInfo-container-item">
                <div class="title-pic" @click="chooseImg">
                  <img :src="postForm.filePath" class="avatar">
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div>
          <EditorTool class="tool-style" style="margin-top: 50px;margin-left: 15px" v-model="postForm.content"
                      :is-clear="isClear"
                      @change="changeEditor"/>
          <span v-show="contentLength" class="word-content">{{ contentLength }}字符</span>
        </div>
      </div>
    </el-form>

    <el-dialog
      title="图片选择"
      :visible.sync="dialogVisible"
      width="50%" v-loading="imageLoading" element-loading-spinner="el-icon-loading">
      <el-tabs tab-position="left" @tab-click="handleClick">
        <el-tab-pane v-for="type in imageType" :key="type">
          <span slot="label"><i class="el-icon-date"></i> {{ type }}</span>
          <div class="demo-image" :key="comKey">
            <div class="block" v-for="(url,index) in imageList" :key="index" v-if="type===url.type">
              <el-image
                style="width: 100px; height: 100px"
                :src="url.pictureId"
                fit="fill" @click="chooseTitleImg(url)">
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
          </span>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <el-dialog :visible.sync="imageUrlDialog">
      <img width="100%" :src="imageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import EditorTool from './components/wangEditor'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky'
import {addArticle, editArticle, uploadPhoto} from '@/api/blog/blog'
import axios from 'axios'
import {getFileTypeList, getList} from "@/api/file/img/img";

const defaultForm = {
  status: '0',
  title: '', // 文章题目
  content: '', // 文章内容
  summary: '', // 文章摘要
  author: '孟耀', // 文章摘要
  source: '原创', // 文章摘要
  filePath: 'http://mxy.mxyit.com/278668fa-6aa7-43e2-8f90-ef4645fea5ee', // 标题图片
  display_time: undefined, // 前台展示时间
  id: undefined
}
export default {
  name: 'UpdateArticle',
  components: {EditorTool, MDinput, Sticky},
  data() {
    return {
      postForm: Object.assign({}, defaultForm),
      isClear: false,
      loading: false,
      progressFlag: false, // 上传标题图进度条控制
      progressPercent: 0, // 上传标题图进度条值
      dialogVisible: false,
      imageLoading: false,
      imageUrlDialog: false,
      /*控制刷新左侧标签内容*/
      comKey: 0,
      /*图片标签*/
      imageType: [],
      /*图片集合*/
      imageList: [],
      /*图片预览地址*/
      imageUrl: ''
    }
  },
  computed: {
    // 监控当前输入字符数
    contentShortLength() {
      return this.postForm.summary.length
    },
    contentLength() {
      return this.postForm.content.length
    },
    // 发布时间 设置
    displayTime: {
      get() {
        return (+new Date(this.postForm.display_time))
      },
      set(val) {
        this.postForm.display_time = new Date(val)
      }
    }
  },
  created() {
      this.setFormData(this.$route.query.row);
  },
  methods: {
    setFormData(obj){
      this.postForm ={
        status: obj.status,
        title: obj.title, // 文章题目
        content: obj.content, // 文章内容
        summary: obj.summary, // 文章摘要
        author: obj.author, // 文章摘要
        source: obj.source, // 文章摘要
        filePath: obj.filePath, // 标题图片
        display_time: undefined, // 前台展示时间
        id: obj.id
      }
    },
    // 表单提交
    submitForm() {
      if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true
          editArticle(this.postForm).then(() => {
            this.$notify({
              title: '成功',
              message: '更新博客成功',
              type: 'success',
              duration: 2000
            })
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    changeEditor(val) {
      console.log(val)
    },
    chooseTitleImg(val) {
      this.postForm.filePath = val.pictureId;
      this.dialogVisible = false;
    },
    chooseImg() {
      this.dialogVisible=true;
      this.imageLoading = true;
      getList({currentPage: 1,pageSize: 1000,}).then(response => {
        this.imageList = response.data.records
      })
      getFileTypeList({id:undefined}).then(response => {
        let arr = response.data.typeMap;
        let type = [];
        for (let i = 0; i < arr.length; i++) {
          type.push(arr[i].value);
        }
        this.imageType = type;
      })
      this.imageLoading = false
    },
    /*标签切换点击事件*/
    handleClick(tab, event) {
      this.comKey += 1;
    },
    indexPreview(url) {
      this.imageUrl = url.pictureId;
      this.imageUrlDialog = true;
    },
    // 上传 标题图
    uploadImg (f) {
      this.progressFlag = true
      let formData = new FormData()
      formData.append('imageUrl', f.file)
      axios({
        url: 'http://mxyit.com:8088/api/foreign/uploadPhoto',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
        onUploadProgress: progressEvent => {
          // progressEvent.loaded:已上传文件大小
          // progressEvent.total:被上传文件的总大小
          this.progressPercent = (progressEvent.loaded / progressEvent.total * 100)
        }
      }).then(res => {
        this.postForm.filePath = res.data.data;
        if (this.progressPercent === 100) {
          this.progressFlag = false
          this.progressPercent = 0
        }
      }).then(error => {
        console.log(error)
      })
    },
    // 上传标题图 校验
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      return isLt2M;
    }
  }
}
</script>
<style lang="scss" scoped>
.word-content{
  position: relative;
  float: right;
  top: 4px;
  font-size: xx-small;
}
.title-pic{
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}


.article-textarea ::v-deep {
  textarea {
    padding-right: 40px;
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
}

.word-counter {
  width: 40px;
  position: absolute;
  right: 10px;
  top: 0px;
}
</style>
