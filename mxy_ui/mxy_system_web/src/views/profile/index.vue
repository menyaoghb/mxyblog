<template>
  <div class="app-container">
    <div v-if="user">
      <el-row :gutter="20">

        <el-col :span="6" :xs="24">
          <user-card :user="user" />
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="个人信息" name="activity">
                <activity />
                <div class="text-center">
                  <userAvatar :user="user" />
                </div>
              </el-tab-pane>
              <el-tab-pane label="心情语录" name="timeline">
                <timeline />
              </el-tab-pane>
              <el-tab-pane label="密码修改" name="account">
                <account :user="user" />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import UserCard from './components/UserCard'
import Activity from './components/Activity'
import Timeline from './components/Timeline'
import Account from './components/Account'
import userPhoto from './components/userPhoto'


export default {
  name: 'Profile',
  components: { UserCard, Activity, Timeline, Account ,userPhoto},
  data() {
    return {
      user: {},
      activeTab: 'activity'
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  },
  created() {
    this.getUser()
  },
  methods: {
    getUser() {
      this.user = {
        name: "阿云",
        role: "S级管理员",
        email: 'mxy@qq.com',
        avatar: "y"
      }
    }
  }
}
</script>
