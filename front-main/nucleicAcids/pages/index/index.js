
const app = getApp()
Page({
  data: {
    id:'',
    password:'',
  },
  nameInput(e){
    this.setData({
      id:e.detail.value
    })
  },
  onShow(){
    console.log();
    this.setData({
      id:wx.getStorageSync('id'),
      password:wx.getStorageSync('password'),
    })
  },
  passwordInput(e){
    this.setData({
      password:e.detail.value
    })
  },
  submit(){
    wx.setStorageSync('id',this.data.id)
    wx.setStorageSync('password',this.data.password)
    app.myrequest({
      url: '/user/nucleicAcidsLogin',
      method:'POST',
      data:{
        identity_card:this.data.id,
        password:this.data.password
      },
      success(res){
        if(res.data.code == 200){
          app.globalData.token = res.data.data
          wx.switchTab({
            url: '/pages/sample/sample',
          })
        }
      }
  })}
})
