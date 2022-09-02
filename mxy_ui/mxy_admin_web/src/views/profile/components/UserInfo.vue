<template>
  <div class="block">
    <el-form>
      <el-form-item label="姓名">
        <el-input v-model.trim="nickName" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model.trim="email" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model.trim="phoneNumber" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import store from "@/store";

  export default {
    data() {
      return {
        nickName: store.getters.nickName,
        email: store.getters.email,
        phoneNumber: store.getters.phoneNumber,
        sex: store.getters.sex,
        userId: store.getters.userId
      };
    },
    methods: {
      submit() {
        this.$message({
          message: '修改成功',
          type: 'success',
          duration:  1500
        })
      }
    }
  }
</script>
