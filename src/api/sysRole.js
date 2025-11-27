import request from '@/utils/request'

// 分页查询角色数据
// 分页查询角色数据 - 最佳实践
export const GetSysRoleListByPage = (queryDto, pageNum, pageSize) => {
    return request({
        url: '/admin/system/sysRole/findByPage',
        method: 'post',
        // 查询条件放入请求体
        data: queryDto, 
        // 分页参数放入URL查询参数
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        }
    })
}