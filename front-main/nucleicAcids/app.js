// app.js
App({
  async onLaunch() {
  },

  
  myrequest(options){
    wx.request({
      url: 'http://localhost:8090'+options.url,
      method:options.method,
      header:{
        Authorization:this.globalData.token,
      },
      data:options.data,
      success(res){
        if(res.data.code != 200)
        wx.showToast({
          title: res.data.message,
          icon:'none'
        })
        options.success&&options.success(res)
      },
      fail(res){
        wx.showToast({
          title: res.data.message,
          icon:'none'
        })
        options.fail&&options.fail(res)
      },
      complete:(res)=>{
        console.log({
          url: options.url,
          method:options.method,
          header:{
            Authorization:this.globalData.token,
          },
          data:options.data,},res);
        options.complete&&options.complete(res)
      }
    })
  },
  globalData: {
    token: null,
  }
})
