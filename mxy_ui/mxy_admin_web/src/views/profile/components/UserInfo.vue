<template>
  <div class="block">
    <el-form ref="userForm" :model="userForm" :rules="userRules">
      <el-form-item label="姓名" prop="nickName">
        <el-input v-model.trim="userForm.nickName"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model.trim="userForm.email"/>
      </el-form-item>
      <el-form-item label="手机号" prop="phoneNumber">
        <el-input v-model.trim="userForm.phoneNumber"/>
      </el-form-item>
      <el-form-item label="备注">
        <span>{{ remark }}</span>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import store from "@/store";
import {validateEmail} from "@/utils/validate";
import {editUser} from "@/api/sys/user/user";

export default {
  data() {
    const validatePhone = (rule, value, callback) => {
      const reg = /^[1][3-9][0-9]{9}$/;
      if (value == '' || value == undefined || value == null) {
        callback(new Error('请输入手机号码'));
      } else {
        if ((!reg.test(value)) && value != '') {
          callback(new Error('请输入正确的手机号码'));
        } else {
          callback();
        }
      }
    }
    return {
      sex: store.getters.sex,
      remark: store.getters.remark,
      userForm: {
        userId: store.getters.userId,
        nickName: store.getters.nickName,
        email: store.getters.email,
        phoneNumber: store.getters.phoneNumber
      },
      userRules: {
        nickName: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
          {min: 2, max: 6, message: '长度在 2 到 6 个字符', trigger: 'change'}
        ],
        email: [
          {required: false, message: '请输入邮箱', trigger: 'blur'},
          {validator: validateEmail, trigger: 'change'}
        ],
        phoneNumber: [{required: false, trigger: 'change', validator: validatePhone}],
      }
    };
  },
  methods: {
    submit() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          editUser(this.userForm).then((data) => {
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
