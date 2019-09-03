<template>
  <div class="login-container">
    <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="loginForm">
      <el-form-item label="username" prop="username">
        <el-input type="text" v-model="loginForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="password" prop="password">
        <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('loginForm')" :loading="loading">提交</el-button>
        <el-button @click="resetForm('loginForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

  import {login} from '@/api/login'
  import {Message} from 'element-ui'
  import {mapActions} from 'vuex';

  export default {
    component: 'Login',
    data() {
      return {
        loginForm: {},
        userToken: '',
        loading: false,
        rules: {
          username: [
            {required: true, trigger: 'blur', message: '用户名不能为空'}
          ],
          password: [
            {required: true, trigger: 'blur', message: '密码不能为空'}
          ]

        }
      };
    },
    methods: {
      // 注意加上module名称
      ...mapActions('User', ['handleLogin']),
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          let _this = this
          if (valid) {

            this.loading = true

            _this.handleLogin(_this.loginForm).then(() => {

              this.loading = false
              this.$router.push('/home');

            }).catch(() => {

              this.loading = false
              Message({

                message: '登录失败',
                type: 'error',
                duration: 5 * 1000

              })

            })

          } else {

            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style>
  .login-container {
    display: flex;
    justify-content: center;
  }


  .loginForm {
    width: 400px;
  }
</style>
