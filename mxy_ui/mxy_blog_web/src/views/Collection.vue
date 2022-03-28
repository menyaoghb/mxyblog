<template>
  <div class="home">
    <div class="site-content animate" style="width: 100%;">
      <!--通-->
      <div class="bookmarkContent" style="margin-top: 100px;    text-align: center;">
        <div style="display: inline-block;vertical-align: middle;">
          <h1 style="font-size: 32px;padding-bottom: 30px;position: relative;font-weight: 500;color: #000000;font-family: cursive;">
            收藏</h1>
          <template>
            <el-backtop target=".bookmarkContent">
              <div id="up">
                <i class="el-icon-caret-top"></i>
              </div>
            </el-backtop>
          </template>
          <div>
            <div class="menu-item header-search">
              <div class="header-search">
                <i class="iconfont iconsearch" @click.stop="click"></i>
                <input ref="searchInput" :class="{'show':show}" v-model="keyWords" type="text" @click.stop=""
                       @input="handleQuery"/>
              </div>
            </div>
            <template v-for="item in bookmarkList">
              <el-divider></el-divider>
              <a :href="item.url" target="_blank" class="header-title" v-html="item.name"></a>
            </template>
          </div>
          <div style="text-align: center">
            <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage"
                        :limit.sync="listQuery.pageSize"
                        @pagination="bookmarksList"/>
          </div>
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
import {bookmarkList} from '../api'
import Pagination from '@/components/Pagination'

export default {
  name: 'Home',
  props: ['cate', 'words'],
  data() {
    return {
      features: [],
      bookmarkList: [],
      currPage: 1,
      total: 0, // 总条数
      listQuery: {
        pageSize: 1000, currentPage: 1, status: "0"
      },
      show: false,
      keyWords: '',
      results: []
    }
  },
  watch: {
    show(value) {
      if (value) {
        document.body.addEventListener('click', this.close)
      } else {
        document.body.removeEventListener('click', this.close)
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
  mounted() {
    this.bookmarksList();
  },
  methods: {
    bookmarksList() {
      bookmarkList(this.listQuery.currentPage).then(res => {
        this.bookmarkList = res.data.records || []
        this.total = res.data.total
      }).catch(err => {
        console.log(err)
      })
    },
    click() {
      this.searchValue = ''
      this.show = !this.show
      if (this.show) {
        this.$refs.searchInput && this.$refs.searchInput.focus()
      }
    },
    close() {
      this.$refs.searchInput && this.$refs.searchInput.blur()
      this.show = false
    },
    clearTimer() {
      if (this.timer) {
        clearTimeout(this.timer)
      }
    },
    handleQuery(event) {
      this.clearTimer()
      this.timer = setTimeout(() => {
        bookmarkList(this.listQuery.currentPage).then(res => {
          this.changeColor(res.data.records)
        }).catch(err => {
          console.log(err)
        })
        // }
      }, 1000)
    },
    changeColor(resultsList) {
      this.bookmarkList = [];
      resultsList.map((item, index) => {
        if (this.keyWords && this.keyWords.length > 0) {
          // 匹配关键字正则
          let replaceReg = new RegExp(this.keyWords, 'g')
          // 高亮替换v-html值
          let replaceString = '<span class="search-text">' + this.keyWords + '</span>'
          resultsList[index].name = item.name.replace(
              replaceReg,
              replaceString
          )
          if (item.name.includes(this.keyWords)) {
            this.bookmarkList.push(resultsList[index]);
          }
        } else {
          this.bookmarkList = resultsList;
        }
      })
    }
  }
}
</script>
<style lang="less">

.search-text {
  color: red;
}

.header-search {
  display: inline-block;
  position: relative;

  i {
    font-size: 18px;
    position: relative;
    top: 3px;
  }

  input {
    border: none;
    outline: none;
    overflow: hidden;
    background: transparent;
    height: 30px;
    width: 0;
    transition: .2s all;

    &.show {
      width: 200px;
      margin-left: 10px;
    }

    &:focus {
      border-bottom: 1px solid #8fd0cc;
    }
  }
}

#up {
  height: 100%;
  width: 100%;
  background-color: #f2f5f6;
  box-shadow: 0 0 6px rgba(0, 0, 0, .12);
  text-align: center;
  line-height: 40px;
  color: #1989fa;
}

.bookmarkContent {
  height: 100vh;
  overflow-x: hidden;
}

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

</style>
