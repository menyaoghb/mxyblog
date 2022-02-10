import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
  return request({
    url: '/beautifulWords/getList',
    method: 'post',
    data
  })
}
/*数据查询*/
export function getAdviceList(data) {
  return request({
    url: '/beautifulWords/getAdviceList',
    method: 'post',
    data
  })
}
/*新增*/
export function addUser(data) {
  return request({
    url: '/beautifulWords/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editUser(data) {
  return request({
    url: '/beautifulWords/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteUser(data) {
  return request({
    url: '/beautifulWords/delete',
    method: 'post',
    data
  })
}
