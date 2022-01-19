import defaultSettings from '@/settings'

const title = defaultSettings.title || '研发自测系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
