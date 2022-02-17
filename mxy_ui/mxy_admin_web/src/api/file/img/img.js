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
export function deletePicture(data) {
  return request({
    url: '/sysPicture/delete',
    method: 'post',
    data
  })
}

/*下载*/
export function downloadPicture(data) {
  return request({
    url: '/sysPicture/downloadPicture',
    method: 'post',
    data
  })
}

/*数据查询*/
export function getFileTypeList(data) {
  return request({
    url: '/sysPicture/getFileTypeList',
    method: 'post',
    data
  })
}

