import request from '@/utils/request'

/*列表查询*/
export function getSysRoleList(data) {
  return request({
    url: '/sysRole/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addRole(data) {
  return request({
    url: '/sysRole/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editRole(data) {
  return request({
    url: '/sysRole/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteRole(data) {
  return request({
    url: '/sysRole/delete',
    method: 'post',
    data
  })
}
/*角色状态修改*/
export function editRoleStatus(data) {
  return request({
    url: '/sysRole/editRoleStatus',
    method: 'post',
    data
  })
}
