import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
    return request({
        url: '/sysCountry/getList',
        method: 'post',
        data
    })
}
/*新增*/
export function add(data) {
    return request({
        url: '/sysCountry/add',
        method: 'post',
        data
    })
}
/*修改*/
export function edit(data) {
    return request({
        url: '/sysCountry/edit',
        method: 'post',
        data
    })
}
/*删除*/
export function deleteData(data) {
    return request({
        url: '/sysCountry/delete',
        method: 'post',
        data
    })
}
