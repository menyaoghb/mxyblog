<template>
  <div id="layout-header">
    <!--导航栏 LOGO-->
    <div class="site-logo">
      <router-link to="/">
        <img src="@/assets/dw.svg" alt="">
      </router-link>
    </div>
    <!--导航栏 内容-->
    <div class="site-menus">
      <!--搜索按钮-->
<!--      <div class="menu-item header-search">
        <header-search/>
      </div>-->
      <!--标题-->
      <div class="menu-item">
        <ul>
          <router-link target="_blank" to="/"><li><div class="title-line header-title">二零二二</div></li></router-link>
          <router-link target="_blank" to="/blog"><li><div class="title-line header-title">博客</div></li></router-link>
          <router-link target="_blank" to="/Original"><li><div class="title-line header-title">原创</div></li></router-link>
          <router-link target="_blank" to="/Photo"><li><div class="title-line header-title">相册</div></li></router-link>
          <router-link target="_blank" to="/Diary"><li><div class="title-line header-title">成长</div></li></router-link>
          <router-link target="_blank" to="/Collection"><li><div class="title-line header-title">收藏</div></li></router-link>
          <router-link target="_blank" to="/MuiPlayer"><li><div class="title-line header-title">视频</div></li></router-link>
          <li><div class="title-line"><el-badge :value="99999" :max="num" class="item"><a href="#" @click="open5" class="header-title">元气值</a></el-badge></div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderSearch from '@/components/header-search'
import {fetchCategory} from '../../api'

export default {
  name: "layout-header",
  components: {HeaderSearch},
  data() {
    return {
      lastScrollTop: 0,
      fixed: false,
      hidden: false,
      category: [],
      mobileShow: false,
      num: 0
    }
  },
  mounted() {
    this.open0();
  },
  methods: {
    open0() {
      if (sessionStorage.getItem("NUM")){
        this.num = Number(sessionStorage.getItem("NUM"));
      }
    },
    open1() {
      this.$message({
        message: '2022',
        type: 'success'
      });
    },
    open2() {
      this.$message({
        message: '博客',
        type: 'success'
      });
    },
    open3() {
      this.$message({
        message: '暴富',
        type: 'success'
      });
    },
    open4() {
      this.$message({
        message: '脱单',
        type: 'success'
      });
    },
    open5() {
      this.num++;
      sessionStorage.setItem("NUM",this.num);
      if (this.num % 10 === 0) {
        this.$message({
          message: '感谢',
          type: 'success'
        });
      }
    }
  }
}
</script>

<style scoped lang="less">

.header-title {
  padding: 12px 10px;
  color: #545454;
  font-weight: 500;
  font-size: 16px;
}

.title-line{
  position: relative;
}

.title-line:hover:after{
  left: 0%;
  width: 100%;
}

.menu-item ul li{
  display: inline-block;
}
.title-line:after{
  content: "";
  width: 0;
  height: 3px;
  background: #20a0ff;
  position: absolute;
  top: 90%;
  left: 50%;
  transition: all .9s;
}

/*头部 样式*/
#layout-header {
  position: fixed;
  top: 0;
  z-index: 9;
  width: 100%;
  height: 80px;
  padding: 0 80px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: .3s all ease;
  -webkit-transition: .3s all ease;
  -moz-transition: .3s all linear;
  -o-transition: .3s all ease;
  -ms-transition: .3s all ease;
}

/*导航栏 LOGO样式*/
.site-logo {
  text-align: center;
}

.site-logo img {
  width: 60px;
  height: 60px;
}

/*导航栏 样式*/
.site-menus {
  display: flex;
  align-items: center;
}

.menu-item {
  min-width: 60px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  position: relative;
}

/*自适应 宽度设置*/
@media (max-width: 960px) {
  #layout-header {
    padding: 0 20px;
  }
}

@media (max-width: 600px) {
  #layout-header {
    padding: 0 10px;
  }

  .site-menus {
    position: absolute;
    display: none;
    visibility: hidden;
    background-color: #F9F9F9;
    width: 100%;
    left: 0;
    top: 80px;
    z-index: -9;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .menu-item {
      position: relative;
      height: unset;

    }

  }

  .site-menus.mobileShow {
    display: inline-block;
    visibility: visible;
    z-index: 99;
  }

}
</style>
