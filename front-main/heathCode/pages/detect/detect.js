// pages/detect/detect.js

const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[]
  },

  getData(){
    app.myrequest({
      url: '/nucleic-acids/getNucleicAcidTestRecord',
      method:'GET',
      success:(res)=>{
				  console.log(res)
          res.data.data.forEach(item=>{
          const temp =['green','red','organe']
          const text = ['阴性','阳性','未出']
					const time = item.createdAt.replace('T',' ')
          item.statuscolor = temp[item.result]
          item.text = text[item.result]
					console.log(time)
					item.createdAt = time
        })
        this.setData({
          list:res.data.data
        })
      }})
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getData()
  },
})