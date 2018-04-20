//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    inputValue: '',
    name: "",
    password: "",
    rand: "",
    origin: ""
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value
    })

  },
  bindNameInput: function (e) {
    this.setData({
      name: e.detail.value
    })

  },
  bindRandInput: function (e) {
    this.setData({
      rand: e.detail.value
    })
  },
  bindPassWordInput: function (e) {
    this.setData({
      password: e.detail.value
    })
  },
  genPassword: function (e) {
    var randLength = this.data.rand.length
    var rand = this.data.rand
    var password = this.data.password 
    var motto = ""
    for (var i = 0; i < this.data.password.length; i++) {
      motto += String.fromCharCode(password.charAt(i).charCodeAt() + parseInt(rand.charAt(i%randLength)))
    }
    this.setData({
      motto: motto
    })
    var origin = ""
    for (var i = 0; i < this.data.motto.length; i++) {
origin += String.fromCharCode(motto.charAt(i).charCodeAt() - parseInt(rand.charAt(i % randLength)))
    }

    this.setData({
      origin: origin
    })
  }
})
