<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left">

      <div class="title-container">
        <h3 class="title">个人日常博客</h3>
      </div>

      <el-tabs v-show="isRegist" type="border-card" class="login-tab">
        <el-tab-pane><span slot="label"><i class="el-icon-monitor"></i> 系统账号登录</span>
          <el-form-item prop="username">
            <span class="svg-container">
              <svg-icon icon-class="user"/>
            </span>
            <el-input
              ref="username"
              v-model="loginForm.username"
              placeholder="请输入系统账号"
              name="username"
              type="text"
              tabindex="1"
              auto-complete="on"
            />
          </el-form-item>
          <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
            <el-input
              :key="passwordType"
              ref="password"
              v-model="loginForm.password"
              :type="passwordType"
              placeholder="密码"
              name="password"
              tabindex="2"
              auto-complete="on"
              @keyup.enter.native="handleLogin"
            />
            <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
          </el-form-item>
          <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:15px;"
                     @click.native.prevent="handleLogin">登录
          </el-button>
          <p class="tips">
            <a @click.prevent="isRegist=false" type="primary">手机验证码快捷登录</a>
          </p>
        </el-tab-pane>
        <!--        <el-tab-pane><span slot="label"><i class="el-icon-position"></i> 邮箱登录</span>-->

        <!--          <el-form-item label="邮箱" prop="email">-->
        <!--            <el-col :span="10">-->
        <!--              <el-input-->
        <!--                v-model="loginRules.email"-->
        <!--                placeholder="输入邮箱并点击发送验证码"-->
        <!--              />-->
        <!--            </el-col>-->
        <!--            <el-button-->
        <!--              :loading="codeLoading"-->
        <!--              :disabled="isDisable"-->
        <!--              size="small"-->
        <!--              round-->
        <!--              @click="sendMsg"-->
        <!--            >发送验证码-->
        <!--            </el-button>-->

        <!--            <span class="status">{{ statusMsg }}</span>-->
        <!--          </el-form-item>-->
        <!--          <el-form-item label="验证码" prop="code">-->
        <!--            <el-col :span="10">-->
        <!--              <el-input-->
        <!--                v-model="loginRules.code"-->
        <!--                maxlength="6"-->
        <!--                placeholder="请登录邮箱接收验证码"-->
        <!--              />-->
        <!--            </el-col>-->
        <!--          </el-form-item>-->
        <!--          <el-form-item>-->
        <!--            <el-button-->
        <!--              type="primary"-->
        <!--              style="width: 40%"-->
        <!--              @click="register"-->
        <!--            >登录-->
        <!--            </el-button>-->
        <!--          </el-form-item>-->

        <!--        </el-tab-pane>-->
      </el-tabs>

      <el-tabs v-show="!isRegist" type="border-card" class="login-tab">
        <el-tab-pane><span slot="label"><i class="el-icon-mobile-phone"></i> 手机号登录</span>
          <el-form
            ref="phoneForm"
            :model="phoneForm"
            :rules="loginRules"
            autocomplete="off"
            :hide-required-asterisk="true"
            size="medium"
          >
            <el-form-item prop="phoneNo">
              <span class="svg-container">
                <svg-icon icon-class="user"/>
              </span>
              <el-input
                v-model="phoneForm.phoneNo"
                placeholder="请输入手机号/自动注册"
              />
            </el-form-item>
            <div>
              <div style="width: 50%;display: inline-block;">
                <el-form-item prop="code">
              <span class="svg-container">
                <svg-icon icon-class="password"/>
              </span>
                  <el-input
                    v-model="phoneForm.code"
                    maxlength="6"
                    placeholder="请输入验证码"
                  />
                </el-form-item>
              </div>
              <div style="display: inline-block;margin-left: 20px;">
                <el-button
                  :loading="codeLoading"
                  :disabled="isDisable"
                  size="small"
                  round
                  @click="sendMsg"
                >短信验证
                </el-button>
                <span class="status">{{ statusMsg }}</span>
              </div>
            </div>
            <el-button
              :loading="loading"
              type="primary"
              style="width:100%;margin-bottom:15px;"
              @click.native.prevent="phoneLogin"
            >登录
            </el-button>
            <p class="tips">
              <a @click.prevent="isRegist=true" type="primary">账号密码登录</a>
            </p>
          </el-form>
        </el-tab-pane>
      </el-tabs>

    </el-form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能小于6位'))
      } else {
        callback()
      }
    }
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
      loginForm: {
        username: '',
        password: ''
      },
      phoneForm: {
        phoneNo: '',
        code: ''
      },
      emailForm: {
        email: '',
        code: ''
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}],
        phoneNo: [{required: true, trigger: 'blur', validator: validatePhone}],
        email: [{
          required: true,
          type: 'email',
          message: '请输入邮箱',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          type: 'string',
          message: '请输入验证码',
          trigger: 'blur'
        }],
        pwd: [{
          required: true,
          message: '创建密码',
          trigger: 'blur'
        }, {pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/, message: '密码必须同时包含数字与字母,且长度为 8-20位'}],
        cpwd: [{
          required: true,
          message: '确认密码',
          trigger: 'blur'
        }, {
          validator: (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请再次输入密码'))
            } else if (value !== this.ruleForm.pwd) {
              callback(new Error('两次输入密码不一致'))
            } else {
              callback()
            }
          },
          trigger: 'blur'
        }]
      },
      statusMsg: '',
      loading: false,
      isDisable: false,// 验证码按钮禁用
      codeLoading: false,// 验证码loading
      passwordType: 'password',
      redirect: undefined,
      isRegist: true
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    sendMsg: function () {
      const self = this
      let phonePass
      let timeRid
      if (timeRid) {
        return false
      }
      self.statusMsg = ''
      this.$refs['phoneForm'].validateField('phoneNo', (valid) => {
        phonePass = valid
      })
      // 向后台API验证码发送
      if (!phonePass) {
        self.codeLoading = true
        self.statusMsg = '验证码发送中...'
        axios({
          url: window.SITE_CONFIG['systemUrl'] + '/api/foreign/sms/customer/sendMessageCode',
          method: 'get',
          params: {phoneNo: self.phoneForm.phoneNo}
        }).then(res => {

          console.log(res)
          let result = res.data
          if (result.code == 200) {
            this.$message({
              showClose: true,
              message: '发送成功，验证码有效期5分钟',
              type: 'success'
            })
            let count = 60
            self.phoneForm.code = ''
            self.codeLoading = false
            self.isDisable = true
            self.statusMsg = `验证码已发送,${count--}秒后重新发送`
            timeRid = window.setInterval(function () {
              self.statusMsg = `验证码已发送,${count--}秒后重新发送`
              if (count <= 0) {
                window.clearInterval(timeRid)
                self.isDisable = false
                self.statusMsg = ''
              }
            }, 1000)
          } else {
            this.$message({
              showClose: true,
              message: result.data,
              type: 'warning'
            })
            this.isDisable = false
            this.statusMsg = ''
            this.codeLoading = false
          }

        }).catch(err => {
          console.log(err)
          this.isDisable = false
          this.statusMsg = ''
          this.codeLoading = false
          console.log(err.data)
        })
      }
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.show = false
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({path: '/'})
            this.loading = false
            this.show = true
          }).catch(() => {
            this.loading = false
            this.show = true
          })
        } else {
          console.log('操作异常')
          return false
        }
      })
    },
    phoneLogin() {
      this.$refs.phoneForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.show = false
          this.$store.dispatch('user/phoneLogin', this.phoneForm).then(() => {
            this.$router.push({path: '/'})
            this.loading = false
            this.show = true
          }).catch(() => {
            this.loading = false
            this.show = true
          })
        } else {
          console.log('操作异常')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: #000000 !important;
  }
}

.status {
  font-size: 12px;
  margin-left: 20px;
  color: #e6a23c;
}

.el-form-item--medium .el-form-item__content, .el-form-item--medium .el-form-item__label {
  line-height: 40px;
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px #e7e7e7 inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.el-button--primary {
  color: #ffffff;
  background-color: #7151cc;
  border-color: #7151cc;
  opacity: 0.5;
}

.login-container {
  min-height: 100%;
  width: 100%;
  background-image: url(http://mxy.mxyit.com/d9b9511d-4174-420f-9104-3385ba636489);
  background-attachment: fixed;
  overflow: hidden;

  background-size: cover;
  background-repeat: no-repeat;
  background-position: 50% 50%;
  background-color: #e8eaf2;
  position: fixed;
  right: 0;
  left: 0;
  top: 0;
  bottom: 0;

  .login-form {
    position: relative;
    width: 800px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    text-align: center;
    font-size: 14px;
    color: #6c757d !important;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: #7151cc;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>

<style scoped>
/* 修改验证器样式 */
::v-deep .el-form-item.is-error .el-input__inner {
  border-color: #889aa4;
}

::v-deep .el-form-item.is-error .el-input__validateIcon {
  color: #889aa4;
}

::v-deep .el-form-item__error {
  color: #e6a23c;
}
</style>
