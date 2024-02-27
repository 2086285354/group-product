import request from '@/utils/request'

// 手机号登录方法
export function phone(phonenumber, code) {
  return request({
    url: '/auth/phone',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: { phonenumber, code }
  })
}

// 登录方法
export function login(username, password, code, uuid) {
  return request({
    url: '/auth/login',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: { username, password, code, uuid }
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/auth/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 刷新方法
export function refreshToken() {
  return request({
    url: '/auth/refresh',
    method: 'post'
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/system/user/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'delete'
  })
}
//手机验证码
export function getPhoneCode(data){
  const phonenumber = data.phonenumber
  return request({
    url: '/auth/getPhoneCode?phonenumber='+phonenumber,
    method: 'get'
  })
}
// 获取验证码
export function getCodeImg() {
  return request({
    url: '/code',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
