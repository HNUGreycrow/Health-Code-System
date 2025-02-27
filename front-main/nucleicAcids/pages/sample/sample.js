
const app = getApp()
// pages/sample/sample.js
Page({
  data: {
    tubeid:'',
    kind:'',
    total:'',
    now:0
  },
  clicktube(){
     
    wx.scanCode({
      scanType:["barCode"],
      success:(e)=>{
        console.log(e);
        const tubeid = e.result
        const kind =Number(tubeid[0]) 
        const temp = [1,10,20]
        const total = temp[kind]
        const now = 0;
        this.setData({
          tubeid,kind,total,now
        })
				console.log(this.data)
        console.log("tubeid:",this.data.tubeid)
      },
			
    })
  },
  clickqr(){
    wx.scanCode({
      success:(e)=>{
        const token = e.result
        const kind = this.data.kind
        const tubeid = this.data.tubeid
        const test_address = '暂无'
        app.myrequest({
          url: '/nucleic-acids/addNucleicAcidTestRecordByToken',
          method:'POST',
          data:{
            token,kind,tubeid,test_address
          },
          success:(res)=>{
            if(res.data.code == 200){
              wx.showToast({
                title: '采样完成',
              })
              this.setData({
                now:this.data.now+1
              })
              if(this.data.now >= this.data.total)
              {
                wx.showModal({
                  content: '试管全部已采样，请开封新试管',
                  complete: (res) => {
                    this.setData({
                      tubeid:''
                    })
                  }
                })
              }
            }
          }
        })
      }
    })},
  onShow() {
    if(typeof this.getTabBar === 'function' && this.getTabBar()){
      this.getTabBar().setData({
        selected:0
      })
    }
  },
  clickid(){
    wx.showModal({
      title: '输入身份证号',
      content: '',
      editable:true,
      complete: (res) => {    
        if (res.confirm) {
          const identity_card = res.content
          const kind = this.data.kind
          const tubeid = this.data.tubeid
          const test_address = '暂无'
					app.myrequest({
						url: '/user/getUserByID',
						method: 'GET',
						data :{
							identity_card
						},
						success:(res)=>{
							if(res.data.code == 200){
								app.myrequest({
								  url: '/nucleic-acids/addNucleicAcidTestRecordByID',
								  method:'POST',
								  data:{
								    identity_card,kind,tubeid,test_address
								  },
								  success:(res)=>{
								    if(res.data.code == 200){
								      wx.showToast({
								        title: '采样完成',
								      })
								      this.setData({
								        now:this.data.now+1
								      })
								      if(this.data.now >= this.data.total)
								      {
								        wx.showModal({
								          content: '试管全部已采样，请开封新试管',
								          complete: (res) => {
								            this.setData({
								              tubeid:''
								            })
								          }
								        })
								      }
								    }
								  }
								})
							} else {
								// wx.navigateTo({
								// 	url: ""
								// })
							}
						}
					})
        }
      }
    })
  }

})