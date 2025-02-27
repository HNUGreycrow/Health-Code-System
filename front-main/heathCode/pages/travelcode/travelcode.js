const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    created_at:'',
    identity_card:'',
    places:[],
  },

  /**
   * 生命周期函数--监听页面显示
   */
  getData(){
    app.myrequest({
      url: '/itinerary-code/getItinerary',
      method:'GET',
      success:(res)=>{
        const places= res.data.data.places
				console.log("res:",res)
        this.setData({
					identity_card:res.data.data.identityCard,
          created_at:res.data.data.created_at,
          places:res.data.data.places
          // identity_card:'999999999999999999'
        })
      }
    })
  },
  onShow() {
    this.getData()
  },
})