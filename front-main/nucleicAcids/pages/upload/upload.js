// pages/upload/upload.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tubeid:'',
    arr:['阴性','阳性'],
    index:'',
    org:''
  },
  clicktube(){
    wx.scanCode({
      scanType:["barCode"],
      success:(e)=>{
        console.log(e);
        const tubeid = e.result
        this.setData({
          tubeid,
        })
      }
    })
  },
  bindKeyInput(e){
    this.setData({
      org:e.detail.value
    })
  },
  bindKeySelect(e){
    this.setData({
      index:e.detail.value
    })
  },
  submit(){
        const tubeid = (this.data.tubeid)
        const kind = Number(tubeid[0]) 
        const result = Number(this.data.index)
        const testing_organization = this.data.org
        app.myrequest({
          url: '/nucleic-acids/enterNucleicAcidTestRecord',
          method:'put',
          data:[{
            result,kind,tubeid,testing_organization
          }],
          success:(res)=>{
            if(res.data.code == 200){
              wx.showToast({
                title: '提交成功',
              })
}}})}
            ,
  onShow() {
      if(typeof this.getTabBar === 'function' && this.getTabBar()){
          this.getTabBar().setData({
            selected:1
      })
    }
  },
})