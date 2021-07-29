<template>
  <el-form>
    <el-form-item label="旧密码">
      <el-input v-model.trim="user.name" />
    </el-form-item>
    <el-form-item label="新密码">
      <el-input v-model.trim="user.email" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          email: ''
        }
      }
    }
  },
  methods: {
    submit() {
      this.$message({
        message: '修改成功',
        type: 'success',
        duration: 1500
      })
    }
  }
}
</script>
