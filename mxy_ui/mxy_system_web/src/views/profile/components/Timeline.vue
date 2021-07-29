<template>
  <div class="block">
    <el-form>
      <el-form-item label="姓名">
        <el-input v-model.trim="user.name" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model.trim="user.email" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model.trim="user.name" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
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
          duration:  1500
        })
      }
    }
  }
</script>
