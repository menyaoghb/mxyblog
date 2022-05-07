<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="always">
          <div class="grid-content bg-purple">
            <el-divider content-position="left">账号</el-divider>
            <div class="dashboard-text">{{ name }}</div>
            <el-divider content-position="left">角色</el-divider>
            <div class="dashboard-text"><span v-for="role in roles" :key="role">{{ role }}</span></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6"><div class="grid-content bg-purple">
        <el-card shadow="always">
          <el-divider content-position="left">时间</el-divider>
          <el-progress type="circle" :percentage="66"></el-progress>
        </el-card>
      </div>
      </el-col>
      <el-col :span="6"><div class="grid-content bg-purple">
        <el-carousel :interval="4000" type="card" height="200px">
          <el-carousel-item v-for="(item,index) in carouselList" :key="index">
            <router-link  target="_blank" :to="item.path">
              <div class="img-box">
                <div>{{ item.name }}</div>
                <img width="200" height="250" :src="item.src" alt="">
              </div>
            </router-link>
          </el-carousel-item>
        </el-carousel>
      </div>
      </el-col>
      <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="8"><div class="grid-content bg-purple">
        <el-divider content-position="left">进度</el-divider>
        <div class="block">
          <el-timeline>
            <el-timeline-item color="#0bbd87" v-for="index in timeLine" :timestamp="index.timestamp" placement="top">
              <el-card>
                <h4>{{index.content}}</h4>
                <p>{{index.user}}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div></el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import clip from "../../../../mxy_system_web/src/utils/clipboard";

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  data() {
    return {
      timeLine: [{
        'content':'更新 Github 模板5',
        'user':'孟小耀 提交于 2022/01/25 09:46:12',
        'timestamp':'2022/01/25',
      },{
        'content':'更新 Github 模4',
        'user':'孟小耀 提交于 2022/01/23 15:46:12',
        'timestamp':'2022/01/23',
      },{
        'content':'更新 Github 模板3',
        'user':'孟小耀 提交于 2022/01/21 23:46:12',
        'timestamp':'2022/01/21',
      },{
        'content':'更新 Github 模板2',
        'user':'孟小耀 提交于 2022/01/18 22:46:12',
        'timestamp':'2022/01/18',
      },{
        'content':'更新 Github 模板1',
        'user':'孟小耀 提交于 2022/01/15 21:46:12',
        'timestamp':'2022/01/15',
      }],
      carouselList: [
        {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/0570c3e1-b06c-4093-9efd-9a0c83fafc3e'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/e0568183-7cc6-48e9-8137-cd762498ea9a'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/886f9f6a-172e-4693-84f1-4b0befa4b6a2'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/3744eda2-c49d-4259-a1f0-cdbb2752abec'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/da233a3b-533c-44be-a102-b02b10acbd35'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/5eb48b31-778c-4f52-8bbe-9a0bed10d119'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/340ea8ca-fc27-4e23-92cb-f3a575adfe8f'
        }, {
          name: ' ',
          path: { path:'/',query:{type : ''}},
          src: 'http://mxy.mxyit.com/f060914c-3ed1-412e-b7ed-227ac711f88d'
        }
      ]//旋转图片
    }
  },
  methods: {
    handleCopy(text, event) {
    }
  }
}
</script>

<style lang="scss" scoped>
.grid-content{
  width: 100%;
  height: 155px;
}
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.block{
  width: 100%;
  height: 400px;
  overflow: auto
}
.el-col {
  border-radius: 4px;
}
.dashboard {
  &-container {
    margin: 30px;
  }
}
/*滚动条样式*/
.block::-webkit-scrollbar{
  width : 2px;
  height: 6px;
}
.block::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius   : 10px;
  background-color: skyblue;
  background-image: -webkit-linear-gradient(45deg,rgba(255, 255, 255, 0.2) 25%,transparent 25%,transparent 50%,rgba(255, 255, 255, 0.2) 50%,rgba(255, 255, 255, 0.2) 75%,transparent 75%,transparent);
}
.block::-webkit-scrollbar-track{
  /*滚动条里面轨道*/
  box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.2);
  background   : #ededed;
  border-radius: 10px;
}
.block::-webkit-scrollbar-thumb:hover{
  background-color: #98e3ab;
}
</style>
