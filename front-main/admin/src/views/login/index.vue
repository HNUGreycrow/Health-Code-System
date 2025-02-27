<template>
  <div class="login-container">
    <div class="bg" />
    <el-form v-show="mode" ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <!-- 右侧登录卡片 -->
      <div class="title-container">
        <h3 class="title">欢迎登陆</h3>
      </div>

      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
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
          style="width: 260px"
          :type="passwordType"
          placeholder="Password"
          name="password"
          show-password
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
      </el-form-item>

      <div class="test">
        <input style="margin-left: 10px;" type="checkbox">
        <div style="margin-left: 10px;"> 记住密码</div>
        <el-link type="primary" style="margin-left: 120px;" @click="handleMode"> 忘记密码</el-link>
      </div>
      <div class="bottom" style="cursor: pointer;" @click="handleLogin">
        登陆
      </div>

      <div class="tips" style="margin-left: 60px;">
        <span>登录即视为同意《</span>
        <span style="color: #4583FA;"> 服务协议</span>
        <span>》</span>
      </div>

    </el-form>
    <el-form v-show="!mode" ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <img class="add_img" style="height: 20%; margin-left: 8%;" src="../../assets/编组 16.png">
      <!-- 切换为忘记密码卡片 -->
      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          style="width: 260px"
          type="password"
          placeholder="Password"
          show-password
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.newpassword"
          style="width: 260px"
          type="password"
          placeholder="请确认新密码"
          show-password
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
      </el-form-item>

      <div class="bottom" style="cursor: pointer;" @click="handleMode">
        下一步
      </div>

    </el-form>
  </div>
</template>

<script >
import { validUsername } from '@/utils/validate'
import request from '@/utils/request'
import { setToken } from '@/utils/auth'
export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      // 验证账号
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      // 验证密码
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '123456789012345678',
        password: '123456',
        newpassword: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      mode: true,
      passwordType: 'password',
      redirect: undefined
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
    handleMode() {
      // 登录和忘记密码卡片的切换
      this.mode = !this.mode
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        // 登录接口
        if (valid) {
          this.loading = true
          console.log('asd')
          request({
            url: '/user/managerLogin',
            method: 'post',
            data: {
              identity_card: this.loginForm.username,
              password: this.loginForm.password
            }
          }).then(res => {
            setToken(res.data)
            this.loading = false
            this.$message({
              message: '登录成功',
              type: 'success',
              duration: 1000
            })
            setTimeout(() => {
              this.$router.push({ path: this.redirect || '/' })
            }, 1000)
          }).catch(res => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
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

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 30px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: #000;
      height: 30px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
    height: 40px;
    opacity: 1;
    border-radius: 27.5px;
    background: rgba(219, 237, 255, 1);
    margin-top: 20px;
  }
}
.bg{
  background: url('../../assets/bg.png');
  position: absolute;
  top: 150px;
  left: 100px;
  height: 350px;
  width: 500px;
  background-size:auto 100%;
  background-repeat: no-repeat;
}
.bottom{
  left: 0px;
  top: 0px;
  // width: 200px;
  height: 40px;
  opacity: 1;
  border-radius: 27.5px;
  background: linear-gradient(182.83deg, #4B7BF8 0%, #3991FF 100%);
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 4.8px;
  line-height: 40px;
  color: rgba(255, 255, 255, 1);
  text-align: center;
  vertical-align: center;
  margin: 50px auto 30px;
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  min-width: 1080px;
  // background-color: $bg;
  overflow: hidden;
  background: url('../../assets/loginbg.png');

  .login-form {
    position: relative;
    padding: 30px 35px 0;
    // margin: 100px 70% 0 0;
    left: 60%;
    top: 100px;
    overflow: hidden;
    background-color: #fff;
    height: 300px;
    width: 350px;
    height: 400px;
    opacity: 1;
    border-radius: 30px;
    background: rgba(255, 255, 255, 0.7);

    border: 2px solid rgba(255, 255, 255, 1);

  }

  .test{
    display: flex;
    font-size: 14px;
    margin-top: 10px;

  }
  .tips {
    display: flex;

    font-size: 12px;
    margin-bottom: 10px;

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
      margin: 0px auto 30px;
      text-align: center;
      opacity: 1;
      /** 文本1 */
      font-size: 26px;
      font-weight: 700;
      letter-spacing: 0px;
      line-height: 43.44px;
      color: rgba(0, 0, 0, 1);
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
