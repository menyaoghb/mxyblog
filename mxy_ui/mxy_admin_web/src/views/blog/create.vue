<template>
  <div style="margin: 20px;">
    <el-form ref="postForm" :model="postForm" v-loading="loading" element-loading-spinner="el-icon-loading">

      <div style="text-align: right;">
        <sticky>
          <el-button style="margin-left: 10px;" type="success" @click="submitForm">
            发布
          </el-button>
          <el-button type="warning" @click="draftForm">
            保存
          </el-button>
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
                <div class="title-pic">
                  <el-upload class="avatar-uploader" action="#"
                             :show-file-list="false"  :before-upload="beforeAvatarUpload"
                             :http-request="uploadImg">
                    <div v-show="progressFlag" class="head-img">
                      <el-progress type="circle" :percentage="progressPercent"></el-progress>
                    </div>
                    <img v-if="!progressFlag" :src="postForm.filePath" class="avatar">
                    <i class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                </div>
              </el-form-item>
            </el-col>

            <!--                <el-col :span="10">
                              <el-form-item label-width="120px" label="发布时间:" class="postInfo-container-item">
                                <el-date-picker v-model="displayTime" type="datetime" format="yyyy-MM-dd HH:mm:ss"
                                                placeholder="选择发布时间"/>
                              </el-form-item>
                            </el-col>-->

            <!--                <el-col :span="6">
                              <el-form-item label-width="90px" label="Importance:" class="postInfo-container-item">
                                <el-rate
                                  v-model="postForm.importance"
                                  :max="3"
                                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                                  :low-threshold="1"
                                  :high-threshold="3"
                                  style="display:inline-block"
                                />
                              </el-form-item>
                            </el-col>-->
          </el-row>
        </div>
        <EditorTool class="tool-style" style="margin-top: 50px;margin-left: 15px" v-model="postForm.content"
                    :is-clear="isClear"
                    @change="changeEditor"/>
        <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
      </div>
    </el-form>
  </div>
</template>

<script>
import EditorTool from './components/wangEditor'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky'
import {addArticle, uploadPhoto} from '@/api/blog/blog'
import axios from 'axios'

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
  name: 'CreateArticle',
  components: {EditorTool, MDinput, Sticky},
  data() {
    return {
      postForm: Object.assign({}, defaultForm),
      isClear: false,
      loading: false,
      progressFlag: false, // 上传标题图进度条控制
      progressPercent: 0 // 上传标题图进度条值
    }
  },
  computed: {
    // 监控当前输入字符数
    contentShortLength() {
      return this.postForm.summary.length
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
  methods: {
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
          this.postForm.status = '0'
          addArticle(this.postForm).then(() => {
            this.$notify({
              title: '成功',
              message: '发布博客成功',
              type: 'success',
              duration: 2000
            })
            this.postForm = {
              status: '0',
              title: '', // 文章题目
              content: '', // 文章内容
              summary: '', // 文章摘要
              author: '孟耀', // 文章摘要
              source: '原创', // 文章摘要
              filePath: 'http://mxy.mxyit.com/278668fa-6aa7-43e2-8f90-ef4645fea5ee', // 标题图片
              display_time: undefined, // 前台展示时间
              id: undefined
            };
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 表单保存
    draftForm() {
      if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.postForm.status = '1'
      addArticle(this.postForm).then(() => {
        this.$message({
          message: '保存成功',
          type: 'success',
          showClose: true,
          duration: 1000
        })
      })
    },
    changeEditor(val) {
      console.log(val)
    },
    // 上传 标题图
    uploadImg (f) {
      this.progressFlag = true
      let formData = new FormData()
      formData.append('imageUrl', f.file)
      axios({
        url: 'http://localhost:7088/api/foreign/uploadPhoto',
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
.title-pic{
  margin-left: 20px;
  margin-top: -66px;
}
.postInfo-container{
padding-top: 60px;
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
