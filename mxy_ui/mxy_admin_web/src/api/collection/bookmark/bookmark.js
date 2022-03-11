import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
  return request({
    url: '/sysBookmarks/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function add(data) {
  return request({
    url: '/sysBookmarks/add',
    method: 'post',
    data
  })
}
/*修改*/
export function edit(data) {
  return request({
    url: '/sysBookmarks/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteData(data) {
  return request({
    url: '/sysBookmarks/delete',
    method: 'post',
    data
  })
}
