import request from '@/utils/request'
import requestNew from '@/utils/request-new'


/*列表查询*/
export function getSysUserList(data) {
  return request({
    url: '/sysUser/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addUser(data) {
  return request({
    url: '/sysUser/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editUser(data) {
  return request({
    url: '/sysUser/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteUser(data) {
  return request({
    url: '/sysUser/delete',
    method: 'post',
    data
  })
}

// 用户头像上传
export function uploadPhoto(data) {
  return request({
    url: '/sysUserPhoto/uploadPhoto',
    method: 'post',
    data
  })
}

/*角色查询*/
export function getRoles() {
  return request({
    url: '/sysRole/getRoles',
    method: 'post'
  })
}

/*重置密码*/
export function resetPassword(data) {
  return request({
    url: '/sysUser/resetPassword',
    method: 'post',
    data
  })
}

/*修改*/
export function editUserStatus(data) {
  return request({
    url: '/sysUser/editUserStatus',
    method: 'post',
    data
  })
}

/*校验*/
export function verifyOldPassword(data) {
  return requestNew({
    url: '/sysUser/verifyOldPassword',
    method: 'post',
    data
  })
}

/*修改*/
export function updatePassword(data) {
  return requestNew({
    url: '/sysUser/updatePassword',
    method: 'post',
    data
  })
}
