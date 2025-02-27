var wxbarcode = require('../../utils/index.js')
const app = getApp()


Page({
	
	login(){
		const wxLogin = () => {
		  return new Promise((resolve) => {
		    wx.login({
		      success(res) {
		        resolve(res.code);
						console.log()
		      }
		    });
		  });
		}
		wxLogin().then(code=>{
		  this.getToken(code)
		})
	},
	
	getToken(code){
	  app.myrequest({
	    url: '/user/login',
	    method:'POST',
	    data:{
	      code:code
	    },
			
	    success:(data=>{
	      if(data.data.code==200){ 
				 console.log(data)
	       app.globalData.token = data.data.data.token
				 app.globalData.name = data.data.data.name
	       // this.name = data.data.data.userName
	       // this.getHealthCode()
				 console.log("登录成功")
	      };
				wx.showToast({
				  title: '登录成功',
				  icon: 'success',
				  duration: 2000
				});
				setTimeout(() => {
				  wx.navigateTo({
				    url: '/pages/code/code',
				  })
				}, 2000);
	    })
	  })
	},
	
})