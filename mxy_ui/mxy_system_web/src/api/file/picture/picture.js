import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
  return request({
    url: '/sysPicture/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addUser(data) {
  return request({
    url: '/sysPicture/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editUser(data) {
  return request({
    url: '/sysPicture/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteUser(data) {
  return request({
    url: '/sysPicture/delete',
    method: 'post',
    data
  })
}

