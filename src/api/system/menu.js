/*
 * @Author: your name
 * @Date: 2021-11-28 13:37:01
 * @LastEditTime: 2021-12-02 11:49:40
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \vue-element-admin-master\src\api\system\menu.js
 */
import request from '@/utils/request'

const api_name = '/admin/acl/permission'

export default {
  getPermissionTreeList(data) {
    return request({
      url: '/api/permission/queryAllMenu',
      method: 'POST',
      data:data
    })
  },
  // getNestedTreeList() {
  //   return request({
  //     url: '/api/permission',
  //     method: 'POST'
  //   })
  // },
  remove(menu) {
    return request({
      url: `/api/permission/removePermission`,
      method: "POST",
      data: menu
    })
  },
  saveLevelOne(menu) {
    return request({
      url: `/api/permission/save/`,
      method: "post",
      data: menu
    })
  },
  saveButtonLevelOne(menu) {
    return request({
      url: `/api/permission/saveButton`,
      method: "post",
      data: menu
    })
  },
  update(menu) {
    return request({
      url: `/api/permission/update`,
      method: "POST",
      data: menu
    })
  },
  updateButton(menu) {
    return request({
      url: `/api/permission/updateButton`,
      method: "POST",
      data: menu
    })
  },
  toAssign(data) {
    return request({
      url: `/api/permission/toAssign`,
      method: 'POST',
      data:data
    })
  },
  doAssign(data) {
    return request({
      url: `/api/permission/doAssign`,
      method: "post",
      data:data
    })
  }
}
