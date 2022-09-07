<template>
  <el-form ref="passForm" :model="passForm" :rules="passRules">
    <!--    <el-form-item label="原密码" prop="oldPassword">-->
    <!--      <el-input @blur="verifyOldPassword" show-password v-model.trim="passForm.oldPassword"/>-->
    <!--    </el-form-item>-->
    <el-form-item label="新密码" prop="firstPassword">
      <el-input show-password v-model.trim="passForm.firstPassword"/>
    </el-form-item>
    <el-form-item label="确认密码" prop="secondPassword">
      <el-input show-password v-model.trim="passForm.secondPassword"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">修改</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {validatePassword} from '@/utils/validate'
import {verifyOldPassword, updatePassword} from "@/api/sys/user/user";

export default {
  data() {
    return {
      passForm: {
        oldPassword: "",
        firstPassword: "",
        secondPassword: ""
      },
      passRules: {
        oldPassword: [
          {required: true, message: '请输入原密码', trigger: 'blur'}
        ],
        firstPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {validator: validatePassword, trigger: 'blur'}
        ],
        secondPassword: [{
          required: true,
          message: '确认密码',
          trigger: 'blur'
        }, {
          validator: (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请再次输入新密码'))
            } else if (value !== this.passForm.firstPassword) {
              callback(new Error('两次输入密码不一致'))
            } else {
              callback()
            }
          },
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    // 校验旧密码
    verifyOldPassword() {
      verifyOldPassword(this.passForm).then((data) => {
        console.log(data)
        if (data.code === 200) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        } else {
          this.passForm.oldPassword = ""
          this.$message({
            showClose: true,
            message: data.data,
            type: 'warning'
          })
        }
      })
    },
    // 提交修改
    submit() {
      this.$refs['passForm'].validate((valid) => {
        if (valid) {
          updatePassword({password: this.passForm.secondPassword}).then((data) => {
            console.log(data)
            if (data.code === 200) {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
            } else {
              this.$message({
                showClose: true,
                message: data.data,
                type: 'warning'
              })
            }
          })
        }
      })
    }
  }
}
</script>
