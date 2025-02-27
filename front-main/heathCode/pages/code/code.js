var wxbarcode = require('../../utils/index.js')
// pages/code/code.js
const app = getApp()
Page({

  data: {
    name:'',
    status:'',
    statuscolor:'',
    created_at:'',
    result:'',
    resulttext:'',
    resultcolor:'',
    testing_organization:''
  },
  goto(event){
    const page = event.currentTarget.dataset.page;
    wx.navigateTo({
      url: '/pages/'+page+'/'+page,
    })
  },
  unfinished(){
    wx.showToast({
      title: '功能未开放，尽情期待',
      icon:"none"
    })
  },
  getData(){
    this.getLastNucleicAcidTestRecord()
  },
	ifLogin(){
		if(app.globalData.token==null){
			wx.showModal({
			  content: '用户未登录，请先进行登录', // 提示框的文字内容
			  icon:'none',
			  complete(){
			    wx.navigateTo({
			      url: '/pages/login/login',
			    })
			  },
			})
		} else {
			this.getHealthCode()
		}
	},
  getHealthCode(){
    app.myrequest({
      url: '/health-code/getCode',
      method:'GET',
      success:(data=>{
        
        if(data.data.code==400)
        wx.showModal({
          content: '未检测到用户信息，请先注册', // 提示框的文字内容
          icon:'none',
          complete(){
            wx.navigateTo({
              url: '/pages/'+'regist'+'/'+'regist',
            })
          },
        })
        if(data.data.code==200){
          const status = data.data.data.status
          const temp = ['green','yellow','red']
          const statuscolor = temp[status]
          const color = [ '#1b824d','#FFD700','#f60909'][status]
          
          wxbarcode.qrcode('qrcode',app.globalData.token,408,408,color)
          this.setData({
            name:data.data.data.name,
            status:status,
            statuscolor:statuscolor
          })
          this.getData()
        }
      }),
    })
  },

  getLastNucleicAcidTestRecord(){
    app.myrequest({
      url: '/nucleic-acids/getLastNucleicAcidTestRecord',
      method:'GET',
      success:(data=>{
        // data.data.data = {
        //   created_at:'2022-01-01',
        //   result:0,
        //   testing_organization:'huaguoshan'
        // }
        if(data.data.code==200 && data.data.data){
          const created_at = data.data.data.createdAt.replace('T',' ')
          const temp = ['阴性','阳性','未出']
          const tempcolor = ['green','red','orange']
          const result = data.data.data.result
          const resulttext = temp[result]
          const resultcolor = tempcolor[result]
          const testing_organization = data.data.data.testingOrganization

          this.setData({
            created_at,result,testing_organization,resulttext,resultcolor
          })
        }
      }),
    })
  },
  // getCode(){
  //   const wxLogin = () => {
  //     return new Promise((resolve) => {
  //       wx.login({
  //         success(res) {
  //           resolve(res.code);
		// 				console.log()
  //         }
  //       });
  //     });
  //   }
  //   wxLogin().then(code=>{
  //     this.getToken(code)
  //   })
  // },
  // getToken(code){
  //   app.myrequest({
  //     url: '/user/login',
  //     method:'POST',
  //     data:{
  //       code:code
  //     },
			
  //     success:(data=>{
  //       if(data.data.code==200){ 
		// 		 console.log(data)
  //        app.globalData.token = data.data.data.token
  //        this.name = data.data.data.userName
  //        this.getHealthCode()
		// 		 console.log("登录成功")
  //       }
  //     })
  //   })
  // },
  refresh(){
		this.getHealthCode()
	},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
		this.ifLogin()
  },


})