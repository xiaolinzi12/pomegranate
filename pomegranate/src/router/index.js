import Vue from 'vue';
import Router from 'vue-router';
import Login from '@/view/Login'
import Home from '@/view/Home'
import {fetch} from '@/utils/localStorage'


Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/Home',
      name: 'home',
      component: Home
    }
  ]
});

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next();
  } else {
    let token = fetch('Authorization');
    if (null == token || token === 'null' || token === '') {
      next('/login');
    } else {
      next();
    }
  }
});

export default router;
