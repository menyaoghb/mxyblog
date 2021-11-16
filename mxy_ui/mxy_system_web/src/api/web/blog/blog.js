import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
  return request({
    url: '/sysArticle/getList',
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
export function addArticle(data) {
  return request({
    url: '/sysArticle/add',
    method: 'post',
    data
  })
}
/*修改*/
export function editArticle(data) {
  return request({
    url: '/sysArticle/edit',
    method: 'post',
    data
  })
}
/*删除*/
export function deleteArticle(data) {
  return request({
    url: '/sysArticle/delete',
    method: 'post',
    data
  })
}
