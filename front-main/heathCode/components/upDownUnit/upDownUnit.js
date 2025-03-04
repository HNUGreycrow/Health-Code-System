Component({

  /**
   * 接收父组件传值
   */
  properties: {
		// 文字
    text:{
      type:String
    },image_path:{
      type:String
    },page:{
      type:String
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
		
  },

  /**
   * 组件的方法列表
   */
  methods: {
		goto(event){
		  const page = event.currentTarget.dataset.page;
			console.log(page)
		  wx.navigateTo({
		    url: '/pages/'+page+'/'+page,
		  })
		},
  }
})