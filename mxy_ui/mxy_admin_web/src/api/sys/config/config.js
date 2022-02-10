import request from '@/utils/request'

/*列表查询*/
export function getSysConfigList(data) {
  return request({
    url: '/sysConfig/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addSysConfig(data) {
  return request({
    url: '/sysConfig/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editSysConfig(data) {
  return request({
    url: '/sysConfig/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteSysConfig(data) {
  return request({
    url: '/sysConfig/delete',
    method: 'post',
    data
  })
}
