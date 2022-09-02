<template>
  <el-form>
    <el-form-item label="原密码">
      <el-input show-password v-model.trim="user.oldPassword"/>
    </el-form-item>
    <el-form-item label="新密码">
      <el-input show-password v-model.trim="user.firstPassword"/>
    </el-form-item>
    <el-form-item label="确认密码">
      <el-input show-password v-model.trim="user.secondPassword"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import store from "@/store";
import {validateEmail, validateAccount, validatePassword} from '@/utils/validate'
import {addUser} from "@/api/sys/user/user";

export default {
  data() {
    return {
      user: {
        oldPassword: "",
        firstPassword: "",
        secondPassword: ""
      },
      rules: {
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {validator: validatePassword, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submit() {

      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addUser(this.temp).then(() => {
            this.$message({
              message: '新增成功',
              type: 'success'
            });
            this.dialogFormVisible = false
            this.getList();
          })
        }
      })

      this.$message({
        message: '修改成功',
        type: 'success',
        duration: 1500
      })
    }
  }
}
</script>
