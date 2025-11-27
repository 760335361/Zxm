import { createI18n } from 'vue-i18n'
// 关键：替换 element-plus 语言包路径为兼容旧版本的格式
import elementZhCn from 'element-plus/dist/locale/zh-cn.mjs'
import elementEn from 'element-plus/dist/locale/en.mjs'

// 根据实际目录结构修改引入路径
// import zhLocale from './locales/zh-cn/index.js'
// import enLocale from './locales/en/index.js'
const getMessage = modules => {
  return Object.entries(modules).reduce((module, [path, mod]) => {
    const moduleName = path.replace(/^\.\/locales\/[\w-]+\/(.*)\.\w+$/, '$1')
    module[moduleName] = mod.default
    return module
  }, {})
}
const messages = {
  en: {
    // ...enLocale,
    ...getMessage(import.meta.globEager('./locales/en/**/*.js')),
    ...elementEn
  },
  zh: {
    ...getMessage(import.meta.globEager('./locales/zh-cn/**/*.js')),
    // ...zhLocale,
    ...elementZhCn
  }
}

export const getLanguage = () => {
  const chooseLanguage = localStorage.getItem('language')
  if (chooseLanguage) return chooseLanguage

  const language = navigator.language.toLowerCase()
  const locales = Object.keys(messages)
  for (const locale of locales) {
    if (language.indexOf(locale) > -1) {
      return locale
    }
  }
  return 'zh'
}

const i18n = createI18n({
  legacy: false,
  globalInjection: true,
  locale: getLanguage(),
  messages: messages
})

export default i18n