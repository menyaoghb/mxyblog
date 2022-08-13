import request from '@/utils/request'

/*列表查询*/
export function getSysLogList(data) {
  return request({
    url: '/sysOperLog/getList',
    method: 'post',
    data
  })
}
/*详情*/
export function detailLog(data) {
  return request({
    url: '/sysOperLog/add',
    method: 'post',
    data
  })
}
/*统计日志*/
export function statisticsLogInfo(data) {
  return request({
    url: '/indexInfo/statisticsLogInfo',
    method: 'post',
    data
  })
}

