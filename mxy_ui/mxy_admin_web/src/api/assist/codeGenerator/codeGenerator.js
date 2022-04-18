import request from '@/utils/request'

/*表名查询*/
export function getTableName() {
  return request({
    url: '/codeGenerator/getTableName',
    method: 'post'
  })
}
