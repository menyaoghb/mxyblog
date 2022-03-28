import request from '@/utils/request'

export function fetchList(params) {
    const data = {pageSize: 5, currentPage: params, status: "0"};
    return request({
        url: '/foreign/getList',
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
        url: '/foreign/getRandWord',
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
    return request({
        url: '/foreign/getDataById',
        method: 'get',
        params: {id: params}
    })
}

export function bookmarkList(params) {
    const data = {pageSize: 100, currentPage: params, status: "0"};
    return request({
        url: '/foreign/getBookmarkList',
        method: 'post',
        data
    })
}
