<template>
  <div style="margin: 20px;">
    <el-form ref="postForm" :model="postForm">

      <div style="text-align: right;">
        <sticky>
          <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
            发布
          </el-button>
          <el-button v-loading="loading" type="warning" @click="draftForm">
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

            <div class="postInfo-container">
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="60px" label="作者:" class="postInfo-container-item">
                    <el-input v-model="postForm.author"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="120px" label="发布时间:" class="postInfo-container-item">
                    <el-date-picker v-model="displayTime" type="datetime" format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择发布时间"/>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
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
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
        <el-form-item style="margin-bottom: 40px;" label-width="70px" label="简介:">
          <el-input v-model="postForm.summary" :rows="1" type="textarea" class="article-textarea" autosize placeholder="" />
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item>
        <EditorTool class="tool-style" style="margin-top: 50px;margin-left: 15px" v-model="postForm.content" :is-clear="isClear"
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
  import { addArticle } from '@/api/blog/blog'
  const defaultForm = {
    status: '0',
    title: '', // 文章题目
    content: '', // 文章内容
    summary: '', // 文章摘要
    source_uri: '', // 文章外链
    image_uri: '', // 文章图片
    display_time: undefined, // 前台展示时间
    id: undefined,
    platforms: ['a-platform'],
    comment_disabled: false,
    importance: 0
  }
  export default {
    name: 'CreateArticle',
    components: {EditorTool, MDinput,Sticky},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        isClear: false,
        detail: '',
        content: '',
        loading: false,
        userListOptions: []
      }
    },
    computed: {
      contentShortLength() {
        return this.postForm.summary.length
      },
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
      submitForm() {
        console.log(this.postForm)
        this.$refs.postForm.validate(valid => {
          if (valid) {
            this.loading = true
            this.postForm.status = '0'
            addArticle(this.postForm).then(() => {
              this.$notify({
                title: '成功',
                message: '发布文章成功',
                type: 'success',
                duration: 2000
              })
            })
            this.loading = false
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
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
      }
    }
  }
</script>
<style lang="scss" scoped>
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
