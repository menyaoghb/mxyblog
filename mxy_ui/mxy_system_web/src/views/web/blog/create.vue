<template>
 <!-- <article-detail :is-edit="false"/>-->
  <EditorTool class="tool-style" style="margin-top: 50px;margin-left: 15px" v-model="content" :is-clear="isClear" @change="changeEditor" />
</template>

<script>
import ArticleDetail from './components/ArticleDetail'
import EditorTool from './components/wangEditor'

export default {
  name: 'CreateArticle',
  components: { EditorTool  },
  data() {
    return {
      isClear: false,
      detail: '',
      content: ''
    }
  },
  methods: {
    changeEditor(val) {
      console.log(val)
    }
  }
}
</script>

