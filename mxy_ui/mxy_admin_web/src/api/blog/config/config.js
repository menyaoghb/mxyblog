import request from '@/utils/request'

/*列表查询*/
export function getSysWebConfigList(data) {
  return request({
    url: '/sysWebConfig/getList',
    method: 'post',
    data
  })
}
/*新增*/
export function addSysWebConfig(data) {
  return request({
    url: '/sysWebConfig/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editSysWebConfig(data) {
  return request({
    url: '/sysWebConfig/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteSysWebConfig(data) {
  return request({
    url: '/sysWebConfig/delete',
    method: 'post',
    data
  })
}
