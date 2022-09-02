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
              <el-tab-pane label="个人信息" name="userInfo">
                <userInfo  :user="user"/>
              </el-tab-pane>
              <el-tab-pane label="密码修改" name="account">
                <account :user="user" />
              </el-tab-pane>
              <el-tab-pane label="心情语录" name="activity">
                <activity />
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
import UserInfo from './components/UserInfo'
import Account from './components/Account'
import userPhoto from './components/userPhoto'
import store from "@/store";


export default {
  name: 'Profile',
  components: { UserCard, Activity, UserInfo, Account ,userPhoto},
  data() {
    return {
      user: {
        nickName: store.getters.nickName,
        email: store.getters.email,
        phoneNumber: store.getters.phoneNumber,
        sex: store.getters.sex,
        roles: store.getters.roles,
        userId: store.getters.userId
      },
      activeTab: 'userInfo'
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  },
  methods: {
  }
}
</script>
