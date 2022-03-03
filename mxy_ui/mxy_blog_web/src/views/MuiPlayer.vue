<template>
  <div class="home">
    <div class="site-content animate" style="width: 100%;">
      <!--通-->
      <div style="margin-top: 100px;    text-align: center;">
        <div style="display: inline-block;vertical-align: middle;">
          <h1 style="font-size: 32px;padding-bottom: 30px;position: relative;font-weight: 500;color: #000000;font-family: cursive;">
            视频播放</h1>
          <div id="mui-player" style="max-width: 700px;margin: auto">
            <!-- 自定义头部导航 -->
            <template slot="likeButton">
              <svg t="1607603509184" class="icon" viewBox="0 0 1024 1024" version="1.1"
                   xmlns="http://www.w3.org/2000/svg" p-id="2765">
                <path
                    d="M333.90592 437.4016H174.7968v-187.5968H40.50944v524.288H174.7968v-219.648h159.10912v219.648h134.28736v-524.288H333.90592v187.5968z m639.90784-22.4256a235.24352 235.24352 0 0 0-37.60128-85.1968 179.05664 179.05664 0 0 0-91.98592-68.5056 275.69152 275.69152 0 0 0-80.896-11.4688H534.84544v524.288h229.84704c77.9776 0 139.776-34.5088 177.2544-99.84 28.01664-48.3328 41.6256-106.8032 41.59488-174.2848a397.4144 397.4144 0 0 0-9.728-84.992z m-141.4656 179.8144a100.57728 100.57728 0 0 1-34.12992 46.6944 87.808 87.808 0 0 1-52.56192 14.9504h-78.848v-288.9728h78.848a134.30784 134.30784 0 0 1 52.224 8.704 56.32 56.32 0 0 1 27.70944 23.552c13.55776 22.9376 21.41184 58.7776 21.36064 107.2128a236.75904 236.75904 0 0 1-14.60224 87.8592z"
                    p-id="2766" fill="#ffffff"></path>
              </svg>
            </template>
            <!-- 自定义弹出右侧栏窗口 -->
            <template slot="HD">
              <div
                  style="color: white;display: flex;flex-direction: column;align-items: center;justify-content: center;height: 100%;">
                <p type="toggleHD" style="cursor: pointer">蓝光1080P</p>
                <p type="toggleHD" style="cursor: pointer">超清</p>
                <p type="toggleHD" style="cursor: pointer">高清</p>
                <p type="toggleHD" style="cursor: pointer">标清</p>
              </div>
            </template>
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
import {fetchFocus, fetchList} from '../api'
import Pagination from '@/components/Pagination' // 分页
import MuiPlayerDesktopPlugin from 'mui-player-desktop-plugin'
import 'mui-player/dist/mui-player.min.css'
import MuiPlayer from 'mui-player'


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
    this.initFun();
  },
  methods: {
    initFun() {
      var mp = null;
      var config = {
        container: '#mui-player',
        title: '星辰大海',
        muted:true,
        src: 'http://mxy.mxyit.com/videos/VID_20220302_161849.mp4',
        poster: 'https://muiplayer.oss-cn-shanghai.aliyuncs.com/static/image/poster.jpg',
      };
      config.plugins = [
        typeof MuiPlayerDesktopPlugin == 'function' ? new MuiPlayerDesktopPlugin({
          thumbnails: { // 缩略图配置
          },
        }) : {},
      ]
      config.custom = {
        headControls: [ //自定义播放器头控件列表，参数接受一个数组配置选项，最大可配置对象为5个.
          {
            slot: 'likeButton',
            click: function (e) {
              mp.showRightSidebar('HD');
            }
          }
        ]
      }
      mp = new MuiPlayer(config);
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

</style>
