import request from '@/utils/request'

//使用
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  });
}
// 退出登录
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  });
}
