import request from '@/utils/request'

/*表名查询*/
export function getTableName() {
  return request({
    url: '/sysCodeGenerate/getTableName',
    method: 'post'
  })
}

/*代码生成*/
export function codeGenerate(data) {
  return request({
    url: '/sysCodeGenerate/codeGenerate',
    method: 'post',
    data
  })
}
