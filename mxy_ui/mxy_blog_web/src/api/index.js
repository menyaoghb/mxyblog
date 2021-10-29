import request from '@/utils/request'

export function fetchList(params) {
    const data = {pageSize: 10, currentPage: 1};
    return request({
        url: '/sysArticle/getList',
        method: 'post',
        data
    })
}

export function fetchFocus() {
    return request({
        url: '/focus/list',
        method: 'get',
        params: {}
    })
}

export function fetchCategory() {
    return request({
        url: '/category',
        method: 'get',
        params: {}
    })
}

export function fetchFriend() {
    return request({
        url: '/friend',
        method: 'get',
        params: {}
    })
}

export function fetchSocial() {
    const data = {};
    return request({
        url: '/beautifulWords/getList',
        method: 'post',
        data
    })
}

export function fetchSiteInfo() {
    return request({
        url: '/site',
        method: 'get',
        params: {}
    })
}

export function fetchComment() {
    return request({
        url: '/comment',
        method: 'get',
        params: {}
    })
}

export function getDataById(params) {
    const data = {id: params};
    return request({
        url: '/sysArticle/getDataById',
        method: 'post',
        data
    })
}
