import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/post/list',
        method: 'get',
        params: params
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
