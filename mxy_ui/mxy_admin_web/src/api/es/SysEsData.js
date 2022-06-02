import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
    return request({
        url: '/sysEsData/getList',
        method: 'post',
        data
    })
}
/*新增*/
export function add(data) {
    return request({
        url: '/sysEsData/add',
        method: 'post',
        data
    })
}
/*修改*/
export function edit(data) {
    return request({
        url: '/sysEsData/edit',
        method: 'post',
        data
    })
}
/*删除*/
export function deleteData(data) {
    return request({
        url: '/sysEsData/delete',
        method: 'post',
        data
    })
}

export function getWorldTree() {
  return request({
    url: '/sysCountry/getWorldTree',
    method: 'post'
  })
}
