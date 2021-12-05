/*
 * @Author: your name
 * @Date: 2021-11-30 16:53:27
 * @LastEditTime: 2021-12-04 19:18:55
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \vue-element-admin-master\src\utils\auth.js
 */
import Cookies from 'js-cookie'

// const TokenKey = 'Admin-Token'
const TokenKey = 'Token'
export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
