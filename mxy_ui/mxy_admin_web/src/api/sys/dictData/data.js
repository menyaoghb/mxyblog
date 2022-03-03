import request from '@/utils/request'

/*列表查询*/
export function getSysDictDataList(data) {
  return request({
    url: '/sysDictData/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addDictData(data) {
  return request({
    url: '/sysDictData/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editDictData(data) {
  return request({
    url: '/sysDictData/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteDictData(data) {
  return request({
    url: '/sysDictData/delete',
    method: 'post',
    data
  })
}
/*列表查询*/
export function getDictData(data) {
  return request({
    url: '/sysDictData/getDicData',
    method: 'post',
    data
  })
}
