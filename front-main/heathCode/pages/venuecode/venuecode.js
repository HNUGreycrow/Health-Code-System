// pages/venuecode/venuecode.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  onLoad() {
    wx.scanCode({
      success(res){
        console.log(res);
      }
    })
   
  },

})