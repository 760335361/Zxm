import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { useApp } from '@/pinia/modules/app'

const service = axios.create({
  baseURL: 'https://45157bd8.r34.cpolar.top', // 1. http 改成 https（和后端接口协议一致）
  timeout: 10000,
  withCredentials: true,
})

// 拦截请求
service.interceptors.request.use(
  config => {
    const { authorization } = useApp()
    if (authorization) {
      config.headers.token = `${authorization.token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 拦截响应
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code == 208) {
      const redirect = encodeURIComponent(window.location.href)
      // router.push(`/login?redirect=${redirect}`)
      return Promise.reject(new Error(res.message || 'Error'))
    }
    return res 
  },
  async error => {
    if (error.response && error.response.status === 401) {
      const { authorization, clearToken, setToken } = useApp()
      if (!authorization || !authorization.refresh_token) {
        if (router.currentRoute.value.name === 'login') {
          return Promise.reject(error)
        }
        const redirect = encodeURIComponent(window.location.href)
        // router.push(`/login?redirect=${redirect}`)
        clearToken()
        setTimeout(() => {
          ElMessage.closeAll()
          try {
            ElMessage.error(error.response.data.msg)
          } catch (err) {
            ElMessage.error(error.message)
          }
        })
        return Promise.reject(error)
      }
      // 2. 修复 Token 刷新接口（3处修改）
      try {
        const res = await axios({
          method: 'PUT',
          url: '/admin/system/index/authorizations', // 改对路径（和后端接口一致）
          baseURL: 'https://45157bd8.r34.cpolar.top', // 显式指定 https 地址
          timeout: 10000,
          headers: {
            'token': authorization.refresh_token, // 改 Bearer 为 token 头（和请求拦截器一致）
          },
        })
        setToken({
          token: res.data, // 按后端返回格式调整（如果后端返回 {data: 新token}，就写 res.data.data）
          refresh_token: authorization.refresh_token,
        })
        return service(error.config)
      } catch (err) {
        const redirect = encodeURIComponent(window.location.href)
        // 3. 注释最后一处跳转（避免循环刷新）
        // router.push(`/login?redirect=${redirect}`)
        clearToken()
        return Promise.reject(error)
      }
    }

    ElMessage.closeAll()
    try {
      ElMessage.error(error.response.data.msg)
    } catch (err) {
      ElMessage.error(error.message)
    }

    return Promise.reject(error)
  }
)

export default service