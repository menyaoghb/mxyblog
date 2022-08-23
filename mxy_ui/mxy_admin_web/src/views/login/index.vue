<template>
  <div class="login-container">
      <div class="title-container" style="text-align: left">
        <img src="http://mxy.mxyit.com/093ad0a8-c1b5-42a1-af9a-75a11287c148" class="mxy-logo">
      </div>
    <div class="word-s">
      <div class="word-t" :style="{color:colorStyle}">
        <span>{{text}}</span>
      </div>
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="账号"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
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
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-button v-show="show" :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">登入</el-button>
    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import {getRandWord} from "@/api/foreign";

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value.length===0) {
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
    return {
      loginForm: {
        username: 'admin',
        password: '123456'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      show: true,
      text: '',
      passwordType: 'password',
      redirect: undefined,
      colorStyle:"#ff6a00"
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    // this.getRandWord();
    // this.randomColor();
  },
  methods: {
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
            this.$router.push({ path: '/' })
            this.loading = false
            this.show = true
          }).catch(() => {
            this.loading = false
            this.show = true
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    /*输入建议查询*/
    getRandWord() {
      getRandWord().then(response => {
        this.text = response.data.value
      })
    },
    randomColor() {
      var col = "#";
      for (var i = 0; i < 6; i++) col+=parseInt(Math.random() * 16).toString(16);
      this.colorStyle = col;
    }
  }
}
</script>

<style lang="scss">

.mxy-logo{
  width: 150px;
  height: 150px;
}
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#000000;
$cursor: #ffffff;

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
      color: #ffffff;
      height: 47px;
      caret-color: $cursor;
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
/*———————————————————————————字体颜色随机变化———————————————————————————*/
$red: #fc5545;
$pink: #FCCCD1;
$green: #005E61;
$greenLight: #42BA80;
$yellow: #ffcf6f;
$orange: #F57859;
$orangeLight: #FABD91;
$greyLight: #ebebeb;
$greyDark: #2e2e2e;
$blue: #29A8BF;
$blueLight: #B8E3EB;
$blueDark: #142447;

$colors: $pink, $green, $greenLight, $yellow, $orange, $orangeLight, $blue, $blueLight, $blueDark;
$colorsText: $blueDark, $orangeLight, $blueLight, $red, $blueLight, $red, $blueLight, $blue, $pink;

$key: random( length($colors) );
$nthText: nth( $colorsText, $key );

$randomText: $nthText !default;

.boxTitle {
  animation: dynamicColor 1s linear infinite;
}

@keyframes dynamicColor {
//color:$randomText !important;
}
/*———————————————————————————字体颜色随机变化———————————————————————————*/

$bg:#ffffff;
$dark_gray:000000;
$light_gray:#eee;

.el-button--primary {
  color: #ffffff;
  background-color: #7151cc;
  border-color: #7151cc;
  opacity: 0.5;
}

.word-s{
  color: #ffffff;
  width: 100%;
  line-height: 40px;
  padding-top: 50px;
  margin: 0 auto;
  text-align: center;
  font-family: cursive;
  font-size: xx-large;
  font-style: italic;

  animation: fadeInAnimation ease 3s;
  animation-iteration-count: 1; /*设置动画播放次数*/
  animation-fill-mode: forwards; /*设置样式以在动画不播放时应用元素。forward是设置动画结束后，使用元素的结束属性值*/
}
@keyframes fadeInAnimation {
  0% {
    opacity: 0; /*设置不透明度*/
  }
  100% {
    opacity: 1;
  }
}
.word-t{
  width: 100%;
}

.login-container {
  min-height: 100%;
  width: 100%;
  //background-image: url(http://mxy.mxyit.com/53864988-127e-455e-911a-f6145677ddd1);
  //background-image: url(http://mxy.mxyit.com/8c8dfa4b-aaf2-41f5-b477-23ddd330aa87);
  //background-image: url(http://mxy.mxyit.com/53aea0e5-7cb6-4ec5-ab73-ce5504359abd);
  background-image: url(http://mxy.mxyit.com/d9b9511d-4174-420f-9104-3385ba636489);
  background-attachment:fixed;
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
    width: 520px;
    max-width: 100%;
    padding: 20px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    animation: fadeInAnimation ease 3s;
    animation-iteration-count: 1; /*设置动画播放次数*/
    animation-fill-mode: forwards; /*设置样式以在动画不播放时应用元素。forward是设置动画结束后，使用元素的结束属性值*/
  }

  .tips {
    font-size: 14px;
    color: #000000;
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
    text-align: center;

    .title {
      font-size: 26px;
      color: $light_gray;
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
