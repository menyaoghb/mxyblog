<template>
  <div class="articles">
    <div class="site-content animate" style="display: flex;justify-content: center;align-items: center;">
      <el-backtop target=".layout-body">
        <i class="el-icon-caret-top"></i>
      </el-backtop>
      <main class="site-main" style="width: 60%">
        <article class="hentry">
          <!-- 文章头部 -->
          <header class="entry-header">
            <!-- 标题输出 -->
            <h1 class="entry-title">{{ postList.title }}</h1>
            <hr>
            <div class="breadcrumbs">
              <div id="crumbs" v-show="postList.author">
                作者：<span class="crumbs"><el-tag size="medium" effect="success">{{ postList.author }}</el-tag></span>
                来源：<span class="crumbs source"><el-tag size="medium" style="color: limegreen">{{ postList.source }}</el-tag></span>
                发布时间：<span class="crumbs">{{ postList.createTime | parseTime }}</span>
              </div>
            </div>
          </header>
<!--          <div-->
<!--              class="copy-text"-->
<!--              data-clipboard-target=".hljs"-->
<!--              @click="copy()"-->
<!--          >-->
<!--            复制-->
<!--          </div>-->
          <!-- 正文输出 -->
          <div id="code" class="entry-content" v-html="postList.content" v-highlight>
          </div>
          <!-- 文章底部 -->
          <section-title>
            <footer class="post-footer">
              <!-- 阅读次数 -->
              <div class="post-like" v-show="postList.pageViews">
                <i class="iconfont iconeyes"></i>
                <span class="count">{{ postList.pageViews }}</span>
              </div>
            </footer>
          </section-title>
        </article>
      </main>
    </div>
  </div>
</template>

<script>
import Banner from '@/components/banner'
import sectionTitle from '@/components/section-title'
import comment from '@/components/comment'
import menuTree from '@/components/menu-tree'
import {getDataById, fetchComment, fetchList} from '../api'

export default {
  name: 'articles',
  data() {
    return {
      showDonate: false,
      comments: [],
      menus: [],
      postList: {}
    }
  },
  components: {
    Banner,
    sectionTitle,
    comment,
    menuTree
  },
  methods: {
    copy() {
      var clipboard = new this.clipboard('.copy-text');
      clipboard.on('success', function(e) {
        e.clearSelection(); //选中时不显示选中的背景色
        this.$message({
          message: '复制成功',
          type: 'success'
        });
      });
      clipboard.on('error', function() {
        this.$message({
          message: '复制失败',
          type: 'error'
        });
      });
    },
    getComment() {
      fetchComment().then(res => {
        this.comments = res.data || []
      }).catch(err => {
        console.log(err)
      })
    },
    fetchH(arr, left, right) {
      if (right) {
        return arr.filter(item => item.offsetTop > left && item.offsetTop < right)
      } else {
        return arr.filter(item => item.offsetTop > left)
      }
    },
    // 生成目录
    createMenus() {
      let arr = []
      for (let i = 6; i > 0; i--) {
        let temp = []
        let e = document.querySelector(".entry-content").querySelectorAll(`h${i}`)
        for (let j = 0; j < e.length; j++) {
          let child = this.fetchH(arr, e[j].offsetTop, (j + 1 === e.length) ? undefined : e[j + 1].offsetTop)
          temp.push({
            h: i,
            title: e[j].innerText,
            id: e[j].id,
            offsetTop: e[j].offsetTop,
            child
          })
        }
        if (temp.length) {
          arr = temp
        }
      }
      this.menus = arr
    },

    getDataById() {
      getDataById(this.$route.params.id).then(res => {
        this.postList = res.data || {}
      }).catch(err => {
        console.log(err)
      })
    },

  },
  mounted() {
    //this.createMenus()
  },
  created() {
    //this.getComment();
    this.getDataById();
  }
}
</script>
<style scoped lang="less">

.copy-text{
  float: right;
  margin-right: 20px;
  cursor:pointer;
}

.source{
  background: #dcdfe6a8;
  border-radius: 5px;
}

.crumbs{
color: #777777;
}

.hljs {
  background: bisque;
}

.site-content {
  position: relative;

  .site-main {
    padding: 80px 0 0 0;
  }

}

#article-menus {
  position: sticky;
  top: 0;
  box-shadow: 0 2px 6px rgba(0, 0, 0, .1);
  border-radius: 3px;
  padding: 15px;
  width: 300px;
  transform: translateX(-120%) translateY(150px);
  font-size: 14px;
}

article.hentry {

  .entry-header {

    .entry-title {
      text-align: center;
      font-size: 23px;
      font-weight: 600;
      color: #000000;
      margin: 0.67em 0;
      line-height: 40px;

      &
      :before {
        content: "#";
        margin-right: 6px;
        color: #d82e16;
        font-size: 20px;
        font-weight: 600;
      }

    }

    hr {
      height: 1px;
      border: 0;
      background: #EFEFEF;
      margin: 15px 0;
    }

    .breadcrumbs {
      font-size: 14px;
      color: #D2D2D2;
      text-decoration: none;
      margin-bottom: 30px;
    }

  }

  .entry-content {
  }

  footer.post-footer {
    width: 100%;
    padding: 20px 10px;
    margin-top: 30px;
    height: 65px;
    position: relative;

    i {
      font-size: 18px;
      margin-right: 5px;
    }

    .post-like {
      float: right;
      margin: 7px 0 0 20px;
    }

    .post-share {
      float: right;
      list-style: none;
      margin-right: 20px;
    }

    .donate {
      float: left;
      line-height: 36px;
      border-radius: 100%;
      -webkit-border-radius: 100%;
      -moz-border-radius: 100%;
      border: 1px solid #2B2B2B;

      &
      :hover {
        border: 1px solid goldenrod;

        span {
          color: goldenrod;
        }

      }

      span {
        color: #2B2B2B;
        padding: 10px;
        position: relative;
        cursor: pointer;
      }

      .donate_inner {
        display: none;
        margin: 0;
        list-style: none;
        position: absolute;
        left: 80px;
        top: -40px;
        background: #FFF;
        padding: 10px;
        border: 1px solid #ddd;
        box-shadow: 0 2px 6px rgba(0, 0, 0, .08);
        border-radius: 3px;

        &
        .show {
          display: block;
        }

        li {
          float: left;
        }

        img {
          width: 100px;
        }

        p {
          text-align: center;
          font-size: 15px;
          color: #D2D2D2;
          line-height: 1rem;
        }

      }

      .donate_inner:after, .donate_inner:before {
        content: "";
        position: absolute;
        left: 0;
        bottom: 45%;
        margin-left: -8px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-right: 8px solid #fff;
      }

      .donate_inner:before {
        left: -1px;
        border-right: 8px solid #ddd;
      }

    }

    .post-tags {
      margin: 7px 0 0 20px;
      float: left;
      text-transform: uppercase;

      a:hover {
        color: #ff6d6d;
      }

    }
  }

  .open-message {
    margin: 50px 0;
    position: relative;
    background: #2B2B2B;
    padding: 10px 30px;
    border-radius: 3px;
    font-size: 14px;
    color: #fff;

    &
    :after {
      content: "";
      border-left: 10px solid transparent;
      border-right: 10px solid transparent;
      border-bottom: 10px solid #2B2B2B;
      position: absolute;
      top: -8px;
      left: 48%;
    }

    p {
      margin: 10px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    a {
      color: #A0DAD0;
      padding: 0 5px;
    }

  }
}
</style>
