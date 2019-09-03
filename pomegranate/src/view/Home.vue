<template>
  <div class="login-container">
    <h4>Welcome to HomePage</h4>
    <el-button type="primary" @click="logout">退出登录</el-button>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import {Message} from 'element-ui'

  export default {
    component: 'Home',
    data() {
      return {
        loginForm: {},
        userToken: '',
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
      ...mapActions('User', ['handleLogout']),
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      logout() {
        this.handleLogout().then(() => {

          this.$router.push('/login');

        }).catch(() => {

          Message({

            message: '退出失败',
            type: 'error',
            duration: 5 * 1000

          })

        })
      }
    }
  }
</script>
<style>
  .login-container {
    display: flex;
    justify-content: center;
  }
</style>
