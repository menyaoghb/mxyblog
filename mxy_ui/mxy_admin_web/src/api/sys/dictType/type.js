import request from '@/utils/request'

/*列表查询*/
export function getSysDictTypeList(data) {
  return request({
    url: '/sysDictType/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addDictType(data) {
  return request({
    url: '/sysDictType/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editDictType(data) {
  return request({
    url: '/sysDictType/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteDictType(data) {
  return request({
    url: '/sysDictType/delete',
    method: 'post',
    data
  })
}
