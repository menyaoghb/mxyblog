<template>
  <div class="home">
    <banner isHome="true"></banner>
    <div class="site-content animate" style="width: 100%;">
      <!--通-->
      <div style="margin-top: 60px;    text-align: center;">
        <div style="display: inline-block;vertical-align: middle;">
          <h1 style="font-family: 'Softbrush';font-size: 32px;padding-bottom: 30px;position: relative;font-weight: 500;color: #000000;">
            生活只有两种选择，重新出发，做自己生命的主角。抑或停留在原地，做别人的配角。</h1>
          <p style="font-family: 'League';margin-top: 20px;line-height: 22px;color: #888;">
            Meng Xiao Yao
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from '@/components/banner'
import Feature from '@/components/feature'
import sectionTitle from '@/components/section-title'
import Post from '@/components/post'
import SmallIco from '@/components/small-ico'
import Quote from '@/components/quote'
import {fetchFocus, fetchList} from '../api'
import Pagination from '@/components/Pagination' // 分页

export default {
  name: 'Home',
  props: ['cate', 'words'],
  data() {
    return {
      features: [],
      postList: [],
      currPage: 1,
      total: 0, // 总条数
      listQuery: {
        pageSize: 5, currentPage: 1, status: "0"
      }
    }
  },
  components: {
    Banner,
    Feature,
    sectionTitle,
    Post,
    SmallIco,
    Quote,
    Pagination
  },
  computed: {
    searchWords() {
      return this.$route.params.words
    },
    category() {
      return this.$route.params.cate
    },
    hideSlogan() {
      return this.category || this.searchWords
    },
    notice() {
      return this.$store.getters.notice
    }
  },
  mounted() {
    this.fetchFocus();
    this.fetchList();
  },
  methods: {
    fetchFocus() {
      fetchList(this.listQuery.currentPage).then(res => {
        this.postList = res.data.records || []
        this.total = res.data.total
      }).catch(err => {
        console.log(err)
      })
    },
    fetchList() {
      fetchList(this.listQuery.currentPage).then(res => {
        this.postList = res.data.records || []
        this.total = res.data.total
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>
<style scoped lang="less">

.site-content {

  .notify {
    margin: 60px 0;
    border-radius: 3px;

    &
    > div {
      padding: 20px;
    }

  }


  .search-result {
    padding: 15px 20px;
    text-align: center;
    font-size: 20px;
    font-weight: 400;
    border: 1px dashed #ddd;
    color: #828282;
  }

}

.top-feature {
  width: 100%;
  height: auto;
  margin-top: 30px;

  .feature-content {
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
    position: relative;

    .feature-item {
      width: 32.9%;
    }

  }
}

.site-main {
  padding-top: 30px;

  &
  .search {
    padding-top: 0;
  }

}

.more {
  margin: 50px 0;

  .more-btn {
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    color: #ADADAD;
    border: 1px solid #ADADAD;
    border-radius: 20px;
    margin: 0 auto;
    cursor: pointer;

    &
    :hover {
      color: #8fd0cc;
      border: 1px dashed #8fd0cc;
    }

  }
}

/******/
@media (max-width: 800px) {
  .top-feature {
    display: none;
  }

  .site-main {
    padding-top: 40px;
  }

  .site-content {

    .notify {
      margin: 30px 0 0 0;
    }

    .search-result {
      margin-bottom: 20px;
      font-size: 16px;
    }
  }

}

/******/
</style>
