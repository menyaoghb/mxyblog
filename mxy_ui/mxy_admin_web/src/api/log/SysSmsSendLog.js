import request from '@/utils/request'

/*列表查询*/
export function getList(data) {
    return request({
        url: '/sysSmsSendLog/getList',
        method: 'post',
        data
    })
}
/*新增*/
export function add(data) {
    return request({
        url: '/sysSmsSendLog/add',
        method: 'post',
        data
    })
}
/*修改*/
export function edit(data) {
    return request({
        url: '/sysSmsSendLog/edit',
        method: 'post',
        data
    })
}
/*删除*/
export function deleteData(data) {
    return request({
        url: '/sysSmsSendLog/delete',
        method: 'post',
        data
    })
}
