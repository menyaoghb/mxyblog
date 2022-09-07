const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  userId: state => state.user.userId,
  name: state => state.user.name,
  nickName: state => state.user.nickName,
  email: state => state.user.email,
  phoneNumber: state => state.user.phoneNumber,
  sex: state => state.user.sex,
  loginIp: state => state.user.loginIp,
  loginDate: state => state.user.loginDate,
  remark: state => state.user.remark,
  roles: state => state.user.roles,
  permission_routes: state => state.permission.routes
}
export default getters
