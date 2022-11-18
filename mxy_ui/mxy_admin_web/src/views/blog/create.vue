<template>
  <div style="margin: 20px;">
    <el-form ref="postForm" :model="postForm" v-loading="loading" element-loading-spinner="el-icon-loading">

      <div style="text-align: right;">
        <sticky>
          <el-button style="margin-left: 10px;" type="success" @click="submitForm">
            提交审核
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
                <div class="title-pic" @click="chooseImg">
                  <img :src="postForm.filePath" class="avatar">
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label-width="70px" label="分类:" class="postInfo-container-item">
                <div>
                  <el-radio-group v-model="postForm.classify" size="mini">
                    <el-radio v-for="item in dictData" :key="item.value" :label="item.value" border>{{ item.name }}
                    </el-radio>
                  </el-radio-group>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--        <EditorTool class="tool-style" style="margin-top: 50px;margin-left: 15px" v-model="postForm.content" @change="changeEditor"/>-->
        <div class="tool-style" style="margin-top: 50px;margin-left: 15px">
          <div class="full-screen-container" style="border: 1px solid #ccc;">
            <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editor"
              :defaultConfig="toolbarConfig"
              :mode="mode"
            />
            <Editor
              style="height: 500px; overflow-y: hidden;"
              v-model="postForm.content"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="onCreated"
              @onChange="onChange"
            />
          </div>
        </div>


      </div>
    </el-form>
    <el-dialog
      title="图片选择"
      :visible.sync="dialogVisible"
      width="50%">
      <el-tabs tab-position="left" @tab-click="handleClick" v-loading="imageLoading"
               element-loading-spinner="el-icon-loading">
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
            <span class="image-icon image-center" @click="indexCopy(url)">
              <i class="el-icon-copy-document" title="复制链接"></i>
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
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky'
import {addArticle} from '@/api/blog/blog'
import {getFileTypeList, getList} from "@/api/file/img/img";
import {getDictData} from "@/api/sys/dictData/data";
import axios from 'axios'
import store from "@/store";

import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import Vue from 'vue'
import '@wangeditor/editor/dist/css/style.css'

const defaultForm = {
  status: '9',
  title: '', // 文章题目
  content: '', // 文章内容
  summary: '', // 文章摘要
  author: store.getters.nickName, // 文章作者
  source: '原创', // 文章来源
  classify: '1', // 文章分类
  filePath: 'http://mxy.mxyit.com/de47b1e2-9dec-4a2f-9a93-7328733fc9b7', // 标题图片
  display_time: undefined, // 前台展示时间
  id: undefined
}
export default Vue.extend({
  name: 'CreateArticle',
  components: {Editor, Toolbar, MDinput, Sticky},
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
      imageUrl: '',
      dictData: null,
      /*编辑器*/
      editor: null,
      toolbarConfig: {},
      editorConfig: {
        placeholder: '请输入内容...',
        MENU_CONF: {
          // 图片的上传
          uploadImage: {
            async customUpload(file, insertFn) {
              let formData = new FormData();
              formData.append("file", file);
              formData.append('fileName', "博文")
              formData.append('fileType', "博文图片合集")
              const res = await axios({
                url: window.SITE_CONFIG['systemUrl'] +'/api/foreign/uploadPicture',
                method: 'post',
                data: formData,
                headers: {'Content-Type': 'multipart/form-data'},
                onUploadProgress: progressEvent => {
                  let percent = (progressEvent.loaded / progressEvent.total * 100 | 0);
                  console.log(percent)
                }
              }).then(res => {
                const {status, data, code} = res.data;

                if (code === 200) {
                  const alt = (data.split("/")[data.split("/").length - 1]).substring(36);
                  insertFn(data, alt, data);  // insertFn 参数1：路径； 参数2：alt值； 参数三：路径
                } else {
                  console.log("上传失败");
                }
              }).then(error => {
                console.log(error)
              })
            }
          }
        }
      },
      mode: 'default', // or 'simple'
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
  created() {
    this.getDictData();
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
    },
    onChange(editor) {
      console.log('content', this.postForm.content)
    },
    /*类型列表查询*/
    getDictData() {
      getDictData({dictType: "BLOG-TYPE"}).then(response => {
        this.dictData = response.data
      })
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
              author: store.getters.nickName, // 文章摘要
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
    /*标题图弹窗*/
    chooseTitleImg(val) {
      this.postForm.filePath = val.pictureId;
      this.dialogVisible = false;
    },
    /*图片选择*/
    chooseImg() {
      this.dialogVisible = true;
      this.imageLoading = true;
      getList({currentPage: 1, pageSize: 1000,}).then(response => {
        this.imageList = response.data.records
      })
      getFileTypeList({id: undefined}).then(response => {
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
    /*图片预览*/
    indexPreview(url) {
      this.imageUrl = url.pictureId;
      this.imageUrlDialog = true;
    },
    /*图片链接复制*/
    indexCopy(url) {
      let domUrl = document.createElement("input");
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
    }
  },
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
})
</script>
<style lang="scss" scoped>
.full-screen-container {
  z-index: 1000000; /* 如有需要，可以自定义 z-index */
}

.image-center {
  padding: 15px;
}

.image-center :hover {
  color: #20a0ff;
}

.title-pic {
  margin-left: 20px;
  margin-top: -66px;
}

.postInfo-container {
  padding-top: 60px;
}

.title-pic {
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

.el-radio.is-bordered + .el-radio.is-bordered {
  margin-left: 0px;
  margin-bottom: 10px;
}

.el-radio {
  margin-right: 10px;
}
</style>
